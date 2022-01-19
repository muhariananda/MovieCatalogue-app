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
import com.aria.moviecatalogue.data.source.local.entity.TvShowEntity;
import com.aria.moviecatalogue.ui.activities.DetailTvShowActivity;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

public class FavoriteTvAdapter extends PagedListAdapter<TvShowEntity, FavoriteTvAdapter.FavoriteTvViewHolder> {

    public FavoriteTvAdapter() {
        super(DIFF_CALLBACK);
    }

    @NonNull
    @Override
    public FavoriteTvAdapter.FavoriteTvViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list, parent, false);
        return new FavoriteTvViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavoriteTvAdapter.FavoriteTvViewHolder holder, int position) {
        final TvShowEntity tvShow = getItem(position);
        if (tvShow != null) {
            holder.tvTitle.setText(tvShow.getName());
            holder.tvDate.setText(tvShow.getFirstAirDate().split("-")[0]);
            holder.tvOverview.setText(tvShow.getOverview());

            Glide.with(holder.itemView.getContext())
                    .load(BuildConfig.BASE_POSTER_URL + tvShow.getPosterPath())
                    .apply(RequestOptions.placeholderOf(R.drawable.ic_placeholder_loading).error(R.drawable.ic_broken_image))
                    .into(holder.imgPoster);

            holder.itemView.setOnClickListener(v -> {
                Context context = holder.itemView.getContext();
                Intent intent = new Intent(context, DetailTvShowActivity.class);
                intent.putExtra(DetailTvShowActivity.EXTRA_CONTENT, tvShow.getId());
                context.startActivity(intent);
            });
        }
    }

    private static DiffUtil.ItemCallback<TvShowEntity> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<TvShowEntity>() {
                @Override
                public boolean areItemsTheSame(@NonNull TvShowEntity oldItem, @NonNull TvShowEntity newItem) {
                    return oldItem.getName().equals(newItem.getName());
                }

                @SuppressLint("DiffUtilEquals")
                @Override
                public boolean areContentsTheSame(@NonNull TvShowEntity oldItem, @NonNull TvShowEntity newItem) {
                    return oldItem.equals(newItem);
                }
            };

    public class FavoriteTvViewHolder extends RecyclerView.ViewHolder {
        final TextView tvTitle, tvDate, tvOverview;
        final ImageView imgPoster;

        public FavoriteTvViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.text_item_title);
            tvDate = itemView.findViewById(R.id.text_item_date);
            tvOverview = itemView.findViewById(R.id.text_item_overview);
            imgPoster = itemView.findViewById(R.id.img_item_poster);
        }
    }
}
