package com.example.ruben.drealitym.UiClasses;

import android.app.AlertDialog;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.ruben.drealitym.Data.RealityCheckEntry;
import com.example.ruben.drealitym.Data.RealityCheckViewModel;
import com.example.ruben.drealitym.HelperClasses.CustomExpandableListAdapter;
import com.example.ruben.drealitym.HelperClasses.TimePickerFragment;
import com.example.ruben.drealitym.Notifications.ScheduleNotificationsService;
import com.example.ruben.drealitym.R;

import java.sql.Time;
import java.util.List;

import static android.content.Context.JOB_SCHEDULER_SERVICE;

public class RealityCheckFragment extends Fragment {

    private static final String TAG = "RealityCheckFragment";
    public static final int GET_NOTIFIED = 1;
    public static final int DONT_GET_NOTIFIED = 2;

    public static final String START_HOUR = "start_hour";
    public static final String START_MINUTE = "start_minute";
    public static final String STOP_HOUR = "stop_hour";
    public static final String STOP_MINUTE = "stop_minute";

    private ExpandableListView listview;
    private List<String> exlvTitleStrings;
    private Button sendTestNotification;

    private RealityCheckViewModel viewModel;
    protected List<RealityCheckEntry> realityCheckEntries;
    private TextView tvNetNotificationInformation;
    private Switch swEnableRealityChecks;

    private int currentGroupPosition = -1;
    private int currentChildPosition = -1;


    //Version2

    private TextView tvStart, tvStop, tvInterval;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_reality_check, container, false);
        //listview = v.findViewById(R.id.activity_reality_check_ex_lv);
        sendTestNotification = v.findViewById(R.id.activity_reality_check_send_notification_button);
        swEnableRealityChecks = v.findViewById(R.id.fragment_reality_check_sw_enable);
        tvNetNotificationInformation = v.findViewById(R.id.fragment_reality_check_tv_message);

        tvStart = v.findViewById(R.id.list_item_tv_start_value);
        tvStop = v.findViewById(R.id.list_item_tv_stop_value);
        tvInterval = v.findViewById(R.id.list_item_tv_interval_value);

        updateTextViews();

        return v;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Context context = getActivity();
//
//        exlvTitleStrings = new ArrayList<>();
//
//        //TODO: create an array in strings.mxl
//        exlvTitleStrings.add("Sunday");
//        exlvTitleStrings.add("Monday");
//        exlvTitleStrings.add("Tuesday");
//        exlvTitleStrings.add("Wednesday");
//        exlvTitleStrings.add("Thursday");
//        exlvTitleStrings.add("Friday");
//        exlvTitleStrings.add("Saturday");

        initializeClickListeners();

    }

    private void initializeClickListeners(){

        sendTestNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ComponentName componentName = new ComponentName(getActivity().getApplication(), ScheduleNotificationsService.class);
                JobInfo info = new JobInfo.Builder(1, componentName)
                        .setPersisted(true) //Job is not lost when rebooting the device
                        .setMinimumLatency(3 * 1000)
                        .build();
                JobScheduler scheduler = (JobScheduler) getActivity().getSystemService(JOB_SCHEDULER_SERVICE);
                int resultcode = scheduler.schedule(info);
                if (resultcode == JobScheduler.RESULT_SUCCESS) {
                    Log.d(TAG, "onClick: Job scheduled");
                } else {
                    Log.d(TAG, "onClick: Job scheduling failed");
                }


                Toast.makeText(getActivity(), "Send Testnotification", Toast.LENGTH_SHORT).show();
            }
        });
//
//        CustomExpandableListAdapter listAdapter = new CustomExpandableListAdapter(getContext(),exlvTitleStrings);
//        initializeAdapter(listAdapter);

        tvStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                initiateTimePickerListener(1);

            }
        });
        tvStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                initiateTimePickerListener(2);

            }
        });
        tvInterval.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder mBuilder = new AlertDialog.Builder(getActivity());
                View mView = getLayoutInflater().inflate(R.layout.dialog_spinner, null);
                mBuilder.setTitle("Choose the Interval of the notifications");
                final Spinner mSpinner = mView.findViewById(R.id.dialog_spinner);
                ArrayAdapter<String> mAdapter = new ArrayAdapter<>(getContext(),
                        android.R.layout.simple_spinner_item,
                        getResources().getStringArray(R.array.spinner_values_intervals));
                mSpinner.setAdapter(mAdapter);


                mBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //this is called when the item is chosen
                        Toast.makeText(getActivity(),
                                mSpinner.getSelectedItem().toString(),
                                Toast.LENGTH_LONG).show();
                        //TODO: update the shared preference
                        dialogInterface.dismiss();
                    }
                });
                mBuilder.setNegativeButton("Dismiss", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();

                    }
                });
                mBuilder.setView(mView); //sets the custom layout to the AlertDialog.Builder
                AlertDialog dialog = mBuilder.create();
                dialog.show();

            }
        });


    }

    private void initiateTimePickerListener(int selector){
        Bundle bundle = new Bundle();
        bundle.putInt("selector",selector);
        TimePickerFragment dialog = new TimePickerFragment();
        dialog.setArguments(bundle);
        if (selector == 2){
            dialog.show(getFragmentManager(), "timePicker_stop");
        }else{
            dialog.show(getFragmentManager(), "timePicker_start");
        }
        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                updateTextViews();
            }
        });

    }

    private String formatTime(int hour, int minute){

        StringBuilder builder = new StringBuilder();

        if (hour < 10){
            builder.append("0");
        }
        builder.append(hour);
        builder.append(":");
        if (minute < 10) {
            builder.append("0");
        }
        builder.append(minute);

        return builder.toString();

    }

    public void updateTextViews(){


        SharedPreferences sharedPrefs = getContext().getSharedPreferences(
                getString(R.string.preference_file_reality_check), Context.MODE_PRIVATE);


        int startHour = sharedPrefs.getInt(START_HOUR,8);
        int startMinute = sharedPrefs.getInt(START_MINUTE, 0);

        int stopHour = sharedPrefs.getInt(STOP_HOUR, 21);
        int stopMinute = sharedPrefs.getInt(STOP_MINUTE, 0);

        tvStart.setText(formatTime(startHour,startMinute));
        tvStop.setText(formatTime(stopHour, stopMinute));
        tvInterval.setText("dummyText");


    }


    //old code
    private void initializeAdapter(final CustomExpandableListAdapter adapter) {

        viewModel = ViewModelProviders.of(this).get(RealityCheckViewModel.class);
        viewModel.getAllRealityChecks().observe(getActivity(), new Observer<List<RealityCheckEntry>>() {
            @Override
            public void onChanged(List<RealityCheckEntry> entries) {
                Log.d(TAG, "onChange called...");
                realityCheckEntries = entries;
                adapter.setRealityCheckEntries(realityCheckEntries);
                listview.setAdapter(adapter);

                adapter.setOnItemClickListener(new CustomExpandableListAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(int groupPosition, int childPosition) {

                        currentChildPosition = childPosition;
                        currentGroupPosition = groupPosition;

                        //TimePickerFragment timePickerFragment = new TimePickerFragment();
                        //timePickerFragment.show(getContext(), "timePicker");


                    }

                    @Override
                    public void onItemClick(final int groupPosition, int childPosition, boolean clickedInterval) {

                        //Start Spinner
                        AlertDialog.Builder mBuilder = new AlertDialog.Builder(getActivity());
                        View mView = getLayoutInflater().inflate(R.layout.dialog_spinner, null);
                        mBuilder.setTitle("Choose how often you wanna make a reality check");
                        final Spinner mSpinner = (Spinner) mView.findViewById(R.id.dialog_spinner);
                        ArrayAdapter<String> mAdapter = new ArrayAdapter<>(getActivity(),
                                android.R.layout.simple_spinner_item,
                                getResources().getStringArray(R.array.spinner_values_intervals));
                        mAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        mSpinner.setAdapter(mAdapter);

                        mBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //this is called when the item is chosen
                                Toast.makeText(getActivity(),
                                        mSpinner.getSelectedItem().toString(),
                                        Toast.LENGTH_LONG).show();
                                updateIntervalInDatabase(groupPosition, mSpinner.getSelectedItemPosition());
                                Log.d(TAG, "Selected item spinner: " + mSpinner.getSelectedItemPosition());
                                dialogInterface.dismiss();
                            }
                        });
                        mBuilder.setNegativeButton("Dismiss", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();

                            }
                        });
                        mBuilder.setView(mView); //sets the custom layout to the AlertDialog.Builder
                        AlertDialog dialog = mBuilder.create();
                        dialog.show();

                    }

                    @Override
                    public void onItemSwitchClick(int groupPosition, int getNotified) {

                        RealityCheckEntry oldEntry = RealityCheckFragment.this.realityCheckEntries.get(groupPosition);
                        RealityCheckEntry newEntry;
                        if (getNotified == 2) {
                            newEntry = new RealityCheckEntry(oldEntry.getStartHour(),
                                    oldEntry.getStartMinute(),
                                    oldEntry.getStopHour(),
                                    oldEntry.getStopMinute(),
                                    oldEntry.getInterval(),
                                    1);


                            newEntry.setId(groupPosition + 1);
                        } else {
                            newEntry = new RealityCheckEntry(oldEntry.getStartHour(),
                                    oldEntry.getStartMinute(),
                                    oldEntry.getStopHour(),
                                    oldEntry.getStopMinute(),
                                    oldEntry.getInterval(),
                                    2);
                            newEntry.setId(groupPosition + 1);
                        }
                        viewModel.update(newEntry);
                        Log.d(TAG, "GroupPosition: " + groupPosition + " isChecked: " + newEntry.getNotification());

                    }
                });
            }
        });

    }

    private void updateIntervalInDatabase(int groupPosition, int interval) {
        RealityCheckEntry oldEntry = realityCheckEntries.get(groupPosition);
        RealityCheckEntry newEntry = new RealityCheckEntry(oldEntry.getStartHour(),
                oldEntry.getStartMinute(),
                oldEntry.getStopHour(),
                oldEntry.getStopMinute(),
                interval,
                oldEntry.getNotification());
        newEntry.setId(groupPosition + 1);
        viewModel.update(newEntry);
    }

}