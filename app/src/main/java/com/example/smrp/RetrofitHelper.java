package com.example.smrp;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitHelper {
    static public Retrofit getRetrofit(){
        return new Retrofit.Builder().baseUrl("http://222.113.57.91:8080/").
                addConverterFactory(GsonConverterFactory.create()).build();
    }
}
