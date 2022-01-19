package com.aria.moviecatalogue.di;

import android.app.Application;

import com.aria.moviecatalogue.data.source.CatalogueRepository;
import com.aria.moviecatalogue.data.source.local.LocalRepository;
import com.aria.moviecatalogue.data.source.local.room.FavoriteDatabase;
import com.aria.moviecatalogue.data.source.remote.RemoteRepository;
import com.aria.moviecatalogue.utils.AppExecutors;

public class Injection {
    public static CatalogueRepository provideRepository(Application application) {

        FavoriteDatabase database = FavoriteDatabase.getInstance(application);

        LocalRepository localRepository = LocalRepository.getInstance(database.favoriteDao());
        RemoteRepository remoteRepository = RemoteRepository.getInstance();
        AppExecutors appExecutors = new AppExecutors();

        return CatalogueRepository.getInstance(localRepository, remoteRepository, appExecutors);
    }
}
