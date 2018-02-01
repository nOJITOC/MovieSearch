package com.mmteams91.test.moviesearch.screens.showmovie;

import com.mmteams91.test.moviesearch.data.network.dto.FindMovieDto;

import dagger.Binds;
import dagger.Provides;

@dagger.Module
public abstract class ShowMovieModule {
    @Provides
    @ShowMovieScope
    static FindMovieDto provideMovieRes(ShowMovieActivity view) {
        return view.getClickedMovieDto();
    }

    @Provides
    @ShowMovieScope
    static String provideLanguage(ShowMovieActivity view) {
        return view.getLanguage();
    }

    @Binds
    @ShowMovieScope
    abstract ShowMovieContract.Presenter presenter(ShowMoviePresenter presenter);

}