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
    private StringBuilder total_information = new StringBuilder(""); // 총 정보


    //String information = null;

    private String longitude="0.0", latitude="0.0"; //String 형 url변수에 필요한 경도, 위도 작성
    int radius=500;
    public PharmacyViewModel(){//longitude:경도 latitude:위도
    }
    void setInformation(double longitude, double latitude){  // 경도와 위도를 설정하는 두개의 매개변수를 가진 setInformation 선언
        this.longitude = String.valueOf(longitude); // 경도값 longitude 변수에 저장
        this.latitude = String.valueOf(latitude); //위도값 latitude 변수에 저장
    }
    public String getInformation(){ //API 파싱한 결과물을 return 받기 위함
        return String.valueOf(total_information);   //String.valueOf(): null일 때 null값 반환
    }
    void run(){ //공공데이터 API를 파싱하기 위한 run 메소드 선언
        String url=null,servicekey=null,option=null;
        url ="http://apis.data.go.kr/B551182/pharmacyInfoService/getParmacyBasisList?"; // 요청할 URL 주소 선언
        servicekey = "LjJVA0wW%2BvsEsLgyJaBLyTywryRMuelTIYxsWnQTaPpxdZjpuxVCdCtyNxvObDmBJ57VVaSi3%2FerYKQFQmKs8g%3D%3D"; //사용자 키(서버으로부터 받은 data를 복호화하기 위해 필요한 Key)
        option = "&xPos="+longitude+"&yPos="+latitude+"&radius="+radius; //파라메터를 설정하는 변수이며 경도, 위도, 반경 값을 설정
        url+="ServiceKey="+servicekey+option;

        DownLoad dwonLoad = new DownLoad();
        dwonLoad.execute(url);
    }
    // < >안에 들은 자료형은 순서대로 doInBackground, onProgressUpdate, onPostExecute의 매개변수 자료형을 뜻한다.(내가 사용할 매개변수타입을 설정하면된다)
    public class DownLoad extends AsyncTask<String,Void,String> {

        @Override
        protected String doInBackground(String... url) {
            try {
                return(String) DownLoadUrl((String) url[0]); //GET 메소드를 통해 얻은 스트림 값을 가지고 있음.
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
            super.onPostExecute(result);            //심사평가원에서는 약국도 요양기관으로 취급
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
            boolean ho_YPos = false; // 요양기관 y좌표
            boolean ho_clCdNm = false; //종별코드명 (약국)
            try {
                XmlPullParserFactory xmlPullParserFactory = XmlPullParserFactory.newInstance();
                xmlPullParserFactory.setNamespaceAware(true);
                XmlPullParser xmlPullParser = xmlPullParserFactory.newPullParser();

                xmlPullParser.setInput(new StringReader(result));
                int eventType = xmlPullParser.getEventType();

                while (eventType != XmlPullParser.END_DOCUMENT) { //문서의 끝을 도달할 떄까지 while 반복문 실행
                    if (eventType == XmlPullParser.START_DOCUMENT) {
                    } else if (eventType == XmlPullParser.START_TAG) { // eventType 이 tag값이면
                        String tag_name = xmlPullParser.getName(); //tag 이름 가져오김
                        switch (tag_name) {
                            case "resultCode":
                                //Log.d("TAG", "resultCode");
                                ho_resultCode = true;
                                break;
                            case "yadmNm": //요양기간 이름
                                //Log.d("TAG", "yadmNm");
                                ho_yadmNm = true;
                                break;
                            case "addr": //요양기간 주소
                                //Log.d("TAG", "addr");
                                ho_addr = true;
                                break;
                            case "telno": //요양기간 전화번호
                                //Log.d("TAG", "telno");
                                ho_telno = true;
                                break;
                            case "XPos": //요양기관 경도
                                //Log.d("TAG", "XPos");
                                ho_XPos = true;
                                break;
                            case "YPos": //요양기관 위도
                                //Log.d("TAG", "YPos");
                                ho_YPos = true;
                                break;
                            case "clCdNm": //요양기관 종별코드명
                                //Log.d("TAG", "YPos");
                                ho_clCdNm = true;
                                break;

                        }
                    } else if (eventType == XmlPullParser.TEXT) {
                        if (ho_resultCode) {
                            resultCode = xmlPullParser.getText();
                            ho_resultCode = false;
                        }
                        if (resultCode.equals("00")) {
                            //////////////////// 약국의 정보는 total_information StringBuilder 에 append 됨
                            if (ho_addr) {
                                addr = xmlPullParser.getText(); //요양기관 주소
                                total_information.append(addr + "/");
                                ho_addr = false;
                            }
                            if (ho_yadmNm) {
                                yadmNm = xmlPullParser.getText();//요양기관 이름
                                total_information.append(yadmNm + "\n");//병원 정보 문자열
                                ho_yadmNm = false;
                            }
                            if (ho_clCdNm) {
                                clCdNm = xmlPullParser.getText(); //요양기관 종별코드명
                                total_information.append(clCdNm + "/");
                                ho_clCdNm = false;
                            }
                            if (ho_telno) {
                                telno = xmlPullParser.getText(); // 요양기관 전화번호
                                total_information.append(telno + "/");
                                ho_telno = false;
                            }
                            if (ho_XPos) {
                                XPos = xmlPullParser.getText();  // 요양기관 경도
                                total_information.append(XPos + "/");
                                ho_XPos = false;
                            }
                            if (ho_YPos) {
                                YPos = xmlPullParser.getText(); // 요양기관 위도
                                total_information.append(YPos + "/");
                                ho_YPos = false;
                            }
                        }
                    } else if (eventType == XmlPullParser.END_TAG) {
                        ;
                    }
                    eventType = xmlPullParser.next(); //문서를 read로 생각하면 됨
                }
                Log.d("TAG", "total_information_result: "+String.valueOf(total_information)+"\n");
                mText = new MutableLiveData<>(); //mText 객체 선언
                mText.setValue(String.valueOf(total_information)); // 약국의 모든 정보를 mText에 설정
                total_information.delete(0,total_information.length()-1); // total_information(StringBuilder)의 값을 초기화 하기 위함
                Log.d("TAG", "total_information: "+String.valueOf(total_information)+"\n");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    private String DownLoadUrl(String data_url) throws IOException{
        HttpURLConnection con = null;
        BufferedReader br;
        try{
            Log.d("TAG", "data_url:"+data_url+"\n");
            URL url = new URL(data_url); //url 객체 선언
            con = (HttpURLConnection) url.openConnection();// url연결
            con.setRequestMethod("GET"); //GET메소드 설정
            BufferedInputStream bufferedInputStream =new BufferedInputStream(con.getInputStream());//url연결 후 스트림 get

            br = new BufferedReader(new InputStreamReader(bufferedInputStream,"UTF-8")); // url연결한 스트림을 읽어 오고 그 스트림을 읽어 올때 UTF-8(으)로 읽음

            String data=null;
            StringBuilder total_data = new StringBuilder(); // StringBuilder total_data 변수를 선언
            while((data = br.readLine())!=null){  // con연겷하여 읽어 온 스트림을 total_data StringBuilder 에 저장
                total_data.append(data);
            }
            return String.valueOf(total_data); // 모든 스트림 readLine이 끝나면 total_data(스트림)을 return 실시
        }finally {
            if (con != null) {
                con.disconnect();
            }
        }

    }

    public LiveData<String> getText() {
        total_information.delete(0,total_information.length()-1);
        return mText;
    }
}
