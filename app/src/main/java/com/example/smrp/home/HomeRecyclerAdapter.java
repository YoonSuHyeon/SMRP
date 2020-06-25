package com.example.smrp.home;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smrp.R;

import java.util.ArrayList;
import java.util.StringTokenizer;

public class HomeRecyclerAdapter extends RecyclerView.Adapter<HomeRecyclerAdapter.ViewHolder> {
    private ArrayList<HomeMedItem> mList;
    public class ViewHolder extends RecyclerView.ViewHolder{
        protected ImageView im;
        protected TextView tv2;
        protected LinearLayout linearLayout;
        public ViewHolder(View view){
            super(view);
            this.im = view.findViewById(R.id.imageView1);
            this.tv2 = view.findViewById(R.id.med_name);
            linearLayout = view.findViewById(R.id.line_medicine);
        }

    }
    HomeRecyclerAdapter(ArrayList<HomeMedItem> list){
        this.mList=list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType){
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.homeitem,viewGroup,false);
        ViewHolder viewHolder =new ViewHolder(view);
        return  viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder,int position){
        String text = mList.get(position).getName();
        StringTokenizer st = new StringTokenizer(text,"(");
        String text2 = st.nextToken();
        if(position==0){
            viewHolder.im.setImageResource(R.drawable.rank1);
        }else if(position==1){
            viewHolder.im.setImageResource(R.drawable.rank2);
            viewHolder.linearLayout.setBackgroundColor(Color.parseColor("#D88659"));
        }else{
            viewHolder.im.setImageResource(R.drawable.rank3);
            viewHolder.linearLayout.setBackgroundColor(Color.parseColor("#5DDDCE"));
        }
        viewHolder.tv2.setText(text2);
    }

    @Override
    public int getItemCount(){
        return (null != mList ? mList.size() : 0);
    }
}

