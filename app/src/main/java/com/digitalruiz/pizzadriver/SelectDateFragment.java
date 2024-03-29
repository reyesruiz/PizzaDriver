package com.digitalruiz.pizzadriver;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.google.android.material.datepicker.MaterialDatePicker;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.Objects;
import java.util.TimeZone;

/**
 * A simple {@link Fragment} subclass.
 * <p>
 * create an instance of this fragment.
 */
public class SelectDateFragment extends Fragment {

    String selectedDate;
    String workingDate;
    SQLiteDBHelper pizzaDriverDB;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER


    // TODO: Rename and change types of parameters


    public SelectDateFragment() {
        // Required empty public constructor
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
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
        // Date formatted (This will be today's date)
        workingDate = formatter.format(cal.getTime());
        Log.d("TEST", "onCreate: " + workingDate);
        // Will try to parse the working date passed from Main Activity, get time so we can get the result in milliseconds, then set our system calendar to it.
        try {
            cal.setTimeInMillis(Objects.requireNonNull(formatter.parse(workingDate)).getTime());
        } catch (ParseException e) {
            // This catch exception should never happen
            e.printStackTrace();
        }

        MaterialDatePicker.Builder<Long> materialDateBuilder = MaterialDatePicker.Builder.datePicker();
        materialDateBuilder.setTitleText("SELECT A DATE");
        materialDateBuilder.setSelection(cal.getTimeInMillis());
        final MaterialDatePicker<Long> materialDatePicker = materialDateBuilder.build();


        Button mPickDateButton = view.findViewById(R.id.pick_date_button);


        mPickDateButton.setOnClickListener(v -> materialDatePicker.show(getParentFragmentManager(), "MATERIAL_DATE_PICKER"));

        materialDatePicker.addOnPositiveButtonClickListener(selection -> {
            // Setting the system calendar to the one selected from the picker.
            cal.setTimeInMillis(selection);
            cal.setTimeZone(TimeZone.getTimeZone("UTC"));
            // Converting that date that was set in the system calendar to the formatted version
            selectedDate = formatter.format(cal.getTimeInMillis());
            Log.d("TAG", "onPositiveButtonClick: " + selectedDate);
            Log.d("TEST", "onPositiveButtonClick: " + selection);
            long BusinessDayId = pizzaDriverDB.getBusinessDay(selectedDate);
            if (BusinessDayId > 0) {
                pizzaDriverDB.insertActiveBusinessDay(BusinessDayId);
            } else {
                BusinessDayId = pizzaDriverDB.insertDate(selectedDate);
                pizzaDriverDB.insertActiveBusinessDay(BusinessDayId);
                //Tracy
                pizzaDriverDB.insertRate(BusinessDayId, 1, "2.50");
                //Mountain House
                pizzaDriverDB.insertRate(BusinessDayId, 2, "3.50");
            }
            NavHostFragment.findNavController(SelectDateFragment.this)
                    .navigate(R.id.action_selectDateFragment_to_mainActivity);

        });

    }
}