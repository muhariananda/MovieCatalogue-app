package com.aria.moviecatalogue.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.aria.moviecatalogue.data.source.CatalogueRepository;
import com.aria.moviecatalogue.data.source.local.entity.TvShowEntity;
import com.aria.moviecatalogue.vo.Resource;

public class DetailTvShowViewModel extends ViewModel {
    private CatalogueRepository catalogueRepository;

    private LiveData<Resource<TvShowEntity>> mutableLiveData;
    private MutableLiveData<Integer> tvShowId = new MutableLiveData<>();

    public DetailTvShowViewModel(CatalogueRepository catalogueRepository) {
        this.catalogueRepository = catalogueRepository;
    }

    public void init() {
        if (mutableLiveData != null) {
            return;
        }
        mutableLiveData = Transformations.switchMap(tvShowId,
                mTvShowId -> catalogueRepository.getTvShow(mTvShowId));
    }

    public LiveData<Resource<TvShowEntity>> getTvShow() {
        return mutableLiveData;
    }

    public void setTvShowId(int tvShowId) {
        this.tvShowId.setValue(tvShowId);
    }

    public int getTvShowId() {
        if (tvShowId.getValue() == null) return 0;
        return tvShowId.getValue();
    }

    public void setFavorite() {
        if (mutableLiveData.getValue() != null) {
            TvShowEntity tvShowEntity = mutableLiveData.getValue().data;
            if (tvShowEntity != null) {
                final boolean newState = !tvShowEntity.isFavorite();
                catalogueRepository.setTvShowFavorite(tvShowEntity, newState);
            }
        }
    }
}
