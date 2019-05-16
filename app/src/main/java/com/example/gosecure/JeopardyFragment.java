package com.example.gosecure;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class JeopardyFragment extends Fragment {
    private DatabaseReference mDatabase;
    private Button topic1_1;
    private Button topic1_2;
    private Button topic1_3;
    private Button topic1_4;
    private Button topic1_5;
    private Button topic2_1;
    private Button topic2_2;
    private Button topic2_3;
    private Button topic2_4;
    private Button topic2_5;
    private Button topic3_1;
    private Button topic3_2;
    private Button topic3_3;
    private Button topic3_4;
    private Button topic3_5;
    private TextView score;
    private String TAG="CheckError";
    private FirebaseAuth mAuth;
    JeopardyListener activityCommander;

    public interface JeopardyListener{
        public void createquestion(int topic, int question);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof JeopardyListener) {
            activityCommander = (JeopardyListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement JeopardyListener");
        }
    }

    public JeopardyFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Users");
        mAuth = FirebaseAuth.getInstance();


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_jeopardy, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
         topic1_1 = view.findViewById(R.id.buttonfirst1);
         topic1_2 = view.findViewById(R.id.buttonfirst2);
         topic1_3 = view.findViewById(R.id.buttonfirst3);
         topic1_4 = view.findViewById(R.id.buttonfirst4);
         topic1_5 = view.findViewById(R.id.buttonfirst5);

         topic2_1 = view.findViewById(R.id.buttonsecond1);
         topic2_2 = view.findViewById(R.id.buttonsecond2);
         topic2_3 = view.findViewById(R.id.buttonsecond3);
         topic2_4 = view.findViewById(R.id.buttonsecond4);
         topic2_5 = view.findViewById(R.id.buttonsecond5);

         topic3_1 = view.findViewById(R.id.buttonthird1);
         topic3_2 = view.findViewById(R.id.buttonthird2);
         topic3_3 = view.findViewById(R.id.buttonthird3);
         topic3_4 = view.findViewById(R.id.buttonthird4);
         topic3_5 = view.findViewById(R.id.buttonthird5);
         score = view.findViewById(R.id.score);

        disableandenable();
        populatePointsView();
        topic1_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popquestion(1,1);
            }
        });

        topic1_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popquestion(1,1);
            }
        });

        topic1_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popquestion(1,2);
            }
        });
        topic1_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popquestion(1,3);
            }
        });
        topic1_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popquestion(1,4);
            }
        });
        topic1_5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popquestion(1,5);
            }
        });


        topic2_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popquestion(2,1);
            }
        });

        topic2_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popquestion(2,2);
            }
        });

        topic2_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popquestion(2,3);
            }
        });

        topic2_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popquestion(2,4);
            }
        });

        topic2_5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popquestion(2,5);
            }
        });



        topic3_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popquestion(3,1);
            }
        });
        topic3_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popquestion(3,2);
            }
        });
        topic3_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popquestion(3,3);
            }
        });
        topic3_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popquestion(3,4);
            }
        });
        topic3_5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popquestion(3,5);
            }
        });


    }


    private void popquestion(int i, int i1) {
        //updateProgress(i);
        activityCommander.createquestion(i,i1);
        //disableandenable();
    }




    private void populatePointsView() {
        final String user_id = mAuth.getCurrentUser().getUid();
        mDatabase.child(user_id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String points = dataSnapshot.child("jeopardyPoints").getValue().toString();
                score.setText("Score: "+points);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void disableandenable() {
        final String user_id = mAuth.getCurrentUser().getUid();
        mDatabase.child(user_id).child("jeopardyProgress").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int topic_1_progress = dataSnapshot.child("1").getValue(Integer.class);
                int topic_2_progress = dataSnapshot.child("2").getValue(Integer.class);
                int topic_3_progress = dataSnapshot.child("3").getValue(Integer.class);
                switch(topic_1_progress)
                {
                    case 1:
                        topic1_1.setEnabled(true);
                        break;

                    case 2:
                        topic1_2.setEnabled(true);
                        topic1_1.setEnabled(false);
                        break;
                    case 3:
                        topic1_3.setEnabled(true);
                        topic1_1.setEnabled(false);
                        topic1_2.setEnabled(false);
                        break;
                    case 4:
                        topic1_4.setEnabled(true);
                        topic1_1.setEnabled(false);
                        topic1_2.setEnabled(false);
                        topic1_3.setEnabled(false);
                        break;
                    case 5:
                        topic1_5.setEnabled(true);
                        topic1_1.setEnabled(false);
                        topic1_2.setEnabled(false);
                        topic1_3.setEnabled(false);
                        topic1_4.setEnabled(false);
                        break;
                    default:


                }
                switch(topic_2_progress)
                {
                    case 1:
                        topic2_1.setEnabled(true);
                        break;
                    case 2:
                        topic2_2.setEnabled(true);
                        topic2_1.setEnabled(false);
                        break;
                    case 3:
                        topic2_3.setEnabled(true);
                        topic2_1.setEnabled(false);
                        topic2_2.setEnabled(false);
                        break;
                    case 4:
                        topic2_4.setEnabled(true);
                        topic2_1.setEnabled(false);
                        topic2_2.setEnabled(false);
                        topic2_3.setEnabled(false);
                        break;
                    case 5:
                        topic2_5.setEnabled(true);
                        topic2_1.setEnabled(false);
                        topic2_2.setEnabled(false);
                        topic2_3.setEnabled(false);
                        topic2_4.setEnabled(false);
                        break;
                    default:


                }

                switch(topic_3_progress)
                {   case 1:
                    topic3_1.setEnabled(true);
                    break;
                    case 2:
                        topic3_2.setEnabled(true);
                        topic3_1.setEnabled(false);
                        break;
                    case 3:
                        topic3_3.setEnabled(true);
                        topic3_1.setEnabled(false);
                        topic3_2.setEnabled(false);
                        break;
                    case 4:
                        topic3_4.setEnabled(true);
                        topic3_1.setEnabled(false);
                        topic3_2.setEnabled(false);
                        topic3_3.setEnabled(false);
                        break;
                    case 5:
                        topic3_5.setEnabled(true);
                        topic3_1.setEnabled(false);
                        topic3_2.setEnabled(false);
                        topic3_3.setEnabled(false);
                        topic3_4.setEnabled(false);
                        break;
                    default:


                }

            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}