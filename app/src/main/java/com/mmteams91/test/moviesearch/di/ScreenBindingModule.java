package com.mmteams91.test.moviesearch.di;

import com.mmteams91.test.moviesearch.screens.findmovies.FindMoviesFragment;
import com.mmteams91.test.moviesearch.screens.findmovies.FindMoviesModule;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Created by Михаил on 29.01.2018.
 */
@Module
public abstract class ScreenBindingModule {
    @ActivityScope
    @ContributesAndroidInjector(modules = FindMoviesModule.class)
    abstract FindMoviesFragment findMoviesFragment();
}
