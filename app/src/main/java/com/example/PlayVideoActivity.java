package com.example;

import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.annotation.OptIn;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.media3.common.MediaItem;
import androidx.media3.common.Player;
import androidx.media3.common.util.UnstableApi;
import androidx.media3.exoplayer.ExoPlayer;
import androidx.viewpager2.widget.ViewPager2;

import java.util.List;

public class PlayVideoActivity extends AppCompatActivity {
    private ViewPager2 viewPager;
    private PlayVideoPagerAdapter adapter;
    private List<String> videoUris;
    private List<String> videoTitles;
    private List<String> comments;
    private List<String> likes;
    private List<String> bookmarks;
    private ExoPlayer exoPlayer;

    @OptIn(markerClass = UnstableApi.class)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_play_video);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Find the close_icon ImageView
        ImageView backButton = findViewById(R.id.back_button);

        // Set a click listener to finish the activity
        backButton.setOnClickListener(v -> {
            // Close the SearchActivity and return to the previous fragment
            onBackPressed();
        });

        exoPlayer = new ExoPlayer.Builder(this).build();

        exoPlayer.setRepeatMode(ExoPlayer.REPEAT_MODE_OFF);
        exoPlayer.setShuffleModeEnabled(false);
        exoPlayer.addListener(new Player.Listener() {
            @Override
            public void onMediaItemTransition(@Nullable MediaItem mediaItem, int reason) {
                // Prevent auto-play of the next item until user swipes
                if (reason == Player.MEDIA_ITEM_TRANSITION_REASON_AUTO) {
                    exoPlayer.pause();
                }
            }
        });

        // Retrieve video list from intent
        videoUris = getIntent().getStringArrayListExtra("videoUris");

        // Retrieve video list from intent
        videoTitles = getIntent().getStringArrayListExtra("videoTitles");

        // Retrieve video comments list from intent
        comments = getIntent().getStringArrayListExtra("comments");

        // Retrieve video likes list from intent
        likes = getIntent().getStringArrayListExtra("likes");

        // Retrieve video saves list from intent
        bookmarks = getIntent().getStringArrayListExtra("bookmarks");

        int initialPosition = getIntent().getIntExtra("initialPosition", 0);

        viewPager = findViewById(R.id.viewPager);

        // Set up adapter
        adapter = new PlayVideoPagerAdapter(this, videoUris, videoTitles, comments, likes, bookmarks);
        viewPager.setAdapter(adapter);

        // Set initial video position
        viewPager.setCurrentItem(initialPosition, false);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (exoPlayer != null) {
            exoPlayer.pause();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (exoPlayer != null) {
            exoPlayer.release();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (exoPlayer != null) {
            exoPlayer.release();
        }
    }
}