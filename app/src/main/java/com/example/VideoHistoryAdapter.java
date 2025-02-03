package com.example;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class VideoHistoryAdapter extends RecyclerView.Adapter<VideoHistoryAdapter.ViewHolder> {
    private List<String> historyList;
    private final VideoSearchActivity videoSearchActivity;

    public VideoHistoryAdapter(List<String> historyList, VideoSearchActivity videoSearchActivity) {
        this.historyList = historyList;
        this.videoSearchActivity = videoSearchActivity;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void updateHistory(List<String> newHistory) {
        this.historyList = newHistory;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.video_history_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String historyItem = historyList.get(position);
        holder.historyText.setText(historyItem);

        // Process the delete history event
        holder.removeHistory.setOnClickListener(v -> {
            videoSearchActivity.removeSearchQuery(historyItem);
        });
    }

    @Override
    public int getItemCount() {
        return historyList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView historyText;
        ImageView removeHistory;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            historyText = itemView.findViewById(R.id.history_text);
            removeHistory = itemView.findViewById(R.id.remove_history);
        }
    }
}

