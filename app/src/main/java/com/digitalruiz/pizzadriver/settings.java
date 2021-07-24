package com.digitalruiz.pizzadriver;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

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
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Delete Data");
            builder.setMessage("Are you sure you want to delete all data?");
            builder.setCancelable(false);
            builder.setPositiveButton("Yes", (dialog, which) -> {
                pizzaDriverDB.deleteData();
                Toast.makeText(settings.this, "All data has been deleted", Toast.LENGTH_LONG).show();
                finish();
                Intent intent = new Intent(settings.this, MainActivity.class);
                startActivity(intent);
            });
            builder.setNegativeButton("No", (dialog, which) -> {
                dialog.cancel();
                Toast.makeText(settings.this, "Ok, not deleting", Toast.LENGTH_SHORT).show();
                finish();

            });
            AlertDialog alert = builder.create();
            alert.show();
            return false;
        });



    }


}