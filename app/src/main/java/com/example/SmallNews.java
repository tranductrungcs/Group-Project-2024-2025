package com.example;

import androidx.annotation.NonNull;

import java.util.Locale;

public class SmallNews {
    private int id;
    private String title;
    private String date;
    private String description;
    private String[] contents;
    private String imageUrl;

    public SmallNews(Integer id, String title, String imageUrl){
        this.id = id;
        this.title = title;
        this.imageUrl = imageUrl;
    }

    public SmallNews() {}

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return this.title;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDate() {
        return date;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setContents(String[] contents) {
        this.contents = contents;
    }

    public String[] getContents() {
        return contents;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }

}
