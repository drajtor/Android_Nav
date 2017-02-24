package com.example.darek.nav_10;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;
import static com.example.darek.nav_10.TrackHandler.TrackType.TRACK_BILLABLE;
import static com.example.darek.nav_10.TrackHandler.TrackType.TRACK_NON_BILLABLE;

/**
 * Created by 212449139 on 2/3/2017.
 */
public class FragmentTracking extends Fragment {

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

    JobManager jobManager;

    TrackHandler trackHandler;
    TrackHandler trackHandlerBackup;

    onJobListUpdatedListener mCallback;

    private static final int MARKED_ITEM_COLOR = 0xFF33B5E5;
    private static final int ITEMS_COLOR = 0xFF0099CC;

    private final int TRACK_SUMMARY_REQUEST_CODE = 200;

    public FragmentTracking(Context context){
        this.context = context;
        trackHandler = new TrackHandler(context);
        jobManager = ((MainActivity)context).getJobManager();
    }

    Button activeStartStopButton = null;
    Button activeTrackButton = null;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_tracking, container, false);

        textViewDistanceBillable = (TextView) view.findViewById(R.id.textViewKilometersBillable);
        textViewDistanceNonBillable = (TextView) view.findViewById(R.id.textViewKilometersNonBillable);
        textViewCoordinates = (TextView) view.findViewById(R.id.TextViewGPSCoordinates);
        textViewGpsAccuracy = (TextView) view.findViewById(R.id.TextViewGPSAccuracy);
        textViewDestination = (TextView) view.findViewById(R.id.textViewDestination);

        final Handler secHandler_5 = new Handler();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                TrackJob trackJob;
                trackJob = (TrackJob)jobManager.getActiveJob();
                if (trackJob != null){
                    float distance;
                    distance = trackHandler.ProcessDistanceUpdate();
                    if (trackHandler.getCurrentTrackType() == TRACK_BILLABLE) {
                        trackJob.setDistance(distance, TrackJob.Billability.BILLABLE);
                    }else{
                        trackJob.setDistance(distance, TrackJob.Billability.NON_BILLABLE);
                    }
                    distance = trackHandler.getDistance(TrackHandler.TrackType.TRACK_BILLABLE);
                    textViewDistanceBillable.setText(String.format("%.2f", distance) + "\nkm");
                    distance = trackHandler.getDistance(TrackHandler.TrackType.TRACK_NON_BILLABLE);
                    textViewDistanceNonBillable.setText(String.format("%.2f",distance) + "\nkm");
                }
                secHandler_5.postDelayed(this, 5000);
            }
        };
        secHandler_5.post(runnable);

        buttonBillable = (Button) view.findViewById(R.id.buttonBillable);
        buttonBillable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TrackTypeButtonClicked(TrackHandler.TrackType.TRACK_BILLABLE);
            }
        });
        buttonNonBillable = (Button) view.findViewById(R.id.buttonNonBillable);
        buttonNonBillable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TrackTypeButtonClicked(TrackHandler.TrackType.TRACK_NON_BILLABLE);
            }
        });

        buttonStart = (Button) view.findViewById(R.id.buttonStart);
        buttonStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StateButtonClicked(TrackHandler.State.START);
            }
        });
        buttonPause = (Button) view.findViewById(R.id.buttonPause);
        buttonPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StateButtonClicked(TrackHandler.State.PAUSE);
            }
        });
        buttonStop = (Button) view.findViewById(R.id.buttonStop);
        buttonStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                trackHandlerBackup = new TrackHandler(trackHandler);
                StateButtonClicked(TrackHandler.State.STOP);

                TrackJob trackJob = (TrackJob)jobManager.getActiveJob();
                Intent intent = new Intent(context,TrackSummaryActivity.class);
                intent.putExtra("trackJob", trackJob);
                startActivityForResult(intent,TRACK_SUMMARY_REQUEST_CODE);
            }
        });

        textViewDestination.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TrackJob currentTrackJob;
                try{
                    currentTrackJob = (TrackJob)jobManager.getActiveJob();
                }
                catch(ClassCastException e){
                    throw new ClassCastException("Fragment Tracking error while casting Job to TrackJob");
                }
                if (currentTrackJob != null){
                    Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                            Uri.parse("google.navigation:q=" + currentTrackJob.Number + " " + currentTrackJob.Street + " " + currentTrackJob.City));
                    startActivity(intent);
                }
            }
        });

        TrackTypeButtonClicked(trackHandler.getCurrentTrackType());
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == TRACK_SUMMARY_REQUEST_CODE){
            switch (resultCode){
                case RESULT_OK:
                    Toast.makeText(context,"Raport sent",Toast.LENGTH_SHORT).show();
                    jobManager.remove(jobManager.getActiveJob());
                    mCallback = (onJobListUpdatedListener) context;
                    mCallback.onJobListUpdated();
                    break;
                case RESULT_CANCELED:
                    Toast.makeText(context,"Cancelled",Toast.LENGTH_SHORT).show();
                    if (trackHandlerBackup != null)
                        trackHandler = trackHandlerBackup;
                    if (activeTrackButton == buttonBillable)
                        TrackTypeButtonClicked(TRACK_BILLABLE);
                    else
                        TrackTypeButtonClicked(TRACK_NON_BILLABLE);
                    break;
            }
        }
    }

    private void ChangeStateButtonColor(TrackHandler.State state){
        switch (state){
            case START:
                ChangeActiveStateButtonColor(buttonStart);
                break;
            case STOP:
                ChangeActiveStateButtonColor(buttonStop);
                break;
            case PAUSE:
                ChangeActiveStateButtonColor(buttonPause);
                break;
        }
    }

    private void TrackTypeButtonClicked(TrackHandler.TrackType trackType){
        switch (trackType){
            case TRACK_BILLABLE:
                ChangeTrackButtonColorOnClick(buttonBillable);
                break;
            case TRACK_NON_BILLABLE:
            default:
                ChangeTrackButtonColorOnClick(buttonNonBillable);
                break;
        }
        ChangeStateButtonColor(trackHandler.getTrackState(trackType));
    }

    private void StateButtonClicked(TrackHandler.State state){
        ChangeStateButtonColor(state);
        if (activeTrackButton == buttonBillable){
            trackHandler.setCurrentTrackType (TRACK_BILLABLE);
        }else if(activeTrackButton == buttonNonBillable){
            trackHandler.setCurrentTrackType (TRACK_NON_BILLABLE);
        }
        trackHandler.setActiveTrackState(state);
        trackHandler.TracksStateHandler();
    }

    private void ChangeActiveStateButtonColor(Button button){
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

    public void onTrackChosen() {
        Job job= jobManager.getActiveJob();
        if (job != null)
            textViewDestination.setText(job.getJobName());
    }
}
