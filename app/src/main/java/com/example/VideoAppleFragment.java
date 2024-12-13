package com.example;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link VideoAppleFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class VideoAppleFragment extends Fragment {

    private final String[] videoUris = new String[2];
    private final ImageButton[] thumbnails = new ImageButton[2];

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

        // Initialize video items list
        if (getActivity() != null) {
            videoUris[0] = "android.resource://" + getActivity().getPackageName() + "/" + R.raw.apple_watch_series10;
            videoUris[1] = "android.resource://" + getActivity().getPackageName() + "/" + R.raw.airpods4;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_video_apple, container, false);

        thumbnails[0] = view.findViewById(R.id.apple_watch_series10);
        thumbnails[1] = view.findViewById(R.id.airpods4);

        for (int i = 0; i < thumbnails.length; i++) {
            final int index = i;
            thumbnails[i].setOnClickListener(v -> playVideo(index));
        }

        return view;
    }

    private void playVideo(int index) {
        if (index < 0 || index >= videoUris.length || videoUris[index] == null) {
            Toast.makeText(getContext(), "Video not found", Toast.LENGTH_SHORT).show();
            return;
        }

        Uri videoUri = Uri.parse(videoUris[index]);

        // Start PlayVideoActivity with the selected URI
        if (getActivity() != null) {
            Intent intent = new Intent(getActivity(), PlayVideoActivity.class);
            intent.putExtra("videoUri", videoUri.toString());
            startActivity(intent);
        }
    }
}