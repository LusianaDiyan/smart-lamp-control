package com.example.smarthome;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ToggleButton;

public class BluelampActivity extends AppCompatActivity {

    ToggleButton toggleButton;
    ImageView imageView, status;
    LinearLayout redlamp, yellowlamp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bluelamp);

        toggleButton = findViewById(R.id.toogle);
        imageView = findViewById(R.id.image);
        status = findViewById(R.id.status);

        imageView.setImageDrawable(getResources().getDrawable(R.drawable.blueoff));
        toggleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (toggleButton.isChecked()){
                    imageView.setImageDrawable(getResources().getDrawable(R.drawable.bluelamp));
                    status.setImageDrawable(getResources().getDrawable(R.drawable.heart_orange_fill));
                }else {
                    imageView.setImageDrawable(getResources().getDrawable(R.drawable.blueoff));
                    status.setImageDrawable(getResources().getDrawable(R.drawable.heart));
                }
            }
        });

        redlamp = findViewById(R.id.redlamp);
        redlamp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BluelampActivity.this, RedlampActivity.class);
                startActivity(intent);
            }
        });

        yellowlamp = findViewById(R.id.yellowlamp);
        yellowlamp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BluelampActivity.this, MonitoringActivity.class);
                startActivity(intent);
            }
        });
    }
}
