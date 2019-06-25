package com.example.ruben.drealitym.uiclasses;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.ruben.drealitym.R;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DreamDialogActivity extends AppCompatActivity implements RecordingFragment.recordingInterface, NumberPicker.OnValueChangeListener {

    //TODO: add permission request
    private String LOG_TAG = "DreamDialogActivity";


    private FragmentManager fragmentManager;
    private Button btnSave;
    private Button btnOpenDialog;

    private String mFileName;
    /*
    DreamClarity defines if its lucid pre-lucid or normal dream
    DreamDate adds the date of the dream saved
    DreamHasAudioFile determines whether there is an audio file or not, if there is not an audiofile there is
    also no @mDreamFilePath
     */
    private String dDreamTitle;
    private String dDreamText;
    private int dDreamClarity = 0; //default value
    private int dDreamDate;
    private boolean dDreamHasAudioFile;
    private String dDreamFilePath;

    private EditText mEdtTitle;
    private EditText mEdtText;

    private String mDate;


    //when NumberPickerDialog is called and the value changes, this method triggers
    @Override
    public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
        /*
        0 = normal dream
        1 = pre-lucid dream
        2 = lucid dream
        TODO: there should be a feedback to the user so he knows he chose a value, maybe when clicking the OK button in the NumberPickerDialog.class
         */

        dDreamClarity = newVal;
        Toast.makeText(this, "New Value: " + dDreamClarity, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(LOG_TAG, "OnCreate: called...");

        try {
            mFileName = this.getExternalCacheDir().getAbsolutePath() + "/recording.3gp";
        } catch (NullPointerException ex) {
            Log.d(LOG_TAG, "Could not get path mFileName");
        }
        setContentView(R.layout.fragment_dream_dialog);
        // Hide ActionBar
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        //if screen turns the fragment should not be created again
        if (savedInstanceState == null) {
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
        btnOpenDialog = findViewById(R.id.fragment_dream_dialog_btn_clarity_dialog);
        btnOpenDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showNumberPicker();
            }
        });


        mEdtText = findViewById(R.id.fragment_dream_dialog_edt_title);
        mEdtTitle = findViewById(R.id.fragment_dream_dialog_edt_text);

    }

    @Override
    protected void onStart() {
        super.onStart();

        //determine current date and build string
        Date current = Calendar.getInstance().getTime();
        mDate = new SimpleDateFormat("yyyy-MM-dd").format(current);
    }

    @Override
    public void onFinishRecording(String filePath, int timerMins, int timerSecs) {
        Log.d(LOG_TAG, "onFinishRecording: called...");

        //TODO: maybe here can be determined whether there is an audio file or not
        Toast.makeText(this, "Mins: " + timerMins + "secs " + timerSecs, Toast.LENGTH_SHORT).show();
        fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        Fragment fragment = new PlayingFragment();
        transaction.replace(R.id.fragment_dream_dialog_audio_container, fragment).commit();
    }

    private void saveDream() {
        if(mFileName != null){
            Date timeStamp = Calendar.getInstance().getTime();
            String mTimeStamp = new SimpleDateFormat("yyyy-MM-dd-HH-mm").format(timeStamp);
            //TODO: save audio file 
            File storageDir = new File (this.getFilesDir(), "/audio");




        }


    }

    public void showNumberPicker() {
        NumberPickerDialog dialog = new NumberPickerDialog();
        dialog.setValueChangeListener(this);
        dialog.show(getSupportFragmentManager(), "Clarity Picker");
    }


}
