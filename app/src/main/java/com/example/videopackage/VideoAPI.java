package com.example.videopackage;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface VideoAPI {
    @GET("videos/search")
    Call<List<Video>> getVideos(@Query("originKey") String originKey);
}
