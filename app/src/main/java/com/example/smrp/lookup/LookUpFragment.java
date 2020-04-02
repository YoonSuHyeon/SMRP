package com.example.smrp.lookup;

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

public class LookUpFragment extends Fragment {

    private LookUpViewModel lookUpViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        lookUpViewModel =
                ViewModelProviders.of(this).get(LookUpViewModel.class);
        View root = inflater.inflate(R.layout.look_up_fragment, container, false);
        final TextView textView = root.findViewById(R.id.text_look_up);
        lookUpViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }

}
