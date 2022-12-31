package com.example.isafe.dashboard;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.telephony.SmsManager;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.isafe.R;
import com.klinker.android.send_message.Message;
import com.klinker.android.send_message.Settings;
import com.klinker.android.send_message.Transaction;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.List;

public class TransportModule extends AppCompatActivity {

    private ImageButton cameraIb;
    private ImageView displayIv;
    private TextView noImageTv;
    private Button sendMessageBtn;
    private Uri tempUri;
    private File finalFile;
    private RadioGroup vehicleRg;
    private RadioButton vehicleRb;
    private CheckBox locationCb;
    private StringBuilder whatsappMessage;

    private double lat = -1, lng = -1;
    private LocationManager locationManager;
    private LocationListener locationListener;
    private boolean isSent = false;

    private Toolbar toolbarTransport;

    private static final int CAMERA_REQUEST = 1888;
    private static final int MY_CAMERA_PERMISSION_CODE = 100;

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
        setContentView(R.layout.activity_transport_module);

        cameraIb = findViewById(R.id.cameraIb);
        displayIv = findViewById(R.id.displayIv);
        noImageTv = findViewById(R.id.noImageTv);
        sendMessageBtn = findViewById(R.id.sendMessageBtn);
        vehicleRg = findViewById(R.id.vehicleRg);
        locationCb = findViewById(R.id.locationCb);
        whatsappMessage = new StringBuilder();
        toolbarTransport = findViewById(R.id.toolbarTransport);

        setSupportActionBar(toolbarTransport);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        cameraIb.setOnClickListener(v -> {
            {
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
            }
        });

        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        locationListener = location -> {
            lat = location.getLatitude();
            lng = location.getLongitude();
        };

        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);


        sendMessageBtn.setOnClickListener(v -> {
            vehicleRb = findViewById(vehicleRg.getCheckedRadioButtonId());
            if(!(vehicleRg.getCheckedRadioButtonId() == -1)) {
                whatsappMessage.append("I am travelling trough a ").append(vehicleRb.getText()).append(". ");
            } else {
                Toast.makeText(getApplicationContext(), "Please select your mode of transport!", Toast.LENGTH_SHORT).show();
                return;
            }
            locationListener = location -> {
                lat = location.getLatitude();
                lng = location.getLongitude();
            };
            if (locationCb.isChecked()) {
                if (lat == -1 && lng == -1) {
                    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        return;
                    }
                    /*Location lastKnown = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
                    lat = lastKnown.getLatitude();
                    lng = lastKnown.getLongitude();*/
                    lat = getLastKnownLocation().getLatitude();
                    lng = getLastKnownLocation().getLongitude();
               }
               whatsappMessage.append("\nMy current location is ").append("https://www.google.com/maps/search/?api=1&query=").append(lat).append(",").append(lng);
           }
            sendSms();
        });

    }

    public void sendSms() {
        String message = whatsappMessage.toString();
        Intent whatsappIntent = new Intent(Intent.ACTION_SEND);
        whatsappIntent.setType("text/plain");
        whatsappIntent.setPackage("com.whatsapp");
        whatsappIntent.putExtra(Intent.EXTRA_TEXT, message);

        if(!(finalFile == null)) {
            Uri imgUri = Uri.parse(finalFile.toString());
            whatsappIntent.putExtra(Intent.EXTRA_STREAM, imgUri);
            whatsappIntent.setType("image/jpeg");
            whatsappIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        }

        try {
            startActivity(whatsappIntent);
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(this, "WhatsApp not Installed", Toast.LENGTH_SHORT).show();
        }
        whatsappMessage.setLength(0);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_CAMERA_PERMISSION_CODE)
        {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {
                Toast.makeText(this, "Camera permission granted!", Toast.LENGTH_LONG).show();
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
            }
            else
            {
                Toast.makeText(this, "Camera permission denied!", Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
            ViewGroup.LayoutParams layoutParams = displayIv.getLayoutParams();
            layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT;
            displayIv.setLayoutParams(layoutParams);
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            noImageTv.setText("Captured image is");
            displayIv.setImageBitmap(photo);
            tempUri = getImageUri(getApplicationContext(), photo);
            finalFile = new File(getRealPathFromURI(tempUri));
        }
    }

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    public String getRealPathFromURI(Uri uri) {
        String path = "";
        if (getContentResolver() != null) {
            Cursor cursor = getContentResolver().query(uri, null, null, null, null);
            if (cursor != null) {
                cursor.moveToFirst();
                int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                path = cursor.getString(idx);
                cursor.close();
            }
        }
        return path;
    }
    private Location getLastKnownLocation() {
        Location l=null;
        LocationManager mLocationManager = (LocationManager)getApplicationContext().getSystemService(LOCATION_SERVICE);
        List<String> providers = mLocationManager.getProviders(true);
        Location bestLocation = null;
        for (String provider : providers) {
            if(ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION)==PackageManager.PERMISSION_GRANTED) {
                l = mLocationManager.getLastKnownLocation(provider);
            }
            if (l == null) {
                continue;
            }
            if (bestLocation == null || l.getAccuracy() < bestLocation.getAccuracy()) {
                bestLocation = l;
            }
        }
        return bestLocation;
    }
}