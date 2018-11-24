package com.gdgminna.android.ncdevfestgdgminna.activities;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.gdgminna.android.ncdevfestgdgminna.R;
import com.gdgminna.android.ncdevfestgdgminna.models.Entry;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MyDetailsActivity extends BaseActivity {

    private static final String TAG = "MyDetailsActivity";

    private DatabaseReference mPostReference;
    private ValueEventListener mPostListener;

    public TextView firstNameView;
    public TextView lastNameView;
    public TextView phoneNumberView;
    public TextView roomNumberView;
    private TextView roomPartner;
    public TextView extraPerson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Initialize Database
        final String userId = getUid();
        mPostReference = FirebaseDatabase.getInstance().getReference()
                .child("user-posts");
        mPostReference.keepSynced(true);


        // Initialize Views
        firstNameView = findViewById(R.id.firstName_Tv);
        lastNameView = findViewById(R.id.lastName_tv);
        phoneNumberView = findViewById(R.id.phoneNumber_tv);
        roomNumberView = findViewById(R.id.roomNumber_tv);
        roomPartner = findViewById(R.id.roomPartner_Tv);
        extraPerson = findViewById(R.id.extraPerson_Tv);

    }
//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

    @Override
    public void onStart() {
        super.onStart();

        // Add value event listener to the post
        // [START post_value_event_listener]
        ValueEventListener postListener = new ValueEventListener() {


            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get Entry object and use the values to update the UI
                Entry entry = dataSnapshot.getValue(Entry.class);
                // [START_EXCLUDE]
                firstNameView.setText(entry.firstName);
                lastNameView.setText(entry.lastName);
                phoneNumberView.setText(entry.phoneNumber);
                roomNumberView.setText(entry.roomNumber);
                roomPartner.setText(entry.roomPartner);
                extraPerson.setText(entry.extraPerson);
                // [END_EXCLUDE]
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Entry failed, log a message
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
                // [START_EXCLUDE]
                Toast.makeText(MyDetailsActivity.this, "Failed to load post.",
                        Toast.LENGTH_SHORT).show();
                // [END_EXCLUDE]
            }
        };
        mPostReference.addValueEventListener(postListener);
        // [END post_value_event_listener]

        // Keep copy of post listener so we can remove it when app stops
        mPostListener = postListener;
    }

    @Override
    public void onStop() {
        super.onStop();

        // Remove post value event listener
        if (mPostListener != null) {
            mPostReference.removeEventListener(mPostListener);
        }
    }
}