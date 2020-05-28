package com.example.smrp.alarm;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.smrp.R;
import com.example.smrp.RetrofitHelper;
import com.example.smrp.RetrofitService;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BottomSheetDialog2 extends BottomSheetDialogFragment {
    private   String userId;
    private   String itemSeq;
    public  static BottomSheetDialog2 getInstance() {
        return new BottomSheetDialog2();
    }
    public void init(String userId,String itemSeq){
        this.userId=userId;
        this.itemSeq=itemSeq;
    }


    private LinearLayout Lay_delete;
    private LinearLayout Lay_cancel;
    private LinearLayout Lay_edit;
    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bottom_sheet_ver_alarm, container, false);
        Lay_delete = (LinearLayout) view.findViewById(R.id.Lay_delete);
        Lay_cancel = (LinearLayout) view.findViewById(R.id.Lay_cancel);
        Lay_edit = (LinearLayout) view.findViewById(R.id.Lay_edit);

        Lay_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "delete", Toast.LENGTH_SHORT).show();


           // AlarmSetActivity 의 List 에서 삭제


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
        Lay_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "edit", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getActivity(), AlarmEditActivity.class);
                //Intent intent = new Intent(getContext().getApplicationContext(), MedicineDetailActivity.class);

                startActivity(intent);



                    //  getActivity().onBackPressed();
            }
        });


       // dismiss();
        return view;
    }
}