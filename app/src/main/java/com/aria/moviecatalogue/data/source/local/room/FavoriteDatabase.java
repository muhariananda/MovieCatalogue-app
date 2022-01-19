package com.aria.moviecatalogue.data.source.local.room;

import android.content.Context;
import android.util.Log;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.aria.moviecatalogue.data.source.local.entity.MovieEntity;
import com.aria.moviecatalogue.data.source.local.entity.TvShowEntity;

@Database(entities = {MovieEntity.class, TvShowEntity.class}, version = 1, exportSchema = false)
public abstract class FavoriteDatabase extends RoomDatabase {

    private static FavoriteDatabase INSTANCE;

    public abstract FavoriteDao favoriteDao();

    private static final Object sLock = new Object();
    private static final String TAG = FavoriteDatabase.class.getSimpleName();

    public static FavoriteDatabase getInstance(Context context) {
        synchronized (sLock) {
            if (INSTANCE == null) {
                Log.d(TAG, "Create database instance");

                INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                        FavoriteDatabase.class,
                        "Favorite.db").build();
            }
            Log.d(TAG, "Getting database instance");
            return INSTANCE;
        }
    }
}
