package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private SensorManager sensorManager;

    private Sensor accelerometer;

    private TextView textView;

    private Button btnWifi;

    private Button btnTele;

    private Button btnCamera;

    private Button btnVid;

    private Button btnBluetooth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.text_hello);

        btnWifi = findViewById(R.id.button_wifi);
        btnWifi.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, WifiActivity.class));
        });

        btnTele = findViewById(R.id.btn_telephony);
        btnTele.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, TelephonyActivity.class));
        });

        btnCamera = findViewById(R.id.button_camera);
        btnCamera.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, CameraActivity.class));
        });

        btnVid = findViewById(R.id.btn_video);
        btnVid.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, VideoActivity.class));
        });

        btnBluetooth = findViewById(R.id.button_bluetooth);
        btnBluetooth.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, BluetoothActivity.class));
        });

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        float x = sensorEvent.values[0];
        float y = sensorEvent.values[1];
        float z = sensorEvent.values[2];

        if (x > 5 || y > 5 || z > 5) {
            textView.setBackgroundColor(Color.RED);
        } else if (x < -5 || y < -5 || z < -5) {
            textView.setBackgroundColor(Color.GREEN);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {
//        System.out.println("sensor - " + sensor);
    }
}