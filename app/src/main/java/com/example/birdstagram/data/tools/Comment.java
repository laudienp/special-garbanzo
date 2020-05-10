package com.example.birdstagram.data.tools;

import java.util.Date;

public class Comment {

    private Post postID;
    private User userID;
    private String comment;
    private Date date;

    public Comment(Post postID, User userID, String comment, Date date) {
        this.postID = postID;
        this.userID = userID;
        this.comment = comment;
        this.date = date;
    }

    public Post getPostID() {
        return postID;
    }

    public User getUserID() {
        return userID;
    }

    public String getComment() {
        return comment;
    }

    public Date getDate() {
        return date;
    }
}
