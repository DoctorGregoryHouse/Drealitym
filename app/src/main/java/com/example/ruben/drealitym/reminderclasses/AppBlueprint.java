package com.example.ruben.drealitym.reminderclasses;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;

import com.example.ruben.drealitym.R;


/**
 * This class gets called when the app is started, before any other activity is created
 */

/*THIS CLASS CREATES A NOTIFICATION CHANNEL*/


public class AppBlueprint extends Application {


    public static final String CHANNEL_1_ID = "realityCheckChannel";
    public static final String CHANNEL_2_ID = "readDreamChannel";

    @Override
    public void onCreate() {
        super.onCreate();

        createNotificationChannels();

    }

    private void createNotificationChannels() {

        /**
         * this block of code gets only executed if the android version is oreo or higher,
         * if its not it do not have to create the notification channels because there is only one.
         * In this case it don't needs to be modified
         */
        //CREATE CHANNEL1
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O/*O steht für Android OREO oder API 26*/) {

            NotificationChannel channel1 = new NotificationChannel(
                    CHANNEL_1_ID,
                    "realitycheckChannel", // Name der dem User angezeigt wird //TODO: reference to Strings (languages)
                    NotificationManager.IMPORTANCE_HIGH // was darf die Notification
                    //TODO: do i have to implement the code to modify this parameter or can the user do this by default ?
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
            channel2.setDescription(getString(R.string.blueprint_activity_channel2_description));

            NotificationManager manager = getSystemService(NotificationManager.class);

            if(manager != null){
            manager.createNotificationChannel(channel2);
            manager.createNotificationChannel(channel1);
            }
        }

    }

}


