package com.digitalruiz.pizzadriver;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SelectDateFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SelectDateFragment extends Fragment {

    String selectedDate;
    String workingDate;
    private Button mPickDateButton;
    SQLiteDBHelper pizzaDriverDB;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER


    // TODO: Rename and change types of parameters


    public SelectDateFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @return A new instance of fragment SelectDate.
     */
    // TODO: Rename and change types and number of parameters
    public static SelectDateFragment newInstance(String param1, String param2) {
        SelectDateFragment fragment = new SelectDateFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_select_date, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        pizzaDriverDB = new SQLiteDBHelper(getContext());
        long BusinessId = pizzaDriverDB.getActiveBusinessDay();
        workingDate = pizzaDriverDB.getBusinessDayById(BusinessId);

        // Create a calendar instance inside the system
        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        // Date Formatter to transform date from calendar instance to a simple date format
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
        // Date formatted (This will be today's date)
        workingDate = formatter.format(cal.getTime());
        Log.d("TEST", "onCreate: " + workingDate);
        // Will try to parse the working date passed from Main Activity, get time so we can get the result in milliseconds, then set our system calendar to it.
        try {
            cal.setTimeInMillis(formatter.parse(workingDate).getTime());
        } catch (ParseException e) {
            // This catch exception should never happen
            e.printStackTrace();
        }

        MaterialDatePicker.Builder materialDateBuilder = MaterialDatePicker.Builder.datePicker();
        materialDateBuilder.setTitleText("SELECT A DATE");
        materialDateBuilder.setSelection(cal.getTimeInMillis());
        final MaterialDatePicker materialDatePicker = materialDateBuilder.build();


        mPickDateButton = view.findViewById(R.id.pick_date_button);


        mPickDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                materialDatePicker.show(getParentFragmentManager(), "MATERIAL_DATE_PICKER");
            }
        });

        materialDatePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener<Long>() {
            @Override
            public void onPositiveButtonClick(Long selection) {
                // Setting the system calendar to the one selected from the picker.
                cal.setTimeInMillis(selection);
                cal.setTimeZone(TimeZone.getTimeZone("UTC"));
                // Converting that date that was set in the system calendar to the formatted version
                selectedDate = formatter.format(cal.getTimeInMillis());
                Log.d("TAG", "onPositiveButtonClick: " + selectedDate);
                Log.d("TEST", "onPositiveButtonClick: " + selection);
                long BusinessDayId = pizzaDriverDB.getBusinessDay(selectedDate);
                if (BusinessDayId > 0){
                    pizzaDriverDB.insertActiveBusinessDay(BusinessDayId);
                }
                else {
                    BusinessDayId = pizzaDriverDB.insertDate(selectedDate);
                    pizzaDriverDB.insertActiveBusinessDay(BusinessDayId);
                    //Tracy
                    pizzaDriverDB.insertRate(BusinessDayId, 1, "2.00");
                    //Mountain House
                    pizzaDriverDB.insertRate(BusinessDayId, 2, "3.00");
                }
                NavHostFragment.findNavController(SelectDateFragment.this)
                        .navigate(R.id.action_selectDateFragment_to_mainActivity);

            }
        });

    }
}