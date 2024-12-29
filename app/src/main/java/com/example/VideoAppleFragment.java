package com.example;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.media3.common.MediaItem;
import androidx.media3.exoplayer.ExoPlayer;
import androidx.media3.exoplayer.util.EventLogger;
import androidx.media3.ui.PlayerView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.google.android.material.tabs.TabLayout;

import android.widget.Toast;

import android.os.Handler;
import android.os.Looper;
import android.widget.VideoView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link VideoAppleFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class VideoAppleFragment extends Fragment {
    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;
    private VideoAdapter videoAdapter;
    private final List<Video> videoList = new ArrayList<>();
    private LinearLayout videoContainer;
    private PlayerView playerView;
    private ExoPlayer exoPlayer;
    private TabLayout tabLayout;

    private static final String baseUrl = "https://android-backend-tech-c52e01da23ae.herokuapp.com/";
//
//    private final String[] videoUris = new String[2];
//    private final ImageButton[] thumbnails = new ImageButton[2];

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public VideoAppleFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment VideoAppleFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static VideoAppleFragment newInstance(String param1, String param2) {
        VideoAppleFragment fragment = new VideoAppleFragment();
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

//        // Initialize video items list
//        if (getActivity() != null) {
//            videoUris[0] = "android.resource://" + getActivity().getPackageName() + "/" + R.raw.apple_watch_series10;
//            videoUris[1] = "android.resource://" + getActivity().getPackageName() + "/" + R.raw.airpods4;
//        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_video_apple, container, false);

//        thumbnails[0] = view.findViewById(R.id.apple_watch_series10);
//        thumbnails[1] = view.findViewById(R.id.airpods4);

//        for (int i = 0; i < thumbnails.length; i++) {
//            final int index = i;
//            thumbnails[i].setOnClickListener(v -> playVideo(index));
//        }
//
        tabLayout = requireActivity().findViewById(R.id.tab_layout_main);
        swipeRefreshLayout = view.findViewById(R.id.swipe_layout);
        recyclerView = view.findViewById(R.id.videos);
//        videoContainer = view.findViewById(R.id.videoContainer);
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

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

//        playerView = view.findViewById(R.id.tempPlayerView);
//
//        exoPlayer = new ExoPlayer.Builder(requireContext()).build();
//        playerView.setPlayer(exoPlayer);
//
//        MediaItem mediaItem = MediaItem.fromUri(baseUrl + "fetch/1qMi8eu5aMjLuOwnPIH_n-DjRL19oknGi");
//        exoPlayer.setMediaItem(mediaItem);
//        exoPlayer.prepare();
//        exoPlayer.play();
    }

//    @Override
//    public void onPause() {
//        super.onPause();
//        exoPlayer.pause();
//    }
//
//    @Override
//    public void onDestroyView() {
//        super.onDestroyView();
//        exoPlayer.release();
//    }

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
        Call<List<Video>> call = videoAPI.getVideos("Apple");
        call.enqueue(new Callback<List<Video>>() {
            @Override
            public void onResponse(@NonNull Call<List<Video>> call, @NonNull Response<List<Video>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    for (Video video : response.body()) {
                        if ("Apple".equals(video.getVideoBrandType())) {
                            videoList.add(video);
                            Log.i("Add videos success", video.getVideoUniqueId());
                        }
                    }
                    // Cập nhật giao diện với danh sách video
//                    updateUI();
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

//    private void updateUI() {
////        // Kiểm tra xem có video nào không
////        if (appleVideos.isEmpty()) {
////            Toast.makeText(getContext(), "No Apple videos found", Toast.LENGTH_SHORT).show();
////            return;
////        }
////
////        // Giả sử bạn có 2 video và đã định nghĩa videoUris và thumbnails
////        for (int i = 0; i < Math.min(appleVideos.size(), thumbnails.length); i++) {
////            Video video = appleVideos.get(i);
////            videoUris[i] = video.getFetchableUrl(); // Lưu đường dẫn video
////
////            // Cập nhật thumbnail (nếu có) - ở đây giả sử bạn có một URL thumbnail
////            // Nếu không có thumbnail, bạn có thể bỏ qua bước này hoặc sử dụng một hình ảnh mặc định
//////            if (video.getThumbnailImageFetchableUrl() != null) {
//////                // Tải hình ảnh thumbnail từ URL
//////                // Bạn có thể sử dụng thư viện như Glide hoặc Picasso để tải hình ảnh
//////                // Glide.with(this).load(video.getThumbnailImageFetchableUrl()).into(thumbnails[i]);
//////            } else {
//////                // Nếu không có thumbnail, có thể thiết lập một hình ảnh mặc định
//////                thumbnails[i].setImageResource(R.drawable.default_thumbnail); // Thay đổi tên hình ảnh mặc định nếu cần
//////            }
////
////            // Thiết lập tiêu đề hoặc mô tả cho video (nếu cần)
////            thumbnails[i].setContentDescription(video.getVideoBrandType() + ": " + video.getTitle());
////        }
//        if (videoContainer.getChildCount() != 0) {
//            videoContainer.removeAllViews();
//        }
//
//        for (Video video : appleVideos) {
//            // Inflate layout cho video
//            View videoView = LayoutInflater.from(getContext()).inflate(R.layout.video_item, videoContainer, false);
//
//            // Lấy các thành phần từ layout
//            ImageView videoThumbnail = videoView.findViewById(R.id.videoThumbnail);
//            TextView videoTitle = videoView.findViewById(R.id.videoTitle);
//
//            videoThumbnail.setOnClickListener(listener -> playVideo(video));
//            videoTitle.setOnClickListener(listener -> playVideo(video));
//
//            // Thiết lập dữ liệu
//            Glide.with(this).load(baseUrl + video.getThumbnailImageFetchableUrl()).into(videoThumbnail); // Sử dụng Glide để tải thumbnail
//            videoTitle.setText(video.getTitle());
//
//            // Thêm video vào container
//            videoContainer.addView(videoView);
//
//            Log.i("Add video success", String.format("Add video %s successfully", video.getVideoUniqueId()));
//        }
//    }

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