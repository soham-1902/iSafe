package com.example.isafe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.isafe.loginModule.VerifyOtp;
import com.google.firebase.database.FirebaseDatabase;
import com.hbb20.CountryCodePicker;

import java.util.HashMap;

public class Signup extends AppCompatActivity {

    private Button getOtp, reviewData;
    private EditText name, email, password, confirmPassword, contactNumber;
    private String sname, scontactNum, semail, spassword, sconfirmPassword;
    private HashMap<String, Object> userData;
    private TextView reviewTv;
    private CountryCodePicker ccp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        getOtp = findViewById(R.id.getOtpBtn);
        name = findViewById(R.id.etName);
        email = findViewById(R.id.etEmail);
        password = findViewById(R.id.etPassword);
        confirmPassword = findViewById(R.id.etConfirmPass);
        reviewData = findViewById(R.id.reviewData);
        reviewTv = findViewById(R.id.reviewTv);
        contactNumber = findViewById(R.id.contactNumber);
        ccp = findViewById(R.id.ccp);
        
        ccp.registerCarrierNumberEditText(contactNumber);

        getOtp.setOnClickListener(v -> {

            sname = name.getText().toString().trim();
            scontactNum = contactNumber.getText().toString().trim();
            semail = email.getText().toString().trim();
            spassword = password.getText().toString().trim();
            sconfirmPassword = confirmPassword.getText().toString().trim();

            if(!spassword.equals(sconfirmPassword)) {
                Toast.makeText(Signup.this, "Confirm password does not match", Toast.LENGTH_SHORT).show();
                return;
            }

            if(sname.equals("") || scontactNum.equals("") || semail.equals("") || spassword.equals("") || sconfirmPassword.equals("")) {
                Toast.makeText(Signup.this, "All fields are mandatory", Toast.LENGTH_SHORT).show();
                return;
            }

                /*HashMap<String, Object> userData = new HashMap<>();
                userData.put("Name", sname);
                userData.put("Contact Number", scontactNum);
                userData.put("Email", semail);
                userData.put("Password", spassword);*/

                //FirebaseDatabase.getInstance().getReference().child("Users").push().setValue(userData).addOnCompleteListener(task -> Toast.makeText(Signup.this, "Data saved successfully", Toast.LENGTH_SHORT).show());
            //FirebaseDatabase.getInstance().getReference().child("Contact").push().setValue(userData);//addOnCompleteListener(task -> Toast.makeText(Signup.this, "Data saved successfully", Toast.LENGTH_SHORT).show());

            Intent openSignUp = new Intent(getApplicationContext(), VerifyOtp.class);
            openSignUp.putExtra("ContactNum", ccp.getFullNumberWithPlus().trim());
            openSignUp.putExtra("Name", sname);
            openSignUp.putExtra("Email", semail);
            openSignUp.putExtra("Password", spassword);

            this.finish();
            startActivity(openSignUp);
        });

        reviewData.setOnClickListener(v -> {
            sname = name.getText().toString().trim();
            scontactNum = contactNumber.getText().toString().trim();
            semail = email.getText().toString().trim();
            spassword = password.getText().toString().trim();
            sconfirmPassword = confirmPassword.getText().toString().trim();
            reviewTv.setText(new StringBuilder().append(sname).append("\n").append(ccp.getFullNumberWithPlus().trim()).append("\n").append(semail).append("\n").toString());
        });
    }
}