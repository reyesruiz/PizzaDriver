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
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LocationAddNoteFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LocationAddNoteFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public LocationAddNoteFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LocationAddNoteFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static LocationAddNoteFragment newInstance(String param1, String param2) {
        LocationAddNoteFragment fragment = new LocationAddNoteFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_location_add_note, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle bundle = this.getArguments();
        Log.d("TAG", "Details: " + bundle);
        Log.d("TAG", "onViewCreated: " + "BLAA");
        int AddressId = bundle.getInt("ADDRESS_ID");
        int SubId = bundle.getInt("SUBDIVISION_ID");

        SQLiteDBHelper pizzaDriverDB;
        TextView AddressText;
        TextView AddressSubText;
        AddressText = view.findViewById(R.id.AddressTextViewAddNote);
        AddressSubText = view.findViewById(R.id.addressSubTextViewAddNote);

        EditText NoteText;
        NoteText = view.findViewById(R.id.locationNote);

        final ChipGroup tipNoteStatusChipGroup = view.findViewById(R.id.noteTipStatus);

        Button saveBtn = view.findViewById(R.id.saveNoteButton);

        pizzaDriverDB = new SQLiteDBHelper(getContext());
        Cursor location_address_result = pizzaDriverDB.getLocationAddressDataByAddressId(AddressId);
        location_address_result.moveToFirst();

        String Address;
        Address = location_address_result.getString(location_address_result.getColumnIndex("Address"));

        AddressText.setText(Address);

        String Subdivision;
        if (SubId > 0){
            Subdivision = pizzaDriverDB.getSubDivisionBySubId(SubId);
            AddressSubText.setText(Subdivision);
        }

        NoteText.requestFocus();

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Note;
                Log.v("Test", "Selected " + tipNoteStatusChipGroup.getCheckedChipId());
                int tipNoteTypeSelected = tipNoteStatusChipGroup.getCheckedChipId();
                if (tipNoteTypeSelected == -1){
                    Note = NoteText.getText().toString();
                }
                else {
                    Chip selected_chip = tipNoteStatusChipGroup.findViewById(tipNoteTypeSelected);
                    String selected_value = selected_chip.getText().toString();
                    Log.d("TAG", "onClick: " + selected_value);
                    Note = selected_value + ": " + NoteText.getText().toString();
                }
                String DateAdded;
                Date date = Calendar.getInstance().getTime();
                DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                DateAdded = formatter.format(date);

                long result = pizzaDriverDB.insertLocationNote(AddressId, SubId, Note, DateAdded);
                if (result > 0){
                    //Success
                    Log.d("TAG", "onClick: " + bundle);
                    NavHostFragment.findNavController(LocationAddNoteFragment.this).navigate(R.id.action_locationAddNoteFragment_to_LocationDetailFragment, bundle);
                }
                else {
                    //Something wrong
                }

            }
        });


    };
}