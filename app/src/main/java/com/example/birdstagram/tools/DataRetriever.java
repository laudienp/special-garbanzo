package com.example.birdstagram.tools;

import android.database.Cursor;
import android.util.Log;

import com.example.birdstagram.data.tools.Post;
import com.example.birdstagram.data.tools.Specie;
import com.example.birdstagram.data.tools.User;

import java.sql.Date;
import java.util.ArrayList;

import static com.example.birdstagram.activities.MainActivity.myDb;

public class DataRetriever {

    public DataRetriever() {
    }

    public ArrayList<User> retrieveUsers(){
        ArrayList<User> users = new ArrayList<>();
        Cursor res = myDb.getUsers();
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
        Cursor res = myDb.getSpecies();
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

    public ArrayList<Post> retrieveUserPosts(){
        return null;
    }

}
