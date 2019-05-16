package com.example.gosecure;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;


public class LessonFragment extends Fragment {
    public LessonFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_lesson, container, false);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Button lesson1 = (Button)view.findViewById(R.id.lesson1button);
        Button lesson2 = (Button)view.findViewById(R.id.lesson2button);
        Button lesson3 = (Button)view.findViewById(R.id.lesson3button);
        Button lesson4 = (Button)view.findViewById(R.id.lesson4button);
        lesson1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startTopic(1);
            }
        });

        lesson2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startTopic(2);
            }
        });

        lesson3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startTopic(3);
            }
        });

        lesson4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startTopic(4);
            }
        });

    }

    private void startTopic(int i) {
        Intent intent = new Intent(getActivity(), TopicActivity.class);
        intent.putExtra("lesson_number", i);
        startActivity(intent);
    }


}