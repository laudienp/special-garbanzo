package com.example.birdstagram.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.birdstagram.R;
import com.example.birdstagram.activities.MainActivity;
import com.example.birdstagram.data.tools.Post;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Locale;

public class PostFragment extends Fragment
{
    public PostFragment()
    {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstance)
    {
        final View rootView = inflater.inflate(R.layout.gallery_post, container, false);

        Button shareb = (Button)rootView.findViewById(R.id.shareButton);

        shareb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                Uri screenshotUri = Uri.parse("android.resource://com.example.birdstagram/drawable/" + R.drawable.colibri);

                sharingIntent.setType("image/*");
                sharingIntent.putExtra(Intent.EXTRA_STREAM, screenshotUri);
                startActivity(Intent.createChooser(sharingIntent, "Share image using"));

            }
        });

        Bundle bundle = getArguments();
        int index = bundle.getInt("index", -1);

        if(index != -1)
        {
            Post post = MainActivity.dataBundle.getAppPosts().get(index);

            TextView descText = rootView.findViewById(R.id.shortDescriptionPost);
            descText.setText(post.getDescription());

            TextView dateText = rootView.findViewById(R.id.datePost);
            dateText.setText(
                    new SimpleDateFormat("dd MMM yyyy", Locale.US).format(post.getDate())
            );
        }

        return rootView;
    }
}
