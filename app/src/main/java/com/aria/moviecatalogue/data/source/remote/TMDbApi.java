package com.aria.moviecatalogue.data.source.remote;

import com.aria.moviecatalogue.data.source.local.entity.MovieEntity;
import com.aria.moviecatalogue.data.source.local.entity.TvShowEntity;
import com.aria.moviecatalogue.data.source.remote.response.MovieResponse;
import com.aria.moviecatalogue.data.source.remote.response.TvShowResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface TMDbApi {

    @GET("movie/popular")
    Call<MovieResponse> getAllMovies(
            @Query("api_key") String apiKey,
            @Query("language") String language,
            @Query("page") int page
    );

    @GET("tv/popular")
    Call<TvShowResponse> getAllTvShows(
            @Query("api_key") String apiKey,
            @Query("language") String language,
            @Query("page") int page
    );

    @GET("movie/{movie_id}")
    Call<MovieEntity> getMovie(
            @Path("movie_id") int id,
            @Query("api_key") String apiKey,
            @Query("language") String language
    );

    @GET("tv/{tv_id}")
    Call<TvShowEntity> getTvShow(
            @Path("tv_id") int id,
            @Query("api_key") String apiKey,
            @Query("language") String language
    );
}
