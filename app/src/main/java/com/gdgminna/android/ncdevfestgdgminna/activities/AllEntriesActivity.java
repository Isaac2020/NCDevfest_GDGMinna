package com.gdgminna.android.ncdevfestgdgminna.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.gdgminna.android.ncdevfestgdgminna.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class AllEntriesActivity extends EntryListActivity {

    public AllEntriesActivity (){}
    @Override
    public Query getQuery(DatabaseReference databaseReference) {
        // [START recent_posts_query]
        // Last 100 posts, these are automatically the 100 most recent
        // due to sorting by push() keys
        Query recentPostsQuery = databaseReference.child("posts")
                .limitToFirst(20);
        // [END recent_posts_query]
        DatabaseReference scoresRef = FirebaseDatabase.getInstance().getReference("posts");
        scoresRef.keepSynced(true);

        return recentPostsQuery;
    }
}
