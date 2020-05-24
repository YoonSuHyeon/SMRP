package com.example.smrp.searchPrescription;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.smrp.R;
import com.example.smrp.home.RecyclerAdapter;
import com.example.smrp.medicine.MedicineDetailActivity;

import java.util.ArrayList;

public class PrescriptionAdapter extends RecyclerView.Adapter<PrescriptionAdapter.ViewHolder> {
    private ArrayList<Prescriptionitem> list = new ArrayList<>();
    private OnItemClickListener listener;
    Context context;

    PrescriptionAdapter(ArrayList<Prescriptionitem> list){
        this.list = list;
    }

    public interface OnItemClickListener {
        void onItemClick(View v, int position);
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.recycler_medicine_item,parent,false);
        //View view = inflater.inflate(R.layout.)
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String stringURL = list.get(position).getStringURL();
        String text1 = list.get(position).getText1();
        String text2 = list.get(position).getText2();
        String text3 = list.get(position).getText3();
        String text4 = list.get(position).getText4();
        holder.t1.setText(text1);
        holder.t2.setText(text2);
        holder.t3.setText(text3);
        holder.t4.setText(text4);
        Glide.with(context).load(stringURL).override(500,100).fitCenter().into(holder.img);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView img;
        TextView t1, t2 ,t3 ,t4;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.medicine_img);
            t1 = itemView.findViewById(R.id.medicine_tv1);
            t2 = itemView.findViewById(R.id.medicine_tv2);
            t3 = itemView.findViewById(R.id.medicine_tv3);
            t4 = itemView.findViewById(R.id.medicine_tv4);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();
                    if(pos != RecyclerView.NO_POSITION){
                        Intent intent = new Intent(context.getApplicationContext(), MedicineDetailActivity.class);
                        context.startActivity(intent);

                    }
                }
            });
        }
    }


}
