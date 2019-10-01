package com.example.ruben.drealitym.Data;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class RealityCheckViewModel extends AndroidViewModel {

    private static final String LOG_TAG = "RealityCheckViewModel";

    private DrealitymRepository repository;
    private LiveData<List<RealityCheckEntry>> allRealityChecks;
    private List<RealityCheckEntry> staticRealityCheckList;

    public RealityCheckViewModel(@NonNull Application application){
        super(application);

        repository = new DrealitymRepository(application);
        allRealityChecks = repository.getAllRealityChecks();
        staticRealityCheckList = repository.getStaticRealityCheckList();
        Log.d(LOG_TAG,"swag");
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

    public List<RealityCheckEntry> getStaticRealityCheckList() {return staticRealityCheckList; }

}
