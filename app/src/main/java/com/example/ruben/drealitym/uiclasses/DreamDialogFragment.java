package com.example.ruben.drealitym.uiclasses;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.text.Layout;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ruben.drealitym.R;

import java.io.IOException;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;


public class DreamDialogFragment extends Fragment {

    private String LOG_TAG = "DreamDialogFragment";
    private static final int REQUEST_RECORD_AUDIO_PERMISSION = 200;

    private MediaRecorder mRecorder = null;
    private MediaPlayer mPlayer = null;
    private String mFileName = null;

    private boolean isRecording = false;
    private boolean isPlaying = false;

    //Requesting permission to record audio
    private boolean permissionToRecordAccepted = false;
    private String[] permissions = {Manifest.permission.RECORD_AUDIO};

    FragmentManager fragmentmanager;





    public DreamDialogFragment(){
        //Fragment needs constructor
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case REQUEST_RECORD_AUDIO_PERMISSION:
                permissionToRecordAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                break;
        }
        if(!permissionToRecordAccepted){
            //TODO: grey out the audio part, implement function to grant permission again by clicking on the audio view
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_dream_dialog, container, false);

    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.d(LOG_TAG, "onviewCreated: called... ");

        fragmentmanager = getFragmentManager();
        FragmentTransaction transaction = fragmentmanager.beginTransaction();
        Fragment fragment = new RecordingFragment();
        transaction.add(R.id.fragment_dream_dialog_audio_container,fragment).commit();

        ActivityCompat.requestPermissions(getActivity(), permissions, REQUEST_RECORD_AUDIO_PERMISSION);

    }






    private void stopRecording() {
        mRecorder.stop();
        mRecorder.release();
        mRecorder = null;
        //recordButton.setImageResource(R.drawable.ic_mic_48dp);
        isRecording = false;

        //timeSwapBuff += timeInMilliseconds;
        //timerThreadHandler.removeCallbacks(updateTimerThread);

        //TODO: only enable the button when there is a valid file to play
        //TODO: change color of the button if he is not usable

    }

    private void startPlaying() {
        mPlayer = new MediaPlayer();
        try {
            mPlayer.setDataSource(mFileName);
            mPlayer.prepare();
            mPlayer.start();
        } catch (IOException e) {
            Log.e(LOG_TAG, "prepare() failed");
        }
        isPlaying = true;
        Toast.makeText(getContext(), "Playing started", Toast.LENGTH_SHORT).show();

    }

    private void stopPlaying() {
        mPlayer.release();
        mPlayer = null;
        isPlaying = false;


    }


}





