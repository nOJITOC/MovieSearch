package com.mmteams91.test.moviesearch;

import com.mmteams91.test.moviesearch.data.managers.PreferenceManager;
import com.mmteams91.test.moviesearch.data.network.dto.ConfigureDto;
import com.mmteams91.test.moviesearch.data.network.dto.FindMovieDto;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.inject.Inject;

/**
 * Created by User New on 30.01.2018.
 */

public class MainPresenter implements MainContract.Presenter {
    private MainContract.View view;
    private PreferenceManager preferenceManager;
    private FindMovieDto currentMovie;
    private String language;

    public MainPresenter(PreferenceManager preferenceManager) {
        this.preferenceManager = preferenceManager;
    }


    @Override
    public void saveConfig(ConfigureDto configureDto) {
        preferenceManager.saveBaseImageUrl(configureDto.getImages().getBaseUrl());
        int requiredWidth = (view.getDisplayWidth() - view.dpToPx(16*2))/3;
        List<String> posterSizes = configureDto.getImages().getPosterSizes();
        Pattern digitsPattern = Pattern.compile("\\d+");
        for (String posterSize : posterSizes) {
            Matcher matcher = digitsPattern.matcher(posterSize);
            if (matcher.find() && requiredWidth <= Integer.parseInt(matcher.group())) {
                preferenceManager.savePosterSize(posterSize);
                break;
            }
        }
    }

    @Override
    public void setMovie(FindMovieDto movie, String language) {
        this.currentMovie = movie;
        this.language = language;
        view.showMovieInfo(movie, language);
    }

    @Override
    public FindMovieDto getClickedMovieRes() {
        return currentMovie;
    }

    @Override
    public void takeView(MainContract.View view) {
        this.view = view;
    }

    @Override
    public String getLanguage() {
        return language;
    }

    @Override
    public void dropView() {
        this.view = null;
    }
}
