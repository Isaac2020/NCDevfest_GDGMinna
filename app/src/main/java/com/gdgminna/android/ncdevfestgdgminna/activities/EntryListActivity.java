package com.gdgminna.android.ncdevfestgdgminna.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.gdgminna.android.ncdevfestgdgminna.R;
import com.gdgminna.android.ncdevfestgdgminna.models.Entry;
import com.gdgminna.android.ncdevfestgdgminna.viewholder.EntryViewHolder;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class EntryListActivity extends AppCompatActivity {

    private static final String TAG = "EntryListFragment";

    // [START define_database_reference]
    private DatabaseReference mDatabase;
    // [END define_database_reference]

    private FirebaseRecyclerAdapter<Entry, EntryViewHolder> mAdapter;
    private RecyclerView mRecycler;
    private LinearLayoutManager mManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entry_list);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mRecycler = findViewById(R.id.messages_list);
        mRecycler.setHasFixedSize(true);


        // Set up Layout Manager, reverse layout
            mManager = new LinearLayoutManager(getApplicationContext());
            mManager.setReverseLayout(true);
            mManager.setStackFromEnd(true);
            mRecycler.setLayoutManager(mManager);

        mDatabase = FirebaseDatabase.getInstance().getReference();
        // [END create_database_reference]

        // Set up FirebaseRecyclerAdapter with the Query
        Query postsQuery = getQuery(mDatabase);

        FirebaseRecyclerOptions options = new FirebaseRecyclerOptions.Builder<Entry>()
                .setQuery(postsQuery, Entry.class)
                .build();


        mAdapter = new FirebaseRecyclerAdapter<Entry, EntryViewHolder>(options) {

            @Override
            public EntryViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
                LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
                return new EntryViewHolder(inflater.inflate(R.layout.entry_item, viewGroup, false));
            }

            @Override
            protected void onBindViewHolder(EntryViewHolder viewHolder, int position, final Entry model) {
                final DatabaseReference postRef = getRef(position);

                // Set click listener for the whole post view
                final String postKey = postRef.getKey();
                viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Launch PostDetailActivity
                        Intent intent = new Intent(getApplicationContext(), DetailsActivity.class);
                        intent.putExtra(DetailsActivity.EXTRA_POST_KEY, postKey);
                        startActivity(intent);
                    }
                });
                viewHolder.bindToPost(model);
            }

        };
        mRecycler.setAdapter(mAdapter);

    }

    @Override
    public void onStart() {
        super.onStart();
        if (mAdapter != null) {
            mAdapter.startListening();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAdapter != null) {
            mAdapter.stopListening();
        }
    }

            public String getUid() {
                return FirebaseAuth.getInstance().getCurrentUser().getUid();
            }


    public Query getQuery(DatabaseReference databaseReference) {
        // [START recent_posts_query]
        // Last 100 posts, these are automatically the 100 most recent
        // due to sorting by push() keys
        Query recentPostsQuery = databaseReference.child("posts")
                .limitToFirst(20);
        recentPostsQuery.keepSynced(true);
        // [END recent_posts_query]

        return recentPostsQuery;
    }
}



