package com.mmteams91.test.moviesearch.data.managers;

import com.mmteams91.test.moviesearch.data.network.RestApi;
import com.mmteams91.test.moviesearch.data.network.dto.ConfigureDto;
import com.mmteams91.test.moviesearch.data.network.dto.FindMovieRes;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;

/**
 * Created by Михаил on 31.01.2018.
 */
@Singleton
public class DataManager {

    private final PreferenceManager preferenceManager;
    private final RestApi api;

    @Inject
    public DataManager(PreferenceManager preferenceManager, RestApi api) {
        this.preferenceManager = preferenceManager;
        this.api = api;
    }

    public PreferenceManager getPreferenceManager() {
        return preferenceManager;
    }

    public RestApi getApi() {
        return api;
    }

    public void saveBaseImageUrl(String baseUrl) {
        preferenceManager.saveBaseImageUrl(baseUrl);
    }

    public void savePosterSize(String posterSize) {
        preferenceManager.savePosterSize(posterSize);
    }

    public Observable<ConfigureDto> loadConfig() {
        return api.getConfig();
    }

    public Observable<FindMovieRes> findMovies(String query, String language, int page) {
        return api.findMovies(query, language, page);
    }
}
