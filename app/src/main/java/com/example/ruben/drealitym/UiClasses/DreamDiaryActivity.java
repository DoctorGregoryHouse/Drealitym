package com.example.ruben.drealitym.UiClasses;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.SearchView;
import android.widget.Toast;
import android.widget.Toolbar;

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

public class DreamDiaryActivity extends AppCompatActivity /*implements DreamDiaryAdapter.OnItemClickListener */{


    //CONSTANTS
    private final String LOG_TAG = "DreamDiaryActivity";

    public static final String EXTRA_ID ="com.example.ruben.drealitym.uiclasses.EXTRA_ID";
    public static final String EXTRA_TYPE = "com.example.ruben.drealitym.uiclasses.EXTRA_TYPE";
    public static final String EXTRA_CHECKFILE = "com.example.ruben.drealitym.uiclasses.CHECK_FILE";
    public static final String EXTRA_DATE = "com.example.ruben.drealitym.uiclasses.EXTRA_DATE";
    public static final String EXTRA_TITLE = "com.example.ruben.drealitym.uiclasses.EXTRA_TITLE";
    public static final String EXTRA_TEXT = "com.example.ruben.drealitym.uiclasses.EXTRA_TEXT";
    public static final String EXTRA_AUDIO_PATH = "com.example.ruben.drealitym.uiclasses.EXTRA_AUDIO_PATH";
    public static final String EXTRA_FAVOURITE = "com.example.ruben.drealitym.uiclasses.EXTRA_FAVOURITE";

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

        SearchView searchView = findViewById(R.id.dream_diary_searchView);




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
                adapter.setOnItemClickListener(new DreamDiaryAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(DreamEntry entry) {

                        Intent intent = new Intent(DreamDiaryActivity.this, DreamDialogActivity.class);
                        //this indicates that the dream is not a new dream so the data can be loaded into the called activity
                        intent.putExtra(DreamDialogActivity.EXTRA_REQUEST_CODE,DreamDialogActivity.REQUEST_CODE_EXISTING_ENTRY);

                        intent.putExtra(EXTRA_ID,entry.getId());
                        intent.putExtra(EXTRA_TYPE,entry.getType());
                        intent.putExtra(EXTRA_CHECKFILE,entry.getCheckFile());
                        intent.putExtra(EXTRA_DATE,entry.getDate());
                        intent.putExtra(EXTRA_TITLE,entry.getTitle());
                        intent.putExtra(EXTRA_TEXT, entry.getText());
                        intent.putExtra(EXTRA_FAVOURITE, entry.getFavourite());

                        if(entry.getCheckFile() == DreamDialogActivity.AUDIO_FILE ){
                            intent.putExtra(EXTRA_AUDIO_PATH,entry.getAudioPath());
                        }
                        startActivity(intent);


                    }
                });
                Toast.makeText(DreamDiaryActivity.this, "onChanged", Toast.LENGTH_SHORT).show();
            }
        });
    }



}
