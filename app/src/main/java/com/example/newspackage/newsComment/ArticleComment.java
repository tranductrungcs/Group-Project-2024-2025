package com.example.newspackage.newsComment;

public class ArticleComment {
    private int id;
    private String content;
    private int user;
    private String username;
    private int articleId;
    private String createdTime;
    private int likeNum;

    public ArticleComment(
            int id,
            String content,
            int user,
            String username,
            int articleId,
            String createdTime,
            int likeNum
    ) {
        this.id = id;
        this.content = content;
        this.user = user;
        this.articleId = articleId;
        this.createdTime = createdTime;
        this.likeNum = likeNum;
    }

    public int getId() {
        return id;
    }

    public int getArticleId() {
        return articleId;
    }

    public int getLikeNum() {
        return likeNum;
    }

    public int getUser() {
        return user;
    }

    public String getUsername() {
        return username;
    }

    public String getContent() {
        return content;
    }

    public String getCreatedTime() {
        return createdTime;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setArticleId(int articleId) {
        this.articleId = articleId;
    }

    public void setUser(int user) {
        this.user = user;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setCreatedTime(String createdTime) {
        this.createdTime = createdTime;
    }

    public void setLikeNum(int likeNum) {
        this.likeNum = likeNum;
    }
}
