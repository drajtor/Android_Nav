package com.example.darek.nav_10;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;

/**
 * Created by Darek on 22.01.2017.
 */

public class GPSTracker extends Service implements LocationListener{

    private final Context context;

    boolean isGPSenabled = false;
    boolean canGetLocation = false;

    double latitude;
    double longtitude;

    static final int MIN_DIST_FOR_POS_UPDATE = 10;
    static final int MIN_TIM_FOR_POS_UPDATE = 1000 * 60;

    protected LocationManager locationManager;


    public GPSTracker(Context context){
        this.context=context;
        getLocation();
    }

    private Location getLocation()
    {
        try{
            locationManager = (LocationManager)context.getSystemService(LOCATION_SERVICE);
        }
        catch (Exception e){
            e.printStackTrace();
        }
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
