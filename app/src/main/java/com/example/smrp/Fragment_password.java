package com.example.smrp;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Fragment_password extends Fragment {
    findIdActivity findIdActivity;
    String name,id,email;
    EditText et_pass_name,et_pass_id,et_pass_email;
    Button btn_findPassword;
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
        ViewGroup rootview=(ViewGroup)inflater.inflate(R.layout.find_password,container,false);
        et_pass_name =rootview.findViewById(R.id.et_pass_name);
        et_pass_id =rootview.findViewById(R.id.et_pass_id);
        et_pass_email =rootview.findViewById(R.id.et_pass_email);
        btn_findPassword = rootview.findViewById(R.id.btn_findPassword);
        btn_findPassword.setOnClickListener(new View.OnClickListener() {//이름, 아이디, 이메
            @Override
            public void onClick(View v) {
                name= et_pass_name.getText().toString();
                id = et_pass_id.getText().toString();
                email = et_pass_email.getText().toString();
                RetrofitService networkService=RetrofitHelper.getRetrofit().create(RetrofitService.class);
                Call<response> call = networkService.findPassword(name,id,email);

                call.enqueue(new Callback<response>() {
                    @Override
                    public void onResponse(Call<response> call, Response<response> response) {

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
