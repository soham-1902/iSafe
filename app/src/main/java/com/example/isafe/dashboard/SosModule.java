package com.example.isafe.dashboard;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;

import com.example.isafe.R;
import com.example.isafe.databaseHandling.DatabaseHelper;
import com.example.isafe.databaseHandling.ParticipantModel;

import java.util.List;

public class SosModule extends AppCompatActivity {
    private String[] numbers;
    private String message = "I might be in danger.";

    double lat = -1, lng = -1;

    private static final int REQUEST_CODE_PERMISSION = 1;
    String mPermission = Manifest.permission.ACCESS_FINE_LOCATION;

    private Button startBtn, stopBtn;
    private MediaPlayer mediaPlayer = null;

    private LocationManager locationManager;
    private LocationListener locationListener;

    private Toolbar toolbarSos;

    private boolean isSent = false;

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
        setContentView(R.layout.activity_sos_module);

        startBtn = findViewById(R.id.startBtn);
        stopBtn = findViewById(R.id.stopBtn);
        toolbarSos = findViewById(R.id.toolbarSos);

        setSupportActionBar(toolbarSos);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        numbers = getNumbers();


        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        locationListener = location -> {
                lat = location.getLatitude();
                lng = location.getLongitude();

            if (lat == -1 && lng == -1) {
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                Location lastKnown = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                lat = lastKnown.getLatitude();
                lng = lastKnown.getLongitude();
            }
                message = "Hello I am in danger! My current location is: " + "https://www.google.com/maps/search/?api=1&query=" + lat + "," + lng;
                if (!isSent) {
                    for (String num : numbers) {
                        sendSms(num);
                    }
                    isSent = true;
                }
        };

        locationManager.requestSingleUpdate(LocationManager.NETWORK_PROVIDER, locationListener, null);

        if (Build.VERSION.SDK_INT >= 23) {

            if (checkSelfPermission(mPermission) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(SosModule.this, new String[]{mPermission}, REQUEST_CODE_PERMISSION);
                return;
            } else {
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 1, locationListener);
            }
        }

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
    public void onStop() {
        super.onStop();
        mediaPlayer.stop();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults.length > 0) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 1, locationListener);
            }
         }
    }

    private void sendSms(String num) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED) {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(num, null, message, null, null);
            Toast.makeText(getApplicationContext(), "Message Sent (" + num + ")", Toast.LENGTH_LONG).show();
        } else {
            ActivityCompat.requestPermissions(SosModule.this, new String[]{Manifest.permission.SEND_SMS}, 2);
        }
    }

    public String[] getNumbers() {
        DatabaseHelper databaseHelper = new DatabaseHelper(SosModule.this);
        List<ParticipantModel> all = databaseHelper.getAll();

        String[] arr = new String[all.size()];

        for (int i = 0; i < all.size(); i++) {

            arr[i] = all.get(i).getNumber();

        }

        return arr;
    }
}