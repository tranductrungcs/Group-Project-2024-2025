package com.example;

import android.content.Context;
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
    private Context context;
    private List<SmallNews> NewsList;
    private final SelectListener selectListener;

    public NewsAdapter(Context context, List<SmallNews> newsList, SelectListener selectListener) {
        this.context = context;
        this.NewsList = newsList;
        this.selectListener = selectListener;
    }

    public static class NewsViewHolder extends RecyclerView.ViewHolder {
        ImageView newsImageView;
        TextView newsTitle;

        public NewsViewHolder(@NonNull View itemView) {
            super(itemView);
            newsImageView = itemView.findViewById(R.id.ImageNews);
            newsTitle = itemView.findViewById(R.id.TitleNews);
        }
    }

    @NonNull
    @Override
    public NewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_item, parent, false);
        return new NewsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsViewHolder holder, int position) {
        SmallNews smallNews = NewsList.get(position);

        holder.newsTitle.setText(smallNews.getTitle());

        Picasso.get()
                .load(smallNews.getImageUrl())
                .placeholder(R.drawable.avoid_red)
                .error(R.drawable.tomato_logo)
                .fit()
                .into(holder.newsImageView);
    }

    @Override
    public int getItemCount() {
        return NewsList.size();
    }

    public void setNewsList(List<SmallNews> list) {
        this.NewsList = list;
        notifyDataSetChanged();
    }

}
