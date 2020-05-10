package com.example.birdstagram.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.birdstagram.R;

public class SettingActivity extends AppCompatActivity {
    static boolean displayNotif;
    static boolean displayGeneralNotif;
    static boolean displaySocialNotif;

    private Switch displayNotifButton;
    private Switch displayGeneralNotifButton;
    private Switch displaySocialNotifButton;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reglage);

        displayNotifButton = (Switch) findViewById(R.id.allNotif);
        displayNotifButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    setDisplayNotif(true);
                    Toast.makeText(getApplicationContext(),"Notifications will be displayed.",Toast.LENGTH_LONG).show();
                }
                else{
                    setDisplayNotif(false);
                    Toast.makeText(getApplicationContext(),"Notifications won't be displayed.",Toast.LENGTH_LONG).show();
                }
            }
        });

        displayGeneralNotifButton = (Switch) findViewById(R.id.generalNotif);
        displayGeneralNotifButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    setDisplayGeneralNotif(true);
                    Toast.makeText(getApplicationContext(),"General notifications will be displayed.",Toast.LENGTH_LONG).show();
                }
                else{
                    setDisplayGeneralNotif(false);
                    Toast.makeText(getApplicationContext(),"General notifications won't be displayed.",Toast.LENGTH_LONG).show();
                }
            }
        });

        displaySocialNotifButton = (Switch) findViewById(R.id.socialNotif);
        displaySocialNotifButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    setDisplaySocialNotif(true);
                    Toast.makeText(getApplicationContext(),"Social notifications will be displayed.",Toast.LENGTH_LONG).show();
                }
                else{
                    setDisplaySocialNotif(false);
                    Toast.makeText(getApplicationContext(),"Social notifications won't be displayed.",Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    boolean getDisplayNotif(){
        return displayNotif;
    }

    boolean getDisplayGeneralNotif(){
        return displayGeneralNotif;
    }

    boolean getDisplaySocialNotif(){
        return displaySocialNotif;
    }

    void setDisplayNotif(boolean bool) {
        displayNotif = bool;
    }

    void setDisplayGeneralNotif(boolean bool) {
        displayGeneralNotif = bool;
    }

    void setDisplaySocialNotif(boolean bool) {
        displaySocialNotif = bool;
    }
}
