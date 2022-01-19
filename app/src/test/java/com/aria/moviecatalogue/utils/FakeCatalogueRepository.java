package com.aria.moviecatalogue.utils;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import com.aria.moviecatalogue.data.source.CatalogueDataSource;
import com.aria.moviecatalogue.data.source.local.LocalRepository;
import com.aria.moviecatalogue.data.source.local.entity.MovieEntity;
import com.aria.moviecatalogue.data.source.local.entity.TvShowEntity;
import com.aria.moviecatalogue.data.source.remote.NetworkBoundResource;
import com.aria.moviecatalogue.data.source.remote.RemoteRepository;
import com.aria.moviecatalogue.data.source.remote.response.ApiResponse;
import com.aria.moviecatalogue.vo.Resource;

import java.util.ArrayList;
import java.util.List;

public class FakeCatalogueRepository implements CatalogueDataSource {

    private volatile static FakeCatalogueRepository INSTANCE = null;

    private final LocalRepository localRepository;
    private RemoteRepository remoteRepository;
    private final AppExecutors appExecutors;

    public FakeCatalogueRepository(@NonNull LocalRepository localRepository, @NonNull RemoteRepository remoteRepository, AppExecutors appExecutors) {
        this.localRepository = localRepository;
        this.remoteRepository = remoteRepository;
        this.appExecutors = appExecutors;
    }

    public static FakeCatalogueRepository getInstance(LocalRepository localRepository, RemoteRepository remoteData, AppExecutors appExecutors) {
        if (INSTANCE == null) {
            synchronized (FakeCatalogueRepository.class) {
                if (INSTANCE == null) {
                    INSTANCE = new FakeCatalogueRepository(localRepository, remoteData, appExecutors);
                }
            }
        }
        return INSTANCE;
    }

    @Override
    public LiveData<Resource<List<MovieEntity>>> getAllMovies() {
        return new NetworkBoundResource<List<MovieEntity>, List<MovieEntity>>(appExecutors) {

            @Override
            protected LiveData<List<MovieEntity>> loadFromDB() {
                return localRepository.getAllMovies();
            }

            @Override
            protected Boolean shouldFetch(List<MovieEntity> data) {
                return (data == null) || (data.size() == 0);
            }

            @Override
            protected LiveData<ApiResponse<List<MovieEntity>>> createCall() {
                return remoteRepository.getAllMovies();
            }

            @Override
            protected void saveCallResult(List<MovieEntity> data) {
                List<MovieEntity> movieEntities = new ArrayList<>();
                if (data != null) {
                    movieEntities.addAll(data);
                }
                localRepository.insertMovies(movieEntities);
            }
        }.asLiveData();
    }

    @Override
    public LiveData<Resource<List<TvShowEntity>>> getAllTvShows() {
        return new NetworkBoundResource<List<TvShowEntity>, List<TvShowEntity>>(appExecutors) {

            @Override
            protected LiveData<List<TvShowEntity>> loadFromDB() {
                return localRepository.getAllTvShows();
            }

            @Override
            protected Boolean shouldFetch(List<TvShowEntity> data) {
                return (data == null) || (data.size() == 0);
            }

            @Override
            protected LiveData<ApiResponse<List<TvShowEntity>>> createCall() {
                return remoteRepository.getAllTvShows();
            }

            @Override
            protected void saveCallResult(List<TvShowEntity> data) {
                List<TvShowEntity> tvShowEntities = new ArrayList<>();
                if (data != null) {
                    tvShowEntities.addAll(data);
                }
                localRepository.insertTvShow(tvShowEntities);
            }
        }.asLiveData();
    }

    @Override
    public LiveData<Resource<MovieEntity>> getMovies(int movieId) {
        return new NetworkBoundResource<MovieEntity, MovieEntity>(appExecutors) {

            @Override
            protected LiveData<MovieEntity> loadFromDB() {
                return localRepository.getMovies(movieId);
            }

            @Override
            protected Boolean shouldFetch(MovieEntity data) {
                return data == null;
            }

            @Override
            protected LiveData<ApiResponse<MovieEntity>> createCall() {
                return remoteRepository.getMovies(movieId);
            }

            @Override
            protected void saveCallResult(MovieEntity data) {

            }
        }.asLiveData();
    }

    @Override
    public LiveData<Resource<TvShowEntity>> getTvShow(int tvShowId) {
        return new NetworkBoundResource<TvShowEntity, TvShowEntity>(appExecutors) {

            @Override
            protected LiveData<TvShowEntity> loadFromDB() {
                return localRepository.getTvShow(tvShowId);
            }

            @Override
            protected Boolean shouldFetch(TvShowEntity data) {
                return data == null;
            }

            @Override
            protected LiveData<ApiResponse<TvShowEntity>> createCall() {
                return remoteRepository.getTvShow(tvShowId);
            }

            @Override
            protected void saveCallResult(TvShowEntity data) {

            }
        }.asLiveData();
    }

    @Override
    public LiveData<Resource<PagedList<MovieEntity>>> getAllFavoriteMovies() {
        return new NetworkBoundResource<PagedList<MovieEntity>, List<MovieEntity>>(appExecutors) {

            @Override
            protected LiveData<PagedList<MovieEntity>> loadFromDB() {
                return new LivePagedListBuilder<>(localRepository.getAllFavoriteMovies(), 20).build();
            }

            @Override
            protected Boolean shouldFetch(PagedList<MovieEntity> data) {
                return false;
            }

            @Override
            protected LiveData<ApiResponse<List<MovieEntity>>> createCall() {
                return null;
            }

            @Override
            protected void saveCallResult(List<MovieEntity> data) {

            }
        }.asLiveData();
    }

    @Override
    public LiveData<Resource<PagedList<TvShowEntity>>> getAllFavoriteTvShows() {
        return new NetworkBoundResource<PagedList<TvShowEntity>, List<TvShowEntity>>(appExecutors) {

            @Override
            protected LiveData<PagedList<TvShowEntity>> loadFromDB() {
                return new LivePagedListBuilder<>(localRepository.getAllFavoriteTvShow(), 20).build();
            }

            @Override
            protected Boolean shouldFetch(PagedList<TvShowEntity> data) {
                return false;
            }

            @Override
            protected LiveData<ApiResponse<List<TvShowEntity>>> createCall() {
                return null;
            }

            @Override
            protected void saveCallResult(List<TvShowEntity> data) {

            }
        }.asLiveData();
    }

    @Override
    public void setMovieFavorite(MovieEntity movies, boolean state) {
        Runnable runnable = () -> localRepository.setMovieFavorite(movies, state);
        appExecutors.diskIO().execute(runnable);
    }

    @Override
    public void setTvShowFavorite(TvShowEntity tvShows, boolean state) {
        Runnable runnable = () -> localRepository.setTvShowFavorite(tvShows, state);
        appExecutors.diskIO().execute(runnable);
    }
}
