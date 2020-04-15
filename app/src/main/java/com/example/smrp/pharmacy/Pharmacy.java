package com.example.smrp.pharmacy;

import java.util.HashMap;

//Pharmacy 클래스.
public class Pharmacy {
    private String addr; //주소
    private String created_at; //거리
    private String name;// 전화번호
    private String remain_stat; //약국 이름
    private String stock_at;
    private String type;
    private float latitude, longitude ;



    public Pharmacy(String addr, String created_at, float latitude, float longitude, String name, String remain_stat, String stock_at, String type) {
        this.addr = addr;
        this.created_at = created_at;
        this.latitude = latitude;
        this.longitude = longitude;
        this.name= name;
        this.remain_stat = remain_stat;
        this.stock_at = stock_at;
        this.type = type;
    }
    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRemain_stat() {
        return remain_stat;
    }

    public void setRemain_stat(String remain_stat) {
        this.remain_stat = remain_stat;
    }

    public String getStock_at() {
        return stock_at;
    }

    public void setStock_at(String stock_at) {
        this.stock_at = stock_at;
    }

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}