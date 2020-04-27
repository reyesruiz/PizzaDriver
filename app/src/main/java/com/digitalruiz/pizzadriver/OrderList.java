package com.digitalruiz.pizzadriver;

import android.content.Intent;
import android.os.Bundle;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.View;

public class OrderList extends AppCompatActivity {

    SQLiteDBHelper pizzaDriverDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_list);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        pizzaDriverDB = new SQLiteDBHelper(this);

        FloatingActionButton addOrder = (FloatingActionButton)findViewById(R.id.add);
        FloatingActionButton startStop = (FloatingActionButton)findViewById(R.id.startStopShift);

        addOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                Intent addOrderIntent = new Intent(OrderList.this, addOrderNumber.class);
                OrderList.this.startActivity(addOrderIntent);
            }
        });

        startStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

}
