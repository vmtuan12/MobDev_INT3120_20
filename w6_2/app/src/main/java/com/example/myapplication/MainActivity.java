package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.AudioAttributes;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    Button playMusicButton;

    Button locationButton;

    Button mapButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        locationButton = (Button) findViewById(R.id.button_location);
        locationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, LocationActivity.class);
                startActivity(intent);
            }
        });

        mapButton = (Button) findViewById(R.id.button_map);
        mapButton.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, MapActivity.class));
        });

        String url = "http://commondatastorage.googleapis.com/codeskulptor-assets/Epoq-Lepidoptera.ogg";
        MediaPlayer mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioAttributes(
                new AudioAttributes
                        .Builder()
                        .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                        .build());
        try {
//            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
//                @Override
//                public void onPrepared(MediaPlayer mp) {
//                    mp.start();
//                }
//            });
            mediaPlayer.setDataSource(url);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        playMusicButton = (Button) findViewById(R.id.button_play_music);
        playMusicButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mediaPlayer.isPlaying()) {
                    mediaPlayer.stop();
                } else {
                    try {
                        mediaPlayer.prepare();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    mediaPlayer.start();
                }
            }
        });


    }
}