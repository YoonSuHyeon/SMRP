package com.example.smrp.pharmacy;


import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitFactory {
    private static String BASE_URL="https://8oi9s0nnth.apigw.ntruss.com/";//"http://222.113.57.91:8080/";
    static RetrofitService create(){
        Retrofit retrofit= new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit.create(RetrofitService.class);
    }


}
