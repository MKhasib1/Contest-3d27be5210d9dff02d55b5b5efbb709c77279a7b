package com.example.hackathon;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;

import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class WaterTracking extends AppCompatActivity {
    private ImageView leadershipButton;
    private Button drinkButton;
    private TextView glassesWaterDrinked;
    private TextView glassesWaterNeeded;
    private TextView timerTextView;
    private TextView first;
    private TextView second;
    private ImageView glass;

    public  static final String DRINKED_WATER="com.example.login.DRINKED_WATER";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_water_tracking);

        leadershipButton=findViewById(R.id.leadershipButton);
        drinkButton=findViewById(R.id.drinkButton);
        glassesWaterDrinked=findViewById(R.id.glassesWaterDrinked);
        glassesWaterNeeded=findViewById(R.id.glassesWaterNeeded);
        first=findViewById(R.id.first);
        second=findViewById(R.id.second);
        glass=findViewById(R.id.glass1);
        timerTextView=findViewById(R.id.timerTextView);
        leadershipButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(WaterTracking.this,waterLeaderShip.class);
                intent.putExtra(DRINKED_WATER,glassesWaterDrinked.getText().toString());
                startActivity(intent);
            }
        });
        drinkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                timerTextView.setText("04:00:00");
                glassesWaterDrinked.setText(""+(Integer.parseInt(glassesWaterDrinked.getText().toString())+1));
                if(Integer.parseInt(glassesWaterNeeded.getText().toString())<0) {
                    first.setText("exceeded the total number of water needed");
                    first.setTextColor(Color.parseColor("#FF0000"));
                    second.setText("");
                    glass.setImageResource(0);


                }

                else {
                    glassesWaterNeeded.setText("" + (Integer.parseInt(glassesWaterNeeded.getText().toString()) - 1));
                }
            }
        });
    }

    public void LeadershipActivity(View view) {
//        Intent leadership = new Intent(WaterTracking.this, .class);
//        startActivity(leadership);
    }

    public void drink200MLButton(View view) {
    }
}
