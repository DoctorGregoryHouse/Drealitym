package com.example.ruben.drealitym.uiclasses;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.ruben.drealitym.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

public class StartScreenFragment extends Fragment {

    private String LOG_TAG = "StartScreenFragment";

    private Button btnDreamDiary;
    private CardView cvRealityChecks;

    public StartScreenFragment() {
        //Fragment needs at least one constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        Log.d(LOG_TAG, "OnCreateView: called...");
        return inflater.inflate(R.layout.fragment_start_screen, container, false);



    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        FloatingActionButton fab = view.findViewById(R.id.activity_main_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), DreamDialogActivity.class);
                intent.putExtra(DreamDialogActivity.EXTRA_REQUEST_CODE,DreamDialogActivity.REQUEST_CODE_NEW_ENTRY);
                startActivity(intent);
            }
        });

        btnDreamDiary = view.findViewById(R.id.fragment_start_screen_btn_myDiary);
        btnDreamDiary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), DreamDiaryActivity.class);
                startActivity(intent);
            }
        });

        cvRealityChecks = view.findViewById(R.id.activity_main_card_reminders);
        cvRealityChecks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), RealityCheckActivity.class);
                startActivity(intent);
            }
        });


    }
}
