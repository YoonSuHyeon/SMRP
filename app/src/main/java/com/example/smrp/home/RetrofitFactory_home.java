package com.example.smrp.home;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitFactory_home {
    private static String BASE_URL="http://api.openweathermap.org/data/2.5/";
    static RetrofitService_home create(){
        Retrofit retrofit= new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit.create(RetrofitService_home.class);
    }
}
