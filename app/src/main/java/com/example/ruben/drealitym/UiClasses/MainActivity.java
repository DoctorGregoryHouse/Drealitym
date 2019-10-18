package com.example.ruben.drealitym.UiClasses;


import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.example.ruben.drealitym.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;


public class MainActivity extends  FragmentActivity {

    private static final String TAG = "MainActivtiy";

    private CardView cvRealityChecks, cvAddDream, cvDreamDiary;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation_bar);
        bottomNav.setOnNavigationItemSelectedListener(navListener);

    }





    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                    Fragment selectedFragment = null;

                    switch (menuItem.getItemId()) {

                        case R.id.nav_diary:
                            selectedFragment = new DreamDiaryFragment();
                            break;

                        case R.id.nav_home:
                            break;

                        case R.id.nav_reminders:
                            //selectedFragment = new DreamDiaryFragment();
                            break;

                        case R.id.nav_wiki:
                            //selectedFragment = new DreamDiaryFragment();
                            break;


                    }
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_main_container, selectedFragment).commit();
                    return true;
                }
            };
}




//        cvRealityChecks = findViewById(R.id.activity_main_cv_realty_check);
//        cvRealityChecks.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(MainActivity.this, RealityCheckActivity.class);
//                startActivity(intent);
//            }
//        });
//
//        cvAddDream = findViewById(R.id.activity_main_cv_add_dream);
//        cvAddDream.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(MainActivity.this, DreamDialogActivity.class);
//                intent.putExtra(DreamDialogActivity.EXTRA_REQUEST_CODE,DreamDialogActivity.REQUEST_CODE_NEW_ENTRY);
//                startActivity(intent);
//            }
//        });
//
//        cvDreamDiary = findViewById(R.id.activity_main_cv_dream_diary);
//        cvDreamDiary.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(MainActivity.this, DreamDiaryActivity.class);
//                startActivity(intent);
//            }
//        });