package com.example.birdstagram.activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.birdstagram.activities.inscription.ProfileActivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.birdstagram.R;

public class MenuActivity extends AppCompatActivity {
    boolean netWorkState;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu);

        recupNetworkState();
        setOnlineOrOffline();

        Button addBirdButton = findViewById(R.id.add);
        addBirdButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), LocateBirdActivity.class);
                shareNetworkState(intent);
                startActivity(intent);
            }
        });


/*        Button subscriptionButton = findViewById(R.id.subscription);
        subscriptionButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SignUpActivity.class);
                startActivity(intent);
            }
        }); */

        Button profilButton = findViewById(R.id.profil);
        profilButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ProfileActivity.class);
                startActivity(intent);
            }
        });

        Button galleryButton = findViewById(R.id.gallery);
        galleryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                Intent intent = new Intent(getApplicationContext(), GalleryActivity.class);
                startActivity(intent);
            }
        });

        ImageButton reglageButton = (ImageButton) findViewById(R.id.reglageIcon);
        reglageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SettingActivity.class);
                startActivity(intent);
            }
        });

        ImageButton mapButton = (ImageButton) findViewById(R.id.mapIcon);
        mapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MapActivity.class);
                startActivity(intent);
            }
        });
    }

    void recupNetworkState(){
        Intent intent = getIntent();
        netWorkState = intent.getBooleanExtra("network", netWorkState);
    }

    void shareNetworkState(Intent intent){
        Bundle bundleOnlineOffline = new Bundle();
        bundleOnlineOffline.putBoolean("network", netWorkState);
        intent.putExtras(bundleOnlineOffline);
    }

    void setOnlineOrOffline(){
        TextView onlineView = findViewById(R.id.onlineView);
        ImageButton mapButton = findViewById(R.id.mapIcon);
        if (netWorkState == true) {
            onlineView.setText("Online");
            onlineView.setTextColor(Color.GREEN);
        }
        else {
            onlineView.setText("Offline");
            onlineView.setTextColor(Color.RED);
            mapButton.setVisibility(View.GONE);
        }
    }
}