package com.digitalruiz.pizzadriver;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.TextView;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;
import kotlin.Pair;


public class DatePicker extends AppCompatActivity {
    String selectedDate;
    String workingDate;
    private Button mPickDateButton;
    private TextView mShowSelectedDateText;

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

        // Create a calendar instance inside the system
        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        // Date Formatter to transform date from calendar instance to a simple date format
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
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

        MaterialDatePicker.Builder materialDateBuilder = MaterialDatePicker.Builder.dateRangePicker();
        materialDateBuilder.setTitleText("SELECT A DATE");
        materialDateBuilder.setSelection(cal.getTimeInMillis());
        final MaterialDatePicker materialDatePicker = materialDateBuilder.build();


        mPickDateButton = findViewById(R.id.pick_date_button);
        mShowSelectedDateText = findViewById(R.id.show_selected_date);

        mPickDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                materialDatePicker.show(getSupportFragmentManager(), "MATERIAL_DATE_PICKER");
            }
        });

        materialDatePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener<Long>() {
            @Override
            public void onPositiveButtonClick(Long selection) {
                mShowSelectedDateText.setText("Current Selected Date is : " + materialDatePicker.getHeaderText());
                // Setting the system calendar to the one selected from the picker.
                cal.setTimeInMillis(selection);
                cal.setTimeZone(TimeZone.getTimeZone("UTC"));
                // Converting that date that was set in the system calendar to the formatted version
                selectedDate = formatter.format(cal.getTimeInMillis());
                Log.d("TEST", "onPositiveButtonClick: " + selection);
                Intent BackToMain = new Intent(DatePicker.this, MainActivity.class);
                BackToMain.putExtra("SelectedDate", selectedDate);
                startActivity(BackToMain);
            }
        });


    }
}