package com.mmteams91.test.moviesearch.data.network;

import com.mmteams91.test.moviesearch.AppConfig;
import com.mmteams91.test.moviesearch.data.network.dto.GenresRes;
import com.mmteams91.test.moviesearch.data.network.dto.MovieDto;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by User New on 29.01.2018.
 */

public interface RestApi {

    @GET("search/movie?api_key=" + AppConfig.API_KEY)
    Observable<List<MovieDto>> findMovies(@Query("query") String query, @Query("language") String language);
    @GET("/genre/movie/list?api_key=" + AppConfig.API_KEY)
    Observable<GenresRes> getGenres();
}
