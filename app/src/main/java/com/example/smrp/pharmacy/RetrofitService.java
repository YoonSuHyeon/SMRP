package com.example.smrp.pharmacy;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RetrofitService {


    @GET("/corona19-masks/v1/storesByGeo/json?")//("/userInfo")
//dgsbjtCd
    Call<ItemModel> getList(@Query("lat") double lat, @Query("lng") double lng, @Query("m") Integer m);

}
