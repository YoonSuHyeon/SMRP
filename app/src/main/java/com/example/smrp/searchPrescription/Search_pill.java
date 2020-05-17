package com.example.smrp.searchPrescription;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.smrp.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.wonderkiln.camerakit.CameraKit;
import com.wonderkiln.camerakit.CameraKitError;
import com.wonderkiln.camerakit.CameraKitEvent;
import com.wonderkiln.camerakit.CameraKitEventListener;
import com.wonderkiln.camerakit.CameraKitImage;
import com.wonderkiln.camerakit.CameraKitVideo;
import com.wonderkiln.camerakit.CameraView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class Search_pill extends AppCompatActivity {
private CameraView cameraView;
private Bitmap bitmap;
private FloatingActionButton fb;
private FileOutputStream out = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_pill);

        fb = findViewById(R.id.take_button);
        cameraView = findViewById(R.id.cameraView);
        fb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cameraView.captureImage();
            }
        });
        cameraView.setFocus(CameraKit.Constants.FOCUS_CONTINUOUS);
        cameraView.addCameraKitListener(new CameraKitEventListener() {
            @Override
            public void onEvent(CameraKitEvent cameraKitEvent) {

            }

            @Override
            public void onError(CameraKitError cameraKitError) {

            }

            @Override
            public void onImage(CameraKitImage cameraKitImage) {
                bitmap = cameraKitImage.getBitmap();
                Log.d("TAG", "onImage: ");
                Download(bitmap);
            }

            @Override
            public void onVideo(CameraKitVideo cameraKitVideo) {

            }
        });
    }
    private void Download(Bitmap bitmap){
        String filename="me.jpg";
        File storage = Environment.getDataDirectory().getAbsoluteFile();
        Log.d("storage", "storage: ");
        File file = new File(String.valueOf(storage));


        try {
            if(!file.isDirectory())
                 file.createNewFile();
            //File file1 = new File(storage+filename);
           // file1.createNewFile();
            out = new FileOutputStream(file+filename);
            bitmap.compress(Bitmap.CompressFormat.JPEG,100,out);
            Log.d("TAG", "Download: ");
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

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
}
