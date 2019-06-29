package com.example.ruben.drealitym.uiclasses;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ruben.drealitym.R;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

public class RecordingFragment extends Fragment {

    public interface recordingInterface {

        public void onFinishRecording(String filePath, int timerMins, int timerSecs);

    }
    private final String LOG_TAG = "RecordingFragment";

    private recordingInterface recordingInterface;
    private int timerSecs;
    private int timerMins;

    private TextView tvRecordingTime;
    private ImageButton iBtnRecord;

    private MediaRecorder mRecorder;
    private String mFileName;
    private boolean isRecording;

    private String mFilePath;

    //variables for the @updateTimerThread method to display the recording seconds
    private Handler timerThreadHandler;
    private long startHTime = 0L;
    private long timeInMilliseconds = 0L;
    private long timeSwapBuff = 0L;
    private long updatedTime = 0L;

    private boolean permissionToRecordAccepted = false;
    private String[] permissions = {Manifest.permission.RECORD_AUDIO};
    public static final int REQUEST_RECORD_AUDIO_PERMISSION = 200;

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

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        //if statement checks whether the underlying activity has implemented the FragmentReminderListener interface
        if (context instanceof recordingInterface){

            recordingInterface = (recordingInterface) context;
        }else {
            throw new RuntimeException(context.toString() + " must implement recordingInterface");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_recording, container, false);

        Log.d(LOG_TAG,"onCreateView: called...");

        tvRecordingTime = v.findViewById(R.id.fragment_playing_tv_recording_time);
        iBtnRecord = v.findViewById(R.id.fragment_playing_imgBtn_record);



        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Log.d(LOG_TAG,"onViewCreated: called...");
        timerThreadHandler = new Handler();
        isRecording = false;

        //TODO: store in cache, only save if the user does add it
        //outputFile = Environment.getExternalStorageDirectory().getAbsolutePath()+ "/recording.3gp";
        try {
            mFileName = getActivity().getExternalCacheDir().getAbsolutePath()+ "/recording.3gp";
        }catch (NullPointerException ex){
            Log.d(LOG_TAG,"NullPointerException on getActivity().getExternalCacheDir().getAbsolutePath();");
        }


        iBtnRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "RecordButton Clicked...", Toast.LENGTH_SHORT).show();

                //request permission to use microphone
                ActivityCompat.requestPermissions(getActivity(), permissions, REQUEST_RECORD_AUDIO_PERMISSION);

                if (isRecording){stopRecording();
                }else {
                    Date timeStamp = Calendar.getInstance().getTime();
                    String mTimeStamp = new SimpleDateFormat("yyyy-MM-dd-HH-mm").format(timeStamp);
                    File storageDir = new File (getActivity().getFilesDir(), "/audio/");
                    storageDir.mkdir();
                    mFilePath = storageDir +"/"+ mTimeStamp +"_rec.3gp";

                    if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.RECORD_AUDIO ) == PackageManager.PERMISSION_GRANTED){
                        startRecording();
                    }else {
                        //permission not granted
                        //TODO: grey out the audio button
                    }


                }
            }
        });


    }

    private void startRecording(){
        mRecorder = new MediaRecorder();
        mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mRecorder.setOutputFile(mFilePath);
        mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
        try {
            mRecorder.prepare();//TODO: no such file or directory, create the dir correctly
        } catch (IOException e) {
            Log.e(LOG_TAG, "prepare() failed");
        }
        mRecorder.start();
        iBtnRecord.setImageResource(R.drawable.ic_mic_lila_48dp);
        Toast.makeText(getContext(), "Recording started", Toast.LENGTH_SHORT).show();
        isRecording =true;

        //starts the updateTimerThread to count the recording time
        startHTime = SystemClock.uptimeMillis();
        timerThreadHandler.postDelayed(updateTimerThread, 0);


    }

    private void stopRecording() {
        mRecorder.stop();
        mRecorder.release();
        mRecorder = null;
        iBtnRecord.setImageResource(R.drawable.ic_mic_48dp);
        isRecording =false;

        //timeSwapBuff += timeInMilliseconds;
        timerThreadHandler.removeCallbacks(updateTimerThread);

        //TODO: change color of the button if he is not usable due not accepting the audio permissions

        //calling the interface to pass the relevant information to the underlying activity
        recordingInterface.onFinishRecording(mFilePath, timerMins, timerSecs);

    }



    private Runnable updateTimerThread = new Runnable() {
        @Override
        public void run() {

            timeInMilliseconds = SystemClock.uptimeMillis()-startHTime;
            //updatedTime = timeSwapBuff + timeInMilliseconds;
            updatedTime = timeInMilliseconds;
            int secs = (int) (updatedTime/1000);
            int mins = secs /60;
            secs = secs % 60;
            tvRecordingTime.setText("" + String.format("%02d", mins) + ":"
                    + String.format("%02d", secs));
            timerThreadHandler.postDelayed(this, 0);

            timerSecs = secs;
            timerMins = mins;
        }
    };

}

