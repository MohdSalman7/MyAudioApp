package com.example.dell.myaudioapp;

import android.app.Activity;
import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.SeekBar;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends Activity {
    MediaPlayer mediaPlayer;
    AudioManager audioManager;

    public  void playTapped(View view)
    {
         mediaPlayer.start();
    }
    public void pauseTapped(View view)
    {
        mediaPlayer.pause();
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       int id= getResources().getIdentifier("audio", "raw", getPackageName());
         mediaPlayer=MediaPlayer.create(this,id);

         // Getting context from audio service

         audioManager=(AudioManager)getSystemService(Context.AUDIO_SERVICE);
         int myFullVolume=audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
         int myCurrentVolume=audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);

         // Setting context values to seekbar

        SeekBar volumeRocker=findViewById(R.id.seekBar);
        volumeRocker.setMax(myFullVolume);
        volumeRocker.setProgress(myCurrentVolume);

        // Set on seekbar change listener

        volumeRocker.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
                audioManager.setStreamVolume(AudioManager.STREAM_MUSIC,progress,0);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        // setting seekbar to timeline

        final SeekBar timeline=(SeekBar) findViewById(R.id.timeline);
        timeline.setMax(mediaPlayer.getDuration());

        timeline.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
                mediaPlayer.seekTo(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                timeline.setProgress(mediaPlayer.getCurrentPosition());
            }
        },0,1000);                      

    }
}
