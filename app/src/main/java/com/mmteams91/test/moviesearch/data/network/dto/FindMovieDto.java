package com.mmteams91.test.moviesearch.data.network.dto;

import com.squareup.moshi.Json;


public class FindMovieDto {
    @Json(name = "id")
    private Integer id;
    @Json(name = "title")
    private String title;
    @Json(name = "poster_path")
    private String posterPath;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }
}
