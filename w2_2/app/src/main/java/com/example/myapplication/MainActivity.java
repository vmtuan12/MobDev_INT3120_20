package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.myapplication.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener {

    private RadioGroup radioGroupCheese;
    private RadioButton radioButtonCheese;

    private RadioGroup radioGroupShape;
    private RadioButton radioButtonShape;

    ActivityMainBinding binding;

    private String result;

    private List<String> topping = new ArrayList<>();
    private String shape = "";
    private String cheese = "";

    private int checkBoxCount = 0;

    private Button relativeButton;

    private Button tableButton;

    private Button constraintButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        radioGroupCheese = (RadioGroup) findViewById(R.id.cheese_group);
        radioGroupCheese.setOnCheckedChangeListener(this);

        radioGroupShape = (RadioGroup) findViewById(R.id.shape_group);
        radioGroupShape.setOnCheckedChangeListener(this);

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

        constraintButton = (Button) findViewById(R.id.button_constraint);
        constraintButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ConstraintActivity.class);
                startActivity(intent);
            }
        });

        result = "You have not ordered";
        binding.setOrder(result);
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int i) {
        int selectedId = radioGroup.getCheckedRadioButtonId();
        if (result.equals("You have not ordered")) {
            result = "";
        }
        if (radioGroup.getId() == R.id.cheese_group) {
            radioButtonCheese = (RadioButton) findViewById(selectedId);
            this.cheese = ("- " + radioButtonCheese.getText() + "\n");
            result += this.cheese;
        } else {
            radioButtonShape = (RadioButton) findViewById(selectedId);
            this.shape = ("- " + radioButtonShape.getText() + "\n");
            result += this.shape;
        }
        binding.setOrder(result);
    }

    public void onCheckboxClicked(View view) {
        boolean checked = ((CheckBox) view).isChecked();
        if (checked) {
            this.checkBoxCount += 1;
            this.topping.add(((CheckBox) view).getText().toString());
        } else {
            this.checkBoxCount -= 1;
            this.topping.remove(((CheckBox) view).getText().toString());
        }
        final StringBuilder finalTopping = new StringBuilder();
        if (this.topping.size() > 0) {
            finalTopping.append("- ");
            this.topping.forEach((item) -> {
                finalTopping.append(item + ", ");
            });
            finalTopping.delete(finalTopping.length() - 2, finalTopping.length());
        }
        result = this.cheese + this.shape + finalTopping.toString();
        binding.setOrder(result);
    }
}