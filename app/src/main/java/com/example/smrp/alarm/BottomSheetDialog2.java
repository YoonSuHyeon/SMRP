package com.example.smrp.alarm;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
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
import com.example.smrp.UserAlarm;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.ALARM_SERVICE;

public class BottomSheetDialog2 extends BottomSheetDialogFragment {
    private   String userId;
    private   String itemSeq;
    private   long groupId;
    //private int capacity;
    public static AlarmManager alarmManager=null;
    public static PendingIntent pendingIntent=null;
    Intent intent;
    Context context;
    public  static BottomSheetDialog2 getInstance() {
        return new BottomSheetDialog2();
    }
    public void init(String userId,String itemSeq){
        this.userId=userId;
        this.itemSeq=itemSeq;
    }

    public void init(Long groupId){
        this.groupId=groupId;
       // this.capacity = capacity;
    }

    private LinearLayout Lay_delete;
    private LinearLayout Lay_cancel;

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bottom_sheet_ver_alarm, container, false);
        context= view.getContext();
        Lay_delete = (LinearLayout) view.findViewById(R.id.Lay_delete);
        Lay_cancel = (LinearLayout) view.findViewById(R.id.Lay_cancel);

        alarmManager = (AlarmManager)getContext().getSystemService(context.ALARM_SERVICE);
        intent = new Intent(context,Alarm_Reciver.class);
        Lay_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "delete", Toast.LENGTH_SHORT).show();


                // AlarmSetActivity 의 List 에서 삭제

                RetrofitService networkService= RetrofitHelper.getRetrofit().create(RetrofitService.class);
                Call<String> call = networkService.deleteAlram(groupId);
               /*pendingIntent = PendingIntent.getBroadcast(context,response.body().getAlramMedicines().get(i).getAlramGroupId(),intent,PendingIntent.FLAG_CANCEL_CURRENT);
                alarmManager.cancel(pendingIntent);
                pendingIntent.cancel();
                pendingIntent=null;*/
                call.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                       // if(response.body().getAlramMedicines().size()!=0){
                            //for(int i=0; i<response.body().getAlramMedicines().size(); i++){
                                //String what = response.body().getAlramMedicines().get(i).getDoseType();
                                Log.d("123123",response.body());
                                switch (response.body()){
                                    case "1":{
                                        pendingIntent = PendingIntent.getBroadcast(context,(int)groupId,intent,PendingIntent.FLAG_CANCEL_CURRENT);
                                        alarmManager.cancel(pendingIntent);
                                        pendingIntent.cancel();
                                        pendingIntent=null;
                                        break;
                                    }
                                    case "2":{
                                        pendingIntent = PendingIntent.getBroadcast(context,(int)groupId,intent,PendingIntent.FLAG_CANCEL_CURRENT);
                                        alarmManager.cancel(pendingIntent);
                                        pendingIntent.cancel();
                                        pendingIntent=null;

                                        pendingIntent = PendingIntent.getBroadcast(context,(int)groupId+100,intent,PendingIntent.FLAG_CANCEL_CURRENT);
                                        alarmManager.cancel(pendingIntent);
                                        pendingIntent.cancel();
                                        pendingIntent=null;
                                        break;
                                    }
                                    case "3":{
                                        pendingIntent = PendingIntent.getBroadcast(context,(int)groupId,intent,PendingIntent.FLAG_CANCEL_CURRENT);
                                        alarmManager.cancel(pendingIntent);
                                        pendingIntent.cancel();
                                        pendingIntent=null;

                                        pendingIntent = PendingIntent.getBroadcast(context,(int)groupId+100,intent,PendingIntent.FLAG_CANCEL_CURRENT);
                                        alarmManager.cancel(pendingIntent);
                                        pendingIntent.cancel();
                                        pendingIntent=null;

                                        pendingIntent = PendingIntent.getBroadcast(context,(int)groupId+200,intent,PendingIntent.FLAG_CANCEL_CURRENT);
                                        alarmManager.cancel(pendingIntent);
                                        pendingIntent.cancel();
                                        pendingIntent=null;
                                        break;
                                    }
                                    default:{
                                        pendingIntent = PendingIntent.getBroadcast(context,(int)groupId,intent,PendingIntent.FLAG_CANCEL_CURRENT);
                                        alarmManager.cancel(pendingIntent);
                                        pendingIntent.cancel();
                                        pendingIntent=null;

                                        pendingIntent = PendingIntent.getBroadcast(context,(int)groupId+100,intent,PendingIntent.FLAG_CANCEL_CURRENT);
                                        alarmManager.cancel(pendingIntent);
                                        pendingIntent.cancel();
                                        pendingIntent=null;

                                        pendingIntent = PendingIntent.getBroadcast(context,(int)groupId+200,intent,PendingIntent.FLAG_CANCEL_CURRENT);
                                        alarmManager.cancel(pendingIntent);
                                        pendingIntent.cancel();
                                        pendingIntent=null;
                                    }
                                }
                          //  }
                           // Intent intent = new Intent(context, LoginActivity.class);
                          //  startActivity(intent);
                           // getActivity().finish();

                       // }
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        Toast.makeText(getContext(), "서버상태가 원할하지 않습니다", Toast.LENGTH_SHORT).show();
                    }
                });

                getActivity().onBackPressed();
            }
        });
        Lay_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "cancel", Toast.LENGTH_SHORT).show();

                getActivity().onBackPressed();
            }
        });
        /*Lay_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "edit", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getActivity(), AlarmEditActivity.class);
                intent.putExtra("groupId",groupId);
                //Intent intent = new Intent(getContext().getApplicationContext(), MedicineDetailActivity.class);
                startActivity(intent);
                //  getActivity().onBackPressed();
            }
        });*/
        // dismiss();
        return view;
    }
}