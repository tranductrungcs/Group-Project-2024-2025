package com.example;

public class Video {
    private String title;
    private String thumbnailImageUrl;
    private String videoBrandType;

    // Constructor
    public Video(String title, String thumbnailImageUrl, String videoBrandType) {
        this.title = title;
        this.thumbnailImageUrl = thumbnailImageUrl;
        this.videoBrandType = videoBrandType;
    }

    // Getter cho title
    public String getTitle() {
        return title;
    }

    // Getter cho thumbnailImageUrl
    public String getThumbnailImageUrl() {
        return thumbnailImageUrl;
    }

    // Getter cho videoBrandType
    public String getVideoBrandType() {
        return videoBrandType;
    }

    public byte[] getThumbnailImageFetchableUrl() {
        return new byte[0];
    }
}
