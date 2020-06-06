package com.example.smarthome;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ToggleButton;

public class RedlampActivity extends AppCompatActivity {
    ToggleButton toggleButton;
    ImageView imageView, status;
    LinearLayout bluelamp, yellowlamp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_redlamp);

        toggleButton = findViewById(R.id.toogle);
        imageView = findViewById(R.id.image);
        status = findViewById(R.id.status);

        imageView.setImageDrawable(getResources().getDrawable(R.drawable.redoff));
        toggleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (toggleButton.isChecked()){
                    imageView.setImageDrawable(getResources().getDrawable(R.drawable.redon));
                    status.setImageDrawable(getResources().getDrawable(R.drawable.heart_orange_fill));
                }else {
                    imageView.setImageDrawable(getResources().getDrawable(R.drawable.redoff));
                    status.setImageDrawable(getResources().getDrawable(R.drawable.heart));
                }
            }
        });

        bluelamp = findViewById(R.id.bluelamp);
        bluelamp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RedlampActivity.this, BluelampActivity.class);
                startActivity(intent);
            }
        });

        yellowlamp = findViewById(R.id.yellowlamp);
        yellowlamp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RedlampActivity.this, MonitoringActivity.class);
                startActivity(intent);
            }
        });
    }
}
