package com.example.ruben.drealitym.uiclasses;

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.widget.ExpandableListView;
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
import com.example.ruben.drealitym.R;

import java.util.ArrayList;
import java.util.List;


public class RealityCheckActivity extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener {

    private static final String LOG_TAG = "RealityCheckActivity";

    private ExpandableListView listview;
    private CustomExpandableListAdapter listAdapter;
    private List<String> exlvTitleStrings;
    private List<String> exlvContentStrings;

    private RealityCheckViewModel viewModel;
    private List<RealityCheckEntry> realityCheckEntryList;

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
        exlvContentStrings = new ArrayList<>();

        listview = findViewById(R.id.activity_reality_check_ex_lv);

        final CustomExpandableListAdapter listAdapter = new CustomExpandableListAdapter(this, exlvTitleStrings,exlvContentStrings);

        viewModel = ViewModelProviders.of(this).get(RealityCheckViewModel.class);
        viewModel.getAllRealityChecks().observe(this, new Observer<List<RealityCheckEntry>>() {
            @Override
            public void onChanged(List<RealityCheckEntry> realityCheckEntries) {

                realityCheckEntryList = realityCheckEntries;
                listAdapter.setRealityCheckEntries(realityCheckEntries);
                listview.setAdapter(listAdapter);


                //ClickListener for the TimePicker
                //TODO: distinguish between Start and End time
                listAdapter.setOnItemClickListener(new CustomExpandableListAdapter.OnItemClickListener() {
                    /**
                     *
                     * @param groupPosition
                     * @param childPosition if this value is 0, the startTime is edited, if it is 1 the stopTime
                     */
                    @Override
                    public void onItemClick(int groupPosition, int childPosition) {

                        currentChildPosition = childPosition;
                        currentGroupPosition = groupPosition;

                        TimePickerFragment timePickerFragment = new TimePickerFragment();
                        timePickerFragment.show(getSupportFragmentManager(), "timePicker");

                    }
                });

            }
        });

        exlvTitleStrings.add("Monday");
        exlvTitleStrings.add("Tuesday");
        exlvTitleStrings.add("Wednesday");
        exlvTitleStrings.add("Thursday");
        exlvTitleStrings.add("Friday");
        exlvTitleStrings.add("Saturday");
        exlvTitleStrings.add("Sunday");

        exlvContentStrings.add("Von: 8:00");
        exlvContentStrings.add("Bis: 16:00");
        exlvContentStrings.add("Alle 20 minuten");
        exlvContentStrings.add("Uhrzeicheck");
        exlvContentStrings.add("Notifications on");

    }


    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {


        RealityCheckEntry oldEntry = realityCheckEntryList.get(currentGroupPosition);
        RealityCheckEntry newEntry = null;

        if(currentChildPosition == 0){
            //editing startTime
            newEntry = new RealityCheckEntry(hourOfDay,minute,oldEntry.getStopHour(),oldEntry.getStopMinute(),oldEntry.getInterval(),oldEntry.getNotification());
            newEntry.setId(currentGroupPosition +1);
        }else if(currentChildPosition == 1){
            //editing stoptime
            newEntry = new RealityCheckEntry(oldEntry.getStartHour(),oldEntry.getStartMinute(),hourOfDay,minute,oldEntry.getInterval(),oldEntry.getNotification());
            newEntry.setId(currentGroupPosition +1);
        }else if(currentChildPosition == -1){
            Log.e(LOG_TAG,"invalid childposition : -1");
        }

        viewModel.update(newEntry);






        Toast.makeText(this, "HourOfDay: " + hourOfDay , Toast.LENGTH_SHORT).show();
    }
}
