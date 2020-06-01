package com.example.smrp.alarm;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.smrp.R;
import com.example.smrp.RetrofitHelper;
import com.example.smrp.RetrofitService;
import com.example.smrp.medicine.ListViewItem;
import com.example.smrp.reponse_medicine2;
import com.example.smrp.reponse_medicine3;
import com.example.smrp.response;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/*
 알람 수정 기능-
 AlarmSetActivity랑 거의 유사합니다. 여기에 Btn_edit 눌렀을ㅇ 경우, 수정 완료되는 작업이 필요합니다.
 그리고 여기에 각 EditText에 알람 정보들 서버에서 불러와야합니다.(구현 안해놨음)



 */

public class AlarmEditActivity extends AppCompatActivity {
    private NestedScrollView nsv_View;
    private AlarmViewModel alarmViewModel;
    private Spinner spin_type;
    ArrayList<String> typeList; // 식전, 식후 담는 리스트
    ArrayAdapter<String> arrayAdapter; // 배열 어댑터
    Button Btn_add,Btn_edit;
    ArrayList<com.example.smrp.medicine.ListViewItem> alarmMedicineList=new ArrayList<>(); // 약추가한 리스트
    AlarmListViewAdapter alarmListViewAdapter; //알람에 약을 추가한 어댑터
    ListView Lst_medicine;
    EditText et_oneTimeCapacity,et_alramName,et_dosingPeriod,et_oneTimeDose;
    ImageView iv_back;
    Long groupId;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_edit);

        alarmViewModel =
                ViewModelProviders.of(this).get(AlarmViewModel.class);
        Intent intent = getIntent();
        groupId = intent.getLongExtra("groupId",0);
        iv_back = findViewById(R.id.iv_back);
        spin_type = findViewById(R.id.spin_type);
        Btn_add = findViewById(R.id.Btn_add);
        Lst_medicine=findViewById(R.id.Lst_medicine2);

        Btn_edit= findViewById(R.id.Btn_edit);
        et_oneTimeCapacity= findViewById(R.id.et_oneTimeCapacity);

        et_alramName=findViewById(R.id.et_alramName);
        et_dosingPeriod=findViewById(R.id.et_dosingPeriod);
        et_oneTimeDose=findViewById(R.id.et_oneTimeDose);


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

        alarmListViewAdapter=new AlarmListViewAdapter(alarmMedicineList,this);
        Lst_medicine.setAdapter(alarmListViewAdapter);



        RetrofitService networkService= RetrofitHelper.getRetrofit().create(RetrofitService.class);
        Log.d("groupIdadfsdf",groupId.toString());
        Call<Response_AlarmMedicine> call = networkService.getAlram(groupId);
        call.enqueue(new Callback<Response_AlarmMedicine>() {
            @Override
            public void onResponse(Call<Response_AlarmMedicine> call, Response<Response_AlarmMedicine> response) {
                Response_AlarmMedicine response_alarmMedicine =response.body();

                et_alramName.setText(response_alarmMedicine.getAlramName());
                et_oneTimeDose.setText(response_alarmMedicine.getOneTimeDose().toString());
                et_oneTimeCapacity.setText(response_alarmMedicine.getOneTimeCapacity().toString());
                et_dosingPeriod.setText(response_alarmMedicine.getDosingPeriod().toString());

                if(response_alarmMedicine.getDoseType().equals("식전")) {
                    spin_type.setSelection(0); ;
                }else{
                    spin_type.setSelection(1); ;
                }

                ArrayList<reponse_medicine3> temp =response_alarmMedicine.getMedicine3s();
                for( reponse_medicine3 i :temp){
                    alarmMedicineList.add(new ListViewItem(i.getImageUrl(),i.getItemName(),i.getItemSeq(),i.getCreatedAt()));
                }
               alarmListViewAdapter.notifyDataSetChanged();

            }

            @Override
            public void onFailure(Call<Response_AlarmMedicine> call, Throwable t) {
                Log.d("zxcbzxcb",t.toString());

            }
        });


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

        Btn_edit.setOnClickListener(new View.OnClickListener() {//알람설정을 누른경우
            @Override
            public void onClick(View v) {
              //수정 누르면 수정하기 작업
                ArrayList<String> temp = new ArrayList<String>(); //일련번호 리스트를 만드는과정
                for(ListViewItem i :alarmMedicineList){
                    temp.add(i.getItemSeq());
                }

                RetrofitService networkService=RetrofitHelper.getRetrofit().create(RetrofitService.class);
                AlarmMedicine alarmMedicine = new AlarmMedicine("cc",et_alramName.getText().toString(),Integer.parseInt(et_dosingPeriod.getText().toString()),Integer.parseInt(et_oneTimeDose.getText().toString())
                        ,Integer.parseInt(et_oneTimeCapacity.getText().toString()),spin_type.getSelectedItem().toString(),temp);


                Call<response> call = networkService.updateAlram(groupId,alarmMedicine);
                call.enqueue(new Callback<response>() {
                    @Override
                    public void onResponse(Call<response> call, Response<response> response) {
                        Log.d("zzzzzzz","dddd");
                    }

                    @Override
                    public void onFailure(Call<response> call, Throwable t) {
                        Log.d("bbbbbb",t.toString());
                    }
                });
                Toast.makeText(AlarmEditActivity.this, "수정", Toast.LENGTH_SHORT).show();
                onBackPressed();


            }
        });

        // return root;
    }
    private void showAlertDialog()
    {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.alarm_med_dialog, null);
        builder.setView(view);
        final Button Btn_ok = view.findViewById(R.id.Btn_ok);
        final ArrayList<ListViewItem> items = new ArrayList<>();

        ListView Lst_medicine = view.findViewById(R.id.Lst_medicine);
        final AlertDialog dialog = builder.create();

        final ListViewAdapter adapter = new ListViewAdapter(items, this);
        Lst_medicine.setAdapter(adapter);




        Btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { // 확인 버튼 누르기
                Toast.makeText(getApplicationContext(), "추가 되었습니다.", Toast.LENGTH_SHORT).show();
                alarmMedicineList.addAll(adapter.res());
                Log.d("dddzxcb",alarmMedicineList.size()+"");

                alarmListViewAdapter.notifyDataSetChanged();
                dialog.dismiss();
            }
        });

        RetrofitService networkService= RetrofitHelper.getRetrofit().create(RetrofitService.class);

        //String id  사용자 id를 가져와야함
        String id ="cc";
        Call<List<reponse_medicine3>> call = networkService.findUserMedicine(id);
        call.enqueue(new Callback<List<reponse_medicine3>>() {
            @Override
            public void onResponse(Call<List<reponse_medicine3>> call, Response<List<reponse_medicine3>> response) {
                List<reponse_medicine3> reponse_medicines =response.body();
                items.clear();

                for(int i = 0; i<  reponse_medicines.size(); i++)
                {
                    items.add(new ListViewItem(reponse_medicines.get(i).getImageUrl(),reponse_medicines.get(i).getItemName(),reponse_medicines.get(i).getItemSeq(),reponse_medicines.get(i).getCreatedAt()));
                    /*Log.d("dfsdazxcv",reponse_medicines.get(i).getItemImage().toString());
                    Log.d("dfsdazxcv",reponse_medicines.get(i).getItemName());*/
                }
                adapter.notifyDataSetChanged();
                //Toast.makeText(getApplicationContext(),"사용 가능한 아이디입니다.",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<List<reponse_medicine3>> call, Throwable t) {
                Log.d("ddd",t.toString());

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
