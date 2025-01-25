package com.example;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.google.android.material.tabs.TabLayout;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link VideoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class VideoFragment extends Fragment {
    //    private SearchView searchView;
    private ImageButton ic_setting;

    private static final float SMALL_FONT_SIZE = 14f;
    private static final float MEDIUM_FONT_SIZE = 18f; // Default
    private static final float LARGE_FONT_SIZE = 22f;

    private float currentFontSize = MEDIUM_FONT_SIZE;

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

        return view;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.video_menu, menu);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.ic_user) {

            return true;
        }

        else if (id == R.id.aa) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ImageButton imageButton = requireView().findViewById(R.id.ic_user);
        imageButton.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), SettingActivity.class);
            startActivity(intent);
        });

        ImageView fontSizeButton = requireView().findViewById(R.id.aa);
        fontSizeButton.setOnClickListener(v -> changeFontSize());
    }

    private void changeFontSize() {
        // Cycle through font sizes
        if (currentFontSize == SMALL_FONT_SIZE) {
            currentFontSize = MEDIUM_FONT_SIZE;
        } else if (currentFontSize == MEDIUM_FONT_SIZE) {
            currentFontSize = LARGE_FONT_SIZE;
        } else {
            currentFontSize = SMALL_FONT_SIZE;
        }

        // Update the font size of the text views
        updateFontSize();
    }

    private void updateFontSize() {
        TextView appName = requireView().findViewById(R.id.app_name);
        appName.setTextSize(currentFontSize);

        // Update other TextViews as needed
    }
}