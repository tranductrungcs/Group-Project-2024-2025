package com.example;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsViewHolder> {
    private List<SmallNews> newsList;

    public NewsAdapter(List<SmallNews> newsList) {
        this.newsList = newsList;
    }

    public static class NewsViewHolder extends RecyclerView.ViewHolder {
        ImageView newsImageView;
        TextView newsShortTitle, newsDescription, newsDate;

        public NewsViewHolder(@NonNull View itemView) {
            super(itemView);
            newsImageView = itemView.findViewById(R.id.mainImage);
            newsShortTitle = itemView.findViewById(R.id.newsShortTitle);
            newsDescription = itemView.findViewById(R.id.newsDescription);
            newsDate = itemView.findViewById(R.id.newsDate);
        }
    }

    @NonNull
    @Override
    public NewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_small_news, parent, false);
        return new NewsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsViewHolder holder, int position) {
        SmallNews smallNews = newsList.get(position);

        holder.newsShortTitle.setText(smallNews.getTitle());
        holder.newsDescription.setText(smallNews.getDescription());
        holder.newsDate.setText(smallNews.getDate());

        Picasso.get()
                .load(smallNews.getImageUrl())
                .placeholder(R.drawable.baseline_photo_24)
                .error(R.drawable.baseline_photo_24)
                .into(holder.newsImageView);
    }

    @Override
    public int getItemCount() {
        return newsList.size();
    }

    public void setNewsList(List<SmallNews> list) {
        this.newsList = list;
        notifyDataSetChanged();
    }
}
