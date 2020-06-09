package com.example.smrp.alarm;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import android.util.Log;
import android.view.LayoutInflater;

import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageView;
import android.widget.ListView;

import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;

import com.example.smrp.MainActivity;
import com.example.smrp.R;
import com.example.smrp.RetrofitHelper;
import com.example.smrp.RetrofitService;
import com.example.smrp.medicine.ListViewItem;
import com.example.smrp.medicine.ViewPagerAdapter;

import java.util.ArrayList;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import me.relex.circleindicator.CircleIndicator;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class AlarmFragment extends Fragment {
    // 배너 ViewPager
    private ViewPagerAdapter adapter;
    private ListViewAdapter2 listViewAdapter;
    private ViewPager viewPager;
    private ListView Lst_medicine; // 등록한 약 목록(아직 구현x)
    private TextView Txt_empty; // 등록한 약이 없을 시 text메세지로 알려줌
    private ImageView Img_ic_plus; // +아이콘
    private MainActivity home;
    private final long DELAY_MS = 1000; // 자동 슬라이드를 위한 변수
    private final long PERIOD_MS = 3000; // 자동 슬라이드를 위한 변수
    private int currentPage = 0; // 자동 슬라이드를 위한 변수(현재 페이지)
    private Timer timer; // 자동 슬라이드를 위한 변수


    private String user_id;
    ArrayList<ListViewAlarmItem> items = new ArrayList<ListViewAlarmItem>();

    private int[] images= {R.drawable.slide1, R.drawable.slide2,R.drawable.slide3}; // ViewPagerAdapter에  보낼 이미지. 이걸로 이미지 슬라이드 띄어줌

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        home = (MainActivity) getActivity();
        SharedPreferences loginInfromation = getActivity().getSharedPreferences("setting",0);
        user_id = loginInfromation.getString("id","");
        View v = inflater.inflate(R.layout.alarm_fragment, container, false);
        CircleIndicator indicator = v.findViewById(R.id.indicator); // 인디케이터

        viewPager =  v.findViewById(R.id.banner);
        adapter = new ViewPagerAdapter(getActivity(),images);
        viewPager.setAdapter(adapter);
        indicator.setViewPager(viewPager); // 인디케이터 뷰에 추가

        //
        Lst_medicine = v.findViewById(R.id.Lst_medicine);
        Txt_empty = v.findViewById(R.id.Txt_empty);
        Lst_medicine.setEmptyView(Txt_empty);
        Img_ic_plus = v.findViewById(R.id.Img_ic_plus);
        //


        listViewAdapter= new ListViewAdapter2(items,getActivity());
        Lst_medicine.setAdapter(listViewAdapter);

        /* 클릭 이벤트들 */
        //1. +아이콘 클릭시
        Img_ic_plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext().getApplicationContext(), AlarmSetActivity.class);
                startActivity(intent);


            }
        });

        /* 자동 슬라이드*/
        final Handler handler = new Handler();
        final Runnable Update = new Runnable() {
            @Override
            public void run() {
                if(currentPage == 3) { // 현재페이지가 맨 끝일 시 맨 처음 페이지로 돌아간다.
                    currentPage = 0;
                }

                viewPager.setCurrentItem(currentPage++, true);
            }
        };
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(Update);
            }
        }, DELAY_MS, PERIOD_MS);


        RetrofitService networkService= RetrofitHelper.getRetrofit().create(RetrofitService.class);

        //서버에게 사용자 ID를 보낸후  등록된 약들을 받아서 Adapter에 등록한다.


        //String id  사용자 id를 가져와야함





        return v;


    }
    public void onStart() {
        super.onStart();


        RetrofitService networkService= RetrofitHelper.getRetrofit().create(RetrofitService.class);
        Call<ArrayList<Response_AlarmMedicine>> call = networkService.getList(user_id);
        call.enqueue(new Callback<ArrayList<Response_AlarmMedicine>>() {
            @Override
            public void onResponse(Call<ArrayList<Response_AlarmMedicine>> call, Response<ArrayList<Response_AlarmMedicine>> response) {
                List<Response_AlarmMedicine> reponse_medicines =response.body();
                items.clear();

                for(int i = 0; i<  reponse_medicines.size(); i++)
                {
                    items.add(new ListViewAlarmItem(reponse_medicines.get(i).getAlramName(),reponse_medicines.get(i).getStartAlram(),reponse_medicines.get(i).getAlramGroupId()));


                }
                listViewAdapter.notifyDataSetChanged();
                //Toast.makeText(getApplicationContext(),"사용 가능한 아이디입니다.",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<ArrayList<Response_AlarmMedicine>> call, Throwable t) {


            }
        });
    }
}
