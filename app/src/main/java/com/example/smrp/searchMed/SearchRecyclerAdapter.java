package com.example.smrp.searchMed;

import android.content.Context;
import android.graphics.Color;
import android.graphics.ColorSpace;
import android.media.Image;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.example.smrp.R;


import java.util.ArrayList;
import java.util.List;

public class SearchRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private ArrayList<ListItem> mData = null ;
    RecyclerView rList;
    private SparseBooleanArray mSelectedItems = new SparseBooleanArray(0);
    public interface OnItemClickListener {
        void onItemClick(View v, int position, RecyclerView rList) ;
    }

    private OnItemClickListener mListener = null ;

    //public void setOnItemClickListener(OnItemClickListener listener) {
    //  this.mListener = listener ;
    // }

    SearchRecyclerAdapter(ArrayList<ListItem> list, OnItemClickListener mListener, RecyclerView rList) {
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
        if(viewType == 0)
        {
            view = inflater.inflate(R.layout.search_list, parent, false);
            return new ViewHolder(view);
        }
        else if(viewType == 1){
            view = inflater.inflate(R.layout.search_list2, parent, false);
            return new TextViewHolder(view);
        }
        //   return vh ;
        return null;    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ListItem item = mData.get(position);
        if(holder instanceof ViewHolder)
        {
            ((ViewHolder) holder).icon.setImageDrawable(item.getIcon()) ;
            ((ViewHolder) holder).name.setText(item.getName());
        }
        else if(holder instanceof TextViewHolder) {
            ((TextViewHolder) holder).name.setText(item.getName());

        }


    }


    @Override
    public int getItemCount() {
        return mData.size() ;
    }
    @Override
    public int getItemViewType(int position) {
        return mData.get(position).getViewType();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView icon ;
        TextView name;
        String text;
        ArrayList<String> list; // text = item.getName();
        ViewHolder(View itemView) {
            super(itemView) ;

            // 뷰 객체에 대한 참조. (hold strong reference)
            icon = itemView.findViewById(R.id.Img_icon) ;
            name = itemView.findViewById(R.id.Txt_name);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition() ;
                    mListener.onItemClick(v, getAdapterPosition(),rList);




                }
            });



        }
    }

    public class TextViewHolder extends RecyclerView.ViewHolder{

        TextView name;
        TextViewHolder(View itemView) {
            super(itemView) ;

            // 뷰 객체에 대한 참조. (hold strong reference)
            name = itemView.findViewById(R.id.Txt_name);
            // text = item.getName();
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