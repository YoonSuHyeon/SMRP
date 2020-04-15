package com.example.smrp;


import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface RetrofitService {

    @GET("/userId")
    Call<response> getId(
            @Query("id") String id
    );

    @POST("/userRegister")
    Call<response> getUser(
            @Body User user
    );
}
