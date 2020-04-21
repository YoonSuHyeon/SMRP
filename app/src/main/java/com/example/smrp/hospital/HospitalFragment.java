package com.example.smrp.hospital;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.smrp.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HospitalFragment extends Fragment {
    private View root;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        root = inflater.inflate(R.layout.hospital_fragment, container, false);

        RetrofitService json = new RetrofitFactory().create();
        json.getList(37.303633,127.920252,500).enqueue(new Callback<Return_inf>() {
            @Override
            public void onResponse(Call<Return_inf> call, Response<Return_inf> response) {
                
            }

            @Override
            public void onFailure(Call<Return_inf> call, Throwable t) {

            }
        });

        return root;
    }

}
