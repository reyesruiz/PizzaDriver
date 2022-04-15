package com.digitalruiz.pizzadriver;

import android.database.Cursor;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.google.android.material.chip.Chip;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;


public class LocationListFragment extends Fragment {

    SQLiteDBHelper pizzaDriverDB;
    String workingDate;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_location_list, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        pizzaDriverDB = new SQLiteDBHelper(getContext());
        long BusinessDayId = pizzaDriverDB.getActiveBusinessDay();
        if (BusinessDayId > 0) {
            workingDate = pizzaDriverDB.getBusinessDayById(BusinessDayId);
        } else {
            Date date = Calendar.getInstance().getTime();
            DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
            workingDate = formatter.format(date);
            BusinessDayId = pizzaDriverDB.getBusinessDay(workingDate);
            if (BusinessDayId <= 0) {
                BusinessDayId = pizzaDriverDB.insertDate(workingDate);
                pizzaDriverDB.insertActiveBusinessDay(BusinessDayId);
            }
        }

        ArrayList<Integer> all_location_address_ids;
        all_location_address_ids = pizzaDriverDB.getAllLocationAddressIds();

        TableLayout WrapperTable = view.findViewById(R.id.wrapperTableLayoutLocations);

        TableRow HeadLine = new TableRow(getContext());
        HeadLine.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.MATCH_PARENT));
        HeadLine.setBackgroundColor(ResourcesCompat.getColor(getResources(), R.color.mm_pine_green_shade_1, requireContext().getTheme()));
        HeadLine.setPadding(10, 10, 0, 10);
        HeadLine.setDividerPadding(10);

        TextView AddressNameStatic = new TextView(getContext());
        AddressNameStatic.setText(getString(R.string.address_name));
        AddressNameStatic.setTypeface(AddressNameStatic.getTypeface(), Typeface.BOLD);
        AddressNameStatic.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
        AddressNameStatic.setTextColor(ResourcesCompat.getColor(getResources(), R.color.white_50, requireContext().getTheme()));

        HeadLine.addView(AddressNameStatic);

        WrapperTable.addView(HeadLine);

        int counter = 0;
        for (final Integer AddressId : all_location_address_ids) {
            counter = counter + 1;
            Cursor location_address_result = pizzaDriverDB.getLocationAddressDataByAddressId(AddressId);
            location_address_result.moveToFirst();
            String AddressName = location_address_result.getString(location_address_result.getColumnIndex("AddressName"));

            TableRow Row = new TableRow(getContext());
            Row.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
            Row.setPadding(0, 0, 0, 0);
            if (counter % 2 == 0) {
                Row.setBackgroundColor(ResourcesCompat.getColor(getResources(), R.color.mm_pine_green_shade_2, requireContext().getTheme()));
            } else {
                Row.setBackgroundColor(ResourcesCompat.getColor(getResources(), R.color.mm_wild_yellow_shade_2, requireContext().getTheme()));

            }

            Chip addressNameChip = new Chip(requireContext());
            addressNameChip.setEnsureMinTouchTargetSize(false);
            addressNameChip.setText(AddressName);
            addressNameChip.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
            Bundle bundle;
            bundle = new Bundle();
            bundle.putInt("ADDRESS_ID", AddressId);

            addressNameChip.setOnClickListener(v -> NavHostFragment.findNavController(LocationListFragment.this)
                    .navigate(R.id.action_LocationListFragment_to_DetailsFragment, bundle));

            addressNameChip.setOnLongClickListener(v -> {
                showPopup(v, AddressId);
                return true;
            });


            Row.addView(addressNameChip);

            WrapperTable.addView(Row);
        }

        view.findViewById(R.id.buttonBackToOrdersList).setOnClickListener(v -> NavHostFragment.findNavController(LocationListFragment.this)
                .navigate(R.id.action_LocationListFragment_to_mainActivity));


    }

    private void showPopup(View view, int AddressId) {
        PopupMenu popup = new PopupMenu(getContext(), view);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.location_options, popup.getMenu());
        popup.show();

        MenuItem delete = popup.getMenu().findItem(R.id.location_delete);
        delete.setOnMenuItemClickListener(v -> {
            //TODO
            Log.d("TEST", "showPopup: deleted");

            boolean deleted = pizzaDriverDB.deleteLocationId(AddressId);
            if (deleted) {
                Toast deletedToast = Toast.makeText(view.getContext(), "Deleted Location ID " + AddressId, Toast.LENGTH_SHORT);
                deletedToast.show();
            } else {
                Toast deletedToast = Toast.makeText(view.getContext(), "Unable to delete Location ID " + AddressId + " , something wrong", Toast.LENGTH_LONG);
                deletedToast.show();
            }

            requireActivity().finish();
            startActivity(requireActivity().getIntent());
            return deleted;
        });
    }
}