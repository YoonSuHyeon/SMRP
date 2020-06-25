package com.example.smrp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Fragment_id extends Fragment {
    String name,email,id;
    findIdActivity findIdActivity;
    EditText et_name;
    EditText et_email;
    Button btn_findId;
    AlertDialog.Builder dialog;

    private FragmentManager fragmentManager;
    private FragmentTransaction transaction;
    private Fragment_password fragment_password;
    private LinearLayout ll_id,ll_password;
    private TextView tv_id,tv_password;
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        findIdActivity=(findIdActivity)getActivity();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        findIdActivity=null;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootview=(ViewGroup)inflater.inflate(R.layout.find_id,container,false);

        dialog = new AlertDialog.Builder(getContext());
        et_name =rootview.findViewById(R.id.et_name); // 사용자 이름
        et_email =rootview.findViewById(R.id.et_email); // ID를 받을 이메일 주소
        btn_findId = rootview.findViewById(R.id.btn_findId); // 아이디 찾기 버튼

        fragment_password = new Fragment_password();
        fragmentManager = getActivity().getSupportFragmentManager();

        final findIdActivity findIdActivity = com.example.smrp.findIdActivity.getInstance();
        final Change_Password change_password = findIdActivity.getChang_Password();



        btn_findId.setOnClickListener(new View.OnClickListener() { //이름 이메일 필
            @Override
            public void onClick(View v) {

                transaction = fragmentManager.beginTransaction();
                name = et_name.getText().toString();
                email = et_email.getText().toString();

                RetrofitService networkService=RetrofitHelper.getRetrofit().create(RetrofitService.class);
                Call<response> call = networkService.findId(name,email);

                call.enqueue(new Callback<response>() {
                    @Override
                    public void onResponse(Call<response> call, Response<response> response) {
                        Log.d("TAG", "onResponse: "+response.body().getResponse());
                        id = response.body().getResponse();
                        if(id.length()>0&&!id.equals("404")){
                            dialog.setTitle("아이디 찾기 성공");
                            SpannableStringBuilder sp = new SpannableStringBuilder("귀하의 아이디는 "+id+" 입니다.");
                            sp.setSpan(new ForegroundColorSpan(Color.RED),9,9+id.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                            dialog.setMessage(sp);

                            dialog.setNegativeButton("비밀번호 찾기", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {


                                    Log.d("TAG", "id: "+id);
                                    Log.d("TAG", "name: "+name);
                                    Log.d("TAG", "email: "+email);

                                    change_password.setEmail(email);
                                    change_password.setName(name);
                                    change_password.setUserId(id);

                                    transaction.replace(R.id.frame_layout,fragment_password).commitAllowingStateLoss();
                                    findIdActivity.tv_id.setTextColor(Color.parseColor("#666464"));
                                    findIdActivity.ll_id.setBackgroundColor(Color.parseColor("#666464"));
                                    findIdActivity.tv_password.setTextColor(Color.parseColor("#2196F3"));
                                    findIdActivity.ll_password.setBackgroundColor(Color.parseColor("#2196F3"));
                                    dialog.dismiss();
                                    et_name.setText(""); //사용자 이름 초기화
                                    et_email.setText(""); //사용자 비밀 초기화
                                }
                            });
                        }else{
                            dialog.setTitle("아이디 찾기 실패");
                            dialog.setMessage("사용자 계정이 없습니다.");
                        }
                        dialog.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });

                        dialog.show();
                    }

                    @Override
                    public void onFailure(Call<response> call, Throwable t) {

                    }
                });
            }
        });
        return rootview;
    }
}
