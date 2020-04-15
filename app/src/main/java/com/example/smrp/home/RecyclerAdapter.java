package com.example.smrp.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.smrp.R;

import java.util.ArrayList;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {
    private ArrayList<ListItem> mData = null ;


    RecyclerAdapter(ArrayList<ListItem> list) {
        mData = list ;
    }


    @Override
    public RecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext() ;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) ;

        View view = inflater.inflate(R.layout.home_alarm_list, parent, false) ;
        RecyclerAdapter.ViewHolder vh = new RecyclerAdapter.ViewHolder(view) ;

        return vh ;
    }


    @Override
    public void onBindViewHolder(RecyclerAdapter.ViewHolder holder, int position) {

        ListItem item = mData.get(position) ;

        holder.icon.setImageDrawable(item.getIcon()) ;
        holder.date.setText(item.getDate()) ;
        holder.name.setText(item.getName());
        holder.contents.setText(item.getContents()) ;
    }


    @Override
    public int getItemCount() {
        return mData.size() ;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView icon ;
        TextView date ;
        TextView contents ;
        TextView name;

        ViewHolder(View itemView) {
            super(itemView) ;

            // 뷰 객체에 대한 참조. (hold strong reference)
            icon = itemView.findViewById(R.id.Img_icon) ;
            date = itemView.findViewById(R.id.Txt_date) ;
            contents = itemView.findViewById(R.id.Txt_contents) ;
            name = itemView.findViewById(R.id.Txt_name);
        }
    }

}