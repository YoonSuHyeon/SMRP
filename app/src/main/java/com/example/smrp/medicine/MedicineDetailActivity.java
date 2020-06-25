package com.example.smrp.medicine;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.smrp.MedicineUserId;
import com.example.smrp.R;
import com.example.smrp.RetrofitHelper;
import com.example.smrp.RetrofitService;
import com.example.smrp.reponse_medicine2;
import com.example.smrp.response;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MedicineDetailActivity extends AppCompatActivity {
    Context context;
    ImageView medicineImage;//약 사진
    ImageView iv_back; //뒤로가기 이미지뷰
    TextView medicineName, medicineEntpName, medicineChart, medicineClassName, medicineEtcOtcName, medicineEffect, medicineUsage;
    String itemSeq;
    Button addMedicine; //추가하기 버튼
    String user_id,getData="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicine_detail);
        SharedPreferences loginInfromation = this.getSharedPreferences("setting", 0);
        user_id = loginInfromation.getString("id", "");
        //Init

        context = this;

        medicineName = findViewById(R.id.tv_medicine_name);    //약이름
        medicineEntpName = findViewById(R.id.tv_entpName);//약 제조사
        medicineChart = findViewById(R.id.tv_chart);//약성상
        medicineClassName = findViewById(R.id.tv_className);//약분류
        medicineEtcOtcName = findViewById(R.id.tv_etcOtcName);//약구분
        medicineEffect = findViewById(R.id.tv_effect);//약효능효과
        medicineUsage = findViewById(R.id.tv_usage);//약용법용량

        addMedicine = findViewById(R.id.btn_add);//추가버튼
        medicineImage = findViewById(R.id.iv_medicine); //약이미지
        iv_back = findViewById(R.id.iv_back);//뒤로가기 버튼


        //일련번호 ItemSeq 를 Intent로 받는다.  사진촬영이든,검색을 해서 든 .
        Intent intent = getIntent();
        itemSeq = intent.getStringExtra("itemSeq");

        final String StrUrl = "http://apis.data.go.kr/1470000/DURPrdlstInfoService/getUsjntTabooInfoList";
        final String ServiceKey = "LjJVA0wW%2BvsEsLgyJaBLyTywryRMuelTIYxsWnQTaPpxdZjpuxVCdCtyNxvObDmBJ57VVaSi3%2FerYKQFQmKs8g%3D%3D";


        RetrofitService networkService = RetrofitHelper.getRetrofit().create(RetrofitService.class);

        Call<reponse_medicine2> call = networkService.findmedicine(itemSeq);
        call.enqueue(new Callback<reponse_medicine2>() { //약 의 내용을 서버에서 가져오는 과정
            @Override
            public void onResponse(Call<reponse_medicine2> call, Response<reponse_medicine2> response) {
                reponse_medicine2 reponse_medicine2 = response.body();
                //약에 대한 정보들을 넣어준다
                if (reponse_medicine2 == null) {

                    medicineName.setText("다시 시도 해주세요");
                } else {
                    Glide.with(context).load(reponse_medicine2.getItemImage()).override(1200, 700).fitCenter().into(medicineImage);//이미지 등록
                    medicineName.setText(reponse_medicine2.getItemName());
                    medicineChart.setText(reponse_medicine2.getChart());
                    medicineEntpName.setText(reponse_medicine2.getEntpName());
                    medicineClassName.setText(reponse_medicine2.getClassName());
                    medicineEtcOtcName.setText(reponse_medicine2.getEtcOtcName());
                    medicineEffect.setText(reponse_medicine2.getEffect());
                    medicineUsage.setText(reponse_medicine2.getUsage());

                    String TotalUrl = StrUrl + "?ServiceKey=" + ServiceKey + "&itemName=" + reponse_medicine2.getItemName();
                    DownLoad1 d1 = new DownLoad1();
                    d1.execute(TotalUrl);
                }
            }

            @Override
            public void onFailure(Call<reponse_medicine2> call, Throwable t) {


            }
        });


        //뒤로가기 버튼
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        //추가하기 버튼
        addMedicine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//추가 하기 버튼을 눌렀을때 서버에게 현재 자기가 등록 한 약이 무엇이다라는 것을 알려준다.  // userId 사용자 id    itemSeq  일련번호
                RetrofitService networkService = RetrofitHelper.getRetrofit().create(RetrofitService.class);
                Log.d("user_id detailActivity", user_id);
                MedicineUserId medicineUserId = new MedicineUserId(user_id, itemSeq);
                Call<response> call = networkService.addMedicine(medicineUserId);
                call.enqueue(new Callback<response>() {
                    @Override
                    public void onResponse(Call<response> call, Response<response> response) {
                        try {
                            if (response.body().getResponse().equals("ok")) {

                            } else {

                            }

                        } catch (NullPointerException e) {

                        }
                        onBackPressed();
                        finish();

                        //Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                        //startActivity(intent);
                    }

                    @Override
                    public void onFailure(Call<response> call, Throwable t) {


                    }
                });

            }
        });

    }

    public class DownLoad1 extends AsyncTask<String, Void, String> {////////////////////// 사용자가 입력한 dgsbjtCd 진료과목을 갖는 병원 파싱

        @Override
        protected String doInBackground(String... url) {
            try {
                Log.d("TAG", "search_hos_Activity_doInBackground: ");
                return (String) DownLoadUrl1((String) url[0]);
            } catch (IOException e) {
                return "다운로드 실패";
            }
        }

        protected void onPostExecute(String result) {
            //super.onPostExecute(result);
            String resultCode = "";   //결과코드
            String yadmNm = "";   //요양기관 이름
            String addr = "";   //요양기관 주소
            String clCdNm = ""; // 요양기관 규모
            String telno = "";   //요양기관 전화번호
            String XPos = "";   //요양기관x좌표
            String YPos = "";  //요양기관 y좌표

            boolean ho_resultCode = false;
            boolean ho_yadmNm = false;//요양기관 이름
            boolean ho_addr = false;//요양기관 주소
            boolean ho_telno = false;//요양기관 전화번호
            boolean ho_XPos = false;//요양기관 x좌료
            boolean ho_YPos = false;
            boolean ho_clCdNm = false;
            try {
                XmlPullParserFactory xmlPullParserFactory = XmlPullParserFactory.newInstance();
                xmlPullParserFactory.setNamespaceAware(true);
                XmlPullParser xmlPullParser = xmlPullParserFactory.newPullParser();

                xmlPullParser.setInput(new StringReader(result));
                int eventType = xmlPullParser.getEventType();


                //Log.d("TAG", "\n\n\n\ninformation123" + information + "\n\n\n\n");
                //  Log.d("TAG", "\n==========num==10===========: \n");


            } catch (Exception e) {
                e.printStackTrace();
            }
        }


    }
    public String DownLoadUrl1(String myurl) throws IOException {
        HttpURLConnection com = null;
        BufferedReader bufferedReader;
        try {
            Log.d("TAG", "\n==========DownLoadUrl1==========\n ");
            URL url = new URL(myurl);
            com = (HttpURLConnection) url.openConnection();
            com.setRequestMethod("GET");
            BufferedInputStream bufferedInputStream = new BufferedInputStream(com.getInputStream());
            bufferedReader = new BufferedReader(new InputStreamReader(bufferedInputStream, "UTF-8"));

            String total = null;
            getData = "";
            while ((total = bufferedReader.readLine()) != null) {
                getData += total;
            }
            return getData;
        } finally {
            com.disconnect();
        }
    }
}
