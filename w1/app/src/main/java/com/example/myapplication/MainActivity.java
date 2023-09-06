package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;
import android.widget.NumberPicker;

import com.example.myapplication.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private NumberPicker numberPicker;

    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        int amount = 0;
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
        numberPicker.setOnValueChangedListener((picker, oldVal, newVal) -> {

        });
    }
}