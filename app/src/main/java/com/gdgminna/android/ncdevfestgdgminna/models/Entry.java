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
    public String phoneNumber;
    public String roomNumber;
    public String roomPartner;
    public String extraPerson;
    public Long dateAndTime;




    public Entry() {
        // Default constructor required for calls to DataSnapshot.getValue(Entry.class)
    }

    public Entry (String uid, String userName, String firstName, String lastName, String phoneNumber, String roomNumber, String roomPartner,
                  String extraPerson, Long dateAndTime) {
        this.uid = uid;
        this.userName = userName;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
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
        result.put("lastName", lastName);
        result.put("phoneNumber", phoneNumber);
        result.put("roomNumber", roomNumber);
        result.put("roomPartner", roomPartner);
        result.put("extraPerson", extraPerson);
        result.put("dateAndTime", ServerValue.TIMESTAMP);

        return result;
    }
}
