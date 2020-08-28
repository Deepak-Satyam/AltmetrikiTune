package com.deepak.itunesearch.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.deepak.itunesearch.data.remote.Repository;
import com.deepak.itunesearch.model.ResponseResult;

/**
 * ViewModel of {@link com.deepak.itunesearch.model.Track} to manage UI data
 *
 * @author May Ann Palencia on 31/08/2019
 * @version 1.0.0
 * @use
 * @desc Android Developer
 * @since 1.0
 * Copyright (c) 2019
 */
public class TrackViewModel extends ViewModel {

    /**
     * LiveData representing the response result of the API call
     */
    private MutableLiveData<ResponseResult> mutableLiveData;



    public void searchTerm(Repository repository, String term){
        mutableLiveData = repository.searchTerm(term);
    }
    /**
     * Expose the LiveData {@link com.deepak.itunesearch.model.TrackResponse} query so the UI can observe it.
     */
    public LiveData<ResponseResult> getTrackRepository() {
        return mutableLiveData;
    }

}
