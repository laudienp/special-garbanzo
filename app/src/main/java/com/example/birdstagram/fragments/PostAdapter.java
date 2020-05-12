package com.example.birdstagram.fragments;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.example.birdstagram.R;
import com.example.birdstagram.data.tools.Post;

public class PostAdapter extends ArrayAdapter<Post>
{

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        LayoutInflater inflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View postView = inflater.inflate(R.layout.gallery_list_element, parent, false);
        ImageView imageView = (ImageView)postView.findViewById(R.id.glistImage);

        if(convertView == null)
            imageView.setImageResource(R.drawable.colibri);
        else
            postView = (View)convertView;

        return postView;
    }

    public PostAdapter(Context context, Post[] values)
    {
        super(context, R.layout.gallery_list_element, values);
    }
}
