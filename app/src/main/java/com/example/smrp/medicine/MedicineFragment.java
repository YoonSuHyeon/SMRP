package com.example.smrp.medicine;
import androidx.lifecycle.ViewModelProviders;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.smrp.MainActivity;
import com.example.smrp.R;
import com.example.smrp.RetrofitHelper;
import com.example.smrp.RetrofitService;
import com.example.smrp.home.HomeFragment;
import com.example.smrp.reponse_medicine;
import com.example.smrp.response;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import me.relex.circleindicator.CircleIndicator;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MedicineFragment extends Fragment {
    // 배너 ViewPager
    private ViewPagerAdapter adapter;
    private ListViewAdapter listViewAdapter;
    private ViewPager viewPager;
    private ListView Lst_medicine; // 등록한 약 목록(아직 구현x)
    private TextView Txt_empty; // 등록한 약이 없을 시 text메세지로 알려줌
    private ImageView Img_ic_plus; // +아이콘
    private MainActivity home;
    private final long DELAY_MS = 1000; // 자동 슬라이드를 위한 변수
    private final long PERIOD_MS = 3000; // 자동 슬라이드를 위한 변수
    private int currentPage = 0; // 자동 슬라이드를 위한 변수(현재 페이지)
    private Timer timer; // 자동 슬라이드를 위한 변수

    ArrayList<ListViewItem> items = new ArrayList<ListViewItem>();

    private int[] images= {R.drawable.slide1, R.drawable.slide2,R.drawable.slide3}; // ViewPagerAdapter에  보낼 이미지. 이걸로 이미지 슬라이드 띄어줌
    private MedicineViewModel medicineViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        medicineViewModel =
                ViewModelProviders.of(this).get(MedicineViewModel.class);

        home = (MainActivity) getActivity();

        View v = inflater.inflate(R.layout.medicine_fragment, container, false);
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


        listViewAdapter=new ListViewAdapter(items,getActivity());
        Lst_medicine.setAdapter(listViewAdapter);

        /* 클릭 이벤트들 */
        //1. +아이콘 클릭시
        Img_ic_plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupFragment p = new PopupFragment(); // DialogFragment(약촬영, 약봉투, 처방전 팝업창을 위한 프레그먼트)
                p.show(getActivity().getSupportFragmentManager(),"popup"); //팝업 창 띄우기

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




        //서버에게 사용자 ID를 보낸후  등록된 약들을 받아서 Adapter에 등록한다.

        RetrofitService networkService= RetrofitHelper.getRetrofit().create(RetrofitService.class);

        //String id  사용자 id를 가져와야함
        String id ="cc";
        Call<List<reponse_medicine>> call = networkService.findUserMedicine(id);
        call.enqueue(new Callback<List<reponse_medicine>>() {
            @Override
            public void onResponse(Call<List<reponse_medicine>> call, Response<List<reponse_medicine>> response) {
                List<reponse_medicine> reponse_medicines =response.body();
                items.clear();

                for(int i = 0; i<  reponse_medicines.size(); i++)
                {
                    items.add(new ListViewItem(reponse_medicines.get(i).getItemImage(),reponse_medicines.get(i).getItemName(),reponse_medicines.get(i).getItemSeq()));
                    /*Log.d("dfsdazxcv",reponse_medicines.get(i).getItemImage().toString());
                    Log.d("dfsdazxcv",reponse_medicines.get(i).getItemName());*/
                }
                listViewAdapter.notifyDataSetChanged();
                //Toast.makeText(getApplicationContext(),"사용 가능한 아이디입니다.",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<List<reponse_medicine>> call, Throwable t) {
                Log.d("ddd",t.toString());

            }
        });



        return v;


    }




}
// 약 리스트(커스텀 ) - 클랫
