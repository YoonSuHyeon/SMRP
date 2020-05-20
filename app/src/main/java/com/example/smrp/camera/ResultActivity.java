package com.example.smrp.camera;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.pdf.PdfDocument;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.smrp.R;
import com.example.smrp.RetrofitHelper;
import com.example.smrp.RetrofitService;
import com.example.smrp.medicine.MedicineDetailActivity;

import java.io.File;
import java.util.ArrayList;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ResultActivity extends Activity {

    Button btn_ok;
    ArrayList<String> list = new ArrayList<>();
    ImageView img;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        btn_ok=findViewById(R.id.btn_ok);
        img = findViewById(R.id.img);
        getImage("med"); // 캐시 이미지 파일 불러오기 호출

        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                sendFile(new File(getCacheDir().toString()));
                Intent intent = new Intent(getApplicationContext(), MedicineDetailActivity.class);
                //Intent intent = new Intent(getContext().getApplicationContext(), MedicineDetailActivity.class);
                startActivity(intent);

            }
        });
    }
    private void sendFile(File tempFile){//서버에게 이미지 전송

            RequestBody body = RequestBody.create(MediaType.parse("image/*"),tempFile);
            MultipartBody.Part mPart = MultipartBody.Part.createFormData("files","med.jpg",body);

            RetrofitService retrofitCameraService = RetrofitHelper.getRetrofit().create(RetrofitService.class);
            Call<String> call  = retrofitCameraService.uploadImage(mPart);
            call.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    Log.d("1234","sdgsdg");
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    Log.d("ddd",t.toString());
                }
            });

            //서버에게 약 데이터를 받은후 MedicinDetailActivity 로 이동
    }


    private void getImage(String name){ // 캐시에서 이미지 불러오기

        File file = new File(getCacheDir().toString()); //캐시
        File[] files = file.listFiles();


        Log.w("LENGTH", files.length+" ");
        Log.w("CACHE", getCacheDir().length()+" ");

        for(File tempFile : files) {
            if(tempFile.getName().contains(name)) {
                list.add(tempFile.getName());
                if(tempFile.getName().contains(name)) {
                    list.add(tempFile.getName());

                }
            }

        }
        Log.e("LIST SIZE", list.size()+" ");
        int size=0;
        if(list.size()>0) // 리스트에 있는 거 가져오기 위해 list position 조절하는 부분
            size = list.size()-1;

        String path = getCacheDir() + "/" + list.get(size);

        Log.e("PATH", path);


        //비트맵을 생성
        Bitmap bitmap = BitmapFactory.decodeFile(path);
        img.setImageBitmap(bitmap);

        //캐시 삭제는 뒤로가기 누를 때마다 삭제를 하고 있음(바뀔 수 있음. 내용도)

    }

    @Override
    public void onBackPressed() {


        // 캐시 파일 삭제한다
        File cache = getCacheDir();
        File appDir = new File(cache.getParent());
        if (appDir.exists()) {
            String[] children = appDir.list();
            for (String s : children) {

                if(s.equals("lib") || s.equals("files")) continue;
                deleteDir(new File(appDir, s));
                Log.d("test", "File /data/data/"+getPackageName()+"/" + s + " DELETED");
            }
        }

        // 캐시 파일 삭제


        Intent intent = new Intent(ResultActivity.this, CameraActivity.class);
        startActivity(intent);
        finish();
    }



    private static boolean deleteDir(File dir) { // 캐시파일 삭제. 나중에 조금 수정할 수 있음
        if (dir != null && dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }
        return dir.delete();
    }

}


