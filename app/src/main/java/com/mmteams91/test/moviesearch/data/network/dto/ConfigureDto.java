package com.mmteams91.test.moviesearch.data.network.dto;

import com.squareup.moshi.Json;

/**
 * Created by User New on 30.01.2018.
 */

public class ConfigureDto {
    @Json(name = "images")
    private ImagesConfig images;

    public ImagesConfig getImages() {
        return images;
    }
}
