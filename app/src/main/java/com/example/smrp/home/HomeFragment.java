package com.example.smrp.home;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;

import android.view.ViewGroup.LayoutParams;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.viewpager.widget.ViewPager;

import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.smrp.R;
import com.example.smrp.medicine.ViewPagerAdapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import me.relex.circleindicator.CircleIndicator;

public class HomeFragment extends Fragment {

    private ViewPagerAdapter adapter;
    private ViewPagerAdapter bannerAdapter;
    private ViewPager viewPager;
    private ViewPager viewPager2;
    private HomeViewModel homeViewModel;
    private final long DELAY_MS = 1000; // 자동 슬라이드를 위한 변수
    private final long PERIOD_MS = 3000; // 자동 슬라이드를 위한 변수
    private int currentPage = 0; // 자동 슬라이드를 위한 변수(현재 페이지)
    private Timer timer; // 자동 슬라이드를 위한 변수

    long now = System.currentTimeMillis();
    // 현재시간을 date 변수에 저장한다.
    Date date = new Date(now);
    // 시간을 나타냇 포맷을 정한다 ( yyyy/MM/dd 같은 형태로 변형 가능 )
    SimpleDateFormat sdfNow = new SimpleDateFormat("a hh : mm");
    // nowDate 변수에 값을 저장한다.
    String formatDate = sdfNow.format(date);
    TextView time;



    private int[] images= {R.drawable.img_home_p1, R.drawable.img_self,R.drawable.img_home_p3}; // ViewPagerAdapter에  보낼 이미지. 이걸로 이미지 슬라이드 띄어줌
    private int[] bannerImages ={R.drawable.slide1, R.drawable.slide2,R.drawable.slide3};

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.home_fragment, container, false);

        /*리스트 프래그먼트 */
        FragmentManager fm = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.add(R.id.Frg_med_alarm, new HomeAlarmFragment());
        fragmentTransaction.commit();



        CircleIndicator indicator = root.findViewById(R.id.indicator_home); // 인디케이터
        CircleIndicator indicator2 = root.findViewById(R.id.indicator_home2); // 인디케이터

        /*동적으로 배너 크기 바끄기 */
        DisplayMetrics displayMetrics = new DisplayMetrics();
        WindowManager windowManager = (WindowManager) getContext().getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
        windowManager.getDefaultDisplay().getMetrics(displayMetrics);
        int deviceHeight = displayMetrics.heightPixels/3-5;
        LinearLayout viewP = root.findViewById(R.id.viewP);
        LayoutParams lay = (LayoutParams) viewP.getLayoutParams();
        lay.height = deviceHeight;
        viewP.setLayoutParams(lay);

        viewPager =  root.findViewById(R.id.banner);
        viewPager2 =  root.findViewById(R.id.banner2);
        adapter = new ViewPagerAdapter(getActivity(),images,1);
        bannerAdapter =  new ViewPagerAdapter(getActivity(),bannerImages);


        /*첫 번째 배너 사이 간격 조정*/
        int dpValue = 40;
        float d = getResources().getDisplayMetrics().density;
        final int margin = (int) (dpValue * d);
        viewPager.setClipToPadding(false);
        viewPager.setPadding(margin, 0, margin, 0);
        viewPager.setPageMargin(margin/2);


        viewPager.setAdapter(adapter);
        viewPager2.setAdapter(bannerAdapter);
        viewPager.setCurrentItem(1);
        indicator.setViewPager(viewPager);
        indicator2.setViewPager(viewPager2);





        //배너2 자동스크롤
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
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(Update);
            }
        }, DELAY_MS, PERIOD_MS);



        return root;



    }

}
