package com.example.smrp.searchMed;

import android.content.Context;
import android.graphics.Color;
import android.graphics.ColorSpace;
import android.media.Image;
import android.util.Log;
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
    RecyclerView.ViewHolder holder1, holder2;
    int size;
    private int[] row_images;
    int t1=0, t2=0;

    public interface OnItemClickListener {
        void onItemClick(View v, int position, RecyclerView rList) ;
    }

    private OnItemClickListener mListener = null ;

    //public void setOnItemClickListener(OnItemClickListener listener) {
    //  this.mListener = listener ;
    // }

    SearchRecyclerAdapter(ArrayList<ListItem> list, OnItemClickListener mListener, RecyclerView rList, int size, int[] images) {
        mData = list ;
        this.mListener = mListener;
        this.rList = rList;
        this.size = size;
        mSelectedItems.put(0,true);

        row_images = images;
        t1=1;
        for(int i =1 ; i < size; i++) {

            mSelectedItems.put(i,false);}
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
        { holder1 = ((ViewHolder) holder);

        ((ViewHolder) holder).icon.setImageDrawable(item.getIcon()) ;
        ((ViewHolder) holder).name.setText(item.getName());
        ((ViewHolder) holder).name.setTextColor(Color.parseColor("#989898"));

            if(mSelectedItems.get(position)) {
                ((ViewHolder) holder).icon.setImageResource(row_images[position - 1]);
                ((ViewHolder) holder).name.setTextColor(Color.rgb(0, 119, 63));
            }
        }

        else if(holder instanceof TextViewHolder) {
            holder2 = (TextViewHolder) holder;
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

            //  icon.setImageResource();
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition() ;
                    mListener.onItemClick(v, getAdapterPosition(),rList);
                    if (mSelectedItems.get(pos, false) ){// 00전체가 아닌 아이템이 선택된 상태에서 다시 한번 선택(클릭)시
                        mSelectedItems.put(pos, false);
                        notifyItemChanged(pos); // 다시 그린다.(Bind 다시 함)

                    } else { // 00 전체가 아닌 아이템이 선택한 상태가 아닌데 클릭한 경우
                        mSelectedItems.put(pos, true); // 선택된 상태
                        TextView name0 =(TextView)holder2.itemView.findViewById(R.id.Txt_name);//00전체의 ViewHolder에서 00전체 아이템 참조
                        // 00 전체 아이템의 선택 해제 상태를 그림
                        name0.setTextColor(Color.parseColor("#989898"));
                        name0.setBackgroundResource(android.R.color.transparent);
                        //
                        mSelectedItems.put(0, false); //00 전체 선택 해제
                        // 클릭한 아이템 선택된 상태를 그림
                        icon.setImageResource(row_images[pos-1]);
                        name.setTextColor(Color.rgb(0,119,63));
                        //

                    }




                }
            });



        }
    }

    public class TextViewHolder extends RecyclerView.ViewHolder{

        TextView name; int p;
        TextViewHolder(View itemView) {
            super(itemView) ;
            // 뷰 객체에 대한 참조. (hold strong reference)
            name = itemView.findViewById(R.id.Txt_name);

            // text = item.getName();
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.onItemClick(v, getAdapterPosition(),rList);
                    int pos = getAdapterPosition() ;
                    if (mSelectedItems.get(pos, false) ){ //00 전체가 선택된 상태에서 다시 클릭 시
                        mSelectedItems.put(pos, false); // 00 전체 해제
                        //00전체 선택 해제를 그림
                        name.setBackgroundResource(android.R.color.transparent);
                        name.setTextColor(Color.parseColor("#989898"));
                        //
                    } else { //00 전체가 선택되지 않은 상태에서 00 전체 클릭 시
                        mSelectedItems.put(pos, true);// 00 전체 선택
                        for (int i = 1; i < mSelectedItems.size(); i++) {//00 전체가 아닌 아이템들은 선택 해제
                            mSelectedItems.put(i, false);// 선택 해제
                            notifyItemChanged(i); // 다시 그림 -> 다시 bind함으로써 맨 처음 상태로 되돌림(맨 처음 상태 : 해제된 상태)

                        }
                        //00 전체 선택 상태를 그림
                        name.setBackgroundResource(R.drawable.img_stroke);
                        name.setTextColor(Color.rgb(0,119,63));
                        //

                    }


                    // @@@
                    /*if ( mSelectedItems.get(pos, false) ){
                        mSelectedItems.put(pos, false);
                    } else {


                        notifyItemChanged(0);
                        for (int i = 1; i < mSelectedItems.size(); i++) {
                            position = mSelectedItems.keyAt(i);
                            mSelectedItems.put(position, false);
                            notifyItemChanged(position);
                        }


                        mSelectedItems.put(pos, true);

                    }
                    ///@@*/





                }
            });



        }
    }
    private boolean isItemSelected(int position) {
        return mSelectedItems.get(position, false);
    }

}