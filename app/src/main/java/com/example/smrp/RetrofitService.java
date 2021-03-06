package com.example.smrp;


import com.example.smrp.alarm.AlarmMedicine;
import com.example.smrp.alarm.Response_AlarmMedicine;
import com.example.smrp.home.HomeMedItem;
import com.example.smrp.inquiry.Send_Message;
import com.example.smrp.searchMed.SelectedItem;
import com.example.smrp.searchPrescription.User_Select;

import java.util.ArrayList;
import java.util.List;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface RetrofitService {

    @POST("/userInfo/login")
    Call<UserAlarm> login(
        @Body User userId
    );
    @GET("/userId")
    Call<response> getId(
            @Query("id") String id
    );
    @GET("/findPassword")
    Call<response> findPassword( //패스워드 검색
                                 @Query("name") String name,
                                 @Query("id") String id,
                                 @Query("email") String email
    );

    @POST("/findPassword/changePass")
    Call<response> changpasswd(
            @Body Change_Password change_password
    );
    @POST("medicine2/addAlram") //알람 추가
    Call<Long> addAlram(
            @Body AlarmMedicine alarmMedicine
    );

    @PATCH ("medicine2/updateAlram/{groupId}")
    Call<response> updateAlram(
            @Path("groupId") Long groupId,
            @Body AlarmMedicine alarmMedicine
    );
    @POST("/medicine2/find")
    Call<List<reponse_medicine2>> findList2(
            @Body SelectedItem selectedItem
    );

    @POST("/userRegister")
    Call<response> setUser(
            @Body User user
    );
    @POST("/medicineRegister")
    Call<response>addMedicine(
            @Body MedicineUserId medicineUserId
    );

    @GET("medicine2/getRank") //Rank
    Call<ArrayList<HomeMedItem>>getRank(
    );
    @GET("medicine2/getAlramList") //유저 알람 가져오기
    Call<ArrayList<Response_AlarmMedicine>> getList(
            @Query("userId") String userId

    );

    @GET("medicine2/getAlram") //알람 가져오기
    Call<Response_AlarmMedicine>getAlram(
            @Query("groupId") Long groupId
    );
    @DELETE("medicine2/deleteAlram")//알람삭제
    Call<String>deleteAlram(
            @Query("groupId") Long groupId
    );

    @GET("/medicineRegister")
    Call<List<reponse_medicine3>>findUserMedicine(
            @Query("userId")String userId
    );


    @GET("/findId")
    Call<response> findId( //아이디 검색
                           @Query("name") String id,
                           @Query("email") String email
    );



    @GET("/medicine1/find")
    Call<List<reponse_medicine>> findList( // 약 찾가
                                           @Query("shape") String shape,
                                           @Query("color") String color,
                                           @Query("formula") String formula,
                                           @Query("line") String line
    );
    @DELETE("/medicineRegister/delete")
    Call<String> deleteRegister(
            @Query("userId") String userId,
            @Query("itemSeq") String itemSeq
    );
    //@POST("/medicine1/find2")
    // Call<List<reponse_medicine>> getPill(
    //         @Body Pillname pillname
    // );
    @POST("/medicine2/getStringList")
    Call<ArrayList<reponse_medicine>> getPill(
            @Body Pillname pillname
    );
    @Multipart
    @POST("/medicine2/uploadImage")
    Call<String> uploadImage(@Part MultipartBody.Part files);

    @GET("/medicine2/findItemId")
    Call<reponse_medicine2> findmedicine(
            @Query("itemSeq") String itemSeq
    );

    @POST("/medicineRegister/ListType")
    Call<response>addSelectMedicine(
            @Body User_Select user_select
    );

    @POST("/inquiry/add")
    Call<response>sendInquiry(
            @Body Send_Message send_message
    );




}