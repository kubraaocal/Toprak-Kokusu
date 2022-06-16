package com.example.toprakkokusu.ui.activity;

public class ActivityModel {
    private String photo,title,text;

    public ActivityModel(String photo, String text, String title) {
        this.photo = photo;
        this.title = title;
        this.text = text;
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
}
