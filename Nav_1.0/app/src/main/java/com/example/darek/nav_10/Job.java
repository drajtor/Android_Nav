package com.example.darek.nav_10;

/**
 * Created by 212449139 on 2/21/2017.
 */

public interface Job {
    public int getJobID();
    public String getJobName();
    public String getJobPrincipal(); //zleceniodawaca :)
    public void EnableJob();
    public void DisableJob();
    public void sendJobSummary();
    public void sendStatusUpdate();
}
