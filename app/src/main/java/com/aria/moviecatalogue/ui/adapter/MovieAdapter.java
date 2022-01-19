package com.aria.moviecatalogue.ui.adapter;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.aria.moviecatalogue.BuildConfig;
import com.aria.moviecatalogue.R;
import com.aria.moviecatalogue.data.source.local.entity.MovieEntity;
import com.aria.moviecatalogue.ui.activities.DetailMovieActivity;
import com.aria.moviecatalogue.utils.GlideApp;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;
import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {
    private final Activity activity;
    private List<MovieEntity> movies = new ArrayList<>();

    public MovieAdapter(Activity activity) {
        this.activity = activity;
    }

    public void setMovies(List<MovieEntity> movies) {
        if (movies == null) return;
        this.movies.clear();
        this.movies.addAll(movies);
    }

    @NonNull
    @Override
    public MovieAdapter.MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list, parent, false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieAdapter.MovieViewHolder holder, int position) {
        MovieEntity movie = movies.get(position);

        holder.textTitle.setText(movie.getTitle());
        holder.textDate.setText(movie.getReleaseDate().split("-")[0]);
        holder.textOverview.setText(movie.getOverview());

        GlideApp.with(holder.itemView.getContext())
                .load(BuildConfig.BASE_POSTER_URL + movie.getPosterPath())
                .apply(RequestOptions.placeholderOf(R.drawable.ic_placeholder_loading).error(R.drawable.ic_paceholder_error))
                .into(holder.imgPoster);

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(activity, DetailMovieActivity.class);
            intent.putExtra(DetailMovieActivity.EXTRA_CONTENT, movie.getId());
            activity.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    public class MovieViewHolder extends RecyclerView.ViewHolder {
        final TextView textTitle, textDate, textOverview;
        final ImageView imgPoster;

        public MovieViewHolder(@NonNull View itemView) {
            super(itemView);
            textTitle = itemView.findViewById(R.id.text_item_title);
            textDate = itemView.findViewById(R.id.text_item_date);
            textOverview = itemView.findViewById(R.id.text_item_overview);
            imgPoster = itemView.findViewById(R.id.img_item_poster);
        }
    }
}
