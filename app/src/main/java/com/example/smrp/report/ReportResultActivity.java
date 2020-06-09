package com.example.smrp.report;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smrp.R;

import java.util.ArrayList;

class DiseaseArray{
   public ArrayList<Integer> symptomAndDisease = new ArrayList<>();

    public void setArray(int a[]){
        for(int i = 0; i < a.length; i++)
            symptomAndDisease.add(--a[i]);

    }
}
public class ReportResultActivity extends AppCompatActivity implements DiseaseRecyclerAdapter.OnItemClickListener {//implements ReportFragment.OnSymptomListener {

    TextView Txt_name;
    ArrayList<DiseaseList> list = new ArrayList<DiseaseList>();
    double symptom_count=0.0;
    DiseaseRecyclerAdapter adapter;
    RecyclerView Lst_disease;
    ImageView iv_back;
    int sym0[] = {22, 7, 34, 32, 31, 13, 24};int sym7[] = {20, 7, 49, 28, 45};int sym8[] = { 20, 29, 39, 51, 44, 10, 3, 12, 7, 49};int sym9[] = {23, 38, 8, 9, 26, 42};
    int sym1[] = {24};int sym10[] = {31, 12, 28};int sym11[] = {30, 36, 16};
    int sym2[] = {0};int sym12[] = {4, 37, 39,27, 51};int sym13[] = {15, 43, 19, 40, 27, 24};
    int sym3[] = {8,9};int sym14[] = {44, 20, 12, 31, 47,11,3};int sym15[] = {37, 3, 15};
    int sym4[] = { 12};int sym16[] = {34};int sym17[] = {44, 37, 33, 51, 18, 7, 49, 45, 22, 32, 13};
    int sym5[] = {37, 33, 20, 38, 44, 7, 28, 49, 3, 45};int sym18[] = {10, 46, 1, 41};int sym19[] = {44, 37, 18, 2, 10, 46};
    int sym6[] = {37, 44, 39, 51, 43, 12, 15, 16, 48, 41, 40, 19, 14};int sym20[] = {7, 15, 19};
    int sym21[] = {35};int sym22[] = {5, 4};int sym23[] = {21};int sym24[] = { 17, 5, 4};int sym25[] = {37, 10, 31};
    int sym26[] = {18, 10, 31, 40, 41};int sym27[] = {37, 18, 10, 40, 41};int sym28[] = {25, 29, 51, 44, 3, 49, 19};int sym29[] = {22, 32};int sym30[] = {17, 22};
    int sym31[] = {4, 20, 5, 51,19, 39, 33, 37, 27, 40, 15, 26};int sym32[] = {41};int sym33[] = {6};int sym34[] = {35, 17,29, 43, 25, 50, 37, 4, 5, 18, 6};
       private ArrayList<Integer> mSelectedItems = new ArrayList<Integer>();
  //  private int[][] symptomAndDisease = new int[4][]
    //private ArrayList<Integer> symptomAndDisease = new ArrayList<Integer>();
    int array[][] = {sym0,sym1,sym2,sym3,sym4,sym5,sym6,sym7,sym8,sym9,sym10,sym11,sym12,sym13,sym14,sym15,sym16,sym17,sym18,sym19,sym20
          ,sym21,sym22,sym23,sym24,sym25,sym26,sym27,sym28,sym29,sym30,sym31,sym32,sym33,sym34};
    DiseaseArray[] dis = new DiseaseArray[35];
    private int[] disease = new int[51];
    private String[] Str_diseaseName = {"간혈관종", "간흡충증","감기","갑상선 기능 저하증","갑상선염",
            "건선","결핵","고소공포증","공포증", "급성 대장염", "급성 인두염",  "급성 편도염",   "기흉", "긴장성 두통", "냉방병", "노안", "당뇨병",
            "두드러기", "만성 피로 증후군", "만성 후두염", "복압성 요실금", "부정맥", "분리불안장애", "빈맥", "사면발이", "산후 우울증", "소아청소년 빈혈",
            "쇼그렌 증후군", "수두", "시신경염", "식도염", "심근경색증", "심부전", "역류성 식도염", "요도염" , "원시", "음식물 알레르기", "의존성 인격장애", "인후염", "일사병", "저혈당증", "적응장애", "적혈구 과다증", "조류인플루엔자", "천식", "크론병", "편도 비대", "편두통", "폐렴", "항문소양증", "혈관병"
    };
  //  Context context = this;

    private String disease_depart[];
            //getResources().getStringArray(R.array.disease_depart);
    private String disease_contents[];


    //부정맥, 결핵, 역류성 식도염, 삼근경색증, 식도염, 기흉, 빈맥, 공포증, 고소공포증, 급성 편도염, 음식물 알레르기, 심부전, 만성 후두염, 인후염, 조류인플루엔자
    // 결핵, 쇼그렌 증후군, 폐렴, 감기, 천식, 혈관염, 적혈구 과다증, 냉방병, 노안, 편두통, 일사병, 저혈당증, 만성 피로 증후군, 긴장성 두통
    // 가슴통증
    // 부정맥, 결핵, 역류성 식도염, 삼근경색증, 식도염, 기흉, 빈맥

    //심계항징   //요통
    // 빈맥    //0

    //공포
    //공포증, 고소공포증

//구취
//급성 편도염2

//기침
//음식물 알레르기, 심부전, 만성 후두염, 인후염, 조류인플루엔자, 결핵, 쇼그렌 증후군, 폐렴, 감기, 천식
//
//두통
//음식물 알레르기 조류인플루엔자 인후염 혈관염 1 적혈구 과다증1 급성 편도염 냉방병 1 노안1 편두통  1 저혈당증 1 일사병 1 만성 피로 증후군 1 긴장성 두통 1
   // private SparseBooleanArray mSelectedItems = new SparseBooleanArray(0);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reprot_result);
        Txt_name = findViewById(R.id.Txt_name);
        Lst_disease = findViewById(R.id.Lst_disease);
        iv_back = findViewById(R.id.iv_back);
        disease_depart =getResources().getStringArray(R.array.disease_depart);
        //getResources().getStringArray(R.array.disease_depart);
        disease_contents = getResources().getStringArray(R.array.disease_contents);


        for(int i = 0; i < dis.length; i++) {
            dis[i] = new DiseaseArray();
            dis[i].setArray(array[i]);

        }

        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });




        Intent intent = getIntent();
        mSelectedItems = intent.getIntegerArrayListExtra("selected");
        //array_symptom = new ArrayList<String>();
       // mSelectedItems = intent.getParcelableExtra("selected");
        //array_symptom= intent.getStringArrayListExtra("selected");
     //   list = new ArrayList<ListItem>();
        //Txt_name.setText(list.get(i).getSymptom());


        for(int i = 0; i < mSelectedItems.size(); i++){
          if(mSelectedItems.get(i)==1) { // 특정 증상이 선택되었다면,
              //이때 mSelectedITems는 intent를 통해 전달받은 데이터이다.
              {
                  symptom_count++; // 선택된 증상 개수 카운트
                  for(int j=0; j < dis[i].symptomAndDisease.size(); j++){
                      if(dis[i].symptomAndDisease.get(j)==-1) break;
                      disease[dis[i].symptomAndDisease.get(j)]++; // 각증상에 해당하는 병에 +1
                  }
              }
          }
        }


        for(int i=0; i<disease.length; i++){
            if(disease[i] != 0){ // 병에 대응하는 배열 요소 값이 0이 아닌 경우(증상에 해당하는 병인 경우)

                // RecyclerView에 나타나는 아이템 추가하기
                // 여기서 disease[i]/symptom_count)*100.0는 확률을 구하는 값
                addItem(list,Str_diseaseName[i],disease_contents[i],(disease[i]/symptom_count)*100.0,disease_depart[i]);
            }
        }
        adapter = new DiseaseRecyclerAdapter(list, this,Lst_disease);
        Lst_disease.setAdapter(adapter);





    }
    public void addItem(ArrayList<DiseaseList> list ,String dis, String sym, double prob, String depart) {
        DiseaseList item = new DiseaseList();
        item.setDisease(dis);
        item.setSymptom(sym);
        item.setProbability(prob+"");
        item.setDepartment(depart);
        list.add(item);
    }

    @Override
    public void onItemClick(View v, int position, RecyclerView rList) {
        String sym;
        String disease;
        String depart;

        sym = list.get(position).getSymptom(); // 클릭한 질병의 증상 정보
        disease =list.get(position).getDisease(); // 클릭한 질병의 이름
        depart = list.get(position).getDepartment(); // 클릭한 질병의 진료 과

        // 각 정보들을 상세정보 페이지에 전달해준다.
        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra("selected_sym",sym);
        intent.putExtra("selected_dis",disease);
        intent.putExtra("selected_depart",depart);
        startActivity(intent);

        //TextView Txt_sym=  v.findViewById(R.id.Txt_symptom);
        /*int i;
        for(i = 0; i < Str_diseaseName.length; i++)
            if(sym.equals(Str_diseaseName[i])) break;
        sym = disease_contents[i];
        disease =Str_diseaseName[i];
        depart = disease_depart[i];*/
        ///if()

    }
/*    @Override
    public void onSymptomSelected(SparseBooleanArray s, ArrayList<ListItem> l){

        for(int i =0; i<s.size(); i++){
            if(s.get(i))
                Txt_name.setText(l.get(i).getSymptom());


        }


    }*/
}
