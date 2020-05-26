package com.example.smrp.searchPrescription;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smrp.R;
import com.example.smrp.RetrofitHelper;
import com.example.smrp.RetrofitService;
import com.example.smrp.User;
import com.example.smrp.reponse_medicine;
import com.example.smrp.response;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Select_Pill extends AppCompatActivity implements Serializable { //사진을 찍은 후에 약 목록들을 보여주는 클래스

    private ArrayList<reponse_medicine> intent_list;
    private ArrayList<Prescriptionitem> list1;
    private ArrayList<User_Select> list;
    private ArrayList<String> itemseq_list;
    private Button add_Btn;
    private PrescriptionAdapter adapter;
    private HashMap<Integer,String> select_pill_list; //사용자 선택한 약 정보를 담는 hashmap
    private String id ="cc";
    ImageView back_imgView;
    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_pill);

        add_Btn = findViewById(R.id.add_btn);
        Intent intent = getIntent();

        itemseq_list = new ArrayList<String>();


        add_Btn.setOnClickListener(new View.OnClickListener() { //추가하기 버튼 누를시
            @Override
            public void onClick(View v) {
                Log.d("TAG", "total size(): "+select_pill_list.size()+"\n");
                Log.d("TAG", "result==>");

                for(Map.Entry<Integer,String>elem : select_pill_list.entrySet())
                    itemseq_list.add(elem.getValue());
                    //Log.d("TAG", "==>: "+elem.getKey()+","+elem.getValue()+"\n");
                RetrofitService networkService= RetrofitHelper.getRetrofit().create(RetrofitService.class);
                User_Select user_select = new User_Select(id,itemseq_list);
                Call<response> call = networkService.addSelectMedicine(user_select);
                call.enqueue(new Callback<response>() {
                    @Override
                    public void onResponse(Call<response> call, Response<response> response) {
                        Log.d("TAG", "add_Btn_Success: ");
                        onBackPressed();
                    }

                    @Override
                    public void onFailure(Call<response> call, Throwable t) {
                        Log.d("TAG", "add_Btn_Fail: ");
                        onBackPressed();
                    }
                });

            }
        });



        intent_list = (ArrayList<reponse_medicine>)intent.getSerializableExtra("list");



        Log.d("TAG", "intent_list.size(): "+intent_list.size());
        for(int i = 0 ;i < intent_list.size();i++){
            Log.d("TAG", "intent_list: "+intent_list.get(i).getItemName());

        }
       // intent_list = new  ArrayList<Prescriptionitem>();
        list1 = new ArrayList<Prescriptionitem>();
        //Prescriptionitem p = new Prescriptionitem("ㅇㄴㅇㄴㅁㅇㅁㄴ","2","3","4","5");
        //intent_list.add(p);
        back_imgView = findViewById(R.id.back_btn);

        back_imgView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        RecyclerView recyclerView = findViewById(R.id.recycler_list);
        list1 = new ArrayList<Prescriptionitem>();


        try{
            for(int i = 0; i < intent_list.size();i++){
                list1.add(new Prescriptionitem(intent_list.get(i).getItemSeq(),intent_list.get(i).getItemImage(),intent_list.get(i).getItemName(),intent_list.get(i).getEntpName(),
                        intent_list.get(i).getFormCodeName(),intent_list.get(i).getEtcOtcName()));
                Log.d("TAG", "intent_list.get(i).getItemSeq(): "+intent_list.get(i).getItemSeq());
            }
        }catch(NullPointerException e){
            e.printStackTrace();
        }


        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        adapter = new PrescriptionAdapter(list1);
        recyclerView.setAdapter(adapter);
        LinearLayoutManager mlinearLayoutManager = new LinearLayoutManager(getApplicationContext());
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), mlinearLayoutManager.getOrientation());//구분선을 넣기 위함
        recyclerView.addItemDecoration(dividerItemDecoration);

        adapter.setOnClickListener(new PrescriptionAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(PrescriptionAdapter.ViewHolder holder, View v, int position) {
                Log.d("TAG", "onItemClick: "+list1.get(position).getText1());
                if(select_pill_list == null){ //생성자 생성
                    select_pill_list = new HashMap<Integer, String>();
                }
                if(select_pill_list.size()==0){//초기
                    select_pill_list.put(position,list1.get(position).getItemSeq());
                    Log.d("TAG", "select_pill_list.size()==0: "+list1.get(position).getItemSeq()+"."+list1.get(position).getText1());
                }else{
                    if(select_pill_list.get(position)==null){ //선택한 약이 hashmap에 없을경우
                        select_pill_list.put(position,list1.get(position).getItemSeq());
                    }else{ //선택한 약이 hashmap에 있을경우 삭제 : 사용자가 2번 누른것
                        select_pill_list.remove(position);
                    }
                }

            }


        });
    }
}
