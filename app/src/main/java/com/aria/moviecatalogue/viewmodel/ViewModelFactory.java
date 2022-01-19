package com.aria.moviecatalogue.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.aria.moviecatalogue.data.source.CatalogueRepository;
import com.aria.moviecatalogue.di.Injection;

public class ViewModelFactory extends ViewModelProvider.NewInstanceFactory {
    private static volatile ViewModelFactory INSTANCE;

    private final CatalogueRepository catalogueRepository;

    private ViewModelFactory(CatalogueRepository catalogueRepository) {
        this.catalogueRepository = catalogueRepository;
    }

    public static ViewModelFactory getInstance(Application application) {
        if (INSTANCE == null) {
            synchronized (ViewModelFactory.class) {
                if (INSTANCE == null) {
                    INSTANCE = new ViewModelFactory(Injection.provideRepository(application));
                }
            }
        }
        return INSTANCE;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(MovieViewModel.class)) {
            //noinspection unchecked
            return (T) new MovieViewModel(catalogueRepository);
        } else if (modelClass.isAssignableFrom(TvShowViewModel.class)) {
            //noinspection unchecked
            return (T) new TvShowViewModel(catalogueRepository);
        } else if (modelClass.isAssignableFrom(DetailMovieViewModel.class)) {
            //noinspection unchecked
            return (T) new DetailMovieViewModel(catalogueRepository);
        } else if (modelClass.isAssignableFrom(DetailTvShowViewModel.class)) {
            //noinspection unchecked
            return (T) new DetailTvShowViewModel(catalogueRepository);
        } else if (modelClass.isAssignableFrom(FavoriteMovieViewModel.class)) {
            //noinspection unchecked
            return (T) new FavoriteMovieViewModel(catalogueRepository);
        } else if (modelClass.isAssignableFrom(FavoriteTvShowViewModel.class)) {
            //noinspection unchecked
            return (T) new FavoriteTvShowViewModel(catalogueRepository);
        }

        throw new IllegalArgumentException("Unknown ViewModel class: " + modelClass.getName());
    }
}
