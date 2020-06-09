package com.example.smrp.alarm;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.example.smrp.R;
import com.example.smrp.RetrofitHelper;
import com.example.smrp.RetrofitService;
import com.example.smrp.reponse_medicine2;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BottomSheetDialog extends BottomSheetDialogFragment {
    private   String userId;
    private   String itemSeq;
    public  static BottomSheetDialog getInstance() {
        return new BottomSheetDialog();
    }
    public void init(String userId,String itemSeq){
        this.userId=userId;
        this.itemSeq=itemSeq;
    }


    private LinearLayout Lay_delete;
    private LinearLayout Lay_cancel;

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bottom_sheet, container, false);
        Lay_delete = (LinearLayout) view.findViewById(R.id.Lay_delete);
        Lay_cancel = (LinearLayout) view.findViewById(R.id.Lay_cancel);


        Lay_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "delete", Toast.LENGTH_SHORT).show();


                RetrofitService networkService= RetrofitHelper.getRetrofit().create(RetrofitService.class);

                Call<String> call = networkService.deleteRegister(userId,itemSeq);
                call.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {


                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {


                    }
                });


                getActivity().onBackPressed();
            }
        });
        Lay_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "cancel", Toast.LENGTH_SHORT).show();
                getActivity().onBackPressed();
            }
        });


       // dismiss();
        return view;
    }
}