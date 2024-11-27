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
    private String videoUrl;

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

    public void setVideoUrl(String videoUrl) { this.videoUrl = videoUrl; }

    public String getVideoUrl() { return videoUrl; }

    @NonNull
    public String toString() {
        return String.format(
                Locale.US,
                "%d - %s - %s - %s - %s - %s",
                id,
                title,
                date,
                description,
                contents.length != 0 ? "" : contents[0],
                imageUrl
        );
    }
}
