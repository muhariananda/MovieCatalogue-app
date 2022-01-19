package com.aria.moviecatalogue.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.paging.PagedList;

import com.aria.moviecatalogue.data.source.CatalogueRepository;
import com.aria.moviecatalogue.data.source.local.entity.MovieEntity;
import com.aria.moviecatalogue.vo.Resource;

public class FavoriteMovieViewModel extends ViewModel {
    private CatalogueRepository catalogueRepository;
    private LiveData<Resource<PagedList<MovieEntity>>> liveData;

    public FavoriteMovieViewModel(CatalogueRepository catalogueRepository) {
        this.catalogueRepository = catalogueRepository;
    }

    public void init() {
        if (liveData != null) {
            return;
        }

        liveData = catalogueRepository.getAllFavoriteMovies();
    }

    public LiveData<Resource<PagedList<MovieEntity>>> getFavoriteMovies() {
        return liveData;
    }
}
