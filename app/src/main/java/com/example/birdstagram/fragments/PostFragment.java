package com.example.birdstagram.fragments;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.icu.util.Calendar;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;

import com.example.birdstagram.R;
import com.example.birdstagram.activities.MainActivity;
import com.example.birdstagram.activities.MapActivity;
import com.example.birdstagram.activities.Notification;
import com.example.birdstagram.activities.SettingActivity;
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
    private final String CHANNEL_ID = "New Like";
    private final int NOTIFICATION_ID = 002;
    Notification notification;

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
            public void onClick(View v) {
                Like newlike = new Like(linkedPost, MainActivity.dataBundle.getUserSession(), new Date());
                MainActivity.BDD.insertDataLike(newlike);

                try {
                    MainActivity.fillDataBundle();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                TextView likeCounter = rootView.findViewById(R.id.likeCounter);

                List<Like> allLikes = MainActivity.dataBundle.getAppLikes();
                int count = 0;

                for (Like like :
                        allLikes) {
                    if (like.getPostID().getId() == linkedPost.getId())
                        count++;
                }

                String likes = count + " like";

                likeCounter.setText(likes);
                if (Notification.getDisplaySocialNotif() && Notification.getDisplayNotif()) {
                    sendNotificationChannel("Un nouveau like", "Position :" + newlike.getPostID().getDescription() + "     Bird :" + newlike.getPostID().getSpecie().getEnglishName(), CHANNEL_ID, 1, null);
                }
            }
        });

        return rootView;
    }

    public void sendNotificationChannel(String title, String message, String channelId, int priority, Bitmap image){
        Intent landingIntent = new Intent(getActivity(), MapActivity.class);

        if (title.equals("Nouvel oiseau ajouté")){
            landingIntent = new Intent(getActivity(), MapActivity.class);
            landingIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        }
        if (title.equals("Un nouveau like")){
            landingIntent = new Intent(getActivity(), PostFragment.class);
            landingIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        }

        PendingIntent pendingIntent = PendingIntent.getActivity(getActivity(), 0, landingIntent, PendingIntent.FLAG_ONE_SHOT);
        Date currentTime = Calendar.getInstance().getTime();
        String time = "";
        if (currentTime.getHours() < 10)
            time+= "0" + currentTime.getHours();
        else
            time+= currentTime.getHours();
        time+=":";
        if (currentTime.getMinutes() < 10)
            time+= "0" + currentTime.getMinutes();
        else
            time+=currentTime.getMinutes();


        NotificationCompat.Builder builder = new NotificationCompat.Builder(getActivity(), channelId)
                .setSmallIcon(R.drawable.bird)
                .setContentTitle(title +
                        "                                               "
                        + time)
                .setContentText(message)
                .setPriority(priority)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);

        if (title.equals("Nouvel oiseau ajouté") && image == null){
            builder.setStyle(new NotificationCompat.BigPictureStyle()
                    .bigPicture(BitmapFactory.decodeResource(getActivity().getResources(), R.drawable.bird_big))
                    .bigLargeIcon(null) );
        } else if (image != null){
            builder.setStyle(new NotificationCompat.BigPictureStyle()
                    .bigPicture(image)
                    .bigLargeIcon(null) );
        }

        NotificationManager mNotificationManager =
                (NotificationManager) getActivity().getSystemService(Context.NOTIFICATION_SERVICE);
        //mNotificationManager.notify(NOTIFICATION_ID, builder.build());
        mNotificationManager.notify(NOTIFICATION_ID, builder.build());
    }
}
