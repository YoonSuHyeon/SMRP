package com.example.smrp.report;

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
    String symptom[] ={"가슴 통증", "심계항징", "요통", "공포", "구취", "기침", "두통", "만성 기침", "발열", "불안", "삼킴곤란", "시력 감소",
            "식욕 부진", "어지럼증", "인두통 및 인후통", "콧물", "트림", "호흡장애", "복통", "설사", "소화불량", "배뇨통", "생리불순", "요실금", "변비", "혈변", "구역", "구토", "근육통", "실신", "저혈압", "피로", "혼수", "발진", "소양감"};
   /* public interface OnSymptomListener{
        public void onSymptomSelected(SparseBooleanArray s, ArrayList<ListItem> l);
    }*/

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        reportViewModel =
                ViewModelProviders.of(this).get(ReportViewModel.class);
        View root = inflater.inflate(R.layout.report_fragment, container, false);
    //    Intent intent = new Intent(getActivity(), ReportNoticeActivity.class);
      //..  startActivity(intent);

        Lst_symptom = root.findViewById(R.id.Lst_symptom);

        array_symptom = new ArrayList<String>();
        for(int i=0; i < symptom.length; i++)
            addItem(list, symptom[i]);

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


                }
                // 제출하기 버튼을 클릭한 경우
                // 증상들의 클릭되었는지 여부를 나타내는 ArrayList를 새로운 Acitivity에 전달한다.


            }

        });


        return root;
    }
    public void addItem(ArrayList<ListItem> list, String s) {
        ListItem item = new ListItem();
        item.setSymptom(s);
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
