package com.example.birdstagram.data.tools;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class DataBundle implements Parcelable {

    private User userSession;
    private ArrayList<Post> userPosts;
    private ArrayList<User> appUsers;
    private ArrayList<Specie> appSpecies;
    private ArrayList<Post> appPosts;
    private ArrayList<Like> appLikes;
    private ArrayList<Comment> appComments;
    private ArrayList<View> appViewers;

    public DataBundle() {

    }

    public DataBundle(User userSession, ArrayList<Post> userPosts, ArrayList<User> appUsers, ArrayList<Specie> appSpecies, ArrayList<Post> appPosts, ArrayList<Like> appLikes, ArrayList<Comment> appComments, ArrayList<View> appViewers) {
        this.userSession = userSession;
        this.userPosts = userPosts;
        this.appUsers = appUsers;
        this.appSpecies = appSpecies;
        this.appPosts = appPosts;
        this.appLikes = appLikes;
        this.appComments = appComments;
        this.appViewers = appViewers;
    }

    protected DataBundle(Parcel in) {
        userSession = in.readParcelable(User.class.getClassLoader());
        appUsers = in.createTypedArrayList(User.CREATOR);
    }

    public static final Creator<DataBundle> CREATOR = new Creator<DataBundle>() {
        @Override
        public DataBundle createFromParcel(Parcel in) {
            return new DataBundle(in);
        }

        @Override
        public DataBundle[] newArray(int size) {
            return new DataBundle[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(userSession, flags);
        dest.writeTypedList(appUsers);
    }

    public User getUserSession() {
        return userSession;
    }

    public void setUserSession(User userSession) {
        this.userSession = userSession;
    }

    public ArrayList<Post> getUserPosts() {
        return userPosts;
    }

    public void setUserPosts(ArrayList<Post> userPosts) {
        this.userPosts = userPosts;
    }

    public ArrayList<User> getAppUsers() {
        return appUsers;
    }

    public void setAppUsers(ArrayList<User> appUsers) {
        this.appUsers = appUsers;
    }

    public ArrayList<Specie> getAppSpecies() {
        return appSpecies;
    }

    public void setAppSpecies(ArrayList<Specie> appSpecies) {
        this.appSpecies = appSpecies;
    }

    public ArrayList<Post> getAppPosts() {
        return appPosts;
    }

    public void setAppPosts(ArrayList<Post> appPosts) {
        this.appPosts = appPosts;
    }

    public ArrayList<Like> getAppLikes() {
        return appLikes;
    }

    public void setAppLikes(ArrayList<Like> appLikes) {
        this.appLikes = appLikes;
    }

    public ArrayList<Comment> getAppComments() {
        return appComments;
    }

    public void setAppComments(ArrayList<Comment> appComments) {
        this.appComments = appComments;
    }

    public ArrayList<View> getAppViewers() {
        return appViewers;
    }

    public void setAppViewers(ArrayList<View> appViewers) {
        this.appViewers = appViewers;
    }
}
