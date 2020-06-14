package com.example.smrp.home;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.viewpager.widget.ViewPager;

import com.example.smrp.R;
import com.example.smrp.RetrofitHelper;
import com.example.smrp.RetrofitService;
import com.example.smrp.User;
import com.example.smrp.UserAlarm;
import com.example.smrp.alarm.Alarm_Reciver;
import com.example.smrp.medicine.ViewPagerAdapter;
import com.example.smrp.searchMed.SearchActivity;
import com.example.smrp.searchPrescription.Search_prescriptionActivity;


import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

import me.relex.circleindicator.CircleIndicator;
import retrofit2.Call;
import retrofit2.Callback;

public class HomeFragment extends Fragment {

    private ViewPagerAdapter adapter;
    private ViewPagerAdapter bannerAdapter;
    private ViewPager viewPager;
    private ViewPager viewPager2;
    private final long DELAY_MS = 1000; // 자동 슬라이드를 위한 변수
    private final long PERIOD_MS = 3000; // 자동 슬라이드를 위한 변수
    private int currentPage = 0; // 자동 슬라이드를 위한 변수(현재 페이지)

    static Timer timer = null; // 자동 슬라이드를 위한 변수
    static TimerTask timerTask= null;
    long now = System.currentTimeMillis();
    // 현재시간을 date 변수에 저장한다.
    Date date = new Date(now);
    // 시간을 나타냇 포맷을 정한다 ( yyyy/MM/dd 같은 형태로 변형 가능 )
    SimpleDateFormat sdfNow = new SimpleDateFormat("a hh : mm");
    // nowDate 변수에 값을 저장한다.
    String formatDate = sdfNow.format(date);
    private static TextView time,pm_textview,humidity_textView,temp_textview,Txt_statement, Txt_weather;//, min_max_textview, feel_textview;
    private static ImageView weather_imageview;

    private Location location;
    private double latitude, longitude;
    private Bitmap bitmap;
    private int[] images= {R.drawable.t3, R.drawable.t1,R.drawable.t2}; // ViewPagerAdapter에  보낼 이미지. 이걸로 이미지 슬라이드 띄어줌
    private int[] bannerImages ={R.drawable.slide1, R.drawable.slide2,R.drawable.slide3};

    //하단 이미지 버튼
    ImageView ic_med_search;
    ImageView ic_prescription_register;
    ImageView ic_envelope_register;
    ImageView ic_pharmacy_search;
    ImageView ic_hospital_search;

    ImageView ic_register_record;
    ImageView ic_dose_record;
    ImageView ic_alarm_set;


    NavHostFragment navHostFragment;
    NavController navController;
    private static RetrofitService_home json;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        if(container.getChildCount() > 0)
            container.removeViewAt(0);

        final View root = inflater.inflate(R.layout.home_fragment, container, false);

        final HashMap<String,String> sky_image = new HashMap<>();
        final RetrofitService retrofitService = RetrofitHelper.getRetrofit().create(RetrofitService.class);

        //밤일떄 return 받는 이미지가 n으로 끝남
        if(sky_image != null) {
            sky_image.put("01n", "clear_sky");
            sky_image.put("02n", "few_clouds");
            sky_image.put("03n", "scattered_clouds");
            sky_image.put("04n", "broken_clouds");
            sky_image.put("09n", "show_rain");
            sky_image.put("10n", "rain");
            sky_image.put("11n", "thunderstom");
            sky_image.put("13n", "snow");
            sky_image.put("50n", "mist");

            //아침,낮 일떄 return 받는 이미지가 d으로 끝남
            sky_image.put("01d", "clear_sky");
            sky_image.put("02d", "few_clouds");
            sky_image.put("03d", "scattered_clouds");
            sky_image.put("04d", "broken_clouds");
            sky_image.put("09d", "show_rain");
            sky_image.put("10d", "rain");
            sky_image.put("11d", "thunderstom");
            sky_image.put("13d", "snow");
            sky_image.put("50d", "mist");

        }

        if(navHostFragment==null) {
            navHostFragment =
                    (NavHostFragment) ((AppCompatActivity) getContext()).getSupportFragmentManager()
                            .findFragmentById(R.id.nav_host_fragment);
        }
        navController = navHostFragment.getNavController();

        weather_imageview = root.findViewById(R.id.weather_imageview); //하늘 상태 사진
        temp_textview = root.findViewById(R.id.temp_textview); //온도 textView
       // min_max_textview = root.findViewById(R.id.min_max_textview);
       // feel_textview = root.findViewById(R.id.feel_textview);
        humidity_textView = root.findViewById(R.id.humidity_textView); //하늘상태
        Txt_statement = root.findViewById(R.id.Txt_statement);
        Txt_weather = root.findViewById(R.id.Txt_weather);
        // 하단 이미지 버튼
        ic_med_search = root.findViewById(R.id.ic_med_search);
        ic_prescription_register = root.findViewById(R.id.ic_prescription_register);
        ic_envelope_register = root.findViewById(R.id.ic_envelope_register);
        ic_pharmacy_search = root.findViewById(R.id.ic_pharmacy_search);
        ic_hospital_search = root.findViewById(R.id.ic_hospital_search);
        ic_register_record = root.findViewById(R.id.ic_register_record);
        ic_dose_record = root.findViewById(R.id.ic_dose_record);
        ic_alarm_set = root.findViewById(R.id.ic_alarm_set);
//        ic_mask_search = root.findViewById(R.id.ic_mask_search);
  //      ic_inquiry = root.findViewById(R.id.ic_inquiry);




        startLocationService();//사용자 현재위치 경도 및 위도 GET

        /*리스트 프래그먼트 */
        FragmentManager fm = getActivity().getSupportFragmentManager(); // Fragment를 관리하기 위해서는 FragmentManager를 사용
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.add(R.id.Frg_med_alarm, new HomeAlarmFragment()); // parameter1 : activity 내에서 fragment 를 삽입할 Layout id
        fragmentTransaction.commit();                                        // parameter2 : 삽입할 fragment


        CircleIndicator indicator = root.findViewById(R.id.indicator_home); // 인디케이터
        CircleIndicator indicator2 = root.findViewById(R.id.indicator_home2); // 인디케이터

        /*동적으로 배너 크기 바꾸기 */
        DisplayMetrics displayMetrics = new DisplayMetrics();
        WindowManager windowManager = (WindowManager) getContext().getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
        windowManager.getDefaultDisplay().getMetrics(displayMetrics);
        int mHeight = displayMetrics.heightPixels/3-5;
        LinearLayout viewP = root.findViewById(R.id.viewP);
        LayoutParams lay = (LayoutParams) viewP.getLayoutParams();
        lay.height =  mHeight;
        viewP.setLayoutParams(lay);

        viewPager =  root.findViewById(R.id.banner);
        viewPager2 =  root.findViewById(R.id.banner2);
        adapter = new ViewPagerAdapter(getActivity(),images,1);
        bannerAdapter =  new ViewPagerAdapter(getActivity(),bannerImages);


        /*첫 번째 배너 사이 간격 조정*/
        int dp = 40;
        float d = getResources().getDisplayMetrics().density;
        final int margin = (int) (dp * d);
        viewPager.setClipToPadding(false);
        viewPager.setPadding(margin, 0, margin, 0);
        viewPager.setPageMargin(margin/2);


        viewPager.setAdapter(adapter);
        viewPager2.setAdapter(bannerAdapter);
        viewPager.setCurrentItem(1);
        indicator.setViewPager(viewPager);
        indicator2.setViewPager(viewPager2);

        viewPager2.setCurrentItem(0);
        currentPage =0;
        if(timer != null){

            timerTask.cancel();

            timer.cancel() ;
        }



        final Handler handler = new Handler();
        final Runnable Update = new Runnable() {
            @Override
            public void run() {
                if(currentPage == 3) { // 현재페이지가 맨 끝일 시 맨 처음 페이지로 돌아간다.
                    currentPage = 0;
                }

                viewPager2.setCurrentItem(currentPage++, true);
            }
        };
        timerTask = new TimerTask() {

            public void run() {

                handler.post(Update);

            }

        };


        timer = new Timer();

        timer.schedule(timerTask, DELAY_MS, PERIOD_MS);



        //배너2 자동스크롤
        json = new RetrofitFactory_home().create();
        json.getList(latitude,longitude).enqueue(new Callback<Response>() {
            @Override
            public void onResponse(Call<Response> call, final retrofit2.Response<Response> response) {
              //  int a = (int)response.body().getWeather_main().getTemp();
                double temp = Math.round((response.body().getWeather_main().getTemp()) * 10) / 10.0;


                temp_textview.setText(String.valueOf(temp+"℃")); //현재 온도
            //    min_max_textview.append(String.valueOf(response.body().getWeather_main().getTemp_min())+"℃/"+String.valueOf(response.body().getWeather_main().getTemp_max())+"℃");
             //   feel_textview.setText("체감온도: "+String.valueOf(response.body().getWeather_main().getFells_like())+"℃");

                humidity_textView.setText("습도: "+response.body().getWeather_main().getHumidity()+"%"); //하늘상태


                String icon_name = response.body().getweatherList().get(0).getIcon();
                if(icon_name.contains("d")){ //밤과 낮의 아이콘을 하나로 통일하기위해서 낮으로 replace
                    icon_name = icon_name.replaceAll("d","n");
                }
                String str = sky_image.get(icon_name);

                if(str.equals("clear_sky")){
                    Txt_weather.setText("맑음 | ");
                    if(temp > 28.0){
                        Txt_statement.setText("더위 조심하세요!");

                    }else if(temp >10.0){
                        Txt_statement.setText("오늘은 따뜻한 날씨입니다");
                    } else{
                        Txt_statement.setText("오늘은 춥습니다");
                    }
                }else if(str.equals("few_clouds")){
                    Txt_weather.setText("흐림 | ");
                    Txt_statement.setText("오늘은 약간 흐립니다");
                }else if(str.equals("scattered_clouds")){
                    Txt_weather.setText("흐림 | ");
                    Txt_statement.setText("오늘은 우중충합니다");
                }else if(str.equals("rain")){
                    Txt_weather.setText("비 | ");
                    Txt_statement.setText("우산 잊지 않으셨나요?");
                }else if(str.equals("broken_clouds")){
                    Txt_weather.setText("흐림 | ");
                    Txt_statement.setText("오늘은 우중충합니다");
                }else if(str.equals("show_rain")){
                    Txt_weather.setText("비 | ");
                    Txt_statement.setText("우산 잊지 않으셨나요?");
                }

                if(str.equals("clear_sky")){
                    //BitmapDrawable image  = (BitmapDrawable)getResources().getDrawable(R.drawable.Sun1);
                    weather_imageview.setImageResource(R.drawable.clear_sky1);
                }else{
                    String str_url = " http://openweathermap.org/img/wn/"+ response.body().getweatherList().get(0).getIcon()+"@2x.png";
                    Url_Connection uc = new Url_Connection();
                    uc.execute(str_url);

                }

            }

            @Override
            public void onFailure(Call<Response> call, Throwable t) {

            }
        });

        //하단 이미지 버튼 이동
        ic_med_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getContext(), SearchActivity.class);
                startActivity(intent);

              //  navController.navigate(R.id.action_nav_home_to_nav_medicine);
            }
        });
        ic_prescription_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent = new Intent(getContext(), Search_prescriptionActivity.class);
                startActivity(intent);
            }
        });
        ic_envelope_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { //약봉투


                Intent intent = new Intent(getContext(), Search_prescriptionActivity.class);
                startActivity(intent);


            }
        });
        /*ic_alarm_set.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.action_nav_home_to_nav_alarm);
            }
        });*/
        ic_pharmacy_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.action_nav_home_to_nav_pharmacy);
            }
        });

        ic_hospital_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.action_nav_home_to_nav_hospital);
            }
        });

        ic_register_record.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.action_nav_home_to_nav_medicine);
            }
        });

        return root;

    }


    private class Url_Connection extends AsyncTask<String,Void,String>{
        @Override
        protected String doInBackground(String... str_url) {
            try {
                URL url = new URL(str_url[0]);
                URLConnection urlConnection = url.openConnection();
                urlConnection.setDoInput(true);
                urlConnection.connect();

                InputStream is = urlConnection.getInputStream();
                bitmap = BitmapFactory.decodeStream(is);

            } catch (MalformedURLException e) {
                e.printStackTrace();
            }catch (IOException e1){
                e1.printStackTrace();
            }
            return null;
        }
        protected void onPostExecute(String result){
            weather_imageview.setImageBitmap(bitmap);

        }
    }
    private void startLocationService(){ //사용자의 위치 좌표를 가져오기 위한 클래스
        LocationManager locationManager1 = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);//위치관리자 생성
        LocationManager locationManager2 = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);//위치관리자 생성
        GPSListener gpsListener = new GPSListener();
        long minTime = 1000;//단위 위치정보를 초기화 하기 위한 기준 설정 (millisecond , m)
        float minDistance = 10;//단위 m
        try {
            locationManager1.requestLocationUpdates(LocationManager.GPS_PROVIDER, minTime, minDistance, gpsListener); //// 위치 기반을 GPS모듈을 이용함
            locationManager2.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,minTime,minDistance,gpsListener);//// 위치 기반을 네트워크 모듈을 이용함
            //5초 마다 or 10m 이동할떄마다 업데이트   network는 gps에 비해 정확도가 떨어짐
            location = locationManager1.getLastKnownLocation(LocationManager.GPS_PROVIDER);//최근 gps기록  실내에서는 안잡힐수가 있다
            if (location != null) {
                latitude = location.getLatitude(); // GPS 모듈 경도 값 ex) 37.30616958190577
                longitude = location.getLongitude(); //GPS 모듈 위도 값 ex) 127.92099856059595
            }else{
                location = locationManager2.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);///네트워크로 얻은 마지막 위치좌표를 이용
                latitude = location.getLatitude(); //네트워크 경도 값
                longitude = location.getLongitude(); // 네트워크 위도 값
            }
        } catch (SecurityException e) { //보안적인 예외처리 발생시 실행
            e.printStackTrace();
        }

    }
    private class GPSListener implements LocationListener {//위치리너스 클래스

        @Override
        public void onLocationChanged(Location location) {
            latitude = location.getLatitude();
            longitude = location.getLongitude();

        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }//위치 공급자의 상태가 바뀔 때 호출

        @Override
        public void onProviderEnabled(String provider) {

        } //위치 공급자가 사용 가능해질(enabled) 때 호출

        @Override
        public void onProviderDisabled(String provider) {

        }//위치 공급자가 사용 불가능해질(disabled) 때 호출
    }
}
