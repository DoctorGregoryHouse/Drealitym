package com.example.ruben.drealitym.uiclasses;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ruben.drealitym.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.io.IOException;


public class PlayingFragment extends Fragment {

    private final String LOG_TAG ="PlayingFragment";

    private SeekBar seekbar;
    private ImageButton iBtnPlay;
    private TextView tvPlayTime;

    private MediaPlayer mPlayer;
    private boolean isPlaying = false;
    private String mFileName;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            mFileName = getActivity().getExternalCacheDir().getAbsolutePath()+ "/recording.3gp";
        }catch (NullPointerException ex){
            Log.d(LOG_TAG,"NullPointerException on getActivity().getExternalCacheDir().getAbsolutePath();");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_playing, container, false);

        tvPlayTime = v.findViewById(R.id.fragment_playing_tv_play_time);
        iBtnPlay = v.findViewById(R.id.fragment_playing_imgBtn_play);
        seekbar = v.findViewById(R.id.fragment_playing_seekbar);

        return  v;

    }

    private void startPlaying() {
        mPlayer = new MediaPlayer();
        try {
            mPlayer.setDataSource(mFileName);
            mPlayer.prepare();
            mPlayer.start();
        } catch (IOException e) {
            Log.e(LOG_TAG, "prepare() failed");
        }
        isPlaying = true;
        Toast.makeText(getContext(), "Playing started", Toast.LENGTH_SHORT).show();

    }

    private void stopPlaying() {
        mPlayer.release();
        mPlayer = null;
        isPlaying = false;


    }

}
