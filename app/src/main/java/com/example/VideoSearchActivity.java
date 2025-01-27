package com.example;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class VideoSearchActivity extends AppCompatActivity {
    private RecyclerView videoSearchResult;
    private VideoSearchAdapter videoSearchAdapter;
    private List<Video> videoSearchList = new ArrayList<>();
    private EditText searchText;
    private Button searchButton;

    private static final String baseUrl = "https://android-backend-tech-c52e01da23ae.herokuapp.com/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_video_search);
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
            finish();
        });

        // Find the close_icon ImageView
        ImageView closeIcon = findViewById(R.id.close_icon);

        // Set a click listener to finish the activity
        closeIcon.setOnClickListener(v -> {
            // Close the SearchActivity and return to the previous fragment
            finish();
        });

        videoSearchResult = findViewById(R.id.video_search_result);
        videoSearchResult.setLayoutManager(new GridLayoutManager(getBaseContext(), 2));
        videoSearchAdapter = new VideoSearchAdapter(getBaseContext(), videoSearchList, baseUrl, this::playVideo);
        videoSearchResult.setAdapter(videoSearchAdapter);
        videoSearchResult.setVisibility(View.GONE); // Ensure it's hidden initially

        // Load videos
        fetchVideos();

        searchText = findViewById(R.id.search_text);
        searchButton = findViewById(R.id.search_button);

        // Set an OnClickListener for the search button
        searchButton.setOnClickListener(v -> {
            String query = searchText.getText().toString().trim();
            filterVideos(query);
        });
    }

    private void fetchVideos() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        VideoAllAPI videoAllAPI = retrofit.create(VideoAllAPI.class);
        Call<List<Video>> call = videoAllAPI.getVideos();
        call.enqueue(new Callback<List<Video>>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onResponse(@NonNull Call<List<Video>> call, @NonNull Response<List<Video>> response) {
                if (response.isSuccessful() && response.body() != null && !response.body().isEmpty()) {
                    List<Video> videoSearchList = response.body();
                    // Update adapter with video list
                    videoSearchAdapter.setData(videoSearchList);
                    // Shuffle the video list
                    videoSearchAdapter.shuffleVideos();
                    // Update the adapter after adding video
                    videoSearchAdapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(getBaseContext(), "No videos available", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Video>> call, @NonNull Throwable t) {
                Toast.makeText(getBaseContext(), "Failed to fetch videos", Toast.LENGTH_SHORT).show();
                Log.e("API Error", Objects.requireNonNull(t.getMessage()));
            }
        });
    }

    private void playVideo(Video video) {
        ArrayList<String> videoUris = new ArrayList<>(); // List of video URIs
        ArrayList<String> videoTitles = new ArrayList<>(); // List of video titles
        ArrayList<Integer> comments = new ArrayList<>(); // List of video comments
        ArrayList<Integer> likes = new ArrayList<>(); // List of video likes
        ArrayList<Integer> bookmarks = new ArrayList<>(); // List of video saves

        for (Video v : videoSearchList) {
            videoUris.add(baseUrl + v.getFetchableUrl());
            videoTitles.add(v.getTitle());
            comments.add(v.getCommentNum());
            likes.add(v.getLikeNum());
            bookmarks.add(v.getBookmarkNum());
        }

        // Get the selected video's position
        int selectedPosition = videoSearchList.indexOf(video);

        // Start PlayVideoActivity with the video list and selected position
        Intent intent = new Intent(this, PlayVideoActivity.class);
        intent.putStringArrayListExtra("videoUris", videoUris);
        intent.putStringArrayListExtra("videoTitles", videoTitles);
        intent.putIntegerArrayListExtra("comments", comments);
        intent.putIntegerArrayListExtra("likes", likes);
        intent.putIntegerArrayListExtra("bookmarks", bookmarks);
        intent.putExtra("initialPosition", selectedPosition);
        startActivity(intent);
    }

    // Add the filterVideos method
    @SuppressLint("NotifyDataSetChanged")
    private void filterVideos(String query) {
        if (query.isEmpty()) {
            // If the search query is empty, reset the adapter with the original list
            videoSearchAdapter.setData(videoSearchList);
        } else {
            List<Video> filteredList = new ArrayList<>();
            for (Video video : videoSearchList) {
                if (video.getTitle().toLowerCase().contains(query.toLowerCase())) {
                    filteredList.add(video);
                }
            }
            videoSearchAdapter.setData(filteredList);
            videoSearchResult.setVisibility(filteredList.isEmpty() ? View.GONE : View.VISIBLE); // Show or hide based on results
        }
        videoSearchAdapter.notifyDataSetChanged();
    }
}