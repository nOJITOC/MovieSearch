
package com.mmteams91.test.moviesearch.data.network.dto;

import com.squareup.moshi.Json;

public class Genre {

    @Json(name = "name")
    private String name;
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
