package com.example.smrp.alarm;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.fragment.app.DialogFragment;

import com.example.smrp.R;
import com.example.smrp.RetrofitHelper;
import com.example.smrp.RetrofitService;
import com.example.smrp.medicine.ListViewItem;
import com.example.smrp.reponse_medicine3;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MedDialog extends DialogFragment { // 약 등록하기 Fragment에서 +버튼 눌렀을 때 띄어주는 Dialog 창



    private Button Btn_ok;
    private ListView Lst_medicine;
    private Intent intent;
    ListViewAdapter adapter;
    ArrayList<ListViewItem> items = new ArrayList<>();
    ArrayList<String> s = new ArrayList<>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.alarm_med_dialog, container, false);


        //
        Btn_ok = view.findViewById(R.id.Btn_ok);
        Lst_medicine = view.findViewById(R.id.Lst_medicine);

        //
        //AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        //builder.setView(view);

        // final AlertDialog dialog = builder.create();

        //adapter = new ListViewAdapter(items, getActivity(),);
        Lst_medicine.setAdapter(adapter);

        //items.add(new ListViewItem(getActivity().getDrawable(R.drawable.slide3), "dfgdf","dgdfgfd"));
        //items.add(new ListViewItem(getActivity().getDrawable(R.drawable.slide1), "dfgdf","dgdfgfd"));


        //서버에게 사용자 ID를 보낸후  등록된 약들을 받아서 Adapter에 등록한다.

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

        Lst_medicine.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // view.setBackgroundColor(Color.parseColor("#000000"));
                Toast.makeText(getContext(), "a",Toast.LENGTH_SHORT).show();


            }
        });



        Lst_medicine.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                // dialog.dismiss();
            }
        });

        // dialog.setCancelable(false);
        //  dialog.show();


        /* 클릭 이벤트 */
        Btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        return view;
    }

}