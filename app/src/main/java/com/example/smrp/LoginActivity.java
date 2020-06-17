package com.example.smrp;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
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

import com.example.smrp.alarm.Alarm_Reciver;

import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    public static final String NOTIFICATION_CHANNEL_ID = "10001";
    Button Btn_login; //로그인 버튼 객체
    EditText Txt_id, Txt_password; // 아이디 입력 객체, 비밀번호 입력 객체
    String id="", password=""; // 사용자의 아이디와 비밀번호를 저장하는 변수
    TextView tv_findId;
    ImageView iv_back;
    String user_id="",user_pass="",getAutoLogin="";
    boolean bool_login = false;
    String name="";
    CheckBox auto_lgoin;
    SharedPreferences loginInfromation;
    SharedPreferences.Editor editor;

    int count=0;
    Calendar calendar ;
    Context context;
    AlarmManager alarmManager;
    final RetrofitService retrofitService = RetrofitHelper.getRetrofit().create(RetrofitService.class);

    public StartActivity startclass = (StartActivity) StartActivity.StartActivity;
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

        this.context=this;
        final Intent my_intent = new Intent(this.context, Alarm_Reciver.class);
        alarmManager=(AlarmManager)getSystemService(ALARM_SERVICE);
        calendar = Calendar.getInstance();


        user_id = loginInfromation.getString("id","");
        user_pass = loginInfromation.getString("password","");
        name = loginInfromation.getString("name","");
        getAutoLogin = loginInfromation.getString("auto_login","");
        Log.d("TAG", "user_id: "+user_id);
        Log.d("TAG", "user_pass: "+user_pass);
        Log.d("TAG","getAutoLogin"+getAutoLogin);
        if(getAutoLogin.equals("true")){ //자동로그인 실시 !user_id.equals("")&&!user_pass.equals("")
            Log.d("TAG", "onCreate: ");
            Txt_id.setText(user_id);//자동로그인시 사용자의 id 텍스트핑드의 자동로그인하는 계정 id값을 출력
            Txt_password.setText(user_pass);//자동로그인시 사용자의 password 텍스트핑드의 자동로그인하는 계정 password값을 출력
            auto_lgoin.setChecked(true); //자동 로그인 checkbox true

            /*editor.putString("id",user_id); //자동로그인을 하기 위해서 getString을 할 시 메모리에 해당 data가 한번사용 후 사라지기에 이를 다시 넣는다.
            editor.putString("password",user_pass); //getString한 id와 password값을 다시 넣는다.
            editor.putString("name",name);
            editor.commit(); //변경사항을 반영하고자하는 commit실행*/

            User user = new User(user_id,"",user_pass,"","","");
            Call<UserAlarm> call = retrofitService.login(user);
            call.enqueue(new Callback<UserAlarm>() {
                @Override
                public void onResponse(Call<UserAlarm> call, Response<UserAlarm> response) {
                    if (!response.body().getUserName().equals("empty")) {
                        if(response.body().getAlramMedicines().size() != 0){
                            Log.d("qweqwe", response.body().getAlramMedicines().get(0).getAlramName());
                        }else{
                            Log.d("qweqwe", "등록된 알람이없습니다.");
                        }

                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        intent.putExtra("name", name);
                        startActivity(intent);
                        finish();
                        startclass.finish();
                    } else {
                        Toast.makeText(getApplicationContext(), "로그인 실패: 아이디 및 비밀번호 확인해주세요.", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<UserAlarm> call, Throwable t) {
                    Toast.makeText(getApplicationContext(),"서버와 통신이 불안정합니다.",Toast.LENGTH_SHORT).show();
                }
            });
        }



        Btn_login.setOnClickListener(new View.OnClickListener() {//로그인 버튼 누를시
            @Override
            public void onClick(View v) { //로그인버튼 활성화
                user_id =  Txt_id.getText().toString();
                user_pass =  Txt_password.getText().toString();
                User user = new User(user_id,"",user_pass,"","",""); //서버에서 USER 클래스를 받기에 불필요한 매개변수가 들어가도 이해할것
                Call<UserAlarm> call = retrofitService.login(user);
                call.enqueue(new Callback<UserAlarm>() {
                    @Override
                    public void onResponse(Call<UserAlarm> call, Response<UserAlarm> response) {
                        if(!response.body().getUserName().equals("empty")){
                            name = response.body().getUserName();
                            editor.putString("id", user_id); //자동로그인시 ID 값 입력
                            editor.putString("password", user_pass); //자동로그인시 패스워드 값 입력
                            editor.putString("name",name); //자동로그인시 패스워드 이름 입력
                            if (bool_login) {//자동 로그인을 체크 하고 로그인 버튼을 누를시
                                editor.putString("auto_login", "true");
                            }else{ //자동로그인을 하지 않은 상태에서 로그인시
                                editor.putString("auto_login", "false");
                            }
                            editor.commit();


                            if(response.body().getAlramMedicines().size()!=0){
                                for(int i=0; i<response.body().getAlramMedicines().size(); i++){
                                    switch (response.body().getAlramMedicines().get(i).getOneTimeCapacity()){
                                        case 1:{
                                            if(response.body().getAlramMedicines().get(i).getDoseType().equals("식전")){
                                                calendar.set(Calendar.HOUR_OF_DAY,11);      //식전
                                                calendar.set(Calendar.MINUTE,00);
                                                if(calendar.before(Calendar.getInstance())){
                                                    calendar.add(Calendar.DATE, 1);
                                                }
                                                PendingIntent sender = PendingIntent.getBroadcast(LoginActivity.this,response.body().getAlramMedicines().get(i).getAlramGroupId(),my_intent,0);
                                                alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),24*60*60*1000,sender);
                                            }else{
                                                calendar.set(Calendar.HOUR_OF_DAY,13);      //식후
                                                calendar.set(Calendar.MINUTE,00);
                                                if(calendar.before(Calendar.getInstance())){
                                                    calendar.add(Calendar.DATE, 1);
                                                }
                                                PendingIntent sender = PendingIntent.getBroadcast(LoginActivity.this,response.body().getAlramMedicines().get(i).getAlramGroupId(),my_intent,0);
                                                alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),24*60*60*1000,sender);
                                            }

                                            break;
                                        }
                                        case 2:{
                                            if(response.body().getAlramMedicines().get(i).getDoseType().equals("식전")){
                                                calendar.set(Calendar.HOUR_OF_DAY,7);      //식전
                                                calendar.set(Calendar.MINUTE,00);
                                                if(calendar.before(Calendar.getInstance())){
                                                    calendar.add(Calendar.DATE, 1);
                                                }
                                                PendingIntent sender = PendingIntent.getBroadcast(LoginActivity.this,response.body().getAlramMedicines().get(i).getAlramGroupId(),my_intent,0);
                                                alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),24*60*60*1000,sender);

                                                calendar.set(Calendar.HOUR_OF_DAY,17);      //식전
                                                calendar.set(Calendar.MINUTE,00);
                                                if(calendar.before(Calendar.getInstance())){
                                                    calendar.add(Calendar.DATE, 1);
                                                }
                                                PendingIntent sender2 = PendingIntent.getBroadcast(LoginActivity.this,response.body().getAlramMedicines().get(i).getAlramGroupId()+100,my_intent,0);
                                                alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),24*60*60*1000,sender2);
                                            }else{
                                                calendar.set(Calendar.HOUR_OF_DAY,9);      //식후
                                                calendar.set(Calendar.MINUTE,00);
                                                if(calendar.before(Calendar.getInstance())){
                                                    calendar.add(Calendar.DATE, 1);
                                                }
                                                PendingIntent sender = PendingIntent.getBroadcast(LoginActivity.this,response.body().getAlramMedicines().get(i).getAlramGroupId(),my_intent,0);
                                                alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),24*60*60*1000,sender);

                                                calendar.set(Calendar.HOUR_OF_DAY,18);      //식후
                                                calendar.set(Calendar.MINUTE,00);
                                                if(calendar.before(Calendar.getInstance())){
                                                    calendar.add(Calendar.DATE, 1);
                                                }
                                                PendingIntent sender2 = PendingIntent.getBroadcast(LoginActivity.this,response.body().getAlramMedicines().get(i).getAlramGroupId()+100,my_intent,0);
                                                alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),24*60*60*1000,sender2);
                                            }
                                            break;
                                        }
                                        case 3:{
                                            if(response.body().getAlramMedicines().get(i).getDoseType().equals("식전")){
                                                calendar.set(Calendar.HOUR_OF_DAY,19);      //식전
                                                calendar.set(Calendar.MINUTE,35);
                                                calendar.set(Calendar.SECOND,00);
                                                if(calendar.before(Calendar.getInstance())){
                                                    calendar.add(Calendar.DATE, 1);
                                                }
                                                PendingIntent sender = PendingIntent.getBroadcast(LoginActivity.this,response.body().getAlramMedicines().get(i).getAlramGroupId(),my_intent,PendingIntent.FLAG_UPDATE_CURRENT);
                                                alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),24*60*60*1000,sender);

                                                calendar.set(Calendar.HOUR_OF_DAY,19);      //식전
                                                calendar.set(Calendar.MINUTE,37);
                                                if(calendar.before(Calendar.getInstance())){
                                                    calendar.add(Calendar.DATE, 1);
                                                }
                                                PendingIntent sender2 = PendingIntent.getBroadcast(LoginActivity.this,response.body().getAlramMedicines().get(i).getAlramGroupId()+100,my_intent,PendingIntent.FLAG_UPDATE_CURRENT);
                                                alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),24*60*60*1000,sender2);

                                                calendar.set(Calendar.HOUR_OF_DAY,19);      //식전
                                                calendar.set(Calendar.MINUTE,39);
                                                if(calendar.before(Calendar.getInstance())){
                                                    calendar.add(Calendar.DATE, 1);
                                                }
                                                PendingIntent sender3 = PendingIntent.getBroadcast(LoginActivity.this,response.body().getAlramMedicines().get(i).getAlramGroupId()+200,my_intent,PendingIntent.FLAG_UPDATE_CURRENT);
                                                alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),24*60*60*1000,sender3);
                                            }else{
                                                calendar.set(Calendar.HOUR_OF_DAY,9);      //식후
                                                calendar.set(Calendar.MINUTE,00);
                                                if(calendar.before(Calendar.getInstance())){
                                                    calendar.add(Calendar.DATE, 1);
                                                }
                                                PendingIntent sender = PendingIntent.getBroadcast(LoginActivity.this,response.body().getAlramMedicines().get(i).getAlramGroupId(),my_intent,0);
                                                alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),24*60*60*1000,sender);

                                                calendar.set(Calendar.HOUR_OF_DAY,13);      //식후
                                                calendar.set(Calendar.MINUTE,00);
                                                if(calendar.before(Calendar.getInstance())){
                                                    calendar.add(Calendar.DATE, 1);
                                                }
                                                PendingIntent sender2 = PendingIntent.getBroadcast(LoginActivity.this,response.body().getAlramMedicines().get(i).getAlramGroupId()+100,my_intent,0);
                                                alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),24*60*60*1000,sender2);

                                                calendar.set(Calendar.HOUR_OF_DAY,18);      //식후
                                                calendar.set(Calendar.MINUTE,00);
                                                if(calendar.before(Calendar.getInstance())){
                                                    calendar.add(Calendar.DATE, 1);
                                                }
                                                PendingIntent sender3 = PendingIntent.getBroadcast(LoginActivity.this,response.body().getAlramMedicines().get(i).getAlramGroupId()+200,my_intent,0);
                                                alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),24*60*60*1000,sender3);
                                            }
                                            break;
                                        }

                                        default:{


                                        }
                                    }

                                }
                            }
                            Intent intent =  new Intent(getApplicationContext(), MainActivity.class);
                            intent.putExtra("name",name);
                            startActivity(intent);
                            finish();
                            startclass.finish();
                        }
                    }

                    @Override
                    public void onFailure(Call<UserAlarm> call, Throwable t) {
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
