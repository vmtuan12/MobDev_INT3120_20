package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;

public class TableActivity extends AppCompatActivity {

    private NumberPicker numberPicker;

    private Button mainActivityButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table);

        numberPicker = findViewById(R.id.numberPicker);
        numberPicker.setEnabled(true);
        numberPicker.setFocusable(false);
        numberPicker.setClickable(false);
        numberPicker.setFocusableInTouchMode(false);
        numberPicker.setWrapSelectorWheel(true);
        numberPicker.setMinValue(0);
        numberPicker.setMaxValue(1000);
        numberPicker.setValue(999);

        mainActivityButton = (Button) findViewById(R.id.button_main);
        mainActivityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TableActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}