package com.example;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.MediaController;
import android.widget.VideoView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.OptIn;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.media3.common.MediaItem;
import androidx.media3.common.util.UnstableApi;
import androidx.media3.exoplayer.DefaultLoadControl;
import androidx.media3.exoplayer.DefaultRenderersFactory;
import androidx.media3.exoplayer.ExoPlayer;
import androidx.media3.ui.PlayerView;

public class PlayVideoActivity extends AppCompatActivity {
    private PlayerView playerView;
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

//        DefaultLoadControl loadControl = new DefaultLoadControl.Builder()
//                .setBufferDurationsMs(
//                        DefaultLoadControl.DEFAULT_MIN_BUFFER_MS,
//                        DefaultLoadControl.DEFAULT_MAX_BUFFER_MS,
//                        DefaultLoadControl.DEFAULT_BUFFER_FOR_PLAYBACK_MS,
//                        DefaultLoadControl.DEFAULT_BUFFER_FOR_PLAYBACK_AFTER_REBUFFER_MS
//                ).build();

//        DefaultRenderersFactory renderersFactory = new DefaultRenderersFactory(this)
//                .setEnableDecoderFallback(true)
//                .setExtensionRendererMode(DefaultRenderersFactory.EXTENSION_RENDERER_MODE_PREFER);

        exoPlayer = new ExoPlayer.Builder(this)
//                .setLoadControl(loadControl)
//                .setRenderersFactory(renderersFactory)
                .build();

        playerView = findViewById(R.id.tempPlayerView);
        String videoUriString = getIntent().getStringExtra("videoUri");
        Log.i("videoUri", videoUriString);
        Uri videoUri = Uri.parse(videoUriString);
        MediaItem mediaItem = MediaItem.fromUri(videoUri);
        playerView.setPlayer(exoPlayer);

        playerView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    if (exoPlayer.isPlaying()) {
                        exoPlayer.pause();
                    } else {
                        exoPlayer.play();
                    }
                }
                return false;
            }
        });

        exoPlayer.setMediaItem(mediaItem);
        exoPlayer.prepare();
        exoPlayer.play();
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY | View.SYSTEM_UI_FLAG_LAYOUT_STABLE |
                            View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_FULLSCREEN |
                            View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            );
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        exoPlayer.pause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        exoPlayer.release();
    }
}