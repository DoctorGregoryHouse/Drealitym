package com.example.ruben.drealitym.Notifications;

import android.app.Notification;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.ruben.drealitym.R;

import static com.example.ruben.drealitym.Notifications.DrealitymApplication.CHANNEL_1_ID;

public class ScheduleNotificationsService extends JobService {

    private static final String LOG_TAG = "NotificationJob";
    //TODO: manifest
    //TODO: help: https://code.tutsplus.com/tutorials/using-the-jobscheduler-api-on-android-lollipop--cms-23562
    NotificationManagerCompat notificationManger;


    @Override
    public boolean onStartJob(JobParameters jobParameters) {
        Log.d(LOG_TAG,"Job started...");
        //notify
        notificationManger = NotificationManagerCompat.from(getApplicationContext());
        //build the notification
        Notification notification = new NotificationCompat.Builder(getApplicationContext(), CHANNEL_1_ID)
                .setContentTitle("Title")
                .setContentText("Text")
                .setSmallIcon(R.drawable.ic_notify)
                .setPriority(NotificationCompat.DEFAULT_ALL)
                .build();
        notificationManger.notify(1, notification);
        return false;
    }


    @Override
    public boolean onStopJob(JobParameters jobParameters) {
        NotificationScheduler ns = new NotificationScheduler(getBaseContext());
        ns.scheduleNotification();
        return false;
    }
}
