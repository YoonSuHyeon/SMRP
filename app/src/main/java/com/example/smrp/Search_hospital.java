package com.example.smrp;

import android.os.AsyncTask;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOError;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Search_hospital {
    String local_code;
    StringBuilder total_information = null; // 총 정보
    StringBuilder total_url = null; // 요청메시지
    double longitude=0.0, latitude=0.0;
    public Search_hospital(String local_code,double longitude, double latitude){//longitude:경도 latitude:위도
        this.local_code = local_code;
        this.longitude = longitude;
        this.latitude = latitude;
    }
    public String getInformation(){
        return String.valueOf(total_information);   //String.valueOf(): null일 때 null값 반환
                                                    //.toString(): null일때 NullPointerException 발생
    }
    public void run(){
        String url=null,servicekey=null,option=null;
        url ="http://apis.data.go.kr/B551182/hospInfoService/getHospBasisList?";
        servicekey = "LjJVA0wW%2BvsEsLgyJaBLyTywryRMuelTIYxsWnQTaPpxdZjpuxVCdCtyNxvObDmBJ57VVaSi3%2FerYKQFQmKs8g%3D%3D";
        option = "";
        total_url.append(url+"?ServiceKey="+servicekey+option);
        DwonLoad dwonLoad = new DwonLoad();
        dwonLoad.execute(String.valueOf(total_url));
    }
    public class DwonLoad extends AsyncTask<String,Void,String>{

        @Override
        protected String doInBackground(String... url) {
            try {
                return(String) DownLoadUrl((String) url[0]);
            } catch (IOException e) {
                return "다운로드 실패";
            }
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);


        }
    }
    public String DownLoadUrl(String data_url) throws IOException{
        HttpURLConnection con = null;
        BufferedReader br;
        try{
            URL url = new URL(data_url); //url 객체 선언
            con = (HttpURLConnection) url.openConnection();// url연결
            con.setRequestMethod("GET"); //메소드 설정
            BufferedInputStream bufferedInputStream =new BufferedInputStream(con.getInputStream());//url연결 후 스트림 get
            br = new BufferedReader(new InputStreamReader(bufferedInputStream));

            String data=null;
            StringBuilder total_data=null;
            while((data = br.readLine())!=null){
                total_data.append(data);
            }
            return String.valueOf(total_data);
        }finally {
            con.disconnect();
        }

    }

}
