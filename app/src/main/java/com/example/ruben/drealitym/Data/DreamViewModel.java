package com.example.ruben.drealitym.Data;

import android.app.Application;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class DreamViewModel extends AndroidViewModel {

    private DrealitymRepository repository;
    private LiveData<List<DreamEntry>> allDreams;

    public DreamViewModel(@NonNull Application application) {
        super(application);

        repository = new DrealitymRepository(application);
        allDreams = repository.getAllDreams();

    }

    public void insert(DreamEntry dreamEntry){
        repository.insertDream(dreamEntry);
    }
    public void update(DreamEntry dreamEntry){
        repository.updateDream(dreamEntry);
    }
    public void delete(DreamEntry dreamEntry){
        repository.deleteDream(dreamEntry);
    }
    public void deleteAllDreams(){
        repository.deleteAllDreams();
    }
    public LiveData<List<DreamEntry>> getAllDreams(){
        return allDreams;
    }

}
