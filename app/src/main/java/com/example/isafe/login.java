package com.example.isafe;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.isafe.dashboard.AppDashboard;

import java.util.Hashtable;
import java.util.Map;

public class login extends AppCompatActivity {

    private Button loginBtn;
    private TextView etPhone, etPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginBtn = findViewById(R.id.loginBtn);
        etPhone = findViewById(R.id.etPhoneLogin);
        etPass = findViewById(R.id.etPasswordLLogin);

        loginBtn.setOnClickListener(v -> {

            Map<String, String> phoneAndPass = new Hashtable<>();

            phoneAndPass.put("9529664779", "qwertyui");

            if (etPhone.getText().toString().equals("9529664779") && etPass.getText().toString().equals("qwertyui")) {
                startActivity(new Intent(getApplicationContext(), AppDashboard.class));
            }
        });
    }
}