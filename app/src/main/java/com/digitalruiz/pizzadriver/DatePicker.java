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
import java.text.ParseException;
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

        // Create the calendar view
        CalendarView datePicker = this.findViewById(R.id.datePicker);
        // Create a calendar instance inside the system
        Calendar cal = Calendar.getInstance();
        // Date Formatter to transform date from calendar instance to a simple date format
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        // Date formatted (This will be today's date)
        selectedDate = formatter.format(cal.getTime());
        Log.d("TEST", "onCreate: " + selectedDate);
        // Will try to parse the working date passed from Main Activity, get time so we can get the result in milliseconds, then set our system calendar to it.
        try {
            cal.setTimeInMillis(formatter.parse(workingDate).getTime());
        } catch (ParseException e) {
            // This catch exception should never happen
            e.printStackTrace();
        }

        // Now we set the calendar view to the date passed.
        datePicker.setDate(cal.getTimeInMillis());

        // Now accessing the calendar view to see if user selected something else.
        datePicker.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                // Grabbing the selected date from calendar view, then updating the calendar from the system
                cal.set(year, month, dayOfMonth);
                // Converting that date that was set in the system calendar to the formatted version
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