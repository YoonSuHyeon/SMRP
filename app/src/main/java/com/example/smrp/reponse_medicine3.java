package com.example.smrp;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class reponse_medicine3 {
    @SerializedName("itemSeq")
    @Expose
    private String itemSeq;

    @SerializedName("itemName")
    @Expose
    private String itemName;

    @SerializedName("userId")
    @Expose
    private String userId;

    @SerializedName("createdAt")
    @Expose
    private String createdAt;

    @SerializedName("imageUrl")
    @Expose
    private String imageUrl;

    @SerializedName("entpName")
    @Expose
    private String entpName;

    public String getItemSeq() {
        return itemSeq;
    }

    public void setItemSeq(String itemSeq) {
        this.itemSeq = itemSeq;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getEntpName() {
        return entpName;
    }

    public void setEntpName(String entpName) {
        this.entpName = entpName;
    }
}
