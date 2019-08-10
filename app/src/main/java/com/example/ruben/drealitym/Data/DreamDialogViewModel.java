package com.example.ruben.drealitym.Data;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

public class DreamDialogViewModel extends AndroidViewModel {

    private DrealitymRepository repository;

    public DreamDialogViewModel(@NonNull Application application) {
        super(application);
        repository = new DrealitymRepository(application);
    }

    public void insert(DreamEntry dreamEntry) {repository.insertDream(dreamEntry);}

    public void update(DreamEntry dreamEntry) {repository.updateDream(dreamEntry);}

}
