package com.example.ruben.drealitym.Data;


import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "reality_check_table" )
public class RealityCheckEntry {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private int day;
    private int startHour;
    private int startMinute;
    private int stopHour;
    private int stopMinute;
    private int interval;
    private int notification;

    public RealityCheckEntry(int day, int startHour, int startMinute, int stopHour, int stopMinute, int interval, int notification) {
        this.day = day;
        this.startHour = startHour;
        this.startMinute = startMinute;
        this.stopHour = stopHour;
        this.stopMinute = stopMinute;
        this.interval = interval;
        this.notification = notification;
    }

    public void setId(int id){
        this.id = id;}

    public int getId(){
        return id;}

    public int getStartHour() {
        return startHour;}

    public int getStartMinute(){
        return startMinute;
    }
    public int getStopMinute(){
        return stopMinute;
    }

    public int getStopHour() {
        return stopHour;
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
