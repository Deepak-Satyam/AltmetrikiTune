package com.deepak.itunesearch.viewmodel;

import android.view.View;

import com.deepak.itunesearch.model.ResponseResult;
import com.deepak.itunesearch.model.Track;
import com.deepak.itunesearch.utility.Utility;

import java.util.ArrayList;
import java.util.List;

import androidx.databinding.BindingAdapter;
import androidx.databinding.ObservableField;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

public class CartViewModel extends ViewModel {
    private MutableLiveData<List<Track>> mutableLiveData = new MutableLiveData<>();

    public LiveData<List<Track>> getSelectedCartList() {
        List<Track> selecteTrack = new ArrayList<Track>(Utility.getTrackList().values());
        mutableLiveData.setValue(selecteTrack);
        return mutableLiveData;
    }

}
