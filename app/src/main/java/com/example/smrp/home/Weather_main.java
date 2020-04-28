package com.example.smrp.home;

import com.google.gson.annotations.SerializedName;

public class Weather_main {

    @SerializedName("temp") private double temp;

    public double getTemp() {
        return temp;
    }

    public void setTemp(double temp) {
        this.temp = temp;
    }
}
