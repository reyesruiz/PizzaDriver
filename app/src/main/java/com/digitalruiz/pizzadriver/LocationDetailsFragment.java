package com.digitalruiz.pizzadriver;

import android.database.Cursor;
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

public class LocationDetailsFragment extends Fragment {

    private final String TAG = "TEST";
    SQLiteDBHelper pizzaDriverDB;
    TextView AddressText;

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

        AddressText = view.findViewById(R.id.AddressTextView);

        Button backToListButton = view.findViewById(R.id.backButton);
        Button addNoteButton = view.findViewById(R.id.AddNoteButton);

        pizzaDriverDB = new SQLiteDBHelper(getContext());
        Cursor location_address_result = pizzaDriverDB.getLocationAddressDataByAddressId(AddressId);
        location_address_result.moveToFirst();

        String Address;
        Address = location_address_result.getString(location_address_result.getColumnIndex("Address"));

        AddressText.setText(Address);

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

    }
}