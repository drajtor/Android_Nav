package com.example.darek.nav_10;

import android.Manifest;
import android.content.pm.PackageManager;
import android.icu.text.DecimalFormat;
import android.location.Location;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    Button buttonShowCoordinates;
    Button buttonStart;
    Button buttonStop;
    TextView textViewCoordinates;
    TextView textViewDistance;
    TextView textViewGpsAccuracy;

    GPSTracker gpsTracker;
    boolean trackingOn = false;
    float distance = 0;
    Location currentLocation;
    Location lastLocation;

    static final int METERS_TO_KILOMETERS_RATIO = 1000;
    static final int METERS_DISPLAY_PRECISION = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewCoordinates = (TextView) findViewById(R.id.TextViewGPSCoordinates);
        textViewDistance = (TextView) findViewById(R.id.textViewKilometers);
        textViewGpsAccuracy = (TextView) findViewById(R.id.TextViewGPSAccuracy);

        gpsTracker = new GPSTracker(MainActivity.this){
            @Override
            public void onLocationChanged(Location location) {
                float accuracy = location.getAccuracy();
                textViewCoordinates.setText(Double.toString(location.getLongitude()) +
                        "\n" +
                        Double.toString(location.getLatitude()));
                textViewGpsAccuracy.setText("GPS Accuracy\n" + Float.toString(accuracy));
                if (trackingOn && accuracy < 100){
                    currentLocation = location;
                    distance = distance + (float)Math.round((currentLocation.distanceTo(lastLocation)/METERS_TO_KILOMETERS_RATIO)*METERS_DISPLAY_PRECISION)/METERS_DISPLAY_PRECISION;
                    lastLocation = currentLocation;
                    textViewDistance.setText(Float.toString(distance)+"km");
                }
            }
        };
        buttonShowCoordinates = (Button) findViewById(R.id.buttonGPSCoordinates);
        buttonShowCoordinates.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Location location = gpsTracker.getLocation();
                if (location != null) {
                    textViewCoordinates.setText(Double.toString(location.getLongitude()) +
                            "\n" +
                            Double.toString(location.getLatitude()));
                }
            }
        });
        buttonStart = (Button) findViewById(R.id.buttonStart);
        buttonStart.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                distance = 0;
                trackingOn = true;
                currentLocation = gpsTracker.getLocation();
                lastLocation = currentLocation;
                textViewDistance.setText(Float.toString(distance)+"km");
            }
        });
        buttonStop = (Button) findViewById(R.id.buttonStop);
        buttonStop.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                trackingOn = false;
                textViewDistance.setText("0km");
            }
        });

        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                        checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                    requestPermissions(new String[]{
                            Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_COARSE_LOCATION,
                            Manifest.permission.INTERNET
                            }, 10);
                    return;
                }
            }
        }   catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case 10:{
                if(grantResults.length >0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    //TODO close app
                }
            }
        }
    }
}
