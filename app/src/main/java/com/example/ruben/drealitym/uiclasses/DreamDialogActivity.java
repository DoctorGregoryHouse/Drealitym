package com.example.ruben.drealitym.uiclasses;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.ruben.drealitym.R;

public class DreamDialogActivity extends AppCompatActivity implements RecordingFragment.recordingInterface{

    private String LOG_TAG ="DreamDialogActivity";

    private FragmentManager fragmentManager;
    private Button btnSave;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(LOG_TAG,"OnCreate: called...");
        setContentView(R.layout.fragment_dream_dialog);
        // Hide ActionBar
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        //if screen turns the fragment should not be created again
        if(savedInstanceState==null) {
            fragmentManager = getSupportFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            Fragment fragment = new RecordingFragment();
            transaction.add(R.id.fragment_dream_dialog_audio_container, fragment).commit();
        }
        btnSave = findViewById(R.id.fragment_dream_dialog_btn_save);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //save dream to Room database
            }
        });
    }

    @Override
    public void onFinishRecording(String filePath, int timerMins, int timerSecs) {
        Log.d(LOG_TAG,"onFinishRecording: called...");

        Toast.makeText(this, "Mins: "+ timerMins + "secs "+ timerSecs, Toast.LENGTH_SHORT).show();
        fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        Fragment fragment = new PlayingFragment(); 
        transaction.replace(R.id.fragment_dream_dialog_audio_container, fragment).commit();
    }



}
