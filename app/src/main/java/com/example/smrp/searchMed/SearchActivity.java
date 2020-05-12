package com.example.smrp.searchMed;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Layout;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.MalformedJsonException;
import android.util.SparseBooleanArray;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.smrp.Medicines;
import com.example.smrp.R;
import com.example.smrp.RetrofitHelper;
import com.example.smrp.RetrofitService;
import com.example.smrp.User;
import com.example.smrp.reponse_medicine;
import com.example.smrp.response;

import org.w3c.dom.Text;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchActivity extends AppCompatActivity implements SearchRecyclerAdapter.OnItemClickListener{

    String color="color_all",shape="shape_all",formula="formula_all",line="line_all";
    //Medicines medicines;
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
    ImageView iv1;
    ScrollView scrollView,scrollView2;

    Bitmap bitmap,resized;
    String stringURL;
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


   /* public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight){
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) >= reqHeight
                    && (halfWidth / inSampleSize) >= reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }*/
    public void init(final List<reponse_medicine> list){
        TableLayout tableLayout = (TableLayout)findViewById(R.id.table);
        tableLayout.removeAllViews();
        TableRow tbrow0 = new TableRow(this);
        TextView t1 = new TextView(this);
        //t1.setLayoutParams(new TableRow.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT,2f));
        t1.setBackgroundColor(Color.BLUE);
        t1.setTextColor(Color.WHITE);
        t1.setText("식별/포장");
        t1.setGravity(Gravity.CENTER);
        tbrow0.addView(t1);
        TextView t2 = new TextView(this);
        t2.setLayoutParams(new TableRow.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT,1.5f));
        t2.setBackgroundColor(Color.BLUE);
        t2.setTextColor(Color.WHITE);
        t2.setText("제품명");
        t2.setGravity(Gravity.CENTER);
        tbrow0.addView(t2);
        TextView t3 = new TextView(this);
        t3.setLayoutParams(new TableRow.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT,1.5f));
        t3.setBackgroundColor(Color.BLUE);
        t3.setTextColor(Color.WHITE);
        t3.setText("회사명");
        t3.setGravity(Gravity.CENTER);
        tbrow0.addView(t3);
        TextView t4 = new TextView(this);
        t4.setLayoutParams(new TableRow.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT,0.7f));
        t4.setBackgroundColor(Color.BLUE);
        t4.setTextColor(Color.WHITE);
        t4.setText("제형");
        t4.setGravity(Gravity.CENTER);
        tbrow0.addView(t4);
        TextView t5 = new TextView(this);
        t5.setLayoutParams(new TableRow.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT,0.7f));
        t5.setBackgroundColor(Color.BLUE);
        t5.setTextColor(Color.WHITE);
        t5.setText("구분");
        t5.setGravity(Gravity.CENTER);
        tbrow0.addView(t5);
        tableLayout.addView(tbrow0);

        Log.d("1234","ggigigigigi");
        Log.d("1234",Integer.toString(list.size()));
        for(int i=0; i<list.size(); i++){

            Log.d("1234543534535","ggigigigigi");
            TableRow tbrow = new TableRow(this);
            iv1 = new ImageView(this);
            stringURL=list.get(i).getItemImage();
            Log.d("999999",list.get(i).getItemImage());
           // Thread mThread = new Thread(){
            //    @Override
             //   public void run() {
              //      Glide.with(getBaseContext()).load(stringURL).override(800,100).fitCenter().into(iv1);
               // }
            //};
            //mThread.start();
            //try{
             //   mThread.join();
            //}catch (InterruptedException e){
             //   e.printStackTrace();
            //}
            Glide.with(this).load(stringURL).override(800,100).fitCenter().into(iv1);
            Log.d("list1",Integer.toString(i));
           // Log.d("list1",list.get(i).getImageURL());
           /* try{
                Thread.sleep(100);
            }catch (InterruptedException e){
                e.printStackTrace();
            }*/

           // URL url = new URL(stringURL);
           /* Log.d("URL",stringURL);
            Thread mThread = new Thread(){
                @Override
                public void run() {
                    try{
                        URL url = new URL(stringURL);
                        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                        conn.setDoInput(true);
                        conn.connect();

                        InputStream is = conn.getInputStream();

                        BitmapFactory.Options options = new BitmapFactory.Options();
                        //options.inSampleSize=2;
                        //bitmap = BitmapFactory.decodeStream(is,null,options);
                        //resized = Bitmap.createScaledBitmap(bitmap,100,100,false);
                        options.inJustDecodeBounds = true;
                        BitmapFactory.decodeStream(is,null,options);
                        options.inSampleSize = calculateInSampleSize(options,380,60);
                        options.inJustDecodeBounds = false;
                        //conn.setDoInput(true);
                        //conn.connect();
                        URL url2 = new URL(stringURL);
                        HttpURLConnection conn2 = (HttpURLConnection) url.openConnection();
                        conn2.setDoInput(true);
                        conn2.connect();
                        is = conn2.getInputStream();

                        bitmap = BitmapFactory.decodeStream(is,null,options);
                        Log.d("size",Integer.toString(bitmap.getHeight()));
                        //resized = Bitmap.createScaledBitmap(bitmap,200 , 100,true);


                        //BitmapFactory.decodeStream(is,null,options);
                        //options.inSampleSize = calculateInSampleSize(options,100,100);
                       // options.inJustDecodeBounds = false;

                       // bitmap = BitmapFactory.decodeStream(is,null,options);
                    }catch(MalformedJsonException e){
                        e.printStackTrace();
                    }catch (IOException e){
                        e.printStackTrace();
                    }
                }
            };
            mThread.start();
            try{
                mThread.join();

                iv1.setImageBitmap(bitmap);
            }catch (InterruptedException e){
                e.printStackTrace();
            }*/
            //iv1.setLayoutParams(new TableRow.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT,1f));
            //iv1.setText(list.get(i).getImageURL());
            Log.d("999999",list.get(i).getItemImage());
            //tv1.setTextColor(Color.BLACK);
            //tv1.setGravity(Gravity.CENTER);
            tbrow.addView(iv1);
            TextView tv2 = new TextView(this);
            tv2.setLayoutParams(new TableRow.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT,1.5f));
            tv2.setText(list.get(i).getItemName());
            tv2.setTextColor(Color.BLACK);
            tv2.setGravity(Gravity.CENTER);
            tbrow.addView(tv2);
            TextView tv3 = new TextView(this);
            tv3.setLayoutParams(new TableRow.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT,1.5f));
            tv3.setText(list.get(i).getEntpName());
            tv3.setTextColor(Color.BLACK);
            tv3.setGravity(Gravity.CENTER);
            tbrow.addView(tv3);
            TextView tv4 = new TextView(this);
            tv4.setLayoutParams(new TableRow.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT,0.7f));
            tv4.setText(list.get(i).getFormCodeName());
            tv4.setTextColor(Color.BLACK);
            tv4.setGravity(Gravity.CENTER);
            tbrow.addView(tv4);
            TextView tv5 = new TextView(this);
            tv5.setLayoutParams(new TableRow.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT,0.7f));
            tv5.setText(list.get(i).getEtcOtcName());
            tv5.setTextColor(Color.BLACK);
            tv5.setGravity(Gravity.CENTER);
            tbrow.addView(tv5);
            tableLayout.addView(tbrow);
        }


    }

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
        scrollView = (ScrollView)findViewById(R.id.scrollView);
        scrollView2 = (ScrollView)findViewById(R.id.scrollView2);


        scrollView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_UP){
                    scrollView2.requestDisallowInterceptTouchEvent(false);
                }else{
                    scrollView2.requestDisallowInterceptTouchEvent(true);
                }
                return false;
            }
        });
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
                RetrofitService networkService= RetrofitHelper.getRetrofit().create(RetrofitService.class);
                //medicines = new Medicines(shape,color,formula,line);
                //Call<List<com.example.smrp.reponse_medicine>> call = networkService.findList(shape,color,formula,line);
                Log.d("???",shape+color+formula+line);
                Call<List<reponse_medicine>> call = networkService.findList(shape,color,formula,line);
                call.enqueue(new Callback<List<reponse_medicine>>() {
                    @Override
                    public void onResponse(Call<List<reponse_medicine>> call, Response<List<reponse_medicine>> response) {
                       // Log.d("12312313",response.body().toString());
                        List<reponse_medicine> list = response.body();
                       // int a = list.size();
                       // String b = list.get(0).getCompany();
                       // Log.d("1234",Integer.toString(a));
                      //  Log.d("1234",b);
                        TableLayout tableLayout = (TableLayout)findViewById(R.id.table);
                        Log.d("1234","ggigigigigi");
                        init(list);
                        //String test = list.get(0).getColor();
                        //Log.d("1234",test);
                        //if(response.body() != null){
                          //  Log.d("12312313",response.body().toString());
                        //}
                    }

                    @Override
                    public void onFailure(Call<List<reponse_medicine>> call, Throwable t) {
                        Log.d("12312312312312313",t.toString());
                    }
                });
                //Call<com.example.smrp.reponse_medicine> call = networkService.findLists();
                /*call.enqueue(new Callback<reponse_medicine>() {
                    @Override
                    public void onResponse(Call<reponse_medicine> call, Response<reponse_medicine> response) {
                        String url;
                        if(response.body() == null){
                            Log.d("1234","Serseresresr");
                        }else{
                            url=response.body().getName();
                            if(url==null){
                                url ="resresrsr";
                            }
                            Log.d("1235554",url);
                        }
                    }

                    @Override
                    public void onFailure(Call<reponse_medicine> call, Throwable t) {
                        Log.d("122323232334",t.toString());
                    }
                });*/
                /*call.enqueue(new Callback<response>() {
                    @Override
                    public void onResponse(Call<response> call, Response<response> response) {
                       if(response.body() == null){
                           Log.d("1234","Serseresresr");
                       }else{
                           Log.d("1235554","Serseresresr");
                       }

                    }

                    @Override
                    public void onFailure(Call<response> call, Throwable t) {

                    }
                });*/


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
                Toast.makeText(this,  item.getName(), Toast.LENGTH_SHORT).show();
                switch(item.getName()){
                    case "모양전체" : {
                        shape = "shape_all";
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
                    case "오각형" : {
                        shape = "pentagon";
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
                }

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

                switch(item.getName()){
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
                    case "연두" : {
                        color = "yellowgreen";
                        break;
                    }

                }
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
                switch(item.getName()){
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

                }
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
                switch(item.getName()){
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

                }
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