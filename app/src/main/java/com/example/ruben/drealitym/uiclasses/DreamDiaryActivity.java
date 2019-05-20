package com.example.ruben.drealitym.uiclasses;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.widget.Toast;

import com.example.ruben.drealitym.Data.DreamEntry;
import com.example.ruben.drealitym.Data.DreamViewModel;
import com.example.ruben.drealitym.HelperClasses.DreamDiaryAdapter;
import com.example.ruben.drealitym.R;

import java.util.List;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class DreamDiaryActivity extends AppCompatActivity {
    private final String LOG_TAG = "DreamDiaryActivity";
    private DreamViewModel dreamViewModel;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dream_diary);
        Log.d(LOG_TAG, "onCreate called...");

        //hide actionBar
        if (getSupportActionBar() != null){
            getSupportActionBar().hide();
        }


        RecyclerView recyclerView = findViewById(R.id.activity_dream_diary_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true); //if you know its size won't change, change this to true because it makes the view more efficient

        final DreamDiaryAdapter adapter = new DreamDiaryAdapter();
        recyclerView.setAdapter(adapter);

        dreamViewModel = ViewModelProviders.of(this).get(DreamViewModel.class);
        dreamViewModel.getAllDreams().observe(this, new Observer<List<DreamEntry>>() {
            @Override
            public void onChanged(List<DreamEntry> dreamEntries) {
                //here update the recyclerview
                adapter.setDreams(dreamEntries);
                Toast.makeText(DreamDiaryActivity.this, "onChanged", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
