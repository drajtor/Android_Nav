package com.example.darek.nav_10;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class TrackSummaryActivity extends AppCompatActivity {

    Button buttonOK;
    Button buttonCancel;

    TrackRaport trackRaport;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_track_summary);

        Intent intent= getIntent();
        intent.getSerializableExtra("trackJob");

        TrackJob trackJob =  (TrackJob)(intent.getSerializableExtra("trackJob"));
        trackRaport = new TrackRaport(trackJob);

        buttonOK = (Button) findViewById(R.id.button_AcceptTrackSummary);
        buttonOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                trackRaport.sendJobSummaryRaport();
                setResult(RESULT_OK);
                finish();
            }
        });

        buttonCancel = (Button) findViewById(R.id.button_cancelTrackSummary);
        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_CANCELED);
                finish();
            }
        });

    }

    private void FillSummaryFields (){

    }
}
