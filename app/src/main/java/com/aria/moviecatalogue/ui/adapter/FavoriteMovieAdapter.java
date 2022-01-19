package com.aria.moviecatalogue.ui.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.aria.moviecatalogue.BuildConfig;
import com.aria.moviecatalogue.R;
import com.aria.moviecatalogue.data.source.local.entity.MovieEntity;
import com.aria.moviecatalogue.ui.activities.DetailMovieActivity;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

public class FavoriteMovieAdapter extends PagedListAdapter<MovieEntity, FavoriteMovieAdapter.FavoriteMovieViewHolder> {

    public FavoriteMovieAdapter() {
        super(DIFF_CALLBACK);
    }

    @NonNull
    @Override
    public FavoriteMovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list, parent, false);
        return new FavoriteMovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavoriteMovieViewHolder holder, int position) {
        final MovieEntity movie = getItem(position);
        if (movie != null) {
            holder.tvTitle.setText(movie.getTitle());
            holder.tvDate.setText(movie.getReleaseDate().split("-")[0]);
            holder.tvOverview.setText(movie.getOverview());

            Glide.with(holder.itemView.getContext()).
                    load(BuildConfig.BASE_POSTER_URL + movie.getPosterPath()).
                    apply(RequestOptions.placeholderOf(R.drawable.ic_placeholder_loading).error(R.drawable.ic_broken_image)).
                    into(holder.imgPoster);

            holder.itemView.setOnClickListener( v -> {
                Context context = holder.itemView.getContext();
                Intent intent = new Intent(context, DetailMovieActivity.class);
                intent.putExtra(DetailMovieActivity.EXTRA_CONTENT, movie.getId());
                context.startActivity(intent);
            });
        }
    }

    private static DiffUtil.ItemCallback<MovieEntity> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<MovieEntity>() {
                @Override
                public boolean areItemsTheSame(@NonNull MovieEntity oldItem, @NonNull MovieEntity newItem) {
                    return oldItem.getTitle().equals(newItem.getTitle());
                }

                @SuppressLint("DiffUtilEquals")
                @Override
                public boolean areContentsTheSame(@NonNull MovieEntity oldItem, @NonNull MovieEntity newItem) {
                    return oldItem.equals(newItem);
                }
            };

    class FavoriteMovieViewHolder extends RecyclerView.ViewHolder {
        final TextView tvTitle, tvDate, tvOverview;
        final ImageView imgPoster;

        public FavoriteMovieViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.text_item_title);
            tvDate = itemView.findViewById(R.id.text_item_date);
            tvOverview = itemView.findViewById(R.id.text_item_overview);
            imgPoster = itemView.findViewById(R.id.img_item_poster);
        }
    }
}
