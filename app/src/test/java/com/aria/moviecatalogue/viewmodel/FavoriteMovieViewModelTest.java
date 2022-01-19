package com.aria.moviecatalogue.viewmodel;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.paging.PagedList;

import com.aria.moviecatalogue.data.source.CatalogueRepository;
import com.aria.moviecatalogue.data.source.local.entity.MovieEntity;
import com.aria.moviecatalogue.vo.Resource;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class FavoriteMovieViewModelTest {

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    private FavoriteMovieViewModel viewModel;
    private CatalogueRepository catalogueRepository = mock(CatalogueRepository.class);

    @Before
    public void setUp() {
        viewModel = new FavoriteMovieViewModel(catalogueRepository);
    }

    @Test
    public void getMovies() {
        MutableLiveData<Resource<PagedList<MovieEntity>>> dummyMovies = new MutableLiveData<>();
        PagedList<MovieEntity> pagedList = mock(PagedList.class);

        dummyMovies.setValue(Resource.success(pagedList));

        when(catalogueRepository.getAllFavoriteMovies()).thenReturn(dummyMovies);

        Observer<Resource<PagedList<MovieEntity>>> observer = mock(Observer.class);

        viewModel.init();
        viewModel.getFavoriteMovies().observeForever(observer);

        verify(observer).onChanged(Resource.success(pagedList));
    }

}