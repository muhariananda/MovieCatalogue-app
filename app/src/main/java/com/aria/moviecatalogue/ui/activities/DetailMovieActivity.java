package com.aria.moviecatalogue.ui.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.ViewModelProviders;

import com.aria.moviecatalogue.BuildConfig;
import com.aria.moviecatalogue.R;
import com.aria.moviecatalogue.data.source.local.entity.MovieEntity;
import com.aria.moviecatalogue.utils.GlideApp;
import com.aria.moviecatalogue.viewmodel.DetailMovieViewModel;
import com.aria.moviecatalogue.viewmodel.ViewModelFactory;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

public class DetailMovieActivity extends AppCompatActivity {

    public static final String EXTRA_CONTENT = "extra_content";

    private TextView tvTitle, tvDate, tvOverview;
    private ImageView imgPoster, imgBackdrop;
    private ProgressBar progressBar;
    private ConstraintLayout parentLayout;
    private FloatingActionButton btnFab;

    private boolean state;

    private DetailMovieViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_movie);
        setToolbar();

        viewModel = obtainViewModel(this);

        initUI();
        getContent();

        btnFab.setOnClickListener(v -> {
            if (state) {
                viewModel.setFavorite();
                Snackbar.make(parentLayout, getResources().getString(R.string.remove), Snackbar.LENGTH_SHORT).show();
            } else {
                viewModel.setFavorite();
                Snackbar.make(parentLayout, getResources().getString(R.string.add), Snackbar.LENGTH_SHORT).show();
            }
        });
    }

    private void setToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
    }

    private void initUI() {
        tvTitle = findViewById(R.id.tv_detail_title);
        tvDate = findViewById(R.id.tv_detail_release);
        tvOverview = findViewById(R.id.tv_detail_overview);
        imgPoster = findViewById(R.id.image_detail_poster);
        imgBackdrop = findViewById(R.id.img_detail_backdrop);
        progressBar = findViewById(R.id.progressBar);
        btnFab = findViewById(R.id.btn_fab);
        parentLayout = findViewById(R.id.parenLayout);
    }

    private void getContent() {
        int movieId = getIntent().getIntExtra(EXTRA_CONTENT, 0);

        viewModel.setMovieId(movieId);
        viewModel.init();
        viewModel.getMovies().observe(this, movieEntityResource -> {
            if (movieEntityResource != null) {
                switch (movieEntityResource.status) {
                    case LOADING:
                        showLoading(true);
                        break;

                    case SUCCESS:
                        if (movieEntityResource.data != null) {
                            populateContent(movieEntityResource.data);
                            showLoading(false);

                            state = movieEntityResource.data.isFavorite();
                            setFavoriteState(state);
                        }
                        break;

                    case ERROR:
                        showLoading(false);
                        showError();
                        break;
                }
            }
        });
    }

    private void populateContent(MovieEntity content) {
        tvTitle.setText(content.getTitle());
        tvDate.setText(content.getReleaseDate().split("-")[0]);
        tvOverview.setText(content.getOverview());


        GlideApp.with(getApplicationContext())
                .load(BuildConfig.BASE_POSTER_URL + content.getPosterPath())
                .apply(RequestOptions.placeholderOf(R.drawable.ic_placeholder_loading).error(R.drawable.ic_paceholder_error))
                .into(imgPoster);


        GlideApp.with(getApplicationContext())
                .load(BuildConfig.BASE_BACKDROP + content.getBackdropPath())
                .apply(RequestOptions.placeholderOf(R.color.colorPrimary))
                .into(imgBackdrop);
    }

    private static DetailMovieViewModel obtainViewModel(AppCompatActivity activity) {
        // Use a Factory to inject dependencies into the ViewModel
        ViewModelFactory factory = ViewModelFactory.getInstance(activity.getApplication());
        return ViewModelProviders.of(activity, factory).get(DetailMovieViewModel.class);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void setFavoriteState(boolean state) {
        if (state) {
            btnFab.setImageResource(R.drawable.ic_favorite);
        } else {
            btnFab.setImageResource(R.drawable.ic_favorite_border);
        }
    }

    private void showLoading(boolean state) {
        if (state) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.INVISIBLE);
        }
    }

    private void showError() {
        Toast.makeText(this, getResources().getString(R.string.error), Toast.LENGTH_SHORT).show();
    }
}
