package com.deepak.itunesearch.data.remote;

import com.deepak.itunesearch.model.TrackResponse;

import io.reactivex.Single;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Query;


public interface Api {

    /**
     * Calling Search API request
     * @param term
    * @return This will return a Call object with type {@link TrackResponse}
     */
    @GET("/search")
    Single<Response<TrackResponse>> searchTerm(@Query("term") String term);
}
