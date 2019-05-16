package com.example.gosecure;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class TopicActivity extends AppCompatActivity {

    TextView topic_1, topic_2, topic_3,topic_4,topic_5, lesson_description;
    int lesson_number;
    private DatabaseReference mDatabase;
    private DatabaseReference mRootref;

    static int total_topic_count = 0, topic_count_1 = 0, topic_count_2 =0, topic_count_3 = 0,topic_count_4 = 0,topic_count_5 = 0;
    ProgressBar lesson_progress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topic);

        Bundle lesson_data = getIntent().getExtras();
        if(lesson_data == null)
        {
            return;
        }
        lesson_number = lesson_data.getInt("lesson_number");
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Topic").child(String.valueOf(lesson_number));
        mRootref = FirebaseDatabase.getInstance().getReference().child("Lesson").child(String.valueOf(lesson_number));
        TextView lesson_heading = (TextView)findViewById(R.id.lessonHeading);
        lesson_heading.setText("Lesson Number "+lesson_number);
        topic_1 = (TextView)findViewById(R.id.topic1);
        topic_2 = (TextView)findViewById(R.id.topic2);
        topic_3 = (TextView)findViewById(R.id.topic3);
        topic_4 = (TextView)findViewById(R.id.topic4);
        topic_5 = (TextView)findViewById(R.id.topic5);
        lesson_progress = (ProgressBar)findViewById(R.id.lessonProgress);
        lesson_description = (TextView)findViewById(R.id.lessonDescription);
        populateViews();

    }

    private void populateViews() {
        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                topic_1.setText(dataSnapshot.child("1").getValue().toString());
                topic_2.setText(dataSnapshot.child("2").getValue().toString());
                topic_3.setText(dataSnapshot.child("3").getValue().toString());
                topic_4.setText(dataSnapshot.child("4").getValue().toString());
                topic_5.setText(dataSnapshot.child("5").getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        mRootref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                lesson_description.setText(dataSnapshot.getValue().toString());
                lesson_description.setTextSize(25);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    public void quizNavigator(View view)
    {
        Intent intent = new Intent(this, AccountActivity.class);
        intent.putExtra("lesson_number", lesson_number);
        startActivity(intent);
    }

    public void topic1Navigator(View view)
    {   topic_count_1 = 1;
        addToProgressBar();
        Intent intent = new Intent(this, AccountActivity.class);
        intent.putExtra("lesson_number", lesson_number);
        intent.putExtra("topic_number", 1);
        intent.putExtra("progress_bar_count", total_topic_count);
        startActivity(intent);
    }



    public void topic2Navigator(View view)
    {   topic_count_2 = 1;
        addToProgressBar();
        Intent intent = new Intent(this, AccountActivity.class);
        intent.putExtra("lesson_number", lesson_number);
        intent.putExtra("topic_number", 2);
        intent.putExtra("progress_bar_count", total_topic_count);
        startActivity(intent);
    }

    public void topic3Navigator(View view)
    {
        topic_count_3 = 1;
        addToProgressBar();
        Intent intent = new Intent(this, AccountActivity.class);
        intent.putExtra("lesson_number", lesson_number);
        intent.putExtra("topic_number", 3);
        startActivity(intent);
    }
    public void topic4Navigator(View view)
    {   topic_count_4 = 1;
        addToProgressBar();
        Intent intent = new Intent(this, AccountActivity.class);
        intent.putExtra("lesson_number", lesson_number);
        intent.putExtra("topic_number", 4);
        intent.putExtra("progress_bar_count", total_topic_count);
        startActivity(intent);
    }

    public void topic5Navigator(View view)
    {   topic_count_5 = 1;
        addToProgressBar();
        Intent intent = new Intent(this, AccountActivity.class);
        intent.putExtra("lesson_number", lesson_number);
        intent.putExtra("topic_number", 5);
        intent.putExtra("total_topic_count", total_topic_count);
        startActivity(intent);

    }
    void addToProgressBar()
    {
        total_topic_count=topic_count_1+topic_count_2+topic_count_3+topic_count_4+topic_count_5;
        total_topic_count = total_topic_count*20;
        lesson_progress.setMax(100);
        lesson_progress.setProgress(total_topic_count);

    }

}
