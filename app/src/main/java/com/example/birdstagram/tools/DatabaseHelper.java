package com.example.birdstagram.tools;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.birdstagram.data.tools.Comment;
import com.example.birdstagram.data.tools.Like;
import com.example.birdstagram.data.tools.Post;
import com.example.birdstagram.data.tools.Specie;
import com.example.birdstagram.data.tools.User;
import com.example.birdstagram.data.tools.Views;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "Birdstagram_DB";
    public static final String USER_TABLE = "USER";
    public static final String SPECIE_TABLE = "SPECIE";
    public static final String POST_TABLE = "POST";
    public static final String POST_VIEWS_TABLE = "VIEWS";
    public static final String POST_LIKES_TABLE = "LIKES";
    public static final String POST_COMMENTS_TABLE = "COMMENTS";

    public static final String USER_ID = "ID";
    public static final String USER_PSEUDO = "PSEUDO";
    public static final String USER_NAME = "NAME";
    public static final String USER_SURNAME = "SURNAME";
    public static final String USER_AGE = "AGE";
    public static final String USER_MAIL = "MAIL";
    public static final String USER_PASSWORD = "PASSWORD";

    public static final String SPECIE_ID = "ID";
    public static final String SPECIE_ENGLISH_NAME = "ENGLISH_NAME";
    public static final String SPECIE_FRENCH_NAME = "FRENCH_NAME";
    public static final String SPECIE_DESCRIPTION = "DESCRIPTION";

    public static final String POST_ID = "ID";
    public static final String POST_DESCRIPTION = "DESCRIPTION";
    public static final String POST_DATE= "DATE";
    public static final String POST_LONGITUDE= "LONGITUDE";
    public static final String POST_LATITUDE = "LATITUDE";
    public static final String POST_IS_PUBLIC = "IS_PUBLIC";
    public static final String POST_FOREIGN_USER_ID = "USER_ID";
    public static final String POST_FOREIGN_SPECIE_ID = "SPECIE_ID";

    public static final String POST_VIEW_ID = "VIEW_ID";
    public static final String POST_VIEW_POST_ID = "POST_ID";
    public static final String POST_VIEW_USER_ID = "USER_ID";
    public static final String POST_VIEW_DATE = "DATE";

    public static final String POST_LIKE_ID = "LIKE_ID";
    public static final String POST_LIKE_POST_ID = "POST_ID";
    public static final String POST_LIKER_ID = "USER_ID";
    public static final String POST_LIKE_DATE= "DATE";

    public static final String POST_COMMENT_ID = "COMMENT_ID";
    public static final String POST_COMMENT_POST_ID = "POST_ID";
    public static final String POST_COMMENTOR_ID = "USER_ID";
    public static final String POST_COMMENT_TEXT = "COMMENT";
    public static final String POST_COMMENT_DATE= "DATE";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 2);
        SQLiteDatabase db = this.getWritableDatabase(); // Active la création de la base de données
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

    public void insertDataSpecie(String englishName, String frenchName, String description){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(SPECIE_ENGLISH_NAME, englishName);
        values.put(SPECIE_FRENCH_NAME, frenchName);
        values.put(SPECIE_DESCRIPTION, description);
        long insertResult = db.insert(USER_TABLE, null, values);
        if(insertResult == -1){
            Log.d("DATABASE", "INSERT HAS FAILED.");
        }
        else {
            Log.d("DATABASE", "INSERT HAS SUCCEEDED.");
        }
    }

    public void insertDataSpecie(Specie specie){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(SPECIE_ENGLISH_NAME, specie.getEnglishName());
        values.put(SPECIE_FRENCH_NAME, specie.getFrenchName());
        values.put(SPECIE_DESCRIPTION, specie.getDescription());
        long insertResult = db.insert(USER_TABLE, null, values);
        if(insertResult == -1){
            Log.d("DATABASE", "INSERT HAS FAILED.");
        }
        else {
            Log.d("DATABASE", "INSERT HAS SUCCEEDED.");
        }
    }

    public void insertDataPost(Post post){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(POST_DESCRIPTION, post.getDescription());
        values.put(POST_DATE, post.getDate().toString());
        values.put(POST_LONGITUDE, Double.toString(post.getLongitude()));
        values.put(POST_LATITUDE, Double.toString(post.getLatitude()));
        values.put(POST_IS_PUBLIC, Boolean.toString(post.isPublic()));
        values.put(POST_FOREIGN_SPECIE_ID, Integer.toString(post.getSpecie().getId()));
        values.put(POST_FOREIGN_USER_ID, Integer.toString(post.getUser().getId()));

        long insertResult = db.insert(POST_TABLE, null, values);
        if(insertResult == -1){
            Log.d("DATABASE", "INSERT HAS FAILED.");
        }
        else {
            Log.d("DATABASE", "INSERT HAS SUCCEEDED.");
        }
    }

    public void insertDataView(Views view) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(POST_VIEW_POST_ID, view.getPostID().getId());
        values.put(POST_VIEW_USER_ID, view.getUserID().getId());
        values.put(POST_VIEW_DATE, view.getDate().toString());
        long insertResult = db.insert(POST_VIEWS_TABLE, null, values);
        if(insertResult == -1){
            Log.d("DATABASE", "INSERT HAS FAILED.");
        }
        else {
            Log.d("DATABASE", "INSERT HAS SUCCEEDED.");
        }
    }

    public void insertDataLike(Like like) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(POST_LIKE_POST_ID, like.getPostID().getId());
        values.put(POST_LIKER_ID, like.getUserID().getId());
        values.put(POST_LIKE_DATE, like.getDate().toString());
        long insertResult = db.insert(POST_LIKES_TABLE, null, values);
        if(insertResult == -1){
            Log.d("DATABASE", "INSERT HAS FAILED.");
        }
        else {
            Log.d("DATABASE", "INSERT HAS SUCCEEDED.");
        }
    }

    public void insertDataComment(Comment comment) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(POST_COMMENT_POST_ID, comment.getPostID().getId());
        values.put(POST_COMMENTOR_ID, comment.getUserID().getId());
        values.put(POST_COMMENT_TEXT, comment.getComment());
        values.put(POST_COMMENT_DATE, comment.getDate().toString());
        long insertResult = db.insert(POST_COMMENTS_TABLE, null, values);
        if(insertResult == -1){
            Log.d("DATABASE", "INSERT HAS FAILED.");
        }
        else {
            Log.d("DATABASE", "INSERT HAS SUCCEEDED.");
        }
    }

    public Cursor getConnectionUser(String email, String password){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor =  db.rawQuery("SELECT * FROM " + USER_TABLE + " WHERE " + USER_MAIL + " = '" + email + "' AND " + USER_PASSWORD + " = '" + password + "'", null);
        return cursor;
    }

    public Cursor getAllUsers(){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.rawQuery("SELECT * FROM " + USER_TABLE,null);
    }

    public Cursor getLikes(){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.rawQuery("SELECT * FROM " + POST_LIKES_TABLE,null);
    }

    public Cursor getViews(){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.rawQuery("SELECT * FROM " + POST_VIEWS_TABLE,null);
    }

    public Cursor verifyEmailExists(String email){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor =  db.rawQuery("SELECT * FROM " + USER_TABLE + " WHERE " + USER_MAIL + " = '" + email + "'", null);
        return cursor;
    }

    public Cursor getUsers() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor =  db.rawQuery("SELECT * FROM " + USER_TABLE, null);
        return cursor;
    }

    public Cursor getSpecies() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor =  db.rawQuery("SELECT * FROM " + SPECIE_TABLE, null);
        return cursor;
    }

    public Cursor getUserPosts(int userID) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor =  db.rawQuery("SELECT * FROM " + POST_TABLE + " WHERE " + POST_FOREIGN_USER_ID + " = '" + userID + "'", null);
        return cursor;
    }

    public Cursor getAllPosts(){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.rawQuery("SELECT * FROM " + POST_TABLE,null);
    }

    public Cursor getPostSpecieId(String id){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT "+  POST_FOREIGN_SPECIE_ID  + "  FROM " + POST_TABLE + " WHERE id=" + id;
        return db.rawQuery(query, null);
    }

    public Cursor getPostUserId(String id){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT "+  POST_FOREIGN_USER_ID  + "  FROM " + POST_TABLE + " WHERE id=" + id;
        return db.rawQuery(query, null);
    }

    public Cursor getSpecie(String id){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.rawQuery("SELECT * FROM " + SPECIE_TABLE + " WHERE id=" + id ,null);
    }

    public Cursor getUser(String id){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.rawQuery("SELECT * FROM " + USER_TABLE + " WHERE id=" + id ,null);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.i("DATABASE", "IN ONCREATE");
        db.execSQL("create table " + USER_TABLE + "("+ USER_ID +" INTEGER PRIMARY KEY AUTOINCREMENT, " + USER_PSEUDO + " TEXT,"  + USER_NAME + " TEXT, "+ USER_SURNAME +" TEXT," +
                USER_AGE +" INTEGER, "+ USER_MAIL + " TEXT, " + USER_PASSWORD +" TEXT)");
        db.execSQL("create table " + SPECIE_TABLE + "( " + SPECIE_ID +" INTEGER PRIMARY KEY AUTOINCREMENT, "+ SPECIE_ENGLISH_NAME  +" TEXT, " + SPECIE_FRENCH_NAME+" TEXT, " + SPECIE_DESCRIPTION + " TEXT) ");
        db.execSQL("create table " + POST_TABLE + "(" + POST_ID +" INTEGER PRIMARY KEY AUTOINCREMENT, " + POST_DESCRIPTION + " TEXT, " + POST_DATE +" DATE, " + POST_LONGITUDE +" FLOAT" +
                ", "+ POST_LATITUDE + " FLOAT, " + POST_IS_PUBLIC + " BOOLEAN, " + POST_FOREIGN_SPECIE_ID + " INTEGER, " + POST_FOREIGN_USER_ID +" INTEGER)");
        db.execSQL("create table " + POST_VIEWS_TABLE +"("+ POST_VIEW_ID +" INTEGER PRIMARY KEY AUTOINCREMENT, " + POST_VIEW_POST_ID + " INTEGER, " + POST_VIEW_USER_ID + " INTEGER, " + POST_VIEW_DATE + " DATE)");
        db.execSQL("create table " + POST_LIKES_TABLE +"("+ POST_LIKE_ID +" INTEGER PRIMARY KEY AUTOINCREMENT, " + POST_LIKE_POST_ID + " INTEGER, " + POST_LIKER_ID + " INTEGER," + POST_LIKE_DATE + " DATE)");
        db.execSQL("create table " + POST_COMMENTS_TABLE +"(" + POST_COMMENT_ID +" INTEGER PRIMARY KEY , " + POST_COMMENT_POST_ID + " INTEGER," + POST_COMMENTOR_ID + " INTEGER," + POST_COMMENT_TEXT +" TEXT, " +
                POST_COMMENT_DATE + " DATE)");
        db.execSQL("INSERT INTO USER VALUES('999', 'Super_User', 'Super_UserName', 'Super_UserSurName', '21', 'super', 'pwd')");
        db.execSQL("INSERT INTO USER VALUES('50', 'Fan_User', 'Fan_UserName', 'Fan_UserSurName', '15', 'fan', 'pwd')");
        db.execSQL("insert into specie values('1','Crow','Corbeau','No Description')");
        db.execSQL("insert into specie values('2','Peacock','Paon','No Description')");
        db.execSQL("insert into specie values('3','Dove','Colombe','No Description')");
        db.execSQL("insert into specie values('4','Sparrow','Moineau','No Description')");
        db.execSQL("insert into specie values('5','Goose','Oie','No Description')");
        db.execSQL("insert into specie values('6','Ostrich',' Autruche','No Description')");
        db.execSQL("insert into specie values('7','Pigeon','Pigeon','No Description')");
        db.execSQL("insert into specie values('8','Turkey','Dinde','No Description')");
        db.execSQL("insert into specie values('9','Hawk','Faucon','No Description')");
        db.execSQL("insert into specie values('10','Bald eagle','Pygargue à tête blanche','\tNo Description')");
        db.execSQL("insert into specie values('11','Raven','Corbeau','No Description');\n");
        db.execSQL("insert into specie values('12','Parrot','Perroquet','No Description');\n");
        db.execSQL("insert into specie values('13','Flamingo','Flamant','No Description');\n");
        db.execSQL("insert into specie values('14','Seagull','Mouette','No Description');\n");
        db.execSQL("insert into specie values('15','Swallow','Hirondelle','No Description');\n");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + USER_TABLE);
        onCreate(db);
    }

    public void modifyUserData(User connectedUser, User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(USER_NAME, user.getName());
        values.put(USER_PASSWORD, user.getPassword());
        values.put(USER_AGE, user.getAge());
        values.put(USER_MAIL, user.getMail());

        long insertResult = db.update(USER_TABLE, values, USER_ID + "=" + connectedUser.getId(), null);
        if(insertResult == -1){
            Log.d("DATABASE", "UPDATE HAS FAILED.");
        }
        else {
            Log.d("DATABASE", "UPDATE HAS SUCCEEDED.");
        }
    }


}
