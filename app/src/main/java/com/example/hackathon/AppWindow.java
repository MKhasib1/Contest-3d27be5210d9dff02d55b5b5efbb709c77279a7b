package com.example.hackathon;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class AppWindow extends AppCompatActivity {
private Button waterTrackingButton;
private Button  stepsCountValue;
   int steps = 0 ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_window);
        waterTrackingButton=findViewById(R.id.waterTrackingButton);
        stepsCountValue=findViewById(R.id.stepsCountValue);
        stepsCountValue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(AppWindow.this,StepTracker.class);

                startActivity(intent);
            }
        });
        waterTrackingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(AppWindow.this,WaterTracking.class);

                startActivity(intent);
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();


    }
}
