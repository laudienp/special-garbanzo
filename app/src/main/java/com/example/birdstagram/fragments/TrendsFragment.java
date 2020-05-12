package com.example.birdstagram.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.fragment.app.Fragment;

import com.example.birdstagram.R;
import com.example.birdstagram.activities.MainActivity;
import com.example.birdstagram.data.tools.Post;

import java.util.List;

public class TrendsFragment extends Fragment
{
    PostFragment postFragment;

    public TrendsFragment()
    {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstance)
    {
        View rootView = inflater.inflate(R.layout.fragment_trends, container, false);

        ImageButton button = rootView.findViewById(R.id.trendButton1);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                loadPostFrag();
            }
        });

        List<Post> posts = MainActivity.dataBundle.getAppPosts();

        Log.d("POSTTS", "il y a " + posts.size());
        Post[] list = new Post[posts.size()];
        for(int i=0;i<posts.size();i++)
            list[i] = posts.get(i);

        PostAdapter adapter = new PostAdapter(rootView.getContext(), list);

        return rootView;
    }

    public void loadPostFrag()
    {
        if(postFragment == null)
            postFragment = new PostFragment();

        getFragmentManager()
                .beginTransaction()
                .replace(R.id.frame_layout_gallery, postFragment)
                .addToBackStack(null)
                .commit();
    }
}
