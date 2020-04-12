package com.example.smrp.home;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.smrp.R;

import java.util.ArrayList;

public class HomeAlarmFragment extends Fragment {

    TextView Txt_Lst_empty;
    RecyclerView Lst_alarm = null ;
    RecyclerAdapter mAdapter = null ;
    ArrayList<ListItem> mList = new ArrayList<ListItem>();
    Button Btn_add;


    public HomeAlarmFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.home_alarm_fragment, container, false);

        Lst_alarm = root.findViewById(R.id.Lst_alarm) ;

        mAdapter = new RecyclerAdapter(mList) ;
        Lst_alarm .setAdapter(mAdapter) ;
        Lst_alarm .setLayoutManager(new LinearLayoutManager(root.getContext())) ;
        Btn_add = root.findViewById(R.id.Btn_add);
        Txt_Lst_empty = root.findViewById(R.id.Txt_Lst_empty);

        Btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Txt_Lst_empty.setVisibility(View.GONE);
                Btn_add.setVisibility(View.GONE);

                addItem(getActivity().getDrawable(R.drawable.pills),
                        "알림1", "알림내용 ", "일자 : 2020-04-13   만료 : 2020-04-20 ") ;
                addItem(getActivity().getDrawable(R.drawable.pills),
                        "알림2", "알림내용 ", "일자 : 2020-04-13   만료 : 2020-04-20 ") ;
                addItem(getActivity().getDrawable(R.drawable.pills),
                        "알림3", "알림내용 ", "일자 : 2020-04-13   만료 : 2020-04-20 ") ;

                mAdapter.notifyDataSetChanged();
            }
        });

        return  root;

    }

    public void addItem(Drawable icon, String n, String c, String d) {
        ListItem item = new ListItem();

        item.setIcon(icon);
        item.setName(n);
        item.setDate(d);
        item.setContents(c);

        mList.add(item);
    }
}