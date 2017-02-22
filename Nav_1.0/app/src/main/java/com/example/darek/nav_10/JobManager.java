package com.example.darek.nav_10;

import java.util.ArrayList;

/**
 * Created by 212449139 on 2/16/2017.
 */

public class JobManager extends ArrayList<Job>{

    private Job ActiveJob;

    public JobManager(){
        super();
        updateJobList();
    }

    public void setActiveJob(Job job){
        ActiveJob = job;
    }

    public Job getActiveJob(){
        return ActiveJob;
    }

    private void updateJobList (){
        add(new TrackJob("Katowice", "Jordana", "1"));
        add(new TrackJob("Warszawa","Krakowska","110"));
        add(new TrackJob("Gliwice","Zwycięstwa","2"));
    }
}
