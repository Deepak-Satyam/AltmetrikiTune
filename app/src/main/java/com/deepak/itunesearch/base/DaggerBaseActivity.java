package com.deepak.itunesearch.base;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.deepak.itunesearch.data.remote.Repository;
import com.deepak.itunesearch.di.component.ApplicationComponent;
import com.deepak.itunesearch.di.component.DaggerApplicationComponent;
import com.deepak.itunesearch.di.module.ContextModule;

import javax.inject.Inject;


public abstract class DaggerBaseActivity extends AppCompatActivity {


    @Inject
    Repository repository;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ApplicationComponent component = DaggerApplicationComponent.builder()
                .contextModule(new ContextModule(this))
                .build();
        repository = component.getTrackRepository();

    }

    public Repository getRepository() {
        return repository;
    }

}
