package com.example.smrp.alarm;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.widget.NestedScrollView;
import androidx.lifecycle.ViewModelProviders;

import com.example.smrp.LoginActivity;
import com.example.smrp.R;
import com.example.smrp.RetrofitHelper;
import com.example.smrp.RetrofitService;
import com.example.smrp.medicine.ListViewItem;
import com.example.smrp.reponse_medicine3;
import com.example.smrp.response;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AlarmSetActivity extends AppCompatActivity {

    public static final String NOTIFICATION_CHANNEL_ID = "10001";
    private NestedScrollView nsv_View;
    private AlarmViewModel alarmViewModel;
    private Spinner spin_type;
    ArrayList<String> typeList; // 식전, 식후 담는 리스트
    ArrayAdapter<String> arrayAdapter; // 배열 어댑터
    Button Btn_add,btn_Set_Alarm;
    ArrayList<com.example.smrp.medicine.ListViewItem> alarmMedicineList=new ArrayList<>(); // 약추가한 리스트
    AlarmListViewAdapter alarmListViewAdapter; //알람에 약을 추가한 어댑터
    ListView Lst_medicine;
    EditText et_oneTimeCapacity,et_alramName,et_dosingPeriod,et_oneTimeDose;
    ImageView iv_back;
    int count=0;

    Context context;
    AlarmManager alarmManager;
    private InputMethodManager imm;
    ArrayList<ListViewItem>list = new ArrayList<>();
    String back="a";
    String user_id;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1){
            if(resultCode == RESULT_OK) {

                alarmMedicineList.clear(); // 전에 있던 약 리스트 정보를 삭제
                //반환값과 함께 전달받은 리스트뷰로 갱신하기
                alarmMedicineList.addAll((ArrayList<ListViewItem>) data.getSerializableExtra("listViewItemArrayList"));
                alarmListViewAdapter.notifyDataSetChanged();

            }
        }
    }

    //alarmMedicineList.remove
    @Override
    protected void onStart() {
        super.onStart();
        //alarmListViewAdapter.notifyDataSetChanged();
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alarm_med_fragment);

        alarmViewModel =
                ViewModelProviders.of(this).get(AlarmViewModel.class);

        this.context=this;
        alarmManager=(AlarmManager)getSystemService(ALARM_SERVICE);

        final Intent my_intent = new Intent(this.context,Alarm_Reciver.class);
        final Calendar calendar = Calendar.getInstance();



        Intent intent = getIntent();
        SharedPreferences loginInfromation = getSharedPreferences("setting",0);
        user_id = loginInfromation.getString("id","");
        list = (ArrayList<ListViewItem>) intent.getSerializableExtra("list");
    //    alarmMedicineList = (ArrayList<ListViewItem>) intent.getSerializableExtra("listViewItemArrayList");
        back = intent.getStringExtra("back");
//        Log.e("afaf",back);
        iv_back = findViewById(R.id.iv_back);
        spin_type = findViewById(R.id.spin_type);
        Btn_add = findViewById(R.id.Btn_add);

        btn_Set_Alarm= findViewById(R.id.btn_set_alarm);
        et_oneTimeCapacity= findViewById(R.id.et_oneTimeCapacity);

        et_alramName=findViewById(R.id.et_alramName);
        et_dosingPeriod=findViewById(R.id.et_dosingPeriod);
        et_oneTimeDose=findViewById(R.id.et_oneTimeDose);


        Lst_medicine = findViewById(R.id.Lst_medicine2);
        imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        typeList = new ArrayList<>();
        typeList.add("식전");
        typeList.add("식후");
        arrayAdapter = new ArrayAdapter<>(getApplicationContext(),
                android.R.layout.simple_spinner_dropdown_item,
                typeList);
        spin_type.setAdapter(arrayAdapter);
        spin_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getApplicationContext(),typeList.get(i)+"",
                        Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        alarmListViewAdapter=new AlarmListViewAdapter(alarmMedicineList,this); //alarmMedicineList =ArrayList
        Lst_medicine.setAdapter(alarmListViewAdapter);  //Lst_medicine: listView



        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        Btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // MedDialog p = new MedDialog(); //
                // p.show(getActivity().getSupportFragmentManager(),"popup");
                showAlertDialog();
            }
        });

        btn_Set_Alarm.setOnClickListener(new View.OnClickListener() {//알람설정을 누른경우
            @Override
            public void onClick(View v) { // 알람설정

                if (et_oneTimeCapacity.getText().toString().equals("") || et_alramName.getText().toString().equals("") || et_dosingPeriod.getText().toString().equals("")
                        || et_oneTimeDose.getText().toString().equals("")) {
                    Toast.makeText(context, "모두 입력해 주세요 .", Toast.LENGTH_SHORT).show();
                } else {
                    imm.hideSoftInputFromWindow(et_alramName.getWindowToken(), 0);
                    ArrayList<String> temp = new ArrayList<String>(); //일련번호 리스트를 만드는과정
                    for (ListViewItem i : alarmMedicineList) {
                        temp.add(i.getItemSeq());
                    }
                    if (temp.size() == 0) {
                        Toast.makeText(context, "약을 등록해 주세요.", Toast.LENGTH_SHORT).show();
                    } else {
                        RetrofitService networkService = RetrofitHelper.getRetrofit().create(RetrofitService.class);


                        AlarmMedicine alarmMedicine = new AlarmMedicine(user_id, et_alramName.getText().toString(), Integer.parseInt(et_dosingPeriod.getText().toString()), Integer.parseInt(et_oneTimeDose.getText().toString())
                                , Integer.parseInt(et_oneTimeCapacity.getText().toString()), spin_type.getSelectedItem().toString(), temp);


                        Call<response> call = networkService.addAlram(alarmMedicine);
                        call.enqueue(new Callback<response>() {
                            @Override
                            public void onResponse(Call<response> call, Response<response> response) {
                                try {
                                    String respon = response.body().getResponse();

                                } catch (NullPointerException e) {

                                }
                                // PendingIntent sender = PendingIntent.getBroadcast(AlarmSetActivity.this,0,my_intent,0);

                                if (Integer.parseInt(et_oneTimeCapacity.getText().toString()) == 1) {
                                    if (spin_type.getSelectedItem().toString() == "식전") {
                                        calendar.set(Calendar.HOUR_OF_DAY, 11);      //식전
                                        PendingIntent sender = PendingIntent.getBroadcast(AlarmSetActivity.this, count++, my_intent, 0);
                                        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), 24 * 60 * 60 * 1000, sender);
                                    } else {
                                        calendar.set(Calendar.HOUR_OF_DAY, 13);      //식후
                                        PendingIntent sender = PendingIntent.getBroadcast(AlarmSetActivity.this, count++, my_intent, 0);
                                        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), 24 * 60 * 60 * 1000, sender);
                                    }
                                } else if (Integer.parseInt(et_oneTimeCapacity.getText().toString()) == 2) {
                                    if (spin_type.getSelectedItem().toString() == "식전") {
                                        calendar.set(Calendar.HOUR_OF_DAY, 7);      //식전
                                        PendingIntent sender = PendingIntent.getBroadcast(AlarmSetActivity.this, count++, my_intent, 0);
                                        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), 24 * 60 * 60 * 1000, sender);
                                        calendar.set(Calendar.HOUR_OF_DAY, 17);      //식전
                                        PendingIntent sender2 = PendingIntent.getBroadcast(AlarmSetActivity.this, count++, my_intent, 0);
                                        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), 24 * 60 * 60 * 1000, sender2);
                                    } else {
                                        calendar.set(Calendar.HOUR_OF_DAY, 9);      //식후
                                        PendingIntent sender = PendingIntent.getBroadcast(AlarmSetActivity.this, count++, my_intent, 0);
                                        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), 24 * 60 * 60 * 1000, sender);
                                        calendar.set(Calendar.HOUR_OF_DAY, 18);      //식후
                                        PendingIntent sender2 = PendingIntent.getBroadcast(AlarmSetActivity.this, count++, my_intent, 0);
                                        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), 24 * 60 * 60 * 1000, sender2);
                                    }
                                } else {
                                    if (spin_type.getSelectedItem().toString() == "식전") {
                                        calendar.set(Calendar.HOUR_OF_DAY, 12);      //식전
                                        calendar.set(Calendar.MINUTE, 35);
                                        PendingIntent sender = PendingIntent.getBroadcast(AlarmSetActivity.this, count++, my_intent, 0);
                                        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), 24 * 60 * 60 * 1000, sender);
                                        calendar.set(Calendar.HOUR_OF_DAY, 12);      //식전
                                        calendar.set(Calendar.MINUTE, 37);
                                        PendingIntent sender2 = PendingIntent.getBroadcast(AlarmSetActivity.this, count++, my_intent, 0);
                                        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), 24 * 60 * 60 * 1000, sender2);
                                        calendar.set(Calendar.HOUR_OF_DAY, 12);      //식전
                                        calendar.set(Calendar.MINUTE, 39);
                                        PendingIntent sender3 = PendingIntent.getBroadcast(AlarmSetActivity.this, count++, my_intent, 0);
                                        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), 24 * 60 * 60 * 1000, sender3);
                                        calendar.set(Calendar.HOUR_OF_DAY, 12);      //식전
                                        calendar.set(Calendar.MINUTE, 41);
                                        PendingIntent sender4 = PendingIntent.getBroadcast(AlarmSetActivity.this, count++, my_intent, 0);
                                        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), 24 * 60 * 60 * 1000, sender4);
                                        calendar.set(Calendar.HOUR_OF_DAY, 19);      //식전
                                        PendingIntent sender5 = PendingIntent.getBroadcast(AlarmSetActivity.this, count++, my_intent, 0);
                                        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), 24 * 60 * 60 * 1000, sender5);
                                    } else {
                                        calendar.set(Calendar.HOUR_OF_DAY, 9);      //식후
                                        PendingIntent sender = PendingIntent.getBroadcast(AlarmSetActivity.this, count++, my_intent, 0);
                                        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), 24 * 60 * 60 * 1000, sender);
                                        calendar.set(Calendar.HOUR_OF_DAY, 13);      //식후
                                        PendingIntent sender2 = PendingIntent.getBroadcast(AlarmSetActivity.this, count++, my_intent, 0);
                                        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), 24 * 60 * 60 * 1000, sender2);
                                        calendar.set(Calendar.HOUR_OF_DAY, 18);      //식후
                                        PendingIntent sender3 = PendingIntent.getBroadcast(AlarmSetActivity.this, count++, my_intent, 0);
                                        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), 24 * 60 * 60 * 1000, sender3);
                                    }
                                }
                                //calendar.set(Calendar.HOUR_OF_DAY,19);
                                //calendar.set(Calendar.MINUTE,40);
                                //PendingIntent sender = PendingIntent.getBroadcast(AlarmSetActivity.this,0,my_intent,0);

                                //alarmManager.set(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),sender);

                                Toast.makeText(getApplicationContext(), "성공", Toast.LENGTH_SHORT).show();
                                onBackPressed();

                                //notificationSomethings();
                                //Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                                //startActivity(intent);
                            }

                            @Override
                            public void onFailure(Call<response> call, Throwable t) {

                                //Toast.makeText(getApplicationContext(),"회원가입 실패",Toast.LENGTH_SHORT).show();
                            }
                        });
                    }


                }


            }
        });

        if(back != null){

            alarmMedicineList = (ArrayList<ListViewItem>) intent.getSerializableExtra("listViewItemArrayList");
            alarmListViewAdapter.notifyDataSetChanged();
        }
        else{
            if(list!=null&&list.size()>0) {
                alarmMedicineList.addAll(list);
                alarmListViewAdapter.notifyDataSetChanged();
            }

        }



            }

       // return root;




    public void notificationSomethings(){
        NotificationManager notificationManager =(NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);

        Intent notificationIntent = new Intent(this, LoginActivity.class);
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,0,notificationIntent,PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this,NOTIFICATION_CHANNEL_ID)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(),R.drawable.ic_launcher_foreground))
                .setContentTitle("얄 알람 서비스")
                .setContentText("약을 드실 시간입니다")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            builder.setSmallIcon(R.drawable.ic_launcher_foreground);
            CharSequence channelName ="테스트 채널";
            String description = "오레오 이상의 버전을 위한 것";
            int importance = NotificationManager.IMPORTANCE_HIGH;

            NotificationChannel channel = new NotificationChannel(NOTIFICATION_CHANNEL_ID,channelName,importance);
            channel.setDescription(description);

            assert notificationManager != null;
            notificationManager.createNotificationChannel(channel);
        }else builder.setSmallIcon(R.mipmap.ic_launcher); //오레오 이하 에서는 밉맵사용해야만 함

        assert  notificationManager != null;
        notificationManager.notify(1234,builder.build());
    }

    private void showAlertDialog() //약 추가하기 팝업창
    {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.alarm_med_dialog, null);
        builder.setView(view);
        final Button Btn_ok = view.findViewById(R.id.Btn_ok);
        final ArrayList<ListViewItem> items = new ArrayList<>();

        ListView Lst_medicine = view.findViewById(R.id.Lst_medicine); //약 추가하기 팝업 창 내가 등록한 약
        final AlertDialog dialog = builder.create();

        final ListViewAdapter adapter = new ListViewAdapter(items, this);
        Lst_medicine.setAdapter(adapter); //




        Btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { // 확인 버튼 누르기(약추가하기 기능에서)


                int num= 0;
                Toast.makeText(getApplicationContext(), "추가 되었습니다.", Toast.LENGTH_SHORT).show();

                ArrayList<ListViewItem>list = adapter.res();
                if(alarmMedicineList.size()==1){//등록된 약 기능에서 알람추가시 중복제거

                    for(int i =0;i<list.size();i++){
                        if(alarmMedicineList.get(0).getItemSeq().equals(list.get(i).getItemSeq())) {
                            list.remove(i);
                            num++;
                        }
                    }
                }
                else{
                    for(int i =0; i < alarmMedicineList.size() ; i++){
                        for(int j = 0; j<list.size();j++){
                            if(alarmMedicineList.get(i).getItemSeq().equals(list.get(j).getItemSeq())){
                                list.remove(j);
                                num++;
                            }
                        }
                    }
                }

                if(num>0)
                    Toast.makeText(getApplicationContext(), "중복된 약 "+num+"건을 제외하였습니다.",Toast.LENGTH_SHORT).show();
                alarmMedicineList.addAll(list);


                alarmListViewAdapter.notifyDataSetChanged();
                dialog.dismiss();
            }
        });

        RetrofitService networkService= RetrofitHelper.getRetrofit().create(RetrofitService.class);

        //String id  사용자 id를 가져와야함

        Call<List<reponse_medicine3>> call = networkService.findUserMedicine(user_id);
        call.enqueue(new Callback<List<reponse_medicine3>>() {
            @Override
            public void onResponse(Call<List<reponse_medicine3>> call, Response<List<reponse_medicine3>> response) {
                List<reponse_medicine3> reponse_medicines =response.body();
                items.clear();

                for(int i = 0; i<  reponse_medicines.size(); i++)
                {
                    //items:ArrayList
                    items.add(new ListViewItem(reponse_medicines.get(i).getImageUrl(),reponse_medicines.get(i).getItemName(),reponse_medicines.get(i).getItemSeq(),reponse_medicines.get(i).getCreatedAt()));

                }
                adapter.notifyDataSetChanged();
                //Toast.makeText(getApplicationContext(),"사용 가능한 아이디입니다.",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<List<reponse_medicine3>> call, Throwable t) {


            }
        });




       /* Lst_medicine.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                dialog.dismiss();
            }
        });*/

        //ialog.setCancelable(true);
        dialog.show();
    }
}
