package com.example.ruben.drealitym.uiclasses;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.ruben.drealitym.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


public class PlayingFragment extends Fragment {

    private SeekBar seekbar;
    private ImageButton iBtnPlay;
    private TextView tvPlayTime;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_playing, container, false);

        tvPlayTime = v.findViewById(R.id.fragment_playing_tv_play_time);
        iBtnPlay = v.findViewById(R.id.fragment_playing_imgBtn_play);
        seekbar = v.findViewById(R.id.fragment_playing_seekbar);

        return  v;

    }
}
