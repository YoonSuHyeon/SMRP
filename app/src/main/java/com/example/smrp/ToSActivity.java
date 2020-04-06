package com.example.smrp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatCheckBox;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;

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
            public void onClick(View v) { //동의하고 가입하기

                Intent intent =new Intent(context,SignUpActivity.class);
                startActivity(intent);
                finish();

            }
        });
        iv_back=findViewById(R.id.iv_back);
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });





    }
}
