package com.example.newspackage;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface NewsAPI {
    @GET("articles")
    Call<List<SmallNews>> getNews();
}
