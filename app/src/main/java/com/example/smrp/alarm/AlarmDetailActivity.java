package com.example.smrp.alarm;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.smrp.R;
import com.example.smrp.RetrofitHelper;
import com.example.smrp.RetrofitService;
import com.example.smrp.reponse_medicine2;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AlarmDetailActivity extends AppCompatActivity {
    Context context;
    ImageView medicineImage;//약 사진
    ImageView iv_back; //뒤로가기 이미지뷰
    ImageView ic_dot;
    Button Btn_set;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_detail);
        context=this;
        //Init
        Btn_set = findViewById(R.id.btn_set);
        medicineImage=findViewById(R.id.iv_medicine);
        iv_back=findViewById(R.id.iv_back);
        ic_dot = findViewById(R.id.ic_dot);


        //itemSeq 받는 과정
        Intent intent =getIntent();
        String itemSeq =intent.getStringExtra("itemSeq");
        Log.d("Zxcbzxcb",itemSeq);

        RetrofitService networkService= RetrofitHelper.getRetrofit().create(RetrofitService.class);

        Call<reponse_medicine2> call = networkService.findmedicine(itemSeq);
        call.enqueue(new Callback<reponse_medicine2>() {
            @Override
            public void onResponse(Call<reponse_medicine2> call, Response<reponse_medicine2> response) {
                reponse_medicine2 reponse_medicine2 =response.body();
                //Image 등록
                Glide.with(context).load(reponse_medicine2.getItemImage()).override(300,400).fitCenter().into(medicineImage);

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

        //설정하기 버튼
        Btn_set.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//추가 하기 버튼을 눌렀을때 서버에게 현재 자기가 등록 한 약이 무엇이다라는 것을 알려준다.

            }
        });

        ic_dot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { // dialog를 띄울 Activity에서 구현
                BottomSheetDialog bottomSheetDialog = BottomSheetDialog.getInstance();
                bottomSheetDialog.show(getSupportFragmentManager(),"bottomSheet");
            }
        });

    }
}
