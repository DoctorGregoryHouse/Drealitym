package com.example.ruben.drealitym.UiClasses;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.ruben.drealitym.Data.DrealitymRepository;
import com.example.ruben.drealitym.Data.DreamEntry;
import com.example.ruben.drealitym.Data.DreamViewModel;
import com.example.ruben.drealitym.HelperClasses.DreamDiaryAdapter;
import com.example.ruben.drealitym.R;

import java.util.List;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
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
    DrealitymRepository repository;
    RecyclerView recyclerView;
    DreamDiaryAdapter adapter;


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
        searchView.setSubmitButtonEnabled(true);
        searchView.setOnQueryTextListener(onQueryTextListener);


        repository = new DrealitymRepository(getApplication());

        recyclerView = findViewById(R.id.activity_dream_diary_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true); //if you know its size won't change, change this to true because it makes the view more efficient

        adapter = new DreamDiaryAdapter();
        recyclerView.setAdapter(adapter);

        dreamViewModel = ViewModelProviders.of(this).get(DreamViewModel.class);
        dreamViewModel.getAllDreams().observe(this, new Observer<List<DreamEntry>>() {
            @Override
            public void onChanged(final List<DreamEntry> dreamEntries) {
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

                    @Override
                    public void onItemLongClick(final DreamEntry entry) {
                        Toast.makeText(DreamDiaryActivity.this, "LongClick on: " + entry.getId() , Toast.LENGTH_SHORT).show();

                        final Dialog dialog = new Dialog(DreamDiaryActivity.this);
                        dialog.setContentView(R.layout.dialog_custom);
                        dialog.setTitle("Title");
                        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                        Button dialogAcceptButton = (Button) dialog.findViewById(R.id.custom_dialog_accept);
                        Button dialogDismissButton = (Button) dialog.findViewById(R.id.custom_dialog_dismiss);
                        dialogDismissButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                dialog.dismiss();

                            }
                        });
                        dialogAcceptButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                //Delete the clicked entry
                                dreamViewModel.delete(entry);
                                dialog.dismiss();
                                Toast.makeText(DreamDiaryActivity.this, "Sucessfully deleted !", Toast.LENGTH_SHORT).show();
                            }
                        });

                        dialog.show();

                    }
                });
            }
        });
    }


    private SearchView.OnQueryTextListener onQueryTextListener =
            new SearchView.OnQueryTextListener() {

                @Override
                public boolean onQueryTextSubmit(String query) {
                    //get the values from db
                    getDreamFromDb(query);
                    return true;
                }

                @Override
                public boolean onQueryTextChange(String query) {
                    getDreamFromDb(query);
                    return true;
                }

                private void getDreamFromDb(String searchText){
                    searchText = "%"+searchText+"%";
                    repository.getQueriedDreams(searchText).observe(DreamDiaryActivity.this, new Observer<List<DreamEntry>>() {
                        @Override
                        public void onChanged(List<DreamEntry> dreamEntries) {
                            if (dreamEntries==null){
                                return;
                            }
                            DreamDiaryAdapter adapter = new DreamDiaryAdapter(dreamEntries);
                            recyclerView.setAdapter(adapter);

                            //https://www.zoftino.com/android-search-functionality-using-searchview-and-room
                        }
                    });


                }
            };
}
