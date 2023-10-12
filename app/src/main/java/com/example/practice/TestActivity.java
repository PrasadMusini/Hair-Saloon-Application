package com.example.practice;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Toast;

import java.util.Calendar;

public class TestActivity extends AppCompatActivity {

    DatePicker date_picker;
    Button date_picker_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        date_picker = findViewById(R.id.date_picker);
        date_picker_btn = findViewById(R.id.date_picker_btn);

        Calendar today = Calendar.getInstance();
        long time = today.getTimeInMillis();
        date_picker.init(1999,7,10, null);
        date_picker.setMaxDate(time);

        date_picker_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                date_picker.setVisibility(View.VISIBLE);


                Toast.makeText(TestActivity.this, "Selected Date: "+ date_picker.getDayOfMonth() + "/" +
                        (date_picker.getMonth()) + "/" + date_picker.getYear(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}


//"Selected date: " + (datePicker.getMonth()+1) + "/" + datePicker.getDayOfMonth() + "/" + datePicker.getYear()