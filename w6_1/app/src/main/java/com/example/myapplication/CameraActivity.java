package com.example.myapplication;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Camera;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.ImageView;

import java.util.Objects;

public class CameraActivity extends AppCompatActivity {

    private Button btnIntentCam;

    private Button btnMain;

    private Button btnRawCam;

    private ImageView imageView;

    private final int TAKE_PICTURE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

        btnIntentCam = findViewById(R.id.btn_intent_cam);
        btnMain = findViewById(R.id.btn_main_from_cam);
        imageView = findViewById(R.id.imageView);
        btnRawCam = findViewById(R.id.btn_raw_cam);

        btnIntentCam.setOnClickListener(v -> {
            if (ActivityCompat.checkSelfPermission(CameraActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(CameraActivity.this, new String[]{Manifest.permission.CAMERA}, 1);
            }
            startActivityForResult(new Intent(MediaStore.ACTION_IMAGE_CAPTURE), TAKE_PICTURE);
        });

        btnMain.setOnClickListener(v -> {
            startActivity(new Intent(CameraActivity.this, MainActivity.class));
        });

        btnRawCam.setOnClickListener(v -> {
            startActivity(new Intent(CameraActivity.this, Camera2Activity.class));

        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && requestCode == TAKE_PICTURE && data != null) {
            Bitmap photo = (Bitmap) Objects.requireNonNull(data.getExtras()).get("data");
            imageView.setImageBitmap(photo);
        }
    }


}