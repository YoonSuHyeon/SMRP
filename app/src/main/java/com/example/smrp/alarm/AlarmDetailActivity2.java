package com.example.smrp.alarm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.smrp.R;
import com.example.smrp.RetrofitHelper;
import com.example.smrp.RetrofitService;
import com.example.smrp.medicine.ListViewItem;
import com.example.smrp.reponse_medicine2;

import java.io.Serializable;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AlarmDetailActivity2 extends AppCompatActivity implements Serializable {

    Context context;
    ImageView medicineImage;//약 사진
    ImageView iv_back; //뒤로가기 이미지뷰
    ImageView ic_dot;
    TextView medicineName,medicineEntpName,medicineChart,medicineClassName,medicineEtcOtcName,medicineEffect,medicineUsage;
    String itemSeq;
    private ArrayList<ListViewItem> listViewItemArrayList ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_detail2);
        context=this;
        //Init
        medicineImage=findViewById(R.id.iv_medicine);
        iv_back=findViewById(R.id.iv_back);

        ic_dot = findViewById(R.id.ic_dot);

        medicineName=findViewById(R.id.tv_medicine_name) ;    //약이름
        medicineEntpName=findViewById(R.id.tv_entpName);//약 제조사
        medicineChart=findViewById(R.id.tv_chart);//약성상
        medicineClassName=findViewById(R.id.tv_className);//약분류
        medicineEtcOtcName=findViewById(R.id.tv_etcOtcName);//약구분
        medicineEffect=findViewById(R.id.tv_effect);//약효능효과
        medicineUsage=findViewById(R.id.tv_usage);//약용법용량

        //itemSeq 받는 과정
        Intent intent =getIntent();
        itemSeq =intent.getStringExtra("itemSeq");
        listViewItemArrayList = (ArrayList<ListViewItem>) intent.getSerializableExtra("listViewItemArrayList");
        Log.d("Zxcbzxcb",itemSeq);

        RetrofitService networkService= RetrofitHelper.getRetrofit().create(RetrofitService.class);

        Call<reponse_medicine2> call = networkService.findmedicine(itemSeq);
        call.enqueue(new Callback<reponse_medicine2>() {
            @Override
            public void onResponse(Call<reponse_medicine2> call, Response<reponse_medicine2> response) {
                reponse_medicine2 reponse_medicine2 =response.body();
                //Image 등록
                Glide.with(context).load(reponse_medicine2.getItemImage()).override(1200,700).fitCenter().into(medicineImage);//이미지 등록
                medicineName.setText(reponse_medicine2.getItemName());
                medicineChart.setText(reponse_medicine2.getChart());
                medicineEntpName.setText(reponse_medicine2.getEntpName());
                medicineClassName.setText(reponse_medicine2.getClassName());
                medicineEtcOtcName.setText(reponse_medicine2.getEtcOtcName());
                medicineEffect.setText(reponse_medicine2.getEffect());
                medicineUsage.setText(reponse_medicine2.getUsage());
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

        ic_dot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { // dialog를 띄울 Activity에서 구현
                BottomSheetDialog2 bottomSheetDialog2 = BottomSheetDialog2.getInstance();
                bottomSheetDialog2.init("cc",itemSeq, listViewItemArrayList, AlarmDetailActivity2.this);
                bottomSheetDialog2.show(getSupportFragmentManager(),"bottomSheet");
            }
        });



    }
}
