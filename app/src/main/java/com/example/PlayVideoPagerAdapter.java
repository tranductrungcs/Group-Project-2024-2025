package com.example;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.media3.common.MediaItem;

import androidx.media3.exoplayer.ExoPlayer;
import androidx.media3.ui.PlayerView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class PlayVideoPagerAdapter extends RecyclerView.Adapter<PlayVideoPagerAdapter.VideoViewHolder>{
    private List<String> videoUris;
    private Context context;

    public PlayVideoPagerAdapter(Context context, List<String> videoUris) {
        this.context = context;
        this.videoUris = videoUris;
    }

    @NonNull
    @Override
    public VideoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.play_video_item, parent, false);
        return new VideoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VideoViewHolder holder, int position) {
        holder.bind(videoUris.get(position));
    }

    @Override
    public int getItemCount() {
        return videoUris.size();
    }

    public class VideoViewHolder extends RecyclerView.ViewHolder {
        private PlayerView playerView;
        private ExoPlayer exoPlayer;

        public VideoViewHolder(@NonNull View itemView) {
            super(itemView);
            playerView = itemView.findViewById(R.id.tempPlayerView);
        }

        public void bind(String videoUri) {
            exoPlayer = new ExoPlayer.Builder(context).build();
            playerView.setPlayer(exoPlayer);

            MediaItem mediaItem = MediaItem.fromUri(videoUri);
            exoPlayer.setMediaItem(mediaItem);
            exoPlayer.prepare();
            exoPlayer.play();

            itemView.addOnAttachStateChangeListener(new View.OnAttachStateChangeListener() {
                @Override
                public void onViewAttachedToWindow(View v) {
                    if (exoPlayer != null) {
                        exoPlayer.play();
                    }
                }

                @Override
                public void onViewDetachedFromWindow(View v) {
                    if (exoPlayer != null) {
                        exoPlayer.pause();
                        exoPlayer.release();
                    }
                }
            });
        }
    }
}
