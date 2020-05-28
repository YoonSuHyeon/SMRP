package com.example.smrp.alarm;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.FragmentActivity;

import com.bumptech.glide.Glide;
import com.example.smrp.R;
import com.example.smrp.medicine.ListViewItem;

import java.util.ArrayList;


public class ListViewAdapter2 extends BaseAdapter {
    private ArrayList<ListViewAlarmItem> listViewItemArrayList ;
    private FragmentActivity activity;

    public ListViewAdapter2(ArrayList<ListViewAlarmItem> listViewItemArrayList, FragmentActivity activity){
        this.listViewItemArrayList=listViewItemArrayList;
        this.activity=activity;
    }
    @Override
    public int getCount() {
        return listViewItemArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return listViewItemArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        final int pos = position;
        final Context context = parent.getContext();

        // "listview_item" Layout을 inflate하여 convertView 참조 획득.
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.listview_item, parent, false);
        }

        // 화면에 표시될 View(Layout이 inflate된)으로부터 위젯에 대한 참조
        LinearLayout linearLayout = (LinearLayout) convertView.findViewById(R.id.line_medicine);
        ImageView iconImageView = (ImageView) convertView.findViewById(R.id.imageView1) ;
        TextView titleTextView = (TextView) convertView.findViewById(R.id.textView1) ;
        TextView descTextView = (TextView) convertView.findViewById(R.id.textView2) ;

        // Data Set(listViewItemList)에서 position에 위치한 데이터 참조 획득
        final ListViewAlarmItem listViewAlarmItemI = listViewItemArrayList.get(position);

        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                Intent intent = new Intent(activity.getBaseContext().getApplicationContext(), AlarmInformActivity.class);
                //Intent intent = new Intent(getContext().getApplicationContext(), MedicineDetailActivity.class);
                intent.putExtra("itemSeq",listViewAlarmItemI.getAlramGroupId());
                activity. startActivity(intent);
                //Toast.makeText(getActivity(), "Shoot", Toast.LENGTH_LONG).show(); // 임시 메세지

            }
        });


        // 아이템 내 각 위젯에 데이터 반영
        //iconImageView.setImageDrawable(listViewItem.getUrl());//500,100
        titleTextView.setText(listViewAlarmItemI.getAlramName());
        descTextView.setText(listViewAlarmItemI.getStartAlram().toString());

        return convertView;


    }
}
