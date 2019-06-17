package com.example.ruben.drealitym.uiclasses;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.ruben.drealitym.R;

public class DreamDialogActivity extends AppCompatActivity implements RecordingFragment.recordingInterface{

    private String LOG_TAG ="DreamDialogActivity";

    FragmentManager fragmentManager;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_dream_dialog);

        // Hide ActionBar
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }




        fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        Fragment fragment = new RecordingFragment();
        transaction.add(R.id.fragment_dream_dialog_audio_container, fragment).commit();

    }

    @Override
    public void onFinishRecording(String filePath, int timerMins, int timerSecs) {

        Toast.makeText(this, "Mins: "+ timerMins + "secs "+ timerSecs, Toast.LENGTH_SHORT).show();
        fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        Fragment fragment = new PlayingFragment(); 
        transaction.replace(R.id.fragment_dream_dialog_audio_container, fragment).commit();
    }



}
