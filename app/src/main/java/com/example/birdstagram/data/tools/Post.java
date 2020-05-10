package com.example.birdstagram.data.tools;

import java.util.Date;

public class Post {

    private int id;
    private String description;
    private Date date;
    private double longitude;
    private double latitude;
    private Specie specie;
    private User user;
    private boolean isPublic;

    public Post(String description, Date date, double longitude, double latitude, Specie specie, User user, boolean isPublic) {
        this.description = description;
        this.date = date;
        this.longitude = longitude;
        this.latitude = latitude;
        this.specie = specie;
        this.user = user;
        this.isPublic = isPublic;
    }

    public Post(int id, String description, Date date, double longitude, double latitude, Specie specie, User user, boolean isPublic) {
        this.id = id;
        this.description = description;
        this.date = date;
        this.longitude = longitude;
        this.latitude = latitude;
        this.specie = specie;
        this.user = user;
        this.isPublic = isPublic;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public Specie getSpecie() {
        return specie;
    }

    public void setSpecie(Specie specie) {
        this.specie = specie;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public boolean isPublic() {
        return isPublic;
    }

    public void setPublic(boolean aPublic) {
        isPublic = aPublic;
    }
}
