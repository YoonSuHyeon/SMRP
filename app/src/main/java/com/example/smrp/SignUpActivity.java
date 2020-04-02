package com.example.smrp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class SignUpActivity extends AppCompatActivity {
    /* 이름, 아이디, 이메일, 비밀번호, 비밀번호 확인 객치(여기서 sua는 SignUpActivity의 앞글자를 딴 것이다.) */

    Button Btn_sua_singup; // 회원가입 버튼 객체
    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);




        Btn_sua_singup = findViewById(R.id.Btn_sua_signup);

        Btn_sua_singup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
}
