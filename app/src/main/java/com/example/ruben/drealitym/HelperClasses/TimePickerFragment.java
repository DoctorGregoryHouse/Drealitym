package com.example.ruben.drealitym.HelperClasses;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.ruben.drealitym.R;
import com.example.ruben.drealitym.UiClasses.RealityCheckFragment;

import static com.example.ruben.drealitym.UiClasses.RealityCheckFragment.ARGS_SELECTOR;
import static com.example.ruben.drealitym.UiClasses.RealityCheckFragment.ARGS_START_HOUR;
import static com.example.ruben.drealitym.UiClasses.RealityCheckFragment.ARGS_START_MINUTE;
import static com.example.ruben.drealitym.UiClasses.RealityCheckFragment.ARGS_STOP_HOUR;
import static com.example.ruben.drealitym.UiClasses.RealityCheckFragment.ARGS_STOP_MINUTE;
import static com.example.ruben.drealitym.UiClasses.RealityCheckFragment.TIMEPICKER_START;
import static com.example.ruben.drealitym.UiClasses.RealityCheckFragment.TIMEPICKER_STOP;

public class TimePickerFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener {

    int hour = 10;
    int minute = 10;

    public static final String LOG_TAG = "TimePickerFragment";

    private DialogInterface.OnDismissListener onDismissListener;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        //return super.onCreateDialog(savedInstanceState);
        Log.d(LOG_TAG,"onCreateDialog: called...");


        //check for the bundle to exist, it should contain the start or stop time depending on what childItem is clicked on
        //TODO: update values
        if (getArguments() != null) {
            if (getArguments().getInt(ARGS_SELECTOR) == TIMEPICKER_START) {
                hour = getArguments().getInt(ARGS_START_HOUR);
                minute = getArguments().getInt(ARGS_START_MINUTE);

            }else if(getArguments().getInt(ARGS_SELECTOR) == TIMEPICKER_STOP){
                hour = getArguments().getInt(ARGS_STOP_HOUR);
                minute = getArguments().getInt(ARGS_STOP_MINUTE);

            }
        }




        //this creates the Dialog with the corresponding timeFormat set in the phone settings

        return new TimePickerDialog(getActivity(),this,hour,minute,true /*DateFormat.is24HourFormat(getActivity())*/);
    }


    public void setOnDismissListener(DialogInterface.OnDismissListener onDismissListener) {
        this.onDismissListener = onDismissListener;
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        //interface to the RealtyCheckFragment due to update of TextView after dismiss dialog
        if(onDismissListener != null){
            onDismissListener.onDismiss(dialog);
        }
    }

    @Override
    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {


        SharedPreferences sharedPrefs = getContext().getSharedPreferences(
                getString(R.string.preference_file_reality_check), Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedPrefs.edit();

        int selector = getArguments().getInt("selector");

        if (selector == 1) {
            editor.putInt(RealityCheckFragment.START_HOUR, hourOfDay);
            editor.putInt(RealityCheckFragment.START_MINUTE, minute);
            editor.commit();


        }else if(selector == 2){
            editor.putInt(RealityCheckFragment.STOP_HOUR, hourOfDay);
            editor.putInt(RealityCheckFragment.STOP_MINUTE, minute);
            editor.commit();
        }
    }
}
