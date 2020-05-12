package com.example.smrp;


import java.util.List;

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

    @GET("/findId")
    Call<response> findId(
            @Query("name") String id,
            @Query("email") String email
    );

    @GET("/findPassword")
    Call<response> findPassword(
            @Query("name") String name,
            @Query("id") String id,
            @Query("email") String email
    );

    @GET("/medicine1/find")
    Call<List<reponse_medicine>> findList(
            @Query("shape") String shape,
            @Query("color") String color,
            @Query("formula") String formula,
            @Query("line") String line
    );

}
