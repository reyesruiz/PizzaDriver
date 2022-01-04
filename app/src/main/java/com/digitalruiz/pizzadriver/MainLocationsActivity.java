package com.digitalruiz.pizzadriver;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.fragment.NavHostFragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.common.api.Status;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import android.view.MenuItem;


public class MainLocationsActivity extends AppCompatActivity implements Toolbar.OnMenuItemClickListener {
    String workingDate;
    private static int AUTOCOMPLETE_REQUEST_CODE = 1;
    private static String TAG = "TEST";
    String apiKey = BuildConfig.API_KEY;
    PlacesClient placesClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(0,0);

        setContentView(R.layout.activity_main_locations);

        Toolbar toolbar = findViewById(R.id.locations_toolbar);

        Log.d(TAG, "onCreate: " + apiKey);
        toolbar.setOnMenuItemClickListener(this);

        Places.initialize(getApplicationContext(), apiKey);
        placesClient = Places.createClient(this);

        Intent intent = getIntent();
        if(intent != null){
            workingDate = intent.getStringExtra("SelectedDate");
            if (workingDate == null){
                Date date = Calendar.getInstance().getTime();
                DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                workingDate = formatter.format(date);
            }
        }
        else {
            Date date = Calendar.getInstance().getTime();
            DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            workingDate = formatter.format(date);
        }
        Log.d("TEST", "onViewCreated: " + workingDate);
        toolbar.setSubtitle(workingDate);
        toolbar.setNavigationOnClickListener(v -> {
            Intent settings = new Intent(MainLocationsActivity.this, settings.class);
            MainLocationsActivity.this.startActivity(settings);
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == AUTOCOMPLETE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Place place = Autocomplete.getPlaceFromIntent(data);
                Log.i(TAG, "Place: " + place.getName() + ", " + place.getId());
                Log.d(TAG, "Place: " + place);

                SQLiteDBHelper pizzaDriverDB;
                pizzaDriverDB = new SQLiteDBHelper(this);
                pizzaDriverDB.getWritableDatabase();

                long insert_result_location_address = pizzaDriverDB.insertLocationAddress(place.getId(), place.getName(), place.getAddress(), place.getAddressComponents().toString());
                Log.d(TAG, "onActivityResult: " + insert_result_location_address);
                if (insert_result_location_address > 0){

                    int AddressId;
                    AddressId = ((int) insert_result_location_address);

                    Bundle bundle;
                    bundle = new Bundle();
                    bundle.putInt("ADDRESS_ID", AddressId);
                    Log.d(TAG, "Bzz: " + bundle);

                    //NavHostFragment.findNavController(getSupportFragmentManager().getPrimaryNavigationFragment())
                  //          .navigate(R.id.action_LocationListFragment_to_DetailsFragment, bundle);

                }
                else {
                    //TODO check if exists in database otherwise show error.
                }


            } else if (resultCode == AutocompleteActivity.RESULT_ERROR) {
                // TODO: Handle the error.
                Status status = Autocomplete.getStatusFromIntent(data);
                Log.i(TAG, status.getStatusMessage());
            } else if (resultCode == RESULT_CANCELED) {
                // The user canceled the operation.
            }
            return;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public boolean onPrepareOptionsMenu(final Menu menu)
    {
        menu.clear();
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.top_app_bar_locations, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onMenuItemClick(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.add_location:
                if (!Places.isInitialized()) {
                    Places.initialize(getApplicationContext(), apiKey);
                }

                // Set the fields to specify which types of place data to return.
                List<Place.Field> fields = Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.ADDRESS, Place.Field.ADDRESS_COMPONENTS);

                // Start the autocomplete intent.
                Intent auto_complete_intent = new Autocomplete.IntentBuilder(
                        AutocompleteActivityMode.FULLSCREEN, fields)
                        .build(this);

                startActivityForResult(auto_complete_intent, AUTOCOMPLETE_REQUEST_CODE);
                return true;
        }
        return false;
    }
}