package com.example.ruben.drealitym.uiclasses;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;


import com.example.ruben.drealitym.R;


public class RemindersActivity extends AppCompatActivity implements FragmentReminderPicker.FragmentReminderListener {




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
    public void onInputSent(String swag) {

    }

    //    /**
//     * THIS IS A TEST METHOD WHICH CREATES A DUMMY DREAM_REMINDER OBJECT
//     */
//    public DreamReminder DummyDreamReminder() {
//
//        //nur montag aktiv
//        int[] dummydays = new int[7];
//        dummydays[0] = 1;
//
//
//
//
//
//        DreamReminder dummyReminder = new DreamReminder(dummydays, )
//    }






    /**
     * DREAM_REMINDER OBJECT
     */
    public class DreamReminder {

        private int[] mDays;
        private int mStartTime;
        private int mStopTime;
        private String mName;
        private int mInterval;
        private int mId;

        public DreamReminder(int[] days, int startTime, int stopTime, String name, int interval, int id) {

            mDays = days;
            mStartTime = startTime;
            mStopTime = stopTime;
            mName = name;
            mInterval = interval;
            mId = id;
        }




    }




}


