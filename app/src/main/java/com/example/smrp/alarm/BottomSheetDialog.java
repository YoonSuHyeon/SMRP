package com.example.smrp.alarm;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.smrp.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class BottomSheetDialog extends BottomSheetDialogFragment {

    public static BottomSheetDialog getInstance() { return new BottomSheetDialog(); }

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