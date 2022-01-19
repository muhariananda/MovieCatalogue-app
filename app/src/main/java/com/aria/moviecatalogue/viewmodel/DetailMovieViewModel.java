package com.aria.moviecatalogue.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.aria.moviecatalogue.data.source.CatalogueRepository;
import com.aria.moviecatalogue.data.source.local.entity.MovieEntity;
import com.aria.moviecatalogue.vo.Resource;

public class DetailMovieViewModel extends ViewModel {
    private CatalogueRepository catalogueRepository;

    private LiveData<Resource<MovieEntity>> mutableLiveData;
    private MutableLiveData<Integer> movieId = new MutableLiveData<>();

    public DetailMovieViewModel(CatalogueRepository catalogueRepository) {
        this.catalogueRepository = catalogueRepository;
    }

    public void init() {
        if (mutableLiveData != null) {
            return;
        }
        mutableLiveData = Transformations.switchMap(movieId,
                mMovieId -> catalogueRepository.getMovies(mMovieId));
    }

    public LiveData<Resource<MovieEntity>> getMovies() {
        return mutableLiveData;
    }

    public void setMovieId(int movieId) {
        this.movieId.setValue(movieId);
    }

    public int getMovieId() {
        if (movieId.getValue() == null) return 0;
        return movieId.getValue();
    }

    public void setFavorite() {
        if (mutableLiveData.getValue() != null) {
            MovieEntity movieEntity = mutableLiveData.getValue().data;
            if (movieEntity != null) {
                final boolean newState = !movieEntity.isFavorite();
                catalogueRepository.setMovieFavorite(movieEntity, newState);
            }
        }
    }
}
