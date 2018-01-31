package com.mmteams91.test.moviesearch.screens.findmovies;

import com.mmteams91.test.moviesearch.di.ActivityScope;

import dagger.Binds;
import dagger.Module;

/**
 * Created by Михаил on 29.01.2018.
 */
@Module
public abstract class FindMoviesModule {

    @ActivityScope
    @Binds
    abstract FindMoviesContract.Presenter providePresenter(FindMoviesPresenter presenter);
}
