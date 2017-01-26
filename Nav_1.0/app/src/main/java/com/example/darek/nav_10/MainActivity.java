package com.example.darek.nav_10;

import android.Manifest;
import android.content.pm.PackageManager;
import android.icu.text.DecimalFormat;
import android.location.Location;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Timer;

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

    ArrayList<Location> LocationContainer;

    static final int METERS_TO_KILOMETERS_RATIO = 1000;
    static final int METERS_DISPLAY_PRECISION = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewCoordinates = (TextView) findViewById(R.id.TextViewGPSCoordinates);
        textViewDistance = (TextView) findViewById(R.id.textViewKilometers);
        textViewGpsAccuracy = (TextView) findViewById(R.id.TextViewGPSAccuracy);
        LocationContainer = new ArrayList<Location>();

        final Handler secHandler_5 = new Handler();
        Runnable runnable = new Runnable(){
            @Override
            public void run() {
                if (LocationContainer != null && LocationContainer.size() > 0){
                    Location location = getMostAccurateLocation();
                    Log.i("Best Accuracy:",Float.toString(location.getAccuracy()));
                    if (trackingOn && location.getAccuracy() <= 20 ){
                        currentLocation = location;
                        float deltaDistance = currentLocation.distanceTo(lastLocation);
                        if (deltaDistance > 50){
                            distance = distance + (float)Math.round((deltaDistance/METERS_TO_KILOMETERS_RATIO)*METERS_DISPLAY_PRECISION)/METERS_DISPLAY_PRECISION;
                            lastLocation = currentLocation;
                            textViewDistance.setText(Float.toString(distance)+"km");
                        }
                    }
                }else{
                    Log.i("No location available"," ");
                }
                secHandler_5.postDelayed(this,5000);
            }
        };
        secHandler_5.post(runnable);

        gpsTracker = new GPSTracker(MainActivity.this){
            @Override
            public void onLocationChanged(Location location) {
                if (location != null){
                    LocationContainer.add(location);
                    textViewCoordinates.setText(Double.toString(location.getLongitude()) +
                            "\n" +
                            Double.toString(location.getLatitude()));
                    textViewGpsAccuracy.setText("GPS Accuracy\n" + Float.toString(location.getAccuracy()));
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
                if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                        && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//                        ){
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

    public Location getMostAccurateLocation (){
        Location max = LocationContainer.get(0);

        for (int i = 1 ; i < LocationContainer.size() ; i++){
            if (LocationContainer.get(i-1).getAccuracy() > LocationContainer.get(i).getAccuracy() ){
                max = LocationContainer.get(i);
            }
        }
        LocationContainer.clear();
        return max;
    }
}

