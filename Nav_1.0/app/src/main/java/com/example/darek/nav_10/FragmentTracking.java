package com.example.darek.nav_10;

import android.content.Context;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by 212449139 on 2/3/2017.
 */
public class FragmentTracking extends Fragment {

    Button buttonStart;
    Button buttonStop;
    TextView textViewCoordinates;
    TextView textViewGpsAccuracy;

    DistanceCounter distanceCounter;
    TextView textViewDistance;
    GPSTracker gpsTracker;

    Context context;

    public FragmentTracking(Context context_){
        context = context_;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        return inflater.inflate(R.layout.fragment_tracking,container,false);

        View view = inflater.inflate(R.layout.fragment_tracking,container,false);

        distanceCounter = new DistanceCounter();
        textViewDistance = (TextView) view.findViewById(R.id.textViewKilometers);
        textViewCoordinates = (TextView) view.findViewById(R.id.TextViewGPSCoordinates);
        textViewGpsAccuracy = (TextView) view.findViewById(R.id.TextViewGPSAccuracy);

        buttonStart = (Button) view.findViewById(R.id.buttonStart);
        buttonStart.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                distanceCounter.StartCountingDistance(gpsTracker.getLocation());
                textViewDistance.setText("0.00km");
            }
        });
        buttonStop = (Button) view.findViewById(R.id.buttonStop);
        buttonStop.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                distanceCounter.StopCountingDistance();
            }
        });
        gpsTracker = new GPSTracker(context){
            @Override
            public void onLocationChanged(Location location) {
                if (location != null){
                    distanceCounter.UpdateLocation(location);
                    textViewCoordinates.setText(Double.toString(location.getLongitude()) +
                            "\n" +
                            Double.toString(location.getLatitude()));
                    textViewGpsAccuracy.setText("GPS Accuracy\n" + Float.toString(location.getAccuracy()));
                }
            }
        };

        return view;
    }
}
