package com.example;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NewsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NewsFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public NewsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NewsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static NewsFragment newInstance(String param1, String param2) {
        NewsFragment fragment = new NewsFragment();
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
        View view = inflater.inflate(R.layout.fragment_news, container, false);
        ImageButton container_test = view.findViewById(R.id.test_to_show_news1);
        ImageButton container_test1 = view.findViewById(R.id.test_to_show_news2);
        ImageButton container_test2 = view.findViewById(R.id.test_to_show_news3);
        ImageButton container_test3 = view.findViewById(R.id.test_to_show_news4);
        RelativeLayout clickme = view.findViewById(R.id.test_to_show_news5);
        container_test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                movetoNewsDetail();
            }
        });
        //HAVE TO REPLACE THIS LATER
        //REMEMBER TO REPLACE THIS LATER
        //THIS FOR TESTING
        container_test1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                movetoNewsDetail();
            }
        });
        container_test2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                movetoNewsDetail();
            }
        });
        container_test3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                movetoNewsDetail();
            }
        });
        //HAVE TO MAKE INTENT FROM RELATIVELAYOUT OR SIMILAR. OR ANOTHER APPROACH
        clickme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                movetoNewsDetail();
            }
        });
        //TESTING STOP HERE
        //WILL DELETE LATER
        return view;

    }
    public void movetoNewsDetail(){
        Intent intent = new Intent(getContext(),ShowNewsActivity.class);
        startActivity(intent);
    }
}