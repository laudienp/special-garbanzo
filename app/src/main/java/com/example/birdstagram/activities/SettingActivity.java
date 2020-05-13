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
    private Switch displayNotifButton;
    private Switch displayGeneralNotifButton;
    private Switch displaySocialNotifButton;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reglage);



        displayNotifButton = (Switch) findViewById(R.id.allNotif);
        displayNotifButton.setChecked(Notification.getDisplayNotif());
        displayNotifButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Notification.setDisplayNotif(displayNotifButton.isChecked());
                if (isChecked){
                    Toast.makeText(getApplicationContext(),"Notifications will be displayed.",Toast.LENGTH_LONG).show();
                }
                else{
                    Toast.makeText(getApplicationContext(),"Notifications won't be displayed.",Toast.LENGTH_LONG).show();
                }
            }
        });

        displayGeneralNotifButton = (Switch) findViewById(R.id.generalNotif);
        displayGeneralNotifButton.setChecked(Notification.getDisplayGeneralNotif());
        displayGeneralNotifButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Notification.setDisplayGeneralNotif(displayGeneralNotifButton.isChecked());
                if (isChecked){
                    Toast.makeText(getApplicationContext(),"General notifications will be displayed.",Toast.LENGTH_LONG).show();
                }
                else{
                    Toast.makeText(getApplicationContext(),"General notifications won't be displayed.",Toast.LENGTH_LONG).show();
                }
            }
        });

        displaySocialNotifButton = (Switch) findViewById(R.id.socialNotif);
        displaySocialNotifButton.setChecked(Notification.getDisplaySocialNotif());
        displaySocialNotifButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Notification.setDisplaySocialNotif(displaySocialNotifButton.isChecked());
                if (isChecked){
                    Toast.makeText(getApplicationContext(),"Social notifications will be displayed.",Toast.LENGTH_LONG).show();
                }
                else{
                    Toast.makeText(getApplicationContext(),"Social notifications won't be displayed.",Toast.LENGTH_LONG).show();
                }
            }
        });

    }
}
