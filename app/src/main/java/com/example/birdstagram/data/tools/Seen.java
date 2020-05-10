package com.example.birdstagram.data.tools;

import java.util.Date;

public class Seen {

    private Post postID;
    private User userID;
    private Date date;

    public Seen(Post postID, User userID, Date date) {
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
