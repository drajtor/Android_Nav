package com.example.darek.nav_10;

/**
 * Created by 212449139 on 2/23/2017.
 */

public class TrackRaport extends TrackJob implements JobRaport {

    TrackRaport(){}
    TrackRaport(TrackJob trackJob){
        Alias = trackJob.Alias ;
        City = trackJob.City;
        Postal = trackJob.Postal;
        Street = trackJob.Street;
        Number = trackJob.Number;
        TrackString = trackJob.TrackString;

        DistanceBillable = trackJob.DistanceBillable;
        DistanceNonBillable = trackJob.DistanceNonBillable;
        TimeBillable = trackJob.TimeBillable;
        TimeNonBillable = trackJob.TimeNonBillable;
    }

    @Override
    public void FillRaportWithJobData() {

    }

    @Override
    public void sendJobSummaryRaport() {

    }

}
