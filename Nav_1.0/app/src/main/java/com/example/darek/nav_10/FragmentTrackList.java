package com.example.darek.nav_10;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 212449139 on 2/3/2017.
 */
public class FragmentTrackList extends Fragment {

    private ListView listView;
    private ArrayList<String> taskList = new ArrayList<String>();
    private TrackListAdapter trackListAdapter;
    private ListElementViewHolder currentTrack = null;

    private final int MARKED_ITEM_COLOR = 0xFF33B5E5;
    private final int ITEMS_COLOR = 0xFF0099CC;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_track_list,container,false);

        taskList.add("Katowice, Centrum");
        taskList.add("Gliwice, Zwycięstwa 1");
        taskList.add("Warszawa, Krakowska 110");

        listView = (ListView) view.findViewById(R.id.ListView_TrackList);
//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(listView.getContext(),R.layout.support_simple_spinner_dropdown_item,taskList);
        trackListAdapter = new TrackListAdapter(listView.getContext(),R.layout.track_list_item,taskList);
        listView.setAdapter(trackListAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String message = taskList.get(position);
                Toast.makeText(listView.getContext(),message,Toast.LENGTH_LONG).show();
            }
        });

        return view;
    }

    private class TrackListAdapter extends ArrayAdapter<String> {

        int layout;
        public TrackListAdapter(Context context, int resource, List<String> objects) {
            super(context, resource, objects);
            layout = resource;
        }

        @NonNull
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (null == convertView){
                LayoutInflater inflater = LayoutInflater.from(listView.getContext());
                convertView = inflater.inflate(layout,parent,false);
                final ListElementViewHolder listElementViewHolder = new ListElementViewHolder();
                listElementViewHolder.image = (ImageView) convertView.findViewById(R.id.imageView_listItem);
                listElementViewHolder.text = (TextView) convertView.findViewById(R.id.textView_listItem);
                listElementViewHolder.text.setText(taskList.get(position));
                listElementViewHolder.button = (Button) convertView.findViewById(R.id.button_listItem);

                listElementViewHolder.button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ItemClicked(listElementViewHolder);
                    }
                });
                convertView.setTag(listElementViewHolder);
            }else{
//                ListElementViewHolder viewHolder = (ListElementViewHolder)convertView.getTag();
//                viewHolder.text.setText(taskList.get(position));

            }

            return convertView;
        }
    }
    private class ListElementViewHolder{
        ImageView image;
        TextView text;
        Button button;
    }

    private void ItemClicked (ListElementViewHolder listElementViewHolder){

        if (null != currentTrack){
            currentTrack.text.setBackgroundColor(ITEMS_COLOR);
            currentTrack.image.setBackgroundColor(ITEMS_COLOR);
            currentTrack.button.setBackgroundColor(ITEMS_COLOR);
        }
        currentTrack = listElementViewHolder;
        listElementViewHolder.text.setBackgroundColor(MARKED_ITEM_COLOR);
        listElementViewHolder.image.setBackgroundColor(MARKED_ITEM_COLOR);
        listElementViewHolder.button.setBackgroundColor(MARKED_ITEM_COLOR);
    }
}
