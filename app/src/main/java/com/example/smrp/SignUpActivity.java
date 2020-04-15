package com.example.smrp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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

        Btn_duplicate.setOnClickListener(new View.OnClickListener() {   //아이디 중복확인 부분
            @Override
            public void onClick(View v) {
                String id=Txt_sua_id.getText().toString();
                RetrofitService networkService=RetrofitHelper.getRetrofit().create(RetrofitService.class);
                Call<response> call = networkService.getId(id);

                call.enqueue(new Callback<response>() {
                    @Override
                    public void onResponse(Call<response> call, Response<response> response) {
                        Log.d("1234",response.body().getResponse());
                        Toast.makeText(getApplicationContext(),"사용 가능한 아이디입니다.",Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Call<response> call, Throwable t) {
                        Log.d("ddd",t.toString());

                    }
                });
            }
        });

        Btn_sua_signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id,email,password,passwordCheck,name,gender,birth;
                id = Txt_sua_id.getText().toString();
                email = Txt_sua_email.getText().toString();
                password= Txt_sua_password.getText().toString();
                passwordCheck = Txt_sua_passwordCheck.getText().toString();
                name = Txt_sua_name.getText().toString();
                birth=Txt_birth.getText().toString();
                if(password.equals(passwordCheck)){ //비밀번호 같을 때
                    if(Rdb_man.isChecked()){ //남자면 1 여자면 0 으로보냄
                        gender = "1";
                    }
                    else{
                        gender="0";
                    }
                    RetrofitService networkService=RetrofitHelper.getRetrofit().create(RetrofitService.class);
                    User user = new User(id,email,password,name,gender,birth);
                    Call<response> call = networkService.getUser(user);
                    call.enqueue(new Callback<response>() {
                        @Override
                        public void onResponse(Call<response> call, Response<response> response) {
                            try{
                                Log.d("12345",response.body().getResponse());
                            }catch (NullPointerException e){
                                Log.d("d",e.toString());
                            }


                            Toast.makeText(getApplicationContext(),"회원가입 성공",Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onFailure(Call<response> call, Throwable t) {
                            Log.d("dddd",t.toString());
                            //Toast.makeText(getApplicationContext(),"회원가입 실패",Toast.LENGTH_SHORT).show();
                        }
                    });

                }else{//비밀번호 다를때
                    Toast.makeText(getApplicationContext(),"비밀번호가 같지 않습니다.",Toast.LENGTH_SHORT).show();
                }


            }
        });

        /*RetrofitService networkService=RetrofitHelper.getRetrofit().create(RetrofitService.class);
        ItemModel itemModel = new ItemModel("23","bdh","25");
        Call<ItemModel> call = networkService.getPost(itemModel);

        call.enqueue(new Callback<ItemModel>() {
            @Override
            public void onResponse(Call<ItemModel> call, Response<ItemModel> response) {
                if(response.isSuccessful()){

                    Log.d("1234",response.body().getId());


                }

            }

            @Override
            public void onFailure(Call<ItemModel> call, Throwable t) {
                Log.d("ddd",t.toString());

            }

        });*/
    }
}
