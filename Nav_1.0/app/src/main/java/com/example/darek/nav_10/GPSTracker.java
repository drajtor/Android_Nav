package com.example.darek.nav_10;

import android.Manifest;
import android.app.Activity;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;

/**
 * Created by Darek on 22.01.2017.
 */

public class GPSTracker extends Service implements LocationListener {

    private final Context context;

    Location location;

    boolean isGPSenabled = false;
    boolean isNetworkEnabled = false;
    boolean canGetLocation = false;

    static final int MIN_DIST_FOR_POS_UPDATE = 1;//25;

    static final int MIN_TIM_FOR_POS_UPDATE = 5000;
    static final int MIN_TIM_FOR_POS_UPDATE_LOW_ACCURACY = 1000;

    static final int TIM_AND_DIST__MIN = 0;

    static final int DESIRED_ACCURACY = 20;

    protected LocationManager locationManager;

    public GPSTracker(Context context) {
        this.context = context;
        getLocation();
    }

    public Location getLocation() {
        try {
            locationManager = (LocationManager) context.getSystemService(LOCATION_SERVICE);
            if (locationManager == null) {
                return null;
            }
            isGPSenabled = locationManager.isProviderEnabled(locationManager.GPS_PROVIDER);
            isNetworkEnabled = locationManager.isProviderEnabled(locationManager.NETWORK_PROVIDER);

            if (!isGPSenabled && !isNetworkEnabled) {

            } else {
                this.canGetLocation = true;

                long timeForPositionUpdate = 0;
                float distanceForPositionUpdate = 0;
//                if (location.getAccuracy() > DESIRED_ACCURACY){
//                    timeForPositionUpdate = MIN_TIM_FOR_POS_UPDATE_LOW_ACCURACY;
//                }else{
//                    timeForPositionUpdate = MIN_TIM_FOR_POS_UPDATE;
//                }
                timeForPositionUpdate = MIN_TIM_FOR_POS_UPDATE_LOW_ACCURACY;

                if (isNetworkEnabled) {
                    locationManager.requestLocationUpdates(
                            locationManager.NETWORK_PROVIDER,
                            timeForPositionUpdate,
                            MIN_DIST_FOR_POS_UPDATE,
                            this);
                    location = locationManager.getLastKnownLocation(locationManager.NETWORK_PROVIDER);
                }
                if (isGPSenabled){
                    locationManager.requestLocationUpdates(
                            locationManager.GPS_PROVIDER,
                            timeForPositionUpdate,
                            MIN_DIST_FOR_POS_UPDATE,
                            this );
                    location = locationManager.getLastKnownLocation(locationManager.GPS_PROVIDER);
                }
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    return location;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
