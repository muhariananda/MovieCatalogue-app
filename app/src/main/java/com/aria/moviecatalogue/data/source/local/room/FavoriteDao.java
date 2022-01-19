package com.aria.moviecatalogue.data.source.local.room;

import androidx.annotation.WorkerThread;
import androidx.lifecycle.LiveData;
import androidx.paging.DataSource;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import com.aria.moviecatalogue.data.source.local.entity.MovieEntity;
import com.aria.moviecatalogue.data.source.local.entity.TvShowEntity;

import java.util.List;

@Dao
public interface FavoriteDao {

    //Movies
    @WorkerThread
    @Query("SELECT * FROM table_movies")
    LiveData<List<MovieEntity>> getAllMovies();

    @WorkerThread
    @Query("SELECT * FROM table_movies WHERE favorite = 1")
    DataSource.Factory<Integer, MovieEntity> getAllFavoriteMovies();

    @Transaction
    @Query("SELECT * FROM table_movies WHERE id =:movieId")
    LiveData<MovieEntity> getMovies(int movieId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long[] insertMovie(List<MovieEntity> movies);

    @Update(onConflict = OnConflictStrategy.FAIL)
    int updateMovie(MovieEntity movies);

    @Query("UPDATE table_movies SET title =:title WHERE id=:movieId")
    int updateMovieById(String title, int movieId);


    //tv shows
    @WorkerThread
    @Query("SELECT * FROM table_tv")
    LiveData<List<TvShowEntity>> getAllTvShows();

    @WorkerThread
    @Query("SELECT * FROM table_tv WHERE favorite = 1")
    DataSource.Factory<Integer, TvShowEntity> getAllFavoriteTvShows();

    @Transaction
    @Query("SELECT * FROM table_tv WHERE id =:tvShowId")
    LiveData<TvShowEntity> getTvShows(int tvShowId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long[] insertTvShow(List<TvShowEntity> tvShows);

    @Update(onConflict = OnConflictStrategy.FAIL)
    int updateTvShow(TvShowEntity tvShows);

    @Query("UPDATE table_tv SET name =:name WHERE id =:tvShowId")
    int updateTvById(String name, int tvShowId);
}
