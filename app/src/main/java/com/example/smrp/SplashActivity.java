package com.example.smrp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.os.Handler;
import android.util.Base64;
import android.util.Log;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SplashActivity extends AppCompatActivity {
    private Handler handler = new Handler();
    String str ="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //getHashKey(); //해쉬 키값을 얻기위함 (카카오 api를 사용하기 위해 필요함 !!!! 지우지말것!!)

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(getApplicationContext(),StartActivity.class);
                startActivity(intent);
                finish();
            }
        },2000);

       /*
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

*/

    }
    private void getHashKey(){
        PackageInfo packageInfo = null;
        try {
            packageInfo = getPackageManager().getPackageInfo(getPackageName(), PackageManager.GET_SIGNATURES);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        if (packageInfo == null)
            Log.e("KeyHash", "KeyHash:null");

        for (Signature signature : packageInfo.signatures) {
            try {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            } catch (NoSuchAlgorithmException e) {
                Log.e("KeyHash", "Unable to get MessageDigest. signature=" + signature, e);
            }
        }
    }


}
