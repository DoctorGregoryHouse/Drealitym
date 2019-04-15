package com.example.ruben.drealitym.uiclasses;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.example.ruben.drealitym.R;

public class RemindersActivity extends AppCompatActivity{




    @Override
    protected void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminders);

        //hide the actionBar
        if (getSupportActionBar() != null){
            getSupportActionBar().hide();
        }

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.reminders_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                Fragment fragment = new FragmentReminderPicker();
                fragmentTransaction.add(R.id.reminders_fragment_container, fragment).commit();


            }
        });

    }


    public class ReminderObject {

        String name;


    }

}


