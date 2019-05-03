package com.example.ruben.drealitym.uiclasses;

import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.example.ruben.drealitym.R;

import java.util.Calendar;


public class FragmentReminderPicker extends DialogFragment implements TimePickerDialog.OnTimeSetListener {




    //interface to communicate with the underlying activity
    //SEE ON_ATTACH
    public interface FragmentReminderListener {

        /**
         *
         * @param name
         * @param startHour
         * @param startMin
         * @param stopHour
         * @param stopMin
         * @param days
         * @param interval
         */

        void onInputSent(String name, int startHour, int startMin, int stopHour, int stopMin, int[] days, int interval);
    }

    private FragmentReminderListener listener;


    //this bool tells whether there was a bundle or not
    boolean editMode;


    EditText editTextName;
    String name = "";

    EditText editTextStart;
    EditText editTextStop;
    View clickedEditText;

    int startHour = 0;
    int startMin = 0;
    int stopHour = 0;
    int stopMin = 0;


    Button saveButton;
    Button discardButton;

    //values for the ReminderObject


    int[] dayValues;
    int interval;


    //This method attaches the listener to the underlying activity
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        //if statement checks whether the underlying activity has implemented the FragmentReminderListener interface
        if (context instanceof FragmentReminderListener){

            listener = (FragmentReminderListener) context;
        }else {
            throw new RuntimeException(context.toString() + " must implement FragmenReminderListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();

        //when the fragment gets detached from the activity, the listener won't be needed anymore
        listener = null;
    }

    //this method initializes the views.
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_add_reminders, container, false);

    }



    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (savedInstanceState != null) {
            editMode = true;
        } else editMode = false;

        if (editMode) {
            //TODO: get values from bundle
        } else {
            dayValues = new int[]{0, 0, 0, 0, 0, 0, 0};
        }

        //SAVE BUTTON
        saveButton = view.findViewById(R.id.reminder_fragment_button_save);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /**
                 * @param name
                 * @param startHour
                 * @param startMin
                 * @param stopHour
                 * @param stopMin
                 * @param days
                 * @param interval
                 */


                listener.onInputSent(name, startHour, startMin, stopHour, stopMin, dayValues, interval );

                getFragmentManager().popBackStack();

            }
        });

        //DISCARD BUTTON
        discardButton = view.findViewById(R.id.reminder_fragment_button_discard);
        discardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().popBackStack();

            }
        });

        /**
         * TOGGLE_BUTTON LISTENER
         */

        //creates a listener for every ToggleButton
        ToggleButton.OnCheckedChangeListener multilistener = new ToggleButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                switch (buttonView.getId()) {

                    case R.id.reminder_fragment_toggle_button_monday:
                        if (dayValues[0] == 1) {
                            dayValues[0] = 0;
                        } else {
                            dayValues[0] = 1;
                        }
                        break;
                    case R.id.reminder_fragment_toggle_button_tuesday:
                        if (dayValues[1] == 1) {
                            dayValues[1] = 0;
                        } else {
                            dayValues[1] = 1;
                        }
                        break;
                    case R.id.reminder_fragment_toggle_button_wednesday:
                        if (dayValues[2] == 1) {
                            dayValues[2] = 0;
                        } else {
                            dayValues[2] = 1;
                        }
                        break;
                    case R.id.reminder_fragment_toggle_button_thursday:
                        if (dayValues[3] == 1) {
                            dayValues[3] = 0;
                        } else {
                            dayValues[3] = 1;
                        }
                        break;
                    case R.id.reminder_fragment_toggle_button_friday:
                        if (dayValues[4] == 1) {
                            dayValues[4] = 0;
                        } else {
                            dayValues[4] = 1;
                        }
                        break;
                    case R.id.reminder_fragment_toggle_button_saturday:
                        if (dayValues[5] == 1) {
                            dayValues[5] = 0;
                        } else {
                            dayValues[5] = 1;
                        }
                        break;
                    case R.id.reminder_fragment_toggle_button_sunday:
                        if (dayValues[6] == 1) {
                            dayValues[6] = 0;
                        } else {
                            dayValues[6] = 1;
                        }
                        break;

                }

            }
        };

        ToggleButton[] days = new ToggleButton[7];

        days[0] = view.findViewById(R.id.reminder_fragment_toggle_button_monday);
        days[0].setOnCheckedChangeListener(multilistener);

        days[1] = view.findViewById(R.id.reminder_fragment_toggle_button_tuesday);
        days[1].setOnCheckedChangeListener(multilistener);

        days[2] = view.findViewById(R.id.reminder_fragment_toggle_button_wednesday);
        days[2].setOnCheckedChangeListener(multilistener);

        days[3] = view.findViewById(R.id.reminder_fragment_toggle_button_thursday);
        days[3].setOnCheckedChangeListener(multilistener);

        days[4] = view.findViewById(R.id.reminder_fragment_toggle_button_friday);
        days[4].setOnCheckedChangeListener(multilistener);

        days[5] = view.findViewById(R.id.reminder_fragment_toggle_button_saturday);
        days[5].setOnCheckedChangeListener(multilistener);

        days[6] = view.findViewById(R.id.reminder_fragment_toggle_button_sunday);
        days[6].setOnCheckedChangeListener(multilistener);


        Spinner intervalSpinner = view.findViewById(R.id.reminder_fragment_spinner_interval);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(), R.array.reminders_fragment_spinner_array, android.R.layout.simple_spinner_item);
        //Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Apply the adapter to the spinner
        intervalSpinner.setAdapter(adapter);

        intervalSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                /**
                 * 20 min   0
                 * 40 min   1
                 * 1 h      2
                 * 2 h      3
                 * 3 h...8h
                 * these are the values of the spinner.
                 */
                interval = position;

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

                //default value = 1 hour
                interval = 2;
            }
        });

        editTextStart = view.findViewById(R.id.reminder_fragment_start_time_edit_text);
        editTextStop = view.findViewById(R.id.reminder_fragment_stop_time_edit_text);

        editTextStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new TimePickerDialog(getActivity(), FragmentReminderPicker.this, 0, 0, false).show();
                clickedEditText = v;



            }
        });

        editTextStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //opens the TimePickerDialog. the the "textIsSelectable" line in the XML file lets vanish the keyboard
                new TimePickerDialog(getActivity(), FragmentReminderPicker.this,0, 0, false).show();
                clickedEditText = v;
                //TODO: when the activity is reopened, the startTime must stay on the time set before




            }
        });

        editTextName = view.findViewById(R.id.reminder_fragment_edit_text_name);
       name =  editTextName.getText().toString();

    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {


            if (clickedEditText.getId() == editTextStart.getId()){

                startHour = hourOfDay;
                startMin = minute;

                editTextStart.setText(hourOfDay + ":" + minute);}
            else {
                editTextStop.setText(hourOfDay + ":" + minute);

                stopHour = hourOfDay;
                stopMin = minute;
            }
            clickedEditText = null;




    }

}



