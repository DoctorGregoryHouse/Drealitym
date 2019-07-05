package com.example.ruben.drealitym.uiclasses;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.ruben.drealitym.R;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class ProfileActivity extends AppCompatActivity {
    private CardView cvRealityChecks;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        // Hide ActionBar
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        cvRealityChecks = findViewById(R.id.activity_profile_cv_reality_checks);
        cvRealityChecks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                Fragment fragment = new RealityCheckFragment();
                transaction.add(R.id.activity_profile_container, fragment).commit();
                Toast.makeText(ProfileActivity.this, "Swag", Toast.LENGTH_SHORT).show();
            }
        });
    }


}
