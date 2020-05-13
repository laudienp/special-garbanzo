package com.example.birdstagram.activities;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Objects;

import static androidx.core.content.ContextCompat.getSystemService;


public class Notification extends AppCompatActivity {
    static boolean displayNotif;
    static boolean displayGeneralNotif;
    static boolean displaySocialNotif;

    Notification() {
        displayNotif = true;
        displayGeneralNotif = true;
        displaySocialNotif = true;
    }

    public static boolean getDisplayNotif(){
        return displayNotif;
    }

    public static boolean getDisplayGeneralNotif(){
        return displayGeneralNotif;
    }

    public static boolean getDisplaySocialNotif(){
        return displaySocialNotif;
    }

    public static void setDisplayNotif(boolean bool) {
        displayNotif = bool;
    }

    public static void setDisplayGeneralNotif(boolean bool) {
        displayGeneralNotif = bool;
    }

    public static void setDisplaySocialNotif(boolean bool) {
        displaySocialNotif = bool;
    }
}
