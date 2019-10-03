package com.example.ruben.drealitym.Notifications;

import android.app.Application;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.example.ruben.drealitym.Data.DrealitymDatabase;
import com.example.ruben.drealitym.Data.DrealitymRepository;
import com.example.ruben.drealitym.Data.RealityCheckDao;
import com.example.ruben.drealitym.Data.RealityCheckEntry;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static com.example.ruben.drealitym.UiClasses.RealityCheckActivity.GET_NOTIFIED;

public class NotificationScheduler {

    private static final String LOG_TAG = "NotificationScheduler";

    private DrealitymDatabase db;
    private RealityCheckDao realityCheckDao;
    private List<RealityCheckEntry> entries;
    private Context context;


    public NotificationScheduler(Context context) {
        this.context = context;
        DrealitymDatabase database = DrealitymDatabase.getInstance(context);
        realityCheckDao = database.realityCheckDao();

    }

    public void scheduleNotification(){
        GetEntryAsyncTask task = new GetEntryAsyncTask(realityCheckDao, context);
        task.execute();
    }

    private static class GetEntryAsyncTask extends AsyncTask<Void, Void, List<RealityCheckEntry>> {
        private RealityCheckDao dao;
        private List<RealityCheckEntry> entries;
        private Context context;

        private GetEntryAsyncTask(RealityCheckDao dao, Context context) {
            this.dao = dao;
            this.context = context;
        }
        @Override
        protected List<RealityCheckEntry> doInBackground(Void... voids) {
            Log.d(LOG_TAG,"called doInBackground");
            entries = dao.getStaticDreamList();
            return entries;
        }
        @Override
        protected void onPostExecute(List<RealityCheckEntry> realityCheckEntries) {
            super.onPostExecute(realityCheckEntries);
            Log.d(LOG_TAG,"called onPostExecute");
            int time = calculateScheduleTime(realityCheckEntries);
            Log.d(LOG_TAG,"scheduled time: " + time);
            if (time == -1) {
                //Toast.makeText(this, "You should add some time", Toast.LENGTH_SHORT).show();
                return;
            }
            JobScheduler mJobScheduler = (JobScheduler) context.getSystemService(Context.JOB_SCHEDULER_SERVICE);
            JobInfo.Builder builder = new JobInfo.Builder(1, new ComponentName(context.getPackageName(), ScheduleNotificationsService.class.getName()));
            //builder.setMinimumLatency(time * 60 * 1000);
            builder.setMinimumLatency(19);

            if (mJobScheduler.schedule(builder.build()) <= 0) {
                //If something goes wrong
                //TODO: handle this
                //TODO: tell the jobscheduler to finish the job after executing
            }

        }
    }

    private static int calculateScheduleTime(List<RealityCheckEntry> entries) {
        Calendar calendar = Calendar.getInstance();

        int current_day = calendar.get(Calendar.DAY_OF_WEEK);
        current_day -= 1;

        int current_hour = calendar.get(Calendar.HOUR_OF_DAY);
        int current_minute = calendar.get(Calendar.MINUTE);
        int current_day_time = (current_hour * 60) + current_minute;
        int iterating_day = current_day;

        int[] notification_values = getNotificationValues(entries);
        List<Integer> intervals = prepareIntervalsForCalculation(iterating_day, entries);

        if (notification_values[iterating_day] == GET_NOTIFIED) {
            Log.d(LOG_TAG,"scheduling for the current day...");
            //schedule notificatin for the current day
            //iterate through the intervals and return the first which is greater than the current time
            for (int i = 0; i < intervals.size(); i++) {
                if (current_day_time < intervals.get(i)) {
                    Log.d(LOG_TAG,"scheduled interval: " + intervals.get(i));
                    return (intervals.get(i) - current_day_time);
                }
            }
        }
        //schedule notification for another day
        //this block of code only gets executed if its not the current day
        Log.d(LOG_TAG,"scheduling another day than today...");
        int prevent_loop = 0;
        while (notification_values[iterating_day] != 1 || prevent_loop == 10) {
            iterating_day++;
            prevent_loop++;
        }
        if (prevent_loop == 10) {
            //if no schedule time can be calculated due to the settings return -1
            return -1;
        } else {
            int counted_day = iterating_day - current_day;
            Log.d(LOG_TAG,"counted days: " + counted_day);
            intervals = prepareIntervalsForCalculation(iterating_day, entries);
            return (counted_day * 24 * 60) + intervals.get(0) + 1440 - current_day_time;
        }

    }

    private static int[] getNotificationValues(List<RealityCheckEntry> entries) {
        int[] notification_values = new int[7];
        RealityCheckEntry entry;
        for (int i = 0; i < 7; i++) {
            entry = entries.get(i);
            notification_values[i] = entry.getNotification();
        }
        Log.d(LOG_TAG, "sucessfully created notification_values");
        return notification_values;
    }

    private static List<Integer> prepareIntervalsForCalculation(int day, List<RealityCheckEntry> entries) {
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
        Log.d(LOG_TAG, "Returned intervals for day: " + day + " , first interval: " + intervals.get(0));
        return intervals;
    }


}