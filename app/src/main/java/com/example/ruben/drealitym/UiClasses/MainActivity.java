package com.example.ruben.drealitym.UiClasses;


import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.ruben.drealitym.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;


public class MainActivity extends  FragmentActivity {

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

}
