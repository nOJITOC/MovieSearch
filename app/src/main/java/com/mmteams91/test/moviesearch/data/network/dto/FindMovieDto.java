package com.mmteams91.test.moviesearch.data.network.dto;

import android.os.Parcel;
import android.os.Parcelable;

import com.squareup.moshi.Json;


public class FindMovieDto implements Parcelable {
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.id);
        dest.writeString(this.title);
        dest.writeString(this.posterPath);
    }

    public FindMovieDto() {
    }

    protected FindMovieDto(Parcel in) {
        this.id = (Integer) in.readValue(Integer.class.getClassLoader());
        this.title = in.readString();
        this.posterPath = in.readString();
    }

    public static final Parcelable.Creator<FindMovieDto> CREATOR = new Parcelable.Creator<FindMovieDto>() {
        @Override
        public FindMovieDto createFromParcel(Parcel source) {
            return new FindMovieDto(source);
        }

        @Override
        public FindMovieDto[] newArray(int size) {
            return new FindMovieDto[size];
        }
    };
}
