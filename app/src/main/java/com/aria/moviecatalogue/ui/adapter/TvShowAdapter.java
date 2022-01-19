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
import com.aria.moviecatalogue.data.source.local.entity.TvShowEntity;
import com.aria.moviecatalogue.ui.activities.DetailTvShowActivity;
import com.aria.moviecatalogue.utils.GlideApp;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;
import java.util.List;

public class TvShowAdapter extends RecyclerView.Adapter<TvShowAdapter.TvShowViewHolder> {
    private final Activity activity;
    private List<TvShowEntity> tvShows = new ArrayList<>();

    public TvShowAdapter(Activity activity) {
        this.activity = activity;
    }

    public void setTvShows(List<TvShowEntity> tvShows) {
        if (tvShows == null) return;
        this.tvShows.clear();
        this.tvShows.addAll(tvShows);
    }

    @NonNull
    @Override
    public TvShowAdapter.TvShowViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list, parent, false);
        return new TvShowViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TvShowAdapter.TvShowViewHolder holder, int position) {
        TvShowEntity tvShow = tvShows.get(position);

        holder.textTitle.setText(tvShow.getName());
        holder.textDate.setText(tvShow.getFirstAirDate().split("-")[0]);
        holder.textOverview.setText(tvShow.getOverview());

        GlideApp.with(holder.itemView.getContext())
                .load(BuildConfig.BASE_POSTER_URL + tvShow.getPosterPath())
                .apply(RequestOptions.placeholderOf(R.drawable.ic_placeholder_loading).error(R.drawable.ic_paceholder_error))
                .into(holder.imgPoster);

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(activity, DetailTvShowActivity.class);
            intent.putExtra(DetailTvShowActivity.EXTRA_CONTENT, tvShow.getId());
            activity.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return tvShows.size();
    }

    class TvShowViewHolder extends RecyclerView.ViewHolder {
        final TextView textTitle, textDate, textOverview;
        final ImageView imgPoster;

        TvShowViewHolder(@NonNull View itemView) {
            super(itemView);
            textTitle = itemView.findViewById(R.id.text_item_title);
            textDate = itemView.findViewById(R.id.text_item_date);
            textOverview = itemView.findViewById(R.id.text_item_overview);
            imgPoster = itemView.findViewById(R.id.img_item_poster);
        }
    }
}
