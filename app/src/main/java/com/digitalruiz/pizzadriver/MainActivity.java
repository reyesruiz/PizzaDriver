package com.digitalruiz.pizzadriver;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.fragment.NavHostFragment;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity implements Toolbar.OnMenuItemClickListener {

    String workingDate;
    public FloatingActionButton addOrder;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(0,0);
        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.toolbar);
        toolbar.inflateMenu(R.menu.top_app_bar);
        toolbar.setOnMenuItemClickListener(this);
        Log.d("TAG", "onCreate: " + savedInstanceState);
        Bundle b = getIntent().getExtras();

        if (b != null){
            workingDate = b.getString("SelectedDate");
        }
        else {
            Date date = Calendar.getInstance().getTime();
            DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            workingDate = formatter.format(date);
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
        bundleAddOrderNumber.putString("SelectedDate", workingDate);

        addOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toolbar.setVisibility(View.GONE);
                String currentFragment = (String) NavHostFragment.findNavController(getSupportFragmentManager().getPrimaryNavigationFragment()).getCurrentDestination().getLabel();
                Log.d("TAG", "onClick: " + currentFragment);
                if (currentFragment.equals("Order List Fragment")) {
                    Log.d("TAG", "onClick: " + currentFragment);
                    NavHostFragment.findNavController(getSupportFragmentManager().getPrimaryNavigationFragment())
                          .navigate(R.id.action_OrderListFragment_to_addOrderNumber, bundleAddOrderNumber);
                }
                else if (currentFragment.equals("Summary Fragment")){
                    Log.d("TAG", "onClick: " + currentFragment);
                    NavHostFragment.findNavController(getSupportFragmentManager().getPrimaryNavigationFragment())
                            .navigate(R.id.action_SummaryFragment_to_addOrderNumber, bundleAddOrderNumber);
                }
                else {
                    Log.d("TAG", "onClick: " + "Something Wrong");
                }
            }
        });
    }

    @Override
    public boolean onMenuItemClick(MenuItem menuItem) {
        String currentFragment = (String) NavHostFragment.findNavController(getSupportFragmentManager().getPrimaryNavigationFragment()).getCurrentDestination().getLabel();
        switch (menuItem.getItemId()) {
            case R.id.settings:
                Bundle bundle = new Bundle();
                bundle.putString("SelectedDate",workingDate);
                if (currentFragment.equals("Order List Fragment")) {
                    Log.d("TAG", "onClick: " + currentFragment);
                    Log.d("TAG", "onMenuItemClick: " + workingDate);
                    NavHostFragment.findNavController(getSupportFragmentManager().getPrimaryNavigationFragment())
                            .navigate(R.id.action_OrderListFragment_to_selectDateFragment, bundle);
                }
                else if (currentFragment.equals("Summary Fragment")){
                    Log.d("TAG", "onClick: " + currentFragment);
                    NavHostFragment.findNavController(getSupportFragmentManager().getPrimaryNavigationFragment())
                            .navigate(R.id.action_SummaryFragment_to_selectDateFragment, bundle);
                }
                else {
                    Log.d("TAG", "onClick: " + "Something Wrong");
                }
                return true;
            case R.id.add_location:
                Intent find_place = new Intent(MainActivity.this, MainLocationsActivity.class);
                find_place.putExtra("SelectedDate", workingDate);
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
