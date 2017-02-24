package com.example.darek.nav_10;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TrackSummaryActivity extends AppCompatActivity {

    Button buttonOK;
    Button buttonCancel;

    ListView listView;
    ListAdapter listAdapter;

    TrackRaport trackRaport;

    ArrayList<ListElement> raportList = new ArrayList<ListElement>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_track_summary);

        raportList.add(new ListElement("ID",""));
        raportList.add(new ListElement("JobType",""));
        raportList.add(new ListElement("Principal",""));
        raportList.add(new ListElement("Name",""));
        raportList.add(new ListElement("Start",""));
        raportList.add(new ListElement("Finish",""));
        raportList.add(new ListElement("Distance",""));
        raportList.add(new ListElement("Time",""));


        Intent intent= getIntent();
        intent.getSerializableExtra("trackJob");

        TrackJob trackJob =  (TrackJob)(intent.getSerializableExtra("trackJob"));
        trackRaport = new TrackRaport(trackJob);

        listView = (ListView) findViewById(R.id.ListView_JobRaportList);
        listAdapter = new ListAdapter(getApplicationContext(),R.layout.job_raport_list_item,raportList);
        listView.setAdapter(listAdapter);

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

    private class ListAdapter extends ArrayAdapter<ListElement> {

        int resource;

        public ListAdapter(Context context, int resource, List<ListElement> objects) {
            super(context, resource, objects);
            this.resource = resource;
        }

        @NonNull
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null){
                LayoutInflater inflater = LayoutInflater.from(listView.getContext());
                convertView = inflater.inflate(resource,parent,false);

                ListElementViewHolder listElementViewHolder = new ListElementViewHolder((TextView) convertView.findViewById(R.id.textView_JobRaportItem_Name),
                                                                                        (EditText) convertView.findViewById(R.id.editText_JobRaportItem_Value),
                                                                                        raportList.get(position));
                convertView.setTag(listElementViewHolder);
            }else{

            }
            return convertView;
        }
    }

    private class ListElementViewHolder {
        TextView ParameterName;
        EditText Parameter;
        ListElement listElement;

        ListElementViewHolder (TextView textView, EditText editText,ListElement listElement){
            this.listElement=listElement;
            this.ParameterName = textView;
            this.ParameterName.setText(listElement.ParameterName);
            this.Parameter = editText;
            this.Parameter.setText(listElement.Parameter);

        }
    }

    private class ListElement {
        String ParameterName;
        String Parameter;

        ListElement(String parameterName, String parameter){
            this.Parameter = parameter;
            this.ParameterName = parameterName;
        }
    }

    private void FillSummaryFields (){

    }
}
