package com.example.gosecure;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

public class AccountActivity extends AppCompatActivity {

    private Button logOutButton;
    private FirebaseAuth mAuth;
    private int lesson_number;
    private int topic_number;
    private FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        Bundle topic_data = getIntent().getExtras();
        if(topic_data == null)
        {
            return;
        }
        lesson_number = topic_data.getInt("lesson_number");
        topic_number = topic_data.getInt("topic_number");
    }

}
