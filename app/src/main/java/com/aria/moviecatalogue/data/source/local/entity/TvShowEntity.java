package com.aria.moviecatalogue.data.source.local.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

@Entity(tableName = "table_tv")
public class TvShowEntity {

    @PrimaryKey()
    @ColumnInfo(name = "id")
    @SerializedName("id")
    private int id;

    @ColumnInfo(name = "name")
    @SerializedName("name")
    private String name;

    @ColumnInfo(name = "first_air_date")
    @SerializedName("first_air_date")
    private String firstAirDate;

    @ColumnInfo(name = "overview")
    @SerializedName("overview")
    private String overview;

    @ColumnInfo(name = "poster_path")
    @SerializedName("poster_path")
    private String posterPath;

    @ColumnInfo(name = "backdrop_path")
    @SerializedName("backdrop_path")
    private String backdropPath;

    @ColumnInfo(name = "favorite")
    private boolean favorite = false;

    public TvShowEntity() {
    }

    public TvShowEntity(int id, String name, String firstAirDate, String overview, String posterPath, String backdropPath) {
        this.id = id;
        this.name = name;
        this.firstAirDate = firstAirDate;
        this.overview = overview;
        this.posterPath = posterPath;
        this.backdropPath = backdropPath;
    }

    public void setFirstAirDate(String firstAirDate) {
        this.firstAirDate = firstAirDate;
    }

    public String getFirstAirDate() {
        return firstAirDate;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getOverview() {
        return overview;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setBackdropPath(String backdropPath) {
        this.backdropPath = backdropPath;
    }

    public String getBackdropPath() {
        return backdropPath;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public boolean isFavorite() {
        return favorite;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }

    @Override
    public String toString() {
        return
                "TvShowEntity{" +
                        "first_air_date = '" + firstAirDate + '\'' +
                        ",overview = '" + overview + '\'' +
                        ",poster_path = '" + posterPath + '\'' +
                        ",backdrop_path = '" + backdropPath + '\'' +
                        ",name = '" + name + '\'' +
                        ",id = '" + id + '\'' +
                        "}";
    }
}