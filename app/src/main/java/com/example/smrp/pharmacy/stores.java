package com.example.smrp.pharmacy;


import com.google.gson.annotations.SerializedName;


public class stores{

    @SerializedName("addr") String addr;
    @SerializedName("code") String code;
    @SerializedName("created_at") String created_at;
    @SerializedName("lat") float lat;
    @SerializedName("lng") float lng;
    @SerializedName("name") String name;
    @SerializedName("remain_stat") String remain_stat;
    @SerializedName("stock_at") String stock_at;
    @SerializedName("type") String type;

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public void setLat(float lat) {
        this.lat = lat;
    }

    public void setLng(float lng) {
        this.lng = lng;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setRemain_stat(String remain_stat) {
        this.remain_stat = remain_stat;
    }

    public void setStock_at(String stock_at) {
        this.stock_at = stock_at;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAddr() {
        return addr;
    }

    public String getCode() {
        return code;
    }

    public String getCreated_at() {
        return created_at;
    }

    public float getLat() {
        return lat;
    }

    public float getLng() {
        return lng;
    }

    public String getName() {
        return name;
    }

    public String getRemain_stat() {
        return remain_stat;
    }

    public String getStock_at() {
        return stock_at;
    }

    public String getType() {
        return type;
    }
}
