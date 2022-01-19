package com.aria.moviecatalogue.viewmodel;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.aria.moviecatalogue.data.source.CatalogueRepository;
import com.aria.moviecatalogue.data.source.local.entity.TvShowEntity;
import com.aria.moviecatalogue.utils.FakeDataDummy;
import com.aria.moviecatalogue.vo.Resource;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class DetailTvShowViewModelTest {

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    private DetailTvShowViewModel viewModel;
    private CatalogueRepository catalogueRepository = mock(CatalogueRepository.class);
    private TvShowEntity dummyTvShows = FakeDataDummy.generateDummyTvShows().get(0);
    private int tvShowId = dummyTvShows.getId();

    @Before
    public void setUp() {
        viewModel = new DetailTvShowViewModel(catalogueRepository);
        viewModel.setTvShowId(tvShowId);
    }

    @Test
    public void getTvShows() {
        Resource<TvShowEntity> resource = Resource.success(FakeDataDummy.generateDummyTvShows().get(0));
        MutableLiveData<Resource<TvShowEntity>> tvShowEntities = new MutableLiveData<>();
        tvShowEntities.setValue(resource);

        when(catalogueRepository.getTvShow(tvShowId)).thenReturn(tvShowEntities);

        Observer<Resource<TvShowEntity>> observer = mock(Observer.class);

        viewModel.init();
        viewModel.getTvShow().observeForever(observer);

        verify(observer).onChanged(resource);
    }
}