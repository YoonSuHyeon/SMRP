package com.example.smrp.pharmacy;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.smrp.R;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.logging.Handler;

public class PharmacyViewModel extends ViewModel {
    private MutableLiveData<String> mText;
    private Handler handler;
    private String phy_data; //내 위치 기준 반경 300m 약국정보
    StringBuilder total_information = new StringBuilder(""); // 총 정보
    StringBuilder total_url = null; // 요청메시지
    ArrayList<String> list = new ArrayList<>();

    //String information = null;
    int count = 0;
    String longitude="0.0", latitude="0.0";
    public int radius=500;
    public PharmacyViewModel(){//longitude:경도 latitude:위도
    }
    public void setInformation(double longitude, double latitude){  //
        this.longitude = String.valueOf(longitude);
        this.latitude = String.valueOf(latitude);
        Log.d("TAG", "Search_hospital: "+longitude+","+latitude);
    }
    public String getInformation(){
        Log.d("TAG", "getInformationgetInformation\n"+ String.valueOf(total_information)+"\n");
        return String.valueOf(total_information);   //String.valueOf(): null일 때 null값 반환
        //.toString(): null일때 NullPointerException 발생
    }
    public void run(){
        String url=null,servicekey=null,option=null;
        url ="http://apis.data.go.kr/B551182/pharmacyInfoService/getParmacyBasisList?";
        servicekey = "LjJVA0wW%2BvsEsLgyJaBLyTywryRMuelTIYxsWnQTaPpxdZjpuxVCdCtyNxvObDmBJ57VVaSi3%2FerYKQFQmKs8g%3D%3D";
        option = "&xPos="+longitude+"&yPos="+latitude+"&radius="+radius;
        url+="ServiceKey="+servicekey+option;

        DownLoad dwonLoad = new DownLoad();
        dwonLoad.execute(url);
    }
    // < >안에 들은 자료형은 순서대로 doInBackground, onProgressUpdate, onPostExecute의 매개변수 자료형을 뜻한다.(내가 사용할 매개변수타입을 설정하면된다)
    public class DownLoad extends AsyncTask<String,Void,String> {

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
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
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

                while (eventType != XmlPullParser.END_DOCUMENT) {
                    if (eventType == XmlPullParser.START_DOCUMENT) {
                    } else if (eventType == XmlPullParser.START_TAG) {
                        String tag_name = xmlPullParser.getName();
                        switch (tag_name) {
                            case "resultCode":
                                //Log.d("TAG", "resultCode");
                                ho_resultCode = true;
                                break;
                            case "yadmNm":
                                //Log.d("TAG", "yadmNm");
                                ho_yadmNm = true;
                                break;
                            case "addr":
                                //Log.d("TAG", "addr");
                                ho_addr = true;
                                break;
                            case "telno":
                                //Log.d("TAG", "telno");
                                ho_telno = true;
                                break;
                            case "XPos":
                                //Log.d("TAG", "XPos");
                                ho_XPos = true;
                                break;
                            case "clCdNm":
                                //Log.d("TAG", "YPos");
                                ho_clCdNm = true;
                                break;
                            case "YPos":
                                //Log.d("TAG", "YPos");
                                ho_YPos = true;
                                break;
                        }
                    } else if (eventType == XmlPullParser.TEXT) {
                        if (ho_resultCode) {
                            resultCode = xmlPullParser.getText();
                            ho_resultCode = false;
                        }
                        if (resultCode.equals("00")) {


                            if (ho_addr) {
                                addr = xmlPullParser.getText();
                                total_information.append(addr + "/");
                                ho_addr = false;
                            }
                            if (ho_yadmNm) {
                                yadmNm = xmlPullParser.getText();//요양기관 이름
                                total_information.append(yadmNm + "\n");//병원 정보 문자열
                                ho_yadmNm = false;
                            }
                            if (ho_clCdNm) {
                                clCdNm = xmlPullParser.getText();
                                total_information.append(clCdNm + "/");
                                ho_clCdNm = false;
                            }
                            if (ho_telno) {
                                telno = xmlPullParser.getText();
                                total_information.append(telno + "/");
                                ho_telno = false;
                            }
                            if (ho_XPos) {
                                XPos = xmlPullParser.getText();
                                total_information.append(XPos + "/");
                                ho_XPos = false;
                            }
                            if (ho_YPos) {
                                YPos = xmlPullParser.getText();
                                total_information.append(YPos + "/");
                                ho_YPos = false;
                            }
                        }
                    } else if (eventType == XmlPullParser.END_TAG) {
                        ;
                    }
                    eventType = xmlPullParser.next();
                }
                Log.d("TAG", "total_information_result: \n"+String.valueOf(total_information)+"\n");
                mText = new MutableLiveData<>();
                mText.setValue(String.valueOf(total_information));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    public String DownLoadUrl(String data_url) throws IOException{
        HttpURLConnection con = null;
        BufferedReader br;
        try{
            Log.d("TAG", "data_url:"+data_url+"\n");
            URL url = new URL(data_url); //url 객체 선언
            con = (HttpURLConnection) url.openConnection();// url연결
            con.setRequestMethod("GET"); //메소드 설정
            BufferedInputStream bufferedInputStream =new BufferedInputStream(con.getInputStream());//url연결 후 스트림 get

            br = new BufferedReader(new InputStreamReader(bufferedInputStream,"UTF-8"));

            String data=null;
            StringBuilder total_data = new StringBuilder();
            while((data = br.readLine())!=null){
                total_data.append(data);
            }
            return String.valueOf(total_data);
        }finally {
            con.disconnect();
        }

    }

    public LiveData<String> getText() {
        return mText;
    }
}
