package com.example.smrp.searchPrescription;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RetrofitService_takenpicture {
    @GET("weather?APPID=49168c2b50d7dfa50b8e7a0054b1b229&lang=kr&units=metric")//
    Call<Text_Response> getList(@Query("lat") double lat, @Query("lon") double lon);
}
