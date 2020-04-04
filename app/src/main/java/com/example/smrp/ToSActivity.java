package com.example.smrp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class ToSActivity extends AppCompatActivity {
    Context context; //this 객체
    Button btn_agree; // 동의 버튼 객체
    ImageView iv_back; //뒤로가기 이미지
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_s);

        //Init
        context=this;
        btn_agree=findViewById(R.id.btn_agree);
        btn_agree.setOnClickListener(new View.OnClickListener() { //동의 버튼  클릭시
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(context,SignUpActivity.class);
                startActivity(intent);
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
