package com.example.smrp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatCheckBox;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.Toast;

public class ToSActivity extends AppCompatActivity {
    Context context; //this 객체
    Button btn_agree; // 동의 버튼 객체
    ImageView iv_back; //뒤로가기 이미지
    CheckBox checkAll,check1,check2,check3;  //전체동의 등... 체크박스
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_s);

        checkAll=findViewById(R.id.cb_All);
        check1=findViewById(R.id.cb_Check1);
        check2=findViewById(R.id.cb_Check2);
        check3=findViewById(R.id.cb_Check3);

        //Init
        context=this;
        btn_agree=findViewById(R.id.btn_agree);
        btn_agree.setOnClickListener(new View.OnClickListener() { //동의 버튼  클릭시
            @Override
            public void onClick(View v) {

                if(check1.isChecked()&&check2.isChecked()&&check3.isChecked()){
                    Intent intent =new Intent(context,SignUpActivity.class);
                    startActivity(intent);
                    finish();
                }else{
                    Toast.makeText(context,"모든 약관을 동의 해야합니다.", Toast.LENGTH_SHORT).show();
                }
            }
        });


        iv_back=findViewById(R.id.iv_back);
        iv_back.setOnClickListener(new View.OnClickListener() { //뒤로가기 이미지를 눌렀을때
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        checkAll.setOnClickListener(new CheckBox.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkAll.isChecked()){//모두 동의 true
                    check1.setChecked(true);
                    check2.setChecked(true);
                    check3.setChecked(true);
                    btn_agree.setBackgroundColor(Color.parseColor("#5769B1"));
                    btn_agree.setTextColor(Color.parseColor("#FFFFFF"));
                }else{
                    check1.setChecked(false);
                    check2.setChecked(false);
                    check3.setChecked(false);
                    btn_agree.setBackgroundColor(Color.parseColor("#D6D7D7"));
                    btn_agree.setTextColor(Color.parseColor("#404040"));
                }
            }
        });
        check1.setOnClickListener(new CheckBox.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(check1.isChecked()&&check2.isChecked()&&check3.isChecked()){
                    btn_agree.setBackgroundColor(Color.parseColor("#5769B1"));
                    btn_agree.setTextColor(Color.parseColor("#FFFFFF"));
                }else{
                    btn_agree.setBackgroundColor(Color.parseColor("#D6D7D7"));
                    btn_agree.setTextColor(Color.parseColor("#404040"));
                }

            }
        });
        check2.setOnClickListener(new CheckBox.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(check1.isChecked()&&check2.isChecked()&&check3.isChecked()){
                    btn_agree.setBackgroundColor(Color.parseColor("#5769B1"));
                    btn_agree.setTextColor(Color.parseColor("#FFFFFF"));
                }else{
                    btn_agree.setBackgroundColor(Color.parseColor("#D6D7D7"));
                    btn_agree.setTextColor(Color.parseColor("#404040"));
                }

            }
        });
        check3.setOnClickListener(new CheckBox.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(check1.isChecked()&&check2.isChecked()&&check3.isChecked()){
                    btn_agree.setBackgroundColor(Color.parseColor("#5769B1"));
                    btn_agree.setTextColor(Color.parseColor("#FFFFFF"));
                }else{
                    btn_agree.setBackgroundColor(Color.parseColor("#D6D7D7"));
                    btn_agree.setTextColor(Color.parseColor("#404040"));
                }

            }
        });






    }
}