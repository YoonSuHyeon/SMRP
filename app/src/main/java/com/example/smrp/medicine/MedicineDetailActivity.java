package com.example.smrp.medicine;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

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

    Button addMedicine; //추가하기 버튼
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicine_detail);

        //Init
        context=this;
        addMedicine=findViewById(R.id.btn_add);
        medicineImage=findViewById(R.id.iv_medicine);
        iv_back=findViewById(R.id.iv_back);



        //일련번호 ItemSeq 를 Intent로 받는다.  사진촬영이든,검색을 해서 든 .
        Intent intent =getIntent();
        String itemSeq =intent.getStringExtra("itemSeq");
        //Log.d("Zxcbzxcb",itemSeq);



        RetrofitService networkService= RetrofitHelper.getRetrofit().create(RetrofitService.class);

        Call<reponse_medicine2> call = networkService.findmedicine(itemSeq);
        call.enqueue(new Callback<reponse_medicine2>() { //약 의 내용을 서버에서 가져오는 과정
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

        //추가하기 버튼
        addMedicine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//추가 하기 버튼을 눌렀을때 서버에게 현재 자기가 등록 한 약이 무엇이다라는 것을 알려준다.  // userId 사용자 id    itemSeq  일련번호

                RetrofitService networkService=RetrofitHelper.getRetrofit().create(RetrofitService.class);
                MedicineUserId medicineUserId = new MedicineUserId("cc","201503211");
                Call<response> call = networkService.addMedicine(medicineUserId);
                call.enqueue(new Callback<response>() {
                    @Override
                    public void onResponse(Call<response> call, Response<response> response) {
                        try{

                            Log.d("12345",response.body().getResponse());

                        }catch (NullPointerException e){
                            Log.d("d",e.toString());
                        }

                        //Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                        //startActivity(intent);
                    }

                    @Override
                    public void onFailure(Call<response> call, Throwable t) {
                        Log.d("dddd",t.toString());
                        //Toast.makeText(getApplicationContext(),"회원가입 실패",Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });

    }
}
