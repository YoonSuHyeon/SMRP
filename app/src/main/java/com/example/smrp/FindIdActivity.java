package com.example.smrp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class FindIdActivity extends AppCompatActivity {
    LinearLayout ll_fragment_id,ll_fragment_password,ll_id,ll_password;
    TextView tv_id,tv_password;
    ImageView iv_back;
    private FragmentManager fragmentManager;
    private Fragment_id fragment_id;
    private Fragment_password fragment_password;
    private FragmentTransaction transaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_id);

        iv_back = findViewById(R.id.iv_back);
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        ll_fragment_id=findViewById(R.id.ll_fragment_id);
        ll_fragment_password= findViewById(R.id.ll_fragment_password);
        ll_id = findViewById(R.id.ll_id);
        ll_password = findViewById(R.id.ll_password);
        tv_id = findViewById(R.id.tv_id);
        tv_password=findViewById(R.id.tv_password);

        View.OnClickListener clickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                transaction = fragmentManager.beginTransaction();

                switch (v.getId()){

                    case R.id.ll_fragment_id:{

                        transaction.replace(R.id.frame_layout,fragment_id).commitAllowingStateLoss();
                        tv_id.setTextColor(Color.parseColor("#2196F3"));
                        ll_id.setBackgroundColor(Color.parseColor("#2196F3"));
                        tv_password.setTextColor(Color.parseColor("#666464"));
                        ll_password.setBackgroundColor(Color.parseColor("#666464"));
                        break;
                    }
                    case R.id.ll_fragment_password:{

                        transaction.replace(R.id.frame_layout,fragment_password).commitAllowingStateLoss();
                        tv_id.setTextColor(Color.parseColor("#666464"));
                        ll_id.setBackgroundColor(Color.parseColor("#666464"));
                        tv_password.setTextColor(Color.parseColor("#2196F3"));
                        ll_password.setBackgroundColor(Color.parseColor("#2196F3"));
                        break;
                    }

                }
            }
        };
        ll_fragment_id.setOnClickListener(clickListener);
        ll_fragment_password.setOnClickListener(clickListener);


        fragmentManager = getSupportFragmentManager();

        fragment_id = new Fragment_id();
        fragment_password = new Fragment_password();

        transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.frame_layout,fragment_id).commitAllowingStateLoss();
    }

   /*public void onClick(View view){

    }*/
}
