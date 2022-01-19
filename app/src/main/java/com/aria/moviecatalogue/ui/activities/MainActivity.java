package com.aria.moviecatalogue.ui.activities;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.aria.moviecatalogue.R;
import com.aria.moviecatalogue.ui.fragments.FavoriteFragment;
import com.aria.moviecatalogue.ui.fragments.MovieFragment;
import com.aria.moviecatalogue.ui.fragments.TvShowFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private final String SELECTED_MENU = "selected_menu";
    private BottomNavigationView navigationView;
    private String actionBarTitle;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemReselectedListener = menuItem -> {
        Fragment fragment = null;

        if (menuItem.getItemId() == R.id.action_movie) {
            fragment = MovieFragment.newInstance();
            actionBarTitle = getResources().getString(R.string.movies);
        } else if (menuItem.getItemId() == R.id.action_tv) {
            fragment = TvShowFragment.newInstance();
            actionBarTitle = getResources().getString(R.string.tv_show);
        } else if (menuItem.getItemId() == R.id.action_favorite) {
            fragment = FavoriteFragment.newInstance();
            actionBarTitle = getResources().getString(R.string.favorite);
        }

        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                    .replace(R.id.container, fragment)
                    .commit();
        }

        if (getSupportActionBar() != null) {
            getSupportActionBar().setElevation(0);
            getSupportActionBar().setTitle(actionBarTitle);
        }

        return true;
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        navigationView = findViewById(R.id.nav_view);
        navigationView.setOnNavigationItemSelectedListener(mOnNavigationItemReselectedListener);

        if (savedInstanceState != null) {
            savedInstanceState.getInt(SELECTED_MENU);
        } else {
            navigationView.setSelectedItemId(R.id.action_movie);
        }

    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(SELECTED_MENU, navigationView.getSelectedItemId());
    }
}
