package com.example.smrp.alarm;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.fragment.app.FragmentActivity;

import com.bumptech.glide.Glide;
import com.example.smrp.R;
import com.example.smrp.medicine.ListViewItem;

import java.util.ArrayList;

public class AlarmListViewAdapter extends BaseAdapter {
    private ArrayList<ListViewItem> listViewItemArrayList ;
    private FragmentActivity activity;
    ListView list_view;
    private int REQUEST_TEST=1;
    public AlarmListViewAdapter(ArrayList<ListViewItem> listViewItemArrayList, FragmentActivity activity){
        this.listViewItemArrayList=listViewItemArrayList;
        this.activity=activity;
//        this.adapter = adapter;

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
        TextView entpTextView =  convertView.findViewById(R.id.textView3);//약 제조사
        TextView titleTextView = (TextView) convertView.findViewById(R.id.textView1) ;
        TextView descTextView = (TextView) convertView.findViewById(R.id.med_name_1) ;

        // Data Set(listViewItemList)에서 position에 위치한 데이터 참조 획득
        final ListViewItem listViewItem = listViewItemArrayList.get(position);

        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // 삭제할 약 클릭한 경우 - AlarmDetailActivity2 호출
                Intent intent = new Intent(activity.getBaseContext().getApplicationContext(), AlarmDetailActivity2.class);
                intent.putExtra("itemSeq",listViewItem.getItemSeq());
                intent.putExtra("listViewItemArrayList",listViewItemArrayList);
                // startActivityForResult()를 통해 이 호출하는 액티비티의 반환값을 받겠다는 것을 알림
                activity.startActivityForResult(intent, REQUEST_TEST);

                //Log.e("TTag","Afdafdadf");
                //Intent intent = new Intent(getContext().getApplicationContext(), MedicineDetailActivity.class);
                // intent.putExtra("adapter",list_view);
              //  activity. startActivity(intent);
                //Toast.makeText(getActivity(), "Shoot", Toast.LENGTH_LONG).show(); // 임시 메세지

            }
        });


        // 아이템 내 각 위젯에 데이터 반영
      //  iconImageView.setImageDrawable(listViewItem.getUrl());//500,100
        Glide.with(activity).load(listViewItem.getUrl()).override(500, 150).fitCenter().into(iconImageView);
        titleTextView.setText(listViewItem.getName());
        descTextView.setText(listViewItem.getTime());
        entpTextView.setText(listViewItem.getEntpName());
        return convertView;


    }

}
