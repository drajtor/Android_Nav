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
        Job newJob;

        newJob = new TrackJob("Katowice", "Jordana", "1");
        newJob.setJobID(1);
        newJob.setJobPrincipal("Some hospital");
        newJob.setJobType(534);
        add(newJob);

        newJob = new TrackJob("Warszawa","Krakowska","110");
        newJob.setJobID(2);
        newJob.setJobPrincipal("Some hospital");
        newJob.setJobType(534);
        add(newJob);

        newJob = new TrackJob("Gliwice","Zwycięstwa","2");
        newJob.setJobID(3);
        newJob.setJobPrincipal("Some hospital");
        newJob.setJobType(534);
        add(newJob);
    }
}
