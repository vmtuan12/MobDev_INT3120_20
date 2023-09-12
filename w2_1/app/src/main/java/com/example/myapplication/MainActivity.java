package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;

import com.example.myapplication.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private NumberPicker numberPicker;

    private EditText editText;

    private Button linearButton;

    private Button relativeButton;

    private Button tableButton;

    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        int amount = 0;

        editText = (EditText) findViewById(R.id.editTextText2);

        binding.setAmount(amount);
        binding.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String sText = binding.editTextText2.getText().toString().trim();
                Integer addedAmount = Integer.parseInt(sText);
                binding.setAmount(binding.getAmount() + addedAmount);
            }
        });

        numberPicker = findViewById(R.id.numberPicker);
        numberPicker.setEnabled(true);
        numberPicker.setFocusable(false);
        numberPicker.setClickable(false);
        numberPicker.setFocusableInTouchMode(false);
        numberPicker.setWrapSelectorWheel(true);
        numberPicker.setMinValue(0);
        numberPicker.setMaxValue(1000);
        numberPicker.setValue(999);

        linearButton = (Button) findViewById(R.id.button_linear);
        linearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, LinearActivity.class);
                startActivity(intent);
            }
        });

        relativeButton = (Button) findViewById(R.id.button_relative);
        relativeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, RelativeActivity.class);
                startActivity(intent);
            }
        });

        tableButton = (Button) findViewById(R.id.button_table);
        tableButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, TableActivity.class);
                startActivity(intent);
            }
        });
    }
}