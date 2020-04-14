package com.example.smrp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.viewpager.widget.ViewPager;

import android.Manifest;
import android.content.Intent;
import android.icu.util.ICUUncheckedIOException;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import java.util.Timer;
import java.util.TimerTask;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.viewpager.widget.PagerAdapter;

import com.example.smrp.medicine.ViewPagerAdapter;

import me.relex.circleindicator.CircleIndicator;

public class StartActivity extends AppCompatActivity{

    ViewPagerAdapter adapter;
    ViewPager viewPager;
    int currentPage = 0;
    private int[] images = {R.drawable.slide1, R.drawable.slide2,R.drawable.slide3};
    Timer timer;
    Button Btn_login; // 로그인 버튼 객체
    Button Btn_signup; // 회원가입 버튼 객체

    final long DELAY_MS = 1000;
    final long PERIOD_MS = 3000;
    final int PERMISSION = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        CircleIndicator indicator = findViewById(R.id.indicator);
        Btn_login = findViewById(R.id.Btn_login);
        Btn_signup = findViewById(R.id.Btn_signup);
        viewPager = findViewById(R.id.view);


        adapter = new ViewPagerAdapter(this, images);
        viewPager.setAdapter(adapter);
        indicator.setViewPager(viewPager);

        if (Build.VERSION.SDK_INT >= 23) {      //퍼미션 권한 부여
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.INTERNET,Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.CALL_PHONE,Manifest.permission.ACCESS_COARSE_LOCATION}, PERMISSION);
        }//퍼미션접근 권한

        Btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
                startActivity(intent);

            }
        });
        Btn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),ToSActivity.class);
                startActivity(intent);

            }
        });


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




