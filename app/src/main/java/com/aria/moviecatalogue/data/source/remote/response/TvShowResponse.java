package com.aria.moviecatalogue.data.source.remote.response;

import com.aria.moviecatalogue.data.source.local.entity.TvShowEntity;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TvShowResponse {

    @SerializedName("page")
    private int page;

    @SerializedName("total_pages")
    private int totalPages;

    @SerializedName("results")
    private List<TvShowEntity> results;

    @SerializedName("total_results")
    private int totalResults;

    public void setResults(List<TvShowEntity> results) {
        this.results = results;
    }

    public List<TvShowEntity> getResults() {
        return results;
    }
}