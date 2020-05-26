package com.example.smrp.alarm;

import androidx.lifecycle.ViewModelProviders;

import android.app.AlertDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.smrp.R;
import com.example.smrp.RetrofitHelper;
import com.example.smrp.RetrofitService;
import com.example.smrp.reponse_medicine;


import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AlarmFragment extends Fragment {

    private AlarmViewModel alarmViewModel;
    private Spinner spin_type;
    ArrayList<String> typeList; // 식전, 식후 담는 리스트
    ArrayAdapter<String> arrayAdapter; // 배열 어댑터
    Button Btn_add;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        alarmViewModel =
                ViewModelProviders.of(this).get(AlarmViewModel.class);
        View root = inflater.inflate(R.layout.alarm_med_fragment, container, false);

        spin_type = root.findViewById(R.id.spin_type);
        Btn_add = root.findViewById(R.id.Btn_add);
        typeList = new ArrayList<>();
        typeList.add("식전");
        typeList.add("식후");
        arrayAdapter = new ArrayAdapter<>(getContext().getApplicationContext(),
                android.R.layout.simple_spinner_dropdown_item,
                typeList);
        spin_type.setAdapter(arrayAdapter);
        spin_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getContext().getApplicationContext(),typeList.get(i)+"",
                        Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
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



        return root;
    }
    private void showAlertDialog()
    {

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.alarm_med_dialog, null);
        builder.setView(view);
        final Button Btn_ok = view.findViewById(R.id.Btn_ok);
        final ArrayList<ListViewItem> items = new ArrayList<>();

        ListView Lst_medicine = view.findViewById(R.id.Lst_medicine);
        final AlertDialog dialog = builder.create();

       final ListViewAdapter adapter = new ListViewAdapter(items, getActivity());
        Lst_medicine.setAdapter(adapter);


        //서버에게 사용자 ID를 보낸후  등록된 약들을 받아서 Adapter에 등록한다.

        RetrofitService networkService= RetrofitHelper.getRetrofit().create(RetrofitService.class);

        Btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), adapter.res()+"", Toast.LENGTH_SHORT).show();
            }
        });

        //String id  사용자 id를 가져와야함
        String id ="cc";
        Call<List<reponse_medicine>> call = networkService.findUserMedicine(id);
        call.enqueue(new Callback<List<reponse_medicine>>() {
            @Override
            public void onResponse(Call<List<reponse_medicine>> call, Response<List<reponse_medicine>> response) {
                List<reponse_medicine> reponse_medicines =response.body();
                items.clear();

                for(int i = 0; i<  reponse_medicines.size(); i++)
                {
                    items.add(new ListViewItem(reponse_medicines.get(i).getItemImage(),reponse_medicines.get(i).getItemName(),reponse_medicines.get(i).getItemSeq()));

                }
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onFailure(Call<List<reponse_medicine>> call, Throwable t) {
                Log.d("ddd",t.toString());

            }
        });




        Lst_medicine.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

               // dialog.dismiss();
            }
        });

        //ialog.setCancelable(true);
        dialog.show();
    }
}

