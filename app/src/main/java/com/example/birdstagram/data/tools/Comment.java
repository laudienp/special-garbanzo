package com.example.birdstagram.data.tools;

import java.util.Date;

public class Comment {

    private int postID;
    private int userID;
    private String comment;
    private Date date;

    public Comment(int postID, int userID, String comment, Date date) {
        this.postID = postID;
        this.userID = userID;
        this.comment = comment;
        this.date = date;
    }

    public int getPostID() {
        return postID;
    }

    public int getUserID() {
        return userID;
    }

    public String getComment() {
        return comment;
    }

    public Date getDate() {
        return date;
    }
}
