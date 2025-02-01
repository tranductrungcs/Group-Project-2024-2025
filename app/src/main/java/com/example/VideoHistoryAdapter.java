package com.example;

import android.content.Context;
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
    private Context context;
//    private OnHistoryItemRemovedListener listener;

    public VideoHistoryAdapter(Context context, List<String> historyList) {
        this.context = context;
        this.historyList = historyList;
    }

//    public VideoHistoryAdapter(Context context, List<String> historyList, OnHistoryItemRemovedListener listener) {
//        this.context = context;
//        this.historyList = historyList;
//        this.listener = listener;
//    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.video_history_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String historyItem = historyList.get(position);
        holder.historyText.setText(historyItem);

        // Process the delete history event
        holder.removeHistory.setOnClickListener(v -> {
            historyList.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, historyList.size());
//            listener.onHistoryItemRemoved(historyList); // Callback for notification
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

//    public interface OnHistoryItemRemovedListener {
//        void onHistoryItemRemoved(List<String> updatedHistoryList);
//    }
}

