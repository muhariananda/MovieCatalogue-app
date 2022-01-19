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

import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class TvShowViewModelTest {

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();
    private TvShowViewModel viewModel;
    private CatalogueRepository catalogueRepository = mock(CatalogueRepository.class);

    private String USERNAME = "Aria";

    @Before
    public void setUp() {
        viewModel = new TvShowViewModel(catalogueRepository);
    }

    @Test
    public void getTvShows() {
        Resource<List<TvShowEntity>> dummyTvShow = Resource.success(FakeDataDummy.generateDummyTvShows());

        MutableLiveData<Resource<List<TvShowEntity>>> tvShows = new MutableLiveData<>();
        tvShows.setValue(dummyTvShow);

        when(catalogueRepository.getAllTvShows()).thenReturn(tvShows);

        Observer<Resource<List<TvShowEntity>>> observer = mock(Observer.class);

        viewModel.setUsername(USERNAME);
        viewModel.init();

        viewModel.getTvShows().observeForever(observer);

        verify(observer).onChanged(dummyTvShow);
    }
}