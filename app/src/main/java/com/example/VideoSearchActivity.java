package com.example;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
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
    private RecyclerView histories;
    private RelativeLayout trendsAndHistory;
    private VideoSearchAdapter videoSearchAdapter;
    private VideoHistoryAdapter videoHistoryAdapter;
    private List<Video> videoSearchList = new ArrayList<>();
    private List<Video> originalList = new ArrayList<>();
    private List<String> historyList = new ArrayList<>();
    private EditText searchText;
    private Button searchButton;
    private Button deleteHistoryButton;
    private SharedPreferences sharedPreferences;

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

        ImageView clearSearchButton = findViewById(R.id.clear_search);

        // Set a click listener to clear the search text
        clearSearchButton.setOnClickListener(v -> {
            // Clear the typed text to search
            clearSearch();
        });

        videoSearchResult = findViewById(R.id.video_search_result);
        videoSearchResult.setLayoutManager(new GridLayoutManager(getBaseContext(), 2));
        videoSearchAdapter = new VideoSearchAdapter(getBaseContext(), videoSearchList, baseUrl, this::playVideo);
        videoSearchResult.setAdapter(videoSearchAdapter);
        videoSearchResult.setVisibility(View.GONE); // Ensure it's hidden initially

        trendsAndHistory = findViewById(R.id.trends_and_history);
        trendsAndHistory.setVisibility(View.VISIBLE);

        searchText = findViewById(R.id.search_text);

        searchButton = findViewById(R.id.search_button);
        searchButton.setVisibility(View.VISIBLE);

        deleteHistoryButton = findViewById(R.id.delete_history);
        deleteHistoryButton.setVisibility(View.VISIBLE);

        // Set an OnClickListener for the search button
        searchButton.setOnClickListener(v -> {
            String query = searchText.getText().toString().trim();
            if (!query.isEmpty()) {
                saveToHistory(query); // Save the keyword to history
                filterVideos(query);
            }
        });

        // Load videos
        fetchVideos();

        // History part
        histories = findViewById(R.id.histories);
        histories.setLayoutManager(new LinearLayoutManager(this));
        videoHistoryAdapter = new VideoHistoryAdapter(this, historyList);
//        videoHistoryAdapter = new VideoHistoryAdapter(this, historyList, updatedHistoryList -> {
//            historyList.clear();
//            historyList.addAll(updatedHistoryList);
//            saveHistoryToPreferences(); // Update SharedPreferences
//        });
        histories.setAdapter(videoHistoryAdapter);

        // Remove all histories
        deleteHistoryButton.setOnClickListener(v -> {
            clearAllHistory();
        });

//        sharedPreferences = getSharedPreferences("search_history", MODE_PRIVATE);
//        loadHistory(); // Load the search history when rerun the app
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
                    // Save the original list
                    originalList = response.body();
                    // Update adapter with video list
                    videoSearchAdapter.setData(originalList);
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
            // Hide videoSearchResult if there are no keywords
            videoSearchResult.setVisibility(View.GONE);
        } else {
            List<Video> filteredList = new ArrayList<>();
            for (Video video : originalList) {
                if (video.getTitle().toLowerCase().contains(query.toLowerCase())) {
                    filteredList.add(video);
                }
            }
            videoSearchAdapter.setData(filteredList);
            // Show or hide based on results
            videoSearchResult.setVisibility(filteredList.isEmpty() ? View.GONE : View.VISIBLE);
            trendsAndHistory.setVisibility(filteredList.isEmpty() ? View.VISIBLE : View.GONE);
            searchButton.setVisibility(filteredList.isEmpty() ? View.VISIBLE : View.GONE);
            deleteHistoryButton.setVisibility(filteredList.isEmpty() ? View.VISIBLE : View.GONE);
        }
        videoSearchAdapter.notifyDataSetChanged();
    }

    // Method to clear the search input and reset the video list
    private void clearSearch() {
        searchText.setText(""); // Clear the search text
        videoSearchAdapter.setData(originalList); // Reset to the original list
        videoSearchResult.setVisibility(View.GONE); // Hide the search results
        trendsAndHistory.setVisibility(View.VISIBLE);
        searchButton.setVisibility(View.VISIBLE);
        deleteHistoryButton.setVisibility(View.VISIBLE);
    }

    private void saveToHistory(String query) {
        if (!historyList.contains(query)) {
            historyList.add(query);
            videoHistoryAdapter.notifyItemInserted(historyList.size() - 1);
//            saveHistoryToPreferences(); // Save history to SharedPreferences
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private void clearAllHistory() {
        historyList.clear(); // Clear all histories
        videoHistoryAdapter.notifyDataSetChanged(); // Update the adapter
//        saveHistoryToPreferences(); // Update SharedPreferences
        Toast.makeText(this, "All search histories are deleted", Toast.LENGTH_SHORT).show();
    }

//    private void saveHistoryToPreferences() {
//        SharedPreferences.Editor editor = sharedPreferences.edit();
//        Gson gson = new Gson();
//        String json = gson.toJson(historyList);
//        editor.putString("history_list", json);
//        editor.apply();
//    }
//
//    @SuppressLint("NotifyDataSetChanged")
//    private void loadHistory() {
//        Gson gson = new Gson();
//        String json = sharedPreferences.getString("history_list", null);
//        Type type = new TypeToken<List<String>>() {}.getType();
//        historyList = gson.fromJson(json, type);
//
//        if (historyList == null) {
//            historyList = new ArrayList<>();
//        }
//
//        // Update adapter after load history
//        videoHistoryAdapter.notifyDataSetChanged();
//    }
}