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
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class DashboardFragment extends Fragment {

    private CircleImageView mDisplayImage;
    private TextView mDisplayName;
    private TextView mDisplayPoints;
    private TextView mDisplayRank;
    private CircleImageView mSetupProfile;
    private DatabaseReference mDatabase;
    private DatabaseReference mRootReference;
    private FirebaseAuth mAuth;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_dashboard, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mDisplayImage = view.findViewById(R.id.display_image);
        mDisplayName = view.findViewById(R.id.display_name);
        mDisplayPoints = view.findViewById(R.id.display_points);
        mSetupProfile = view.findViewById(R.id.setup_profile);
        mDisplayRank = view.findViewById(R.id.display_rank);
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Users");
        mRootReference = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        populateViews();
        mSetupProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToSetup();
            }
        });


    }

    private void goToSetup() {
        Intent intent = new Intent(getActivity(), SetupActivity.class);
        startActivity(intent);
    }

    private void populateViews() {
        final String user_id = mAuth.getCurrentUser().getUid();
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.hasChild(user_id))
                {
                     String name = dataSnapshot.child(user_id).child("name").getValue().toString();
                     String points = dataSnapshot.child(user_id).child("jeopardyPoints").getValue().toString();
                     mDisplayName.setText("Name:    "+name);
                     mDisplayPoints.setText("Points:    "+points);
                     String imageUrl = dataSnapshot.child(user_id).child("image").getValue().toString();
                     Picasso.get().load(imageUrl).into(mDisplayImage);

                    int order = 10000 - Integer.valueOf(points);
                    mDatabase.child(user_id).child("order").setValue(order);
                    mRootReference.child("Rankings").child(user_id).setValue(order);
                    }

            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        //Display rank
        mRootReference.child("Rankings").orderByValue().addChildEventListener(new ChildEventListener() {
            int count = 1;
            final String user_id = mAuth.getCurrentUser().getUid();

            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                if(dataSnapshot.getKey().equals(user_id))
                {
                    mDisplayRank.setText("Rank: "+count);
                }
                else
                {
                    count++;
                }

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }
}

