package com.digitalruiz.pizzadriver;

import android.content.Intent;
import android.os.Bundle;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class OrderList extends AppCompatActivity {

    SQLiteDBHelper pizzaDriverDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_list);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        pizzaDriverDB = new SQLiteDBHelper(this);

        FloatingActionButton addOrder = findViewById(R.id.add);

        addOrder.setOnClickListener(view -> {
            Intent addOrderIntent = new Intent(OrderList.this, addOrderNumber.class);
            OrderList.this.startActivity(addOrderIntent);
        });


    }

}
