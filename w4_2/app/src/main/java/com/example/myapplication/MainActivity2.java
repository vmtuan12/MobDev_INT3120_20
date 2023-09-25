package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

public class MainActivity2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        SharedPreferences sharedPref = this.getSharedPreferences("mysetting", 0);
        int highScore = sharedPref.getInt("high score", 1);
        String score = String.valueOf(highScore);
        Toast.makeText(MainActivity2.this, score, Toast.LENGTH_SHORT).show();

    }
}