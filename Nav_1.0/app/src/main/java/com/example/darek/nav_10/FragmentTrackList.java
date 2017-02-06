package com.example.darek.nav_10;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

/**
 * Created by 212449139 on 2/3/2017.
 */
public class FragmentTrackList extends Fragment {

    ListView listView;
    String taskList[]={"Katowice, Centrum", "Gliwice, Zwycięstwa 1", "Warszawa, Krakowska 110"};


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_track_list,container,false);

        listView = (ListView) view.findViewById(R.id.ListView_TrackList);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(listView.getContext(),R.layout.support_simple_spinner_dropdown_item,taskList);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String message = taskList[position];
                Toast.makeText(listView.getContext(),message,Toast.LENGTH_LONG).show();
            }
        });

        return view;
    }
}
