package com.example.smrp.report;

import android.content.Context;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.smrp.R;

import java.util.ArrayList;

public class DiseaseRecyclerAdapter  extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private ArrayList<DiseaseList> mData = null ;
    RecyclerView rList;

    public interface OnItemClickListener {
        void onItemClick(View v, int position, RecyclerView rList) ;
    }

    private DiseaseRecyclerAdapter.OnItemClickListener mListener = null ;

    //public void setOnItemClickListener(OnItemClickListener listener) {
    //  this.mListener = listener ;
    // }

    DiseaseRecyclerAdapter(ArrayList<DiseaseList> list, DiseaseRecyclerAdapter.OnItemClickListener mListener, RecyclerView rList) {
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

        view = inflater.inflate(R.layout.disease_list, parent, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        DiseaseList item = mData.get(position);
        if(holder instanceof ViewHolder)
        {
            //((ViewHolder)holder).Lay_symptom.setBackground(Color.parseColor("#FFFFFF"));
            ((ViewHolder)holder).Txt_disease.setText(item.getDisease());
            ((ViewHolder)holder).Txt_symptom.setText(item.getSymptom());
            ((ViewHolder)holder).Txt_probability.setText(item.getProbability());
            ((ViewHolder)holder).Txt_department.setText(item.getDepartment());
            //      ((SearchRecyclerAdapter.ViewHolder) holder).icon.setImageDrawable(item.getIcon()) ;
            //    ((SearchRecyclerAdapter.ViewHolder) holder).name.setText(item.getName());
        }



    }


    @Override
    public int getItemCount() {

        Log.e("F", mData.size()+" ");
        return mData.size() ;
    }
    @Override
    public int getItemViewType(int position) {
        return 0;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        String Str_symptom;
        ArrayList<String> list; // text = item.getName();
       // LinearLayout Lay_symptom;
        TextView Txt_symptom;
        TextView Txt_disease;
        TextView Txt_department;
        TextView Txt_probability;
        ViewHolder(View itemView) {
            super(itemView) ;
           //Lay_symptom = itemView.findViewById(R.id.Lay_symptom);
            Txt_disease = itemView.findViewById(R.id.Txt_disease);
            Txt_symptom = itemView.findViewById(R.id.Txt_Symptom);
            Txt_probability = itemView.findViewById(R.id.Txt_probability);
            Txt_department = itemView.findViewById(R.id.Txt_department);

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
