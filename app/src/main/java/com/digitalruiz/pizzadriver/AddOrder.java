package com.digitalruiz.pizzadriver;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

public class AddOrder extends AppCompatActivity {


    public TextView TipCreditText;
    public EditText tipCreditEditText;
    public CheckBox cashCheckedBox;
    public TextView orderTotalText;
    public EditText orderTotalEditText;
    public TextView cashReceivedText;
    public EditText cashReceivedEditText;

    Integer orderNumber;
    Boolean locationTracy;
    Boolean locationMountainHouse;
    String orderType;
    double TipCredit;
    double TipCash;
    double OrderTotal;
    double CashReceived;
    String location;

    SQLiteDBHelper pizzaDriverDB;

    Button SaveButton;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_order);

        Intent intent = getIntent();
        orderNumber = intent.getExtras().getInt("orderNumber");
        locationTracy = intent.getExtras().getBoolean("locationTracy");
        locationMountainHouse = intent.getExtras().getBoolean("locationMountainHouse");

        pizzaDriverDB = new SQLiteDBHelper(this);

        SaveButton = (Button)findViewById(R.id.saveButton);

        final Chip orderNumberChip = (Chip)this.findViewById(R.id.orderNumberChip);

        final ChipGroup orderTypeChipGroup = (ChipGroup)findViewById(R.id.orderType);

        final Chip creditAutoChip = (Chip)this.findViewById(R.id.creditAutoType);
        final Chip creditManualChip = (Chip)this.findViewById(R.id.creditManualType);
        final Chip cashChip = (Chip)this.findViewById(R.id.cashType);
        final Chip grubhubChip = (Chip)this.findViewById(R.id.grubhubType);
        final Chip otherChip = (Chip)this.findViewById(R.id.otherType);

        final ChipGroup placeChipGroup = (ChipGroup)findViewById(R.id.placeChip);

        final Chip tracyChip = (Chip)this.findViewById(R.id.tracyChip);
        final Chip mountainHouseChip = (Chip)this.findViewById(R.id.mountainHouseChip);

        TipCreditText = (TextView)findViewById(R.id.tipCreditStatic);
        orderTotalText = (TextView)findViewById(R.id.orderTotalStatic);
        cashReceivedText = (TextView)findViewById(R.id.cashReceivedStatic);

        tipCreditEditText = (EditText)findViewById(R.id.tipCredit);
        orderTotalEditText = (EditText)findViewById(R.id.orderTotal);
        cashReceivedEditText = (EditText)findViewById(R.id.cashReceived);

        cashCheckedBox = (CheckBox)this.findViewById(R.id.cashCheckedBox);

        orderNumberChip.setText(orderNumber.toString());



        Log.v("test", "bla" + "");
        tracyChip.setChecked(locationTracy);
        mountainHouseChip.setChecked(locationMountainHouse);
        if (tracyChip.isChecked()){
            Log.v("test", "tracy");

        }
        else if (mountainHouseChip.isChecked()){

        }
        else {
            tracyChip.setChecked(true);
        }
        setInvisible();
        creditAutoChip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (creditAutoChip.isChecked()){
                    setInvisible();
                    TipCreditText.setVisibility(TextView.VISIBLE);
                    tipCreditEditText.setVisibility(EditText.VISIBLE);
                }
                else {
                    setInvisible();

                }
            }
        });
        creditManualChip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (creditManualChip.isChecked()){
                    setInvisible();
                    TipCreditText.setVisibility(TextView.VISIBLE);
                    tipCreditEditText.setVisibility(EditText.VISIBLE);
                    cashCheckedBox.setVisibility(CheckBox.VISIBLE);
                    cashCheckedBox.setChecked(false);
                }
                else {
                    setInvisible();
                }
            }
        });
        cashChip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cashChip.isChecked()){
                    setInvisible();
                    TipCreditText.setVisibility(TextView.VISIBLE);
                    tipCreditEditText.setVisibility(EditText.VISIBLE);
                    cashCheckedBox.setVisibility(CheckBox.VISIBLE);
                    cashCheckedBox.setChecked(true);
                    orderTotalText.setVisibility(TextView.VISIBLE);
                    orderTotalEditText.setVisibility(EditText.VISIBLE);
                    cashReceivedText.setVisibility(TextView.VISIBLE);
                    cashReceivedEditText.setVisibility(EditText.VISIBLE);

                    if (TextUtils.isEmpty(tipCreditEditText.getText().toString())) {
                        TipCredit = 0.00d;
                    }
                    else {
                        TipCredit = Double.parseDouble(tipCreditEditText.getText().toString());
                    }
                    if (TextUtils.isEmpty(orderTotalEditText.getText().toString())){
                        OrderTotal = 0.00d;
                    }
                    else {
                        OrderTotal = Double.parseDouble(orderTotalEditText.getText().toString());
                    }
                    if (TextUtils.isEmpty(cashReceivedEditText.getText().toString())){
                        CashReceived = 0.00d;
                    }
                    else {
                        CashReceived = Double.parseDouble(cashReceivedEditText.getText().toString());
                    }
                    orderTotalEditText.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                        }

                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {
                            Log.v("Test", "changed");
                            if (TextUtils.isEmpty(tipCreditEditText.getText().toString())) {
                                TipCredit = 0.00d;
                            }
                            else {
                                TipCredit = Double.parseDouble(tipCreditEditText.getText().toString());
                            }
                            if (TextUtils.isEmpty(orderTotalEditText.getText().toString())){
                                OrderTotal = 0.00d;
                            }
                            else {
                                OrderTotal = Double.parseDouble(orderTotalEditText.getText().toString());
                            }
                            if (TextUtils.isEmpty(cashReceivedEditText.getText().toString())){
                                CashReceived = 0.00d;
                            }
                            else {
                                CashReceived = Double.parseDouble(cashReceivedEditText.getText().toString());
                            }
                            double tip = CashReceived - OrderTotal;
                            Log.v("Test", "changed" + tip);
                            tipCreditEditText.setText(Double.toString(tip));
                        }

                        @Override
                        public void afterTextChanged(Editable s) {

                        }
                    });
                    cashReceivedEditText.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                        }

                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {
                            Log.v("Test", "changed");
                            if (TextUtils.isEmpty(tipCreditEditText.getText().toString())) {
                                TipCredit = 0.00d;
                            }
                            else {
                                TipCredit = Double.parseDouble(tipCreditEditText.getText().toString());
                            }
                            if (TextUtils.isEmpty(orderTotalEditText.getText().toString())){
                                OrderTotal = 0.00d;
                            }
                            else {
                                OrderTotal = Double.parseDouble(orderTotalEditText.getText().toString());
                            }
                            if (TextUtils.isEmpty(cashReceivedEditText.getText().toString())){
                                CashReceived = 0.00d;
                            }
                            else {
                                CashReceived = Double.parseDouble(cashReceivedEditText.getText().toString());
                            }
                            double tip = CashReceived - OrderTotal;
                            Log.v("Test", "changed" + tip);
                            tipCreditEditText.setText(Double.toString(tip));
                        }

                        @Override
                        public void afterTextChanged(Editable s) {

                        }
                    });
                }
                else {
                    setInvisible();
                }
            }
        });
        grubhubChip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (grubhubChip.isChecked()){
                    setInvisible();
                    TipCreditText.setVisibility(TextView.VISIBLE);
                    tipCreditEditText.setVisibility(EditText.VISIBLE);
                }
                else {
                    setInvisible();
                }
            }
        });
        otherChip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (otherChip.isChecked()){
                    setInvisible();
                    TipCreditText.setVisibility(TextView.VISIBLE);
                    tipCreditEditText.setVisibility(EditText.VISIBLE);
                    cashCheckedBox.setVisibility(CheckBox.VISIBLE);
                    cashCheckedBox.setChecked(false);
                    orderTotalText.setVisibility(TextView.VISIBLE);
                    orderTotalEditText.setVisibility(EditText.VISIBLE);
                    cashReceivedText.setVisibility(TextView.VISIBLE);
                    cashReceivedEditText.setVisibility(EditText.VISIBLE);
                }
                else {
                    setInvisible();
                }
            }
        });

        orderNumberChip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Boolean locationTracy = tracyChip.isChecked();
                final Boolean locationMountainHouse = mountainHouseChip.isChecked();
                Intent changeNumberIntent = new Intent(AddOrder.this, addOrderNumber.class);
                changeNumberIntent.putExtra("orderNumber", orderNumber);
                changeNumberIntent.putExtra("locationTracy", locationTracy);
                changeNumberIntent.putExtra("locationMountainHouse", locationMountainHouse);
                startActivity(changeNumberIntent);

            }
        });

        SaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                orderType = "CreditAuto";
                TipCredit = 10.00d;
                TipCash = 0d;
                OrderTotal = 0d;
                CashReceived = 0d;
                location = "Tracy";
                Boolean result = pizzaDriverDB.insertOrder(orderNumber, orderType, TipCredit, TipCash, OrderTotal, CashReceived, location);
                if (result){
                    Log.v("Test", "Data inserted" + result);
                }
                else {
                    Log.v("Test", "ERROR inserting data");
                }
            }
        });


    }

    private void setInvisible() {
        //Setting everything to invisible.
        TipCreditText.setVisibility(TextView.INVISIBLE);
        tipCreditEditText.setVisibility(EditText.INVISIBLE);
        cashCheckedBox.setVisibility(CheckBox.INVISIBLE);

        orderTotalText.setVisibility((TextView.INVISIBLE));
        orderTotalEditText.setVisibility(EditText.INVISIBLE);

        cashReceivedText.setVisibility(TextView.INVISIBLE);
        cashReceivedEditText.setVisibility(EditText.INVISIBLE);
    }


}
