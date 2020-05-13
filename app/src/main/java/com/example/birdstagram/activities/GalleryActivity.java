package com.example.birdstagram.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.birdstagram.R;
import com.example.birdstagram.fragments.NotFoundFragment;
import com.example.birdstagram.fragments.PostFragment;
import com.example.birdstagram.fragments.TrendsFragment;

import java.util.Objects;


public class GalleryActivity extends AppCompatActivity {

    EditText editText;
    TrendsFragment trendsFragment;
    NotFoundFragment notFoundFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);
        enableNotifForFragment();
        editText = findViewById(R.id.search_gallery_src_text);

        loadTrendFrag();

        ImageButton search = (ImageButton) findViewById(R.id.search_gallery_button);

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Log.d("aaaaaaaaaa", "ddddddddddddddddddddddddddddddddddd");
                String text = editText.getText().toString();
                Log.d("aaaaaaaaaa", "text:'"+text+"'");
                if(text.equals(""))
                {
                    //load trend frag
                    loadTrendFrag();
                }
                else
                {
                    //rien trouver frag
                    loadNotFoundFrag();
                }
            }
        });
    }

    public void loadTrendFrag()
    {
        if(trendsFragment == null)
            trendsFragment = new TrendsFragment();

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_layout_gallery, trendsFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public void loadNotFoundFrag()
    {
        if(notFoundFragment == null)
            notFoundFragment = new NotFoundFragment();

        getSupportFragmentManager()
            .beginTransaction()
            .replace(R.id.frame_layout_gallery, notFoundFragment)
            .addToBackStack(null)
            .commit();
    }

    public void clearTrendFrag()
    {
        if(trendsFragment != null)
        {
            Log.d("dd", "deleted fragggggggg");
            getSupportFragmentManager().beginTransaction().remove(trendsFragment).commit();
        }
    }

    void enableNotifForFragment(){
        createNotificationChannelNewLike();
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
}
