package com.deepak.itunesearch.di.component;

import com.deepak.itunesearch.data.remote.Repository;
import com.deepak.itunesearch.di.module.TrackRepositoryModule;

import javax.inject.Singleton;

import dagger.Component;



@Singleton
@Component(modules = {TrackRepositoryModule.class})
public interface ApplicationComponent {

    Repository getTrackRepository();

}
