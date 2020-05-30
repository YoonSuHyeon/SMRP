package com.example.smrp.pharmacy;

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
        this.addr = addr; //약국주소소
        this.created_at =created_at;// 입고시간
        this.latitude = latitude; //경도
        this.longitude = longitude; //위도
        this.name= name; //병원 이름
        this.remain_stat = remain_stat; //마스크 보유량
        this.stock_at = stock_at;//데이터 생성일자
        this.type = type;// (약국 , 우체국, 하나로마트)
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