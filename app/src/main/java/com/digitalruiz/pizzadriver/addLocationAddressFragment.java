package com.digitalruiz.pizzadriver;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class addLocationAddressFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "placeId";
    private static final String ARG_PARAM2 = "placeName";
    private static final String ARG_PARAM3 = "placeAddress";
    private static final String ARG_PARAM4 = "placeAddressComponents";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private String mParam3;
    private String mParam4;

    public addLocationAddressFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
            mParam3 = getArguments().getString(ARG_PARAM3);
            mParam4 = getArguments().getString(ARG_PARAM4);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_location_address, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        String placeId = mParam1;
        String placeName = mParam2;
        String placeAddress = mParam3;
        String placeAddressComponents = mParam4;

        Button saveBtn = view.findViewById(R.id.saveLocationButton);
        Button cancelBtn = view.findViewById(R.id.cancelLocationSave);

        TextView AddressText = view.findViewById(R.id.AddressTextViewAdd);
        AddressText.setText(placeAddress);

        EditText subDivision = view.findViewById(R.id.editTextSubdivision);


        cancelBtn.setOnClickListener(v -> {
            //Just go back
            NavHostFragment.findNavController(addLocationAddressFragment.this).popBackStack();
        });

        saveBtn.setOnClickListener(v -> {
            SQLiteDBHelper pizzaDriverDB;
            pizzaDriverDB = new SQLiteDBHelper(getContext());
            pizzaDriverDB.getWritableDatabase();
            Log.d("TAG", "onClick: " + placeId);
            long insert_result_location_address = pizzaDriverDB.insertLocationAddress(placeId, placeName, placeAddress, placeAddressComponents);

            if (insert_result_location_address > 0) {
                int AddressId = (int) insert_result_location_address;
                int SubdivisionId = 0;
                if (subDivision.length() > 0) {
                    String subDivisionText = subDivision.getText().toString();
                    long insert_subdivision_result = pizzaDriverDB.insertSubDivisionAddress(AddressId, subDivisionText);
                    if (insert_subdivision_result > 0) {
                        SubdivisionId = (int) insert_subdivision_result;
                    } else {
                        //TODO Toast failed
                        Log.d("TAG", "Do something here");
                    }
                }
                Bundle bundle;
                bundle = new Bundle();
                bundle.putInt("ADDRESS_ID", AddressId);
                bundle.putInt("SUBDIVISION_ID", SubdivisionId);
                Log.d("TAG", "Bzz: " + bundle);
                NavHostFragment.findNavController(addLocationAddressFragment.this)
                        .navigate(R.id.action_addLocationAddressFragment_to_LocationDetailFragment, bundle);

            } else {
                //TODO check if exists in database otherwise show error.
                Log.d("TAG", "Do something here");
            }
        });


    }

}