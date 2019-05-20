package com.example.ruben.drealitym.Data;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "reminder_table") //this statement creates a table
public class ReminderEntry {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String name;
    private String days;
    private int interval;
    private int startHour;
    private int startMin;
    private int stopHour;
    private int stopMin;


    public ReminderEntry(String name, String days, int interval, int startHour, int startMin, int stopHour, int stopMin) {
        this.name = name;
        this.days = days;
        this.interval = interval;
        this.startHour = startHour;
        this.startMin = startMin;
        this.stopHour = stopHour;
        this.stopMin = stopMin;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDays() {
        return days;
    }

    public int getInterval() {
        return interval;
    }

    public int getStartHour() {
        return startHour;
    }

    public int getStartMin() {
        return startMin;
    }

    public int getStopHour() {
        return stopHour;
    }

    public int getStopMin() {
        return stopMin;
    }
}
