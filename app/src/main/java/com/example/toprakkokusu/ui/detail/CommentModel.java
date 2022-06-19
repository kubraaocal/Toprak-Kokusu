package com.example.toprakkokusu.ui.detail;

public class CommentModel {
    String userId,comment,time;

    public CommentModel( String userId, String comment,String time) {
        this.userId = userId;
        this.comment = comment;
        this.time = time;
    }

    public CommentModel() {
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
