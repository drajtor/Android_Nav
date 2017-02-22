package com.example.darek.nav_10;

import android.content.Context;
import android.location.Location;

import static com.example.darek.nav_10.TrackHandler.State.PAUSE;
import static com.example.darek.nav_10.TrackHandler.State.START;
import static com.example.darek.nav_10.TrackHandler.TrackType.TRACK_BILLABLE;
import static com.example.darek.nav_10.TrackHandler.TrackType.TRACK_NON_BILLABLE;

/**
 * Created by 212449139 on 2/22/2017.
 */

public class TrackHandler {

    enum State {START, PAUSE, STOP,}
    enum TrackType {TRACK_BILLABLE, TRACK_NON_BILLABLE}
    private class StateC{
        State state;
    }

    Context context;

    private StateC BillableTrackState = new StateC();
    private StateC NonBillableTrackState = new StateC();
    private StateC ActiveTrackState = NonBillableTrackState;

    private DistanceCounter distanceCounterBillable = new DistanceCounter();
    private DistanceCounter distanceCounterNonBillable = new DistanceCounter();
    private DistanceCounter ActiveDistanceCounter = distanceCounterNonBillable;

    private TrackType currentTrackType = TRACK_NON_BILLABLE;

    GPSTracker gpsTracker;

    public TrackHandler (Context context){
        this.context = context;
        BillableTrackState.state = PAUSE;
        NonBillableTrackState.state = PAUSE;

        gpsTracker = new GPSTracker(context) {
            @Override
            public void onLocationChanged(Location location) {
                if (location != null) {
                    if (BillableTrackState.state == START){
                        distanceCounterBillable.UpdateLocation(location);
                    }
                    else if (NonBillableTrackState.state == START){
                        distanceCounterNonBillable.UpdateLocation(location);
                    }
//                    textViewGpsAccuracy.setText("GPS Accuracy\n" + Float.toString(location.getAccuracy()));
                }
            }
        };
    }

    public TrackType getCurrentTrackType (){
        return  currentTrackType;
    }

    public void setCurrentTrackType (TrackType trackType){
        currentTrackType = trackType;
        if (currentTrackType == TRACK_BILLABLE){
            ActiveTrackState = BillableTrackState;
            ActiveDistanceCounter = distanceCounterBillable;
        }else{
            ActiveTrackState = NonBillableTrackState;
            ActiveDistanceCounter = distanceCounterNonBillable;
        }
    }

    public void setActiveTrackState (State state){
        ActiveTrackState.state = state;
    }

    public State getActiveTrackState(){
        return ActiveTrackState.state;
    }

    public State getTrackState (TrackType trackType){
        switch (trackType){
            case TRACK_BILLABLE:
                return BillableTrackState.state;
            case TRACK_NON_BILLABLE:
            default:
                return NonBillableTrackState.state;
        }
    }

    public float UpdateDistance (){
        return ActiveDistanceCounter.UpdateDistance();
    }

    public float getDistance (TrackType trackType){
        switch (trackType){
            case TRACK_BILLABLE:
                return distanceCounterBillable.getDistance();
            case TRACK_NON_BILLABLE:
            default:
                return distanceCounterNonBillable.getDistance();
        }
    }

    public void TracksStateHandler (){
        switch (currentTrackType){
            case TRACK_BILLABLE:
                StateHandler(BillableTrackState,NonBillableTrackState,distanceCounterBillable,distanceCounterNonBillable);
                break;
            case TRACK_NON_BILLABLE:
            default:
                StateHandler(NonBillableTrackState,BillableTrackState,distanceCounterNonBillable,distanceCounterBillable);
                break;
        }
    }

    private void StateHandler (StateC state, StateC previousState,
                               DistanceCounter activeCounter, DistanceCounter pausedCounter ){
        switch (state.state){
            case START:
                activeCounter.StartCountingDistance(gpsTracker.getLocation());
                pausedCounter.StopCountingDistance();
                previousState.state = PAUSE;
                break;
            case STOP:
                activeCounter.ResetCounter();
                break;
            case PAUSE:
                activeCounter.StopCountingDistance();
                break;
        }
    }
}
