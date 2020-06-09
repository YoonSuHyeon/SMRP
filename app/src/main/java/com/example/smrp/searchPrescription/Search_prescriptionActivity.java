package com.example.smrp.searchPrescription;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smrp.Pillname;
import com.example.smrp.R;
import com.example.smrp.RetrofitHelper;
import com.example.smrp.RetrofitService;
import com.example.smrp.reponse_medicine;
import com.example.smrp.response;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.vision.v1.Vision;
import com.google.api.services.vision.v1.VisionRequest;
import com.google.api.services.vision.v1.VisionRequestInitializer;
import com.google.api.services.vision.v1.model.AnnotateImageRequest;
import com.google.api.services.vision.v1.model.BatchAnnotateImagesRequest;
import com.google.api.services.vision.v1.model.BatchAnnotateImagesResponse;
import com.google.api.services.vision.v1.model.EntityAnnotation;
import com.google.api.services.vision.v1.model.Feature;
import com.google.api.services.vision.v1.model.Image;


import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.lang.ref.WeakReference;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.StringTokenizer;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Search_prescriptionActivity extends AppCompatActivity implements Serializable {
    private static final String CLOUD_VISION_API_KEY = "AIzaSyDZfaBD1mddJVfGxgrhnUh0Lg02Mfc38KA";//구글 인증키
    private FloatingActionButton fb;
    private Bitmap bitmap;
    private static final String ANDROID_CERT_HEADER = "X-Android-Cert";
    private static final String ANDROID_PACKAGE_HEADER = "X-Android-Package";
    private static final int MAX_LABEL_RESULTS = 10;
    private static final int MAX_DIMENSION = 1080;//1080
    private Dialog dialog;
    private boolean bool_end = false;
    private RetrofitService_takenpicture json;
    private RetrofitService retrofitService;


    private ArrayList<Prescriptionitem> list;
    private ArrayList<String> itemseq_list;
    private Button add_Btn;
    private PrescriptionAdapter adapter;
    private HashMap<Integer,String> select_pill_list; //사용자 선택한 약 정보를 담는 hashmap
    private String id ="cc";
    private ImageView back_imgView;
    private Uri photoUri;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_pill);//activity_search_prescription
        add_Btn = findViewById(R.id.add_btn);
        back_imgView = findViewById(R.id.back_btn);
        select_pill_list = new HashMap<Integer, String>();
        itemseq_list = new ArrayList<String>();
        list = new ArrayList<Prescriptionitem>();
        adapter = new PrescriptionAdapter(list);
        recyclerView = findViewById(R.id.recycler_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setAdapter(adapter);


        add_Btn.setOnClickListener(new View.OnClickListener() { //추가하기 버튼 누를시
            @Override
            public void onClick(View v) {
                if(select_pill_list.size()==0){
                    Toast.makeText(getApplicationContext(),"약을 선택해 주세요.",Toast.LENGTH_SHORT).show();
                }else{
                    for(Map.Entry<Integer,String>elem : select_pill_list.entrySet())
                        itemseq_list.add(elem.getValue());
                    //Log.d("TAG", "==>: "+elem.getKey()+","+elem.getValue()+"\n");
                    RetrofitService networkService= RetrofitHelper.getRetrofit().create(RetrofitService.class);
                    User_Select user_select = new User_Select(id,itemseq_list);
                    Call<response> call = networkService.addSelectMedicine(user_select);
                    call.enqueue(new Callback<response>() {
                        @Override
                        public void onResponse(Call<response> call, Response<response> response) {
                            Log.d("TAG", "add_Btn_Success: ");
                            onBackPressed();
                        }

                        @Override
                        public void onFailure(Call<response> call, Throwable t) {
                            Log.d("TAG", "add_Btn_Fail: ");
                            onBackPressed();
                        }
                    });
                }


            }
        });
        back_imgView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        adapter.setOnClickListener(new PrescriptionAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(PrescriptionAdapter.ViewHolder holder, View v, int position) {
                Log.d("TAG", "onItemClick: "+list.get(position).getText1());

                if(select_pill_list.size()==0){//초기
                    select_pill_list.put(position,list.get(position).getItemSeq());
                    Log.d("TAG", "select_pill_list.size()==0: "+list.get(position).getItemSeq()+"."+list.get(position).getText1());
                }else{
                    if(select_pill_list.get(position)==null){ //선택한 약이 hashmap에 없을경우
                        select_pill_list.put(position,list.get(position).getItemSeq());
                    }else{ //선택한 약이 hashmap에 있을경우 삭제 : 사용자가 2번 누른것
                        select_pill_list.remove(position);
                    }
                }

            }


        });





        sendTakePhotoIntent();



    }


    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 672 && resultCode == RESULT_OK) {

            try {
                //imageView.setImageURI(photoUri);
                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), photoUri);
            } catch (IOException e) {
                e.printStackTrace();
            }

            Uploading_bitmap(bitmap);
        }
    }
    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "TEST_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,      /* prefix */
                ".jpg",         /* suffix */
                storageDir          /* directory */
        );
        //imageFilePath = image.getAbsolutePath();
        return image;
    }
    private void sendTakePhotoIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/Android/data/com.example.smrp/files/Pictures");//Android/data/com.raonstudio.cameratest/files
            Log.d("TAG", "sendTakePhotoIntent: "+Environment.getExternalStorageDirectory().getAbsolutePath());
            if(!file.exists()){
                Log.d("TAG", "file not exists(): ");
                file.mkdir();

            }
            Log.d("TAG", "file.exists()file.exists(): "+file.exists());
            File photoFile = null;

            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
            }

            if (photoFile != null) {
                photoUri = FileProvider.getUriForFile(this, getPackageName(), photoFile);

                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
                startActivityForResult(takePictureIntent, 672);
            }
        }
    }



    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (Build.VERSION.SDK_INT >= 23) {
            if(grantResults[0]== PackageManager.PERMISSION_GRANTED){
                Log.v("TAG","Permission: "+permissions[0]+ "was "+grantResults[0]);
                //resume tasks needing this permission
            }
        }
    }


    private void Uploading_bitmap(Bitmap bitmap){
        if(bitmap != null){

            Log.d("TAG", "bitmap width: "+bitmap.getWidth());
            Log.d("TAG", "bitmap height: "+bitmap.getHeight());
            bitmap = scaleBitmapDown(bitmap,MAX_DIMENSION);

            //cameraView.setVisibility(View.GONE);
            //imageView.setImageBitmap(bitmap);
            callCloudVision(bitmap);
        }
        else{
            Log.d("TAG", "Uploading_bitmap fail ");
        }

    }

    private Bitmap scaleBitmapDown(Bitmap bitmap, int maxDimension) {

        int originalWidth = bitmap.getWidth();
        int originalHeight = bitmap.getHeight();
        int resizedWidth = maxDimension;
        int resizedHeight = maxDimension;
        if (originalHeight > originalWidth) { // 촬영한 사진의 세로길이가 너비보다 크면
            resizedHeight = maxDimension;
            resizedWidth = (int) (resizedHeight * (float) originalWidth / (float) originalHeight);
        } else if (originalWidth > originalHeight) {
            resizedWidth = maxDimension;
            resizedHeight = (int) (resizedWidth * (float) originalHeight / (float) originalWidth);
        } else if (originalHeight == originalWidth) {
            resizedHeight = maxDimension;
            resizedWidth = maxDimension;
        }
        return Bitmap.createScaledBitmap(bitmap, resizedWidth, resizedHeight, false);
    }

    private class LableDetectionTask extends AsyncTask<Object, Void, String> {
        private final WeakReference<Search_prescriptionActivity> mActivityWeakReference;
        private Vision.Images.Annotate mRequest;
        ProgressDialog progressDialog = new ProgressDialog(Search_prescriptionActivity.this);
        LableDetectionTask(Search_prescriptionActivity activity, Vision.Images.Annotate annotate) {
            mActivityWeakReference = new WeakReference<>(activity);
            mRequest = annotate;
        }

        @Override
        protected String doInBackground(Object... params) {
            try {

                BatchAnnotateImagesResponse response = mRequest.execute();
                return convertResponseToString(response);

            } catch (GoogleJsonResponseException e) {
                Log.d("TAG", "failed to make API request because " + e.getContent());
            } catch (IOException e) {
                Log.d("TAG", "failed to make API request because of other IOException " +
                        e.getMessage());
            }
            //return "Cloud Vision API request failed. Check logs for details.";

            return "Cloud Vision API request failed. Check logs for details.";
        }
        @Override
        protected void onPreExecute() {
            /*ViewGroup group = (ViewGroup) root.getParent();
            if(group!=null)
                group.removeView(root);*/
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.setMessage("로딩중입니다..");

            // show dialog
            progressDialog.show();
            super.onPreExecute();
        }
        protected void onPostExecute(String result) { // 객체에서 문자 추출한 결과: result
            Search_prescriptionActivity activity = mActivityWeakReference.get();
            StringBuilder st_result = new StringBuilder();
            if (activity != null && !activity.isFinishing()) {
                // TextView imageDetail = activity.findViewById(R.id.textViewResult);
                //imageDetail.setText(result);

                //st_result.append(result);
                ArrayList<String> pill_list = new ArrayList();


                StringTokenizer token = new StringTokenizer(result , "\n");
                while(token.hasMoreTokens()){
                    pill_list.add(token.nextToken());
                }
                pill_list.remove(pill_list.size() -1); //약 리스트의 마지막 리스트 삭제
                Log.d("TAG", "onPostExecute: "+result);
                Log.d("TAG", "size: "+ pill_list.size());


                for(int i = 0 ; i<pill_list.size();i++){
                    Log.d("TAG", "pil_name: ["+i+"]="+pill_list.get(i));
                }
                Pillname pillname = new Pillname(pill_list);
                retrofitService = RetrofitHelper.getRetrofit().create(RetrofitService.class); //아래부터는 약품명을 서버에 요청하기 위한 코드
                Call<ArrayList<reponse_medicine>> call = retrofitService.getPill(pillname);
                call.enqueue(new Callback<ArrayList<reponse_medicine>>() {
                    @Override
                    public void onResponse(Call<ArrayList<reponse_medicine>> call, Response<ArrayList<reponse_medicine>> response) {//접속에 성공하였을때
                        progressDialog.dismiss();
                        Log.d("TAG", "onResponseonResponseonResponseonResponse: ");
                        ArrayList<reponse_medicine>list1 = response.body();

                        if(list1.size()==0){
                            Toast.makeText(getApplicationContext(),"인식 문자 없음.",Toast.LENGTH_SHORT).show();
                            onBackPressed();
                            finish();
                        }else{
                            for(int i =0 ; i< list1.size();i++){
                                list.add(new Prescriptionitem(list1.get(i).getItemSeq(),list1.get(i).getItemImage(),list1.get(i).getItemName(),list1.get(i).getEntpName(),
                                        list1.get(i).getFormCodeName(),list1.get(i).getEtcOtcName()));
                            }

                            LinearLayoutManager mlinearLayoutManager = new LinearLayoutManager(getApplicationContext());
                            DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), mlinearLayoutManager.getOrientation());//구분선을 넣기 위함
                            recyclerView.addItemDecoration(dividerItemDecoration);
                        }



                    }

                    @Override
                    public void onFailure(Call<ArrayList<reponse_medicine>> call, Throwable t) {// 접속실패했을때
                        bool_end = true;

                        onBackPressed();
                        finish();

                    }
                });

                bool_end = true;

            }
        }
    }
    private Vision.Images.Annotate prepareAnnotationRequest(final Bitmap bitmap) throws IOException {
        HttpTransport httpTransport = AndroidHttp.newCompatibleTransport();
        JsonFactory jsonFactory = GsonFactory.getDefaultInstance();

        VisionRequestInitializer requestInitializer =
                new VisionRequestInitializer(CLOUD_VISION_API_KEY) {
                    /**
                     * We override this so we can inject important identifying fields into the HTTP
                     * headers. This enables use of a restricted cloud platform API key.
                     */
                    @Override
                    protected void initializeVisionRequest(VisionRequest<?> visionRequest)
                            throws IOException {
                        super.initializeVisionRequest(visionRequest);

                        String packageName = getPackageName();
                        visionRequest.getRequestHeaders().set(ANDROID_PACKAGE_HEADER, packageName);

                        String sig = PackageManagerUtils.getSignature(getPackageManager(), packageName);

                        visionRequest.getRequestHeaders().set(ANDROID_CERT_HEADER, sig);
                    }
                };

        Vision.Builder builder = new Vision.Builder(httpTransport, jsonFactory, null);
        builder.setVisionRequestInitializer(requestInitializer);

        Vision vision = builder.build();

        BatchAnnotateImagesRequest batchAnnotateImagesRequest =
                new BatchAnnotateImagesRequest();
        batchAnnotateImagesRequest.setRequests(new ArrayList<AnnotateImageRequest>() {{
            AnnotateImageRequest annotateImageRequest = new AnnotateImageRequest();

            // Add the image
            Image base64EncodedImage = new Image();
            // Convert the bitmap to a JPEG
            // Just in case it's a format that Android understands but Cloud Vision
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 90, byteArrayOutputStream);
            byte[] imageBytes = byteArrayOutputStream.toByteArray();

            // Base64 encode the JPEG
            base64EncodedImage.encodeContent(imageBytes);
            annotateImageRequest.setImage(base64EncodedImage);

            // add the features we want
            annotateImageRequest.setFeatures(new ArrayList<Feature>() {{
                Feature labelDetection = new Feature();
                labelDetection.setType("TEXT_DETECTION");//LABEL_DETECTION:이미지 Text_DETECTION
                labelDetection.setMaxResults(MAX_LABEL_RESULTS);
                add(labelDetection);
            }});

            // Add the list of one thing to the request
            add(annotateImageRequest);
        }});

        Vision.Images.Annotate annotateRequest =
                vision.images().annotate(batchAnnotateImagesRequest);
        // Due to a bug: requests to Vision API containing large images fail when GZipped.
        annotateRequest.setDisableGZipContent(true);
        Log.d("TAG", "created Cloud Vision request object, sending request");

        return annotateRequest;
    }
    /*@Override
    public void onFinished() {
    }
    @Override
    public void onError(int code, String message) {
    }*/
    private void callCloudVision(final Bitmap bitmap) {
        Log.d("TAG", "callCloudVision: ");
        // Switch text to loading
        //mImageDetails.setText(R.string.loading_message);

        // Do the real work in an async task, because we need to use the network anyway
        try {
            AsyncTask<Object, Void, String> labelDetectionTask = new LableDetectionTask(this, prepareAnnotationRequest(bitmap));
            labelDetectionTask.execute();
        } catch (IOException e) {
            Log.d("TAG", "failed to make API request because of other IOException " +
                    e.getMessage());
        }
    }
    private String convertResponseToString(BatchAnnotateImagesResponse response) {
        StringBuilder message = new StringBuilder();

        List<EntityAnnotation> labels = response.getResponses().get(0).getTextAnnotations();//Texts에 들어있는 값들을 하나씩 꺼내 Text에 대입
        Log.d("TAG", "convertResponseToString: "+ response.getResponses().get(0).getFullTextAnnotation());
        if (labels != null) {
            for (EntityAnnotation label : labels) {

                message.append(String.format(Locale.KOREAN, "%s", label.getDescription()));//%.3f: , label.getScore()
                //message.append("\n");
            }
        } else {
            //message.append("nothing");
            message.append("nothing");
        }

        return message.toString();
    }

    private class Dialog extends AsyncTask<Void,Void,Void>{
        ProgressDialog progressDialog1 = new ProgressDialog(Search_prescriptionActivity.this);
        @Override
        protected void onPreExecute() {
            /*ViewGroup group = (ViewGroup) root.getParent();
            if(group!=null)
                group.removeView(root);*/
            progressDialog1.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog1.setMessage("로딩중입니다..");

            // show dialog
            progressDialog1.show();
            super.onPreExecute();
        }
        @Override
        protected Void doInBackground(Void... voids) {
            /*try {
                Thread.sleep(2500); // 2초 지속
            } catch (InterruptedException e) {
                e.printStackTrace();
            }*/
            while(!bool_end)
                ;
            bool_end = false;
            return null;
        }
        @Override
        protected void onPostExecute(Void result) {
            progressDialog1.dismiss();

            //finish();

            super.onPostExecute(result);
        }
        @Override
        protected void onCancelled() {
            // TODO 작업이 취소된후에 호출된다.
            super.onCancelled();
        }
    }

}