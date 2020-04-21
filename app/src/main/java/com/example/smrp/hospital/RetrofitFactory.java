package com.example.smrp.hospital;


import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitFactory {
    private static String BASE_URL="http://apis.data.go.kr/";//"http://222.113.57.91:8080/";
    static RetrofitService create(){
        Retrofit retrofit= new Retrofit.Builder().
                baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit.create(RetrofitService.class);
    }
}
