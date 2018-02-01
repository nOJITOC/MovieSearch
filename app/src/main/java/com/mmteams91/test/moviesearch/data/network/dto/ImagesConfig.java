package com.mmteams91.test.moviesearch.data.network.dto;

import com.squareup.moshi.Json;

import java.util.List;

/**
 * Created by User New on 30.01.2018.
 */

public class ImagesConfig {

    @Json(name = "base_url")
    private String baseUrl;
    @Json(name = "secure_base_url")
    private String secureBaseUrl;
    @Json(name = "poster_sizes")
    private List<String> posterSizes = null;

    public List<String> getPosterSizes() {
        return posterSizes;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public String getSecureBaseUrl() {
        return secureBaseUrl;
    }

    public void setSecureBaseUrl(String secureBaseUrl) {
        this.secureBaseUrl = secureBaseUrl;
    }
}
