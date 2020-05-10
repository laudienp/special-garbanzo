package com.example.birdstagram;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.birdstagram.fragments.NotFoundFragment;
import com.example.birdstagram.fragments.TrendsFragment;


public class GalleryActivity extends AppCompatActivity {

    EditText editText;
    TrendsFragment trendsFragment;
    NotFoundFragment notFoundFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);

        editText = findViewById(R.id.search_gallery_src_text);

        loadTrendFrag();

        Button shareb = (Button)findViewById(R.id.shareButton);

        shareb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                Uri screenshotUri = Uri.parse("android.resource://com.example.birdstagram/drawable/" + R.drawable.zemour);

                sharingIntent.setType("image/*");
                sharingIntent.putExtra(Intent.EXTRA_STREAM, screenshotUri);
                startActivity(Intent.createChooser(sharingIntent, "Share image using"));

            }
        });

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
}
