package com.example.darek.nav_10;

import android.Manifest;
import android.content.pm.PackageManager;
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
    TextView textViewCoordinates;
    GPSTracker gpsTracker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewCoordinates = (TextView) findViewById(R.id.TextViewGPSCoordinates);
        gpsTracker = new GPSTracker(MainActivity.this){
            @Override
            public void onLocationChanged(Location location) {
                textViewCoordinates.setText(Double.toString(location.getLongitude()) +
                        "\n" +
                        Double.toString(location.getLatitude()));
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
