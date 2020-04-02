package com.example.smrp.mask;

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

public class MaskFragment extends Fragment {

    private MaskViewModel maskViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        maskViewModel =
                ViewModelProviders.of(this).get(MaskViewModel.class);
        View root = inflater.inflate(R.layout.mask_fragment, container, false);
        final TextView textView = root.findViewById(R.id.text_mask);
        maskViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }

}
