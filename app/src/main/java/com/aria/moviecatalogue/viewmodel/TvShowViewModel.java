package com.aria.moviecatalogue.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.aria.moviecatalogue.data.source.CatalogueRepository;
import com.aria.moviecatalogue.data.source.local.entity.TvShowEntity;
import com.aria.moviecatalogue.vo.Resource;

import java.util.List;

public class TvShowViewModel extends ViewModel {
    private CatalogueRepository catalogueRepository;
    private LiveData<Resource<List<TvShowEntity>>> mutableLiveData;

    private MutableLiveData<String> mLogin = new MutableLiveData<>();

    public TvShowViewModel(CatalogueRepository catalogueRepository) {
        this.catalogueRepository = catalogueRepository;
    }

    public void init() {
        if (mutableLiveData != null) {
            return;
        }

        mutableLiveData = Transformations.switchMap(mLogin,
                data -> catalogueRepository.getAllTvShows());
    }

    public LiveData<Resource<List<TvShowEntity>>> getTvShows() {
        return mutableLiveData;
    }

    public void setUsername(String username) {
        mLogin.setValue(username);
    }
}
