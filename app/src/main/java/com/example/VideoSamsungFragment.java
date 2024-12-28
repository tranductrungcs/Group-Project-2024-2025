package com.example;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link VideoSamsungFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class VideoSamsungFragment extends Fragment {
    private Button button;

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
        return view;
    }
}