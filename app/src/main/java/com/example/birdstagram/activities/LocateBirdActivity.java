package com.example.birdstagram.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.birdstagram.R;
import com.example.birdstagram.data.tools.Post;
import com.example.birdstagram.data.tools.Specie;
import com.example.birdstagram.data.tools.User;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class LocateBirdActivity extends AppCompatActivity {

    public static Bitmap lastImage;
    private ImageButton picture;
    TextView currentLocation;
    Button validate;
    Spinner specieView;
    Specie specie;
    String description;
    EditText descriptionView;
    User user;
    Switch isPublicView;
    boolean isPublic;
    double longitude = 0;
    double latitude = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.locate_a_bird);
        //On récupère la longitude et la latitude
        Intent intent = getIntent();

        if(intent != null){
            longitude = intent.getDoubleExtra("longitude", 0);
            latitude = intent.getDoubleExtra("latitude", 0);
            user = intent.getParcelableExtra("user");
        }
        currentLocation = findViewById(R.id.currentLocation);
        currentLocation.setText("Longitude : " + longitude + "\nLatitude : " + latitude);

        picture = findViewById(R.id.imageButton);
        picture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                startActivityForResult(intent, MainActivity.REQUEST_IMAGE_CAPTURE);
            }
        });


        //Envoi des données à la BDD
        validate = findViewById(R.id.buttonValidate);
        validate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                descriptionView = findViewById(R.id.inputDescription);
                description = descriptionView.getText().toString();

                /*DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                Date current = Calendar.getInstance().getTime();
                String date = dateFormat.format(current);*/

                Date date = new Date();

                specieView = findViewById(R.id.spinner);
                specie = (Specie) specieView.getSelectedItem();

                isPublicView = findViewById(R.id.switchImageGallery);
                isPublicView.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (isChecked){
                            setIsPublic(true);
                            Toast.makeText(getApplicationContext(),"Publication will be public.",Toast.LENGTH_LONG).show();
                        }
                        else{
                            setIsPublic(false);
                            Toast.makeText(getApplicationContext(),"Publication won't be public.",Toast.LENGTH_LONG).show();
                        }
                    }
                });

                Post post = new Post(description, date, longitude, latitude, isPublic, specie, user);
                MainActivity.myDb.insertDataPost(post);

            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        if (lastImage!=null){
            picture.setImageBitmap(lastImage);
        }
    }

    @SuppressLint("MissingSuperCall")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == MainActivity.REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            lastImage = (Bitmap) extras.get("data");
            picture.setImageBitmap(lastImage);
        }
    }

    void setIsPublic(boolean bool){
        isPublic = bool;
    }
}
