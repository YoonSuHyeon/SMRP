package com.example.smrp.inquiry;

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

public class InquiryFragment extends Fragment {

    private InquiryViewModel inquiryViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        inquiryViewModel =
                ViewModelProviders.of(this).get(InquiryViewModel.class);
        View root = inflater.inflate(R.layout.inquiry_fragment, container, false);
        /*final TextView textView = root.findViewById(R.id.text_inquiry);
        inquiryViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });*/
        return root;
    }

}
