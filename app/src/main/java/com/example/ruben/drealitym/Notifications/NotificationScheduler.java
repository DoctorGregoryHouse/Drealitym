package com.example.ruben.drealitym.Notifications;

import android.app.Application;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.util.Log;

import com.example.ruben.drealitym.Data.DrealitymDatabase;
import com.example.ruben.drealitym.Data.DrealitymRepository;
import com.example.ruben.drealitym.Data.RealityCheckDao;
import com.example.ruben.drealitym.Data.RealityCheckEntry;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static com.example.ruben.drealitym.UiClasses.RealityCheckActivity.GET_NOTIFIED;

public class NotificationScheduler extends Application {

    private static final String LOG_TAG = "NotificationScheduler";

    private DrealitymRepository repository;
    private RealityCheckDao realityCheckDao;
    private List<RealityCheckEntry> entries;


    public NotificationScheduler(){
        repository = new DrealitymRepository(this);
        entries = realityCheckDao.getStaticDreamList();
    }

    public  void scheduleNotification() {
        int time = calculateScheduleTime();
        //TODO: Does this Toast show when the user does not but the algorithm schedules an alarm ?
        if (time == -1){
            //Toast.makeText(this, "You should add some time", Toast.LENGTH_SHORT).show();
            return;
        }
        Log.d(LOG_TAG,"Calculated time for next notificaiton: " + time);
        JobScheduler mJobScheduler = (JobScheduler) getSystemService(Context.JOB_SCHEDULER_SERVICE);
        JobInfo.Builder builder = new JobInfo.Builder(1, new ComponentName(getPackageName(), ScheduleNotificationsService.class.getName()));
        builder.setMinimumLatency(time * 60 * 1000);

        if (mJobScheduler.schedule(builder.build()) <= 0) {
            //If something goes wrong
            Log.d(LOG_TAG, "Jobscheduler could not schedule notifciation");
        }
    }


    private  int calculateScheduleTime() {
        Calendar calendar = Calendar.getInstance();
        //sunday=1, monday=2, tuesday=3 ... saturday=7
        int current_day = calendar.get(Calendar.DAY_OF_WEEK );
        current_day -= 1 ;
        int current_hour = calendar.get(Calendar.HOUR_OF_DAY);
        int current_minute = calendar.get(Calendar.MINUTE);
        int current_day_time = (current_hour * 60) + current_minute;
        RealityCheckEntry entry;

        int[][] notification_values = new int[7][4];
        for (int i = 0; i < 7; i++) {
            entry = entries.get(i);
            notification_values[i][0] = entry.getNotification();
        }

        int iterating_day = current_day;
        List<Integer> intervals = prepareIntervalsForCalculation(iterating_day);
        if (notification_values[iterating_day][0] == GET_NOTIFIED) {
            //notifications are activated at this day
            //TODO: create some log messages
            for (int i = 0; i < intervals.size(); i++) {
                if (current_day_time < intervals.get(i)) {
                    return ( intervals.get(i) - current_day_time);
                }
            }
        }
        //TODO:maybe this part of the code will never get called
        int prevent_loop = 0;
        while (notification_values[iterating_day][0] != 1 || prevent_loop == 10) {
            iterating_day++;
            prevent_loop++;
        }
        if (prevent_loop == 10) {
            return -1;
        } else {
            int counted_day = iterating_day - current_day;
            intervals = prepareIntervalsForCalculation(iterating_day);
            return (counted_day * 24 * 60) + intervals.get(0) + 1440 - current_day_time;
        }

    }

    private  List<Integer> prepareIntervalsForCalculation(int day) {

        List<Integer> intervals = new ArrayList<Integer>();
        RealityCheckEntry currentEntry = entries.get(day);
        int startTime = (currentEntry.getStartHour() * 60) + currentEntry.getStartMinute();
        int stopTime = (currentEntry.getStopHour() * 60) + currentEntry.getStopMinute();
        int intervalTime = currentEntry.getInterval() * 60; //the minimum interval-time is one hour
        boolean check = true;
        int counter = startTime;

        intervals.add(startTime);
        while (check) {
            counter = counter + intervalTime;
            if (counter < stopTime) {
                intervals.add(counter);
            } else {
                check = false;
            }
        }
        Log.d(LOG_TAG,"Returned intervals for day: " + day +" , first interval: " + intervals.get(0));
        return intervals;
    }



}
