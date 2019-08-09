package com.example.ruben.drealitym.uiclasses;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
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

    //CONSTANTS
    private final String LOG_TAG ="PlayingFragment";

    public final static String ARGUMENT_AUDIO_PATH = "com.example.ruben.drealitym.uiclasses.ARGUMENT_AUDIO_PATH";


    private SeekBar mSeekBar;
    private ImageButton iBtnPlay;
    private TextView tvPlayTime;

    private MediaPlayer mPlayer;
    private boolean isPlaying = false;
    private String mFilePath;

    private Handler mSeekBarUpdateHandler;
    private Runnable mUpdateSeekBar = new Runnable() {
        @Override
        public void run() {
            mSeekBar.setProgress(mPlayer.getCurrentPosition());
            mSeekBarUpdateHandler.postDelayed(this,50);
        }
    };

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments() != null){
            mFilePath = getArguments().getString(ARGUMENT_AUDIO_PATH);
        }else{
            Log.e(LOG_TAG,"getArguments = null, could not get string path");

        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_playing, container, false);

        tvPlayTime = v.findViewById(R.id.fragment_playing_tv_play_time);
        iBtnPlay = v.findViewById(R.id.fragment_playing_imgBtn_play);
        mSeekBar = v.findViewById(R.id.fragment_playing_seekbar);

        return  v;

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mSeekBarUpdateHandler = new Handler();
        mPlayer = new MediaPlayer();
        try {
            mPlayer.setDataSource(mFilePath);
            mPlayer.prepare();

            initializeSeekBar();


        } catch (IOException e) {
            Log.e(LOG_TAG, "prepare() failed");
        }


        iBtnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isPlaying){
                    startPlaying();
                }else {
                    //stopPlaying();
                    pausePlaying();
                }
            }
        });

        mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if(mPlayer!=null && fromUser){
                    mPlayer.seekTo(progress);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        //when finished playing the audio file, this method triggers and set @param isPlaying to false, it also change Icon to play drawable
        mPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                isPlaying=false;
                iBtnPlay.setImageResource(R.drawable.ic_play_arrow_48dp);
            }
        });



    }

    private void startPlaying() {
        mPlayer.start();
        isPlaying = true;
        Toast.makeText(getContext(), "Playing started", Toast.LENGTH_SHORT).show();
        iBtnPlay.setImageResource(R.drawable.ic_pause_48dp);
        mSeekBarUpdateHandler.postDelayed(mUpdateSeekBar,0);
    }

    //TODO: wenn das fragment zerstört wird soll der mediaplayer auch zerstört werden
    private void stopPlaying() {
        mPlayer.release();
        mPlayer = null;
        isPlaying = false;
        if(mSeekBarUpdateHandler!=null){
            mSeekBarUpdateHandler.removeCallbacks(mUpdateSeekBar);
        }
    }

    private void pausePlaying(){
        mPlayer.pause();
        isPlaying=false;
        iBtnPlay.setImageResource(R.drawable.ic_play_arrow_48dp);
        if(mSeekBarUpdateHandler!=null){
            mSeekBarUpdateHandler.removeCallbacks(mUpdateSeekBar);
        }
    }


    private void initializeSeekBar(){
        mSeekBar.setMax(mPlayer.getDuration());
        mSeekBarUpdateHandler.postDelayed(mUpdateSeekBar,0);
    }

}
