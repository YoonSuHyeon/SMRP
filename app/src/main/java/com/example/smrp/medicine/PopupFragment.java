package com.example.smrp.medicine;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.fragment.app.DialogFragment;

import com.example.smrp.R;
import com.example.smrp.alarm.AlarmDetailActivity;
import com.example.smrp.alarm.AlarmSetFragment;
import com.example.smrp.camera.CameraActivity;
import com.example.smrp.searchMed.SearchActivity;
import com.example.smrp.searchPrescription.Search_prescription;
import com.example.smrp.alarm.AlarmDetailActivity;

/* 아직 각 버튼에 관한 구현은 하지 않았다 */
public class PopupFragment extends DialogFragment { // 약 등록하기 Fragment에서 +버튼 눌렀을 때 띄어주는 Dialog 창



    private ImageView Img_med_shoot; // 약 촬영 아이콘
    private ImageView Img_envelope; // 약 봉투 아이콘
    private ImageView Img_prescription; // 처방전 아이콘
    private ImageView Img_search; // 약명검색 아이콘
    private Button Btn_cancel; // 취소 아이콘
    private Intent intent;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.medicine_dialog, container, false);

        //
        Img_med_shoot = view.findViewById(R.id.Img_med_shoot);
        Img_envelope = view.findViewById(R.id.Img_envelope);
        Img_prescription = view.findViewById(R.id.Img_prescription);
        Img_search = view.findViewById(R.id.Img_search);
        Btn_cancel = view.findViewById(R.id.Btn_cancel);
        //

        /* 클릭 이벤트 */
        //1. 약 촬영 아이콘 클릭시
        Img_med_shoot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //현재 사진 촬영 단계를 건너뛴 다음 액티비티 약 상세정보 액티비티로 전환 시킴.

                intent = new Intent(getContext().getApplicationContext(), CameraActivity.class);
                //Intent intent = new Intent(getContext().getApplicationContext(), MedicineDetailActivity.class);
                startActivity(intent);
                Toast.makeText(getActivity(), "Shoot", Toast.LENGTH_LONG).show(); // 임시 메세지
                dismiss();

            }
        });

        //2. 약 봉투 아이콘 클릭시
        Img_envelope.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                intent = new Intent(getContext().getApplicationContext(), CameraActivity.class);
                startActivity(intent);
                Toast.makeText(getActivity(), "envelope", Toast.LENGTH_LONG).show(); // 임시 메세지
                dismiss();
            }
        });

        //3. 처방전 아이콘 클릭시
        Img_prescription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(getContext().getApplicationContext(), Search_prescription.class);//
                startActivity(intent);
                dismiss();
            }
        });

        //4. 약명 검색 아이콘 클릭시
        Img_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext().getApplicationContext(), SearchActivity.class);
                startActivity(intent);
                dismiss();
                Toast.makeText(getActivity(), "seach", Toast.LENGTH_LONG).show();// 임시 메세지
            }
        });

        //5. 취소 버튼 클릭 시
        Btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "cancel", Toast.LENGTH_LONG).show(); // 임시 메세지
                dismiss(); // 창 없애기(이때 팝업 창 말고 빈 화면을 클릭 했을 시 팝업창을 닫을 것인지 아니면 취소 버튼을 눌러야지만 닫힐 것인지 생각해 볼 것.)
            }
        });

        return view;
    }

}
