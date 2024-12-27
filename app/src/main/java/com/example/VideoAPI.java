package com.example;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface VideoAPI {
    @GET("https://android-backend-tech-c52e01da23ae.herokuapp.com/videos?format=api")
    Call<List<Video>> getVideos(String apple);
}
