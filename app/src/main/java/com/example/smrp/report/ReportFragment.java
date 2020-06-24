package com.example.smrp.report;

import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Parcelable;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.smrp.R;

import java.util.ArrayList;

public class ReportFragment extends Fragment implements ReportRecyclerAdapter.OnItemClickListener{

    private ReportViewModel reportViewModel;
    ArrayList<ListItem> list = new ArrayList<ListItem>();
   // private SparseBooleanArray mSelectedItems = new SparseBooleanArray(0);
    private ArrayList<Integer> mSelectedItems = new ArrayList<Integer>();
    ReportRecyclerAdapter adapter;
    RecyclerView Lst_symptom;
    private Activity activity;
    Button Btn_submit;
    ArrayList<String> array_symptom;
    int count=0;
    View root;
    String symptom[] ={"가슴 통증", "심계항징", "요통", "공포", "구취", "기침", "두통", "만성 기침", "발열", "불안", "삼킴곤란", "시력 감소",
            "식욕 부진", "어지럼증", "인두통 및 인후통", "콧물", "트림", "호흡장애", "복통", "설사", "소화불량", "배뇨통", "생리불순", "요실금", "변비", "혈변", "구역", "구토", "근육통", "실신", "저혈압", "피로", "혼수", "발진", "소양감"};

    String symptom_content[]={"가슴이 아프거나 결리는 질환","심장 박동을 불편하게 느끼는 증상", "허리에 발생하는 통증", "두렵고 무서움" , "구강에서 나는 악취", "기침",
            "머리 부분에 생기는 통증", "3주 이상 지속되는 기침을 만성 기침", "37 ℃가 넘을 때", "마음이 편하지 아니하고 조마조마함", "음식물을 삼키기 어려운 증상", "사물을 인지하는 능력이 떨어지는 상태", "음식을 먹는 욕구가 떨어진 상태",
            "아찔함, 현기증, 평형 이상, 불안정", "침이나 음식을 삼킬때 목에 통증", "비점막으로부터 흘러나오는 것", "위에서 가스가 구강으로 역류", "호흡 운동에 장애가 발생하는 상태", "복부에 발생한 통증", "변이 무르고 물기가 많은 상태",
            "속쓰림, 식후 포만감, 조기 만복감", "소변을 볼 때 아픔", "불규칙한 생리", "자신도 모르게 소변이 유출", "정상적으로 배변이 이루어지지 않음", "혈액이 섞인 분변", "구토가 급박한 느낌","입에서 오물이나 먹은 음식이 나옴",
            "근육의 통증", "병이나 충격 따위로 정신을 잃음", "혈압이 100/60 mmHg 이하", "과로로 정신이나 몸이 지쳐 힘듦", "정신없이 잠이 듦", "피부나 점막에 돋아난 작은 종기", "피부가 가려운 증상"};
    /* public interface OnSymptomListener{
        public void onSymptomSelected(SparseBooleanArray s, ArrayList<ListItem> l);
    }*/

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        reportViewModel =
                ViewModelProviders.of(this).get(ReportViewModel.class);
        root = inflater.inflate(R.layout.report_fragment, container, false);
    //    Intent intent = new Intent(getActivity(), ReportNoticeActivity.class);
      //..  startActivity(intent);

        Lst_symptom = root.findViewById(R.id.Lst_symptom);

        array_symptom = new ArrayList<String>();
        for(int i=0; i < symptom.length; i++)
            addItem(list, symptom[i],symptom_content[i]);

      //  addItem(list,"복통");
        Btn_submit = root.findViewById(R.id.Btn_submit);
        adapter = new ReportRecyclerAdapter(list, this,Lst_symptom);
        Lst_symptom.setAdapter(adapter);
        for(int i=0; i < list.size(); i++)
            mSelectedItems.add(i,0);
           // mSelectedItems.put(i,false);
        Btn_submit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if(count ==0 ){
                    Toast.makeText(getContext(),"한 개이상 선택해주세요.",Toast.LENGTH_SHORT).show();
                }
                else{
                    Intent intent = new Intent(getActivity(), ReportResultActivity.class);
                    intent.putExtra("selected", mSelectedItems);

                    startActivity(intent);
                  //  FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                  //  fragmentManager.beginTransaction().detach(ReportFragment.this).commit();
                   // fragmentManager.popBackStack();




                }
                // 제출하기 버튼을 클릭한 경우
                // 증상들의 클릭되었는지 여부를 나타내는 ArrayList를 새로운 Acitivity에 전달한다.


            }

        });


        return root;
    }
    public void addItem(ArrayList<ListItem> list, String s, String sc) {
        ListItem item = new ListItem();
        item.setSymptom(s);
        item.setSymptomContent(sc);
        list.add(item);
    }

    @Override
    public void onItemClick(View v, int position, RecyclerView rList) {
        ListItem item;
        TextView Txt_sym=  v.findViewById(R.id.Txt_symptom);
        if (mSelectedItems.get(position)==1){
            // 어떤 아이템이든 선택된 상태에서 다시 한번 선택(클릭)시 수행

            mSelectedItems.set(position,0);// 그 아이템을 선택 해제한다.
            count--;


        }
        else { // 선택되지 않은 아이템을 클릭 시
            mSelectedItems.set(position,1);//일단 그 선택한 아이템은 선택 상태로 바꿔주기 -> 1
            count++;

            }




                //@@@@@@@@@@@@@@@@@@

                //@@@@@@@@@@@@@@@@@@@@@@@@@@
    }
  /*  @Override
    public void onAttach(Context context) {

        super.onAttach(context);

        if(context instanceof ReportResultActivity) {

            // 사용될 activity 에 context 정보 가져오는 부분

            this.activity = (ReportResultActivity)context;

        }

    }*/


}
