package com.example.darek.nav_10;

import android.content.Context;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
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

    private final int MARKED_ITEM_COLOR = 0xFF33B5E5;
    private final int ITEMS_COLOR = 0xFF0099CC;

    public FragmentTracking(Context context_){
        context = context_;
    }

    Button activeButton = null;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_tracking, container, false);

        distanceCounter = new DistanceCounter();
        textViewDistance = (TextView) view.findViewById(R.id.textViewKilometers);
        textViewCoordinates = (TextView) view.findViewById(R.id.TextViewGPSCoordinates);
        textViewGpsAccuracy = (TextView) view.findViewById(R.id.TextViewGPSAccuracy);

        final Handler secHandler_5 = new Handler();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                textViewDistance.setText(String.format("%.2f", distanceCounter.UpdateDistance()) + "km");
                secHandler_5.postDelayed(this, 5000);
            }
        };
        secHandler_5.post(runnable);

        buttonStart = (Button) view.findViewById(R.id.buttonStart);
        buttonStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                distanceCounter.StartCountingDistance(gpsTracker.getLocation());
                textViewDistance.setText("0.00km");
                ChangeButtonColorOnClick(buttonStart);
            }
        });
        buttonStop = (Button) view.findViewById(R.id.buttonStop);
        buttonStop.setBackgroundColor(MARKED_ITEM_COLOR);
        activeButton = buttonStop;
        buttonStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                distanceCounter.StopCountingDistance();
                ChangeButtonColorOnClick(buttonStop);
            }
        });
        gpsTracker = new GPSTracker(context) {
            @Override
            public void onLocationChanged(Location location) {
                if (location != null) {
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
    private void ChangeButtonColorOnClick(Button button){
        if (activeButton != null){
            activeButton.setBackgroundColor(ITEMS_COLOR);
        }
        button.setBackgroundColor(MARKED_ITEM_COLOR);
        activeButton = button;
    }
}
