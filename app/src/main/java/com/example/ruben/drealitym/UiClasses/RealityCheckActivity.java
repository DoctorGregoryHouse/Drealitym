package com.example.ruben.drealitym.UiClasses;

import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
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
import com.example.ruben.drealitym.Notifications.ScheduleNotificationsService;
import com.example.ruben.drealitym.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class RealityCheckActivity extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener {

    private static final String LOG_TAG = "RealityCheckActivity";

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
                scheduleNotification();
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
                                ;
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
        exlvTitleStrings.add("Monday");
        exlvTitleStrings.add("Tuesday");
        exlvTitleStrings.add("Wednesday");
        exlvTitleStrings.add("Thursday");
        exlvTitleStrings.add("Friday");
        exlvTitleStrings.add("Saturday");
        exlvTitleStrings.add("Sunday");

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


    public void scheduleNotification() {
        int time = calculateScheduleTime();
        JobScheduler mJobScheduler = (JobScheduler) getSystemService(Context.JOB_SCHEDULER_SERVICE);
        JobInfo.Builder builder = new JobInfo.Builder(1, new ComponentName(getPackageName(), ScheduleNotificationsService.class.getName()));
        builder.setMinimumLatency(time * 1000);

        if (mJobScheduler.schedule(builder.build()) <= 0) {
            //If something goes wrong
            Log.d(LOG_TAG, "Jobscheduler could not schedule notifciation");
        }
    }


    private int calculateScheduleTime() {
        Calendar calendar = Calendar.getInstance();
        //sunday=1, monday=2, tuesday=3 ... saturday=7
        int current_day = calendar.get(Calendar.DAY_OF_WEEK - 1);
        int current_hour = calendar.get(Calendar.HOUR_OF_DAY);
        int current_minute = calendar.get(Calendar.MINUTE);
        int current_day_time = (current_hour * 60) + current_minute;
        RealityCheckEntry entry;

        int[][] notification_values = new int[7][4];
        for (int i = 0; i < 7; i++) {
            entry = realityCheckEntries.get(i);
            notification_values[i][0] = entry.getNotification();
            notification_values[i][1] = (entry.getStartHour() * 60) + entry.getStartMinute();
            notification_values[i][2] = (entry.getStopHour() * 60) + entry.getStopMinute();
            notification_values[i][3] = entry.getInterval() * 60;
        }

        int iterating_day = current_day;
        List<Integer> intervals = prepareIntervalsForCalculation(iterating_day);
        if (notification_values[iterating_day][0] == 1) {
            //notifications are activated at this day
            //TODO: create constants for activated/non activated days
            //TODO: create some log messages
            for (int i = 0; i < intervals.size(); i++) {
                if (current_day_time < intervals.get(i)) {
                    return intervals.get(i);
                }
            }
        }
        int prevent_loop = 0;
        while (notification_values[iterating_day][0] != 1 || prevent_loop == 10) {
            iterating_day++;
            prevent_loop++;
        }
        if (prevent_loop == 10) {
            return -1;
        } else {
            int counted_day = iterating_day - current_day;
            intervals = prepareIntervalsForCalculation(iterating_day);
            return (counted_day * 24 * 60) + intervals.get(0) + 1440 - current_day_time;
        }

    }

    private List<Integer> prepareIntervalsForCalculation(int day) {

        List<Integer> intervals = new ArrayList<Integer>();
        RealityCheckEntry currentEntry = realityCheckEntries.get(day);
        int startTime = (currentEntry.getStartHour() * 60) + currentEntry.getStartMinute();
        int stopTime = (currentEntry.getStopHour() * 60) + currentEntry.getStopMinute();
        int intervalTime = currentEntry.getInterval() * 60;
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
        return intervals;
    }

    private static class UpdateRealityCheckEntry extends AsyncTask<Void , Void, Void>{
        private RealityCheckViewModel viewModel;
        private List<RealityCheckEntry> realityCheckEntryList;
        private int groupPosition;
        private boolean isChecked;

        UpdateRealityCheckEntry(RealityCheckViewModel viewModel, List<RealityCheckEntry> realityCheckEntryList, int groupPosition, boolean isChecked){
            this.viewModel = viewModel;
            this.realityCheckEntryList = realityCheckEntryList;
            this.groupPosition = groupPosition;
            this.isChecked = isChecked;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();


        }

        @Override
        protected Void doInBackground(Void... voids) {
            RealityCheckEntry oldEntry = realityCheckEntryList.get(groupPosition);
            RealityCheckEntry newEntry;
            if(isChecked) {
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
            Log.d(LOG_TAG,"saved new Entry...  ID: " +newEntry.getId() + " Notificaiton: " + newEntry.getNotification());

            return null;
        }

    }

}

