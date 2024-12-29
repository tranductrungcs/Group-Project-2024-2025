package com.example;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link VideoSamsungFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class VideoSamsungFragment extends Fragment {
    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;
    private VideoAdapter videoAdapter;
    private final List<Video> videoList = new ArrayList<>();
    private TabLayout tabLayout;

    private static final String baseUrl = "https://android-backend-tech-c52e01da23ae.herokuapp.com/";

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public VideoSamsungFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment VideoSamsungFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static VideoSamsungFragment newInstance(String param1, String param2) {
        VideoSamsungFragment fragment = new VideoSamsungFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_video_samsung, container, false);

//        String html = "<iframe width=\"360\" height=\"515\" src=\"https://www.youtube.com/embed/QPOLrbKI5oQ\" title=\"YouTube video player\" frameborder=\"0\" allow=\"accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share \" allowfullscreen></iframe>";
//
//        WebView myWebView = view.findViewById(R.id.webview);
//        WebSettings webSettings = myWebView.getSettings();
//        webSettings.setJavaScriptEnabled(true);
//
//        myWebView.loadData(html, "text/html", null);

//        myWebView.loadUrl("http://www.example.com");

//        button = view.findViewById(R.id.video);
//        button.setOnClickListener(new View.OnClickListener() {
//
//            public void onClick(View v) {
//
//                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/embed/BSYsXVFzmKA?si=Mlg5KwkmKBi6fAhH")));
//                Log.i("Video", "Video Playing....");
//
//            }
//        });

        tabLayout = requireActivity().findViewById(R.id.tab_layout_main);
        swipeRefreshLayout = view.findViewById(R.id.swipe_layout);
        recyclerView = view.findViewById(R.id.videos);
        // Setup RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        videoAdapter = new VideoAdapter(getContext(), videoList, baseUrl, this::playVideo);
        recyclerView.setAdapter(videoAdapter);

        // Setup SwipeRefreshLayout
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshData();
                swipeRefreshLayout.setRefreshing(false);
            }
        });

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                if (dy > 0) {
                    tabLayout.setVisibility(View.INVISIBLE);
                } else {
                    tabLayout.setVisibility(View.VISIBLE);
                }
                super.onScrolled(recyclerView, dx, dy);
            }
        });

        // Load videos
        fetchVideos();

        return view;
    }

    private void refreshData() {
        List<Video> newData = getListPost();
        videoAdapter.setData(newData);
        videoAdapter.notifyDataSetChanged();
    }

    private List<Video> getListPost() {
        Log.i("List Post", videoList.toString());
        if (!videoList.isEmpty()) {

        }
        return videoList;
    }

    private void fetchVideos() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        VideoAPI videoAPI = retrofit.create(VideoAPI.class);
        Call<List<Video>> call = videoAPI.getVideos("Samsung");
        call.enqueue(new Callback<List<Video>>() {
            @Override
            public void onResponse(@NonNull Call<List<Video>> call, @NonNull Response<List<Video>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    for (Video video : response.body()) {
                        if ("Samsung".equals(video.getVideoBrandType())) {
                            videoList.add(video);
                            Log.i("Add videos success", video.getVideoUniqueId());
                        }
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Video>> call, Throwable t) {
                if (getContext() != null) {
                    Toast.makeText(getContext(), "Failed to fetch videos", Toast.LENGTH_SHORT).show();
                }
                Log.i("Queue error", t.toString());
            }
        });
    }

    private void playVideo(Video video) {
        Uri videoUri = Uri.parse(baseUrl + video.getFetchableUrl());

        // Start PlayVideoActivity with the selected URI
        if (getActivity() != null) {
            Intent intent = new Intent(getActivity(), PlayVideoActivity.class);
            intent.putExtra("videoUri", videoUri.toString());
            startActivity(intent);
        }
    }
}