package com.digitalruiz.pizzadriver;

import android.database.Cursor;
import android.database.DataSetObserver;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Iterator;

public class LocationDetailsFragment extends Fragment {

    private final String TAG = "TEST";
    SQLiteDBHelper pizzaDriverDB;
    TextView AddressText;
    Spinner SubDivisionSpinner;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_location_details, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle bundle = this.getArguments();
        Log.d(TAG, "Details: " + bundle);
        Log.d(TAG, "onViewCreated: " + "BLAA");
        int AddressId = bundle.getInt("ADDRESS_ID");
        int SubdivisionId = bundle.getInt("SUBDIVISION_ID");

        AddressText = view.findViewById(R.id.AddressTextView);
        SubDivisionSpinner = view.findViewById(R.id.spinnerSubdivion);

        Button backToListButton = view.findViewById(R.id.backButton);
        Button addNoteButton = view.findViewById(R.id.AddNoteButton);
        Button buttonAddSub = view.findViewById(R.id.buttonAddSub);

        pizzaDriverDB = new SQLiteDBHelper(getContext());
        Cursor location_address_result = pizzaDriverDB.getLocationAddressDataByAddressId(AddressId);
        location_address_result.moveToFirst();

        String Address;
        Address = location_address_result.getString(location_address_result.getColumnIndex("Address"));

        AddressText.setText(Address);

        ArrayList<String> SubDivisions = new ArrayList<>();
        SubDivisions = pizzaDriverDB.getSubDivisionsByAddressID(AddressId);

        if (SubDivisions.size() > 0){
            ArrayAdapter<String> adapterList = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, SubDivisions);
            adapterList.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            int i;
            SubDivisionSpinner.setAdapter(adapterList);
            if (SubdivisionId > 0){
                String SubNum = pizzaDriverDB.getSubDivisionBySubId(SubdivisionId);
                for(i=0; i < adapterList.getCount(); i++) {
                    if(SubNum.trim().equals(adapterList.getItem(i).toString())){
                        SubDivisionSpinner.setSelection(i);
                        break;
                    }
                }
            }
        }


        backToListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavHostFragment.findNavController(LocationDetailsFragment.this).navigate(R.id.action_DetailsFragment_to_LocationListFragment);
            }
        });

        addNoteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavHostFragment.findNavController(LocationDetailsFragment.this).navigate(R.id.action_LocationDetailFragment_to_locationAddNoteFragment, bundle);
            }
        });

        buttonAddSub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String[] m_Text = {""};
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("New apt, space, unit, etc number");
                final EditText input = new EditText(getContext());
                input.setInputType(InputType.TYPE_CLASS_TEXT);
                builder.setView(input);
                builder.setPositiveButton("OK", (dialog, which) -> {
                    m_Text[0] = input.getText().toString();
                    String NewSub = m_Text[0];
                    long result = pizzaDriverDB.insertSubDivisionAddress(AddressId, NewSub);
                    if (result > 0){
                        Toast updateToast = Toast.makeText(getContext(), "Inserted apt,space,unit number " + NewSub + " to database", Toast.LENGTH_SHORT);
                        updateToast.show();
                        int sub = (int) result;
                        bundle.putInt("SUBDIVISION_ID", sub);
                        NavHostFragment.findNavController(LocationDetailsFragment.this).navigate(R.id.action_LocationDetailFragment_self, bundle);
                    }
                    else {
                        Toast updateToast = Toast.makeText(getContext(), "Unable to insert apt,space,unit number " + NewSub + " to database", Toast.LENGTH_SHORT);
                        updateToast.show();
                    }

                });
                builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());

                builder.show();
            }
        });

    }

    public static void selectSpinnerItemByValue(Spinner spnr, String value) {
        SimpleCursorAdapter adapter = (SimpleCursorAdapter) spnr.getAdapter();
        for (int position = 0; position < adapter.getCount(); position++) {
            if(adapter.getItem(position).toString() == value) {
                spnr.setSelection(position);
                return;
            }
        }
    }
}