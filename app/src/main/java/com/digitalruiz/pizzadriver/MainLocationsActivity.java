package com.digitalruiz.pizzadriver;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.fragment.NavHostFragment;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.model.RectangularBounds;
import com.google.android.libraries.places.api.model.TypeFilter;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;


public class MainLocationsActivity extends AppCompatActivity implements Toolbar.OnMenuItemClickListener {
    private static final String TAG = "TEST";
    String workingDate;
    final String apiKey = BuildConfig.API_KEY;
    PlacesClient placesClient;
    SQLiteDBHelper pizzaDriverDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(0, 0);

        setContentView(R.layout.activity_main_locations);

        Toolbar toolbar = findViewById(R.id.locations_toolbar);

        Log.d(TAG, "onCreate: " + apiKey);
        toolbar.setOnMenuItemClickListener(this);

        Places.initialize(getApplicationContext(), apiKey);
        placesClient = Places.createClient(this);
        pizzaDriverDB = new SQLiteDBHelper(getApplicationContext());
        long BusinessDayId = pizzaDriverDB.getActiveBusinessDay();
        if (BusinessDayId > 0) {
            workingDate = pizzaDriverDB.getBusinessDayById(BusinessDayId);
        } else {
            Log.d("TAG", "onViewCreated: Something went wrong, code should not reach here");
        }

        Log.d("TEST", "onViewCreated: " + workingDate);
        toolbar.setSubtitle(workingDate);
        toolbar.setNavigationOnClickListener(v -> {
            Intent settings = new Intent(MainLocationsActivity.this, settings.class);
            MainLocationsActivity.this.startActivity(settings);
        });

        boolean search_location = Objects.requireNonNull(getIntent().getExtras()).getBoolean("SearchLocation");
        if (search_location){
            search_location();
        }
    }

    final ActivityResultLauncher<Intent> ActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    // There are no request codes
                    Intent data = result.getData();
                    assert data != null;
                    Place place = Autocomplete.getPlaceFromIntent(data);
                    Log.i(TAG, "Place: " + place.getName() + ", " + place.getId());
                    Log.d(TAG, "Place: " + place);

                    SQLiteDBHelper pizzaDriverDB;
                    pizzaDriverDB = new SQLiteDBHelper(getApplicationContext());
                    pizzaDriverDB.getWritableDatabase();


                    int AddressId = pizzaDriverDB.getAddressIdByLocationId(place.getId());
                    Bundle bundle;
                    bundle = new Bundle();
                    if (AddressId > 0) {
                        Toast foundInDB = Toast.makeText(getApplicationContext(), "Found Address in DB", Toast.LENGTH_SHORT);
                        foundInDB.show();
                        bundle.putInt("ADDRESS_ID", AddressId);
                        Log.d(TAG, "Bzz: " + bundle);
                        NavHostFragment.findNavController(Objects.requireNonNull(getSupportFragmentManager().getPrimaryNavigationFragment()))
                                .navigate(R.id.action_LocationListFragment_to_DetailsFragment, bundle);
                    } else {
                        bundle.putString("placeId", place.getId());
                        bundle.putString("placeName", place.getName());
                        bundle.putString("placeAddress", place.getAddress());
                        bundle.putString("placeAddressComponents", Objects.requireNonNull(place.getAddressComponents()).toString());
                        Toast notFoundInDB = Toast.makeText(getApplicationContext(), "Not Found Address in DB", Toast.LENGTH_SHORT);
                        notFoundInDB.show();
                        NavHostFragment.findNavController(Objects.requireNonNull(getSupportFragmentManager().getPrimaryNavigationFragment())).navigate(R.id.action_LocationListFragment_to_addLocationAddressFragment, bundle);
                    }

                } else if (result.getResultCode() == AutocompleteActivity.RESULT_ERROR) {
                    // TODO: Handle the error.
                    Intent data = result.getData();
                    assert data != null;
                    Status status = Autocomplete.getStatusFromIntent(data);
                    assert status.getStatusMessage() != null;
                    Log.i(TAG, status.getStatusMessage());
                } else if (result.getResultCode() == RESULT_CANCELED) {
                    Log.i(TAG, "User canceled the request");
                }
            });


    @Override
    public boolean onPrepareOptionsMenu(final Menu menu) {
        menu.clear();
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.top_app_bar_locations, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onMenuItemClick(MenuItem menuItem) {
        String currentFragment = (String) Objects.requireNonNull(NavHostFragment.findNavController(Objects.requireNonNull(getSupportFragmentManager().getPrimaryNavigationFragment())).getCurrentDestination()).getLabel();
        int id = menuItem.getItemId();
        if (id == R.id.add_location) {
            search_location();
            return true;
        } else if (id == R.id.show_map) {
            assert currentFragment != null;
            if (currentFragment.equals("Location List Fragment")) {
                Log.d("TAG", "onClick: " + currentFragment);
                NavHostFragment.findNavController(getSupportFragmentManager().getPrimaryNavigationFragment())
                        .navigate(R.id.action_LocationListFragment_to_locationsMapFragment);
            } else {
                Log.d("TAG", "onClick: " + "Something Wrong");
            }
        }
        return false;
    }

    private void search_location() {
        //Tracy and Mountain house
        RectangularBounds bounds = RectangularBounds.newInstance(
                new LatLng(37.638889, -121.619722),
                new LatLng(37.858754, -121.286388));
        // Set the fields to specify which types of place data to return.
        List<Place.Field> fields = Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.ADDRESS, Place.Field.ADDRESS_COMPONENTS);

        // Start the autocomplete intent.
        Intent auto_complete_intent = new Autocomplete.IntentBuilder(
                AutocompleteActivityMode.FULLSCREEN, fields).setLocationBias(bounds).setTypeFilter(TypeFilter.ADDRESS)
                .build(this);

        //TODO work on on this deprecated call
        ActivityResultLauncher.launch(auto_complete_intent);
    }
}