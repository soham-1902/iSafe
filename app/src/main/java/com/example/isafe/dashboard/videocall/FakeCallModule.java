package com.example.isafe.dashboard.videocall;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Size;
import android.widget.ImageButton;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.camera.core.Camera;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.ImageAnalysis;
import androidx.camera.core.Preview;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.camera.view.PreviewView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.isafe.R;
import com.example.isafe.dashboard.SosModule;
import com.google.common.util.concurrent.ListenableFuture;

import java.util.concurrent.ExecutionException;

public class FakeCallModule extends AppCompatActivity {

    private VideoView videoView;

    PreviewView previewView;

    private int REQUEST_CODE_PERMISSIONS = 101;
    private final String[] REQUIRED_PERMISSIONS = new String[]{"android.permission.CAMERA"};

    private ImageButton callCut, sosFromVc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fake_call_module);

        callCut = findViewById(R.id.callCut);
        videoView = findViewById(R.id.videoView);
        sosFromVc = findViewById(R.id.sosFromVc);

        Uri video = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.vc_video);
        videoView.setVideoURI(video);
        videoView.setOnPreparedListener(mp -> {
            mp.setLooping(true);
            videoView.start();
        });

        callCut.setOnClickListener(v -> this.finish());

        sosFromVc.setOnClickListener(v -> {
            finish();
            startActivity(new Intent(getApplicationContext(), SosModule.class));
            this.finish();
        });

        previewView = findViewById(R.id.previewView);


        if(allPermissionsGranted()){
            startCamera();
        } else{
            ActivityCompat.requestPermissions(this, REQUIRED_PERMISSIONS, REQUEST_CODE_PERMISSIONS);
        }
    }
    private boolean allPermissionsGranted(){

        for(String permission : REQUIRED_PERMISSIONS){

            if(ContextCompat.checkSelfPermission(getApplicationContext(), permission) != PackageManager.PERMISSION_GRANTED){
                return false;
            }
        }
        return true;
    }

    public void startCamera(){
        ListenableFuture<ProcessCameraProvider> cameraProviderFuture = ProcessCameraProvider.getInstance(getApplicationContext());

        cameraProviderFuture.addListener(() -> {
            try {
                // Camera provider is now guaranteed to be available
                ProcessCameraProvider cameraProvider = cameraProviderFuture.get();
                // Set up the view finder use case to display camera preview
                Preview preview = new Preview.Builder().build();
                // Choose the camera by requiring a lens facing
                CameraSelector cameraSelector = new CameraSelector.Builder()

                        .requireLensFacing(CameraSelector.LENS_FACING_FRONT)
                        .build();
                //Images are processed by passing an executor in which the image analysis is run
                ImageAnalysis imageAnalysis =
                        new ImageAnalysis.Builder()
                                //set the resolution of the view
                                .setTargetResolution(new Size(1280, 720))
                                //the executor receives the last available frame from the camera at the time that the analyze() method is called
                                .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                                .build();

                // Connect the preview use case to the previewView
                preview.setSurfaceProvider(previewView.getSurfaceProvider());
                // Attach use cases to the camera with the same lifecycle owner
                Camera camera = cameraProvider.bindToLifecycle(
                        this,
                        cameraSelector,
                        preview,
                        imageAnalysis);

            } catch (InterruptedException | ExecutionException e) {
                // Currently no exceptions thrown. cameraProviderFuture.get() should
                // not block since the listener is being called, so no need to

                // handle InterruptedException.
            }
        }, ContextCompat.getMainExecutor(this));
    }

    @Override

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(allPermissionsGranted()){
            startCamera();
        } else{
            Toast.makeText(this, "Permissions not granted by the user.", Toast.LENGTH_SHORT).show();
            finish();
        }
    }
}

