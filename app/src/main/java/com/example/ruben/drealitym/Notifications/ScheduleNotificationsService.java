package com.example.ruben.drealitym.Notifications;

import android.app.Notification;
import android.app.job.JobInfo;
import android.app.job.JobParameters;
import android.app.job.JobScheduler;
import android.app.job.JobService;
import android.content.ComponentName;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.ruben.drealitym.Data.DrealitymDatabase;
import com.example.ruben.drealitym.Data.RealityCheckDao;
import com.example.ruben.drealitym.Data.RealityCheckEntry;
import com.example.ruben.drealitym.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static com.example.ruben.drealitym.Notifications.DrealitymApplication.CHANNEL_1_ID;
import static com.example.ruben.drealitym.UiClasses.RealityCheckActivity.GET_NOTIFIED;

public class ScheduleNotificationsService extends JobService {

    private static final String TAG = "NotificationJob";
    //TODO: manifest
    //TODO: help: https://code.tutsplus.com/tutorials/using-the-jobscheduler-api-on-android-lollipop--cms-23562
    NotificationManagerCompat notificationManger;
    DrealitymDatabase db;
    RealityCheckDao realityCheckDao;


    @Override
    public boolean onStartJob(JobParameters jobParameters) {
        Log.d(TAG,"Job started...");
        //notify
        notificationManger = NotificationManagerCompat.from(getApplicationContext());
        //build the notification
        Notification notification = new NotificationCompat.Builder(getApplicationContext(), CHANNEL_1_ID)
                .setContentTitle("Title")
                .setContentText("Text")
                .setSmallIcon(R.drawable.ic_notify)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setVibrate(new long[] { 1000, 1000, 1000, 1000, 1000 })
                .build();
        notificationManger.notify(1, notification);

        db = DrealitymDatabase.getInstance(getApplicationContext());
        realityCheckDao = db.realityCheckDao();

        GetEntryAsyncTask task = new GetEntryAsyncTask(realityCheckDao, getApplicationContext());
        task.execute();


        return false;
    }


    @Override
    public boolean onStopJob(JobParameters jobParameters) {
//        NotificationScheduler ns = new NotificationScheduler(getBaseContext());
//        ns.scheduleNotification();

        return false;
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
            Log.d(TAG,"called doInBackground");
            entries = dao.getStaticDreamList();
            return entries;
        }
        @Override
        protected void onPostExecute(List<RealityCheckEntry> realityCheckEntries) {
            super.onPostExecute(realityCheckEntries);
            Log.d(TAG,"called onPostExecute");
            int time = calculateScheduleTime(realityCheckEntries);
            Log.d(TAG,"scheduled time: " + time);
            if (time == -1) {
                //Toast.makeText(this, "You should add some time", Toast.LENGTH_SHORT).show();
                return;
            }

            ComponentName componentName = new ComponentName(context, ScheduleNotificationsService.class);
            JobInfo info = new JobInfo.Builder(1, componentName)
                    .setPersisted(true) //Job is not lost when rebooting the device
                    .setMinimumLatency(time * 60 * 1000)
                    .build();
            JobScheduler scheduler = (JobScheduler) context.getSystemService(JOB_SCHEDULER_SERVICE);
            int resultcode = scheduler.schedule(info);
            if(resultcode == JobScheduler.RESULT_SUCCESS) {
                Log.d(TAG, "onClick: Job scheduled, resultcode: " + resultcode +"\n scheduled for time: " + time );
            }else{
                Log.d(TAG, "onClick: Job scheduling failed");
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
            Log.d(TAG,"scheduling for the current day...");
            //schedule notificatin for the current day
            //iterate through the intervals and return the first which is greater than the current time
            for (int i = 0; i < intervals.size(); i++) {
                if (current_day_time < intervals.get(i)) {
                    Log.d(TAG,"scheduled interval: " + intervals.get(i));
                    return (intervals.get(i) - current_day_time);
                }
            }
        }
        //schedule notification for another day
        //this block of code only gets executed if its not the current day
        Log.d(TAG,"scheduling another day than today...");
        int prevent_loop = 0;
        int day_of_week = current_day;
        while (notification_values[day_of_week] != 1 || prevent_loop == 14) {
            iterating_day++;
            prevent_loop++;
            day_of_week = (day_of_week +1) % 7;

        }
        if (prevent_loop == 14) {
            //if no schedule time can be calculated due to the settings return -1
            return -1;
        } else {
            int counted_day = iterating_day - current_day;
            Log.d(TAG,"counted days: " + counted_day);
            intervals = prepareIntervalsForCalculation(day_of_week, entries);
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
        Log.d(TAG, "sucessfully created notification_values");
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
        Log.d(TAG, "Returned intervals for day: " + day + " , first interval: " + intervals.get(0));
        return intervals;
    }




}
