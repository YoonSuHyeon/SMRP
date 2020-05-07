package com.example.smrp.medicine;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.smrp.R;

public class MedicineDetailActivity extends AppCompatActivity {

    ImageView medicineImage;//약 사진
    ImageView iv_back; //뒤로가기 이미지뷰

    Button addMedicine; //추가하기 버튼
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicine_detail);

        //Init
        addMedicine=findViewById(R.id.btn_add);
        medicineImage=findViewById(R.id.iv_medicine);
        iv_back=findViewById(R.id.iv_back);

        //Image 등록
        Glide.with(this).load("https://lh3.googleusercontent.com/proxy/z5m61I7Jz2jDC56-WPtNa2ddl2zFUSasdcyTfqN8migJLE6xOwzbt7AsJv2wWo0B81jFvX0x4UlQKSDe6HZKKu4e7ByOnfTBZf-P9fim6zQ").into(medicineImage);


        //뒤로가기 버튼
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        //추가하기 버튼
        addMedicine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//추가 하기 버튼을 눌렀을때 서버에게 현재 자기가 등록 한 약이 무엇이다라는 것을 알려준다.

            }
        });

    }
}
