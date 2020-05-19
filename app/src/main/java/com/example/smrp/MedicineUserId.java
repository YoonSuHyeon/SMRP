package com.example.smrp;

public class MedicineUserId {

    private String userId;
    private String itemSeq;

    public MedicineUserId(String userId, String itemSeq) {
        this.userId = userId;
        this.itemSeq = itemSeq;
    }
    public String getId() {
        return userId;
    }

    public void setId(String id) {
        this.userId = id;
    }

    public String getItemSeq() {
        return itemSeq;
    }

    public void setItemSeq(String itemSeq) {
        this.itemSeq = itemSeq;
    }



}
