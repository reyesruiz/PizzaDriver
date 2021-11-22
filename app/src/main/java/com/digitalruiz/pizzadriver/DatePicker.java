package com.digitalruiz.pizzadriver;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;


public class DatePicker extends AppCompatActivity {
    String selectedDate;
    String workingDate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date_picker);

        Intent intent = getIntent();
        if(intent != null){
            workingDate = intent.getStringExtra("SelectedDate");

        }
        else {

        }

        CalendarView datePicker = this.findViewById(R.id.datePicker);
        Calendar cal = Calendar.getInstance();
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        selectedDate = formatter.format(cal.getTime());
        Log.d("TEST", "onCreate: " + selectedDate);


        datePicker.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                cal.set(year, month, dayOfMonth);
                selectedDate = formatter.format(cal.getTime());
                Log.d("TEST", "onCreate: " + selectedDate);
            }
        });

        Button setDateBtn = this.findViewById(R.id.btn_set_date);
        setDateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("TEST", "onClick: " + selectedDate);
                Intent BackToMain = new Intent(DatePicker.this, MainActivity.class);
                BackToMain.putExtra("SelectedDate", selectedDate);
                startActivity(BackToMain);
            }
        });


    }
}