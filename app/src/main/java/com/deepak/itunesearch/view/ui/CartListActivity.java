package com.deepak.itunesearch.view.ui;

import android.os.Bundle;

import com.deepak.itunesearch.R;
import com.deepak.itunesearch.base.BaseActivity;
import com.deepak.itunesearch.databinding.ActivityMainBinding;
import com.deepak.itunesearch.model.Track;
import com.deepak.itunesearch.view.adapter.TrackRecyclerViewAdapter;
import com.deepak.itunesearch.viewmodel.CartViewModel;
import com.deepak.itunesearch.viewmodel.MainViewModel;
import com.deepak.itunesearch.viewmodel.TrackViewModel;

import java.util.ArrayList;
import java.util.List;

import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;

public class CartListActivity extends BaseActivity {

    CartViewModel cartViewModel;

    @BindView(R.id.recyclerViewTrack)
    RecyclerView rvTrackList;

    ArrayList<Track> trackArrayList = new ArrayList<>();

    /**
     * {@link RecyclerView} adapter to bind the resulting data to the activity view
     */
    TrackRecyclerViewAdapter trackRecyclerViewAdapter;

    @Override
    protected int layoutRes() {
        return R.layout.activity_cartlist;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setViewModel();
        setUI();
        fillList();


    }

    /**
     * Method to get the view models to handle data for this UI
     */
    public void setViewModel(){
            cartViewModel = ViewModelProviders.of(this).get(CartViewModel.class);
    }

    public void fillList(){
        trackArrayList.clear();
        List<Track> tracks = cartViewModel.getSelectedCartList().getValue();
        trackArrayList.addAll(tracks);
        trackRecyclerViewAdapter.notifyDataSetChanged();
    }

    public void setUI(){
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle("Cart List");

        if (trackRecyclerViewAdapter == null) {
            trackRecyclerViewAdapter = new TrackRecyclerViewAdapter(CartListActivity.this, trackArrayList);
            rvTrackList.setLayoutManager(new LinearLayoutManager(this));
            rvTrackList.setAdapter(trackRecyclerViewAdapter);
            rvTrackList.setItemAnimator(new DefaultItemAnimator());
            rvTrackList.setNestedScrollingEnabled(true);
        } else {
            trackRecyclerViewAdapter.notifyDataSetChanged();
        }
    }


}
