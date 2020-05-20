package com.example.smrp.searchMed;

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
import com.example.smrp.medicine.MedicineDetailActivity;
import com.example.smrp.reponse_medicine;

import java.util.ArrayList;
import java.util.List;

public class MedicineAdapter extends RecyclerView.Adapter<MedicineAdapter.ViewHolder> {
    private ArrayList<MedicineItem> mData = null;
    Context context;
    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView medicine_img;
        TextView medicine_tv1,medicine_tv2,medicine_tv3,medicine_tv4;
        ViewHolder(View itemView){
            super(itemView);

            medicine_img = itemView.findViewById(R.id.medicine_img);
            medicine_tv1 = itemView.findViewById(R.id.medicine_tv1);
            medicine_tv2 = itemView.findViewById(R.id.medicine_tv2);
            medicine_tv3 = itemView.findViewById(R.id.medicine_tv3);
            medicine_tv4 = itemView.findViewById(R.id.medicine_tv4);

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

    MedicineAdapter(ArrayList<MedicineItem> list){
        mData = list;
    }

    @NonNull
    @Override
    public MedicineAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.recycler_medicine_item,parent,false);
        MedicineAdapter.ViewHolder vh = new MedicineAdapter.ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull MedicineAdapter.ViewHolder holder, int position) {
        String stringURL = mData.get(position).getStringURL();
        String text1 = mData.get(position).getText1();
        String text2 = mData.get(position).getText2();
        String text3 = mData.get(position).getText3();
        String text4 = mData.get(position).getText4();
        holder.medicine_tv1.setText(text1);
        holder.medicine_tv2.setText(text2);
        holder.medicine_tv3.setText(text3);
        holder.medicine_tv4.setText(text4);
        Glide.with(context).load(stringURL).override(500,100).fitCenter().into(holder.medicine_img);

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }
}
