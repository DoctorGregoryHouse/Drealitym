package com.example.ruben.drealitym.HelperClasses;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class TimePickerFragment extends DialogFragment {

    int hour = 10;
    int minute = 10;

    //CONSTANTS
    private static final String LOG_TAG = "TimePickerFragment";

    private static final String ARGS_START_HOUR =
            "com.example.ruben.drealitym.uiclasses.start.hour";
    private static final String ARGS_START_MINUTE =
            "com.example.ruben.drealitym.uiclasses.start.minute";
    private static final String ARGS_STOP_HOUR =
            "com.example.ruben.drealitym.uiclasses.stop.hour";
    private static final String ARGS_STOP_MINUTE =
            "com.example.ruben.drealitym.uiclasses.stop.minute";

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        //return super.onCreateDialog(savedInstanceState);
        Log.d(LOG_TAG,"onCreateDialog: called...");

        //check for the bundle to exist, it should contain the start or stop time depending on what childItem is clicked on

        if (getArguments() != null) {
            if (getArguments().getInt(ARGS_STOP_HOUR) == -1) {
                hour = getArguments().getInt(ARGS_START_HOUR);
                minute = getArguments().getInt(ARGS_START_MINUTE);

            }
            if (getArguments().getInt(ARGS_START_HOUR) == -1) {
                hour = getArguments().getInt(ARGS_STOP_HOUR);
                minute = getArguments().getInt(ARGS_STOP_MINUTE);
            }
        }

        //this creates the Dialog with the corresponding timeFormat set in the phone settings

        return new TimePickerDialog(getActivity(),(TimePickerDialog.OnTimeSetListener) getActivity(),hour,minute,true /*DateFormat.is24HourFormat(getActivity())*/);
    }
}