package com.example.birdstagram.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.birdstagram.R;
import com.example.birdstagram.activities.MapActivity;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class FragmentInfoBulle extends Fragment {

    private int id;
    private Button seen;
    private Button notSeen;

    private Float fiability;
    private TextView textFiability;

    public FragmentInfoBulle(){

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_infobulle, container, false);
        TextView textSpecie = view.findViewById(R.id.textSpecie);
        textFiability = view.findViewById(R.id.textFiability);
        TextView textAuthor = view.findViewById(R.id.textAuthor);

        id =  getArguments().getInt("id");
        String specie = getArguments().getString("specie");
        fiability = getArguments().getFloat("fiability");
        String author = getArguments().getString("author");

        textSpecie.setText("Specie: " + specie);
        textFiability.setText("Fiability: " + fiability + "%");
        textAuthor.setText("Author: " + author);


        ImageButton close = view.findViewById(R.id.quit);
        close.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                ((MapActivity) getActivity()).removeFragment();
            }
        });

        seen = view.findViewById(R.id.seen);
        seen.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                seen.setEnabled(false);
                notSeen.setEnabled(true);
                ((MapActivity) getActivity()).addView(true, id);
                fiability++;
                textFiability.setText("Fiability: " + fiability + "%");
            }
        });

        notSeen = view.findViewById(R.id.notSeen);
        notSeen.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                notSeen.setEnabled(false);
                seen.setEnabled(true);
                ((MapActivity) getActivity()).addView(false, id);
                fiability--;
                textFiability.setText("Fiability: " + fiability + "%");
            }
        });
        notSeen.setEnabled(false);

        return view;
    }
}
