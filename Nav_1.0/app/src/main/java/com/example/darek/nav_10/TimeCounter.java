package com.example.darek.nav_10;

/**
 * Created by 212449139 on 2/27/2017.
 */

public class TimeCounter {
    int Seconds = 0;
    int Minutes = 0;
    int Hours= 0;

    private final int SECONDS_IN_MINUTE = 60;
    private final int MINUTES_IN_HOUR = 60;

    void OnOneSecondTick (){
        Seconds ++;
        if (Seconds >= SECONDS_IN_MINUTE){
            Seconds = Seconds - SECONDS_IN_MINUTE;
            Minutes++;
        }
        if (Minutes >= MINUTES_IN_HOUR){
            Minutes = Minutes - MINUTES_IN_HOUR;
            Hours++;
        }

    }

    void ResetTimer(){
        Seconds = 0;
        Minutes = 0;
        Hours = 0;
    }
}
