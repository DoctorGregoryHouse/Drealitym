package com.example.ruben.drealitym.uiclasses;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.ruben.drealitym.HelperClasses.DreamDiaryAdapter;
import com.example.ruben.drealitym.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;


public class MainActivity extends  FragmentActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        FloatingActionButton fab = findViewById(R.id.activity_main_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                Fragment fragment = new DreamDialogFragment();
                fragmentTransaction.add(R.id.frame_layout_dialog_fragment,fragment).commit();
            }
        });



//        final Button addDreamBtn =   findViewById(R.id.add_dream_button);
//        final Button myDiaryBtn =  findViewById(R.id.my_diary_intent_button);
//        final Button remindersBtn = findViewById(R.id.reminders_button);
//        final Button settingsBtn = findViewById(R.id.settings_button);
//
//
//        myDiaryBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent myDiaryIntent = new Intent(MainActivity.this, DreamDiaryActivity.class);
//                startActivity(myDiaryIntent);
//            }
//        });
//
//
//
//        settingsBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent settingsIntent = new Intent(MainActivity.this, SettingsActivity.class);
//                startActivity(settingsIntent);
//            }
//        });

    }



}
