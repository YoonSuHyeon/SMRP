package com.example.smrp.report;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.example.smrp.R;


import java.util.ArrayList;

public class ReportRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private ArrayList<ListItem> mData = null ;
    RecyclerView rList;
    private SparseBooleanArray mSelectedItems = new SparseBooleanArray(0);
    public interface OnItemClickListener {
        void onItemClick(View v, int position, RecyclerView rList) ;
    }

    private ReportRecyclerAdapter.OnItemClickListener mListener = null ;

    //public void setOnItemClickListener(OnItemClickListener listener) {
    //  this.mListener = listener ;
    // }

    ReportRecyclerAdapter(ArrayList<ListItem> list, OnItemClickListener mListener, RecyclerView rList) {
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

            return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ListItem item = mData.get(position);
        if(holder instanceof ViewHolder)
        {
            //((ViewHolder)holder).Lay_symptom.setBackground(Color.parseColor("#FFFFFF"));
            ((ViewHolder)holder).Txt_symptom.setText(item.getSymptom());
            ((ViewHolder)holder).Txt_symptom_content.setText(item.getSymptomContent());
            if ( mSelectedItems.get(position) ){
                //holder.itemView.setBackgroundColor(Color.parseColor("#FFFFFF"));
               // holder.itemView.setBackgroundColor(Color.parseColor("#FFFFFF"));
                //((ViewHolder) holder).Txt_symptom.setBackgroundColor(Color.parseColor("#2658FB"));
                ((ViewHolder) holder).Lay_symptom.setBackgroundResource(R.drawable.shadow3);
                ((ViewHolder) holder).Txt_symptom.setTextColor(Color.parseColor("#0022FF"));
                ((ViewHolder) holder).Txt_symptom_content.setTextColor(Color.parseColor("#0022FF"));
                ((ViewHolder) holder).ic_check.setImageResource(R.drawable.ic_check_color);
            } else {
                //holder.itemView.setBackgroundColor(Color.parseColor("#2658FB"));

                //( (ViewHolder) holder).Txt_symptom.setBackgroundColor(Color.parseColor("#FFFFFF"));
                ((ViewHolder) holder).Lay_symptom.setBackgroundResource(R.drawable.shadow2);
                ((ViewHolder) holder). Txt_symptom.setTextColor(Color.parseColor("#2E2E2E"));
                ((ViewHolder) holder).Txt_symptom_content.setTextColor(Color.parseColor("#8D8D8D"));
                ((ViewHolder) holder).ic_check.setImageResource(R.drawable.ic_check_basic);
            }



      //      ((SearchRecyclerAdapter.ViewHolder) holder).icon.setImageDrawable(item.getIcon()) ;
        //    ((SearchRecyclerAdapter.ViewHolder) holder).name.setText(item.getName());
        }



    }


    @Override
    public int getItemCount() {


        return mData.size() ;
    }
    @Override
    public int getItemViewType(int position) {
        return 0;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        String Str_symptom;
        ArrayList<String> list; // text = item.getName();
        LinearLayout Lay_symptom;
        TextView Txt_symptom;
        TextView Txt_symptom_content;
        ImageView ic_check;
        ViewHolder(View itemView) {
            super(itemView) ;
            Lay_symptom = itemView.findViewById(R.id.Lay_symptom);
            Txt_symptom = itemView.findViewById(R.id.Txt_symptom);
            ic_check = itemView.findViewById(R.id.ic_check);
            Txt_symptom_content = itemView.findViewById(R.id.Txt_symptom_content);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition() ;
                    mListener.onItemClick(v, getAdapterPosition(),rList);
                    if ( mSelectedItems.get(position, false) ){
                        mSelectedItems.put(position, false);
                        Lay_symptom.setBackgroundResource(R.drawable.shadow2);
                        Txt_symptom.setTextColor(Color.parseColor("#2E2E2E"));
                        Txt_symptom_content.setTextColor(Color.parseColor("#8D8D8D"));
                        ic_check.setImageResource(R.drawable.ic_check_basic);
                       // Txt_symptom.setBackgroundColor(Color.parseColor("#FFFFFF"));
                       // Txt_symptom.setTextColor(Color.parseColor("#737373"));

                    } else {
                        mSelectedItems.put(position, true);
                        Lay_symptom.setBackgroundResource(R.drawable.shadow3);
                        Txt_symptom.setTextColor(Color.parseColor("#0022FF"));
                        Txt_symptom_content.setTextColor(Color.parseColor("#0022FF"));
                        ic_check.setImageResource(R.drawable.ic_check_color);
                       // Txt_symptom.setBackgroundColor(Color.parseColor("#2658FB"));
                        //Txt_symptom.setTextColor(Color.parseColor("#FFFFFF"));
                    }






                }
            });



        }
    }



}
