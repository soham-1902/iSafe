package com.example.isafe.dashboard;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Toast;

import com.example.isafe.R;
import com.example.isafe.dashboard.videocall.FakeCallModule;
import com.example.isafe.greetUser.MainActivity2;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class AppDashboard extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private Button logout;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private Toolbar toolbar;
    private CardView sosCv, transportCv, contactsCv, fakeCallCv, empowermentCv, aboutUsCv;
    private MenuItem home;

    private SensorManager sm;
    private float acelVal,acelLast,shake;
    private int counter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_dashboard);
        setNavigationViewListener();

        drawerLayout = findViewById(R.id.drawerLayout);
        navigationView = findViewById(R.id.navigationView);
        toolbar = findViewById(R.id.toolbar);
        sosCv = findViewById(R.id.sosCv);
        transportCv = findViewById(R.id.transportCv);
        contactsCv = findViewById(R.id.contactsCv);
        fakeCallCv = findViewById(R.id.fakeCallCv);
        empowermentCv = findViewById(R.id.empowermentCv);
        aboutUsCv = findViewById(R.id.aboutUsCV);
        sosCv = findViewById(R.id.sosCv);
        aboutUsCv = findViewById(R.id.aboutUsCV);
        counter = 0;


        sm=(SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sm.registerListener(sensorListener,sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);

        acelVal=SensorManager.GRAVITY_EARTH;
        acelLast=SensorManager.GRAVITY_EARTH;
        shake=0.00f;

        int PERMISSION_ALL = 1;
        String[] PERMISSIONS = {
                Manifest.permission.SEND_SMS,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                android.Manifest.permission.READ_SMS,
                android.Manifest.permission.CAMERA
        };

        if (!hasPermissions(this, PERMISSIONS)) {
            ActivityCompat.requestPermissions(this, PERMISSIONS, PERMISSION_ALL);
        }

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,
                R.string.navigation_open, R.string.navigation_close);

        drawerLayout.addDrawerListener(toggle);
        drawerLayout.setDrawerListener(toggle);
        toggle.syncState();
        toggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.white));
        navigationView.bringToFront();

        contactsCv.setOnClickListener(view -> {
            startActivity(new Intent(getApplicationContext(), ContactsModule.class));
        });

        transportCv.setOnClickListener(view -> {
            startActivity(new Intent(getApplicationContext(), TransportModule.class));
        });

        sosCv.setOnClickListener(v -> {
            startActivity(new Intent(getApplicationContext(), SosModule.class));
        });

        aboutUsCv.setOnClickListener(v -> {
            startActivity(new Intent(getApplicationContext(), AboutUsModule.class));
        });

        fakeCallCv.setOnClickListener(v -> {
            startActivity(new Intent(getApplicationContext(), FakeCallModule.class));
        });

        empowermentCv.setOnClickListener(v -> {
            startActivity(new Intent(getApplicationContext(), EmpowermentModule.class));
        });


    }

    public static boolean hasPermissions(Context context, String... permissions) {
        if (context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }

    private void setNavigationViewListener() {
        NavigationView navigationView = findViewById(R.id.navigationView);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        switch (item.getItemId()) {

            case R.id.logoutNav: {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(getApplicationContext(), MainActivity2.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                this.finish();
                startActivity(intent);
                break;
            }
            case R.id.homeNav: {

            }
            case R.id.shareNav: {

            }
        }
        //close navigation drawer
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    private final SensorEventListener sensorListener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event) {
            float x =event.values[0];
            float y =event.values[1];
            float z =event.values[2];
            acelLast=acelVal;
            acelVal=(float) Math.sqrt((double) (x*x)+(y*y)+(z*z));
            float delta= acelVal-acelLast;
            shake =shake*0.9f+delta;

            if(shake>25){
                counter += 1;
                if(counter == 3) {
                    counter = 0;
                    if(hasWindowFocus()) {
                        startActivity(new Intent(getApplicationContext(), SosModule.class));
                    }
                }
            }
        }
        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {}
    };
}