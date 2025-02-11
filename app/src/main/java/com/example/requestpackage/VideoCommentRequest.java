package com.example.requestpackage;

public class VideoCommentRequest {
    private String content;
    private int parentId;
    private int articleId;
    private int user;

    public VideoCommentRequest(String content, int parentId, int articleId, int user) {
        this.content = content;
        this.parentId = parentId;
        this.articleId = articleId;
        this.user = user;
    }
}
