package com.example;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.Configuration;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.VideoView;

import com.google.android.material.search.SearchView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link VideoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class VideoFragment extends Fragment {
    private int currentVideoIndex = 0;
    private final String[] videoUris = new String[16];
    private final ImageButton[] thumbnails = new ImageButton[16];
//    private SearchView searchView;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public VideoFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment VideoFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static VideoFragment newInstance(String param1, String param2) {
        VideoFragment fragment = new VideoFragment();
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
        if (getActivity() != null) {
            videoUris[0] = "android.resource://" + getActivity().getPackageName() + "/" + R.raw.apple_watch_series10;
            videoUris[1] = "android.resource://" + getActivity().getPackageName() + "/" + R.raw.airpods4;
            videoUris[2] = "android.resource://" + getActivity().getPackageName() + "/" + R.raw.ipad_pro;
            videoUris[3] = "android.resource://" + getActivity().getPackageName() + "/" + R.raw.iphone16_pro;
            videoUris[4] = "android.resource://" + getActivity().getPackageName() + "/" + R.raw.galaxy_note9;
            videoUris[5] = "android.resource://" + getActivity().getPackageName() + "/" + R.raw.galaxy_tab_s10_series;
            videoUris[6] = "android.resource://" + getActivity().getPackageName() + "/" + R.raw.galaxys_24_fe;
            videoUris[7] = "android.resource://" + getActivity().getPackageName() + "/" + R.raw.samsung_health;
            videoUris[8] = "android.resource://" + getActivity().getPackageName() + "/" + R.raw.huawei_matebook_x_pro;
            videoUris[9] = "android.resource://" + getActivity().getPackageName() + "/" + R.raw.huawei_matepad_11_5s;
            videoUris[10] = "android.resource://" + getActivity().getPackageName() + "/" + R.raw.huawei_p60_series;
            videoUris[11] = "android.resource://" + getActivity().getPackageName() + "/" + R.raw.huawei_watch_3_3_pro;
            videoUris[12] = "android.resource://" + getActivity().getPackageName() + "/" + R.raw.xiaomi_12t_pro;
            videoUris[13] = "android.resource://" + getActivity().getPackageName() + "/" + R.raw.xiaomi_14t_series;
            videoUris[14] = "android.resource://" + getActivity().getPackageName() + "/" + R.raw.xiaomi_mi_drone;
            videoUris[15] = "android.resource://" + getActivity().getPackageName() + "/" + R.raw.xiaomi_smart_factory;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_video, container, false);

        thumbnails[0] = view.findViewById(R.id.apple_watch_series10);
        thumbnails[1] = view.findViewById(R.id.airpods4);
        thumbnails[2] = view.findViewById(R.id.ipad_pro);
        thumbnails[3] = view.findViewById(R.id.iphone16_pro);
        thumbnails[4] = view.findViewById(R.id.galaxy_note9);
        thumbnails[5] = view.findViewById(R.id.galaxy_tab_s10_series);
        thumbnails[6] = view.findViewById(R.id.galaxy_s24_fe);
        thumbnails[7] = view.findViewById(R.id.samsung_health);
        thumbnails[8] = view.findViewById(R.id.huawei_matebook_x_pro);
        thumbnails[9] = view.findViewById(R.id.huawei_matepad_11_5s);
        thumbnails[10] = view.findViewById(R.id.huawei_p60_series);
        thumbnails[11] = view.findViewById(R.id.huawei_watch_3_3_pro);
        thumbnails[12] = view.findViewById(R.id.xiaomi_12t_pro);
        thumbnails[13] = view.findViewById(R.id.xiaomi_14t_series);
        thumbnails[14] = view.findViewById(R.id.xiaomi_mi_drone);
        thumbnails[15] = view.findViewById(R.id.xiaomi_smart_factory);

        for (int i = 0; i < thumbnails.length; i++) {
            final int index = i;
            thumbnails[i].setOnClickListener(v -> playVideo(index));
        }

//        EditText searchBar = view.findViewById(R.id.search_bar);
//        ImageView searchIcon = view.findViewById(R.id.search_icon);
//
//        searchIcon.setOnClickListener(v -> {
//            String query = searchBar.getText().toString();
//            performSearch(query);
//        });

        return view;
    }

//    private void performSearch(String query) {
//        // Implement your search logic here
//        // For example, filter the video thumbnails based on the query
//        for (int i = 0; i < thumbnails.length; i++) {
//            // Example: Check if the video title contains the query
//            if (videoTitles[i].toLowerCase().contains(query.toLowerCase())) {
//                thumbnails[i].setVisibility(View.VISIBLE);
//            } else {
//                thumbnails[i].setVisibility(View.GONE);
//            }
//        }
//    }

    private void playVideo(int index) {
        currentVideoIndex = index;
        Uri videoUri = Uri.parse(videoUris[currentVideoIndex]);

        if (getActivity() != null) {
            Intent intent = new Intent(getActivity(), PlayVideoActivity.class);
            intent.putExtra("videoUri", videoUri.toString());
            startActivity(intent);
        }
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.video_menu, menu);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        // Refresh (icon always visible):
        if (id == R.id.ic_user) {
            return true;
        }
        // Settings (always in the overflow menu):
        else if (id == R.id.aa) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}