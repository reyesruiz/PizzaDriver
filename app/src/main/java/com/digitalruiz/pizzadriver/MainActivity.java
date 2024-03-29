package com.digitalruiz.pizzadriver;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.fragment.NavHostFragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

public class MainActivity extends AppCompatActivity implements Toolbar.OnMenuItemClickListener {

    public FloatingActionButton addOrder;
    String workingDate;
    Toolbar toolbar;
    SQLiteDBHelper pizzaDriverDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(0, 0);
        setContentView(R.layout.activity_main);
        pizzaDriverDB = new SQLiteDBHelper(getApplicationContext());
        toolbar = findViewById(R.id.toolbar);
        toolbar.inflateMenu(R.menu.top_app_bar);
        toolbar.setOnMenuItemClickListener(this);
        Log.d("TAG", "onCreate: " + savedInstanceState);
        long BusinessDayId = pizzaDriverDB.getActiveBusinessDay();
        if (BusinessDayId > 0) {
            workingDate = pizzaDriverDB.getBusinessDayById(BusinessDayId);
        } else {
            Date date = Calendar.getInstance().getTime();
            DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
            workingDate = formatter.format(date);
            Log.d("TAG", "onCreate: First " + workingDate);
            BusinessDayId = pizzaDriverDB.getBusinessDay(workingDate);
            if (BusinessDayId <= 0) {
                BusinessDayId = pizzaDriverDB.insertDate(workingDate);
                pizzaDriverDB.insertActiveBusinessDay(BusinessDayId);
                //Tracy
                pizzaDriverDB.insertRate(BusinessDayId, 1, "2.50");
                //Mountain House
                pizzaDriverDB.insertRate(BusinessDayId, 2, "3.50");
            }
        }

        addOrder = findViewById(R.id.add);
        Log.d("TEST", "onViewCreated: " + workingDate);
        toolbar.setSubtitle(workingDate);
        toolbar.setNavigationOnClickListener(v -> {
            Intent settings = new Intent(MainActivity.this, settings.class);
            MainActivity.this.startActivity(settings);
        });


        SQLiteDBHelper pizzaDriverDB;
        pizzaDriverDB = new SQLiteDBHelper(this);
        pizzaDriverDB.getWritableDatabase();

        Bundle bundleAddOrderNumber;
        bundleAddOrderNumber = new Bundle();

        addOrder.setOnClickListener(v -> {
            toolbar.setVisibility(View.GONE);
            String currentFragment = (String) Objects.requireNonNull(NavHostFragment.findNavController(Objects.requireNonNull(getSupportFragmentManager().getPrimaryNavigationFragment())).getCurrentDestination()).getLabel();
            Log.d("TAG", "onClick: " + currentFragment);
            assert currentFragment != null;
            if (currentFragment.equals("Order List Fragment")) {
                Log.d("TAG", "onClick: " + currentFragment);
                NavHostFragment.findNavController(getSupportFragmentManager().getPrimaryNavigationFragment())
                        .navigate(R.id.action_OrderListFragment_to_addOrderNumber, bundleAddOrderNumber);
            } else if (currentFragment.equals("Summary Fragment")) {
                Log.d("TAG", "onClick: " + currentFragment);
                NavHostFragment.findNavController(getSupportFragmentManager().getPrimaryNavigationFragment())
                        .navigate(R.id.action_SummaryFragment_to_addOrderNumber, bundleAddOrderNumber);
            } else {
                Log.d("TAG", "onClick: " + "Something Wrong");
            }
        });
    }

    @Override
    public boolean onMenuItemClick(MenuItem menuItem) {
        String currentFragment = (String) Objects.requireNonNull(NavHostFragment.findNavController(Objects.requireNonNull(getSupportFragmentManager().getPrimaryNavigationFragment())).getCurrentDestination()).getLabel();
        int id = menuItem.getItemId();
        if (id == R.id.settings) {
            assert currentFragment != null;
            if (currentFragment.equals("Order List Fragment")) {
                Log.d("TAG", "onClick: " + currentFragment);
                Log.d("TAG", "onMenuItemClick: " + workingDate);
                NavHostFragment.findNavController(getSupportFragmentManager().getPrimaryNavigationFragment())
                        .navigate(R.id.action_OrderListFragment_to_selectDateFragment);
            } else if (currentFragment.equals("Summary Fragment")) {
                Log.d("TAG", "onClick: " + currentFragment);
                NavHostFragment.findNavController(getSupportFragmentManager().getPrimaryNavigationFragment())
                        .navigate(R.id.action_SummaryFragment_to_selectDateFragment);
            } else {
                Log.d("TAG", "onClick: " + "Something Wrong");
            }
            return true;
        } else if (id == R.id.search_location) {
            Bundle bundleSearchLocation;
            bundleSearchLocation = new Bundle();
            bundleSearchLocation.putBoolean("SearchLocation", true);
            Intent find_place = new Intent(MainActivity.this, MainLocationsActivity.class);
            find_place.putExtras(bundleSearchLocation);
            MainActivity.this.startActivity(find_place);
            return true;
        }
        return false;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        toolbar.setVisibility(View.VISIBLE);
        Log.d("TAG", "BACK: " + "HERE");
    }


}
