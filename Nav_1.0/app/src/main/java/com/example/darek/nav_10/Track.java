package com.example.darek.nav_10;

/**
 * Created by 212449139 on 2/16/2017.
 */

public class Track implements Job{

    String Alias;
    String City;
    String Postal;
    String Street;
    String Number;
    String TrackString;

    public Track (){

    }
    public Track (String alias){
        Alias = alias;
        TrackString = alias;
    }
    public Track (String city, String street, String number){
        City = city;
        Street = street;
        Number = number;
        TrackString = city + ", " + street + " "+ number;
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
