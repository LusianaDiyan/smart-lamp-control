package com.example.smarthome;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DownloadManager;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.view.textclassifier.ConversationActions;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.Locale;

public class MonitoringActivity extends AppCompatActivity {
    private   String mStringUrl="http://192.168.43.61/";
    private OkHttpClient client;
    private Request request;
    private  String TAG="MainActivity";

    ToggleButton toggleButton;
    ImageView imageView, status;
    LinearLayout bluelamp, redlamp, greenlamp;

    TextView tvTimer, tvsatuan, tvTagihan;
    ImageView reset;
    double satuan = 0;
    double tagihan =0;

    private int seconds = 0;
    private boolean running;
    private boolean wasRunning;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monitoring);
        client = new OkHttpClient();

        toggleButton = findViewById(R.id.toogle);
        imageView = findViewById(R.id.image);
        status = findViewById(R.id.status);
        reset = findViewById(R.id.reset);

        tvTimer = findViewById(R.id.tvTimer);
        tvTimer.setText("00:00:00");

        tvsatuan = findViewById(R.id.satuan);
        tvsatuan.setText(""+ satuan);

        tvTagihan = findViewById(R.id.tagihan);

        imageView.setImageDrawable(getResources().getDrawable(R.drawable.lampoff));
        toggleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (toggleButton.isChecked()){
                    imageView.setImageDrawable(getResources().getDrawable(R.drawable.yellowlamp));
                    status.setImageDrawable(getResources().getDrawable(R.drawable.heart_orange_fill));
                    nyala("0");
                    //satuan++;
                    //tvsatuan.setText(""+ satuan);

                    running = true;
                }else {
                    imageView.setImageDrawable(getResources().getDrawable(R.drawable.lampoff));
                    status.setImageDrawable(getResources().getDrawable(R.drawable.heart));
                    nyala("1");

                    running = false;
                }
            }
        });

        bluelamp = findViewById(R.id.bluelamp);
        bluelamp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MonitoringActivity.this, BluelampActivity.class);
                startActivity(intent);
            }
        });

        redlamp = findViewById(R.id.redlamp);
        redlamp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MonitoringActivity.this, RedlampActivity.class);
                startActivity(intent);
            }
        });

        greenlamp = findViewById(R.id.greenlamp);
        greenlamp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MonitoringActivity.this, GreenlampActivity.class);
                startActivity(intent);
            }
        });

        if (savedInstanceState != null) {
            seconds
                    = savedInstanceState
                    .getInt("seconds");
            running
                    = savedInstanceState
                    .getBoolean("running");
            wasRunning
                    = savedInstanceState
                    .getBoolean("wasRunning");
        }
        runTimer();
    }

    private void nyala(String id){
        request = new Request.Builder().url(mStringUrl+id).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                Log.d(TAG, "onFailure: ");
            }

            @Override
            public void onResponse(Response response) throws IOException {
                Log.i(TAG, "onResponse: succes ");
            }
        });
    }

    @Override
    public void onSaveInstanceState(
            Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState
                .putInt("seconds", seconds);
        savedInstanceState
                .putBoolean("running", running);
        savedInstanceState
                .putBoolean("wasRunning", wasRunning);
    }

    @Override
    protected void onPause()
    {
        super.onPause();
        wasRunning = running;
        running = false;
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        if (wasRunning) {
            running = true;
        }
    }

    public void onClickReset(View view)
    {
        running = false;
        seconds = 0;
    }

    private void runTimer()
    {
        // Creates a new Handler
        final Handler handler
                = new Handler();
        handler.post(new Runnable() {
            @Override

            public void run()
            {
                int hours = seconds / 3600;
                int minutes = (seconds % 3600) / 60;
                int secs = seconds % 60;

                String time
                        = String
                        .format(Locale.getDefault(),
                                "%d:%02d:%02d", hours,
                                minutes, secs);

                // Set the text view text.
                tvTimer.setText(time);
                satuan = seconds * 0.25;
                tvsatuan.setText("Daya : "+satuan+" Watt");

                tagihan = satuan * 5;
                tvTagihan.setText("Tagihan : Rp. "+tagihan);

                if (running) {
                    seconds++;
                }
                handler.postDelayed(this, 1000);
            }
        });
    }
}
