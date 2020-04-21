package com.digitalruiz.pizzadriver;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

public class addOrderNumber extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_order_number);


        Intent intent = getIntent();
        final InputMethodManager imm = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
        final EditText orderNumberText = (EditText)this.findViewById(R.id.orderNumber);


        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);

        if (intent.hasExtra("orderNumber")) {
            final Integer orderNumber = intent.getExtras().getInt("orderNumber");
            orderNumberText.setText(orderNumber.toString());
        }
        orderNumberText.requestFocus();
        Boolean locationTracy = false;
        if (intent.hasExtra("locationTracy")){
            locationTracy = intent.getExtras().getBoolean("locationTracy");
        }
        Boolean locationMountainHouse = false;
        if (intent.hasExtra("locationMountainHouse")){
            locationMountainHouse =  intent.getExtras().getBoolean("locationMountainHouse");
        }


        final Boolean finalLocationTracy = locationTracy;
        final Boolean finalLocationMountainHouse = locationMountainHouse;
        orderNumberText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId == EditorInfo.IME_ACTION_DONE){
                    Integer orderNumber =  Integer.parseInt(orderNumberText.getText().toString());
                    Intent addOrderIntent = new Intent(addOrderNumber.this, AddOrder.class);
                    addOrderIntent.putExtra("orderNumber", orderNumber);
                    startActivity(addOrderIntent);
                }
                return false;
            }

        });






    }
}
