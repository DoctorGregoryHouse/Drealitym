package com.example.ruben.drealitym.uiclasses;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.ruben.drealitym.HelperClasses.RealityCheckAdapter;
import com.example.ruben.drealitym.R;

import java.util.ArrayList;
import java.util.List;


public class RealityCheckFragment extends Fragment {
    private static final String LOG_TAG = "RealityCheckFragment";


    ExpandableListView mDayListView;
    ExpandableListAdapter mAdapter;
    List<String> mDays;
    List<String> dummyData;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mDays = new ArrayList<>();
        dummyData = new ArrayList<>();

        mDays.add("Monday");
        mDays.add("Tuesday");
        mDays.add("Wednesday");
        mDays.add("Thursday");
        mDays.add("Friday");
        mDays.add("Saturday");
        mDays.add("Sunday");

        dummyData.add("Zeile 1");
        dummyData.add("Zeile 2");
        dummyData.add("Zeile 3");

        Log.d(LOG_TAG,"onCreateView: called");

        View view = inflater.inflate(R.layout.fragment_reality_check, container, false);
        mDayListView = view.findViewById(R.id.fragment_ex_lv_reality_check);

        mAdapter = new RealityCheckAdapter(getContext(),mDays,dummyData);
        mDayListView.setAdapter(mAdapter);

        mDayListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {

            }
        });


        return view;

    }


}
