package com.example.ruben.drealitym.uiclasses;

import android.os.Bundle;
import android.util.Log;
import android.widget.ExpandableListView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ruben.drealitym.HelperClasses.CustomExpandableListAdapter;
import com.example.ruben.drealitym.R;

import java.util.ArrayList;
import java.util.List;

public class RealityCheckActivity extends AppCompatActivity {

    private static final String LOG_TAG = "RealityCheckActivity";

    ExpandableListView listview;
    CustomExpandableListAdapter listAdapter;
    List<String> exlvTitleStrings;
    List<String> exlvContentStrings;

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


        listview.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {
                Toast.makeText(RealityCheckActivity.this, "Expand", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
