package com.example.smrp.searchPrescription;


import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitFactory_takenpicture {
    private static String Base_url = "";
    static RetrofitService_takenpicture create(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Base_url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit.create(RetrofitService_takenpicture.class);
    }
}
