package com.example.smrp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

public class SplashActivity extends AppCompatActivity {
    private Handler handler = new Handler();
    String str ="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

       /* handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(getApplicationContext(),StartActivity.class);
                startActivity(intent);
                finish();
            }
        },2000);*/
       final Search_hospital search_hospital = new Search_hospital(127.919880,37.302744);
       //search_hospital.run();
        Thread thread = new Thread(search_hospital);
        thread.run();

       handler.postDelayed(new Runnable() {
           @Override
           public void run() {
               do{
                   str = search_hospital.getInformation();
                   Log.d("TAG", "strstrstrstrstr: \n"+str+"\n");
               }while(str.equals(""));

           }
       },2000);//2초가량 대기



    }
}
