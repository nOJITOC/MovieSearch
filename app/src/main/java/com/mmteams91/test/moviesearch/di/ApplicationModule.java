package com.mmteams91.test.moviesearch.di;

import android.app.Application;
import android.content.Context;

import com.mmteams91.test.moviesearch.MainActivity;
import com.mmteams91.test.moviesearch.screens.showmovie.ShowMovieFragment;

import dagger.Binds;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Created by Михаил on 29.01.2018.
 */
@Module
abstract class ApplicationModule {
    @ActivityScope
    @ContributesAndroidInjector(modules = ShowMovieFragment.Module.class)
    abstract MainActivity mainActivity();

    @Binds
    abstract Context bindContext(Application application);
}
