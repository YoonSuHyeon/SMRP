package com.example.smrp.searchPrescription;

import java.util.ArrayList;

public class User_Select {
    private String userId;;
    ArrayList<String>itemSeq;

    public User_Select(String id, ArrayList<String>list){
        this.userId = id;
        this.itemSeq = list;
    }

    public String getId() {
        return userId;
    }

    public ArrayList<String> getList() {
        return itemSeq;
    }
}
