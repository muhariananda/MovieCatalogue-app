package com.aria.moviecatalogue.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.paging.PagedList;

import com.aria.moviecatalogue.data.source.CatalogueRepository;
import com.aria.moviecatalogue.data.source.local.entity.TvShowEntity;
import com.aria.moviecatalogue.vo.Resource;

public class FavoriteTvShowViewModel extends ViewModel {
    private CatalogueRepository catalogueRepository;
    private LiveData<Resource<PagedList<TvShowEntity>>> liveData;

    public FavoriteTvShowViewModel(CatalogueRepository catalogueRepository) {
        this.catalogueRepository = catalogueRepository;
    }

    public void init() {
        if (liveData != null) {
            return;
        }

        liveData = catalogueRepository.getAllFavoriteTvShows();
    }

    public LiveData<Resource<PagedList<TvShowEntity>>> getFavoriteTvShows() {
        return liveData;
    }

}
