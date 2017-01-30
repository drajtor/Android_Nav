package com.example.darek.nav_10;

import android.location.Location;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by 212449139 on 1/30/2017.
 */

public class DistanceCounter {

    float Distance = 0;
    Location LastLocation;
    Location CurrentLocation;
    boolean trackingOn = false;

    ArrayList<Location> LocationContainer;

    static final int METERS_TO_KILOMETERS_RATIO = 1000;
    static final int METERS_DISPLAY_PRECISION = 100;

    public DistanceCounter(){
        LocationContainer = new ArrayList<Location>();
    }

    public float getDistance (){
        return Distance;
    }

    public void StartCountingDistance(Location startLocation){
        Distance = 0;
        trackingOn = true;
        CurrentLocation = startLocation;
        LastLocation = CurrentLocation;
    }

    public void StopCountingDistance(){
        trackingOn = false;
    }

    public float UpdateDistance(){
        if (LocationContainer != null && LocationContainer.size() > 0){
            Location location = getMostAccurateLocation();
            Log.i("Best Accuracy:",Float.toString(location.getAccuracy()));
            if (trackingOn && location.getAccuracy() <= 20 ){
                CurrentLocation = location;
                float deltaDistance = CurrentLocation.distanceTo(LastLocation);
                if (deltaDistance > 50){
                    Distance = Distance + (float)Math.round((deltaDistance/METERS_TO_KILOMETERS_RATIO)*METERS_DISPLAY_PRECISION)/METERS_DISPLAY_PRECISION;
                    LastLocation = CurrentLocation;
                }
            }
        }else{
            Log.i("No location available"," ");
        }
        return Distance;
    }

    public void UpdateLocation (Location location ){
        if (location != null) {
            LocationContainer.add(location);
        }
    }

    private Location getMostAccurateLocation (){
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
