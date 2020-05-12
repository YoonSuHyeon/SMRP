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

import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
    String respon; //서버 통신 응
    String tmpid="";
    String id;
    final private static String pt_id = "^[a-zA-Z]{1}[a-zA-Z0-9_]{4,11}$";
    final private static String pt_email = "^([a-z0-9_\\.-]+)@([\\da-z\\.-]+)\\.([a-z\\.]{2,6})$";
    final private static String pt_password = "^[a-z0-9_-]{6,18}$";
    final private static String pt_birth = "\\d{6}";
    //final private static String pt_name = " [^가-힣]$"; //지우지마 한글만 입력되게 해논거임<< 가상머신에서쓰려고 임의로 밑에 정규식씀
    final private static String pt_name ="^[a-zA-Z]{1}[a-zA-Z0-9_]{4,11}$";


    Matcher matcher;
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
                id=Txt_sua_id.getText().toString();


                if(id.matches(pt_id)){
                    RetrofitService networkService=RetrofitHelper.getRetrofit().create(RetrofitService.class);
                    Call<response> call = networkService.getId(id);

                    call.enqueue(new Callback<response>() {
                        @Override
                        public void onResponse(Call<response> call, Response<response> response) {
                            respon = response.body().getResponse();

                            Log.d("1234",respon);
                            if(respon.equals("Join ok")){
                                Toast.makeText(getApplicationContext(),"사용 가능한 아이디입니다.",Toast.LENGTH_SHORT).show();
                                tmpid =id;
                            }else{
                                Toast.makeText(getApplicationContext(),"이미 사용중인 아이디입니다.",Toast.LENGTH_SHORT).show();
                                respon="";
                                id="";
                            }
                            //Toast.makeText(getApplicationContext(),"사용 가능한 아이디입니다.",Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onFailure(Call<response> call, Throwable t) {
                            Log.d("ddd",t.toString());

                        }
                    });
                }else{
                    Toast.makeText(getApplicationContext(),"영문+숫자 5~12 이하로 입력해주세요",Toast.LENGTH_SHORT).show();
                }

            }
        });

        Btn_sua_signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sign_id,email,password,passwordCheck,name,gender,birth;
                sign_id = Txt_sua_id.getText().toString();
                email = Txt_sua_email.getText().toString();
                password= Txt_sua_password.getText().toString();
                passwordCheck = Txt_sua_passwordCheck.getText().toString();
                name = Txt_sua_name.getText().toString();
                birth=Txt_birth.getText().toString();

                if(!tmpid.equals("")&&tmpid.equals(sign_id)){
                    if(email.matches(pt_email)){
                        if(password.matches(pt_password)){
                            if(password.equals(passwordCheck)){
                                if(name.matches(pt_name)){
                                    if(birth.matches(pt_birth)){
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
                                                    respon = response.body().getResponse();
                                                    Log.d("12345",respon);
                                                }catch (NullPointerException e){
                                                    Log.d("d",e.toString());
                                                }


                                                Toast.makeText(getApplicationContext(),"회원가입 성공",Toast.LENGTH_SHORT).show();
                                                onBackPressed();
                                                //Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                                                //startActivity(intent);
                                            }

                                            @Override
                                            public void onFailure(Call<response> call, Throwable t) {
                                                Log.d("dddd",t.toString());
                                                //Toast.makeText(getApplicationContext(),"회원가입 실패",Toast.LENGTH_SHORT).show();
                                            }
                                        });
                                    }else{//생년월일이 숫자가 아닐시
                                        if(birth.length()==6){
                                            Toast.makeText(getApplicationContext(),"생년월일은 숫자만 입력해주세요.",Toast.LENGTH_SHORT).show();
                                        }else{
                                            Toast.makeText(getApplicationContext(),"생년월일 형식을 yymmdd로 입력해주세요.",Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                }else{//이름이 한글이 아닐경
                                    Toast.makeText(getApplicationContext(),"이름을 다시 확인해주세요.",Toast.LENGTH_SHORT).show();
                                }

                            }else{//패스워드가 일치하지 않을
                                Toast.makeText(getApplicationContext(),"패스워드가 다릅니다.",Toast.LENGTH_SHORT).show();
                        }
                        }else{//안전하지 않은 패스워드인 경우..
                            Toast.makeText(getApplicationContext(),"안전하지 않은 패스워드입니다.",Toast.LENGTH_SHORT).show();
                        }
                    }else{//123@abc.com같은 이메일 형식이 다른경우
                        Toast.makeText(getApplicationContext(),"이메일 형식이 아닙니다.",Toast.LENGTH_SHORT).show();
                    }
                }else{//id중복 안눌렀을 경우..
                    Toast.makeText(getApplicationContext(),"아이디 중복을 확인해주세요.",Toast.LENGTH_SHORT).show();
                }
                /*if(password.equals(passwordCheck)){ //비밀번호 같을 때
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
                                respon = response.body().getResponse();
                                Log.d("12345",respon);
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
                }*/


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
