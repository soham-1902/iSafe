package com.example.isafe.dashboard.videocall;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;

import com.example.isafe.R;

public class FakeCallModule extends AppCompatActivity {

    Button maleHindi;
    private Toolbar toolbarFakeCall;

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            this.finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fake_call_module);

        maleHindi = findViewById(R.id.maleHindi);
        toolbarFakeCall = findViewById(R.id.toolbarFakeCall);

        setSupportActionBar(toolbarFakeCall);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        maleHindi.setOnClickListener(v -> {
            startActivity(new Intent(FakeCallModule.this, VcMaleHindi.class));
        });

    }
}