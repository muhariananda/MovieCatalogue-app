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

import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class MovieViewModelTest {

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    private MovieViewModel viewModel;
    private CatalogueRepository catalogueRepository = mock(CatalogueRepository.class);

    private String USERNAME = "Aria";

    @Before
    public void setUp() {
        viewModel = new MovieViewModel(catalogueRepository);
    }

    @Test
    public void getMovies() {
        Resource<List<MovieEntity>> dummyMovies = Resource.success(FakeDataDummy.generateDummyMovies());

        MutableLiveData<Resource<List<MovieEntity>>> movies = new MutableLiveData<>();
        movies.setValue(dummyMovies);

        when(catalogueRepository.getAllMovies()).thenReturn(movies);

        Observer<Resource<List<MovieEntity>>> observer = mock(Observer.class);

        viewModel.init();
        viewModel.setUsername(USERNAME);

        viewModel.getAllMovies().observeForever(observer);

        verify(observer).onChanged(dummyMovies);
    }
}