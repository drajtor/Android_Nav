package com.example.darek.nav_10;

import java.util.ArrayList;

/**
 * Created by 212449139 on 2/16/2017.
 */

public class TrackManager extends ArrayList<Track>{

    private Track ActiveTrack;

    public TrackManager (){
        super();
        add(new Track("Katowice", "Jordana", "1"));
        add(new Track("Warszawa","Krakowska","110"));
        add(new Track("Gliwice","Zwycięstwa","2"));
    }

    public void setActiveTrack(Track track){
        ActiveTrack = track;
    }

    public Track GetActiveTrack (){
        return ActiveTrack;
    }
}
