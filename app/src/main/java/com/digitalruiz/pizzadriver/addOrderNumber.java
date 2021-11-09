package com.digitalruiz.pizzadriver;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import java.util.Objects;

public class addOrderNumber extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_order_number);

        final InputMethodManager imm = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
        final EditText orderNumberText = this.findViewById(R.id.orderNumber);

        assert imm != null;
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);

        orderNumberText.requestFocus();

        orderNumberText.setOnEditorActionListener((v, actionId, event) -> {
            if(actionId == EditorInfo.IME_ACTION_DONE){
                int orderNumber =  Integer.parseInt(orderNumberText.getText().toString());
                Intent addOrderIntent = new Intent(addOrderNumber.this, AddOrder.class);
                addOrderIntent.putExtra("orderNumber", orderNumber);
                startActivity(addOrderIntent);
            }
            return false;
        });
    }
}
