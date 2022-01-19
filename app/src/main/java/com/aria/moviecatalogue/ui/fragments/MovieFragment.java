package com.aria.moviecatalogue.ui.fragments;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.aria.moviecatalogue.R;
import com.aria.moviecatalogue.ui.adapter.MovieAdapter;
import com.aria.moviecatalogue.viewmodel.MovieViewModel;
import com.aria.moviecatalogue.viewmodel.ViewModelFactory;

/**
 * A simple {@link Fragment} subclass.
 */
public class MovieFragment extends Fragment {
    private RecyclerView rvMovies;
    private ProgressBar progressBar;

    private MovieAdapter adapter;

    public MovieFragment() {
        // Required empty public constructor
    }

    public static MovieFragment newInstance() {
        return new MovieFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_movie, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rvMovies = view.findViewById(R.id.rv_movies);
        progressBar = view.findViewById(R.id.progressBar);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (getActivity() != null) {
            MovieViewModel viewModel = obtainViewModel(getActivity());

            viewModel.setUsername("Aria");
            viewModel.init();
            viewModel.getAllMovies().observe(this, movieEntities -> {
                if (movieEntities != null) {
                    switch (movieEntities.status) {
                        case LOADING:
                            showLoading(true);
                            break;

                        case SUCCESS:
                            showLoading(false);
                            adapter.setMovies(movieEntities.data);
                            adapter.notifyDataSetChanged();
                            break;

                        case ERROR:
                            showLoading(false);
                            showError();
                            break;
                    }
                }
            });

            adapter = new MovieAdapter(getActivity());

            rvMovies.setLayoutManager(new LinearLayoutManager(getContext()));
            rvMovies.setHasFixedSize(true);
            rvMovies.setAdapter(adapter);
        }
    }

    @NonNull
    private static MovieViewModel obtainViewModel(FragmentActivity activity) {
        // Use a Factory to inject dependencies into the ViewModel
        ViewModelFactory factory = ViewModelFactory.getInstance(activity.getApplication());
        return ViewModelProviders.of(activity, factory).get(MovieViewModel.class);
    }

    private void showLoading(boolean state) {
        if (state) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.INVISIBLE);
        }
    }

    private void showError() {
        Toast.makeText(getActivity(), getResources().getString(R.string.error), Toast.LENGTH_SHORT).show();
    }
}
