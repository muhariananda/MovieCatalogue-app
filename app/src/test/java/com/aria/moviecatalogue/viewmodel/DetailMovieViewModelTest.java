package com.aria.moviecatalogue.viewmodel;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.aria.moviecatalogue.data.source.CatalogueRepository;
import com.aria.moviecatalogue.data.source.local.entity.MovieEntity;
import com.aria.moviecatalogue.utils.FakeDataDummy;
import com.aria.moviecatalogue.vo.Resource;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class DetailMovieViewModelTest {

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    private DetailMovieViewModel viewModel;
    private CatalogueRepository catalogueRepository = mock(CatalogueRepository.class);

    private MovieEntity dummyMovies = FakeDataDummy.generateDummyMovies().get(0);
    private int movieId = dummyMovies.getId();

    @Before
    public void setUp() {
        viewModel = new DetailMovieViewModel(catalogueRepository);
        viewModel.setMovieId(movieId);
    }

    @Test
    public void getMovies() {
        Resource<MovieEntity> resource = Resource.success(FakeDataDummy.generateDummyMovies().get(0));
        MutableLiveData<Resource<MovieEntity>> movieEntities = new MutableLiveData<>();
        movieEntities.setValue(resource);

        when(catalogueRepository.getMovies(movieId)).thenReturn(movieEntities);

        Observer<Resource<MovieEntity>> observer = mock(Observer.class);

        viewModel.init();
        viewModel.getMovies().observeForever(observer);

        verify(observer).onChanged(resource);
    }
}