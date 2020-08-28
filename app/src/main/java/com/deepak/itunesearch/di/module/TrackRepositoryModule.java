package com.deepak.itunesearch.di.module;

import com.deepak.itunesearch.data.remote.Api;
import com.deepak.itunesearch.data.remote.Repository;
import com.deepak.itunesearch.utility.Constants;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.reactivex.disposables.CompositeDisposable;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;



@Module(includes = OkHttpClientModule.class)
public class TrackRepositoryModule {

    @Provides
    @Singleton
    public Retrofit provideRetrofit(OkHttpClient okHttpClient){
        return new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();
    }

    @Provides
    public Api getTrackService(Retrofit retrofit){
        return retrofit.create(Api.class);
    }


    @Provides
    public CompositeDisposable provideDisposable(){
        return new CompositeDisposable();
    }

    @Provides
    @Singleton
    public Repository provideTrackRepository(Api api, CompositeDisposable disposable){
        return new Repository(api, disposable);
    }
}
