package com.example.ruben.drealitym.uiclasses;

import android.app.ActionBar;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.ruben.drealitym.HelperClasses.AlarmItem;
import com.example.ruben.drealitym.R;
import com.example.ruben.drealitym.reminderclasses.AlertReceiver;

import java.util.ArrayList;
import java.util.Calendar;

public class RemindersActivity extends AppCompatActivity{

    //region Variables TimePicker/SetTime
    public static final String SHARED_PREFS = "shared_prefs";

    public static final String START_HOUR = "start_hour";
    public static final String START_MIN = "start_min";

    public static final String STOP_HOUR = "stop_hour";
    public static final String STOP_MIN = "stop_min";


    //startzeit
    private int startMin;
    private int startHour;

    //stopzeit
    private int stopMin;
    private int stopHour;

    private Button addAlarm;

    //endregion




    @Override
    protected void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminders);


        ArrayList<AlarmItem> alarmItems = new ArrayList<>();

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getBaseContext(), AddAlarmActivity.class);
                startActivity(intent);

                //Snackbar.make(view, "Here's a Snackbar", Snackbar.LENGTH_LONG).setAction("Action",null).show();
            }
        });




        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view_reminders);








    }





    @Override
    protected void onPause() {
        super.onPause();
        saveData();
    }

    @Override
    protected void onResume(){
        super.onResume();
        loadData();
        updateViews();
    }


    @Override
    protected void onStart() {
        super.onStart();
        loadData();
        updateViews();
    }

    //region SaveData/LoadData/UpdateViews
    //speichert die Einstellungen die vom user gemacht werden. (data/data/my.app.id/shared_prefs/your.app.id_preferences.xml)
    private void saveData(){

        //creates shared_prefs.xml
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        //TODO: es muss ein Int gespeichert werden, die zeit an der der Reminder los geht und der an dem es beendet.
//        editor.putString(STOP_TIME, timeStop.getText().toString());
//        editor.putString(START_TIME,timeStart.getText().toString());

        // saves the Stop time into the shared_prefs.xml
        editor.putInt(START_HOUR,startHour);
        editor.putInt(START_MIN, startMin);

        //saves the start time into the shared_prefs.xml
        editor.putInt(STOP_HOUR, stopHour);
        editor.putInt(STOP_MIN, stopMin);



        editor.apply();
        Toast.makeText(this,"Data saved",Toast.LENGTH_LONG);
    }
    private void loadData(){
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS,MODE_PRIVATE);

        //when the activity gets invoked, the time is setted by the information of the shared_prefs
        startMin = sharedPreferences.getInt(START_MIN, 0);
        startHour = sharedPreferences.getInt(START_HOUR, 0);

        stopMin = sharedPreferences.getInt(STOP_MIN, 0);
        stopHour = sharedPreferences.getInt(STOP_HOUR, 0);

    }
    private void updateViews() {

    }

    //endregion

    private  void createAlarm() {

        //specify the time the alarm triggers
        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY,startHour );
        c.set(Calendar.MINUTE, startMin);
        c.set(Calendar.SECOND, 5);

        //TODO: create a period of time in which the notification pops up in a certain interval


        //initializes the alarm and starts an Intent
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, AlertReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this,1,intent, 0);

        // sets the exact time to start the alarm
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), pendingIntent);

        //Debugging
        Toast.makeText(this, "ALarm set", Toast.LENGTH_SHORT).show();
    }

    //TODO: implement functionality to specify the frequency of the notification to occur


}
