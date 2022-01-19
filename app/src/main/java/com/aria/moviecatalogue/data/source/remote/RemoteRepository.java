package com.aria.moviecatalogue.data.source.remote;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.aria.moviecatalogue.BuildConfig;
import com.aria.moviecatalogue.data.source.local.entity.MovieEntity;
import com.aria.moviecatalogue.data.source.local.entity.TvShowEntity;
import com.aria.moviecatalogue.data.source.remote.response.ApiResponse;
import com.aria.moviecatalogue.data.source.remote.response.MovieResponse;
import com.aria.moviecatalogue.data.source.remote.response.TvShowResponse;
import com.aria.moviecatalogue.utils.EspressoIdlingResource;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RemoteRepository {

    private static final String LANGUAGE = "en-US";

    private static RemoteRepository INSTANCE;

    private TMDbApi api;

    private RemoteRepository(TMDbApi api) {
        this.api = api;
    }

    public static RemoteRepository getInstance() {
        if (INSTANCE == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BuildConfig.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            INSTANCE = new RemoteRepository(retrofit.create(TMDbApi.class));
        }

        return INSTANCE;
    }

    public LiveData<ApiResponse<List<MovieEntity>>> getAllMovies() {
        EspressoIdlingResource.increment();
        MutableLiveData<ApiResponse<List<MovieEntity>>> movieAllResult = new MutableLiveData<>();

        api.getAllMovies(BuildConfig.API_KEY, LANGUAGE, 1).enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                if (response.isSuccessful()) {
                    MovieResponse movieResponse = response.body();
                    if (movieResponse != null && movieResponse.getResults() != null) {
                        movieAllResult.setValue(ApiResponse.success(movieResponse.getResults()));
                        if (!EspressoIdlingResource.getEspressoIdlingResource().isIdleNow()) {
                            EspressoIdlingResource.decrement();
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                movieAllResult.setValue(null);
            }
        });

        return movieAllResult;
    }

    public LiveData<ApiResponse<List<TvShowEntity>>> getAllTvShows() {
        EspressoIdlingResource.increment();
        MutableLiveData<ApiResponse<List<TvShowEntity>>> tvShowAllResult = new MutableLiveData<>();

        api.getAllTvShows(BuildConfig.API_KEY, LANGUAGE, 1).enqueue(new Callback<TvShowResponse>() {
            @Override
            public void onResponse(Call<TvShowResponse> call, Response<TvShowResponse> response) {
                if (response.isSuccessful()) {
                    TvShowResponse tvShowResponse = response.body();
                    if (tvShowResponse != null && tvShowResponse.getResults() != null) {
                        tvShowAllResult.setValue(ApiResponse.success(tvShowResponse.getResults()));
                        if (!EspressoIdlingResource.getEspressoIdlingResource().isIdleNow()) {
                            EspressoIdlingResource.decrement();
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<TvShowResponse> call, Throwable t) {
                tvShowAllResult.setValue(null);
            }
        });

        return tvShowAllResult;
    }

    public LiveData<ApiResponse<MovieEntity>> getMovies(int movieId) {
        EspressoIdlingResource.increment();
        MutableLiveData<ApiResponse<MovieEntity>> movieResult = new MutableLiveData<>();

        api.getMovie(movieId, BuildConfig.API_KEY, LANGUAGE).enqueue(new Callback<MovieEntity>() {
            @Override
            public void onResponse(Call<MovieEntity> call, Response<MovieEntity> response) {
                if (response.isSuccessful()) {
                    movieResult.setValue(ApiResponse.success(response.body()));
                    if (!EspressoIdlingResource.getEspressoIdlingResource().isIdleNow()) {
                        EspressoIdlingResource.decrement();
                    }
                }
            }

            @Override
            public void onFailure(Call<MovieEntity> call, Throwable t) {
                movieResult.setValue(ApiResponse.success(null));
            }
        });
        return movieResult;
    }

    public LiveData<ApiResponse<TvShowEntity>> getTvShow(int tvShowId) {
        MutableLiveData<ApiResponse<TvShowEntity>> tvShowResult = new MutableLiveData<>();

        api.getTvShow(tvShowId, BuildConfig.API_KEY, LANGUAGE).enqueue(new Callback<TvShowEntity>() {
            @Override
            public void onResponse(Call<TvShowEntity> call, Response<TvShowEntity> response) {
                if (response.isSuccessful()) {
                    tvShowResult.setValue(ApiResponse.success(response.body()));
                    if (!EspressoIdlingResource.getEspressoIdlingResource().isIdleNow()) {
                        EspressoIdlingResource.decrement();
                    }
                }
            }

            @Override
            public void onFailure(Call<TvShowEntity> call, Throwable t) {
                tvShowResult.setValue(null);
            }
        });
        return tvShowResult;
    }

//    public LiveData<MovieEntity> getMovies(int movieId) {
//        EspressoIdlingResource.increment();
//        MutableLiveData<MovieEntity> movieResult = new MutableLiveData<>();
//
//        api.getMovie(movieId, BuildConfig.API_KEY, LANGUAGE).enqueue(new Callback<MovieEntity>() {
//            @Override
//            public void onResponse(Call<MovieEntity> call, Response<MovieEntity> response) {
//                if (response.isSuccessful()){
//                    movieResult.setValue(response.body());
//                    if (!EspressoIdlingResource.getEspressoIdlingResource().isIdleNow()) {
//                        EspressoIdlingResource.decrement();
//                    }
//                }
//            }
//
//            @Override
//            public void onFailure(Call<MovieEntity> call, Throwable t) {
//                movieResult.setValue(null);
//            }
//        });
//        Log.d(TAG, "getMovies: " + movieResult.getValue());
//        return movieResult;
//    }
//
//    public LiveData<TvShowEntity> getTvShow(int tvShowId) {
//        MutableLiveData<TvShowEntity> tvShowResult = new MutableLiveData<>();
//
//        api.getTvShow(tvShowId, BuildConfig.API_KEY, LANGUAGE).enqueue(new Callback<TvShowEntity>() {
//            @Override
//            public void onResponse(Call<TvShowEntity> call, Response<TvShowEntity> response) {
//                if (response.isSuccessful()) {
//                    tvShowResult.setValue(response.body());
//                }
//            }
//
//            @Override
//            public void onFailure(Call<TvShowEntity> call, Throwable t) {
//                tvShowResult.setValue(null);
//            }
//        });
//        Log.d(TAG, "getTvShow: " + tvShowResult);
//        return tvShowResult;
//    }

}
