package com.example.smrp.home;

import android.content.Context;
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
import com.example.smrp.searchMed.ListItem;
import com.example.smrp.searchMed.SearchRecyclerAdapter;

import java.util.ArrayList;

public class HomeRecyclerAdapter extends RecyclerView.Adapter<HomeRecyclerAdapter.ViewHolder> {
    private ArrayList<HomeMedItem> mList;
    public class ViewHolder extends RecyclerView.ViewHolder{
        protected TextView tv1;
        protected TextView tv2;
        public ViewHolder(View view){
            super(view);
            this.tv1 = (TextView) view.findViewById(R.id.textView1);
            this.tv2 = (TextView) view.findViewById(R.id.textView2);
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
        viewHolder.tv1.setText((position+1)+"위");
        viewHolder.tv2.setText(mList.get(position).getName());
    }

    @Override
    public int getItemCount(){
        return (null != mList ? mList.size() : 0);
    }
}

