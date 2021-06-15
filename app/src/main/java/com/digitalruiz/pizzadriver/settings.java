package com.digitalruiz.pizzadriver;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;



public class settings extends AppCompatActivity {
    SQLiteDBHelper pizzaDriverDB;
    Button clear_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        pizzaDriverDB = new SQLiteDBHelper(this);

        clear_button = findViewById(R.id.clear_data_btn);
        clear_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pizzaDriverDB.deleteData();
            }
        });
    }



}