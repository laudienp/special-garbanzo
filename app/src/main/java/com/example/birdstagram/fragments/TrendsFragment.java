package com.example.birdstagram.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.fragment.app.Fragment;

import com.example.birdstagram.R;

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
