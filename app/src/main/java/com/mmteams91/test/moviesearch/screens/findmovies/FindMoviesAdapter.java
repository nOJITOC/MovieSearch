package com.mmteams91.test.moviesearch.screens.findmovies;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.mmteams91.test.moviesearch.R;
import com.mmteams91.test.moviesearch.data.network.dto.FindMovieDto;
import com.mmteams91.test.moviesearch.ui.OnItemClickListener;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by User New on 30.01.2018.
 */

public class FindMoviesAdapter extends RecyclerView.Adapter<FindMoviesAdapter.MovieViewHolder> {
    List<FindMovieDto> movies = new ArrayList<>();
    OnLastItemBindListener onLastItemBindListener;
    OnItemClickListener<FindMovieDto> onItemClickListener;
    String baseImageUri;

    public FindMoviesAdapter(String baseImageUri) {
        this.baseImageUri = baseImageUri;
    }

    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final MovieViewHolder holder = new MovieViewHolder(parent);
        holder.itemView.setOnClickListener(v -> {
            if (onItemClickListener == null) return;
            FindMovieDto movie = movies.get(holder.getAdapterPosition());
            onItemClickListener.onClick(holder.itemView, movie);
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(MovieViewHolder holder, int position) {
        if (isLastItem(position) && onLastItemBindListener != null)
            onLastItemBindListener.onLastItemBind();
        FindMovieDto movie = movies.get(position);
        holder.title.setText(movie.getTitle());
        Glide.with(holder.itemView.getContext())
                .load(baseImageUri + movie.getPosterPath())
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .placeholder(R.drawable.placeholder)
                .into(holder.image);
    }

    private boolean isLastItem(int position) {
        return getItemCount() != 0 && position == getItemCount() - 1;
    }

    public void clear() {
        movies = new ArrayList<>();
        notifyDataSetChanged();
    }

    public void addItems(Collection<FindMovieDto> movies) {
        int first = getItemCount();
        int size = movies.size();
        this.movies.addAll(movies);
        notifyItemRangeChanged(first, size);
    }

    public void setOnItemClickListener(OnItemClickListener<FindMovieDto> onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void setOnLastItemBindListener(OnLastItemBindListener onLastItemBindListener) {
        this.onLastItemBindListener = onLastItemBindListener;
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    public void setData(List<FindMovieDto> data) {
        this.movies = data;
        notifyDataSetChanged();
    }

    public class MovieViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView title;

        public MovieViewHolder(ViewGroup parent) {
            super(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_movie, parent, false));
            image = (ImageView) ((ViewGroup) itemView).getChildAt(0);
            title = (TextView) ((ViewGroup) itemView).getChildAt(1);
        }
    }

    interface OnLastItemBindListener {
        void onLastItemBind();
    }
}
