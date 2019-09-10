package com.example.ruben.drealitym.Notifications;

import android.app.Notification;
import android.app.job.JobParameters;
import android.app.job.JobService;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import static com.example.ruben.drealitym.Notifications.DrealitymApplication.CHANNEL_1_ID;

public class ScheduleNotificationsService extends JobService {

    //TODO: manifest
    //TODO: help: https://code.tutsplus.com/tutorials/using-the-jobscheduler-api-on-android-lollipop--cms-23562
    NotificationManagerCompat notificationManger;


    @Override
    public boolean onStartJob(JobParameters jobParameters) {
        //notify

        notificationManger = NotificationManagerCompat.from(getApplicationContext());

        //build the notification
        Notification notification = new NotificationCompat.Builder(getApplicationContext(), CHANNEL_1_ID)
                .setContentTitle("Title")
                .setContentText("Text")
                .setPriority(NotificationCompat.DEFAULT_ALL)
                .build();

        notificationManger.notify(1, notification);

        return false;
    }

    @Override
    public boolean onStopJob(JobParameters jobParameters) {
        //schedule the next notification
        return false;
    }
}
