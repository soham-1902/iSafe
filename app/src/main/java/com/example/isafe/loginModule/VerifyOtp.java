package com.example.isafe.loginModule;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.isafe.R;
import com.example.isafe.Signup;
import com.example.isafe.dashboard.AppDashboard;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

public class VerifyOtp extends AppCompatActivity {

    private EditText otpEt;
    private Button  verifyOtp;
    private String bundleContactNumber, otpId, bundleName, bundleEmail, bundlePass;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_otp);

        otpEt = findViewById(R.id.otpEt);
        verifyOtp = findViewById(R.id.verifyOtp);
        mAuth = FirebaseAuth.getInstance();

        bundleContactNumber = getIntent().getStringExtra("ContactNum");
        bundleName = getIntent().getStringExtra("Name");
        bundleEmail = getIntent().getStringExtra("Email");
        bundlePass = getIntent().getStringExtra("Password");

        initiateOtp();

        verifyOtp.setOnClickListener(v -> {
            if(otpEt.getText().toString().isEmpty()) {
                Toast.makeText(getApplicationContext(), "Blank field cannot be processed", Toast.LENGTH_SHORT).show();
            } else if(otpEt.getText().length() != 6) {
                Toast.makeText(getApplicationContext(), "Invalid Otp", Toast.LENGTH_SHORT).show();
            } else {
                PhoneAuthCredential credential = PhoneAuthProvider.getCredential(otpId, otpEt.getText().toString());
                signInWithPhoneAuthCredential(credential);
            }
        });
    }

    private void initiateOtp() {
        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber(bundleContactNumber)       // Phone number to verify
                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(this)                 // Activity (for callback binding)
                        .setCallbacks(new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

                            @Override
                            public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                otpId = s;
                            }

                            @Override
                            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                                //.addOnCompleteListener(task -> Toast.makeText(VerifyOtp.this, "Data saved, welcome!", Toast.LENGTH_SHORT).show());
                                signInWithPhoneAuthCredential(phoneAuthCredential);
                            }

                            @Override
                            public void onVerificationFailed(@NonNull FirebaseException e) {
                                System.out.println(e.getLocalizedMessage());
                                Toast.makeText(getApplicationContext(), "Unable to sign up at the moment", Toast.LENGTH_SHORT).show();
                            }
                        })          // OnVerificationStateChangedCallbacks
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        HashMap<String, String> map = new HashMap<>();
                        map.put("Name", bundleName);
                        map.put("Contact Number", bundleContactNumber);
                        map.put("Email", bundleEmail);
                        map.put("Password", bundlePass);

                        System.out.println("============================================================================");
                        System.out.println(FirebaseDatabase.getInstance().getReference().child("Users").push().setValue(map).isSuccessful());

                        startActivity(new Intent(VerifyOtp.this, AppDashboard.class));
                        finish();
                    } else {
                        Toast.makeText(getApplicationContext(), "Oops, something went wrong", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(VerifyOtp.this, Signup.class));
                        finish();
                    }
                });
    }
}