package com.aria.moviecatalogue.viewmodel;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.paging.PagedList;

import com.aria.moviecatalogue.data.source.CatalogueRepository;
import com.aria.moviecatalogue.data.source.local.entity.TvShowEntity;
import com.aria.moviecatalogue.vo.Resource;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class FavoriteTvShowViewModelTest {

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    private FavoriteTvShowViewModel viewModel;
    private CatalogueRepository catalogueRepository = mock(CatalogueRepository.class);

    @Before
    public void setUp() {
        viewModel = new FavoriteTvShowViewModel(catalogueRepository);
    }

    @Test
    public void getTvShows() {

        MutableLiveData<Resource<PagedList<TvShowEntity>>> dummyTv = new MutableLiveData<>();
        PagedList<TvShowEntity> pagedList = mock(PagedList.class);

        dummyTv.setValue(Resource.success(pagedList));

        when(catalogueRepository.getAllFavoriteTvShows()).thenReturn(dummyTv);

        Observer<Resource<PagedList<TvShowEntity>>> observer = mock(Observer.class);

        viewModel.init();
        viewModel.getFavoriteTvShows().observeForever(observer);

        verify(observer).onChanged(Resource.success(pagedList));
    }

}