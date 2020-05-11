package com.example.birdstagram.data.tools;

import java.util.Date;

public class View {

    private Post postID;
    private User userID;
    private Date date;

    public View(Post postID, User userID, Date date) {
        this.postID = postID;
        this.userID = userID;
        this.date = date;
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
