package com.example.smrp.home;


import com.example.smrp.hospital.RetrofitService;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitFactory_home {
    private static String BASE_URL="http://api.openweathermap.org/data/2.5/";
    static RetrofitService create(){
        Retrofit retrofit= new Retrofit.Builder().
                baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit.create(RetrofitService.class);
    }
}
