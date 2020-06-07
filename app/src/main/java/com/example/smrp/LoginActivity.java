package com.example.smrp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    Button Btn_login; //로그인 버튼 객체
    EditText Txt_id, Txt_password; // 아이디 입력 객체, 비밀번호 입력 객체
    String id="", password=""; // 사용자의 아이디와 비밀번호를 저장하는 변수
    TextView tv_findId;
    ImageView iv_back;
    String user_id,user_pass;
    boolean bool_login = true;
    CheckBox auto_lgoin;
    SharedPreferences loginInfromation;
    SharedPreferences.Editor editor;

    final RetrofitService retrofitService = RetrofitHelper.getRetrofit().create(RetrofitService.class);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);

        Txt_id = findViewById(R.id.Txt_id);//사용자 id텍스트 필드
        Txt_password = findViewById(R.id.Txt_password); //사용자 패스워드 테스트 필드
        Btn_login = findViewById(R.id.Btn_Innerlogin); //사용자계정 로그인 버튼
        tv_findId = findViewById(R.id.tv_findId); //사용자 ID찾기 버튼
        iv_back = findViewById(R.id.iv_back);//뒤로가기 버튼
        auto_lgoin = findViewById(R.id.auto_login); //자동로그인 checkbox버튼

        auto_lgoin.setChecked(false);

        auto_lgoin.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { //자동 로그인 체크박스
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Log.d("TAG", "isChecked: "+isChecked);
                bool_login = isChecked;
            }
        });
        loginInfromation = getSharedPreferences("setting",0);//자동로그인 시 사용자의 id와 password값을 교환하기 위해 setting xml파일 생성 및 (두번쨰 파라메터) 접근권한
        editor = loginInfromation.edit();



        user_id = loginInfromation.getString("id","");
        user_pass = loginInfromation.getString("password","");



        if(!user_id.equals("")&&!user_pass.equals("")){ //자동로그인 실시

            Txt_id.setText(user_id);//자동로그인시 사용자의 id 텍스트핑드의 자동로그인하는 계정 id값을 출력
            Txt_password.setText(user_pass);//자동로그인시 사용자의 password 텍스트핑드의 자동로그인하는 계정 password값을 출력
            auto_lgoin.setChecked(true); //자동 로그인 checkbox true

            editor.putString("id",user_id); //자동로그인을 하기 위해서 getString을 할 시 메모리에 해당 data가 한번사용 후 사라지기에 이를 다시 넣는다.
            editor.putString("password",user_pass); //getString한 id와 password값을 다시 넣는다.
            editor.commit(); //변경사항을 반영하고자하는 commit실행

            User user = new User(user_id,"",user_pass,"","","");
            Call<response> call = retrofitService.login(user);
            call.enqueue(new Callback<response>() {
                @Override
                public void onResponse(Call<response> call, Response<response> response) {

                    if(response.body().getResponse().equals("ok")){
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);
                        finish();

                    }
                }

                @Override
                public void onFailure(Call<response> call, Throwable t) {
                    Toast.makeText(getApplicationContext(),"로그인 실패: 아이디 및 비밀번호 확인해주세요.",Toast.LENGTH_SHORT).show();
                }
            });
        }



        Btn_login.setOnClickListener(new View.OnClickListener() {//로그인 버튼 누를시
            @Override
            public void onClick(View v) {
                 user_id =  Txt_id.getText().toString();
                 user_pass =  Txt_password.getText().toString();

                User user = new User(user_id,"",user_pass,"","",""); //서버에서 USER 클래스를 받기에 불필요한 매개변수가 들어가도 이해할것
                Call<response> call = retrofitService.login(user);
                call.enqueue(new Callback<response>() {
                    @Override
                    public void onResponse(Call<response> call, Response<response> response) {

                        if(response.body().getResponse().equals("ok")) {
                            if (bool_login) {//자동 로그인을 체크 하고 로그인 버튼을 누를시

                                editor.putString("id", Txt_id.getText().toString());
                                editor.putString("password", Txt_password.getText().toString());
                                editor.commit();
                                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<response> call, Throwable t) {
                        Toast.makeText(getApplicationContext(),"로그인 실패: 아이디 및 비밀번호 확인해주세요.",Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });

        tv_findId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, findIdActivity.class);
                startActivity(intent);

            }
        });

        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        id = String.valueOf(Txt_id.getText());
        password = String.valueOf(Txt_password.getText());

    }
}
