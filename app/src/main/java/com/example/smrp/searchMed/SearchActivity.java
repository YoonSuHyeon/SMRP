package com.example.smrp.searchMed;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smrp.R;
import com.example.smrp.RetrofitHelper;
import com.example.smrp.RetrofitService;
import com.example.smrp.reponse_medicine2;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchActivity extends AppCompatActivity implements SearchRecyclerAdapter.OnItemClickListener{
    Boolean scrollStop;
    TableRow[] tbrow ;

    ArrayList<String> shape1 = new ArrayList<String>();
    ArrayList<String> formula1 = new ArrayList<String>();
    ArrayList<String> line1 = new ArrayList<String>();
    ArrayList<String> color1 = new ArrayList<String>();
    String color="color_all",shape="shape_all",formula="formula_all",line="line_all";
    //Medicines medicines;
    Button Btn_search;
    TextView Txt_Lst_empty;
    RecyclerView Lst_shape= null ;
    RecyclerView Lst_color= null ;
    RecyclerView Lst_dosageForm= null ;
    RecyclerView Lst_line= null ;
    ImageView iv_back;

    //TableRow tbrow;

    SearchRecyclerAdapter adapter_row1 = null ;
    SearchRecyclerAdapter adapter_row2  = null ;
    SearchRecyclerAdapter adapter_row3  = null ;
    SearchRecyclerAdapter adapter_row4 = null ;
    ImageView iv1;
    //ScrollView scrollView,scrollView2;

    Bitmap bitmap,resized;
    String stringURL;
    ArrayList<ListItem> list_row1 = new ArrayList<ListItem>();
    ArrayList<ListItem> list_row2= new ArrayList<ListItem>();
    ArrayList<ListItem> list_row3 = new ArrayList<ListItem>();
    ArrayList<ListItem> list_row4 = new ArrayList<ListItem>();

    EditText et_findMedicine;
    ArrayList<MedicineItem> recyclerItem = null;
    Button Btn_add;
    ArrayList<String> selected_row1 = new ArrayList<String>();
    ArrayList<String> selected_row2 = new ArrayList<String>();
    private SparseBooleanArray mSelectedItems1 = new SparseBooleanArray(0);
    private SparseBooleanArray mSelectedItems2 = new SparseBooleanArray(0);
    private SparseBooleanArray mSelectedItems3 = new SparseBooleanArray(0);
    private SparseBooleanArray mSelectedItems4 = new SparseBooleanArray(0);

    //@@@@@@@@@@@@@@@@@
    private int[] row_images1 = {R.drawable.ic_circle_green,R.drawable.ic_triangle_green, R.drawable.ic_rectangle_green,R.drawable.ic_rhombus_green, R.drawable.ic_oblong_green,R.drawable.ic_oval_green,R.drawable.ic_semicircle_green,R.drawable.ic_pentagon_green,R.drawable.ic_hexagon_green,R.drawable.ic_octagon_green,R.drawable.ic_etc_green};
    private int[] row_images2 = {R.drawable.ic_white_green,R.drawable.ic_yellow_green,R.drawable.ic_orange_green,R.drawable.ic_pink_green,R.drawable.ic_red_green,R.drawable.ic_brown_green,R.drawable.ic_yellowgreen_green, R.drawable.ic_purple_green,R.drawable.ic_blue_green,R.drawable.ic_blue_green,R.drawable.ic_navy_green,R.drawable.ic_redviolet_green,R.drawable.ic_gray_green,R.drawable.ic_black_green};
    private int[] row_images3 = {R.drawable.ic_ref_green,R.drawable.ic_hard_cap_green,R.drawable.ic_soft_cap_green};
    private int[] row_images4 = {R.drawable.ic_empty_green,R.drawable.ic_line_minus_green,R.drawable.ic_line_pluse_green,R.drawable.ic_line_etc_green};
    //@@@@@@@@@@@@@@@

    private InputMethodManager imm;
    @SuppressLint("ClickableViewAccessibility")
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        et_findMedicine = findViewById(R.id.et_findMedicine);
        Lst_shape = findViewById(R.id.Lst_shape);
        Lst_color = findViewById(R.id.Lst_color);
        Lst_dosageForm = findViewById(R.id.Lst_dosageForm);
        Lst_line = findViewById(R.id.Lst_line);
        Btn_search = findViewById(R.id.Btn_search);
        iv_back = findViewById(R.id.iv_back);

        shape1.add("모양전체");
        color1.add("색상전체");
        formula1.add("제형전체");
        line1.add("분할선전체");

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);

        adapter_row1  = new SearchRecyclerAdapter(list_row1,this, Lst_shape,12, row_images1) ;
        adapter_row2  = new SearchRecyclerAdapter(list_row2,this, Lst_color,15,row_images2) ;
        adapter_row3  = new SearchRecyclerAdapter(list_row3,this, Lst_dosageForm,4,row_images3) ;
        adapter_row4  = new SearchRecyclerAdapter(list_row4,this,Lst_line,5,row_images4) ;


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
                "모양\n전체",1, "모양전체");
        addItem(list_row1,getDrawable(R.drawable.ic_circle), "원형",0,"원형");
        addItem(list_row1,getDrawable(R.drawable.ic_triangle),
                "삼각형",0, "삼각형");
        addItem(list_row1,getDrawable(R.drawable.ic_rectangle),
                "사각형",0,"사각형");
        addItem(list_row1,getDrawable(R.drawable.ic_rhombus),
                "마름모",0,"마름모형");
        addItem(list_row1,getDrawable(R.drawable.ic_oblong),
                "장방형",0,"장방형");
        addItem(list_row1,getDrawable(R.drawable.ic_oval),
                "타원형",0,"타원형");
        addItem(list_row1,getDrawable(R.drawable.ic_semicircle),
                "반원형",0,"반원형");

        addItem(list_row1,getDrawable(R.drawable.ic_pentagon),
                "오각형",0,"오각형");

        addItem(list_row1,getDrawable(R.drawable.ic_hexagon),
                "육각형",0,"육각형");

        addItem(list_row1,getDrawable(R.drawable.ic_octagon),
                "팔각형",0,"팔각형");


        addItem(list_row1,getDrawable(R.drawable.ic_etc),
                "기타",0,"기타");


        //list_row2 - 색상
        addItem(list_row2,null,
                "색상\n전체",1, "색상전체");
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
        addItem(list_row2,getDrawable(R.drawable.ic_purple),
                "보라",0,"보라");
        addItem(list_row2,getDrawable(R.drawable.ic_bluegreen),
                "청록",0,"청록");
        addItem(list_row2,getDrawable(R.drawable.ic_blue),
                "파랑",0,"파랑");
        addItem(list_row2,getDrawable(R.drawable.ic_navy),
                "남색",0,"남색");
        addItem(list_row2,getDrawable(R.drawable.ic_redviolet),
                "자주",0,"자주");
        addItem(list_row2,getDrawable(R.drawable.ic_gray),
                "회색",0,"회색");
        addItem(list_row2,getDrawable(R.drawable.ic_black),
                "검정",0,"검정");









        //list_row3 - 제형
        addItem(list_row3,null,
                "제형\n전체",1,"제형전체");
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
                "(-)형",0,"-");
        addItem(list_row4,getDrawable(R.drawable.ic_line_plus),
                "(+)형",0,"+");
        addItem(list_row4,getDrawable(R.drawable.ic_line_etc),
                "기타",0,"기타");

        mSelectedItems1.put(0,true);
        mSelectedItems2.put(0,true);
        mSelectedItems3.put(0,true);
        mSelectedItems4.put(0,true);
        for(int i =1 ; i < 12; i++) mSelectedItems1.put(i,false);
        for(int i =1 ; i < 15; i++) mSelectedItems2.put(i,false);
        for(int i =1 ; i < 4; i++) mSelectedItems3.put(i,false);
        for(int i =1 ; i < 5; i++) mSelectedItems4.put(i,false);

        Lst_shape.getItemAnimator().setChangeDuration(0);
        Lst_color.getItemAnimator().setChangeDuration(0);
        Lst_dosageForm.getItemAnimator().setChangeDuration(0);
        Lst_line.getItemAnimator().setChangeDuration(0);
        adapter_row3.notifyDataSetChanged();



        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

      //  RecyclerView recyclerView = findViewById(R.id.recycler_medicine);
        recyclerItem = new ArrayList<MedicineItem>();

        imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        Btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imm.hideSoftInputFromWindow(et_findMedicine.getWindowToken(), 0);
                for(int i = 0; i <shape1.size(); i++){

                }
                for(int i = 0; i <line1.size(); i++){

                }
                if(shape1.size()==0){

                }

                RetrofitService networkService= RetrofitHelper.getRetrofit().create(RetrofitService.class);
                //medicines = new Medicines(shape,color,formula,line);
                //Call<List<com.example.smrp.reponse_medicine>> call = networkService.findList(shape,color,formula,line);

                SelectedItem selectedItem = new SelectedItem(et_findMedicine.getText().toString(),shape1,color1,formula1,line1);
                Call<List<reponse_medicine2>> call = networkService.findList2(selectedItem);


                call.enqueue(new Callback<List<reponse_medicine2>>() {
                    @Override
                    public void onResponse(Call<List<reponse_medicine2>> call, Response<List<reponse_medicine2>> response) {

                        List<reponse_medicine2> list = response.body();
                       // int a = list.size();
                        RecyclerView recyclerView = findViewById(R.id.recycler_medicine);
                        if(recyclerItem.size()!=0){
                            recyclerItem.clear();
                        }
                       // recyclerItem = new ArrayList<MedicineItem>();
                       try {
                           for (int i = 0; i < list.size(); i++) {
                               recyclerItem.add(new MedicineItem(list.get(i).getItemSeq(),list.get(i).getItemImage(), list.get(i).getItemName(), list.get(i).getEntpName(), list.get(i).getFormCodeName(), list.get(i).getEtcOtcName()));
                           }
                       }catch(NullPointerException e){
                           e.printStackTrace();
                       }

                        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

                        MedicineAdapter adapter = new MedicineAdapter(recyclerItem);
                        recyclerView.setAdapter(adapter);

                    }

                    @Override
                    public void onFailure(Call<List<reponse_medicine2>> call, Throwable t) {

                    }
                });



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

        //@@@@@@@@@@@@@2
        //RecyclerView에 있는 item들의 Text부분을 가지고와서 색깔을 변경한다.
        //TextView name2 =(TextView)rList.findViewHolderForAdapterPosition(2).itemView.findViewById(R.id.Txt_name);
       // name2.setTextColor(Color.parseColor("#989898"));
        //@@@@@@@@@@@@@@@222

        switch(rList.getId()){

            case R.id.Lst_shape : {

                item= list_row1.get(position) ;
                Toast.makeText(this,  item.getName(), Toast.LENGTH_SHORT).show();
               /* switch(item.getName()){
                    case "모양전체" : {
                        shape = "shape_all";
                        break;
                    }
                    case "원형" : {
                        shape = "";
                        break;
                    }
                    case "삼각형" : {
                        shape = "삼각형";
                        break;
                    }
                    case "사각형" : {
                        shape = "rectangle";
                        break;
                    }
                    case "마름모" : {
                        shape = "rhombus";
                        break;
                    }
                    case "장방향" : {
                        shape = "oblong";
                        break;
                    }
                    case "타원형" : {
                        shape = "oblong";
                        break;
                    }
                    case "반원형" : {
                        shape = "oblong";
                        break;
                    }
                    case "오각형" : {
                        shape = "pentagon";
                        break;
                    }
                    case "육각형" : {
                        shape = "oblong";
                        break;
                    }
                    case "팔각형" : {
                        shape = "octagon";
                        break;
                    }
                    case "기타" : {
                        shape = "shape_etc";
                        break;
                    }
                }*/
                //@@@@@@@@@@@@@@@@@@
                // 이 코드는 각 case문 아래에 반복됩니다.
                //mSelectedItems1은 리스트 shape의 각 요소가 선택되었는지(true) 안되었는지(false) 관리하는 SparseBooleanArray입니다.
                //다른 mSelectedItems2, 3, 4는 각각 컬러, 제형, 라인을 관리합니다.
                // 이 밑은 제가 아이템이 클릭된 경우 테두리를 변환하는 코드입니다.
                if (mSelectedItems1.get(position, false) ){ // 아이템이 선택된 경우
                    // 어떤 아이템이든 선택된 상태에서 다시 한번 선택(클릭)시 수행
                    mSelectedItems1.put(position, false); // 그 아이템을 선택 해제한다.
                }
                else { // 선택되지 않은 아이템을 클릭 시
                    mSelectedItems1.put(position, true);//일단 그 선택한 아이템은 선택 상태로 바꿔주기 -> true
                    if(item.getViewType()==1){ // 선택한 아이템이 0\전체인 경우
                        for (int i = 1; i < mSelectedItems1.size(); i++)
                            mSelectedItems1.put(i, false); // 나머지 아이템들은 false로 만든다. 즉, 선택 해제

                    }
                    else{// 선택한 아이템이 00 전체가 아닌 경우
                        mSelectedItems1.put(0,false); // 00 전체를 false로 만든다. 즉, 선택 해제
                    }


                }
                for(int i=0; i <mSelectedItems1.size(); i++){
                    if(i==0)shape1.clear();
                    item = list_row1.get(i);
                    if(mSelectedItems1.get(i)){
                        shape1.add(item.getText());
                    }
                }

                //@@@@@@@@@@@@@@@@@@@@@@@@@@

               /* if ( mSelectedItems1.get(position, false) ){
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
                }*/


                break;
            }
            case R.id.Lst_color : {
                item= list_row2.get(position);
                Toast.makeText(this,  item.getName(), Toast.LENGTH_SHORT).show();

                /*switch(item.getName()){
                    case "색상전체" : {
                        color = "color_all";
                        break;
                    }
                    case "하양" : {
                        color = "하양";
                        break;
                    }
                    case "노랑" : {
                        color = "yellow";
                        break;
                    }
                    case "주황" : {
                        color = "orange";
                        break;
                    }
                    case "분홍" : {
                        color = "pink";
                        break;
                    }
                    case "빨강" : {
                        color = "red";
                        break;
                    }
                    case "갈색" : {
                        color = "brown";
                        break;
                    }
                    case "보라" : {
                        color = "yellowgreen";
                        break;
                    }
                    case "청록" : {
                        color = "yellowgreen";
                        break;
                    }
                    case "파랑" : {
                        color = "yellowgreen";
                        break;
                    }
                    case "자주" : {
                        color = "yellowgreen";
                        break;
                    }
                    case "회색" : {
                        color = "yellowgreen";
                        break;
                    }
                    case "검정" : {
                        color = "yellowgreen";
                        break;
                    }


                }*/
                if (mSelectedItems2.get(position, false) ){
                    mSelectedItems2.put(position, false);
                }
                else {
                    mSelectedItems2.put(position, true);
                    if(item.getViewType()==1){
                        for (int i = 1; i < mSelectedItems2.size(); i++)
                            mSelectedItems2.put(i, false);

                    }
                    else{
                        mSelectedItems2.put(0,false);
                    }


                }
                for(int i=0; i <mSelectedItems2.size(); i++){
                    if(i==0)color1.clear();
                    item = list_row2.get(i);

                    if(mSelectedItems2.get(i)){
                        color1.add(item.getText());
                    }
                }
                /*if ( mSelectedItems2.get(position, false) ){
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
                }*/


                break;
            }
            case R.id.Lst_dosageForm : {
                item= list_row3.get(position) ;
                Toast.makeText(this,  item.getName(), Toast.LENGTH_SHORT).show();
                /*switch(item.getName()){
                    case "제형전체" : {
                        formula = "formula_all";
                        break;
                    }
                    case "정제류" : {
                        formula = "정제";
                        break;
                    }
                    case "경질캡슐" : {
                        formula = "hard_cap";
                        break;
                    }
                    case "연질캡슐" : {
                        formula = "soft_cap";
                        break;
                    }

                }*/
                if (mSelectedItems3.get(position, false) ){
                    mSelectedItems3.put(position, false);
                }
                else {
                    mSelectedItems3.put(position, true);
                    if(item.getViewType()==1){
                        for (int i = 1; i < mSelectedItems3.size(); i++)
                            mSelectedItems3.put(i, false);

                    }
                    else{
                        mSelectedItems3.put(0,false);
                    }


                }

                for(int i=0; i <mSelectedItems3.size(); i++){
                    if(i==0)formula1.clear();

                    item = list_row3.get(i);

                    if(mSelectedItems3.get(i)){
                        formula1.add(item.getText());
                    }
                }
                /*if ( mSelectedItems3.get(position, false) ){
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
                }*/


                break;
            }
            case R.id.Lst_line : {
                item= list_row4.get(position) ;
                Toast.makeText(this,  item.getName(), Toast.LENGTH_SHORT).show();
                /*switch(item.getName()){
                    case "분할설전체" : {
                        line = "line_all";
                        break;
                    }
                    case "없음" : {
                        line = "empty";
                        break;
                    }
                    case "(-)형" : {
                        line = "-";
                        break;
                    }
                    case "(+)형" : {
                        line = "line_plus";
                        break;
                    }
                    case "기타" : {
                        line = "line_etc";
                        break;
                    }

                }*/
                if (mSelectedItems4.get(position, false) ){
                    mSelectedItems4.put(position, false);
                }
                else {
                    mSelectedItems4.put(position, true);
                    if(item.getViewType()==1){
                        for (int i = 1; i < mSelectedItems4.size(); i++)
                            mSelectedItems4.put(i, false);

                    }
                    else{
                        mSelectedItems4.put(0,false);
                    }


                }
                for(int i=0; i <mSelectedItems4.size(); i++){
                    if(i==0)line1.clear();
                    item = list_row4.get(i);
                    if(mSelectedItems4.get(i)){
                        line1.add(item.getText());
                    }
                }
                /*if ( mSelectedItems4.get(position, false) ){
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
                }*/


                break;
            }
        }


    }



}