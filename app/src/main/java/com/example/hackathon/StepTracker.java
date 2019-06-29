package com.example.hackathon;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class StepTracker extends AppCompatActivity implements SensorEventListener{


    private TextView stepsCount, calories, distance, activeTime,destinationValue;

    private Button backButton;
    private SensorManager sensorManager;
    private SensorEventListener gyroscopeSensorListener;
    boolean activityRunning = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_tracker);

        stepsCount = findViewById(R.id.stepsCountValue);
        calories = findViewById(R.id.caloriesValue);
        distance = findViewById(R.id.destinationValue);
        activeTime = findViewById(R.id.timeValue);
        backButton=findViewById(R.id.backButton);
        destinationValue= findViewById(R.id.destinationValue);
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);


        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(StepTracker.this,AppWindow.class);

                startActivity(intent);
            }
        });



    }


    @Override
    protected void onResume() {
        super.onResume();
        activityRunning = true;
        Sensor countSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
        //countSensor = sensorManager.getDefaultSensor()
        if (countSensor != null) {
            sensorManager.registerListener(this, countSensor, SensorManager.SENSOR_DELAY_UI);

            //Log.d("counter", countSensor.toString());
        } else {
            Toast.makeText(this, "not available", Toast.LENGTH_LONG).show();
        }
    }


    @Override
    protected void onPause() {
        super.onPause();
        activityRunning = false;

        //sensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {

        if (activityRunning) {
            stepsCount.setText(String.valueOf(sensorEvent.values[0]-45753))
                    ;
           destinationValue.setText(Double.toString((Double.parseDouble(stepsCount.getText().toString())/1250)));
            calories.setText(Double.toString((Double.parseDouble(stepsCount.getText().toString())/20)));

        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {
    }








    public void LeadershipStepTrackerActivity(View view) {
    }

    public void backToHome(View view) {


    }
}