package com.example.birdstagram.tools;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.birdstagram.data.tools.User;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "Birdstagram_DB";
    public static final String USER_TABLE = "USER";
    public static final String USER_ID = "ID";
    public static final String USER_PSEUDO = "PSEUDO";
    public static final String USER_NAME = "NAME";
    public static final String USER_SURNAME = "SURNAME";
    public static final String USER_AGE = "AGE";
    public static final String USER_MAIL = "MAIL";
    public static final String USER_PASSWORD = "PASSWORD";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + USER_TABLE + "("+ USER_ID +" INTEGER PRIMARY KEY AUTOINCREMENT, " + USER_PSEUDO + " TEXT,"  + USER_NAME + " TEXT, "+ USER_SURNAME +" TEXT," +
                USER_AGE +" INTEGER, "+ USER_MAIL + " TEXT, " + USER_PASSWORD +" TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + USER_TABLE);
    }

    public void insertDataUser(String pseudo, String name, String surname, String age, String mail, String pwd){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(USER_PSEUDO, pseudo);
        values.put(USER_NAME, name);
        values.put(USER_SURNAME, surname);
        values.put(USER_AGE, age);
        values.put(USER_MAIL, mail);
        values.put(USER_PASSWORD, pwd);
        long insertResult = db.insert(USER_TABLE, null, values);
        if(insertResult == -1){
            Log.d("DATABASE", "INSERT HAS FAILED.");
        }
        else {
            Log.d("DATABASE", "INSERT HAS SUCCEEDED.");
        }
    }

    public void insertDataUser(User user){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(USER_PSEUDO, user.getPseudo());
        values.put(USER_NAME, user.getName());
        values.put(USER_SURNAME, user.getSurname());
        values.put(USER_AGE, user.getAge());
        values.put(USER_MAIL, user.getMail());
        values.put(USER_PASSWORD, user.getPassword());
        long insertResult = db.insert(USER_TABLE, null, values);
        if(insertResult == -1){
            Log.d("DATABASE", "INSERT HAS FAILED.");
        }
        else {
            Log.d("DATABASE", "INSERT HAS SUCCEEDED.");
        }
    }

}





















