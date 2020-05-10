package com.example.birdstagram.tools;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.birdstagram.data.tools.User;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "Birdstagram_DB";
    public static final String USER_TABLE = "USER";
    public static final String SPECIE_TABLE = "SPECIE";
    public static final String POST_TABLE = "POST";
    public static final String POST_VIEWS_TABLE = "POST_VIEW";
    public static final String POST_LIKES_TABLE = "POST_LIKE";
    public static final String POST_COMMENTS_TABLE = "POST_COMMENT";

    public static final String USER_ID = "ID";
    public static final String USER_PSEUDO = "PSEUDO";
    public static final String USER_NAME = "NAME";
    public static final String USER_SURNAME = "SURNAME";
    public static final String USER_AGE = "AGE";
    public static final String USER_MAIL = "MAIL";
    public static final String USER_PASSWORD = "PASSWORD";

    public static final String SPECIE_ID = "ID";
    public static final String SPECIE_NAME = "NAME";
    public static final String SPECIE_DESCRIPTION = "DESCRIPTION";

    public static final String POST_ID = "ID";
    public static final String POST_DESCRIPTION = "DESCRIPTION";
    public static final String POST_DATE= "DATE";
    public static final String POST_LONGITUDE= "LONGITUDE";
    public static final String POST_LATITUDE = "LATITUDE";
    public static final String POST_IS_PUBLIC = "IS_PUBLIC";
    public static final String POST_FOREIGN_ID = "USER_ID";

    public static final String POST_VIEW_ID = "POST_ID";
    public static final String POST_VIEWER_ID = "USER_ID";
    public static final String POST_VIEW_DATE = "DATE";

    public static final String POST_LIKE_ID = "POST_ID";
    public static final String POST_LIKER_ID = "USER_ID";
    public static final String POST_LIKE_DATE= "DATE";

    public static final String POST_COMMENT_POST_ID = "POST_ID";
    public static final String POST_COMMENTOR_ID = "USER_ID";
    public static final String POST_COMMENT = "COMMENT";
    public static final String POST_COMMENT_DATE= "DATE";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 2);
        SQLiteDatabase db = this.getWritableDatabase(); // Active la création de la base de données
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.i("DATABASE", "IN ONCREATE");
        db.execSQL("create table " + USER_TABLE + "("+ USER_ID +" INTEGER PRIMARY KEY AUTOINCREMENT, " + USER_PSEUDO + " TEXT,"  + USER_NAME + " TEXT, "+ USER_SURNAME +" TEXT," +
                USER_AGE +" INTEGER, "+ USER_MAIL + " TEXT, " + USER_PASSWORD +" TEXT)");
        db.execSQL("create table " + SPECIE_TABLE + "( " + SPECIE_ID +" INTEGER PRIMARY KEY AUTOINCREMENT, "+ SPECIE_NAME  +" TEXT, " + SPECIE_DESCRIPTION + " TEXT) ");
        db.execSQL("create table " + POST_TABLE +"("+ POST_ID +" INTEGER PRIMARY KEY AUTOINCREMENT, " + POST_DESCRIPTION + " TEXT, " + POST_DATE + " DATE, " + POST_LONGITUDE + " FLOAT, " + POST_LATITUDE + " FLOAT, " + POST_IS_PUBLIC + " BOOLEAN, " + POST_FOREIGN_ID + " INTEGER, FOREIGN KEY("+ POST_FOREIGN_ID+") REFERENCES "+ USER_TABLE + "(" + USER_ID + "))");
        db.execSQL("create table " + POST_VIEWS_TABLE +"("+ POST_VIEW_ID +" INTEGER, " + POST_VIEWER_ID + " INTEGER, " + POST_VIEW_DATE + " DATE, FOREIGN KEY("+ POST_VIEW_ID +") REFERENCES "+ POST_TABLE + "(" + POST_ID + "), FOREIGN KEY("+POST_VIEWER_ID +") REFERENCES " + USER_TABLE + "(" + USER_ID + "))");
        db.execSQL("create table " + POST_LIKES_TABLE +"("+ POST_LIKE_ID +" INTEGER, " + POST_LIKER_ID + " INTEGER, " + POST_LIKE_DATE + " DATE, FOREIGN KEY("+ POST_LIKE_ID +") REFERENCES "+ POST_TABLE + "(" + POST_ID + "), FOREIGN KEY("+POST_LIKER_ID +") REFERENCES " + USER_TABLE + "(" + USER_ID + "))");
        db.execSQL("create table " + POST_COMMENTS_TABLE +"(" + POST_COMMENT_POST_ID +" INTEGER, " + POST_COMMENTOR_ID + " INTEGER," + POST_COMMENT +" TEXT, " + POST_COMMENT_DATE + " DATE, FOREIGN KEY("+ POST_COMMENT_POST_ID +") REFERENCES "+ POST_TABLE + "(" + POST_ID + "), FOREIGN KEY("+POST_COMMENTOR_ID +") REFERENCES " + USER_TABLE + "(" + USER_ID + "))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + USER_TABLE);
        onCreate(db);
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
