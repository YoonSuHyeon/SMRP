package com.example.smrp.medicine;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.fragment.app.DialogFragment;

import com.example.smrp.R;

/* 아직 각 버튼에 관한 구현은 하지 않았다 */
public class PopupFragment extends DialogFragment { // 약 등록하기 Fragment에서 +버튼 눌렀을 때 띄어주는 Dialog 창



    private ImageView Img_med_shoot; // 약 촬영 아이콘
    private ImageView Img_envelope; // 약 봉투 아이콘
    private ImageView Img_prescription; // 처방전 아이콘
    private Button Btn_cancel; // 취소 아이콘

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.medicine_dialog, container, false);

        //
        Img_med_shoot = view.findViewById(R.id.Img_med_shoot);
        Img_envelope = view.findViewById(R.id.Img_envelope);
        Img_prescription = view.findViewById(R.id.Img_prescription);
        Btn_cancel = view.findViewById(R.id.Btn_cancel);
        //

        /* 클릭 이벤트 */
        //1. 약 촬용 아이콘 클릭시
        Img_med_shoot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Shoot", Toast.LENGTH_LONG).show(); // 임시 메세지

            }
        });

        //2. 약 봉트 아이콘 클릭시
        Img_envelope.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "envelope", Toast.LENGTH_LONG).show(); // 임시 메세지
            }
        });

        //3. 처방전 아이콘 클릭시
        Img_prescription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "prescription", Toast.LENGTH_LONG).show();// 임시 메세지
            }
        });

        //4. 취소 버튼 클릭 시
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
