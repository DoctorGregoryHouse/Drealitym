package com.example.ruben.drealitym.UiClasses;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ruben.drealitym.R;

public class SettingsActivity extends AppCompatActivity{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        //hide actionBar
        if (getSupportActionBar() != null){
            getSupportActionBar().hide();
        }




    }
}
