package com.example.smrp.searchPrescription;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smrp.R;
import com.example.smrp.reponse_medicine;

import java.io.Serializable;
import java.util.ArrayList;

public class Select_Pill extends AppCompatActivity implements Serializable { //사진을 찍은 후에 약 목록들을 보여주는 클래스

    private  ArrayList<reponse_medicine> intent_list;
    private ArrayList<Prescriptionitem> list1;
    ImageView back_imgView;
    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_pill);

        Intent intent = getIntent();



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
                list1.add(new Prescriptionitem(intent_list.get(i).getItemImage(),intent_list.get(i).getItemName(),intent_list.get(i).getEntpName(),
                        intent_list.get(i).getFormCodeName(),intent_list.get(i).getEtcOtcName()));
            }
        }catch(NullPointerException e){
            e.printStackTrace();
        }


        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        PrescriptionAdapter adapter = new PrescriptionAdapter(list1);
        recyclerView.setAdapter(adapter);
    }
}
