package com.example.isafe.dashboard;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.isafe.R;

public class AlertSirenModule extends AppCompatActivity {

    private Button startBtn, stopBtn;
    private MediaPlayer mediaPlayer = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alert_siren_module);

        startBtn = findViewById(R.id.startBtn);
        stopBtn = findViewById(R.id.stopBtn);

        mediaPlayer = MediaPlayer.create(this, R.raw.police_siren_sound);
        mediaPlayer.setOnCompletionListener(mp -> mediaPlayer.start());

        mediaPlayer.start();

        startBtn.setOnClickListener(v -> {

                if (mediaPlayer.isPlaying()) {
                    return;
                }

                mediaPlayer = MediaPlayer.create(this, R.raw.police_siren_sound);
                mediaPlayer.setOnCompletionListener(mp -> mediaPlayer.start());
                mediaPlayer.start();
        });

        stopBtn.setOnClickListener(v -> {
            if(mediaPlayer != null) {
                mediaPlayer.stop();
            }
        });

    }

    @Override
    public void onPause() {
        super.onPause();
        mediaPlayer.stop();
    }
}