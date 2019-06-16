package com.example.ruben.drealitym.uiclasses;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.ruben.drealitym.HelperClasses.DreamDiaryAdapter;
import com.example.ruben.drealitym.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;


public class MainActivity extends  FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Fragment fragment = new StartScreenFragment();
        fragmentTransaction.add(R.id.main_activity_container, fragment).commit();

    }
}
