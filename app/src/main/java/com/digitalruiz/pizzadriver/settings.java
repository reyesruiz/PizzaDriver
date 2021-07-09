package com.digitalruiz.pizzadriver;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;


public class settings extends AppCompatActivity {
    SQLiteDBHelper pizzaDriverDB;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        pizzaDriverDB = new SQLiteDBHelper(this);

        NavigationView nav_draw_settings = findViewById(R.id.settings_navigationView);
        MenuItem clear_data = nav_draw_settings.getMenu().findItem(R.id.clear_data_menu).setVisible(true);

        clear_data.setOnMenuItemClickListener(item -> {
            pizzaDriverDB.deleteData();
            return false;
        });




        //clear_button = findViewById(R.id.clear_data_menu);
        //clear_data.setOnClickListener(new View.OnClickListener() {
        //    @Override
        //    public void onClick(View v) {
        //        pizzaDriverDB.deleteData();
        //    }
        //});
    }



}