package com.example.smrp.searchMed;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.smrp.R;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity implements SearchRecyclerAdapter.OnItemClickListener{
    Button Btn_search;
    TextView Txt_Lst_empty;
    RecyclerView Lst_shape= null ;
    RecyclerView Lst_color= null ;
    RecyclerView Lst_dosageForm= null ;
    RecyclerView Lst_line= null ;
    ImageView iv_back;
    SearchRecyclerAdapter adapter_row1 = null ;
    SearchRecyclerAdapter adapter_row2  = null ;
    SearchRecyclerAdapter adapter_row3  = null ;
    SearchRecyclerAdapter adapter_row4 = null ;


    ArrayList<ListItem> list_row1 = new ArrayList<ListItem>();
    ArrayList<ListItem> list_row2= new ArrayList<ListItem>();
    ArrayList<ListItem> list_row3 = new ArrayList<ListItem>();
    ArrayList<ListItem> list_row4 = new ArrayList<ListItem>();

    Button Btn_add;
    ArrayList<String> selected_row1 = new ArrayList<String>();
    ArrayList<String> selected_row2 = new ArrayList<String>();
    private SparseBooleanArray mSelectedItems1 = new SparseBooleanArray(0);
    private SparseBooleanArray mSelectedItems2 = new SparseBooleanArray(0);
    private SparseBooleanArray mSelectedItems3 = new SparseBooleanArray(0);
    private SparseBooleanArray mSelectedItems4 = new SparseBooleanArray(0);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        Lst_shape = findViewById(R.id.Lst_shape);
        Lst_color = findViewById(R.id.Lst_color);
        Lst_dosageForm = findViewById(R.id.Lst_dosageForm);
        Lst_line = findViewById(R.id.Lst_line);
        Btn_search = findViewById(R.id.Btn_search);
        iv_back = findViewById(R.id.iv_back);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);

        adapter_row1  = new SearchRecyclerAdapter(list_row1,this, Lst_shape) ;
        adapter_row2  = new SearchRecyclerAdapter(list_row2,this, Lst_color) ;
        adapter_row3  = new SearchRecyclerAdapter(list_row3,this, Lst_dosageForm) ;
        adapter_row4  = new SearchRecyclerAdapter(list_row4,this,Lst_line) ;


        Lst_shape .setAdapter(adapter_row1);
        Lst_shape .setLayoutManager(layoutManager ) ;

        Lst_color .setAdapter(adapter_row2);
        Lst_color .setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)) ;

        Lst_dosageForm .setAdapter(adapter_row3);
        Lst_dosageForm .setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)) ;

        Lst_line .setAdapter(adapter_row4);
        Lst_line .setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)) ;



        DisplayMetrics displayMetrics = new DisplayMetrics();
        WindowManager windowManager = (WindowManager) getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
        windowManager.getDefaultDisplay().getMetrics(displayMetrics);
        int mHeight = displayMetrics.heightPixels/3-5;
        LinearLayout Layout_table= findViewById(R.id.Layout_table);
        ViewGroup.LayoutParams lay = (ViewGroup.LayoutParams) Layout_table.getLayoutParams();
        lay.height =  mHeight;
        Layout_table.setLayoutParams(lay);




        //list_row1 - 모양 결정
        addItem(list_row1,null,
                "모양\n전체",1, "모양 전체");
        addItem(list_row1,getDrawable(R.drawable.ic_triangle),
                "삼각형",0, "삼각형");
        addItem(list_row1,getDrawable(R.drawable.ic_rectangle),
                "사각형",0,"사각형");
        addItem(list_row1,getDrawable(R.drawable.ic_rhombus),
                "마름모",0,"마름모");
        addItem(list_row1,getDrawable(R.drawable.ic_oblong),
                "장방형",0,"장방형");
        addItem(list_row1,getDrawable(R.drawable.ic_pentagon),
                "오각형",0,"오각형");
        addItem(list_row1,getDrawable(R.drawable.ic_octagon),
                "팔각형",0,"팔각형");
        addItem(list_row1,getDrawable(R.drawable.ic_etc),
                "기타",0,"기타");


        //list_row2 - 색상
        addItem(list_row2,null,
                "색상\n전체",1, "색상 전체");
        addItem(list_row2,getDrawable(R.drawable.ic_white),
                "하양",0, "하양");
        addItem(list_row2,getDrawable(R.drawable.ic_yellow),
                "노랑",0,"노랑");
        addItem(list_row2,getDrawable(R.drawable.ic_orange),
                "주황",0,"주황");
        addItem(list_row2,getDrawable(R.drawable.ic_pink),
                "분홍",0,"분홍");
        addItem(list_row2,getDrawable(R.drawable.ic_red),
                "빨강",0,"빨강");
        addItem(list_row2,getDrawable(R.drawable.ic_brown),
                "갈색",0,"갈색");
        addItem(list_row2,getDrawable(R.drawable.ic_yellowgreen),
                "연두",0,"연두");


        //list_row3 - 제형
        addItem(list_row3,null,
                "제형\n전체",1,"제형 전체");
        addItem(list_row3,getDrawable(R.drawable.ic_ref),
                "정제류",0,"정제류");
        addItem(list_row3,getDrawable(R.drawable.ic_hard_cap),
                "경질캡슐",0,"경질캡슐");
        addItem(list_row3,getDrawable(R.drawable.ic_soft_cap),
                "연질캡슐",0,"연질캡슐");

        //list_row4 - 분할선
        addItem(list_row4,null,
                "분할선전체",1,"분할선전체");
        addItem(list_row4,getDrawable(R.drawable.ic_empty),
                "없음",0,"없음");
        addItem(list_row4,getDrawable(R.drawable.ic_minus),
                "(-)형",0,"(-)형");
        addItem(list_row4,getDrawable(R.drawable.ic_line_plus),
                "(+)형",0,"(+)형");
        addItem(list_row4,getDrawable(R.drawable.ic_line_etc),
                "기타",0,"기타");

        mSelectedItems1.put(0,true);
        mSelectedItems2.put(0,true);
        mSelectedItems3.put(0,true);
        mSelectedItems4.put(0,true);
        for(int i =1 ; i < 8; i++) {mSelectedItems1.put(i,false); mSelectedItems2.put(i,false);}
        for(int i =1 ; i < 4; i++) mSelectedItems3.put(i,false);
        for(int i =1 ; i < 5; i++) mSelectedItems4.put(i,false);


        adapter_row3.notifyDataSetChanged();



        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        Btn_search.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String a = " ";
                String b = " ";
                for(int i = 0; i<mSelectedItems1.size();i++){
                    if(mSelectedItems1.get(i)){
                        // 이부분은 DB로 연동해서 할 부분(나중에 테이블에 나오게 할거임)
                        a += list_row1.get(i).getText() +" ";
                    }

                }

                for(int i = 0; i<mSelectedItems2.size();i++){
                    // 이부분은 DB로 연동해서 할 부분(나중에 테이블에 나오게 할거임)
                    if(mSelectedItems2.get(i)){
                        b += list_row2.get(i).getText()+" ";
                    }

                }
                Log.w("Search",a);
                Log.w("Search",b);
            }
        });


    }

    public void addItem(ArrayList<ListItem> list, Drawable icon, String n, int v, String t) {
        ListItem item = new ListItem();
        if((icon!=null)){
            item.setIcon(icon);


        }

        item.setName(n);
        item.setViewType(v);
        item.setText(t);
        list.add(item);
    }

    @Override
    public void onItemClick(View v, int position, RecyclerView rList) {


        ListItem item;
        TextView name =  v.findViewById(R.id.Txt_name);
        ImageView icon = v.findViewById(R.id.Img_icon);
        switch(rList.getId()){

            case R.id.Lst_shape : {
                item= list_row1.get(position) ;
                if ( mSelectedItems1.get(position, false) ){
                    mSelectedItems1.put(position, false);
                    if(item.getViewType()==1){
                        name.setBackgroundResource(android.R.color.transparent);
                        name.setTextColor(getResources().getColor(R.color.searchFont));
                    }
                    else{
                        icon.setBackgroundResource(android.R.color.transparent);
                        name.setTextColor(getResources().getColor(R.color.searchFont));
                    }
                } else {
                    mSelectedItems1.put(position, true);
                    if(item.getViewType()==1){
                        name.setBackgroundResource(R.drawable.img_stroke);
                        name.setTextColor(Color.rgb(0,119,63));
                    }
                    else{
                        icon.setBackgroundResource(R.drawable.img_stroke);

                        name.setTextColor(Color.rgb(0,119,63));
                    }
                }


                break;
            }
            case R.id.Lst_color : {
                item= list_row2.get(position);
                Toast.makeText(this,  item.getName(), Toast.LENGTH_SHORT).show();
                if ( mSelectedItems2.get(position, false) ){
                    mSelectedItems2.put(position, false);
                    if(item.getViewType()==1){
                        name.setBackgroundResource(android.R.color.transparent);
                        name.setTextColor(getResources().getColor(R.color.searchFont));
                    }
                    else{
                        icon.setBackgroundResource(android.R.color.transparent);
                        name.setTextColor(getResources().getColor(R.color.searchFont));
                    }
                } else {
                    mSelectedItems2.put(position, true);
                    if(item.getViewType()==1){
                        name.setBackgroundResource(R.drawable.img_stroke);
                        name.setTextColor(Color.rgb(0,119,63));
                    }
                    else{
                        icon.setBackgroundResource(R.drawable.img_stroke);

                        name.setTextColor(Color.rgb(0,119,63));
                    }
                }


                break;
            }
            case R.id.Lst_dosageForm : {
                item= list_row3.get(position) ;
                Toast.makeText(this,  item.getName(), Toast.LENGTH_SHORT).show();
                if ( mSelectedItems3.get(position, false) ){
                    mSelectedItems3.put(position, false);
                    if(item.getViewType()==1){
                        name.setBackgroundResource(android.R.color.transparent);
                        name.setTextColor(getResources().getColor(R.color.searchFont));
                    }
                    else{
                        icon.setBackgroundResource(android.R.color.transparent);
                        name.setTextColor(getResources().getColor(R.color.searchFont));
                    }
                } else {
                    mSelectedItems3.put(position, true);
                    if(item.getViewType()==1){
                        name.setBackgroundResource(R.drawable.img_stroke);
                        name.setTextColor(Color.rgb(0,119,63));
                    }
                    else{
                        icon.setBackgroundResource(R.drawable.img_stroke);

                        name.setTextColor(Color.rgb(0,119,63));
                    }
                }


                break;
            }
            case R.id.Lst_line : {
                item= list_row4.get(position) ;
                Toast.makeText(this,  item.getName(), Toast.LENGTH_SHORT).show();
                if ( mSelectedItems4.get(position, false) ){
                    mSelectedItems4.put(position, false);
                    if(item.getViewType()==1){
                        name.setBackgroundResource(android.R.color.transparent);
                        name.setTextColor(getResources().getColor(R.color.searchFont));
                    }
                    else{
                        icon.setBackgroundResource(android.R.color.transparent);
                        name.setTextColor(getResources().getColor(R.color.searchFont));
                    }
                } else {
                    mSelectedItems4.put(position, true);
                    if(item.getViewType()==1){
                        name.setBackgroundResource(R.drawable.img_stroke);
                        name.setTextColor(Color.rgb(0,119,63));
                    }
                    else{
                        icon.setBackgroundResource(R.drawable.img_stroke);

                        name.setTextColor(Color.rgb(0,119,63));
                    }
                }


                break;
            }
        }


    }



}