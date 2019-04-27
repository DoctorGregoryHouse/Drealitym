package com.example.ruben.drealitym.uiclasses;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;


import com.example.ruben.drealitym.R;


public class RemindersActivity extends AppCompatActivity implements FragmentReminderPicker.FragmentReminderListener {

    //parameters passed by the interface of the ReminderPickerFragment
    int mStartHour;
    int mStartMin;
    int mStopHour;
    int mStopMin;
    int mInterval;
    int[] mDayValues;
    String mName;


    @Override
    protected void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminders);

        //hide the actionBar
        if (getSupportActionBar() != null){
            getSupportActionBar().hide();
        }


        //start intent when clicking fab
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.reminders_fab);
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


    }




}


