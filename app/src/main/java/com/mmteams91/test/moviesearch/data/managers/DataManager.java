package com.mmteams91.test.moviesearch.data.managers;

import android.content.Context;

import com.mmteams91.test.moviesearch.data.network.CheckNetworkTransformer;
import com.mmteams91.test.moviesearch.data.network.RestApi;
import com.mmteams91.test.moviesearch.data.network.dto.ConfigureDto;
import com.mmteams91.test.moviesearch.data.network.dto.FindMovieRes;
import com.mmteams91.test.moviesearch.data.network.dto.MovieDto;
import com.mmteams91.test.moviesearch.utils.NetworkStatusChecker;

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
    private final NetworkStatusChecker networkStatusChecker;


    @Inject
    public DataManager(PreferenceManager preferenceManager, RestApi api, Context context) {
        this.preferenceManager = preferenceManager;
        this.api = api;
        this.networkStatusChecker = new NetworkStatusChecker(context);
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
        return api.getConfig()
                .compose(new CheckNetworkTransformer<>(networkStatusChecker));
    }

    public Observable<FindMovieRes> findMovies(String query, String language, int page) {
        return api.findMovies(query, language, page)
                .compose(new CheckNetworkTransformer<>(networkStatusChecker));
    }

    public Observable<MovieDto> loadMovie(Integer id, String language) {
        return api.getMovie(id, language)
                .compose(new CheckNetworkTransformer<>(networkStatusChecker));
    }

    public String getPosterSize() {
        return preferenceManager.getPosterSize();
    }

    public String getBaseImageUrl() {
        return preferenceManager.getBaseImageUrl();
    }
}
