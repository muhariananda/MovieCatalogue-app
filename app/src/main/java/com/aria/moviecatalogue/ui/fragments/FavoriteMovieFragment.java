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
import com.aria.moviecatalogue.ui.adapter.FavoriteMovieAdapter;
import com.aria.moviecatalogue.viewmodel.FavoriteMovieViewModel;
import com.aria.moviecatalogue.viewmodel.ViewModelFactory;

/**
 * A simple {@link Fragment} subclass.
 */
public class FavoriteMovieFragment extends Fragment {
    private RecyclerView recyclerView;
    private ProgressBar progressBar;

    private FavoriteMovieAdapter adapter;

    public FavoriteMovieFragment() {
        // Required empty public constructor
    }

    public static FavoriteMovieFragment newInstance() {
        return new FavoriteMovieFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favorite_movie, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.rv_movies);
        progressBar = view.findViewById(R.id.progressBar);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (getActivity() != null) {
            adapter = new FavoriteMovieAdapter();

            FavoriteMovieViewModel viewModel = obtainViewModel(getActivity());

            viewModel.init();
            viewModel.getFavoriteMovies().observe(this, movies -> {
                switch (movies.status) {
                    case LOADING:
                        showLoading(true);
                        break;

                    case SUCCESS:
                        showLoading(false);
                        adapter.submitList(movies.data);
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
    private static FavoriteMovieViewModel obtainViewModel(FragmentActivity activity) {
        ViewModelFactory factory = ViewModelFactory.getInstance(activity.getApplication());
        return ViewModelProviders.of(activity, factory).get(FavoriteMovieViewModel.class);
    }

    private void showLoading(boolean state) {
        if (state) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.INVISIBLE);
        }
    }
}
