package com.mmteams91.test.moviesearch.screens.findmovies;

import android.util.Log;

import com.mmteams91.test.moviesearch.data.managers.DataManager;
import com.mmteams91.test.moviesearch.data.network.dto.ConfigureDto;
import com.mmteams91.test.moviesearch.data.network.dto.FindMovieDto;
import com.mmteams91.test.moviesearch.di.ActivityScope;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;

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
    List<FindMovieDto> findMovies;
    CompositeDisposable disposable = new CompositeDisposable();

    @Inject
    public FindMoviesPresenter(DataManager dataManager) {
        Log.e(TAG, "FindMoviesPresenter() ");
        this.dataManager = dataManager;
        findMovies = new ArrayList<>();
    }

    @Override
    public void takeView(FindMoviesContract.View view) {
        this.view = view;
        disposable.add(dataManager
                .loadConfig()
                .subscribe(this::saveConfig, view::showError));

    }

    @Override
    public void onResume() {
        if (query != null && !query.isEmpty())
            view.setQueryString(query);
        view.setMoviesDtoContainer(findMovies);
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
        findMovies = new ArrayList<>();
        view.setMoviesDtoContainer(findMovies);
        findNextMovies();
    }

    private void findNextMovies() {
        if (pageCount >= page) {
            disposable.add(
                    dataManager.findMovies(query, language, page)
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(movieRes -> {
                                pageCount = movieRes.getTotalPages();
                                addMovies(movieRes.getResults());
                            }, throwable -> view.showError(throwable))
            );
            page++;
        }
    }

    private void addMovies(List<FindMovieDto> movieDtos) {
        int startIndex = findMovies.size();
        int count = movieDtos.size();
        findMovies.addAll(movieDtos);
        if (view != null)
            view.showMovies(startIndex, count);
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
        disposable.clear();
        this.view = null;
    }
}
