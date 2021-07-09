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
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import java.util.Objects;

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
        orderNumber = Objects.requireNonNull(intent.getExtras()).getInt("orderNumber");
        oldOrderNumber = intent.getIntExtra("oldOrderNumber", -1);
        Log.v("test", "Oldordernumber" + oldOrderNumber.toString());



        pizzaDriverDB = new SQLiteDBHelper(this);
        SaveButton = findViewById(R.id.saveButton);

        final Chip orderNumberChip = this.findViewById(R.id.orderNumberChip);

        final ChipGroup orderTypeChipGroup = findViewById(R.id.orderType);

        final Chip creditAutoChip = this.findViewById(R.id.creditAutoType);
        final Chip creditManualChip = this.findViewById(R.id.creditManualType);
        final Chip cashChip = this.findViewById(R.id.cashType);
        final Chip grubhubChip = this.findViewById(R.id.grubhubType);
        final Chip levelUpChip = this.findViewById(R.id.levelUpType);
        final Chip otherChip = this.findViewById(R.id.otherType);

        final ChipGroup orderLocationChipGroup = findViewById(R.id.OrderLocation);

        final Chip tracyChip = this.findViewById(R.id.tracyChip);
        final Chip mountainHouseChip = this.findViewById(R.id.mountainHouseChip);

        TipText = findViewById(R.id.tipStatic);
        orderTotalText = findViewById(R.id.orderTotalStatic);
        cashReceivedText = findViewById(R.id.cashReceivedStatic);

        tipEditText = findViewById(R.id.tip);
        orderTotalEditText = findViewById(R.id.orderTotal);
        cashReceivedEditText = findViewById(R.id.cashReceived);

        cashCheckedBox = this.findViewById(R.id.cashCheckedBox);

        if ((oldOrderNumber != null && oldOrderNumber > 0)) {
            boolean updateResult = pizzaDriverDB.updateOrderNumber(oldOrderNumber, orderNumber);
            if (updateResult){
                Toast updateToast = Toast.makeText(getApplicationContext(), "Update Success" + oldOrderNumber, Toast.LENGTH_SHORT);
                updateToast.show();

            }
            else {
                Toast updateToast = Toast.makeText(getApplicationContext(), "Unable to update Order Number", Toast.LENGTH_LONG);
                updateToast.show();
            }
        }

        orderNumberChip.setText(orderNumber.toString());



        Log.v("test", "bla" + "");

        if ((!tracyChip.isChecked()) && (!mountainHouseChip.isChecked())){
            tracyChip.setChecked(true);
        }
        setInvisible();



        Cursor result = pizzaDriverDB.getData(orderNumber);
        if (result.getCount() == 1){
            result.moveToFirst();
            orderType = result.getString(result.getColumnIndex("OrderType"));
            switch (orderType) {
                case "Credit Auto":
                    creditAutoChip.setChecked(true);
                    creditAuto();
                    break;
                case "Credit Manual":
                    creditManualChip.setChecked(true);
                    creditManual();
                    break;
                case "Cash":
                    cashChip.setChecked(true);
                    cash();
                    break;
                case "Grubhub":
                    grubhubChip.setChecked(true);
                    grubhub();
                    break;
                case "LevelUp":
                    levelUpChip.setChecked(true);
                    levelup();
                    break;
                case "Other":
                    otherChip.setChecked(true);
                    other();
                    break;
                default:
                    //Nothing
                    break;
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
                tracyChip.setChecked(true);
            }

            Tip = new BigDecimal(result.getString(result.getColumnIndex("Tip")));
            tipEditText.setText(Tip.toString());

            TipCashBool = Integer.parseInt(result.getString(result.getColumnIndex("TipCashBool")));
            cashCheckedBox.setChecked(TipCashBool == 1);

            OrderTotal = new BigDecimal(result.getString(result.getColumnIndex("OrderTotal")));
            orderTotalEditText.setText(OrderTotal.toString());

            CashReceived = new BigDecimal(result.getString(result.getColumnIndex("CashReceived")));
            cashReceivedEditText.setText(CashReceived.toString());


            result.close();




        }


        creditAutoChip.setOnClickListener(v -> {
            if (creditAutoChip.isChecked()){
                creditAuto();
            }
            else {
                setInvisible();

            }
        });

        creditManualChip.setOnClickListener(v -> {
            if (creditManualChip.isChecked()){
                creditManual();
            }
            else {
                setInvisible();
            }
        });

        cashChip.setOnClickListener(v -> {
            if (cashChip.isChecked()){
                cash();
            }
            else {
                setInvisible();
            }
        });

        grubhubChip.setOnClickListener(v -> {
            if (grubhubChip.isChecked()){
                grubhub();
            }
            else {
                setInvisible();
            }
        });

        levelUpChip.setOnClickListener(v -> {
            if (levelUpChip.isChecked()){
                levelup();
            }
            else {
                setInvisible();
            }
        });

        otherChip.setOnClickListener(v -> {
            if (otherChip.isChecked()){
                other();
            }
            else {
                setInvisible();
            }
        });

        orderNumberChip.setOnClickListener(v -> {
            Intent changeNumberIntent = new Intent(AddOrder.this, addOrderNumber.class);
            changeNumberIntent.putExtra("orderNumber", orderNumber);
            startActivity(changeNumberIntent);
        });

        SaveButton.setOnClickListener(v -> {
            Log.v("Test", "Selected " + orderTypeChipGroup.getCheckedChipId());
            int OrderTypeSelectedChipID = orderTypeChipGroup.getCheckedChipId();
            // TODO SIMPLIFY THIS
            boolean error = false;

            if (OrderTypeSelectedChipID == -1) {
                error = true;
            }
            else {
                Chip OrderTypeSelectedChip = findViewById(OrderTypeSelectedChipID);
                orderType = OrderTypeSelectedChip.getText().toString();
            }

            int OrderLocationSelectedChipID = orderLocationChipGroup.getCheckedChipId();
            if (OrderLocationSelectedChipID == -1){
                error = true;
            }
            else {
                Chip OrderLocationSelectedChip = findViewById(OrderLocationSelectedChipID);
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
                Intent BackToMain = new Intent(AddOrder.this, MainActivity.class);
                if (data.getCount() == 1) {
                    boolean updateResult = pizzaDriverDB.updateOrder(orderNumber, orderType, Tip.toString(), TipCashBool, OrderTotal.toString(), CashReceived.toString(), OrderLocation);
                    data.close();
                    if (updateResult){
                        Toast updateToast = Toast.makeText(getApplicationContext(), "Update Success", Toast.LENGTH_SHORT);
                        updateToast.show();
                        startActivity(BackToMain);
                    }
                    else {
                        Toast updateToast = Toast.makeText(getApplicationContext(), "Unable to update data", Toast.LENGTH_LONG);
                        updateToast.show();
                    }

                }
                else {
                    boolean result1 = pizzaDriverDB.insertOrder(orderNumber, orderType, Tip.toString(), TipCashBool, OrderTotal.toString(), CashReceived.toString(), OrderLocation);
                    if (result1) {
                        Toast toast = Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_SHORT);
                        toast.show();
                        startActivity(BackToMain);

                    } else {
                        Toast toast = Toast.makeText(getApplicationContext(), "Unable to insert data", Toast.LENGTH_LONG);
                        toast.show();

                    }
                }

            }
        });

        tipEditText.setOnFocusChangeListener((v, hasFocus) -> {
            if ((tipEditText.getText().toString().equals("0")) || (tipEditText.getText().toString().equals("0.0")) || (tipEditText.getText().toString().equals("0.00")) ){
                tipEditText.setText("");
            }
        });

        orderTotalEditText.setOnFocusChangeListener((v, hasFocus) -> {
            if ((orderTotalEditText.getText().toString().equals("0")) || (orderTotalEditText.getText().toString().equals("0.0")) || (orderTotalEditText.getText().toString().equals("0.00")) ){
                orderTotalEditText.setText("");
            }
        });

        cashReceivedEditText.setOnFocusChangeListener((v, hasFocus) -> {
            if ((cashReceivedEditText.getText().toString().equals("0")) || (cashReceivedEditText.getText().toString().equals("0.0")) || (cashReceivedEditText.getText().toString().equals("0.00"))){
                cashReceivedEditText.setText("");
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
        cashCheckedBox.setVisibility(CheckBox.VISIBLE);
        cashCheckedBox.setChecked(false);
    }

    private void levelup() {
        setInvisible();
        TipText.setVisibility(TextView.VISIBLE);
        tipEditText.setVisibility(EditText.VISIBLE);
        cashCheckedBox.setVisibility(CheckBox.VISIBLE);
        cashCheckedBox.setChecked(false);
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
