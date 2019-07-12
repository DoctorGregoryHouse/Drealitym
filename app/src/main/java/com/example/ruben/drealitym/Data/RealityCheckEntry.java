package com.example.ruben.drealitym.Data;


import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "reality_check_table" )
public class RealityCheckEntry {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private int day;
    private int startTime;
    private int stopTime;
    private int interval;
    private int notification;

    public RealityCheckEntry(int id, int day, int startTime, int stopTime, int interval, int notification) {

        this.day = day;
        this.startTime = startTime;
        this.stopTime = stopTime;
        this.interval = interval;
        this.notification = notification;
    }

    public void setId(int id){
        this.id = id;}

    public int getId(){
        return id;}

    public int getStartTime() {
        return startTime;}

    public int getStopTime() {
        return stopTime;
    }

    public int getDay() {
        return day;
    }

    public int getInterval() {
        return interval;
    }

    public int getNotification() {
        return notification;
    }
}
