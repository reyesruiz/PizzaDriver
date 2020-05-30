package com.digitalruiz.pizzadriver;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Objects;

public class addOrderNumber extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_order_number);


        Intent intent = getIntent();
        final InputMethodManager imm = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
        final EditText orderNumberText = this.findViewById(R.id.orderNumber);

        final Integer oldOrderNumber;


        assert imm != null;
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);

        if (intent.hasExtra("orderNumber")) {
            final int orderNumber = Objects.requireNonNull(intent.getExtras()).getInt("orderNumber");
            orderNumberText.setText(Integer.toString(orderNumber));
            oldOrderNumber = orderNumber;
        }
        else {
            oldOrderNumber = null;
        }
        orderNumberText.requestFocus();


        orderNumberText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId == EditorInfo.IME_ACTION_DONE){
                    int orderNumber =  Integer.parseInt(orderNumberText.getText().toString());
                    Intent addOrderIntent = new Intent(addOrderNumber.this, AddOrder.class);
                    addOrderIntent.putExtra("orderNumber", orderNumber);
                    if (oldOrderNumber != null) {
                        if (orderNumber != oldOrderNumber) {
                            addOrderIntent.putExtra("oldOrderNumber", oldOrderNumber);
                        }
                    }
                    startActivity(addOrderIntent);
                }
                return false;
            }

        });
    }
}
