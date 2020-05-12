package com.example.smrp;

import android.content.Context;
import android.os.Bundle;
import android.os.StrictMode;
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

public class Fragment_id extends Fragment {
    String name,email;
    findIdActivity findIdActivity;
    EditText et_name;
    EditText et_email;
    Button btn_findId;

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

        et_name =rootview.findViewById(R.id.et_name);
        et_email =rootview.findViewById(R.id.et_email);
        btn_findId = rootview.findViewById(R.id.btn_findId);
        btn_findId.setOnClickListener(new View.OnClickListener() { //이름 이메일 필
            @Override
            public void onClick(View v) {
                name = et_name.getText().toString();
                email = et_email.getText().toString();
                RetrofitService networkService=RetrofitHelper.getRetrofit().create(RetrofitService.class);
                Call<response> call = networkService.findId(name,email);

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
