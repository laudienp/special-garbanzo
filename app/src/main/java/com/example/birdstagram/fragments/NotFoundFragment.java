package com.example.birdstagram.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.birdstagram.R;

public class NotFoundFragment extends Fragment
{
    public NotFoundFragment()
    {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstance)
    {
        View rootView = inflater.inflate(R.layout.fragment_not_found, container, false);

        return rootView;
    }
}
