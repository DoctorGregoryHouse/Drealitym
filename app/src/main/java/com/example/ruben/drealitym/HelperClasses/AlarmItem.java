package com.example.ruben.drealitym.HelperClasses;

import java.util.Calendar;

public class AlarmItem {

    private Calendar mCalendar;
    private String mName;

    public AlarmItem(String name, Calendar c){

        mName = name;
        mCalendar = c;
    }
}
