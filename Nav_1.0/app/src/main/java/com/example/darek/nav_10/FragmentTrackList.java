package com.example.darek.nav_10;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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

import java.util.List;

/**
 * Created by 212449139 on 2/3/2017.
 */
public class FragmentTrackList extends Fragment {

    private ListView listView;
    private TrackListAdapter trackListAdapter;
    private ListElementViewHolder currentTrack = null;
    private TrackManager trackManager = new TrackManager();

    private final int MARKED_ITEM_COLOR = 0xFF33B5E5;
    private final int ITEMS_COLOR = 0xFF0099CC;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_track_list,container,false);

        listView = (ListView) view.findViewById(R.id.ListView_TrackList);
        trackListAdapter = new TrackListAdapter(listView.getContext(),R.layout.track_list_item,trackManager);
        listView.setAdapter(trackListAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String message = trackManager.get(position).TrackString;
                Toast.makeText(listView.getContext(),message,Toast.LENGTH_LONG).show();
            }
        });
        return view;
    }

    private class TrackListAdapter extends ArrayAdapter<Track> {

        int layout;
        public TrackListAdapter(Context context, int resource, List<Track> objects) {
            super(context, resource, objects);
            layout = resource;
        }

        @NonNull
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (null == convertView){
                LayoutInflater inflater = LayoutInflater.from(listView.getContext());
                convertView = inflater.inflate(layout,parent,false);
                final ListElementViewHolder listElementViewHolder = new ListElementViewHolder((ImageView) convertView.findViewById(R.id.imageView_listItem),
                                                                                                (TextView) convertView.findViewById(R.id.textView_listItem),
                                                                                                (Button) convertView.findViewById(R.id.button_listItem),
                                                                                                trackManager.get(position));
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
        Track track;

        private ListElementViewHolder (ImageView imageView, TextView textView, Button button_, Track track_){
            image = imageView;
            text = textView;
            text.setText(track_.TrackString);
            track = track_;
            button = button_;
        }
    }

    private void ItemClicked (ListElementViewHolder listElementViewHolder){
        trackManager.setActiveTrack(listElementViewHolder.track);
        if (null != currentTrack){
            setListElementColor(currentTrack,ITEMS_COLOR);
        }
        currentTrack = listElementViewHolder;
        setListElementColor(currentTrack,MARKED_ITEM_COLOR);
    }

    private void setListElementColor (ListElementViewHolder listElementViewHolder  ,int color){
        listElementViewHolder.text.setBackgroundColor(color);
        listElementViewHolder.image.setBackgroundColor(color);
        listElementViewHolder.button.setBackgroundColor(color);
    }
}
