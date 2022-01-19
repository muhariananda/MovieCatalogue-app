package com.aria.moviecatalogue.data.source.remote.response;

import com.aria.moviecatalogue.data.source.local.entity.MovieEntity;
import com.google.gson.annotations.SerializedName;

import java.util.List;


public class MovieResponse {

    @SerializedName("page")
    private int page;

    @SerializedName("total_pages")
    private int totalPages;

    @SerializedName("results")
    private List<MovieEntity> results;

    @SerializedName("total_results")
    private int totalResults;


    public void setResults(List<MovieEntity> results) {
        this.results = results;
    }

    public List<MovieEntity> getResults() {
        return results;
    }

}