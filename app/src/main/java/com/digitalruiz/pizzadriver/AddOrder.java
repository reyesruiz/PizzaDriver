package com.digitalruiz.pizzadriver;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.database.Cursor;
import android.icu.math.BigDecimal;
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
import android.widget.Toast;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

public class AddOrder extends AppCompatActivity {


    public TextView TipText;
    public EditText tipEditText;
    public CheckBox cashCheckedBox;
    public TextView orderTotalText;
    public EditText orderTotalEditText;
    public TextView cashReceivedText;
    public EditText cashReceivedEditText;

    String orderType;
    String OrderLocation;
    Integer orderNumber;
    Integer oldOrderNumber;
    boolean locationTracy;
    boolean locationMountainHouse;
    BigDecimal Tip;
    Integer TipCashBool;
    BigDecimal OrderTotal;
    BigDecimal CashReceived;

    SQLiteDBHelper pizzaDriverDB;

    Button SaveButton;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_order);

        Intent intent = getIntent();
        orderNumber = intent.getExtras().getInt("orderNumber");
        oldOrderNumber = intent.getExtras().getInt("oldOrderNumber");




        pizzaDriverDB = new SQLiteDBHelper(this);
        SaveButton = (Button)findViewById(R.id.saveButton);

        final Chip orderNumberChip = (Chip)this.findViewById(R.id.orderNumberChip);

        final ChipGroup orderTypeChipGroup = (ChipGroup)findViewById(R.id.orderType);

        final Chip creditAutoChip = (Chip)this.findViewById(R.id.creditAutoType);
        final Chip creditManualChip = (Chip)this.findViewById(R.id.creditManualType);
        final Chip cashChip = (Chip)this.findViewById(R.id.cashType);
        final Chip grubhubChip = (Chip)this.findViewById(R.id.grubhubType);
        final Chip otherChip = (Chip)this.findViewById(R.id.otherType);

        final ChipGroup orderLocationChipGroup = (ChipGroup)findViewById(R.id.OrderLocation);

        final Chip tracyChip = (Chip)this.findViewById(R.id.tracyChip);
        final Chip mountainHouseChip = (Chip)this.findViewById(R.id.mountainHouseChip);

        TipText = (TextView)findViewById(R.id.tipStatic);
        orderTotalText = (TextView)findViewById(R.id.orderTotalStatic);
        cashReceivedText = (TextView)findViewById(R.id.cashReceivedStatic);

        tipEditText = (EditText)findViewById(R.id.tip);
        orderTotalEditText = (EditText)findViewById(R.id.orderTotal);
        cashReceivedEditText = (EditText)findViewById(R.id.cashReceived);

        cashCheckedBox = (CheckBox)this.findViewById(R.id.cashCheckedBox);

        if (oldOrderNumber != null)  {
            pizzaDriverDB.updateOrderNumber(oldOrderNumber, orderNumber);
        }

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



        Cursor result = pizzaDriverDB.getData(orderNumber);
        if (result.getCount() == 1){
            result.moveToFirst();
            orderType = result.getString(result.getColumnIndex("OrderType"));
            if (orderType.equals("Credit Auto")){
                creditAutoChip.setChecked(true);
                creditAuto();
            }
            else if (orderType.equals("Credit Manual")){
                creditManualChip.setChecked(true);
                creditManual();
            }
            else if (orderType.equals("Cash")){
                cashChip.setChecked(true);
                cash();
            }
            else if (orderType.equals("Grubhub")){
                grubhubChip.setChecked(true);
                grubhub();
            }
            else if (orderType.equals("Other")){
                otherChip.setChecked(true);
                other();
            }
            else {
                //Nothing
            }

            OrderLocation = result.getString(result.getColumnIndex("Location"));
            if (OrderLocation.equals("Tracy")){
                tracyChip.setChecked(true);
            }
            else if (OrderLocation.equals("Mountain House")){
                mountainHouseChip.setChecked(true);
            }
            else {
                // What to do here, set default?
            }

            Tip = new BigDecimal(result.getString(result.getColumnIndex("Tip")));
            tipEditText.setText(Tip.toString());

            TipCashBool = Integer.parseInt(result.getString(result.getColumnIndex("TipCashBool")));
            if (TipCashBool == 1){
                cashCheckedBox.setChecked(true);
            }
            else {
                cashCheckedBox.setChecked(false);
            }

            OrderTotal = new BigDecimal(result.getString(result.getColumnIndex("OrderTotal")));
            orderTotalEditText.setText(OrderTotal.toString());

            CashReceived = new BigDecimal(result.getString(result.getColumnIndex("CashReceived")));
            cashReceivedEditText.setText(CashReceived.toString());


            result.close();




        }


        creditAutoChip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (creditAutoChip.isChecked()){
                    creditAuto();
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
                    creditManual();
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
                    cash();
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
                    grubhub();
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
                    other();
                }
                else {
                    setInvisible();
                }
            }
        });

        orderNumberChip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent changeNumberIntent = new Intent(AddOrder.this, addOrderNumber.class);
                changeNumberIntent.putExtra("orderNumber", orderNumber);
                startActivity(changeNumberIntent);
            }
        });

        SaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.v("Test", "Selected " + orderTypeChipGroup.getCheckedChipId());
                int OrderTypeSelectedChipID = orderTypeChipGroup.getCheckedChipId();
                // TODO SIMPLIFY THIS
                boolean error = false;

                if (OrderTypeSelectedChipID == -1) {
                    error = true;
                }
                else {
                    Chip OrderTypeSelectedChip = (Chip)findViewById(OrderTypeSelectedChipID);
                    orderType = OrderTypeSelectedChip.getText().toString();
                }

                int OrderLocationSelectedChipID = orderLocationChipGroup.getCheckedChipId();
                if (OrderLocationSelectedChipID == -1){
                    error = true;
                }
                else {
                    Chip OrderLocationSelectedChip = (Chip) findViewById(OrderLocationSelectedChipID);
                    OrderLocation = OrderLocationSelectedChip.getText().toString();
                }
                if (error){
                    Toast toast = Toast.makeText(getApplicationContext(), "Please select all options", Toast.LENGTH_LONG);
                    toast.show();
                }
                else {
                    if (cashCheckedBox.isChecked()) {
                        TipCashBool = 1;
                    }
                    else {
                        TipCashBool = 0;
                    }
                    if (TextUtils.isEmpty(tipEditText.getText().toString())){
                        Tip = new BigDecimal("0.00");
                    }
                    else {
                        Tip = new BigDecimal(tipEditText.getText().toString());
                    }


                    if (TextUtils.isEmpty(orderTotalEditText.getText().toString())){
                        OrderTotal = new BigDecimal("0.00");
                    }
                    else {
                        OrderTotal = new BigDecimal(orderTotalEditText.getText().toString());
                    }
                    if (TextUtils.isEmpty(cashReceivedEditText.getText().toString())){
                        CashReceived = new BigDecimal("0.00");
                    }
                    else {
                        CashReceived = new BigDecimal(cashReceivedEditText.getText().toString());
                    }
                    Cursor data = pizzaDriverDB.getData(orderNumber);
                    Intent BackToMain = new Intent(AddOrder.this, OrderList.class);
                    if (data.getCount() == 1) {
                        boolean updateResult = pizzaDriverDB.updateOrder(orderNumber, orderType, Tip.toString(), TipCashBool, OrderTotal.toString(), CashReceived.toString(), OrderLocation);
                        data.close();
                        if (updateResult){
                            Log.v("Test", "Data Updated " + updateResult);
                            Toast updateToast = Toast.makeText(getApplicationContext(), "Update Success", Toast.LENGTH_SHORT);
                            updateToast.show();
                            startActivity(BackToMain);
                        }
                        else {
                            Log.v("Test", "ERROR inserting data" + updateResult);
                            Toast updateToast = Toast.makeText(getApplicationContext(), "Unable to update data", Toast.LENGTH_LONG);
                            updateToast.show();
                        }

                    }
                    else {
                        boolean result = pizzaDriverDB.insertOrder(orderNumber, orderType, Tip.toString(), TipCashBool, OrderTotal.toString(), CashReceived.toString(), OrderLocation);
                        if (result) {
                            Log.v("Test", "Data inserted" + result);
                            Toast toast = Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_SHORT);
                            toast.show();
                            startActivity(BackToMain);

                        } else {
                            Log.v("Test", "ERROR inserting data" + result);
                            Toast toast = Toast.makeText(getApplicationContext(), "Unable to insert data", Toast.LENGTH_LONG);
                            toast.show();

                        }
                    }

                }
            }
        });

        tipEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if ((tipEditText.getText().toString().equals("0")) || (tipEditText.getText().toString().equals("0.0")) || (tipEditText.getText().toString().equals("0.00")) ){
                    tipEditText.setText("");
                }
            }
        });

        orderTotalEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if ((orderTotalEditText.getText().toString().equals("0")) || (orderTotalEditText.getText().toString().equals("0.0")) || (orderTotalEditText.getText().toString().equals("0.00")) ){
                    orderTotalEditText.setText("");
                }
            }
        });

        cashReceivedEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if ((cashReceivedEditText.getText().toString().equals("0")) || (cashReceivedEditText.getText().toString().equals("0.0")) || (cashReceivedEditText.getText().toString().equals("0.00"))){
                    cashReceivedEditText.setText("");
                }
            }
        });





    }

    private void setInvisible() {
        //Setting everything to invisible.
        TipText.setVisibility(TextView.INVISIBLE);
        tipEditText.setVisibility(EditText.INVISIBLE);
        cashCheckedBox.setVisibility(CheckBox.INVISIBLE);

        orderTotalText.setVisibility((TextView.INVISIBLE));
        orderTotalEditText.setVisibility(EditText.INVISIBLE);

        cashReceivedText.setVisibility(TextView.INVISIBLE);
        cashReceivedEditText.setVisibility(EditText.INVISIBLE);
    }

    private void creditAuto() {
        setInvisible();
        TipText.setVisibility(TextView.VISIBLE);
        tipEditText.setVisibility(EditText.VISIBLE);
    }

    private void creditManual() {
        setInvisible();
        TipText.setVisibility(TextView.VISIBLE);
        tipEditText.setVisibility(EditText.VISIBLE);
        cashCheckedBox.setVisibility(CheckBox.VISIBLE);
        cashCheckedBox.setChecked(false);
    }

    private void cash() {
        setInvisible();
        TipText.setVisibility(TextView.VISIBLE);
        tipEditText.setVisibility(EditText.VISIBLE);
        cashCheckedBox.setVisibility(CheckBox.VISIBLE);
        cashCheckedBox.setChecked(true);
        orderTotalText.setVisibility(TextView.VISIBLE);
        orderTotalEditText.setVisibility(EditText.VISIBLE);
        cashReceivedText.setVisibility(TextView.VISIBLE);
        cashReceivedEditText.setVisibility(EditText.VISIBLE);

        if (TextUtils.isEmpty(tipEditText.getText().toString())) {
            Tip = new BigDecimal("0.00");
        }
        else {
            Tip = new BigDecimal(tipEditText.getText().toString());
        }
        if (TextUtils.isEmpty(orderTotalEditText.getText().toString())){
            OrderTotal = new BigDecimal("0.00");
        }
        else {
            OrderTotal = new BigDecimal(orderTotalEditText.getText().toString());
        }
        if (TextUtils.isEmpty(cashReceivedEditText.getText().toString())){
            CashReceived = new BigDecimal("0.00");
        }
        else {
            CashReceived = new BigDecimal(cashReceivedEditText.getText().toString());
        }
        orderTotalEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Log.v("Test", "changed");
                if (TextUtils.isEmpty(tipEditText.getText().toString())) {
                    Tip = new BigDecimal("0.00");
                }
                else {
                    Tip = new BigDecimal(tipEditText.getText().toString());
                }
                if (TextUtils.isEmpty(orderTotalEditText.getText().toString())){
                    OrderTotal = new BigDecimal("0.00");
                }
                else {
                    OrderTotal = new BigDecimal(orderTotalEditText.getText().toString());
                }
                if (TextUtils.isEmpty(cashReceivedEditText.getText().toString())){
                    CashReceived = new BigDecimal("0.00");
                }
                else {
                    CashReceived = new BigDecimal(cashReceivedEditText.getText().toString());
                }
                Tip = new BigDecimal(CashReceived.subtract(OrderTotal).toString());
                Log.v("Test", "changed" + tipEditText.toString());
                tipEditText.setText(Tip.toString());
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
                if (TextUtils.isEmpty(tipEditText.getText().toString())) {
                    Tip = new BigDecimal("0.00");
                }
                else {
                    Tip = new BigDecimal(tipEditText.getText().toString());
                }
                if (TextUtils.isEmpty(orderTotalEditText.getText().toString())){
                    OrderTotal = new BigDecimal("0.00");
                }
                else {
                    OrderTotal = new BigDecimal(orderTotalEditText.getText().toString());
                }
                if (TextUtils.isEmpty(cashReceivedEditText.getText().toString())){
                    CashReceived = new BigDecimal("0.00");
                }
                else {
                    CashReceived = new BigDecimal(cashReceivedEditText.getText().toString());
                }
                Tip = new BigDecimal(CashReceived.subtract(OrderTotal).toString());
                Log.v("Test", "changed" + Tip.toString());
                tipEditText.setText(Tip.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void grubhub() {
        setInvisible();
        TipText.setVisibility(TextView.VISIBLE);
        tipEditText.setVisibility(EditText.VISIBLE);
    }

    private void other() {
        setInvisible();
        TipText.setVisibility(TextView.VISIBLE);
        tipEditText.setVisibility(EditText.VISIBLE);
        cashCheckedBox.setVisibility(CheckBox.VISIBLE);
        cashCheckedBox.setChecked(false);
        orderTotalText.setVisibility(TextView.VISIBLE);
        orderTotalEditText.setVisibility(EditText.VISIBLE);
        cashReceivedText.setVisibility(TextView.VISIBLE);
        cashReceivedEditText.setVisibility(EditText.VISIBLE);
    }
}
