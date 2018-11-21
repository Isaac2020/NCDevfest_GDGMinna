package com.gdgminna.android.ncdevfestgdgminna.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.gdgminna.android.ncdevfestgdgminna.R;
import com.gdgminna.android.ncdevfestgdgminna.models.Entry;

public class EntryViewHolder extends RecyclerView.ViewHolder {

    public TextView firstNameView;
    public TextView lastNameView;
    public TextView phoneNumberView;
    public TextView roomNumberView;

    public EntryViewHolder(View itemView) {
        super(itemView);

        firstNameView = itemView.findViewById(R.id.firstName_Tv);
        lastNameView = itemView.findViewById(R.id.lastName_tv);
        phoneNumberView = itemView.findViewById(R.id.phoneNumber_tv);
        roomNumberView = itemView.findViewById(R.id.roomNumber_tv);
    }

    public void bindToPost(Entry entry) {

        firstNameView.setText(entry.firstName);
        lastNameView.setText(entry.lastName);
        phoneNumberView.setText(entry.phonenumber);
        roomNumberView.setText(entry.roomNumber);

    }
}
