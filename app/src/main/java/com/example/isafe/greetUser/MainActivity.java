package com.example.isafe.greetUser;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.example.isafe.R;
import com.example.isafe.greetUser.MainActivity2;

public class MainActivity extends AppCompatActivity {

    private TextView bypnpTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //bypnpTv = findViewById(R.id.bypnpTv);

        //bypnpTv.setSelected(true);
        //runAnimation();

        new Handler().postDelayed(() -> {
            Intent intent = new Intent(getApplicationContext(), MainActivity2.class);
            startActivity(intent);
            finish();

        }, 1_100);
    }

    private void runAnimation() {
        //Animation a = AnimationUtils.loadAnimation(this, R.anim.tv_animation);
        //bypnpTv.startAnimation(a);

    }

}