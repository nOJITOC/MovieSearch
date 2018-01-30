package com.mmteams91.test.moviesearch.screens.findmovies;

import android.util.Log;

import com.mmteams91.test.moviesearch.MainContract;
import com.mmteams91.test.moviesearch.data.network.RestApi;
import com.mmteams91.test.moviesearch.data.network.dto.FindMovieDto;

import java.util.Locale;
import java.util.regex.Pattern;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;

/**
 * Created by Михаил on 29.01.2018.
 */

public class FindMoviesPresenter implements FindMoviesContract.Presenter {
    private static final String TAG = "FindMoviesPresenter";
    private FindMoviesContract.View view;
    private RestApi api;
    MainContract.Presenter mainPresenter;
    String query;
    private int page = 1;
    private int pageCount = 1;
    private String language;

    @Inject
    public FindMoviesPresenter(MainContract.Presenter mainPresenter, RestApi api) {
        this.api = api;
        this.mainPresenter = mainPresenter;
    }

    @Override
    public void takeView(FindMoviesContract.View view) {
        this.view = view;
        if (query != null)
            loadMovies(query);
    }

    @Override
    public void loadMovies(String query) {
        language = checkLanguage(query);
        this.query = query;
        page = 1;
        view.clearPrevResult();
        findNextMovies();
    }

    private void findNextMovies() {
        if (pageCount >= page) {
            api.findMovies(query, language, page)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(movieRes -> {
                        pageCount = movieRes.getTotalPages();
                        if (view != null)
                            view.addMovies(movieRes.getResults());
                    }, throwable -> Log.e(TAG, "accept: ", throwable));
            page++;
        }
    }

    private String checkLanguage(String query) {
        Pattern englishPattern = Pattern.compile("[A-Za-z]+");
        Pattern rusPattern = Pattern.compile("[А-Яа-я]+");
        if (englishPattern.matcher(query).find())
            return "en-US";
        else if (rusPattern.matcher(query).find())
            return "ru-RU";
        else if (view != null && view.getKeyboardLocale() != null && !view.getKeyboardLocale().isEmpty())
            return view.getKeyboardLocale();
        else if (Locale.getDefault().getLanguage().isEmpty()) {
            String lang = Locale.getDefault().getLanguage();
            String country = Locale.getDefault().getCountry();
            return lang + (country.isEmpty() ? "" : "-" + country);
        } else return "ru-RU";

    }

    @Override
    public void onLastItemBind() {
        findNextMovies();
    }

    @Override
    public void onMovieClick(FindMovieDto movieDto) {
        Log.e(TAG, "onMovieClick: " + movieDto.getTitle());
        mainPresenter.setMovie(movieDto, language);
    }

    @Override
    public void dropView() {
        this.view = null;
    }
}
