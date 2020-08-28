package com.deepak.itunesearch;

import android.app.Application;
import android.content.Context;


public class SearchApplication extends Application {

    /**
     * Instance of our application
     */
    private static SearchApplication instance;

    /**
     *
     * @return Context of our application
     */
    public static Context getContext(){
        return instance;
    }

    @Override
    public void onCreate() {

        instance = this;

        super.onCreate();

    }

}
