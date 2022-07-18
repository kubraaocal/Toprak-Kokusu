package com.example.toprakkokusu.ui.activity;

public class ActivityModel {
    private String photo,title,text,userId;

    public ActivityModel(String photo, String text, String title,String userId) {
        this.photo = photo;
        this.title = title;
        this.text = text;
        this.userId=userId;
    }

    public ActivityModel() {
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
