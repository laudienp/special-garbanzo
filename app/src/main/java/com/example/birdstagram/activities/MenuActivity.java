package com.example.birdstagram.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.birdstagram.activities.inscription.ProfileActivity;

import androidx.appcompat.app.AppCompatActivity;

import com.example.birdstagram.R;

public class MenuActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu);

        Button addBirdButton = findViewById(R.id.add);
        addBirdButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), LocateBirdActivity.class);
                startActivity(intent);
            }
        });

        Button profilButton = findViewById(R.id.profil);
        profilButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ProfileActivity.class);
                startActivity(intent);
            }
        });
    }

}