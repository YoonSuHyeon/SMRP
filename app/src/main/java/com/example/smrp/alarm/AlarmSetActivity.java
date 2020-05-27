package com.example.smrp.alarm;

import android.app.AlertDialog;
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
import com.example.smrp.reponse_medicine3;
import com.example.smrp.response;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AlarmSetActivity extends AppCompatActivity {
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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alarm_med_fragment);

        alarmViewModel =
                ViewModelProviders.of(this).get(AlarmViewModel.class);


        iv_back = findViewById(R.id.iv_back);
        spin_type = findViewById(R.id.spin_type);
        Btn_add = findViewById(R.id.Btn_add);
        Lst_medicine=findViewById(R.id.Lst_medicine2);

        btn_Set_Alarm= findViewById(R.id.btn_set_alarm);
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
            public void onClick(View v) {
                ArrayList<String> temp = new ArrayList<String>(); //일련번호 리스트를 만드는과정
                for(ListViewItem i :alarmMedicineList){
                    temp.add(i.getItemSeq());
                }

                RetrofitService networkService=RetrofitHelper.getRetrofit().create(RetrofitService.class);
                AlarmMedicine alarmMedicine = new AlarmMedicine("cc",et_alramName.getText().toString(),Integer.parseInt(et_dosingPeriod.getText().toString()),Integer.parseInt(et_oneTimeDose.getText().toString())
                        ,Integer.parseInt(et_oneTimeCapacity.getText().toString()),spin_type.getSelectedItem().toString(),temp);


                Call<response> call = networkService.addAlram(alarmMedicine);
                call.enqueue(new Callback<response>() {
                    @Override
                    public void onResponse(Call<response> call, Response<response> response) {
                        try{
                            String respon = response.body().getResponse();
                            Log.d("12345",respon);
                        }catch (NullPointerException e){
                            Log.d("d",e.toString());
                        }


                        Toast.makeText(getApplicationContext(),"성공",Toast.LENGTH_SHORT).show();

                        //Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                        //startActivity(intent);
                    }

                    @Override
                    public void onFailure(Call<response> call, Throwable t) {
                        Log.d("dddd",t.toString());
                        //Toast.makeText(getApplicationContext(),"회원가입 실패",Toast.LENGTH_SHORT).show();
                    }
                });
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
