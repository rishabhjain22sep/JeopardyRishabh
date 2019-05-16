 package com.example.gosecure;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.squareup.picasso.Picasso;

 public class LeaderboardActivity extends AppCompatActivity {
    private String TAG ="LeaderboardWarning";
    private RecyclerView mLeaderList;
    private DatabaseReference mDatabase;
    private Query mQuery;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboard);

        mDatabase = FirebaseDatabase.getInstance().getReference().child("Users");
        mQuery =mDatabase.orderByChild("order");


        mLeaderList = (RecyclerView) findViewById(R.id.leaderlist);
        mLeaderList.setHasFixedSize(true);
        mLeaderList.setLayoutManager(new LinearLayoutManager(this));
    }

     @Override
     protected void onStart() {
         super.onStart();
         FirebaseRecyclerOptions<Leader> options =
                 new FirebaseRecyclerOptions.Builder<Leader>()
                         .setQuery(mQuery, Leader.class)
                         .build();



         FirebaseRecyclerAdapter adapter = new FirebaseRecyclerAdapter<Leader, LeaderViewHolder>(options) {
             @Override
             public LeaderViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                 View view = LayoutInflater.from(parent.getContext())
                         .inflate(R.layout.leader_row, parent, false);

                 return new LeaderViewHolder(view);
             }

             @Override
             protected void onBindViewHolder(LeaderViewHolder holder, int position, Leader model) {
                 holder.setName(model.getName());
                 holder.setJeopardyPoints(model.getJeopardyPoints());
                 holder.setImage(model.getImage());
             }
         };
         adapter.startListening();
         mLeaderList.setAdapter(adapter);
     }

     public static class LeaderViewHolder extends RecyclerView.ViewHolder
    {
        View mView;
        public LeaderViewHolder(@NonNull View itemView) {
            super(itemView);
            mView = itemView;
        }

        public void setName(String name)
        {
            TextView post_title = (TextView)mView.findViewById(R.id.post_title);
            post_title.setText(name);
        }

        public void setJeopardyPoints(int desc)
        {
            TextView post_text = (TextView)mView.findViewById(R.id.post_text);
            String pointString = "Points: "+desc;
            post_text.setText(pointString);
        }

        public void setImage(String image)
        {
            ImageView post_image = (ImageView)mView.findViewById(R.id.post_image);
            Picasso.get().load(image).into(post_image);
        }
    }
}
