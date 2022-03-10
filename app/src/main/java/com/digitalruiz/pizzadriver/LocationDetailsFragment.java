package com.digitalruiz.pizzadriver;

import android.database.Cursor;
import android.database.DataSetObserver;
import android.graphics.Typeface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

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
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TableLayout;
import android.widget.TableRow;
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

        TableLayout WrapperTable = view.findViewById(R.id.wrapperTableNotes);
        TableRow HeadLine = new TableRow(getContext());
        HeadLine.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.MATCH_PARENT));
        HeadLine.setBackgroundColor(ResourcesCompat.getColor(getResources(), R.color.mm_pine_green_shade_1, requireContext().getTheme()));
        HeadLine.setPadding(10, 10, 0, 10);
        HeadLine.setDividerPadding(10);

        TextView DateAddedStatic = new TextView(getContext());
        DateAddedStatic.setText("DateAdded");
        DateAddedStatic.setTypeface(DateAddedStatic.getTypeface(), Typeface.BOLD);
        DateAddedStatic.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
        DateAddedStatic.setTextColor(ResourcesCompat.getColor(getResources(), R.color.white_50, requireContext().getTheme()));
        HeadLine.addView(DateAddedStatic);

        TextView NoteStatic = new TextView(getContext());
        NoteStatic.setText("Note");
        NoteStatic.setTypeface(NoteStatic.getTypeface(), Typeface.BOLD);
        NoteStatic.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
        NoteStatic.setTextColor(ResourcesCompat.getColor(getResources(), R.color.white_50, requireContext().getTheme()));
        HeadLine.addView(NoteStatic);

        WrapperTable.addView(HeadLine);
        ArrayList<Integer> NoteIds = new ArrayList<>();
        NoteIds = pizzaDriverDB.getNoteIds(AddressId, SubdivisionId);

        int counter = 0;
        for (final int noteId: NoteIds){
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
            if (counter % 2 == 0){
                Row.setBackgroundColor(ResourcesCompat.getColor(getResources(), R.color.mm_pine_green_shade_2, getContext().getTheme()));
            }
            else {
                Row.setBackgroundColor(ResourcesCompat.getColor(getResources(), R.color.mm_wild_yellow_shade_2, getContext().getTheme()));

            }
            Row.addView(DateAddedText);
            Row.addView(NoteText);

            Row.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    showPopup(view, noteId);
                    return false;
                }
            });
            WrapperTable.addView(Row);
        }

        ArrayList<Integer> SubDivisionsIds = new ArrayList<>();
        ArrayList<String> SubDivisions = new ArrayList<>();
        SubDivisionsIds = pizzaDriverDB.getSubDivisionsByAddressID(AddressId);


        if (SubDivisionsIds.size() > 0){
            SubDivisions.add("");
            Iterator iter = SubDivisionsIds.iterator();
            while (iter.hasNext()) {
                String subDiv;
                subDiv = pizzaDriverDB.getSubDivisionBySubId(Integer.parseInt(iter.next().toString()));
                SubDivisions.add(subDiv);
            }
            ArrayAdapter<String> adapterList = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, SubDivisions);
            adapterList.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            int i;
            SubDivisionSpinner.setAdapter(adapterList);
            if (SubdivisionId > 0){
                String SubNum = pizzaDriverDB.getSubDivisionBySubId(SubdivisionId);
                for(i=0; i < adapterList.getCount(); i++) {
                    if(SubNum.trim().equals(adapterList.getItem(i))){
                        SubDivisionSpinner.setSelection(i);
                        break;
                    }
                }
            }
            ArrayList<Integer> finalSubDivisionsIds = SubDivisionsIds;
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
                    if (selected.equals(SubNum)) {
                        //nothing
                    } else {
                        if (selected.equals("")) {
                            bundle.putInt("SUBDIVISION_ID", 0);
                            NavHostFragment.findNavController(LocationDetailsFragment.this).navigate(R.id.action_LocationDetailFragment_self, bundle);
                        } else {
                            Iterator iter2 = finalSubDivisionsIds.iterator();
                            while (iter2.hasNext()) {
                                String subDiv;
                                int subId;
                                subId = Integer.parseInt(iter2.next().toString());
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
            }
            else {
                Toast deletedToast = Toast.makeText(view.getContext(), "Unable to delete Note, something wrong", Toast.LENGTH_LONG);
                deletedToast.show();
            }
            //TODO TO self fragment
            getActivity().finish();
            startActivity(getActivity().getIntent());
            return deleted;
        });
    }

}