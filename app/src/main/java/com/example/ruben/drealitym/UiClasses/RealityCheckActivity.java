package com.example.ruben.drealitym.UiClasses;

import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.ruben.drealitym.Data.RealityCheckEntry;
import com.example.ruben.drealitym.Data.RealityCheckViewModel;
import com.example.ruben.drealitym.HelperClasses.CustomExpandableListAdapter;
import com.example.ruben.drealitym.HelperClasses.TimePickerFragment;
import com.example.ruben.drealitym.Notifications.NotificationScheduler;
import com.example.ruben.drealitym.R;

import java.util.ArrayList;
import java.util.List;


public class RealityCheckActivity extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener {

    private static final String LOG_TAG = "RealityCheckActivity";
    public static final int GET_NOTIFIED = 1;
    public static final int DONT_GET_NOTIFIED = 2;


    private ExpandableListView listview;
    private List<String> exlvTitleStrings;
    private Button sendTestNotification;

    private RealityCheckViewModel viewModel;
    private List<RealityCheckEntry> realityCheckEntries;

    private int currentGroupPosition = -1;
    private int currentChildPosition = -1;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reality_check);

        Log.d(LOG_TAG, "OnCreate: called...");
        // Hide ActionBar
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        exlvTitleStrings = new ArrayList<>();

        listview = findViewById(R.id.activity_reality_check_ex_lv);
        sendTestNotification = findViewById(R.id.activity_reality_check_send_notification_button);
        sendTestNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NotificationScheduler ns = new NotificationScheduler(getApplicationContext());
                ns.scheduleNotification();
            }
        });

        final CustomExpandableListAdapter listAdapter = new CustomExpandableListAdapter(this, exlvTitleStrings);

        viewModel = ViewModelProviders.of(this).get(RealityCheckViewModel.class);
        viewModel.getAllRealityChecks().observe(this, new Observer<List<RealityCheckEntry>>() {
            @Override
            public void onChanged(final List<RealityCheckEntry> realityCheckEntries) {
                Log.d(LOG_TAG, "onChanged called...");
                RealityCheckActivity.this.realityCheckEntries = realityCheckEntries;
                listAdapter.setRealityCheckEntries(realityCheckEntries);
                listview.setAdapter(listAdapter);

                listAdapter.setOnItemClickListener(new CustomExpandableListAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(int groupPosition, int childPosition) {
                        currentChildPosition = childPosition;
                        currentGroupPosition = groupPosition;

                        TimePickerFragment timePickerFragment = new TimePickerFragment();
                        timePickerFragment.show(getSupportFragmentManager(), "timePicker");
                    }

                    @Override
                    public void onItemClick(final int groupPosition, final int childPosition, boolean clickedInterval) {
                        //Start Spinner
                        AlertDialog.Builder mBuilder = new AlertDialog.Builder(RealityCheckActivity.this);
                        View mView = getLayoutInflater().inflate(R.layout.dialog_spinner, null);
                        mBuilder.setTitle("Choose how often you wanna make a reality check");
                        final Spinner mSpinner = (Spinner) mView.findViewById(R.id.dialog_spinner);
                        ArrayAdapter<String> mAdapter = new ArrayAdapter<>(RealityCheckActivity.this,
                                android.R.layout.simple_spinner_item,
                                getResources().getStringArray(R.array.spinner_values_intervals));
                        mAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        mSpinner.setAdapter(mAdapter);

                        mBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //this is called when the item is chosen
                                Toast.makeText(RealityCheckActivity.this,
                                        mSpinner.getSelectedItem().toString(),
                                        Toast.LENGTH_LONG).show();
                                updateIntervalInDatabase(groupPosition, mSpinner.getSelectedItemPosition());
                                dialogInterface.dismiss();
                            }
                        });
                        mBuilder.setNegativeButton("Dismiss", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();

                            }
                        });
                        mBuilder.setView(mView); //sets the custom layout to the AlertDialog.Builder
                        AlertDialog dialog = mBuilder.create();
                        dialog.show();

                    }

                    @Override
                    public void onItemSwitchClick(int groupPosition, int getNotified) {

                        RealityCheckEntry oldEntry = RealityCheckActivity.this.realityCheckEntries.get(groupPosition);
                        RealityCheckEntry newEntry;
                        if(getNotified == 2) {
                            newEntry = new RealityCheckEntry(oldEntry.getStartHour(),
                                    oldEntry.getStartMinute(),
                                    oldEntry.getStopHour(),
                                    oldEntry.getStopMinute(),
                                    oldEntry.getInterval(),
                                    1);


                            newEntry.setId(groupPosition + 1);
                        }else {
                            newEntry = new RealityCheckEntry(oldEntry.getStartHour(),
                                    oldEntry.getStartMinute(),
                                    oldEntry.getStopHour(),
                                    oldEntry.getStopMinute(),
                                    oldEntry.getInterval(),
                                    2);
                            newEntry.setId(groupPosition + 1);
                        }
                        viewModel.update(newEntry);
                        Log.d(LOG_TAG, "GroupPosition: " + groupPosition + " isChecked: " + newEntry.getNotification());
                    }
                });

            }
        });
        //TODO: create an array in strings.mxl
        exlvTitleStrings.add("Sunday");
        exlvTitleStrings.add("Monday");
        exlvTitleStrings.add("Tuesday");
        exlvTitleStrings.add("Wednesday");
        exlvTitleStrings.add("Thursday");
        exlvTitleStrings.add("Friday");
        exlvTitleStrings.add("Saturday");

    }


    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {


        RealityCheckEntry oldEntry = realityCheckEntries.get(currentGroupPosition);
        RealityCheckEntry newEntry = null;

        if (currentChildPosition == 0) {
            //editing startTime
            newEntry = new RealityCheckEntry(hourOfDay, minute, oldEntry.getStopHour(), oldEntry.getStopMinute(), oldEntry.getInterval(), oldEntry.getNotification());
            newEntry.setId(currentGroupPosition + 1);
        } else if (currentChildPosition == 1) {
            //editing stoptime
            newEntry = new RealityCheckEntry(oldEntry.getStartHour(), oldEntry.getStartMinute(), hourOfDay, minute, oldEntry.getInterval(), oldEntry.getNotification());
            newEntry.setId(currentGroupPosition + 1);
        } else if (currentChildPosition == -1) {
            Log.e(LOG_TAG, "invalid childposition : -1");
        }

        viewModel.update(newEntry);

        Toast.makeText(this, "HourOfDay: " + hourOfDay, Toast.LENGTH_SHORT).show();
    }

    private void updateIntervalInDatabase(int groupPosition, int interval) {
        RealityCheckEntry oldEntry = realityCheckEntries.get(groupPosition);
        RealityCheckEntry newEntry = new RealityCheckEntry(oldEntry.getStartHour(),
                oldEntry.getStartMinute(),
                oldEntry.getStopHour(),
                oldEntry.getStopMinute(),
                interval,
                oldEntry.getNotification());
        newEntry.setId(groupPosition + 1);
        viewModel.update(newEntry);
    }

    //algorithm for scheduling the next notification
    //region
//    public void scheduleJob() {
//        int time = calculateScheduleTime();
//        //TODO: Does this Toast show when the user does not but the algorithm schedules an alarm ?
//        if (time == -1){
//            Toast.makeText(this, "You should add some time", Toast.LENGTH_SHORT).show();
//            return;
//        }
//        Log.d(LOG_TAG,"Calculated time for next notificaiton: " + time);
//        JobScheduler mJobScheduler = (JobScheduler) getSystemService(Context.JOB_SCHEDULER_SERVICE);
//        JobInfo.Builder builder = new JobInfo.Builder(1, new ComponentName(getPackageName(), ScheduleNotificationsService.class.getName()));
//        builder.setMinimumLatency(time * 60 * 1000);
//
//        if (mJobScheduler.schedule(builder.build()) <= 0) {
//            //If something goes wrong
//            Log.d(LOG_TAG, "Jobscheduler could not schedule notifciation");
//        }
//    }
//
//
//    private int calculateScheduleTime() {
//        Calendar calendar = Calendar.getInstance();
//        //sunday=1, monday=2, tuesday=3 ... saturday=7
//        int current_day = calendar.get(Calendar.DAY_OF_WEEK );
//        current_day -= 1 ;
//        int current_hour = calendar.get(Calendar.HOUR_OF_DAY);
//        int current_minute = calendar.get(Calendar.MINUTE);
//        int current_day_time = (current_hour * 60) + current_minute;
//        RealityCheckEntry entry;
//
//        int[][] notification_values = new int[7][4];
//        for (int i = 0; i < 7; i++) {
//            entry = realityCheckEntries.get(i);
//            notification_values[i][0] = entry.getNotification();
//        }
//
//        int iterating_day = current_day;
//        List<Integer> intervals = prepareIntervalsForCalculation(iterating_day);
//        if (notification_values[iterating_day][0] == GET_NOTIFIED) {
//            //notifications are activated at this day
//            //TODO: create some log messages
//            for (int i = 0; i < intervals.size(); i++) {
//                if (current_day_time < intervals.get(i)) {
//                    return ( intervals.get(i) - current_day_time);
//                }
//            }
//        }
//        //TODO:maybe this part of the code will never get called
//        int prevent_loop = 0;
//        while (notification_values[iterating_day][0] != 1 || prevent_loop == 10) {
//            iterating_day++;
//            prevent_loop++;
//        }
//        if (prevent_loop == 10) {
//            return -1;
//        } else {
//            int counted_day = iterating_day - current_day;
//            intervals = prepareIntervalsForCalculation(iterating_day);
//            return (counted_day * 24 * 60) + intervals.get(0) + 1440 - current_day_time;
//        }
//
//    }
//
//    private List<Integer> prepareIntervalsForCalculation(int day) {
//
//        List<Integer> intervals = new ArrayList<Integer>();
//        RealityCheckEntry currentEntry = realityCheckEntries.get(day);
//        int startTime = (currentEntry.getStartHour() * 60) + currentEntry.getStartMinute();
//        int stopTime = (currentEntry.getStopHour() * 60) + currentEntry.getStopMinute();
//        int intervalTime = currentEntry.getInterval() * 60; //the minimum interval-time is one hour
//        boolean check = true;
//        int counter = startTime;
//
//        intervals.add(startTime);
//        while (check) {
//            counter = counter + intervalTime;
//            if (counter < stopTime) {
//                intervals.add(counter);
//            } else {
//                check = false;
//            }
//        }
//        Log.d(LOG_TAG,"Returned intervals for day: " + day +" , first interval: " + intervals.get(0));
//        return intervals;
//    }
    //endregion
}

