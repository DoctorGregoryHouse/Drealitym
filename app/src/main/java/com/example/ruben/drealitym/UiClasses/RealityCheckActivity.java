package com.example.ruben.drealitym.UiClasses;

import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
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
import com.example.ruben.drealitym.Notifications.ScheduleNotificationsService;
import com.example.ruben.drealitym.R;

import java.util.ArrayList;
import java.util.List;


public class RealityCheckActivity extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener {

    private static final String TAG = "RealityCheckActivity";
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

        Log.d(TAG, "OnCreate: called...");
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

                NotificationScheduler ns = new NotificationScheduler(getBaseContext());
                ns.scheduleNotification();
                Toast.makeText(RealityCheckActivity.this, "Send Testnotification", Toast.LENGTH_SHORT).show();

            }
        });

        final CustomExpandableListAdapter listAdapter = new CustomExpandableListAdapter(this, exlvTitleStrings);

        viewModel = ViewModelProviders.of(this).get(RealityCheckViewModel.class);
        viewModel.getAllRealityChecks().observe(this, new Observer<List<RealityCheckEntry>>() {
            @Override
            public void onChanged(final List<RealityCheckEntry> realityCheckEntries) {
                Log.d(TAG, "onChanged called...");
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
                        Log.d(TAG, "GroupPosition: " + groupPosition + " isChecked: " + newEntry.getNotification());
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
            Log.e(TAG, "invalid childposition : -1");
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


}

