package com.example.darek.nav_10;

/**
 * Created by 212449139 on 2/16/2017.
 */

public class TrackJob extends Job{

    public enum Billability {BILLABLE, NON_BILLABLE};

    String Alias;
    String City;
    String Postal;
    String Street;
    String Number;
    String TrackString;

    protected float DistanceBillable;
    protected float DistanceNonBillable;
    protected int TimeBillable;
    protected int TimeNonBillable;

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

    public float getDistanceBillable() {
        return DistanceBillable;
    }

    public float getDistanceNonBillable() {
        return DistanceNonBillable;
    }

    public int getTimeBillable() {
        return TimeBillable;
    }

    public int getTimeNonBillable() {
        return TimeNonBillable;
    }

    public String getJobName() {
        return TrackString;
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
    public void sendStatusUpdate() {

    }

}
