package com.example.ruben.drealitym.UiClasses;


import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.ruben.drealitym.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;


public class MainActivity extends  FragmentActivity {

    //CONSTANTS
    private static final String TAG = "MainActivtiy";


    Fragment selectedFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation_bar);
        bottomNav.setOnNavigationItemSelectedListener(navListener);

        Fragment HomeFragment = new HomeFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_main_container, HomeFragment).commit();

        initiateSharedPrefs();

    }


    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                    selectedFragment = null;

                    switch (menuItem.getItemId()) {

                        case R.id.nav_diary:
                            selectedFragment = new DreamDiaryFragment();
                            break;

                        case R.id.nav_home:
                            selectedFragment = new HomeFragment();
                            break;

                        case R.id.nav_reminders:
                            selectedFragment = new RealityCheckFragment();
                            break;

                        case R.id.nav_settings:
                            selectedFragment = new SettingsFragment();
                            break;


                    }
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_main_container, selectedFragment).commit();
                    return true;
                }
            };

    private void initiateSharedPrefs(){

        SharedPreferences sharedPrefs = getApplication().getSharedPreferences(
                getString(R.string.preference_file_reality_check), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPrefs.edit();

        if(!(sharedPrefs.contains(RealityCheckFragment.START_HOUR) || sharedPrefs.contains(RealityCheckFragment.START_MINUTE))){
            editor.putInt(RealityCheckFragment.START_HOUR, 8);


            editor.putInt(RealityCheckFragment.START_MINUTE,0);
            editor.commit();
        }

        if(!(sharedPrefs.contains(RealityCheckFragment.STOP_HOUR) || sharedPrefs.contains(RealityCheckFragment.STOP_MINUTE))){
            editor.putInt(RealityCheckFragment.STOP_HOUR, 21);
            editor.putInt(RealityCheckFragment.STOP_MINUTE,0);
            editor.commit();
        }

        if(!(sharedPrefs.contains(RealityCheckFragment.ENABLE_REALITY_CHECKS))){
            editor.putBoolean(RealityCheckFragment.ENABLE_REALITY_CHECKS, false);
            editor.commit();
        }
    }
}
