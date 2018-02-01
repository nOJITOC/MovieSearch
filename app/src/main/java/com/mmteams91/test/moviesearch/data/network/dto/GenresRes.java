package com.mmteams91.test.moviesearch.data.network.dto;

import com.squareup.moshi.Json;

import java.util.List;

/**
 * Created by Михаил on 29.01.2018.
 */

public class GenresRes {
    @Json(name = "genres")
    private List<GenreDto> genres;
    public static class GenreDto {
        @Json(name = "id")
        long id;
        @Json(name = "name")
        String name;
    }
}
