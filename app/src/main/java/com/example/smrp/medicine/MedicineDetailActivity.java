package com.example.smrp.medicine;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.smrp.MedicineUserId;
import com.example.smrp.R;
import com.example.smrp.RetrofitHelper;
import com.example.smrp.RetrofitService;
import com.example.smrp.reponse_medicine2;
import com.example.smrp.response;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MedicineDetailActivity extends AppCompatActivity {
    Context context;
    ImageView medicineImage;//약 사진
    ImageView iv_back; //뒤로가기 이미지뷰
    TextView medicineName,medicineEntpName,medicineChart,medicineClassName,medicineEtcOtcName,medicineEffect,medicineUsage;
    String itemSeq;
    Button addMedicine; //추가하기 버튼
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicine_detail);

        //Init
        context=this;
        medicineName=findViewById(R.id.tv_medicine_name) ;    //약이름
        medicineEntpName=findViewById(R.id.tv_entpName);//약 제조사
        medicineChart=findViewById(R.id.tv_chart);//약성상
        medicineClassName=findViewById(R.id.tv_className);//약분류
        medicineEtcOtcName=findViewById(R.id.tv_etcOtcName);//약구분
        medicineEffect=findViewById(R.id.tv_effect);//약효능효과
        medicineUsage=findViewById(R.id.tv_usage);//약용법용량

        addMedicine=findViewById(R.id.btn_add);//추가버튼
        medicineImage=findViewById(R.id.iv_medicine); //약이미지
        iv_back=findViewById(R.id.iv_back);//뒤로가기 버튼



        //일련번호 ItemSeq 를 Intent로 받는다.  사진촬영이든,검색을 해서 든 .
        Intent intent =getIntent();
        itemSeq =intent.getStringExtra("itemSeq");
        //Log.d("Zxcbzxcb",itemSeq);



        RetrofitService networkService= RetrofitHelper.getRetrofit().create(RetrofitService.class);

        Call<reponse_medicine2> call = networkService.findmedicine(itemSeq);
        call.enqueue(new Callback<reponse_medicine2>() { //약 의 내용을 서버에서 가져오는 과정
            @Override
            public void onResponse(Call<reponse_medicine2> call, Response<reponse_medicine2> response) {
                reponse_medicine2 reponse_medicine2 =response.body();
                //약에 대한 정보들을 넣어준다
                if(reponse_medicine2 == null){

                    medicineName.setText("다시 시도 해주세요");
                }else {
                    Glide.with(context).load(reponse_medicine2.getItemImage()).override(1200, 700).fitCenter().into(medicineImage);//이미지 등록
                    medicineName.setText(reponse_medicine2.getItemName());
                    medicineChart.setText(reponse_medicine2.getChart());
                    medicineEntpName.setText(reponse_medicine2.getEntpName());
                    medicineClassName.setText(reponse_medicine2.getClassName());
                    medicineEtcOtcName.setText(reponse_medicine2.getEtcOtcName());
                    medicineEffect.setText(reponse_medicine2.getEffect());
                    medicineUsage.setText(reponse_medicine2.getUsage());
                }
            }

            @Override
            public void onFailure(Call<reponse_medicine2> call, Throwable t) {
                Log.d("ddd",t.toString());

            }
        });


        //뒤로가기 버튼
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        //추가하기 버튼
        addMedicine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//추가 하기 버튼을 눌렀을때 서버에게 현재 자기가 등록 한 약이 무엇이다라는 것을 알려준다.  // userId 사용자 id    itemSeq  일련번호

                RetrofitService networkService=RetrofitHelper.getRetrofit().create(RetrofitService.class);
                MedicineUserId medicineUserId = new MedicineUserId("cc",itemSeq);
                Call<response> call = networkService.addMedicine(medicineUserId);
                call.enqueue(new Callback<response>() {
                    @Override
                    public void onResponse(Call<response> call, Response<response> response) {
                        try{

                            Log.d("123466665",response.body().getResponse());

                            if(response.body().getResponse().equals("ok")){
                                Log.d("zzzqqqq","okokok");
                            }else{
                                Log.d("Noqqqqqq","no");
                            }

                        }catch (NullPointerException e){
                            Log.d("nulle",e.toString());
                        }
                        onBackPressed();
                        finish();

                        //Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                        //startActivity(intent);
                    }

                    @Override
                    public void onFailure(Call<response> call, Throwable t) {
                        Log.d("dddd",t.toString());

                    }
                });

            }
        });

    }


}
