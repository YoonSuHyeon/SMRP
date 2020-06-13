package com.example.smrp.report;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.example.smrp.R;

public class ReportNoticeFragment extends Fragment {
   // ImageView iv_back;
    Button Btn_start;
    private ReportViewModel reportViewModel;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        reportViewModel =
                ViewModelProviders.of(this).get(ReportViewModel.class);
        View root = inflater.inflate(R.layout.activity_report_notice, container, false);

       // iv_back = root.findViewById(R.id.iv_back);
        Btn_start =root. findViewById(R.id.Btn_start);


        Btn_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavHostFragment navHostFragment =
                        (NavHostFragment) ((AppCompatActivity) getContext()).getSupportFragmentManager()
                                .findFragmentById(R.id.nav_host_fragment);
                NavController navController = navHostFragment.getNavController();
                navController.navigate(R.id.action_nav_report_notice_to_nav_report);

            }
        });
/*        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();

            }
        });*/

        return root;

    }

}
