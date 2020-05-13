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

    public void createNotificationChannelNewLike() {
        // Créer le NotificationChannel, seulement pour API 26+
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Add bird";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("New Like", name, importance);
            channel.setDescription("Display a notification when adding a bird on the map.");
            // Enregister le canal sur le système : attention de ne plus rien modifier après
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            Objects.requireNonNull(notificationManager).createNotificationChannel(channel);
        }
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
