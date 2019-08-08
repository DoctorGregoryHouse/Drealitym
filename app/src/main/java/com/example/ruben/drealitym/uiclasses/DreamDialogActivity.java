package com.example.ruben.drealitym.uiclasses;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

import com.example.ruben.drealitym.Data.DreamDialogViewModel;
import com.example.ruben.drealitym.Data.DreamEntry;
import com.example.ruben.drealitym.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DreamDialogActivity extends AppCompatActivity implements RecordingFragment.recordingInterface, NumberPicker.OnValueChangeListener {

    //TODO: add permission request

    //CONSTANTS
    private static final String LOG_TAG = "DreamDialogActivity";

    public static final int AUDIO_FILE = 1;
    public static final int NO_AUDIO_FILE = 0;


    public static final int REQUEST_CODE_EXISTING_ENTRY = 10;
    public static final int REQUEST_CODE_NEW_ENTRY = 20;
    public static final String EXTRA_REQUEST_CODE = "com.example.ruben.drealitym.uiclasses.EXTRA_REQUEST_CODE";


    private DreamDialogViewModel viewModel;
    private FragmentManager fragmentManager;
    private Fragment audioFragment;

    //Attributes passed by intent
    private int sID;
    private int sRequestCode;
    private int sCheckFile;
    private int sDreamType;
    private int sDreamFavourite;
    private String sDreamFilePath;
    private String sDreamTitle;
    private String sDreamText;
    private String sDreamDate;


    /*
    DreamClarity defines if its lucid pre-lucid or normal dream
    DreamDate adds the date of the dream saved
    DreamHasAudioFile determines whether there is an audio file or not, if there is not an audiofile there is
    also no @mDreamFilePath
     */
    private int dDreamClarity = 0; //default value
    private String dDreamDate;
    private int dDreamHasAudioFile;
    private String dDreamFilePath;

    private EditText mEdtTitle;
    private EditText mEdtText;
    private Button btnSave;
    private Button btnOpenDialog;
    private Button btnEdit;

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
        setContentView(R.layout.activity_dream_dialog);
        Log.d(LOG_TAG, "OnCreate: called...");

        //Intent for getting extras
        Intent intent = getIntent();

        sRequestCode = intent.getIntExtra(EXTRA_REQUEST_CODE, -1);

        // Hide ActionBar
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        viewModel = ViewModelProviders.of(this).get(DreamDialogViewModel.class);
        mEdtTitle = findViewById(R.id.fragment_dream_dialog_edt_title);
        mEdtText = findViewById(R.id.fragment_dream_dialog_edt_text);

        //if screen turns the fragment should not be created again
        if (savedInstanceState == null) {
            fragmentManager = getSupportFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            audioFragment = new RecordingFragment();
            transaction.add(R.id.fragment_dream_dialog_audio_container, audioFragment).commit();
        }
        btnSave = findViewById(R.id.fragment_dream_dialog_btn_save);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveDream();
            }
        });
        btnOpenDialog = findViewById(R.id.fragment_dream_dialog_btn_clarity_dialog);
        btnOpenDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showNumberPicker();
            }
        });

        if (sRequestCode != -1 && sRequestCode == REQUEST_CODE_EXISTING_ENTRY) {
            btnOpenDialog.setVisibility(View.GONE);
            btnSave.setVisibility(View.GONE);

            intent.getIntExtra(DreamDiaryActivity.EXTRA_ID, -1);

            sCheckFile = intent.getIntExtra(DreamDiaryActivity.EXTRA_CHECKFILE, 0);
            if (sCheckFile == AUDIO_FILE) {
                replaceRecordingFragment(intent.getStringExtra(DreamDiaryActivity.EXTRA_AUDIO_PATH), 1, 1);
            } else {
                fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction().hide(audioFragment).commit();

            }

            mEdtTitle.setText(intent.getStringExtra(DreamDiaryActivity.EXTRA_TITLE));
            mEdtTitle.setEnabled(false);
            mEdtText.setText(intent.getStringExtra(DreamDiaryActivity.EXTRA_TEXT));
            mEdtText.setEnabled(false);


        }

        btnEdit = findViewById(R.id.fragment_dream_dialog_btn_edit);
        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: handle the edit click
                mEdtTitle.setEnabled(true);
                mEdtText.setEnabled(true);

                btnOpenDialog.setVisibility(View.VISIBLE);
                btnSave.setVisibility(View.VISIBLE);

                btnEdit.setVisibility(View.GONE);


                //TODO: if a file path exists, the old one should not be overriden so the audio file gets saved in place where the preceding file was stored
//                Bundle bundle = new Bundle();
//                bundle.putString(PlayingFragment.ARGUMENT_AUDIO_PATH, filePath);
//                audioFragment.setArguments(bundle);
//                transaction.replace(R.id.fragment_dream_dialog_audio_container, audioFragment).commit();

                audioFragment = new PlayingFragment();
                fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.fragment_dream_dialog_audio_container, audioFragment).commit();




            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();
        //determine current date and build string
        Date current = Calendar.getInstance().getTime();
        dDreamDate = new SimpleDateFormat("yyyy-MM-dd").format(current);

    }

    //This is the interface callback from the recording fragment
    @Override
    public void onFinishRecording(String filePath, int timerMins, int timerSecs) {
        Log.d(LOG_TAG, "onFinishRecording: called...");
        dDreamFilePath = filePath;
        //TODO: maybe here can be determined whether there is an audio file or not
        Toast.makeText(this, "Mins: " + timerMins + "secs " + timerSecs, Toast.LENGTH_SHORT).show();
        replaceRecordingFragment(filePath, timerMins, timerSecs);

    }

    private void replaceRecordingFragment(String filePath, int timerMins, int timerSecs) {
        fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        audioFragment = new PlayingFragment();
        Bundle bundle = new Bundle();
        bundle.putString(PlayingFragment.ARGUMENT_AUDIO_PATH, filePath);
        audioFragment.setArguments(bundle);
        transaction.replace(R.id.fragment_dream_dialog_audio_container, audioFragment).commit();
    }

    private void saveDream() {
        String title = mEdtTitle.getText().toString();
        String text = mEdtText.getText().toString();

        if (title.trim().isEmpty()) {
            //TODO: STRING_VALUE
            Toast.makeText(this, "Please enter a title!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (dDreamFilePath == null) {
            dDreamHasAudioFile = NO_AUDIO_FILE;
        } else {
            dDreamHasAudioFile = AUDIO_FILE;

        }
        //TODO: add int's for month year and day, change @param dDreamHasAudioFile to boolean
        DreamEntry entry = new DreamEntry(dDreamClarity, dDreamHasAudioFile, dDreamDate, title, text, 0, dDreamFilePath);
        viewModel.insert(entry);
        finish();
        //TODO: add animation to the "my diary" CardView on the main screen that shows the dream was added to the Diary. sth like a stroke lighting up in the action color
    }


    public void showNumberPicker() {
        NumberPickerDialog dialog = new NumberPickerDialog();
        dialog.setValueChangeListener(this);
        dialog.show(getSupportFragmentManager(), "Clarity Picker");
    }


}
