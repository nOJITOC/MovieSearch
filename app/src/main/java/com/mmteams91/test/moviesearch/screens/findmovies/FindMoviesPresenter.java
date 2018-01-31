package com.mmteams91.test.moviesearch.screens.findmovies;

import android.util.Log;

import com.mmteams91.test.moviesearch.data.managers.DataManager;
import com.mmteams91.test.moviesearch.data.network.NetworkObserver;
import com.mmteams91.test.moviesearch.data.network.dto.ConfigureDto;
import com.mmteams91.test.moviesearch.di.ActivityScope;

import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;

/**
 * Created by Михаил on 29.01.2018.
 */
@ActivityScope
public class FindMoviesPresenter implements FindMoviesContract.Presenter {
    private static final String TAG = "FindMoviesPresenter";
    private final DataManager dataManager;
    private FindMoviesContract.View view;
    String query;
    private int page = 1;
    private int pageCount = 1;
    private String language;

    @Inject
    public FindMoviesPresenter(DataManager dataManager) {
        this.dataManager = dataManager;
    }

    @Override
    public void takeView(FindMoviesContract.View view) {
        this.view = view;
        dataManager
                .loadConfig()
                .subscribe(new NetworkObserver<ConfigureDto>() {
                    @Override
                    public void onNext(ConfigureDto value) {
                        saveConfig(value);
                    }
                });

    }

    private void saveConfig(ConfigureDto configureDto) {
        dataManager.saveBaseImageUrl(configureDto.getImages().getBaseUrl());
        int requiredWidth = (view.getDisplayWidth() - view.getMovieContainerPadding()) / 3;
        List<String> posterSizes = configureDto.getImages().getPosterSizes();
        Pattern digitsPattern = Pattern.compile("\\d+");
        for (String posterSize : posterSizes) {
            Matcher matcher = digitsPattern.matcher(posterSize);
            if (matcher.find() && requiredWidth <= Integer.parseInt(matcher.group())) {
                dataManager.savePosterSize(posterSize);
                break;
            }
        }
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
            dataManager.findMovies(query, language, page)
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
    public String getLanguage() {
        return language;
    }

    @Override
    public String getImageUri() {
        return dataManager.getBaseImageUrl() + dataManager.getPosterSize();
    }

    @Override
    public void dropView() {
        this.view = null;
    }
}
