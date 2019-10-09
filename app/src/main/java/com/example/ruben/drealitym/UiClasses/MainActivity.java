package com.example.ruben.drealitym.UiClasses;


import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.ruben.drealitym.R;



public class MainActivity extends  FragmentActivity {

    private static final String TAG = "MainActivtiy";

    private CardView cvRealityChecks, cvAddDream, cvDreamDiary;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        FragmentManager fragmentManager = getSupportFragmentManager();
//        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//        Fragment fragment = new StartScreenFragment();
//        fragmentTransaction.add(R.id.main_activity_container, fragment).commit();

        cvRealityChecks = findViewById(R.id.activity_main_cv_realty_check);
        cvRealityChecks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, RealityCheckActivity.class);
                startActivity(intent);
            }
        });

        cvAddDream = findViewById(R.id.activity_main_cv_add_dream);
        cvAddDream.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, DreamDialogActivity.class);
                intent.putExtra(DreamDialogActivity.EXTRA_REQUEST_CODE,DreamDialogActivity.REQUEST_CODE_NEW_ENTRY);
                startActivity(intent);
            }
        });

        cvDreamDiary = findViewById(R.id.activity_main_cv_dream_diary);
        cvDreamDiary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, DreamDiaryActivity.class);
                startActivity(intent);
            }
        });




    }
}
