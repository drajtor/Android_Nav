package com.example.darek.nav_10;

/**
 * Created by 212449139 on 2/16/2017.
 */

public class Track {

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
}
