package com.example.smrp.pharmacy;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ItemModel {

    @SerializedName("stores") List<stores> list;
    public List<stores> getList(){return list;}



    @SerializedName("count") Integer count;
    public Integer getCount() {
        return count;
    }


   // public ItemModel getItemModel(){return ItemModel;}
}
