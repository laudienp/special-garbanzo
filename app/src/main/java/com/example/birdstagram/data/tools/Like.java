package com.example.birdstagram.data.tools;

import java.util.Date;

public class Like {

    private int id;
    private Post postID;
    private User userID;
    private Date date;

    public Like(int id, Post postID, User userID, Date date) {
        this.id = id;
        this.postID = postID;
        this.userID = userID;
        this.date = date;
    }

    public Like(Post postID, User userID, Date date) {
        this.postID = postID;
        this.userID = userID;
        this.date = date;
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
