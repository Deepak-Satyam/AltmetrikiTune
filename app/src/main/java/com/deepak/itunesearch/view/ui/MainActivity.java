package com.deepak.itunesearch.view.ui;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.deepak.itunesearch.R;
import com.deepak.itunesearch.databinding.ActivityMainBinding;
import com.deepak.itunesearch.model.Track;
import com.deepak.itunesearch.model.TrackResponse;
import com.deepak.itunesearch.utility.UpdateCartCount;
import com.deepak.itunesearch.utility.Utility;
import com.deepak.itunesearch.view.adapter.TrackRecyclerViewAdapter;
import com.deepak.itunesearch.base.BaseActivity;
import com.deepak.itunesearch.viewmodel.MainViewModel;
import com.deepak.itunesearch.viewmodel.TrackViewModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;


public class MainActivity extends BaseActivity implements UpdateCartCount {

    /**
     * Data binding for activity
     */
    ActivityMainBinding activityMainBinding;

    /**
     * Activity UI components
     */
    @BindView(R.id.recyclerViewTrack)
    RecyclerView rvTrackList;

    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout srlLoading;

    @BindView(R.id.editTextTerm)
    EditText etTerm;

    @BindView(R.id.item_count)
    TextView tvCount;

    @BindView(R.id.cart_img)
    ImageView cartImage;

    @BindView(R.id.sortBtnLyt)
    LinearLayout sortBtnLyt;

    @BindView(R.id.sort_track_desc)
    TextView sort_track_desc;

    @BindView(R.id.sort_track)
    TextView sort_track_name;


    String term = "";

    /**
     * {@link ArrayList} to hold the resulting track items from API call
     */
    ArrayList<Track> trackArrayList = new ArrayList<>();

    /**
     * {@link RecyclerView} adapter to bind the resulting data to the activity view
     */
    TrackRecyclerViewAdapter trackRecyclerViewAdapter;

    /**
     * {@link androidx.lifecycle.ViewModel} to hold {@link Track} data
     */
    TrackViewModel trackViewModel;

    /**
     * {@link androidx.lifecycle.ViewModel} to hold {@link MainActivity} configuration data
     */
    MainViewModel mainViewModel;

    /**
     * Saving the {@link MainActivity} as the last visited activity
     */
    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    protected int layoutRes() {
        return R.layout.activity_main;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        // Obtain binding object using the Data Binding library
        activityMainBinding = (ActivityMainBinding) getActivityMainBinding();

        setViewModel();

        setUI();

        callSearch();

        Utility.setTrackList(new HashMap<>());

    }

    /**
     * Method to get the view models to handle data for this UI
     */
    public void setViewModel(){

        trackViewModel = ViewModelProviders.of(this).get(TrackViewModel.class);

        mainViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        activityMainBinding.setViewModel(mainViewModel);
    }

    /**
     * Method to set default values of the activity UI
     */
    public void setUI(){
        srlLoading.setOnRefreshListener(this::attemptSearch);
        setupRecyclerView();

        etTerm.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override public void afterTextChanged(Editable s) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                attemptSearch();
            }
        });

        cartImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, CartListActivity.class);
                startActivity(intent);
            }
        });

        sort_track_desc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Collections.sort(trackArrayList, new Comparator<Track>() {
                    @Override
                    public int compare(Track t1, Track t2) {
                        if(t1.getTrackPrice()!=null && t2.getTrackPrice()!=null){
                        if(t1.getTrackPrice()>t2.getTrackPrice()){
                            return -1;
                        }else if(t1.getTrackPrice()<t2.getTrackPrice()){
                            return 1;
                        }
                    }
                        return 0;
                    }
                });
                trackRecyclerViewAdapter.notifyDataSetChanged();
            }
        });


        sort_track_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Collections.sort(trackArrayList, new Comparator<Track>() {
                    @Override
                    public int compare(Track t1, Track t2) {
                        if(t1.getTrackName()!=null && t2.getTrackName()!=null){
                            return t1.getTrackName().compareTo(t2.getTrackName());
                        }
                        return 0;
                    }
                });
                trackRecyclerViewAdapter.notifyDataSetChanged();
            }
        });
    }



    /**
     * Set up RecyclerView adapter and layout manager
     */
    private void setupRecyclerView() {
        if (trackRecyclerViewAdapter == null) {
            trackRecyclerViewAdapter = new TrackRecyclerViewAdapter(MainActivity.this, trackArrayList);
            rvTrackList.setLayoutManager(new LinearLayoutManager(this));
            rvTrackList.setAdapter(trackRecyclerViewAdapter);
            rvTrackList.setItemAnimator(new DefaultItemAnimator());
            rvTrackList.setNestedScrollingEnabled(true);
        } else {
            trackRecyclerViewAdapter.notifyDataSetChanged();
        }
    }

   private void attemptSearch(){
        //Utility.hideKeyboard(MainActivity.this, etTerm);
        term = etTerm.getText().toString();

        if(TextUtils.isEmpty(term)) {
            Utility.setEditTextError(etTerm, getString(R.string.required));
            return;
        }


        callSearch();

    }


    /**
     * Method to call the searchTrack function from network repository. This function will call the iTunes Search API
     */
    public void callSearch(){
        trackArrayList.clear();
        trackViewModel.searchTerm(getRepository(),term);
        observeTrackDataResponse();
    }

    /**
     * Observe API call response and update {@link RecyclerView} list for data changes
     */
    private void observeTrackDataResponse(){
        mainViewModel.setRefreshing(true);
        trackViewModel.getTrackRepository().observe(this, trackResponseResult -> {
            if(trackResponseResult.getResponseBody().isSuccessful()){
                List<Track> tracks = ((TrackResponse) trackResponseResult.getResponse()).getResults();
                mainViewModel.setIsResultEmpty(tracks.size() <= 0);
                if(tracks.size()>0){
                    sortBtnLyt.setVisibility(View.VISIBLE);
                }else{
                    sortBtnLyt.setVisibility(View.GONE);
                }
                trackArrayList.addAll(tracks);
            }
            else{
                // Show a message feedback to user when api response is not successful due to network error
                if(!Utility.isNetworkAvailable(MainActivity.this)) {
                    Toast.makeText(MainActivity.this, getString(R.string.no_network_available), Toast.LENGTH_SHORT).show();
                    trackArrayList.clear();
                }
            }

            trackRecyclerViewAdapter.notifyDataSetChanged();
            mainViewModel.setRefreshing(false);
        });

    }

    @Override
    public void updateCount(int count) {
        tvCount.setText(""+count);
    }


}
