package com.example.smrp.searchPrescription;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smrp.R;

import java.util.ArrayList;

public class Select_Pill extends AppCompatActivity { //사진을 찍은 후에 약 목록들을 보여주는 클래스

    private  ArrayList<Prescriptionitem> intent_list ,list1;
    Button back_button;
    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_pill);


        intent_list = (ArrayList<Prescriptionitem>) getIntent().getSerializableExtra("list");

        back_button = findViewById(R.id.back_btn);

        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        RecyclerView recyclerView = findViewById(R.id.recycler_pill);
        list1 = new ArrayList<Prescriptionitem>();

        try{
            for(int i = 0; i < intent_list.size();i++){
                list1.add(new Prescriptionitem(intent_list.get(i).getStringURL(),intent_list.get(i).getText1(),intent_list.get(i).getText2(),
                        intent_list.get(i).getText3(),intent_list.get(i).getText4()));
            }
        }catch(NullPointerException e){
            e.printStackTrace();
        }


        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        PrescriptionAdapter adapter = new PrescriptionAdapter(list1);
        recyclerView.setAdapter(adapter);
    }
}
