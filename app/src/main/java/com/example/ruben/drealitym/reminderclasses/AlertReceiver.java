package com.example.ruben.drealitym.reminderclasses;


import android.app.Notification;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import android.widget.Toast;


import com.example.ruben.drealitym.R;

import static com.example.ruben.drealitym.reminderclasses.AppBlueprint.CHANNEL_1_ID;
import static com.example.ruben.drealitym.reminderclasses.AppBlueprint.CHANNEL_2_ID;

public class AlertReceiver extends BroadcastReceiver {

    NotificationManagerCompat notifyManager;
    Context c;

    @Override
    public void onReceive(Context context, Intent intent) {

        Toast.makeText(context, "onReceive called, notification should popup", Toast.LENGTH_LONG).show();

        c = context;
        notifyManager = NotificationManagerCompat.from(context); // HAVE TO use NotificationManagerCompat instead of NotificationManger for backwards compatibility

        sendCheckNotification();
    }



    //notification to make RealityCheck
    public void sendCheckNotification(){


        //builds the notification
        Notification notification  = new NotificationCompat.Builder(c, CHANNEL_1_ID)
                .setContentTitle("Notification Title")
                .setContentText("Notification Text")
                .setSmallIcon(R.drawable.ic_notify)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .build();


        //if the id is the same in another notification, the old notification gets overridden, otherwise it will create another notification
        notifyManager.notify(1,notification);

        //TODO: schedule next notification

    }

    //notification to remind the User to read the dream of the previous day
    //TODO: this should only be executed if there is a dream previously
    public void sendReadNotification(){

        //builds the notification
        Notification notification  = new NotificationCompat.Builder(c, CHANNEL_2_ID)
                .setContentTitle("Notification Title(C2)")
                .setContentText("Notification Text(C2)")
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .build();


        //if the id is the same in another notification, the old notification gets overridden, otherwise it will create another notification
        notifyManager.notify(1,notification);
    }

}
