package com.example.ruben.drealitym.uiclasses;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ruben.drealitym.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;



public class DreamDialogFragment extends DialogFragment {

    private String LOG_TAG = "DreamDialogFragment";

    public DreamDialogFragment(){

        //Fragment needs constructor

    }



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_dream_dialog, container, false);
    }
}
