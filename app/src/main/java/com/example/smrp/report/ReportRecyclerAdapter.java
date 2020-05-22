package com.example.smrp.report;

import android.content.Context;
import android.graphics.Color;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.smrp.R;


import java.util.ArrayList;

public class ReportRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private ArrayList<com.example.smrp.report.ListItem> mData = null ;
    RecyclerView rList;
    private SparseBooleanArray mSelectedItems = new SparseBooleanArray(0);
    public interface OnItemClickListener {
        void onItemClick(View v, int position, RecyclerView rList) ;
    }

    private ReportRecyclerAdapter.OnItemClickListener mListener = null ;

    //public void setOnItemClickListener(OnItemClickListener listener) {
    //  this.mListener = listener ;
    // }

    ReportRecyclerAdapter(ArrayList<com.example.smrp.report.ListItem> list, ReportRecyclerAdapter.OnItemClickListener mListener, RecyclerView rList) {
        mData = list ;
        this.mListener = mListener;
        this.rList = rList;


    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext() ;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) ;

        View view; //inflater.inflate(R.layout.search_list, parent, false) ;
        //    SearchRecyclerAdapter.ViewHolder vh = new SearchRecyclerAdapter.ViewHolder(view) ;

            view = inflater.inflate(R.layout.symptom_list, parent, false);
            return new ReportRecyclerAdapter.ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ListItem item = mData.get(position);
        if(holder instanceof ReportRecyclerAdapter.ViewHolder)
        {
            //((ViewHolder)holder).Lay_symptom.setBackground(Color.parseColor("#FFFFFF"));
            ((ViewHolder)holder).Txt_symptom.setText(item.getSymptom());
      //      ((SearchRecyclerAdapter.ViewHolder) holder).icon.setImageDrawable(item.getIcon()) ;
        //    ((SearchRecyclerAdapter.ViewHolder) holder).name.setText(item.getName());
        }



    }


    @Override
    public int getItemCount() {
        return mData.size() ;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        String Str_symptom;
        ArrayList<String> list; // text = item.getName();
        LinearLayout Lay_symptom;
        TextView Txt_symptom;
        ViewHolder(View itemView) {
            super(itemView) ;
            Lay_symptom = itemView.findViewById(R.id.Lay_symptom);
            Txt_symptom = itemView.findViewById(R.id.Txt_symptom);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition() ;
                    mListener.onItemClick(v, getAdapterPosition(),rList);




                }
            });



        }
    }



}
