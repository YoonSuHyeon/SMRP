package com.example.smrp.hospital;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Items {
    @SerializedName("item") List<Item> list;
    public List<Item> getItemsList(){return list;}
}
