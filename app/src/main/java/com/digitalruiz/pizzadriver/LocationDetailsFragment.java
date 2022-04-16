package com.digitalruiz.pizzadriver;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.google.android.gms.common.api.ApiException;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.FetchPlaceRequest;
import com.google.android.libraries.places.api.net.PlacesClient;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LocationDetailsFragment extends Fragment {

    SQLiteDBHelper pizzaDriverDB;
    TextView AddressText;
    Spinner SubDivisionSpinner;
    PlacesClient placesClient;
    final String apiKey = BuildConfig.API_KEY;

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
        String TAG = "TEST";
        Log.d(TAG, "Details: " + bundle);
        assert bundle != null;
        int AddressId = bundle.getInt("ADDRESS_ID");
        int SubdivisionId = bundle.getInt("SUBDIVISION_ID");

        AddressText = view.findViewById(R.id.AddressTextView);
        SubDivisionSpinner = view.findViewById(R.id.spinnerSubDivision);

        Button backToListButton = view.findViewById(R.id.backButton);
        Button addNoteButton = view.findViewById(R.id.AddNoteButton);
        Button buttonAddSub = view.findViewById(R.id.buttonAddSub);

        pizzaDriverDB = new SQLiteDBHelper(getContext());
        Cursor location_address_result = pizzaDriverDB.getLocationAddressDataByAddressId(AddressId);
        location_address_result.moveToFirst();

        String Address;
        Address = location_address_result.getString(location_address_result.getColumnIndex("Address"));

        AddressText.setText(Address);

        String placeId = pizzaDriverDB.getPlaceIdByAddressId(AddressId);

        Places.initialize(getActivity().getApplicationContext(), apiKey);
        placesClient = Places.createClient(getContext());
        final List<Place.Field> placeFields = Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG);

        final FetchPlaceRequest request = FetchPlaceRequest.newInstance(placeId, placeFields);

        AddressText.setOnClickListener(view12 -> {
            placesClient.fetchPlace(request).addOnSuccessListener((response) -> {
                Place place = response.getPlace();
                Log.i(TAG, "Place found: " + place.getName());
                Log.i(TAG, "Place found: " + place.getLatLng());
                String uriString = "geo:0,0?q=" + Uri.encode(place.getLatLng().latitude + "," + place.getLatLng().longitude + "(" + place.getName() + ")");
                Uri gmmIntentUri = Uri.parse(uriString);
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                if (mapIntent.resolveActivity(getActivity().getPackageManager()) != null) {
                    startActivity(mapIntent);
                }
                else{
                    Log.d(TAG, "No google map app found");
                    startActivity(mapIntent);
                }

            }).addOnFailureListener((exception) -> {
                if (exception instanceof ApiException) {
                    final ApiException apiException = (ApiException) exception;
                    Log.e(TAG, "Place not found: " + exception.getMessage());
                    final int statusCode = apiException.getStatusCode();
                    // TODO: Handle error with given status code.
                }
            });
            String placeIdParameter = "place_id:" + placeId;

        });

        TableLayout WrapperTable = view.findViewById(R.id.wrapperTableNotes);
        TableRow HeadLine = new TableRow(getContext());
        HeadLine.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.MATCH_PARENT));
        HeadLine.setBackgroundColor(ResourcesCompat.getColor(getResources(), R.color.mm_pine_green_shade_1, requireContext().getTheme()));
        HeadLine.setPadding(10, 10, 0, 10);
        HeadLine.setDividerPadding(10);

        TextView DateAddedStatic = new TextView(getContext());
        DateAddedStatic.setText(R.string.date_added);
        DateAddedStatic.setTypeface(DateAddedStatic.getTypeface(), Typeface.BOLD);
        DateAddedStatic.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
        DateAddedStatic.setTextColor(ResourcesCompat.getColor(getResources(), R.color.white_50, requireContext().getTheme()));
        HeadLine.addView(DateAddedStatic);

        TextView NoteStatic = new TextView(getContext());
        NoteStatic.setText(R.string.note);
        NoteStatic.setTypeface(NoteStatic.getTypeface(), Typeface.BOLD);
        NoteStatic.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
        NoteStatic.setTextColor(ResourcesCompat.getColor(getResources(), R.color.white_50, requireContext().getTheme()));
        HeadLine.addView(NoteStatic);

        WrapperTable.addView(HeadLine);
        ArrayList<Integer> NoteIds;
        NoteIds = pizzaDriverDB.getNoteIds(AddressId, SubdivisionId);

        int counter = 0;
        for (final int noteId : NoteIds) {
            counter = counter + 1;
            Cursor result = pizzaDriverDB.getNoteData(noteId);
            Log.d(TAG, "onViewCreatedCount: " + result.getCount());
            result.moveToFirst();
            String DateAdded = result.getString(result.getColumnIndex("DateAdded"));
            String Note = result.getString(result.getColumnIndex("Note"));
            TextView DateAddedText = new TextView(getContext());
            DateAddedText.setText(DateAdded);
            DateAddedText.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));

            TextView NoteText = new TextView(getContext());
            NoteText.setText(Note);
            NoteText.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));

            TableRow Row = new TableRow(getContext());
            Row.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
            Row.setPadding(0, 0, 0, 0);
            if (counter % 2 == 0) {
                Row.setBackgroundColor(ResourcesCompat.getColor(getResources(), R.color.mm_pine_green_shade_2, requireContext().getTheme()));
            } else {
                Row.setBackgroundColor(ResourcesCompat.getColor(getResources(), R.color.mm_wild_yellow_shade_2, requireContext().getTheme()));

            }
            Row.addView(DateAddedText);
            Row.addView(NoteText);

            Row.setOnLongClickListener(view1 -> {
                showPopup(view1, noteId);
                return false;
            });
            WrapperTable.addView(Row);
        }

        ArrayList<Integer> SubDivisionsIds;
        ArrayList<String> SubDivisions = new ArrayList<>();
        SubDivisionsIds = pizzaDriverDB.getSubDivisionsByAddressID(AddressId);


        if (SubDivisionsIds.size() > 0) {
            SubDivisions.add("");
            for (Integer subDivisionsId : SubDivisionsIds) {
                String subDiv;
                subDiv = pizzaDriverDB.getSubDivisionBySubId(Integer.parseInt(subDivisionsId.toString()));
                SubDivisions.add(subDiv);
            }
            ArrayAdapter<String> adapterList = new ArrayAdapter<>(requireActivity(), android.R.layout.simple_spinner_item, SubDivisions);
            adapterList.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            int i;
            SubDivisionSpinner.setAdapter(adapterList);
            if (SubdivisionId > 0) {
                String SubNum = pizzaDriverDB.getSubDivisionBySubId(SubdivisionId);
                for (i = 0; i < adapterList.getCount(); i++) {
                    if (SubNum.trim().equals(adapterList.getItem(i))) {
                        SubDivisionSpinner.setSelection(i);
                        break;
                    }
                }
            }
            SubDivisionSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    String selected = SubDivisionSpinner.getSelectedItem().toString();
                    if (selected.equals("")) {
                        bundle.putInt("SUBDIVISION_ID", 0);
                    }
                    String SubNum;
                    if (SubdivisionId > 0) {
                        SubNum = pizzaDriverDB.getSubDivisionBySubId(SubdivisionId);
                    } else {
                        SubNum = "";
                    }
                    if (!selected.equals(SubNum)) {
                        if (selected.equals("")) {
                            bundle.putInt("SUBDIVISION_ID", 0);
                            NavHostFragment.findNavController(LocationDetailsFragment.this).navigate(R.id.action_LocationDetailFragment_self, bundle);
                        } else {
                            for (Integer finalSubDivisionsId : SubDivisionsIds) {
                                String subDiv;
                                int subId;
                                subId = Integer.parseInt(finalSubDivisionsId.toString());
                                subDiv = pizzaDriverDB.getSubDivisionBySubId(subId);
                                if (subDiv.equals(selected)) {
                                    bundle.putInt("SUBDIVISION_ID", subId);
                                    NavHostFragment.findNavController(LocationDetailsFragment.this).navigate(R.id.action_LocationDetailFragment_self, bundle);
                                }
                            }
                        }
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });
        }


        backToListButton.setOnClickListener(v -> NavHostFragment.findNavController(LocationDetailsFragment.this).navigate(R.id.action_DetailsFragment_to_LocationListFragment));

        addNoteButton.setOnClickListener(v -> NavHostFragment.findNavController(LocationDetailsFragment.this).navigate(R.id.action_LocationDetailFragment_to_locationAddNoteFragment, bundle));

        buttonAddSub.setOnClickListener(v -> {
            final String[] m_Text = {""};
            AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
            builder.setTitle("New apt, space, unit, etc number");
            final EditText input = new EditText(getContext());
            input.setInputType(InputType.TYPE_CLASS_TEXT);
            builder.setView(input);
            builder.setPositiveButton("OK", (dialog, which) -> {
                m_Text[0] = input.getText().toString();
                String NewSub = m_Text[0];
                long result = pizzaDriverDB.insertSubDivisionAddress(AddressId, NewSub);
                if (result > 0) {
                    Toast updateToast = Toast.makeText(getContext(), "Inserted apt,space,unit number " + NewSub + " to database", Toast.LENGTH_SHORT);
                    updateToast.show();
                    int sub = (int) result;
                    bundle.putInt("SUBDIVISION_ID", sub);
                    NavHostFragment.findNavController(LocationDetailsFragment.this).navigate(R.id.action_LocationDetailFragment_self, bundle);
                } else {
                    Toast updateToast = Toast.makeText(getContext(), "Unable to insert apt,space,unit number " + NewSub + " to database", Toast.LENGTH_SHORT);
                    updateToast.show();
                }

            });
            builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());

            builder.show();
        });

    }

    private void showPopup(View view, int noteId) {
        PopupMenu popup = new PopupMenu(getContext(), view);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.note_options, popup.getMenu());
        popup.show();

        MenuItem delete = popup.getMenu().findItem(R.id.note_delete);
        delete.setOnMenuItemClickListener(v -> {
            //TODO
            Log.d("TEST", "showPopup: deleted");
            boolean deleted = pizzaDriverDB.deleteNote(noteId);
            if (deleted) {
                Toast deletedToast = Toast.makeText(view.getContext(), "Deleted Note", Toast.LENGTH_SHORT);
                deletedToast.show();
            } else {
                Toast deletedToast = Toast.makeText(view.getContext(), "Unable to delete Note, something wrong", Toast.LENGTH_LONG);
                deletedToast.show();
            }
            //TODO TO self fragment
            requireActivity().finish();
            startActivity(requireActivity().getIntent());
            return deleted;
        });
    }

}