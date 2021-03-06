package com.example.birdstagram.tools;

import android.database.Cursor;
import android.util.Log;

import com.example.birdstagram.activities.MainActivity;
import com.example.birdstagram.data.tools.Comment;
import com.example.birdstagram.data.tools.Like;
import com.example.birdstagram.data.tools.Post;
import com.example.birdstagram.data.tools.Specie;
import com.example.birdstagram.data.tools.User;
import com.example.birdstagram.data.tools.Views;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import static android.content.Context.LOCATION_SERVICE;
import static com.example.birdstagram.activities.MainActivity.BDD;

public class DataRetriever {

    public DataRetriever() {
    }

    public ArrayList<User> retrieveUsers(){
        ArrayList<User> users = new ArrayList<>();
        Cursor res = BDD.getUsers();
        if(res.getCount() == 0){
            Log.i("BUNDLE", "Found 0 users");
        }
        else{
            while(res.moveToNext()){
                String userID = res.getString(0);
                String userPseudo = res.getString(1);
                String userName = res.getString(2);
                String userSurname = res.getString(3);
                String userAge = res.getString(4);
                String userMail = res.getString(5);
                String userPassword = res.getString(6);
                users.add(new User(Integer.parseInt(userID), userPseudo, userName, userSurname, Integer.parseInt(userAge), userMail, userPassword));
            }
        }
        return users;
    }

    public ArrayList<Specie> retrieveSpecies(){
        ArrayList<Specie> species = new ArrayList<>();
        Cursor res = BDD.getSpecies();
        if(res.getCount() == 0){
            Log.i("BUNDLE", "Found 0 species");
        }
        else{
            while(res.moveToNext()){
                String specieID = res.getString(0);
                String specieEnglishName = res.getString(1);
                String specieFrenchName = res.getString(2);
                String specieDescription = res.getString(3);
                species.add(new Specie(Integer.parseInt(specieID), specieEnglishName, specieFrenchName, specieDescription));
            }
        }
        return species;
    }

    public ArrayList<Post> retrieveUserPosts(int userId) throws ParseException {
        ArrayList<Post> userPosts = new ArrayList<>();
        for(Post post : MainActivity.dataBundle.getAppPosts()) {
            if (userId == post.getUser().getId()) {
                userPosts.add(post);
            }
        }
        return userPosts;
    }

    public ArrayList<Post> retrievePosts() throws ParseException {
        ArrayList<Post> foundPosts = new ArrayList<>();
        Cursor res = BDD.getAllPosts();
        if(res.getCount() == 0){
            Log.i("BUNDLE", "Found 0 posts");
        }
        else{
            while(res.moveToNext()){
                String postID = res.getString(0);
                String postDescription = res.getString(1);
                String postDate = res.getString(2);
                StringBuilder postDateValueString = new StringBuilder();
                int stringSize = postDate.length();
                String postDateDay = new String();
                String postDateMonth = new String();
                String postDateYear = new String();
                String postDateHour = new String();
                String postDateMinute = new String();
                if(stringSize == 34) {
                     postDateDay = postDate.substring(8, 10);
                     postDateMonth = postDate.substring(4, 7);
                     postDateYear = postDate.substring(32, 34);
                     postDateHour = postDate.substring(11, 13);
                     postDateMinute = postDate.substring(14, 16);
                }
                if(stringSize == 28){
                    postDateDay = postDate.substring(8, 10);
                    postDateMonth = postDate.substring(4, 7);
                    postDateYear = postDate.substring(26, 28);
                    postDateHour = postDate.substring(11, 13);
                    postDateMinute = postDate.substring(14, 16);
                }
                postDateValueString.append(postDateDay + " " + postDateMonth + " " + postDateYear + " " + postDateHour + ":" + postDateMinute);
                Date postDateValue = new SimpleDateFormat("dd MMM yy HH:mm", Locale.US).parse(postDateValueString.toString());
                String postLongitude = res.getString(3);
                String postLatitude = res.getString(4);
                String isPublic = res.getString(5);
                Specie postSpecie = retrievePostSpecie(Integer.parseInt(postID));
                User postUser = retrievePostUser(Integer.parseInt(postID));
                foundPosts.add(new Post(Integer.parseInt(postID), postDescription, postDateValue, Double.parseDouble(postLongitude), Double.parseDouble(postLatitude), Boolean.valueOf(isPublic), postSpecie, postUser));
            }
        }
        return foundPosts;
    }

    public Specie retrievePostSpecie(int postID){
        Specie foundSpecie = new Specie();
        Cursor res = BDD.getPostSpecieId(Integer.toString(postID));
        if(res.getCount() == 0){
            Log.i("BUNDLE", "Found 0 posts");
        }
        else{
            while(res.moveToNext()){
                String specieID = res.getString(0);
                Cursor res2 = BDD.getSpecie(specieID);
                if (res2.getCount() == 0){
                    Log.i("BUNDLE", "Specie for post not found");
                }
                else{
                    while(res2.moveToNext()){
                        String specieEnglishName = res2.getString(1);
                        String specieFrenchName = res2.getString(2);
                        String specieDescription = res2.getString(3);
                        foundSpecie = new Specie(Integer.parseInt(specieID), specieEnglishName, specieFrenchName, specieDescription);
                    }
                }
            }
        }
        return foundSpecie;
    }

    public User retrievePostUser(int postID){
        User foundUser = new User();
        Cursor res = BDD.getPostUserId(Integer.toString(postID));
        if(res.getCount() == 0){
            Log.i("BUNDLE", "Found 0 posts");
        }
        else{
            while(res.moveToNext()){
                String userID = res.getString(0);
                Cursor res2 = BDD.getUser(userID);
                if (res2.getCount() == 0){
                    Log.i("BUNDLE", "User for post not found");
                }
                else{
                    while(res2.moveToNext()){
                        String userPseudo = res2.getString(1);
                        String userName = res2.getString(2);
                        String userSurname = res2.getString(3);
                        String userAge = res2.getString(4);
                        String userMail = res2.getString(5);
                        String userPassword = res2.getString(6);
                        foundUser = new User(Integer.parseInt(userID), userPseudo, userName, userSurname, Integer.parseInt(userAge), userMail, userPassword);
                    }
                }
            }
        }
        return foundUser;
    }

    public ArrayList<Like> retrieveLikes() throws ParseException {
        ArrayList<Like> foundLikes = new ArrayList<>();
        Cursor res = BDD.getLikes();
        if(res.getCount() == 0){
            Log.i("BUNDLE", "Found 0 likes");
        }
        else{
            while(res.moveToNext()){
                String likeId = res.getString(0);
                String userID = res.getString(2);
                String postID = res.getString(1);
                String likeDate = res.getString(3);
                StringBuilder likeDateValueString = new StringBuilder();
                int stringSize = likeDate.length();
                String postDateDay = new String();
                String postDateMonth = new String();
                String postDateYear = new String();
                String postDateHour = new String();
                String postDateMinute = new String();
                if(stringSize == 34) {
                    postDateDay = likeDate.substring(8, 10);
                    postDateMonth = likeDate.substring(4, 7);
                    postDateYear = likeDate.substring(32, 34);
                    postDateHour = likeDate.substring(11, 13);
                    postDateMinute = likeDate.substring(14, 16);
                }
                if(stringSize == 28){
                    postDateDay = likeDate.substring(8, 10);
                    postDateMonth = likeDate.substring(4, 7);
                    postDateYear = likeDate.substring(26, 28);
                    postDateHour = likeDate.substring(11, 13);
                    postDateMinute = likeDate.substring(14, 16);
                }
                likeDateValueString.append(postDateDay + " " + postDateMonth + " " + postDateYear + " " + postDateHour + ":" + postDateMinute);
                Date postDateValue = new SimpleDateFormat("dd MMM yy HH:mm", Locale.US).parse(likeDateValueString.toString());
                User likeUser = new User();
                Post likePost = new Post();
                for(User user : MainActivity.dataBundle.getAppUsers()){
                    if(user.getId() == Integer.parseInt(userID)){
                        likeUser = user;
                    }
                }
                for(Post post : MainActivity.dataBundle.getAppPosts()){
                    if(post.getId() == Integer.parseInt(postID)){
                        likePost = post;
                    }
                }
                foundLikes.add(new Like(Integer.parseInt(likeId), likePost, likeUser, postDateValue));
            }
        }
        return foundLikes;
    }

    public ArrayList<Views> retrieveViews() throws ParseException {
        ArrayList<Views> foundViews = new ArrayList<>();
        Cursor res = BDD.getViews();
        if(res.getCount() == 0){
            Log.i("BUNDLE", "Found 0 views");
        }
        else{
            while(res.moveToNext()){
                String viewId = res.getString(0);
                String userID = res.getString(2);
                String postID = res.getString(1);
                String viewDate = res.getString(3);
                StringBuilder viewDateValueString = new StringBuilder();
                int stringSize = viewDate.length();
                String postDateDay = new String();
                String postDateMonth = new String();
                String postDateYear = new String();
                String postDateHour = new String();
                String postDateMinute = new String();
                if(stringSize == 34) {
                    postDateDay = viewDate.substring(8, 10);
                    postDateMonth = viewDate.substring(4, 7);
                    postDateYear = viewDate.substring(32, 34);
                    postDateHour = viewDate.substring(11, 13);
                    postDateMinute = viewDate.substring(14, 16);
                }
                if(stringSize == 28){
                    postDateDay = viewDate.substring(8, 10);
                    postDateMonth = viewDate.substring(4, 7);
                    postDateYear = viewDate.substring(26, 28);
                    postDateHour = viewDate.substring(11, 13);
                    postDateMinute = viewDate.substring(14, 16);
                }
                viewDateValueString.append(postDateDay + " " + postDateMonth + " " + postDateYear + " " + postDateHour + ":" + postDateMinute);
                Date postDateValue = new SimpleDateFormat("dd MMM yy HH:mm", Locale.US).parse(viewDateValueString.toString());
                User viewUser = new User();
                Post viewPost = new Post();
                for(User user : MainActivity.dataBundle.getAppUsers()){
                    if(user.getId() == Integer.parseInt(userID)){
                        viewUser = user;
                    }
                }
                for(Post post : MainActivity.dataBundle.getAppPosts()){
                    if(post.getId() == Integer.parseInt(postID)){
                        viewPost = post;
                    }
                }
                foundViews.add(new Views(Integer.parseInt(viewId), viewPost, viewUser, postDateValue));
            }
        }
        return foundViews;
    }

    public ArrayList<Comment> retrieveComments() throws ParseException {
        ArrayList<Comment> foundComments = new ArrayList<>();
        Cursor res = BDD.getComments();
        if(res.getCount() == 0){
            Log.i("BUNDLE", "Found 0 comments");
        }
        else{
            while(res.moveToNext()){
                String commentId = res.getString(0);
                String userID = res.getString(2);
                String postID = res.getString(1);
                String commentText = res.getString(3);
                String commentDate = res.getString(4);
                StringBuilder commentDateValueString = new StringBuilder();
                int stringSize = commentDate.length();
                String postDateDay = new String();
                String postDateMonth = new String();
                String postDateYear = new String();
                String postDateHour = new String();
                String postDateMinute = new String();
                if(stringSize == 34) {
                    postDateDay = commentDate.substring(8, 10);
                    postDateMonth = commentDate.substring(4, 7);
                    postDateYear = commentDate.substring(32, 34);
                    postDateHour = commentDate.substring(11, 13);
                    postDateMinute = commentDate.substring(14, 16);
                }
                if(stringSize == 28){
                    postDateDay = commentDate.substring(8, 10);
                    postDateMonth = commentDate.substring(4, 7);
                    postDateYear = commentDate.substring(26, 28);
                    postDateHour = commentDate.substring(11, 13);
                    postDateMinute = commentDate.substring(14, 16);
                }
                commentDateValueString.append(postDateDay + " " + postDateMonth + " " + postDateYear + " " + postDateHour + ":" + postDateMinute);
                Date postDateValue = new SimpleDateFormat("dd MMM yy HH:mm", Locale.US).parse(commentDateValueString.toString());
                User commentUser = new User();
                Post commentPost = new Post();
                for(User user : MainActivity.dataBundle.getAppUsers()){
                    if(user.getId() == Integer.parseInt(userID)){
                        commentUser = user;
                    }
                }
                for(Post post : MainActivity.dataBundle.getAppPosts()){
                    if(post.getId() == Integer.parseInt(postID)){
                        commentPost = post;
                    }
                }
                foundComments.add(new Comment(Integer.parseInt(commentId), commentPost, commentUser, commentText,postDateValue));
            }
        }
        return foundComments;
    }

}













