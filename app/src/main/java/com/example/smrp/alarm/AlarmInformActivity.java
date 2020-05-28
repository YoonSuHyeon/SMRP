package com.example.smrp.alarm;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.smrp.R;
import com.example.smrp.RetrofitHelper;
import com.example.smrp.RetrofitService;
import com.example.smrp.reponse_medicine2;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AlarmInformActivity extends AppCompatActivity {
    Context context;
    ImageView iv_back; //뒤로가기 이미지뷰
    ImageView ic_dot;

    String itemSeq;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_inform);
        context=this;
        //Init

        iv_back=findViewById(R.id.iv_back);
        ic_dot = findViewById(R.id.ic_dot);


        //itemSeq 받는 과정
        Intent intent =getIntent();

      //  itemSeq =intent.getStringExtra("itemSeq");
      //  Log.d("Zxcbzxcb",itemSeq);

        /*(RetrofitService networkService= RetrofitHelper.getRetrofit().create(RetrofitService.class);

        Call<reponse_medicine2> call = networkService.findmedicine(itemSeq);
        call.enqueue(new Callback<reponse_medicine2>() {
            @Override
            public void onResponse(Call<reponse_medicine2> call, Response<reponse_medicine2> response) {
                reponse_medicine2 reponse_medicine2 =response.body();

            }

            @Override
            public void onFailure(Call<reponse_medicine2> call, Throwable t) {
                Log.d("ddd",t.toString());

            }
        });*/





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
                BottomSheetDialog2 bottomSheetDialog = BottomSheetDialog2.getInstance();
              //  bottomSheetDialog.init("cc",itemSeq);
                bottomSheetDialog.show(getSupportFragmentManager(),"bottomSheet");
            }
        });

    }
}
