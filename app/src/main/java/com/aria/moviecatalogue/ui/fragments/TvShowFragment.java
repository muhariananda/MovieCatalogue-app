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
import com.aria.moviecatalogue.ui.adapter.TvShowAdapter;
import com.aria.moviecatalogue.viewmodel.TvShowViewModel;
import com.aria.moviecatalogue.viewmodel.ViewModelFactory;

/**
 * A simple {@link Fragment} subclass.
 */
public class TvShowFragment extends Fragment {
    private RecyclerView rvTvShow;
    private ProgressBar progressBar;

    private TvShowAdapter adapter;

    public TvShowFragment() {
        // Required empty public constructor
    }

    public static TvShowFragment newInstance() {
        return new TvShowFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tv_show, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rvTvShow = view.findViewById(R.id.rv_tvShow);
        progressBar = view.findViewById(R.id.progressBar);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (getActivity() != null) {
            TvShowViewModel viewModel = obtainViewModel(getActivity());

            viewModel.setUsername("Aria");
            viewModel.init();
            viewModel.getTvShows().observe(this, tvShowEntities -> {
                if (tvShowEntities != null) {
                    switch (tvShowEntities.status) {
                        case LOADING:
                            showLoading(true);
                            break;

                        case SUCCESS:
                            showLoading(false);
                            adapter.setTvShows(tvShowEntities.data);
                            adapter.notifyDataSetChanged();
                            break;

                        case ERROR:
                            showLoading(false);
                            showError();
                            break;
                    }
                }
            });

            adapter = new TvShowAdapter(getActivity());

            rvTvShow.setLayoutManager(new LinearLayoutManager(getContext()));
            rvTvShow.setHasFixedSize(true);
            rvTvShow.setAdapter(adapter);
        }
    }

    private static TvShowViewModel obtainViewModel(FragmentActivity activity) {
        // Use a Factory to inject dependencies into the ViewModel
        ViewModelFactory factory = ViewModelFactory.getInstance(activity.getApplication());
        return ViewModelProviders.of(activity, factory).get(TvShowViewModel.class);
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
