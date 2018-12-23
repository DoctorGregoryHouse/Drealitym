package com.example.ruben.drealitym.reminderclasses;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;


/*THIS CLASS CREATES A NOTIFICATION CHANNEL*/


public class AppBlueprint extends Application {


    public static final String CHANNEL_1_ID = "realitycheckChannel";
    public static final String CHANNEL_2_ID = "readDreamChannel";

    @Override
    public void onCreate() {
        super.onCreate();

        createNotificationChannels();

    }

    private void createNotificationChannels() {

        //CREATE CHANNEL1
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O/*O steht für Android OREO oder API 26*/) {

            NotificationChannel channel1 = new NotificationChannel(
                    CHANNEL_1_ID,
                    "realitycheckChannel", // Name der dem User angezeigt wird //TODO: reference to Strings (languages)
                    NotificationManager.IMPORTANCE_HIGH // was darf die Notification
            );

            /*
             * Wenn diese Einstellungen verändert werden, dann muss die App neu installiert werden
             * das die Einstellungen übernommen werden.
             * mit diesen einstellungen kann angegeben werden ob die Notification Vibrieren darf,
             * das Licht aufleuchtet oder der Bidschirm angeht.
             * */
            //channel.enableVibration(true);
            channel1.setDescription("Dieser Channel erinnert dich an deine Realitychecks"); //TODO: reference to Strings.xml

            //CREATE CHANNEL2
            NotificationChannel channel2 = new NotificationChannel(
                    CHANNEL_2_ID,
                    "readDreamChannel", // Name der dem User angezeigt wird //TODO: reference to Strings (languages)
                    NotificationManager.IMPORTANCE_DEFAULT // was darf die Notification
            );
            channel2.setDescription("Dieser Channel erinnert dich daram deine Träume zu lesen"); //TODO: reference to Strings.xml

            NotificationManager manager = getSystemService(NotificationManager.class);

            manager.createNotificationChannel(channel2); //TODO: This manager could be null so the App would crash.
            manager.createNotificationChannel(channel1);
        }

    }

}


