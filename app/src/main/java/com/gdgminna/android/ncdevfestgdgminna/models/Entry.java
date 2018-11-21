package com.gdgminna.android.ncdevfestgdgminna.models;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.ServerValue;

import java.util.HashMap;
import java.util.Map;

public class Entry {

    public String uid;
    public String userName;
    public String firstName;
    public String lastName;
    public String phonenumber;
    public String roomNumber;
    public String roomPartner;
    public String extraPerson;
    public Long dateAndTime;
    public Map<String, Boolean> stars = new HashMap<>();



    public Entry() {
        // Default constructor required for calls to DataSnapshot.getValue(Book.class)
    }

    public Entry (String uid, String userName, String lastName, String firstName, String phoneNumber, String roomNumber, String roomPartner,
                  String extraPerson, Long dateAndTime) {
        this.uid = uid;
        this.userName = userName;
        this.lastName = lastName;
        this.firstName = firstName;
        this.phonenumber = phoneNumber;
        this.roomNumber = roomNumber;
        this.roomPartner = roomPartner;
        this.extraPerson = extraPerson;
        this.dateAndTime = dateAndTime;
    }

    // [START post_to_map]
    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("uid", uid);
        result.put("userName", userName);
        result.put("firstName", firstName);
        result.put("LastName", lastName);
        result.put("phomeNumber", phonenumber);
        result.put("roomNumber", roomNumber);
        result.put("roomPartner", roomPartner);
        result.put("extraPerson", extraPerson);
        result.put("dateAndTime", ServerValue.TIMESTAMP);

        return result;
    }
}
