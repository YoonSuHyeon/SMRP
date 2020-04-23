package com.example.smrp.hospital;

import android.view.View;

//Pharmacy 아이템을 클릭했을 때 리스너 인터페이스
public interface OnHospitalItemClickListener {
    public void onItemClick(HospitalAdapter.ViewHolder holder, View view, int position);
    public void onCallClick(int position);
    public void onUrl(int position);
    public void onPath(int position);
}