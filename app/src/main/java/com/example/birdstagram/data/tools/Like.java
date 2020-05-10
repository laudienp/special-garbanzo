package com.example.birdstagram.data.tools;

import java.util.Date;

public class Like {

    private int postID;
    private int userID;
    private Date date;

    public Like(int postID, int userID, Date date) {
        this.postID = postID;
        this.userID = userID;
        this.date = date;
    }

    public int getPostID() {
        return postID;
    }

    public int getUserID() {
        return userID;
    }

    public Date getDate() {
        return date;
    }

}
