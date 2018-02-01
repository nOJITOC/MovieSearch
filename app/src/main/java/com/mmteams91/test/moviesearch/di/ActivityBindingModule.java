package com.mmteams91.test.moviesearch.di;

import com.mmteams91.test.moviesearch.screens.findmovies.FindMoviesActivity;
import com.mmteams91.test.moviesearch.screens.findmovies.FindMoviesModule;
import com.mmteams91.test.moviesearch.screens.showmovie.ShowMovieActivity;
import com.mmteams91.test.moviesearch.screens.showmovie.ShowMovieModule;
import com.mmteams91.test.moviesearch.screens.showmovie.ShowMovieScope;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Created by Михаил on 29.01.2018.
 */
@Module
public abstract class ActivityBindingModule {
    @ActivityScope
    @ContributesAndroidInjector(modules = FindMoviesModule.class)
    abstract FindMoviesActivity mainActivity();

    @ShowMovieScope
    @ContributesAndroidInjector(modules = ShowMovieModule.class)
    abstract ShowMovieActivity showMovieActivity();
}
