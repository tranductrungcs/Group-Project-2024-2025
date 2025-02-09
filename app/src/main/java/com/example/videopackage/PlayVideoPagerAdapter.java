package com.example.videopackage;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.media3.common.MediaItem;

import androidx.media3.exoplayer.ExoPlayer;
import androidx.media3.ui.PlayerView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.R;

import java.util.List;

public class PlayVideoPagerAdapter extends RecyclerView.Adapter<PlayVideoPagerAdapter.VideoViewHolder>{
    private final List<String> videoUris;
    private final List<String> videoTitles;
    private final Context context;
    private final List<Integer> comments;
    private final List<Integer> likes;
    private final List<Integer> bookmarks;

    public PlayVideoPagerAdapter(Context context, List<String> videoUris, List<String> videoTitles, List<Integer> comments, List<Integer> likes, List<Integer> bookmarks) {
        this.context = context;
        this.videoUris = videoUris;
        this.videoTitles = videoTitles;
        this.comments = comments;
        this.likes = likes;
        this.bookmarks = bookmarks;
    }

    @NonNull
    @Override
    public VideoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.play_video_item, parent, false);
        return new VideoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VideoViewHolder holder, int position) {
        holder.bindVideo(videoUris.get(position));
        holder.bindTitle(videoTitles.get(position));
        holder.bindNumComments(comments.get(position));
        holder.bindNumLikes(likes.get(position));
        holder.bindNumSaves(bookmarks.get(position));
    }

    @Override
    public int getItemCount() {
        return videoUris.size();
    }

    public class VideoViewHolder extends RecyclerView.ViewHolder {
        private final PlayerView playerView;
        private ExoPlayer exoPlayer;
        private final TextView videoTitle;
        private final TextView comments;
        private final TextView likes;
        private final TextView bookmarks;

        @SuppressLint("CutPasteId")
        public VideoViewHolder(@NonNull View itemView) {
            super(itemView);
            playerView = itemView.findViewById(R.id.tempPlayerView);
            videoTitle = itemView.findViewById(R.id.video_title);
            comments = itemView.findViewById(R.id.number_of_comments);
            likes = itemView.findViewById(R.id.number_of_likes);
            bookmarks = itemView.findViewById(R.id.number_of_saves);
        }

        public void bindVideo(String videoUri) {
            exoPlayer = new ExoPlayer.Builder(context).build();
            playerView.setPlayer(exoPlayer);

            MediaItem mediaItem = MediaItem.fromUri(videoUri);
            exoPlayer.setMediaItem(mediaItem);
            exoPlayer.prepare();
            exoPlayer.play();

            itemView.addOnAttachStateChangeListener(new View.OnAttachStateChangeListener() {
                @Override
                public void onViewAttachedToWindow(@NonNull View v) {
                    if (exoPlayer != null) {
                        exoPlayer.play();
                    }
                }

                @Override
                public void onViewDetachedFromWindow(@NonNull View v) {
                    if (exoPlayer != null) {
                        exoPlayer.pause();
                        exoPlayer.release();
                    }
                }
            });
        }

        public void bindTitle(String title) {
            // Update the video title
            videoTitle.setText(title); // Set title to TextView
        }

        public void bindNumComments(int comment) {
            // Update the number of video comments
            comments.setText(String.valueOf(comment)); // Set to TextView
        }

        public void bindNumLikes(int like) {
            // Update the number of video likes
            likes.setText(String.valueOf(like)); // Set to TextView
        }

        public void bindNumSaves(int bookmark) {
            // Update the number of video saves
            bookmarks.setText(String.valueOf(bookmark)); // Set to TextView
        }
    }
}
