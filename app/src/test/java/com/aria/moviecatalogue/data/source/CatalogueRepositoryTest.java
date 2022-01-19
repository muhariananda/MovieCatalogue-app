package com.aria.moviecatalogue.data.source;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;
import androidx.paging.PagedList;

import com.aria.moviecatalogue.data.source.local.LocalRepository;
import com.aria.moviecatalogue.data.source.local.entity.MovieEntity;
import com.aria.moviecatalogue.data.source.local.entity.TvShowEntity;
import com.aria.moviecatalogue.data.source.remote.RemoteRepository;
import com.aria.moviecatalogue.utils.FakeCatalogueRepository;
import com.aria.moviecatalogue.utils.FakeDataDummy;
import com.aria.moviecatalogue.utils.InstantAppExecutors;
import com.aria.moviecatalogue.utils.LiveDataTestUtil;
import com.aria.moviecatalogue.utils.PagedListUtil;
import com.aria.moviecatalogue.vo.Resource;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class CatalogueRepositoryTest {

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    private RemoteRepository remote = Mockito.mock(RemoteRepository.class);
    private LocalRepository local = Mockito.mock(LocalRepository.class);
    private InstantAppExecutors executors = Mockito.mock(InstantAppExecutors.class);
    private FakeCatalogueRepository fakeCatalogueRepository = new FakeCatalogueRepository(local, remote, executors);

    private ArrayList<MovieEntity> movieEntities = FakeDataDummy.generateDummyMovies();
    private int movieId = movieEntities.get(0).getId();

    private ArrayList<TvShowEntity> tvShowEntities = FakeDataDummy.generateDummyTvShows();
    private int tvShowId = tvShowEntities.get(0).getId();

    @Before
    public void setUp() {

    }

    @After
    public void tearDown() {

    }

    @Test
    public void getAllMovies() {
        MutableLiveData<List<MovieEntity>> dummyMovies = new MutableLiveData<>();
        dummyMovies.setValue(FakeDataDummy.generateDummyMovies());

        when(local.getAllMovies()).thenReturn(dummyMovies);

        Resource<List<MovieEntity>> result = LiveDataTestUtil.getValue(fakeCatalogueRepository.getAllMovies());

        verify(local).getAllMovies();

        assertNotNull(result);
        assertEquals(movieEntities.size(), result.data.size());
    }

    @Test
    public void getAllTvShows() {
        MutableLiveData<List<TvShowEntity>> dummyTv = new MutableLiveData<>();
        dummyTv.setValue(FakeDataDummy.generateDummyTvShows());

        when(local.getAllTvShows()).thenReturn(dummyTv);

        Resource<List<TvShowEntity>> result = LiveDataTestUtil.getValue(fakeCatalogueRepository.getAllTvShows());

        verify(local).getAllTvShows();

        assertNotNull(result);
        assertEquals(tvShowEntities.size(), result.data.size());
    }

    @Test
    public void getMovies() {
        MutableLiveData<MovieEntity> dummyMovie = new MutableLiveData<>();
        dummyMovie.setValue(FakeDataDummy.generateDummyMovies().get(0));

        when(local.getMovies(movieId)).thenReturn(dummyMovie);

        Resource<MovieEntity> result = LiveDataTestUtil.getValue(fakeCatalogueRepository.getMovies(movieId));

        verify(local).getMovies(movieId);
        assertNotNull(result);

        assertNotNull(result.data);
    }

    @Test
    public void getTvShow() {
        MutableLiveData<TvShowEntity> dummyTv = new MutableLiveData<>();
        dummyTv.setValue(FakeDataDummy.generateDummyTvShows().get(0));

        when(local.getTvShow(tvShowId)).thenReturn(dummyTv);

        Resource<TvShowEntity> result = LiveDataTestUtil.getValue(fakeCatalogueRepository.getTvShow(tvShowId));

        verify(local).getTvShow(tvShowId);
        assertNotNull(result);

        assertNotNull(result.data);
    }

    @Test
    public void getFavoriteMovies() {

        DataSource.Factory<Integer, MovieEntity> dataSourceFactory = mock(DataSource.Factory.class);

        when(local.getAllFavoriteMovies()).thenReturn(dataSourceFactory);
        fakeCatalogueRepository.getAllFavoriteMovies();
        Resource<PagedList<MovieEntity>> result = Resource.success(PagedListUtil.mockPagedList(movieEntities));

        verify(local).getAllFavoriteMovies();
        assertNotNull(result.data);
        assertEquals(movieEntities.size(), result.data.size());
    }

    @Test
    public void getFavoriteTvShow() {

        DataSource.Factory<Integer, TvShowEntity> dataSourceFactory = mock(DataSource.Factory.class);

        when(local.getAllFavoriteTvShow()).thenReturn(dataSourceFactory);
        fakeCatalogueRepository.getAllFavoriteTvShows();
        Resource<PagedList<TvShowEntity>> result = Resource.success(PagedListUtil.mockPagedList(tvShowEntities));

        verify(local).getAllFavoriteTvShow();
        assertNotNull(result.data);
        assertEquals(tvShowEntities.size(), result.data.size());
    }
}