package com.example.birdstagram.data.tools;

import java.util.Date;

public class Views {

    private int id;
    private Post postID;
    private User userID;
    private Date date;

    public Views(int id, Post postID, User userID, Date date) {
        this.id = id;
        this.postID = postID;
        this.userID = userID;
        this.date = date;
    }

    public Views(Post postID, User userID, Date date) {
        this.postID = postID;
        this.userID = userID;
        this.date = date;
    }

    public Views() {

    }

    public int getId() {
        return id;
    }

    public Post getPostID() {
        return postID;
    }

    public User getUserID() {
        return userID;
    }

    public Date getDate() {
        return date;
    }

}
