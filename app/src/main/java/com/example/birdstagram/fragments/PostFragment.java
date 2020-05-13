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
import com.example.birdstagram.data.tools.Like;
import com.example.birdstagram.data.tools.Post;
import com.example.birdstagram.tools.DatabaseHelper;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class PostFragment extends Fragment
{

    Post linkedPost;
    public PostFragment()
    {
        linkedPost = null;
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
            linkedPost = MainActivity.dataBundle.getAppPosts().get(index);

            TextView titleText = rootView.findViewById(R.id.userPost);//user
            titleText.setText(linkedPost.getUser().getPseudo());

            TextView specieText = rootView.findViewById(R.id.speciePost);//specie
            specieText.setText(linkedPost.getSpecie().getEnglishName());

            TextView descText = rootView.findViewById(R.id.shortDescriptionPost);//desc
            descText.setText(linkedPost.getDescription());

            TextView dateText = rootView.findViewById(R.id.datePost);//date
            dateText.setText(
                    new SimpleDateFormat("dd MMM yyyy", Locale.US).format(linkedPost.getDate())
            );

            TextView likeCounter = rootView.findViewById(R.id.likeCounter); //likes

            List<Like> allLikes = MainActivity.dataBundle.getAppLikes();
            int count=0;

            for (Like like :
                    allLikes) {
                if(like.getPostID().getId() == linkedPost.getId())
                    count++;
            }

            String likes = count + " like";

            likeCounter.setText(likes);
        }

        Button likeButton = rootView.findViewById(R.id.likeButton);

        likeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Like newlike = new Like(linkedPost, MainActivity.dataBundle.getUserSession(), new Date());
                MainActivity.BDD.insertDataLike(newlike);

                try {
                    MainActivity.fillDataBundle();
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }

                TextView likeCounter = rootView.findViewById(R.id.likeCounter);

                List<Like> allLikes = MainActivity.dataBundle.getAppLikes();
                int count=0;

                for (Like like :
                        allLikes) {
                    if(like.getPostID().getId() == linkedPost.getId())
                        count++;
                }

                String likes = count + " like";

                likeCounter.setText(likes);
            }
        });

        return rootView;
    }
}
