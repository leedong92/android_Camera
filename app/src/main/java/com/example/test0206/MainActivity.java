package com.example.test0206;

import androidx.appcompat.app.AppCompatActivity;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.ImageAnalysis;
import androidx.camera.core.ImageCapture;
import androidx.camera.core.Preview;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.camera.view.PreviewView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.os.Bundle;

import com.google.common.util.concurrent.ListenableFuture;

public class MainActivity extends AppCompatActivity {
    private final String[] REQUIRED_PERMISSIONS = new String[] {"android.permission","android.permission.WRITE_EXTERNAL_STORAGE"};
    PreviewView previewView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActivityCompat.requestPermissions(this, REQUIRED_PERMISSIONS, MODE_PRIVATE);


        startCamera();
    }
    private void startCamera(){
        final ListenableFuture<ProcessCameraProvider> listenableFuture = ProcessCameraProvider.getInstance(this);

        listenableFuture.addListener(new Runnable() {
            @Override
            public void run() {
                try{
                    ProcessCameraProvider processCameraProvider = listenableFuture.get();
                    bindPievew(processCameraProvider);
                }catch(Exception exception){

                }
            }
        }, ContextCompat.getMainExecutor(this));
    }
    private void bindPievew(ProcessCameraProvider processCameraProvider){
        Preview preview = new Preview.Builder().build();
        CameraSelector cameraSelector = new CameraSelector.Builder().requireLensFacing(CameraSelector.LENS_FACING_FRONT).build();
        ImageAnalysis imageAnalysis = new ImageAnalysis.Builder().build();
        ImageCapture.Builder builder = new ImageCapture.Builder();
        final ImageCapture imageCapture = builder.setTargetRotation(this.getWindowManager().getDefaultDisplay().getRotation()).build();
        preview.setSurfaceProvider(previewView.getSurfaceProvider());
        processCameraProvider.bindToLifecycle(this, cameraSelector,preview,imageAnalysis,imageCapture);
    }

}