package com.example.isafe.dashboard.videocall;

import androidx.appcompat.app.AppCompatActivity;

import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.VideoView;

import com.example.isafe.R;

public class VcMaleHindi extends AppCompatActivity {

    VideoView videoView;
    private LinearLayout.LayoutParams defaultVideoViewParams;
    private int defaultScreenOrientationMode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vc_male_hindi);

        videoView = findViewById(R.id.maleHindiVv);

        Uri uri = Uri.parse("android.resource://"+getPackageName()+"/"+R.raw.test);
        videoView.setVideoURI(uri);
        makeVideoFullScreen();
        videoView.start();
    }

    private void makeVideoFullScreen() {

        defaultScreenOrientationMode = getResources().getConfiguration().orientation;
        defaultVideoViewParams = (LinearLayout.LayoutParams) videoView.getLayoutParams();

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        videoView.postDelayed(() -> {
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT);

            videoView.setLayoutParams(params);
            videoView.layout(10, 10, 10, 10);
        }, 700);
    }
}