package com.aria.moviecatalogue.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.aria.moviecatalogue.data.source.CatalogueRepository;
import com.aria.moviecatalogue.data.source.local.entity.MovieEntity;
import com.aria.moviecatalogue.vo.Resource;

import java.util.List;

public class MovieViewModel extends ViewModel {
    private CatalogueRepository catalogueRepository;
    private LiveData<Resource<List<MovieEntity>>> mutableLiveData;

    private MutableLiveData<String> mLogin = new MutableLiveData<>();

    public MovieViewModel(CatalogueRepository catalogueRepository) {
        this.catalogueRepository = catalogueRepository;
    }

    public void init() {
        if (mutableLiveData != null) {
            return;
        }

        mutableLiveData = Transformations.switchMap(mLogin, data -> catalogueRepository.getAllMovies());
    }

    public LiveData<Resource<List<MovieEntity>>> getAllMovies() {
        return mutableLiveData;
    }

    public void setUsername(String username) {
        mLogin.setValue(username);
    }
}
