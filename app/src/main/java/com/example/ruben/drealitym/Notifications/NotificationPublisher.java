package com.example.ruben.drealitym.Notifications;

import android.app.Notification;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.ruben.drealitym.R;

import static com.example.ruben.drealitym.Notifications.DrealitymApplication.CHANNEL_1_ID;

public class NotificationPublisher extends JobService  {

    private static final String TAG = "NotificationPublisher";
    NotificationManagerCompat notificationManger;


    @Override
    public boolean onStartJob(JobParameters jobParameters) {

        Log.d(TAG,"Job started...");
        //notify
        notificationManger = NotificationManagerCompat.from(getApplicationContext());
        //build the notification
        Notification notification = new NotificationCompat.Builder(getApplicationContext(), CHANNEL_1_ID)
                .setContentTitle("Tittle")
                .setContentText("Text")
                .setSmallIcon(R.drawable.ic_notify)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setVibrate(new long[] { 1000, 1000, 1000, 1000, 1000 })
                .build();
        notificationManger.notify(1, notification);


        return false;
    }

    @Override
    public boolean onStopJob(JobParameters jobParameters) {
        return false;
    }
}
