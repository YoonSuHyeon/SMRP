package com.example.smrp.alarm;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.smrp.R;
import com.example.smrp.RetrofitHelper;
import com.example.smrp.RetrofitService;
import com.example.smrp.medicine.ListViewItem;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;

public class BottomSheetDialog3 extends BottomSheetDialogFragment {
    private   String userId;
    private   String itemSeq;
    private   long groupId;
    private ArrayList<ListViewItem> listViewItemArrayList;
    Activity activity;
    public  static BottomSheetDialog3 getInstance() {
        return new BottomSheetDialog3();
    }
    public void init(String userId, String itemSeq, ArrayList<ListViewItem> list, Activity activity){
        this.userId=userId;
        this.itemSeq=itemSeq;
        listViewItemArrayList = list;
        this.activity = activity;
    }
    public void init(Long groupId){
        this.groupId=groupId;
    }

    private LinearLayout Lay_delete;
    private LinearLayout Lay_cancel;
    private LinearLayout Lay_edit;
    @Nullable
    @Override

    public View onCreateView(final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bottom_sheet_ver_alarm2, container, false);
        Lay_delete = (LinearLayout) view.findViewById(R.id.Lay_delete);
        Lay_cancel = (LinearLayout) view.findViewById(R.id.Lay_cancel);

        Lay_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // AlarmSetActivity 의 List 에서 삭제
               // RetrofitService networkService= RetrofitHelper.getRetrofit().create(RetrofitService.class);
               // Call<String> call = networkService.deleteAlram(groupId);
                for(int i = 0; i < listViewItemArrayList.size(); i++){
                    // 여기서 listViewItemArrayList는 알람 설정 창에서의 약 리스트이다.
                    // 이 listViewItemArrayList에서 클릭한 약의 이름(itemSeq)과 같은 것을 고른다.
                    // (이때 itemSeq는 삭제할 약을 클릭할 때 전달해준다.)
                    if(listViewItemArrayList.get(i).getItemSeq().equals(itemSeq)){
                        listViewItemArrayList.remove(i); // 선택한 약 삭제
                        Intent intent = new Intent();
                        intent.putExtra("result", "some"); // 반환될 때 이 정보들도 가지고 간다.
                        // 여기서 listViewItemArrayList는 알람 설정 창에서의 약 리스트이다.
                        // 이 경우, 알람 설정 창에서의 약 리스트에서 클릭한 약을 삭제한 정보를 갱신한 리스트이다.
                        intent.putExtra("listViewItemArrayList",listViewItemArrayList);// 반환될 때 이 정보들도 가지고 간다.
                        getActivity().setResult(RESULT_OK,intent); // 반환값 설정
                        getActivity().finish(); //activity 종료
                        //    Log.e("삭제삭제삭제", itemSeq+" : : " +listViewItemArrayList.get(i).getItemSeq());


                        // Intent intent = new Intent(getContext().getApplicationContext(), AlarmSetActivity.class);
                        //Log.e("TTag","Afdafdadf");
                        //Intent intent = new Intent(getContext().getApplicationContext(), MedicineDetailActivity.class);
                        // intent.putExtra("listViewItemArrayList",listViewItemArrayList);
                        //  intent.putExtra("back","fsf");
                        // intent.putExtra("adapter",list_view);

                        //  startActivity(intent);
                        Log.e("삭제삭제삭제사이즈", listViewItemArrayList.size()+"");
                        break;
                    }


                }

              /*  call.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        Log.d("sudhjchdj","크크크");
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        Log.d("tat",t.toString());
                    }
                });

                //getActivity().onBackPressed();*/
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