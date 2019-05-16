package com.example.gosecure;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;


public class JeopardyQuestionFragment extends Fragment {
    private TextView mPointsView;
    private TextView mScoreView;
    private TextView mQuestion;
    private EditText mAnswer;
    private Button mSubmitButton;
    int topic_number;
    int question_number;
    private DatabaseReference mDatabase;
    private DatabaseReference mRootReference;
    private FirebaseAuth mAuth;

    public JeopardyQuestionFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        topic_number = getArguments().getInt("topic");
        question_number = getArguments().getInt("question");

        mDatabase = FirebaseDatabase.getInstance().getReference().child("Users");
        mRootReference = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();




    }

    private void updateProgress(int j) {
        final int i =j;
        final String user_id = mAuth.getCurrentUser().getUid();
        mDatabase.child(user_id).child("jeopardyProgress").child(String.valueOf(i)).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int progress = Integer.valueOf(dataSnapshot.getValue().toString());
                progress = progress +1;
                mDatabase.child(user_id).child("jeopardyProgress").child(String.valueOf(i)).setValue(progress);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_jeopardy_question, container, false);


    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mScoreView = view.findViewById(R.id.mScoreView);
        mPointsView = view.findViewById(R.id.mPointsView);
        mQuestion = view.findViewById(R.id.mQuestion);
        mAnswer = view.findViewById(R.id.mAnswer);
        mSubmitButton = view.findViewById(R.id.mSubmitButtom);
        populateScore();
        populatePoints();
        populateQuestion();
        mSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateProgress(topic_number);
                checkanswer();
            }
        });





    }

    private void checkanswer() {
        final String user_answer = mAnswer.getText().toString();
        final String user_id = mAuth.getCurrentUser().getUid();
        mRootReference.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String points_string = dataSnapshot.child("Users").child(user_id).child("jeopardyPoints").getValue().toString();
                int points = Integer.valueOf(points_string);
                String correct_answer = dataSnapshot.child("Questions").child(Integer.toString(topic_number)).child(Integer.toString(question_number)).child("Answer").getValue().toString();
                int updated_points;
                if(correct_answer.equals(user_answer))
                {
                     updated_points = points+question_number*50;

                }
                else
                {
                     updated_points = points-question_number*50;
                }

                mRootReference.child("Users").child(user_id).child("jeopardyPoints").setValue(updated_points);
                getFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new JeopardyFragment()).commit();



            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });




    }

    private void populateQuestion() {
        DatabaseReference question_link = mRootReference.child("Questions").child(Integer.toString(topic_number)).child(Integer.toString(question_number));
        question_link.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String question = dataSnapshot.child("Question").getValue().toString();
                mQuestion.setText(question);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void populatePoints() {
        int value = question_number*50;
        mPointsView.setText("For points: "+Integer.toString(value));
    }

    private void populateScore() {
        final String user_id = mAuth.getCurrentUser().getUid();
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.hasChild(user_id))
                {
                    String points = dataSnapshot.child(user_id).child("jeopardyPoints").getValue().toString();
                    mScoreView.setText("Score:    "+points);
                    int order = 10000 - Integer.valueOf(points);
                    mDatabase.child(user_id).child("order").setValue(order);
                    mRootReference.child("Rankings").child(user_id).setValue(order);
                }

            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


}
