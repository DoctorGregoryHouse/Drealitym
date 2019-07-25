package com.example.ruben.drealitym.uiclasses;

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.widget.ExpandableListView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ruben.drealitym.Data.RealityCheckViewModel;
import com.example.ruben.drealitym.HelperClasses.CustomExpandableListAdapter;
import com.example.ruben.drealitym.HelperClasses.TimePickerFragment;
import com.example.ruben.drealitym.R;

import java.util.ArrayList;
import java.util.List;

public class RealityCheckActivity extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener {

    private static final String LOG_TAG = "RealityCheckActivity";

    ExpandableListView listview;
    CustomExpandableListAdapter listAdapter;
    List<String> exlvTitleStrings;
    List<String> exlvContentStrings;

    RealityCheckViewModel viewModel;


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
        viewModel = new RealityCheckViewModel(getApplication());


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


        listview = findViewById(R.id.activty_reality_check_ex_lv);
        listAdapter = new CustomExpandableListAdapter(this, exlvTitleStrings,exlvContentStrings);
        listview.setAdapter(listAdapter);

        listAdapter.setOnItemClickListener(new CustomExpandableListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int groupPosition, int childPosition) {



                TimePickerFragment timePickerFragment = new TimePickerFragment();
                timePickerFragment.show(getSupportFragmentManager(), "timePicker");

            }
        });
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        Toast.makeText(this, "HourOfDay: " + hourOfDay , Toast.LENGTH_SHORT).show();
    }
}
