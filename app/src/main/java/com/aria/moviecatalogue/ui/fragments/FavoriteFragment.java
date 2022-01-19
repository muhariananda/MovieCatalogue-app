package com.aria.moviecatalogue.ui.fragments;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.aria.moviecatalogue.R;
import com.aria.moviecatalogue.ui.adapter.TabPagerAdapter;
import com.google.android.material.tabs.TabLayout;

/**
 * A simple {@link Fragment} subclass.
 */
public class FavoriteFragment extends Fragment {

    private TabLayout mTabLayout;
    private ViewPager viewPager;

    private TabPagerAdapter tabPagerAdapter;

    public FavoriteFragment() {
        // Required empty public constructor
    }

    public static FavoriteFragment newInstance() {
        return new FavoriteFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favorite, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mTabLayout = view.findViewById(R.id.tabLayout);
        viewPager = view.findViewById(R.id.view_pager);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (getActivity() != null) {
            tabPagerAdapter = new TabPagerAdapter(getChildFragmentManager());
            tabPagerAdapter.addFragemnts(FavoriteMovieFragment.newInstance(), getResources().getString(R.string.movies));
            tabPagerAdapter.addFragemnts(FavoriteTvShowFragment.newInstance(), getResources().getString(R.string.tv_show));
            viewPager.setAdapter(tabPagerAdapter);
            mTabLayout.setupWithViewPager(viewPager);
        }
    }
}
