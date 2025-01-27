package com.example;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class VideoSearchAdapter extends RecyclerView.Adapter<VideoSearchAdapter.VideoViewHolder> {
    private List<Video> videoSearchList;

    public VideoSearchAdapter(List<Video> videoSearchList) {
        this.videoSearchList = videoSearchList;
    }

    @NonNull
    @Override
    public VideoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.video_item, parent, false);
        return new VideoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VideoViewHolder holder, int position) {
        Video video = videoSearchList.get(position);
        holder.titleTextView.setText(video.getTitle());
        holder.authorTextView.setText(video.getAuthor());

        // Sử dụng Glide để tải hình ảnh thumbnail
        Glide.with(holder.itemView.getContext())
                .load(video.getThumbnailImageFetchableUrl())
                .into(holder.thumbnailImageView);
    }

    @Override
    public int getItemCount() {
        return videoSearchList.size();
    }

    static class VideoViewHolder extends RecyclerView.ViewHolder {
        TextView titleTextView;
        TextView authorTextView;
        ImageView thumbnailImageView;

        public VideoViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.videoTitle);
            thumbnailImageView = itemView.findViewById(R.id.videoThumbnail);
        }
    }
}
