package com.example.ruben.drealitym.UiClasses;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ruben.drealitym.Data.DrealitymRepository;
import com.example.ruben.drealitym.Data.DreamEntry;
import com.example.ruben.drealitym.Data.DreamViewModel;
import com.example.ruben.drealitym.HelperClasses.DreamDiaryAdapter;
import com.example.ruben.drealitym.R;

import java.util.List;

public class DreamDiaryFragment extends Fragment {

    //CONSTANTS
    private final String LOG_TAG = "DreamDiaryFragment";

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
    private SearchView searchview;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_dream_diary, container, false);
        searchview = v.findViewById(R.id.dream_diary_searchView);
        recyclerView = v.findViewById(R.id.activity_dream_diary_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(true);

        adapter = new DreamDiaryAdapter();
        recyclerView.setAdapter(adapter);

        repository = new DrealitymRepository(getActivity().getApplication());
        dreamViewModel = ViewModelProviders.of(this).get(DreamViewModel.class);
        dreamViewModel.getAllDreams().observe(this, new Observer<List<DreamEntry>>() {
            @Override
            public void onChanged(List<DreamEntry> dreamEntries) {
                adapter.setDreams(dreamEntries);
                setListener(adapter);
            }
        });

        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        searchview.setSubmitButtonEnabled(true);
        searchview.setOnQueryTextListener(onQueryTextListener);



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
                    repository.getQueriedDreams(searchText).observe(getActivity(), new Observer<List<DreamEntry>>() {
                        @Override
                        public void onChanged(List<DreamEntry> dreamEntries) {
                            if (dreamEntries==null){
                                return;
                            }
                            DreamDiaryAdapter adapter = new DreamDiaryAdapter(dreamEntries);
                            recyclerView.setAdapter(adapter);
                            setListener(adapter);

                            //https://www.zoftino.com/android-search-functionality-using-searchview-and-room
                        }
                    });


                }
            };

    private void setListener(DreamDiaryAdapter adapter){

        adapter.setOnItemClickListener(new DreamDiaryAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(DreamEntry entry) {

                Intent intent = new Intent(getActivity(), DreamDialogActivity.class);
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
                Toast.makeText(getActivity(), "LongClick on: " + entry.getId() , Toast.LENGTH_SHORT).show();

                final Dialog dialog = new Dialog(getActivity());
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
                        Toast.makeText(getActivity(), "Sucessfully deleted !", Toast.LENGTH_SHORT).show();
                    }
                });

                dialog.show();

            }
        });
    }



}
