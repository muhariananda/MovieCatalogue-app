package com.aria.moviecatalogue.data.source.local;

import androidx.lifecycle.LiveData;
import androidx.paging.DataSource;

import com.aria.moviecatalogue.data.source.local.entity.MovieEntity;
import com.aria.moviecatalogue.data.source.local.entity.TvShowEntity;
import com.aria.moviecatalogue.data.source.local.room.FavoriteDao;

import java.util.List;

public class LocalRepository {

    private final FavoriteDao favoriteDao;

    public LocalRepository(FavoriteDao favoriteDao) {
        this.favoriteDao = favoriteDao;
    }

    private static LocalRepository INSTANCE;

    public static LocalRepository getInstance(FavoriteDao favoriteDao) {
        if (INSTANCE == null) {
            INSTANCE = new LocalRepository(favoriteDao);
        }
        return INSTANCE;
    }

    //Movies
    public LiveData<List<MovieEntity>> getAllMovies() {
        return favoriteDao.getAllMovies();
    }

    public LiveData<MovieEntity> getMovies(int movieId) {
        return favoriteDao.getMovies(movieId);
    }

    public void insertMovies(List<MovieEntity> movies) {
        favoriteDao.insertMovie(movies);
    }

    public void setMovieFavorite(MovieEntity movies, boolean newState) {
        movies.setFavorite(newState);
        favoriteDao.updateMovie(movies);
    }

    public void updateMovies(String title, int movieId) {
        favoriteDao.updateMovieById(title, movieId);
    }

    public DataSource.Factory<Integer, MovieEntity> getAllFavoriteMovies() {
        return favoriteDao.getAllFavoriteMovies();
    }

    //Tv Show
    public LiveData<List<TvShowEntity>> getAllTvShows() {
        return favoriteDao.getAllTvShows();
    }

    public LiveData<TvShowEntity> getTvShow(int tvShowId) {
        return favoriteDao.getTvShows(tvShowId);
    }

    public void insertTvShow(List<TvShowEntity> tvShow) {
        favoriteDao.insertTvShow(tvShow);
    }

    public void setTvShowFavorite(TvShowEntity tvShow, boolean newState) {
        tvShow.setFavorite(newState);
        favoriteDao.updateTvShow(tvShow);
    }

    public void updateTvShow(String name, int tvShowId) {
        favoriteDao.updateTvById(name, tvShowId);
    }

    public DataSource.Factory<Integer, TvShowEntity> getAllFavoriteTvShow() {
        return favoriteDao.getAllFavoriteTvShows();
    }

}
