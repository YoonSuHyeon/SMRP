package com.example.smrp.pharmacy;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smrp.R;

import java.util.ArrayList;
import java.util.HashMap;

public class PharmacyAdapter  extends  RecyclerView.Adapter<PharmacyAdapter.ViewHolder> {

    public ArrayList<Pharmacy> items = new ArrayList<Pharmacy>();
    public OnPharmacyItemClickListener listener;
    private HashMap<String,String> hash_type,hash_remain_stat;

    public PharmacyAdapter(ArrayList<Pharmacy> list){
        hash_type = new HashMap<>();
        hash_remain_stat = new HashMap<>();

        hash_type.put("01","약국");hash_type.put("02","우체국");hash_type.put("03","농협");
        hash_remain_stat.put("plenty","100개 이상 ~");hash_remain_stat.put("some","~100개미만");
        hash_remain_stat.put("few","2개이상 ~ 30개 미만");hash_remain_stat.put("empty","1개 이하");
        hash_remain_stat.put("break","판매중지");
        this.items = list;
    }
    public interface  OnPharmacyItemClickListener{
        void onItemClick(PharmacyAdapter.ViewHolder holder, View view, int position);
        void onCallClick(int position);
        void onPath(int position);
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.pharmacy_list, parent, false);
        return new ViewHolder(itemView, this);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Pharmacy item = items.get(position);
        holder.setItem(item);
    }

    public void setOnItemClickListener(OnPharmacyItemClickListener listener){
        this.listener = listener;
    }

    public void onItemClick(PharmacyAdapter.ViewHolder holder, View view, int position){
        if(listener != null)
            listener.onItemClick(holder,view,position);
    }
    public void onCallClick(int position){
        if(listener != null){
            listener.onCallClick(position);
        }
    }
    public void onPath(int position){
        if(listener != null){
            listener.onPath(position);
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView textview_name,textview_time,textView_create_data,textview_type,textview_address,textView_mask_state;
        Button button_path;

        public ViewHolder(View itemView, PharmacyAdapter pharmacyAdapter) {
            super(itemView);

            textview_name = itemView.findViewById(R.id.textView_phy_name);
            textview_time = itemView.findViewById(R.id.textView_input_time);
            textView_create_data = itemView.findViewById(R.id.textView_crete_data);
            textview_type = itemView.findViewById(R.id.textView_phy_type);
            textview_address = itemView.findViewById(R.id.textView_address);
            textView_mask_state = itemView.findViewById(R.id.textView_mask);
            button_path= itemView.findViewById(R.id.button_path);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener != null){
                        int position = getAdapterPosition();
                        listener.onItemClick(ViewHolder.this, v, position);
                    }
                }
            });

            button_path.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener != null){
                        int position = getAdapterPosition();
                        listener.onPath(position);
                    }
                }
            });
        }

        public void setItem(Pharmacy item) {
            textview_name.setText(item.getName()); //이름
            textview_time.setText(item.getStock_at()); //입고시간
            textView_create_data.setText(item.getCreated_at()); //데이터생성일자
            textview_type.setText(hash_type.get(item.getType())); //타입(약국 우체국, 농협)
            textview_address.append(item.getAddr()); //주소
            textView_mask_state.append(hash_remain_stat.get(item.getRemain_stat()));
        }
    }
}
