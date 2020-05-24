package com.example.smrp.searchPrescription;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.smrp.Pillname;
import com.example.smrp.R;
import com.example.smrp.RetrofitHelper;
import com.example.smrp.RetrofitService;
import com.example.smrp.reponse_medicine;
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
import com.wonderkiln.camerakit.CameraKit;
import com.wonderkiln.camerakit.CameraKitError;
import com.wonderkiln.camerakit.CameraKitEvent;
import com.wonderkiln.camerakit.CameraKitEventListener;
import com.wonderkiln.camerakit.CameraKitImage;
import com.wonderkiln.camerakit.CameraKitVideo;
import com.wonderkiln.camerakit.CameraView;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.StringTokenizer;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Search_prescription extends AppCompatActivity {
    private static final String CLOUD_VISION_API_KEY = "AIzaSyAh8cQhRUiXMB5bUmjqnyWcFDMlrhEHySk";//구글 인증키
    private FloatingActionButton fb;
    private CameraView cameraView;
    private Bitmap bitmap;
    private static final String ANDROID_CERT_HEADER = "X-Android-Cert";
    private static final String ANDROID_PACKAGE_HEADER = "X-Android-Package";
    private static final int MAX_LABEL_RESULTS = 10;
    private static final int MAX_DIMENSION = 1080;//1080
    private Dialog dialog;
    private boolean bool_end = false;
    private RetrofitService_takenpicture json;
    private RetrofitService retrofitService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_prescription);

        ArrayList<String>list = new ArrayList<>(); // pill_name 과 약봉투의 공통 ㅇ단어 각 ArrayList를 만들고 이를 Arrays.sort 오름차순으로 둔다 contains 를 하여 true가 되면
        // 두 ArrayList삭제
        dialog = new Dialog();
        fb = findViewById(R.id.take_button);
        cameraView = findViewById(R.id.cameraView);
        //button = findViewById(R.id.button);
        fb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                cameraView.captureImage();

            }
        });

       /* button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cameraView.captureImage();;
            }
        });*/

        cameraView.setFocus(CameraKit.Constants.FOCUS_CONTINUOUS);//자동 포커스
        cameraView.addCameraKitListener(new CameraKitEventListener() {
            @Override
            public void onEvent(CameraKitEvent cameraKitEvent) {

            }

            @Override
            public void onError(CameraKitError cameraKitError) {

            }

            @Override
            public void onImage(CameraKitImage cameraKitImage) { //
                Log.d("TAG", "onImageonImage: ");
                bitmap = cameraKitImage.getBitmap();
                cameraView.stop();
                Uploading_bitmap(bitmap);
                //Search_text\(bitmap);

                dialog.execute();
            }

            @Override
            public void onVideo(CameraKitVideo cameraKitVideo) {

            }
        });

    }
    @Override
    protected void onResume() {
        super.onResume();
        cameraView.start();
    }

    @Override
    protected void onPause() {
        cameraView.stop();
        super.onPause();
    }


    public byte[] bitmapToByteArray( Bitmap bitmap ) { // Bitmap을 binary 로 변환 하는 클래스
        ByteArrayOutputStream stream = new ByteArrayOutputStream() ;
        bitmap.compress( Bitmap.CompressFormat.JPEG, 100, stream) ;
        byte[] byteArray = stream.toByteArray() ;
        return byteArray ;
    }

    private String Search_text(Bitmap bitmap){

        if(json == null){
            json = new RetrofitFactory_takenpicture().create();
        }
        json.getList(12.0,12.5).enqueue(new Callback<Text_Response>() {
            @Override
            public void onResponse(Call<Text_Response> call, Response<Text_Response> response) {

            }

            @Override
            public void onFailure(Call<Text_Response> call, Throwable t) {

            }
        });
        return "";
    }
    private void Uploading_bitmap(Bitmap bitmap){
        if(bitmap != null){
            bitmap = scaleBitmapDown(bitmap,MAX_DIMENSION);
            Log.d("TAG", "bitmap width: "+bitmap.getWidth());
            Log.d("TAG", "bitmap height: "+bitmap.getHeight());
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
        private final WeakReference<Search_prescription> mActivityWeakReference;
        private Vision.Images.Annotate mRequest;

        LableDetectionTask(Search_prescription activity, Vision.Images.Annotate annotate) {
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

        protected void onPostExecute(String result) { // 객체에서 문자 추출한 결과: result
            Search_prescription activity = mActivityWeakReference.get();
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
                Call<List<reponse_medicine>> call = retrofitService.getPill(pillname);
                call.enqueue(new Callback<List<reponse_medicine>>() {
                    @Override
                    public void onResponse(Call<List<reponse_medicine>> call, Response<List<reponse_medicine>> response) {//접속에 성공하였을때

                    }

                    @Override
                    public void onFailure(Call<List<reponse_medicine>> call, Throwable t) {// 접속실패했을때

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

        List<EntityAnnotation> labels = response.getResponses().get(0).getTextAnnotations();
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
        ProgressDialog progressDialog = new ProgressDialog(Search_prescription.this);
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
        @Override
        protected Void doInBackground(Void... voids) {
            try {
                Thread.sleep(2500); // 2초 지속

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            /*while(!bool_end)
                ;*/
            bool_end = false;
            return null;
        }
        @Override
        protected void onPostExecute(Void result) {
            progressDialog.dismiss();

            //finish();

            super.onPostExecute(result);
        }
    }
}
