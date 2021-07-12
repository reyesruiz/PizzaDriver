package com.digitalruiz.pizzadriver;

import android.content.Intent;
import android.os.Bundle;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class MainActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(0,0);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(v -> {
            Intent settings = new Intent(MainActivity.this, settings.class);
            MainActivity.this.startActivity(settings);
        });



        SQLiteDBHelper pizzaDriverDB;
        pizzaDriverDB = new SQLiteDBHelper(this);

        FloatingActionButton addOrder = findViewById(R.id.add);

        addOrder.setOnClickListener(view -> {
            Intent addOrderIntent = new Intent(MainActivity.this, addOrderNumber.class);
            MainActivity.this.startActivity(addOrderIntent);
        });


    }

}
