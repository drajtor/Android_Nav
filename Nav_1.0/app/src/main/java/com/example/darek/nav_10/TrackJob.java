package com.example.darek.nav_10;

/**
 * Created by 212449139 on 2/16/2017.
 */

public class TrackJob implements Job{

    public enum Billability {BILLABLE, NON_BILLABLE};

    String Alias;
    String City;
    String Postal;
    String Street;
    String Number;
    String TrackString;

    private float DistanceBillable;
    private float DistanceNonBillable;
    private int TimeBillable;
    private int TimeNonBillable;

    public TrackJob(){

    }
    public TrackJob(String alias){
        Alias = alias;
        TrackString = alias;
    }
    public TrackJob(String city, String street, String number){
        City = city;
        Street = street;
        Number = number;
        TrackString = city + ", " + street + " "+ number;
    }

    public void setDistance(float distance, Billability billability){
        if (billability == Billability.BILLABLE){
            DistanceBillable = distance;
        }else {
            DistanceNonBillable = distance;
        }
    }

    public void setTimeBillable(int time, Billability billability){
        if (billability == Billability.BILLABLE){
            TimeBillable = time;
        }else {
            TimeNonBillable = time;
        }
    }

    @Override
    public int getJobID() {
        return 0;
    }

    @Override
    public String getJobPrincipal() {
        return null;
    }

    @Override
    public void EnableJob() {

    }

    @Override
    public void DisableJob() {

    }

    @Override
    public void sendJobSummary() {

    }

    @Override
    public void sendStatusUpdate() {

    }

    @Override
    public String getJobName() {
        return TrackString;
    }
}
