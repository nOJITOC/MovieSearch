package com.mmteams91.test.moviesearch.data.network;

import com.mmteams91.test.moviesearch.AppConfig;
import com.mmteams91.test.moviesearch.data.network.dto.ConfigureDto;
import com.mmteams91.test.moviesearch.data.network.dto.FindMovieRes;
import com.mmteams91.test.moviesearch.data.network.dto.MovieDto;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by User New on 29.01.2018.
 */

public interface RestApi {

    @GET("search/movie?api_key=" + AppConfig.API_KEY)
    Observable<FindMovieRes> findMovies(@Query("query") String query, @Query("language") String language, @Query("page") int page);

    @GET("configuration?api_key=" + AppConfig.API_KEY)
    Observable<ConfigureDto> getConfig();

    @GET("movie/{movie_id}?api_key=" + AppConfig.API_KEY)
    Observable<MovieDto> getMovie(@Path("movie_id") int movieId, @Query("language") String language);
}
