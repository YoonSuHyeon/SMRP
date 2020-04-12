package com.example.smrp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;

public class SignUpActivity extends AppCompatActivity {
    /* 이름, 아이디, 이메일, 비밀번호, 비밀번호 확인 객치(여기서 sua는 SignUpActivity의 앞글자를 딴 것이다.) */

    Context context;
    ImageView iv_back; //뒤로가기 이미지
    EditText Txt_sua_id; // 회원가입 페이지의 아이디 입력 칸
    EditText Txt_sua_email;  // 회원가입 페이지의 이메일 입력
    EditText Txt_sua_password;  // 회원가입 페이지의 패스워드 입력
    EditText Txt_sua_passwordCheck;// 회원가입 페이지의 패스워드 체크
    EditText Txt_sua_name; // 회원가입 페이지의 이름 입력 칸
    EditText Txt_birth; // 회원가입 페이지의 생년월일 입력 칸
    Button Btn_duplicate; // 회원가입 페이지의 아이디 중복 확인 버튼
    Button Btn_sua_signUp; // 회원가입 페이지의 회원가입 버튼
    RadioButton Rdb_man; // 회원가입 페이지의 남자 성별 체크 버튼
    RadioButton Rdb_woman; // 회원가입 페이지의 여자 성별 체크 버튼

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);



        context=this;
        Txt_sua_id = findViewById(R.id.Txt_sua_id);
        Txt_sua_email = findViewById(R.id.Txt_sua_email);
        Txt_sua_password = findViewById(R.id.Txt_sua_password);
        Txt_sua_passwordCheck = findViewById(R.id.Txt_sua_passwordCheck);
        Txt_sua_name  = findViewById(R.id.Txt_sua_name);
        Txt_birth = findViewById(R.id.Txt_birth);
        Btn_duplicate = findViewById(R.id.Btn_duplicate);
        Rdb_man = findViewById(R.id.Rdb_man);
        Rdb_woman = findViewById(R.id.Rdn_woman);
        Btn_sua_signUp = findViewById(R.id.Btn_sua_signUp);
        iv_back=findViewById(R.id.iv_back);



        Btn_sua_signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,LoginActivity.class);
                startActivity(intent);
                finish();

            }
        });


        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
}
