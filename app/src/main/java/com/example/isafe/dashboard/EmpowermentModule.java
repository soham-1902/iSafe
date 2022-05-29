package com.example.isafe.dashboard;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import com.example.isafe.R;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class EmpowermentModule extends AppCompatActivity {

    private CardView fiveSelfDefCv, defenceWomanShouldKnowCv, safetyAgainstMolestation, chokeHoldDefenceCv;

    private CardView showawCv, pfrCv, powfdv, irow, claa;

    private Toolbar toolbarEducate;

    private ImageButton ambulanceBtn, helplineBtn, policeBtn;

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
        setContentView(R.layout.activity_empowerment_module);

        fiveSelfDefCv = findViewById(R.id.fiveSelfDefCv);
        defenceWomanShouldKnowCv = findViewById(R.id.defenceWomanShouldKnowCv);
        safetyAgainstMolestation = findViewById(R.id.safetyAgainstMolestationCv);
        chokeHoldDefenceCv = findViewById(R.id.chokeHoldDefenceCv);
        toolbarEducate = findViewById(R.id.toolbarEducate);
        showawCv = findViewById(R.id.showawCv);
        pfrCv = findViewById(R.id.pfrCv);
        powfdv = findViewById(R.id.powfdv);
        irow = findViewById(R.id.irow);
        claa = findViewById(R.id.claa);
        ambulanceBtn = findViewById(R.id.ambulance_btn);
        helplineBtn = findViewById(R.id.helpline_btn);
        policeBtn = findViewById(R.id.police_btn);

        setSupportActionBar(toolbarEducate);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        fiveSelfDefCv.setOnClickListener(v -> {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://youtu.be/KVpxP3ZZtAc")));
        });

        defenceWomanShouldKnowCv.setOnClickListener(v -> {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://youtu.be/k9Jn0eP-ZVg")));
        });

        safetyAgainstMolestation.setOnClickListener(v -> {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://youtu.be/9OdzSFyL0_0")));
        });

        chokeHoldDefenceCv.setOnClickListener(v -> {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://youtu.be/-V4vEyhWDZ0")));
        });

        showawCv.setOnClickListener(v -> {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://en.wikipedia.org/wiki/Sexual_Harassment_of_Women_at_Workplace_(Prevention,_Prohibition_and_Redressal)_Act,_2013")));
        });

        pfrCv.setOnClickListener(v -> {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://devgan.in/ipc/section/376/")));
        });

        powfdv.setOnClickListener(v -> {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://en.wikipedia.org/wiki/Protection_of_Women_from_Domestic_Violence_Act,_2005")));
        });

        irow.setOnClickListener(v -> {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.legalserviceindia.com/legal/article-5848-indecent-representation-of-women-prohibition-act-1986-an-overview.html")));
        });

        claa.setOnClickListener(v -> {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://en.wikipedia.org/wiki/Criminal_Law_(Amendment)_Act,_2013")));
        });

        ambulanceBtn.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_DIAL);
            intent.setData(Uri.parse("tel:102"));
            startActivity(intent);
        });

        helplineBtn.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_DIAL);
            intent.setData(Uri.parse("tel:1091"));
            startActivity(intent);
        });

        policeBtn.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_DIAL);
            intent.setData(Uri.parse("tel:100"));
            startActivity(intent);
        });
    }
}