package com.example.isafe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.example.isafe.dashboard.AppDashboard;

public class login extends AppCompatActivity {

    private Button loginBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginBtn = findViewById(R.id.loginBtn);

        loginBtn.setOnClickListener(v -> {

            startActivity(new Intent(getApplicationContext(), AppDashboard.class));

        });
    }
}