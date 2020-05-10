package com.example.birdstagram.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import com.example.birdstagram.R;

public class LocateBirdActivity extends AppCompatActivity {

    public static Bitmap lastImage;
    private ImageButton picture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.locate_a_bird);



        picture = findViewById(R.id.imageButton);
        picture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                startActivityForResult(intent, MainActivity.REQUEST_IMAGE_CAPTURE);
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
}
