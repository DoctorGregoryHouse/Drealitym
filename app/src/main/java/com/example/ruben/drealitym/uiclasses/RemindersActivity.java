package com.example.ruben.drealitym.uiclasses;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;


import com.example.ruben.drealitym.HelperClasses.DreamContract;
import com.example.ruben.drealitym.HelperClasses.DreamDbHelper;
import com.example.ruben.drealitym.R;



public class RemindersActivity extends AppCompatActivity implements FragmentReminderPicker.FragmentReminderListener {

    static final String LOG_TAG = "RemindersActivity";

    //parameters passed by the interface of the ReminderPickerFragment
    int mStartHour;
    int mStartMin;
    int mStopHour;
    int mStopMin;
    int mInterval;
    int[] mDayValues;
    String mName;

    //TODO: Prepare data to be saved, save data in sharedPrefs, count saved reminders.
    // maybe there should be a limit of 5 or more reminders, so the sharedPrefs can be prepared more precise


    @Override
    protected void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminders);

        Log.d(LOG_TAG, "onCreate called...");

        //hide the actionBar
        if (getSupportActionBar() != null){
            getSupportActionBar().hide();
        }


        //open fragment
        FloatingActionButton fab =  findViewById(R.id.reminders_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                Fragment fragment = new FragmentReminderPicker();
                fragmentTransaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
                fragmentTransaction.add(R.id.reminders_fragment_container, fragment)
                        .addToBackStack(null)
                        .commit();
            }
        });
    }

    @Override
    public void onInputSent(String name, int startHour, int startMin, int stopHour, int stopMin, int[] days, int interval) {

        /**
         *
         * @param name
         * @param startHour
         * @param startMin
         * @param stopHour
         * @param stopMin
         * @param days
         * @param interval
         */

        Log.d(LOG_TAG, "onInputSent called...");
        saveInput(name, startHour, startMin, stopHour, stopMin, days, interval);

        updateList();

        scheduleNotification();


    }


    private void scheduleNotification(){

        //get the number of ids, maybe save this in shared prefs.


        //  Intent notificiationIntent = new Intent(this, AlertReceiver.class);
        //notificiationIntent.putExtra(AlertReceiver.)


    }

    //private Notification getNotification(String content)


    private void saveInput(String name, int startHour, int startMin, int stopHour, int stopMin, int[] days, int interval){

        //TODO: this method saves the input into the the corresponding table


        //create string separated by commas out of the days Array to store in the Database
        String dayString = "";
        for (int i = 0; i < 7 ; i++){

            if(i == 6){
                dayString += days[i];//no comma at the end
            }else{
                dayString += days[i] + ",";
            }
        }

        //create DatabaseObject to save in the AsyncTask
        DatabaseObject dbObject = new DatabaseObject(name, dayString, interval, startHour, startMin, stopHour, stopMin);

        DatabaseOperations dbOperations;
        dbOperations = new DatabaseOperations(this);

        //execute AsyncTask
        dbOperations.execute(dbObject, null,null);


    }

    private void updateList() {




    }

    //region DatabaseObject.class and DatabaseOperations AsyncTask
    private class DatabaseObject {

        String name;
        String days;
        int interval;
        int startHour;
        int startMin;
        int stopHour;
        int stopMin;


         DatabaseObject(String name, String days, int interval, int startHour, int startMin, int stopHour, int stopMin) {
            this.name = name;
            this.days = days;
            this.interval = interval;
            this.startHour = startHour;
            this.startMin = startMin;
            this.stopHour = stopHour;
            this.stopMin = stopMin;
        }

        //Getters
        //region getters
        public String getName() {
            return name;
        }

        public String getDays() {
            return days;
        }

        public int getInterval() {
            return interval;
        }

        public int getStartHour() {
            return startHour;
        }

        public int getStartMin() {
            return startMin;
        }

        public int getStopHour() {
            return stopHour;
        }

        public int getStopMin() {
            return stopMin;
        }
        //endregion
    }

    private static class DatabaseOperations extends AsyncTask<DatabaseObject, Void, String>{

        DreamDbHelper dreamDbHelper;
        final String LOG_TAG = "AddReminderDbOperations";

         DatabaseOperations(Context context) {

            dreamDbHelper = new DreamDbHelper(context);

        }

        @Override
        protected String doInBackground(DatabaseObject... databaseObjects) {

             addReminder(databaseObjects[0]);

            return null;
        }

        private void addReminder(DatabaseObject databaseObject){

            SQLiteDatabase db = dreamDbHelper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(DreamContract.ReminderEntry.COLUMN_REMINDER_NAME, databaseObject.getName() );
            values.put(DreamContract.ReminderEntry.COLUMN_REMINDER_DAYS, databaseObject.getDays() );
            values.put(DreamContract.ReminderEntry.COLUMN_REMINDER_INTERVAL, databaseObject.getInterval() );
            values.put(DreamContract.ReminderEntry.COLUMN_REMINDER_STARTHOUR, databaseObject.getStartHour());
            values.put(DreamContract.ReminderEntry.COLUMN_REMINDER_STARTMIN, databaseObject.getStartMin() );
            values.put(DreamContract.ReminderEntry.COLUMN_REMINDER_STOPHOUR, databaseObject.getStopHour() );
            values.put(DreamContract.ReminderEntry.COLUMN_REMINDER_STOPMIN, databaseObject.getStopMin() );

            long newRowId = db.insert(DreamContract.ReminderEntry.TABLE_NAME, null, values);

            Log.d(LOG_TAG, " added ROW_ID: " + newRowId);

            db.close();

        }



    }
    //endregion





}


