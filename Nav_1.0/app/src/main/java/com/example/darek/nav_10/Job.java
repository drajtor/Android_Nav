package com.example.darek.nav_10;

import java.io.Serializable;
import java.util.HashMap;

/**
 * Created by 212449139 on 2/21/2017.
 */

public abstract class Job extends HashMap<String,Object> implements Serializable{

    protected int ID;
    protected int JobType;
    protected String Name;
    protected String Principal;

    Job (){
        super();
//        this.put("ID","kaka");
//        this.put("JobType","");
//        this.put("Name","");
//        this.put("Principal","");
    }

    public abstract void sendStatusUpdate();

    public  int getJobID(){
        return ID;
    }
    public void setJobID(int id){
        ID = id;
//        this.put("ID",id);
    }
    public  int getJobType(){
        return JobType;
    }
    public void setJobType(int jobType){
        JobType = jobType;
    }
    public String getJobName(){
        return Name;
    }
    public void setJobName(String name){
        Name = name;
    }
    public String getJobPrincipal(){
        return Principal;
    }
    public void setJobPrincipal(String name){
        Principal = name;
    }

}
