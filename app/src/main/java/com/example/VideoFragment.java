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
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

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
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.VideoView;
import android.widget.Toast;

import com.google.android.material.search.SearchView;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link VideoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class VideoFragment extends Fragment {
//    private int currentVideoIndex = 0;
//    private final String[] videoUris = new String[16];
//    private final ImageButton[] thumbnails = new ImageButton[16];

    private final String[] videoUris = new String[4];
    private final ImageButton[] thumbnails = new ImageButton[4];

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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_video, container, false);

        LinearLayout searchBar = view.findViewById(R.id.search_bar);

        // Set click listener on the search bar
        searchBar.setOnClickListener(v -> {
            // Start SearchActivity
            Intent intent = new Intent(getActivity(), SearchActivity.class);
            startActivity(intent);
        });

        TabLayout tabLayout = view.findViewById(R.id.tab_layout);
        ViewPager viewPager = view.findViewById(R.id.view_pager);

        VideoViewPagerAdapter adapter = new VideoViewPagerAdapter(getChildFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        viewPager.setAdapter(adapter);

        tabLayout.setupWithViewPager(viewPager);
        tabLayout.getTabAt(0).setText(R.string.apple);
        tabLayout.getTabAt(1).setText(R.string.samsung);
        tabLayout.getTabAt(2).setText(R.string.huawei);
        tabLayout.getTabAt(3).setText(R.string.xiaomi);
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