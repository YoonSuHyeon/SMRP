package com.example.smrp;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class findIdActivity extends AppCompatActivity {
    LinearLayout ll_fragment_id,ll_fragment_password,ll_id,ll_password;
    TextView tv_id,tv_password;
    ImageView iv_back;
    Button btn_findId,btn_findPassword;
    EditText et_name,et_email,et_pass_name,et_pass_id,et_pass_email;
    String name,email,id;

    private FragmentManager fragmentManager;
    private Fragment_id fragment_id;
    private Fragment_password fragment_password;
    private FragmentTransaction transaction;
    private static findIdActivity findIdActivity;
    private Change_Password change_password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_id);

        findIdActivity = this;

        //btn_findId=findViewById(R.id.btn_findId);
        //btn_findPassword=findViewById(R.id.btn_findPassword);
        //et_name =findViewById(R.id.et_name);
        //et_email = findViewById(R.id.et_email);
        //et_pass_name = findViewById(R.id.et_pass_name);
        //et_pass_id = findViewById(R.id.et_pass_id);
        //et_pass_email = findViewById(R.id.et_pass_email);


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
        tv_id = findViewById(R.id.tv_id); // 아이디 찾기 배너
        tv_password=findViewById(R.id.tv_password); // 비밀번호 찾기 배너

        change_password = new Change_Password("","","","");
        View.OnClickListener clickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                transaction = fragmentManager.beginTransaction();
                findIdActivity = new findIdActivity();
                switch (v.getId()){

                    case R.id.ll_fragment_id:{ //아이디찾기 내용을 표시할 레이아웃

                        transaction.replace(R.id.frame_layout,fragment_id).commitAllowingStateLoss();  // fragment_id => Fragment_id 클래스 객체
                        tv_id.setTextColor(Color.parseColor("#2196F3"));                     // frame_layout : 아이디, 비밀번호 찾기의 디자인을 표실할 구역
                        ll_id.setBackgroundColor(Color.parseColor("#2196F3"));
                        tv_password.setTextColor(Color.parseColor("#666464"));
                        ll_password.setBackgroundColor(Color.parseColor("#666464"));
                        break;
                    }
                    case R.id.ll_fragment_password:{ //비밀번호찾기 내용을 표실할 레이아웃
                        // fragment_password => fragment_password 클래스 객체
                        transaction.replace(R.id.frame_layout,fragment_password).commitAllowingStateLoss();  // frame_layout : 아이디, 비밀번호 찾기의 디자인을 표실할 구역
                        tv_id.setTextColor(Color.parseColor("#666464"));
                        ll_id.setBackgroundColor(Color.parseColor("#666464"));
                        tv_password.setTextColor(Color.parseColor("#2196F3"));
                        ll_password.setBackgroundColor(Color.parseColor("#2196F3"));
                        break;
                    }

                }
            }
        };
        ll_fragment_id.setOnClickListener(clickListener); //아이디 찾기
        ll_fragment_password.setOnClickListener(clickListener); //비밀번호찾기


        fragmentManager = getSupportFragmentManager();

        fragment_id = new Fragment_id();
        fragment_password = new Fragment_password();

        transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.frame_layout,fragment_id).commitAllowingStateLoss();


    }

   /*public void onClick(View view){

    }*/

    public static  findIdActivity getInstance(){
        return findIdActivity;
    }
    public Change_Password getChang_Password(){
        return change_password;
    }
}