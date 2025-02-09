package com.example.savepackage;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.R;
import com.example.videopackage.PlayVideoActivity;
import com.example.videopackage.Video;
import com.example.videopackage.VideoAdapter;
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
 * Use the {@link SaveVideoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SaveVideoFragment  extends Fragment {
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

    public SaveVideoFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment VideoAllFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SaveVideoFragment newInstance(String param1, String param2) {
        SaveVideoFragment fragment = new SaveVideoFragment();
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_save_video, container, false);

        tabLayout = requireActivity().findViewById(R.id.tab_layout_main);
        swipeRefreshLayout = view.findViewById(R.id.swipe_layout);
        recyclerView = view.findViewById(R.id.videos);

        // Setup RecyclerView with GridLayoutManager for 2 columns
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
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

        SaveAPI saveAPI = retrofit.create(SaveAPI.class);
        Call<List<Video>> call = saveAPI.getVideos("5");
        call.enqueue(new Callback<List<Video>>() {
            @Override
            public void onResponse(@NonNull Call<List<Video>> call, @NonNull Response<List<Video>> response) {
                if (response.isSuccessful() && response.body() != null && !response.body().isEmpty()) {
                    videoList.clear();
                    videoList.addAll(response.body());
                    videoAdapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(getContext(), "No videos available", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Video>> call, Throwable t) {
                Toast.makeText(getContext(), "Failed to fetch videos", Toast.LENGTH_SHORT).show();
                Log.e("API Error", t.getMessage());
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