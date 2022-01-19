package com.aria.moviecatalogue.ui.fragments;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.aria.moviecatalogue.R;
import com.aria.moviecatalogue.ui.adapter.FavoriteTvAdapter;
import com.aria.moviecatalogue.viewmodel.FavoriteTvShowViewModel;
import com.aria.moviecatalogue.viewmodel.ViewModelFactory;

/**
 * A simple {@link Fragment} subclass.
 */
public class FavoriteTvShowFragment extends Fragment {
    private RecyclerView recyclerView;
    private ProgressBar progressBar;

    private FavoriteTvAdapter adapter;

    public FavoriteTvShowFragment() {
        // Required empty public constructor
    }

    public static FavoriteTvShowFragment newInstance() {
        return new FavoriteTvShowFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favorite_tv_show, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.rv_tvShow);
        progressBar = view.findViewById(R.id.progressBar);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (getActivity() != null) {
            adapter = new FavoriteTvAdapter();

            FavoriteTvShowViewModel viewModel = obtainViewModel(getActivity());

            viewModel.init();
            viewModel.getFavoriteTvShows().observe(this, tvShows -> {
                switch (tvShows.status) {
                    case LOADING:
                        showLoading(true);
                        break;

                    case SUCCESS:
                        showLoading(false);
                        adapter.submitList(tvShows.data);
                        adapter.notifyDataSetChanged();

                    case ERROR:
                        showLoading(false);
                        break;
                }
            });

            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            recyclerView.setHasFixedSize(true);
            recyclerView.setAdapter(adapter);
        }
    }

    @NonNull
    private static FavoriteTvShowViewModel obtainViewModel(FragmentActivity activity) {
        ViewModelFactory factory = ViewModelFactory.getInstance(activity.getApplication());
        return ViewModelProviders.of(activity, factory).get(FavoriteTvShowViewModel.class);
    }

    private void showLoading(boolean state) {
        if (state) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.INVISIBLE);
        }
    }
}
