package com.deepak.itunesearch.data.remote;

import androidx.lifecycle.MutableLiveData;

import com.deepak.itunesearch.model.ResponseResult;
import com.deepak.itunesearch.model.TrackResponse;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;


public class Repository {

    private CompositeDisposable disposable;

    /**
     * The API interface to use
     */

    private Api api;

    /**
     * Initializing the interface to implement
     */

    @Inject
    public Repository(Api api, CompositeDisposable disposable){
        this.api = api;
        this.disposable = disposable;

    }




    public MutableLiveData<ResponseResult> searchTerm(String term){
        MutableLiveData<ResponseResult> trackData = new MutableLiveData<>();

        disposable.add(api.searchTerm(term)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<Response<TrackResponse>>(){

                    @Override
                    public void onSuccess(Response<TrackResponse> trackResponseResponse) {

                        trackData.setValue(new ResponseResult<>(trackResponseResponse.body(), trackResponseResponse.raw()));
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        trackData.setValue(null);
                    }
                })

        );
        return trackData;
    }

}
