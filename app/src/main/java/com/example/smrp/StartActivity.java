package com.example.smrp;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.viewpager.widget.ViewPager;

import com.example.smrp.medicine.ViewPagerAdapter;

import java.util.Timer;
import java.util.TimerTask;

import me.relex.circleindicator.CircleIndicator;

public class StartActivity extends AppCompatActivity{

    ViewPagerAdapter adapter;
    ViewPager viewPager;
    int currentPage = 0;
    private int[] images = {R.drawable.s2, R.drawable.s3,R.drawable.s4};
    Timer timer;
    Button Btn_login; // 로그인 버튼 객체
    Button Btn_signup; // 회원가입 버튼 객체

    final long DELAY_MS = 1000;
    final long PERIOD_MS = 6000;
    final int PERMISSION = 1;
    public static Activity StartActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        StartActivity = StartActivity.this;
        CircleIndicator indicator = findViewById(R.id.indicator);
        Btn_login = findViewById(R.id.Btn_login);
        Btn_signup = findViewById(R.id.Btn_signup);
        viewPager = findViewById(R.id.view);


        adapter = new ViewPagerAdapter(this, images);
        viewPager.setAdapter(adapter);
        indicator.setViewPager(viewPager);

        if (Build.VERSION.SDK_INT >= 23) {      //퍼미션 권한 부여
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.INTERNET,Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.CALL_PHONE,Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA}, PERMISSION);
        }//퍼미션접근 권한

        Btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { //로그인 버튼
                Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
                startActivity(intent);

            }
        });
        Btn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { //회원가입버튼
                
                Intent intent = new Intent(getApplicationContext(),ToSActivity.class);
                startActivity(intent);

            }
        });

        /*HTTP*/
        

        // 자동 슬라이드
        final Handler handler = new Handler();
        final Runnable Update = new Runnable() {
            @Override
            public void run() {
                if(currentPage == 3) { // currentPage는 현재 페이지로, 3이되면 맨 앞에 페이지로 이동한다.
                    currentPage = 0;
                }

                viewPager.setCurrentItem(currentPage++, true); // 페이지 이동
            }
        };
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(Update);
            }
        }, DELAY_MS, PERIOD_MS);



    }
  }




