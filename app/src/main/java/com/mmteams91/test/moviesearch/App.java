package com.mmteams91.test.moviesearch;

import android.content.Context;

import dagger.android.AndroidInjector;
import dagger.android.support.DaggerApplication;

/**
 * Created by User New on 29.01.2018.
 */

public class App extends DaggerApplication {

    @Override
    protected AndroidInjector<? extends DaggerApplication> applicationInjector() {
        return null;
    }
}
