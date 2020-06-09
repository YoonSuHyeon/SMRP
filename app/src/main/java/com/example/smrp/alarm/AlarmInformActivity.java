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
    TextView Txt_alarmName,Txt_oneTimeDose,Txt_oneTimeCapacity,Txt_dosingPeriod,Txt_type;
    Long alramGroupId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_inform);
        context=this;
        //Init

        iv_back=findViewById(R.id.iv_back);
        ic_dot = findViewById(R.id.ic_dot);
        Txt_alarmName=findViewById(R.id.Txt_alarmName);
        Txt_oneTimeDose=findViewById(R.id.Txt_oneTimeDose);
        Txt_oneTimeCapacity=findViewById(R.id.Txt_oneTimeCapacity);
        Txt_dosingPeriod=findViewById(R.id.Txt_dosingPeriod);
        Txt_type=findViewById(R.id.Txt_type);

        //itemSeq 받는 과정
        Intent intent =getIntent();
        alramGroupId=intent.getLongExtra("alramGroupId",0);

        RetrofitService networkService= RetrofitHelper.getRetrofit().create(RetrofitService.class);

        Call<Response_AlarmMedicine> call = networkService.getAlram(alramGroupId);
        call.enqueue(new Callback<Response_AlarmMedicine>() {
            @Override
            public void onResponse(Call<Response_AlarmMedicine> call, Response<Response_AlarmMedicine> response) {
                Response_AlarmMedicine response_alarmMedicine =response.body();

                Txt_alarmName.setText(response_alarmMedicine.getAlramName());
                Txt_oneTimeDose.setText(response_alarmMedicine.getOneTimeDose().toString());
                Txt_oneTimeCapacity.setText(response_alarmMedicine.getOneTimeCapacity().toString());
                Txt_dosingPeriod.setText(response_alarmMedicine.getDosingPeriod().toString());
                Txt_type.setText(response_alarmMedicine.getDoseType());


            }

            @Override
            public void onFailure(Call<Response_AlarmMedicine> call, Throwable t) {


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
                BottomSheetDialog2 bottomSheetDialog = BottomSheetDialog2.getInstance();

                bottomSheetDialog.init(alramGroupId);
                bottomSheetDialog.show(getSupportFragmentManager(),"bottomSheet");
            }
        });

    }
}
