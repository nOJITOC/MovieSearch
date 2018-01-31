package com.mmteams91.test.moviesearch.screens.showmovie;

import com.mmteams91.test.moviesearch.MainContract;
import com.mmteams91.test.moviesearch.data.network.dto.FindMovieDto;
import com.mmteams91.test.moviesearch.di.ActivityScope;
import com.mmteams91.test.moviesearch.di.ScreenScope;

import dagger.Binds;
import dagger.Provides;

@dagger.Module
public abstract class ShowMovieModule {
    @Provides
    @ActivityScope
    static FindMovieDto provideMovieRes(ShowMovieActivity view) {
        return view.getClickedMovieDto();
    }

    @Provides
    @ActivityScope
    static String provideLanguage(ShowMovieActivity view) {
        return view.getLanguage();
    }

    @Binds
    @ActivityScope
    abstract ShowMovieContract.Presenter presenter(ShowMoviePresenter presenter);

}