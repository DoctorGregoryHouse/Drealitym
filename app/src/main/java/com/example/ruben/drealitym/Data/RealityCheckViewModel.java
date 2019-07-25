package com.example.ruben.drealitym.Data;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class RealityCheckViewModel extends AndroidViewModel {

    private DrealitymRepository repository;
    private LiveData<List<RealityCheckEntry>> allRealityChecks;

    public RealityCheckViewModel(@NonNull Application application){
        super(application);

        repository = new DrealitymRepository(application);
        allRealityChecks = repository.getAllRealityChecks();
    }

    public void insert(RealityCheckEntry realityCheckEntry){
        repository.insertRealityCheck(realityCheckEntry);
    }
    public void update(RealityCheckEntry realityCheckEntry){
        repository.updateRealtiyCheck(realityCheckEntry);
    }
    public void delete(RealityCheckEntry realityCheckEntry){
        repository.deleteRealityCheck(realityCheckEntry);
    }
    public void deleteAllRealityChecks(){
        repository.deleteAllRealityChecks();
    }
    public LiveData<List<RealityCheckEntry>> getAllRealityChecks(){
        return allRealityChecks;
    }




}
