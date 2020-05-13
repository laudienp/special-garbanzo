package com.example.birdstagram.activities.inscription;

import android.Manifest;
import android.app.Activity;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.birdstagram.R;
import com.example.birdstagram.activities.MainActivity;
import com.example.birdstagram.activities.MapActivity;
import com.example.birdstagram.data.tools.DataBundle;
import com.example.birdstagram.data.tools.Like;
import com.example.birdstagram.data.tools.Post;
import com.example.birdstagram.data.tools.User;
import com.example.birdstagram.tools.DatabaseHelper;

import java.util.ArrayList;

import static com.example.birdstagram.activities.MainActivity.BDD;

public class ProfileActivity extends AppCompatActivity {
    private static final int PICK_IMAGE = 2;
    private static final int CAMERA_REQUEST = 1888;
    private static final int MY_CAMERA_PERMISSION_CODE = 100;


    private ImageView imageProfile;
    private EditText name;
    private EditText age;
    private EditText email;
    private EditText password;
    private Button apply_Btn;
    private TextView subs;
    private TextView likes;
    private TextView posts;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

       initialise();

        imageProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    requestPermissions(new String[]{Manifest.permission.CAMERA}, MY_CAMERA_PERMISSION_CODE);
                } else {
                    Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE); // directly open camera
                    startActivityForResult(cameraIntent, CAMERA_REQUEST);
                    /*Intent intent = new Intent(Intent.ACTION_GET_CONTENT); //Directly choose from gallery
                    Intent intent2 = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI); //
                    intent.setType("image/*");
                    startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE);*/
                }
            }
        });

        apply_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Confirmation de l'utilisateur
                if (!name.getText().toString().isEmpty() && !age.getText().toString().isEmpty() && !email.getText().toString().isEmpty() && !password.getText().toString().isEmpty())
                    showMessage("Confirmation", "Apply  Changes ?");
                else
                    Toast.makeText(getApplicationContext(), "Fill all the fields please.", Toast.LENGTH_LONG).show();
            }
        });

        likes.setText(String.valueOf(connectedUserLikes()) + " likes");
        posts.setText(String.valueOf(connectedUserPost()) + " posts");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 2 && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();

            imageProfile.setBackgroundResource(0);
            imageProfile.setImageURI(selectedImage);
        }
        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            imageProfile.setBackgroundResource(0);
            imageProfile.setImageBitmap(photo);
        }
    }

    public void showMessage(String title, String message) {
        final User user = new User();
        user.setAge(Integer.parseInt(age.getText().toString()));
        user.setMail(email.getText().toString());
        user.setName(name.getText().toString());
        user.setPassword(password.getText().toString());
        user.setId(getConnectedUser().getId());

        androidx.appcompat.app.AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton("Oui", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                BDD.modifyUserData(getConnectedUser(), user);
                Toast.makeText(ProfileActivity.this, "Profil Mise à jour", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(ProfileActivity.this, MapActivity.class);
                startActivity(intent);
            }
        }).setNegativeButton("Non", null);
        builder.show();
    }

    private User getConnectedUser() {
        BDD = new DatabaseHelper(this);
        User user = new User();
        Cursor res = BDD.getAllUsers();

        return MainActivity.dataBundle.getUserSession();
        /*if (res.getCount() == 0) {
            Toast.makeText(getApplicationContext(), "Aucun Utlisateur trouvé", Toast.LENGTH_LONG).show();
        } else {
            while (res.moveToNext()) {
                String userID = res.getString(0);
                String userPseudo = res.getString(1);
                String userName = res.getString(2);
                String userSurname = res.getString(3);
                String userAge = res.getString(4);
                String userMail = res.getString(5);
                String userPassword = res.getString(6);
                if (connectedUser.getName().equals(userName) && connectedUser.getAge() == Integer.parseInt(userAge) && ){
                    user = new User(Integer.parseInt(userID), userPseudo, userName, userSurname, Integer.parseInt(userAge), userMail, userPassword);
                    break;
                }
            }

        }
        return user;*/
    }

    private void initialise(){
        imageProfile = findViewById(R.id.profil_image);
        name = findViewById(R.id.name_editView);
        age = findViewById(R.id.age_editView);
        email = findViewById(R.id.email_editView);
        password = findViewById(R.id.password_editView);
        apply_Btn = findViewById(R.id.apply_button);
        likes = findViewById(R.id.nb_of_likes);
        posts = findViewById(R.id.nb_of_posts);
    }

    private int connectedUserLikes(){
        ArrayList<Like> likes = MainActivity.dataBundle.getAppLikes();
        int nb_likes = 0;

        for (Like like : likes){
            if (like.getUserID().getId() == getConnectedUser().getId())
                nb_likes++;

        }
        return nb_likes;
    }

    private int connectedUserPost(){
        ArrayList<Post> posts = MainActivity.dataBundle.getAppPosts();
        int nb_posts = 0;

        for (Post post : posts){
            if (post.getUser().getId() == getConnectedUser().getId())
                nb_posts++;
        }
        return nb_posts;
    }

}
