package com.example.darek.nav_10;

import android.location.Location;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    Button buttonShowCoordinates;
    TextView textViewCoordinates;
    GPSTracker gpsTracker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewCoordinates = (TextView) findViewById(R.id.TextViewGPSCoordinates);
        buttonShowCoordinates = (Button) findViewById(R.id.buttonGPSCoordinates);
        buttonShowCoordinates.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                gpsTracker = new GPSTracker(MainActivity.this);
                Location location =  gpsTracker.getLocation();

                textViewCoordinates.setText(Double.toString( location.getLongitude()) + " " + Double.toString( location.getLatitude()));
            }
        });
    }
}
