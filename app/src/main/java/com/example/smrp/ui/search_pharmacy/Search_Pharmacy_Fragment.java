package com.example.smrp.ui.search_pharmacy;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.smrp.R;
import com.example.smrp.ui.home.HomeViewModel;
import com.google.android.gms.maps.MapView;

public class Search_Pharmacy_Fragment extends Fragment {
    private HomeViewModel homeViewModel;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_find_pharmacy, container, false);
        MapView mapView = new MapView(root.getContext());
        ViewGroup mapViewContainer = root.findViewById(R.id.mapview);
        mapView.addView(mapViewContainer);

        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {

            }
        });
        return root;
    }
}
