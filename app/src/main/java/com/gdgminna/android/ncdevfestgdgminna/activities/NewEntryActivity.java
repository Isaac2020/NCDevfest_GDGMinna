package com.gdgminna.android.ncdevfestgdgminna.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.gdgminna.android.ncdevfestgdgminna.R;
import com.gdgminna.android.ncdevfestgdgminna.models.Entry;
import com.gdgminna.android.ncdevfestgdgminna.models.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class NewEntryActivity extends BaseActivity {
    private static final String TAG = "NewEntryActivity";
    private static final String REQUIRED = "Required";

    // [START declare_database_ref]
    private DatabaseReference mDatabase;
    // [END declare_database_ref]

    private EditText mFirstNameField;
    private EditText mLastNameField;
    private EditText mPhoneNumberField;
    private EditText mRoomNumberField;
    private EditText mRoomPartnerField;
    private EditText mExraPersonField;
    private Button mSubmitButton;
    private Button mCancelButton;
    private Long mdateAndTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_entry);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // [START initialize_database_ref]
       mDatabase = FirebaseDatabase.getInstance().getReference();

        // [END initialize_database_ref]


        mFirstNameField = findViewById(R.id.firstName_field);
        mLastNameField = findViewById(R.id.lastName_field);
        mPhoneNumberField = findViewById(R.id.phoneNumber_field);
        mRoomNumberField = findViewById(R.id.roomNumber_field);
        mRoomPartnerField = findViewById(R.id.roomPartner_field);
        mExraPersonField = findViewById(R.id.extraPerson_field);
        mCancelButton = findViewById(R.id.cancleBT);
        mSubmitButton = findViewById(R.id.submitBT);

        mSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitPost();
            }
        });

        mCancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(NewEntryActivity.this, MainActivity.class));
            }
        });
    }
    private void submitPost() {
        final String firstName = mFirstNameField.getText().toString();
        final String lastName = mLastNameField.getText().toString();
        final String phoneNumber = mPhoneNumberField.getText().toString();
        final String roomNumber = mRoomNumberField.getText().toString();
        final String roomPartner = mRoomPartnerField.getText().toString();
        final String extraPerson = mExraPersonField.getText().toString();
        final Long dateAndTime = mdateAndTime;


        // First Name is required
        if (TextUtils.isEmpty(firstName)) {
            mFirstNameField.setError(REQUIRED);
            return;
        }

        // Last Name is required
        if (TextUtils.isEmpty(lastName)) {
            mLastNameField.setError(REQUIRED);
            return;
        }

        // Phone Number is required
        if (TextUtils.isEmpty(phoneNumber)) {
            mPhoneNumberField.setError(REQUIRED);
            return;
        }

        // Room Number is required
        if (TextUtils.isEmpty(roomNumber)) {
            mRoomNumberField.setError(REQUIRED);
            return;
        }

        // Disable button so there are no multi-posts
        setEditingEnabled(false);
        Toast.makeText(this, "Posting...", Toast.LENGTH_SHORT).show();

        // [START single_value_read]
        final String userId = getUid();
        mDatabase.child("users").child(userId).addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        // Get user value
                        User user = dataSnapshot.getValue(User.class);

                        // [START_EXCLUDE]
                        if (user == null) {
                            // User is null, error out
                            Log.e(TAG, "users" + userId + " is unexpectedly null");
                            Toast.makeText(NewEntryActivity.this,
                                    "Error: could not fetch user.",
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            // Write new post
                            writeNewPost(userId, user.username, firstName, lastName, phoneNumber, roomNumber, roomPartner, extraPerson, dateAndTime );
                        }

                        // Finish this Activity, back to the stream
                        setEditingEnabled(true);
                        finish();
                        // [END_EXCLUDE]
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.w(TAG, "getUser:onCancelled", databaseError.toException());
                        // [START_EXCLUDE]
                        setEditingEnabled(true);
                        // [END_EXCLUDE]
                    }
                });
        // [END single_value_read]
    }

    private void setEditingEnabled(boolean enabled) {
        mFirstNameField.setEnabled(enabled);
        mLastNameField.setEnabled(enabled);
        mPhoneNumberField.setEnabled(enabled);
        mRoomNumberField.setEnabled(enabled);
        mRoomPartnerField.setEnabled(enabled);
        mExraPersonField.setEnabled(enabled);
        if (enabled) {
            mSubmitButton.setVisibility(View.VISIBLE);
        } else {
            mSubmitButton.setVisibility(View.GONE);
        }
    }

    // [START write_fan_out]
    private void writeNewPost(String userId, String username, String lastName, String firstName, String phoneNumber, String roomNumber, String roomPartner,
                              String extraPerson, Long dateAndTime) {
        // Create new post at /user-posts/$userid/$postid and at
        // /posts/$postid simultaneously
        String key = mDatabase.child("posts").push().getKey();
        Entry post = new Entry (userId, username, lastName, firstName, phoneNumber, roomNumber, roomPartner, extraPerson, dateAndTime );
        Map<String, Object> postValues = post.toMap();

        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("/posts/" + key, postValues);
        childUpdates.put("/user-posts/" + userId + "/" + key, postValues);

        mDatabase.updateChildren(childUpdates);
    }
}
