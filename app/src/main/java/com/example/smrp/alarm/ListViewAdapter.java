package com.example.smrp.alarm;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
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

public class ListViewAdapter extends BaseAdapter {
    private ArrayList<ListViewItem> listViewItemArrayList ;
    private ArrayList<Integer> tempViewItemArrayList=new ArrayList<>();
    private FragmentActivity activity;
  //  private AlertDialog dialog;
    private int recent_pos;
    public ArrayList<ListViewItem> res(){
        ArrayList<ListViewItem> arrayList=new ArrayList<>() ;
        for(int i : tempViewItemArrayList){
           arrayList.add(listViewItemArrayList.get(i));
        }
        return arrayList;
    }

    public ListViewAdapter(ArrayList<ListViewItem> listViewItemArrayList, FragmentActivity activity){
        this.listViewItemArrayList=listViewItemArrayList;
        this.activity=activity;
       // this.dialog=dialog;
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
        final LinearLayout linearLayout = (LinearLayout) convertView.findViewById(R.id.line_medicine);
        ImageView iconImageView = (ImageView) convertView.findViewById(R.id.imageView1) ;
        TextView titleTextView = (TextView) convertView.findViewById(R.id.textView1) ;
        TextView descTextView = (TextView) convertView.findViewById(R.id.med_name) ;

        // Data Set(listViewItemList)에서 position에 위치한 데이터 참조 획득
        final ListViewItem listViewItem = listViewItemArrayList.get(position);

        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!tempViewItemArrayList.contains(pos)){
                    tempViewItemArrayList.add(pos);
                    linearLayout.setBackgroundColor(Color.LTGRAY);
                }else{
                    linearLayout.setBackgroundColor(Color.WHITE);
                    tempViewItemArrayList.remove(Integer.valueOf(pos));
                }
                //linearLayout.setBackgroundColor(Color.parseColor("#000000"));

               // Intent intent = new Intent(activity.getBaseContext().getApplicationContext(), AlarmDetailActivity.class);
                //Intent intent = new Intent(getContext().getApplicationContext(), MedicineDetailActivity.class);
               // intent.putExtra("itemSeq",listViewItem.getItemSeq());
                //activity. startActivity(intent);
                //Toast.makeText(getActivity(), "Shoot", Toast.LENGTH_LONG).show(); // 임시 메세지


            }
        });



       // iconImageView.setImageDrawable(listViewItem.getUrl());
        Glide.with(activity).load(listViewItem.getUrl()).override(400,150).fitCenter().into(iconImageView);
//        iconImageView.setImageDrawable(listViewItem.getIcon());
        titleTextView.setText(listViewItem.getName());
        descTextView.setText(listViewItem.getTime());

        return convertView;


    }
}
