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
import android.widget.TextView;

import static com.example.darek.nav_10.FragmentTracking.State.PAUSE;
import static com.example.darek.nav_10.FragmentTracking.State.START;
import static com.example.darek.nav_10.FragmentTracking.State.STOP;
import static com.example.darek.nav_10.FragmentTracking.TrackType.TRACK_BILLABLE;
import static com.example.darek.nav_10.FragmentTracking.TrackType.TRACK_NON_BILLABLE;

/**
 * Created by 212449139 on 2/3/2017.
 */
public class FragmentTracking extends Fragment {

    enum State {START, PAUSE, STOP,}
    enum TrackType {TRACK_BILLABLE, TRACK_NON_BILLABLE}
    private class StateC{
        State state;
    }

    Context context;

    Button buttonStart;
    Button buttonPause;
    Button buttonStop;
    Button buttonBillable;
    Button buttonNonBillable;

    TextView textViewCoordinates;
    TextView textViewGpsAccuracy;
    TextView textViewDestination;

    TextView textViewDistanceBillable;
    TextView textViewDistanceNonBillable;

    TrackManager trackManager;

    StateC BillableTrackState;
    StateC NonBillableTrackState;
    TrackType currentTrackType = TRACK_NON_BILLABLE;

    DistanceCounter distanceCounterBillable;
    DistanceCounter distanceCounterNonBillable;

    GPSTracker gpsTracker;

    private final int MARKED_ITEM_COLOR = 0xFF33B5E5;
    private final int ITEMS_COLOR = 0xFF0099CC;

    public FragmentTracking(Context context_){
        context = context_;
        trackManager = ((MainActivity)context).getTrackManager();
    }

    Button activeStartStopButton = null;
    Button activeTrackButton = null;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_tracking, container, false);

        distanceCounterBillable = new DistanceCounter();
        distanceCounterNonBillable = new DistanceCounter();
        textViewDistanceBillable = (TextView) view.findViewById(R.id.textViewKilometersBillable);
        textViewDistanceNonBillable = (TextView) view.findViewById(R.id.textViewKilometersNonBillable);
        textViewCoordinates = (TextView) view.findViewById(R.id.TextViewGPSCoordinates);
        textViewGpsAccuracy = (TextView) view.findViewById(R.id.TextViewGPSAccuracy);
        textViewDestination = (TextView) view.findViewById(R.id.textViewDestination);

        BillableTrackState = new StateC();
        NonBillableTrackState = new StateC();
        BillableTrackState.state = PAUSE;
        NonBillableTrackState.state = PAUSE;

        final Handler secHandler_5 = new Handler();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                textViewDistanceBillable.setText(String.format("%.2f", distanceCounterBillable.UpdateDistance()) + "\nkm");
                textViewDistanceNonBillable.setText(String.format("%.2f",distanceCounterNonBillable.UpdateDistance()) + "\nkm");
                secHandler_5.postDelayed(this, 5000);
            }
        };
        secHandler_5.post(runnable);

        buttonStart = (Button) view.findViewById(R.id.buttonStart);
        buttonStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentTrackType == TRACK_BILLABLE) {
                    BillableTrackState.state = START;
                }else{
                    NonBillableTrackState.state = START;
                }
                TracksStateHandler();
            }
        });
        buttonStop = (Button) view.findViewById(R.id.buttonStop);

        buttonStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentTrackType == TRACK_BILLABLE) {
                    BillableTrackState.state = STOP;
                }else{
                    NonBillableTrackState.state = STOP;
                }
                TracksStateHandler();
            }
        });

        buttonPause = (Button) view.findViewById(R.id.buttonPause);
        buttonPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentTrackType == TRACK_BILLABLE) {
                    BillableTrackState.state = PAUSE;
                }else{
                    NonBillableTrackState.state = PAUSE;
                }
                TracksStateHandler();
            }
        });

        buttonBillable = (Button) view.findViewById(R.id.buttonBillable);
        buttonBillable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentTrackType = TRACK_BILLABLE;
                TracksStateHandler();
            }
        });
        buttonNonBillable = (Button) view.findViewById(R.id.buttonNonBillable);
        buttonNonBillable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentTrackType = TRACK_NON_BILLABLE;
                TracksStateHandler();
            }
        });

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
                    textViewCoordinates.setText(Double.toString(location.getLongitude()) +
                            "\n" +
                            Double.toString(location.getLatitude()));
                    textViewGpsAccuracy.setText("GPS Accuracy\n" + Float.toString(location.getAccuracy()));
                }
            }
        };

        TracksStateHandler();

        return view;
    }
    private void ChangeStartStopPauseButtonColorOnClick(Button button){
        if (activeStartStopButton != null){
            activeStartStopButton.setBackgroundColor(ITEMS_COLOR);
        }
        button.setBackgroundColor(MARKED_ITEM_COLOR);
        activeStartStopButton = button;
    }
    private void ChangeTrackButtonColorOnClick(Button button){
        if (activeTrackButton != null){
            activeTrackButton.setBackgroundColor(ITEMS_COLOR);
        }
        button.setBackgroundColor(MARKED_ITEM_COLOR);
        activeTrackButton = button;
    }

    private void TracksStateHandler (){
        switch (currentTrackType){
            case TRACK_BILLABLE:
                StateHandler(BillableTrackState,NonBillableTrackState,distanceCounterBillable,distanceCounterNonBillable);
                ChangeTrackButtonColorOnClick(buttonBillable);
                break;
            case TRACK_NON_BILLABLE:
            default:
                StateHandler(NonBillableTrackState,BillableTrackState,distanceCounterNonBillable,distanceCounterBillable);
                ChangeTrackButtonColorOnClick(buttonNonBillable);
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
                ChangeStartStopPauseButtonColorOnClick(buttonStart);
                break;
            case STOP:
                activeCounter.ResetCounter();
                ChangeStartStopPauseButtonColorOnClick(buttonStop);
                break;
            case PAUSE:
                activeCounter.StopCountingDistance();
                ChangeStartStopPauseButtonColorOnClick(buttonPause);
                break;
        }
    }

    public void onTrackChosen() {
        Track track = trackManager.getActiveTrack();
        if (track != null)
            textViewDestination.setText(track.TrackString);
    }
}
