package com.example.isafe.greetUser;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MotionEvent;
import android.widget.Button;

import com.example.isafe.R;
import com.example.isafe.Signup;
import com.example.isafe.dashboard.AppDashboard;
import com.example.isafe.login;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity2 extends AppCompatActivity {

    private Button signUp, login;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            startActivity(new Intent(getApplicationContext(), AppDashboard.class));
            finish();
        }

        signUp = findViewById(R.id.button);
        login = findViewById(R.id.button2);

        signUp.setOnTouchListener((view, motionEvent) -> {

            switch (motionEvent.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    signUp.setScaleX(0.9f);
                    signUp.setScaleY(0.9f);
                    break;
                case MotionEvent.ACTION_UP:
                    signUp.setScaleX(1f);
                    signUp.setScaleY(1f);
                    startActivity(new Intent(getApplicationContext(), Signup.class));
                    break;
            }
            return true;
        });

        login.setOnTouchListener((view, motionEvent) -> {

            switch (motionEvent.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    login.setScaleX(0.9f);
                    login.setScaleY(0.9f);
                    break;
                case MotionEvent.ACTION_UP:
                    login.setScaleX(1f);
                    login.setScaleY(1f);
                    startActivity(new Intent(getApplicationContext(), login.class));
                    break;
            }
            return true;
        });
    }
}