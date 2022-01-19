package com.aria.moviecatalogue.data.source;

import androidx.lifecycle.LiveData;
import androidx.paging.PagedList;

import com.aria.moviecatalogue.data.source.local.entity.MovieEntity;
import com.aria.moviecatalogue.data.source.local.entity.TvShowEntity;
import com.aria.moviecatalogue.vo.Resource;

import java.util.List;

public interface CatalogueDataSource {

    LiveData<Resource<List<MovieEntity>>> getAllMovies();

    LiveData<Resource<List<TvShowEntity>>> getAllTvShows();

    LiveData<Resource<MovieEntity>> getMovies(int movieId);

    LiveData<Resource<TvShowEntity>> getTvShow(int tvShowId);

    LiveData<Resource<PagedList<MovieEntity>>> getAllFavoriteMovies();

    LiveData<Resource<PagedList<TvShowEntity>>> getAllFavoriteTvShows();

    void setMovieFavorite(MovieEntity movies, boolean state);

    void setTvShowFavorite(TvShowEntity tvShows, boolean state);
}
