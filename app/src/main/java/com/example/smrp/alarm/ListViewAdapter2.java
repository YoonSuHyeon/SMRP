package com.example.smrp.alarm;

import android.content.Context;
import android.content.Intent;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.fragment.app.FragmentActivity;

import com.bumptech.glide.Glide;
import com.example.smrp.R;
import com.example.smrp.medicine.ListViewItem;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class ListViewAdapter2 extends BaseAdapter { //AlarmFragment 에 있는 어댑터  유저의 모든 알람을 보여주는데 사용
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
            convertView = inflater.inflate(R.layout.listviewitem_alarm, parent, false);
        }

        // 화면에 표시될 View(Layout이 inflate된)으로부터 위젯에 대한 참조
        LinearLayout linearLayout = (LinearLayout) convertView.findViewById(R.id.line_medicine);
        TextView alarmName = (TextView) convertView.findViewById(R.id.tv_alarmName) ;
        TextView dose = (TextView) convertView.findViewById(R.id.tv_dose) ;
        TextView doseTypeView =(TextView) convertView.findViewById(R.id.tv_doseType) ;
        TextView period = (TextView) convertView.findViewById(R.id.tv_period) ;
        TextView remainingTime= (TextView) convertView.findViewById(R.id.tv_remainingTime);

        ProgressBar progressBar = (ProgressBar) convertView.findViewById(R.id.progress);



        // Data Set(listViewItemList)에서 position에 위치한 데이터 참조 획득
        final ListViewAlarmItem listViewAlarmItemI = listViewItemArrayList.get(position);

        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                Intent intent = new Intent(activity.getBaseContext().getApplicationContext(), AlarmEditActivity.class);
                //Intent intent = new Intent(getContext().getApplicationContext(), MedicineDetailActivity.class);
                intent.putExtra("groupId",listViewAlarmItemI.getAlramGroupId());
                activity. startActivity(intent);
                //Toast.makeText(getActivity(), "Shoot", Toast.LENGTH_LONG).show(); // 임시 메세지

            }
        });


        // 아이템 내 각 위젯에 데이터 반영
        //iconImageView.setImageDrawable(listViewItem.getUrl());//500,100

        //현재 날짜 구하기
        long now = System.currentTimeMillis();
        Date date =new Date(now);
        SimpleDateFormat mformat = new SimpleDateFormat("yyyy-MM-dd");
        String time = mformat.format(date);
        //날짜 차이 구하기
        // date1, date2 두 날짜를 parse()를 통해 Date형으로 변환.
        try{
            Date FirstDate = mformat.parse(listViewAlarmItemI.getFinishAlram());
            Date SecondDate = mformat.parse(time);
            long calDate = FirstDate.getTime() - SecondDate.getTime();

            // Date.getTime() 은 해당날짜를 기준으로1970년 00:00:00 부터 몇 초가 흘렀는지를 반환해준다.
            // 이제 24*60*60*1000(각 시간값에 따른 차이점) 을 나눠주면 일수가 나온다.
            long calDateDays = calDate / ( 24*60*60*1000);

            calDateDays = Math.abs(calDateDays);

            String StartAlram =mformat.format(listViewAlarmItemI.getStartAlram());
            String FinishAlram=mformat.format(listViewAlarmItemI.getFinishAlram());


            alarmName.setText(listViewAlarmItemI.getAlramName());
            dose.setText(listViewAlarmItemI.getOneTimeDose()+"회");
            doseTypeView.setText(listViewAlarmItemI.getDoseType());
            period.setText(StartAlram+" ~ "+FinishAlram);
            remainingTime.setText((Long.parseLong(listViewAlarmItemI.getDosingPeriod())-calDateDays)+"/"+listViewAlarmItemI.getDosingPeriod());

            double progress =(1.0-((double)(calDateDays)/Double.parseDouble(listViewAlarmItemI.getDosingPeriod()))) *100;
               progressBar.setProgress((int) progress);








            Log.d("time",listViewAlarmItemI.getAlramName());
            Log.d("dos",listViewAlarmItemI.getOneTimeDose());
            Log.d("doseType",listViewAlarmItemI.getDoseType());
            Log.d("period",listViewAlarmItemI.getDosingPeriod());
            Log.d("start",listViewAlarmItemI.getStartAlram());
            Log.d("finish",listViewAlarmItemI.getFinishAlram());


        }catch(Exception e){
             e.printStackTrace();
        }


        // Date로 변환된 두 날짜를 계산한 뒤 그 리턴값으로 long type 변수를 초기화 하고 있다.
        // 연산결과 -950400000. long type 으로 return 된다.




        return convertView;
    }
}
