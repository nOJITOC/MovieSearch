package com.mmteams91.test.moviesearch.screens.findmovies;

import com.mmteams91.test.moviesearch.di.ScreenScope;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

/**
 * Created by Михаил on 29.01.2018.
 */
@Module
public abstract class FindMoviesModule {
    @ScreenScope
    @Binds
    abstract FindMoviesContract.Presenter providePresenter(FindMoviesPresenter presenter);
}
