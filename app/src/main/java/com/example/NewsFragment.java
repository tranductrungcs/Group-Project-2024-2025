package com.example;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NewsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NewsFragment extends Fragment implements SelectListener {

    private RecyclerView recyclerNews;
    private NewsAdapter newsAdapter;
    private List<SmallNews> NewsList = new ArrayList<>();
    SQLconnection sqlconnection;
    Connection con;



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
        View view = inflater.inflate(R.layout.fragment_news, container, false);
        recyclerNews = view.findViewById(R.id.NewsList);
        recyclerNews.setHasFixedSize(true);
        recyclerNews.setLayoutManager(new GridLayoutManager(getContext(), 2));

        ImageButton container_test = view.findViewById(R.id.test_to_show_news1);
        container_test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                movetoNewsDetail();
            }
        });

        sqlconnection = new SQLconnection();

        showItem();
        newsAdapter = new NewsAdapter(getContext(), NewsList, this);
        recyclerNews.setAdapter(newsAdapter);



        return view;
    }
    public void movetoNewsDetail(){
        Intent intent = new Intent(getContext(),ShowNewsActivity.class);
        startActivity(intent);
    }

    @Override
    public void onItemClicked(int position) {
        Intent intent = new Intent(getContext(), ShowNewsActivity.class);

    }

    public void onLongItemClick(int position){
        //no usage here, just declare to not abstract
    }

    public void showItem() {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(() -> {
            try {
                con = sqlconnection.CONN();
                String query = "SELECT * FROM newschema.api_article ORDER BY id LIMIT 10";
                PreparedStatement stmt = con.prepareStatement(query);
                ResultSet rs = stmt.executeQuery();
                List<SmallNews> newsList = new ArrayList<>();

                while (rs.next()) {
                    SmallNews newsItem = new SmallNews();
                    newsItem.setId(rs.getInt("id"));
                    newsItem.setTitle(rs.getString("title"));
                    newsItem.setImageUrl(rs.getString("urlToImage"));
                    newsList.add(newsItem);
                }

                requireActivity().runOnUiThread(() -> {
                    NewsList.clear();
                    NewsList.addAll(newsList);
                    newsAdapter.setNewsList(NewsList);
                });

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }

}