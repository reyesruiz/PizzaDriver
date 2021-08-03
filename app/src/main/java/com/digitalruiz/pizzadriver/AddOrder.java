package com.digitalruiz.pizzadriver;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.icu.math.BigDecimal;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

    TextWatcher tipTextWatcher = null;
    TextWatcher orderTotalTextWatcher = null;
    TextWatcher cashReceivedTextWatcher = null;





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
            Tip = Tip.divide(BigDecimal.valueOf(1),2, BigDecimal.ROUND_UNNECESSARY);
            tipEditText.setText(Tip.toString());
            tipEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    Log.d("TEST", "onFocusChange: ");
                    tipEditText.setSelection(tipEditText.getText().toString().length());
                }
            });

            TipCashBool = Integer.parseInt(result.getString(result.getColumnIndex("TipCashBool")));
            cashCheckedBox.setChecked(TipCashBool == 1);

            OrderTotal = new BigDecimal(result.getString(result.getColumnIndex("OrderTotal")));
            OrderTotal = OrderTotal.divide(BigDecimal.valueOf(1),2, BigDecimal.ROUND_UNNECESSARY);
            orderTotalEditText.setText(OrderTotal.toString());
            orderTotalEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    Log.d("TEST", "onFocusChange: ");
                    orderTotalEditText.setSelection(orderTotalEditText.getText().toString().length());
                }
            });
            orderTotalEditText.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("TEST", "onClick: ");
                    orderTotalEditText.setSelection(orderTotalEditText.getText().toString().length());
                }
            });

            CashReceived = new BigDecimal(result.getString(result.getColumnIndex("CashReceived")));
            CashReceived = CashReceived.divide(BigDecimal.valueOf(1),2, BigDecimal.ROUND_UNNECESSARY);
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

        orderNumberChip.setOnClickListener(v -> showPopup(v, orderNumber));

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

        tipChangedText();
        orderTotalChangedText();
        cashReceivedChangedText();
    }

    private void tipChangedText() {
        if (tipEditText.getText().toString().isEmpty() || tipEditText.getText().toString().equals("0")){
            tipEditText.setText("0.00");
        }
        tipEditText.setSelection(tipEditText.getText().toString().length());

        tipTextWatcher = new TextWatcher() {
            BigDecimal r;

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                Log.d("CHANGED", "beforeTextChanged: " + s.toString());
                Log.d("CHANGED", "beforeTextChanged: " + start);
                Log.d("CHANGED", "beforeTextChanged: " + count);
                Log.d("CHANGED", "beforeTextChanged: " + after);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Log.d("CHANGED", "onTextChanged: " + s.toString());
                Log.d("CHANGED", "onTextChanged: " + start);
                Log.d("CHANGED", "onTextChanged: " + before);
                Log.d("CHANGED", "onTextChanged: " + count);
                Log.d("TESTS", s.toString());
                Pattern p = Pattern.compile("^\\.\\d*$");
                Matcher m = p.matcher(s.toString());
                boolean b = m.matches();
                if (b == true){
                    Log.d("MATCHED", "MATCHED: " + b);
                    s = "0" + s;
                    Log.d("MATCHED", "MATCHED: " + s);
                    r = new BigDecimal(s.toString());
                }
                else {
                    r = new BigDecimal(s.toString());
                    if (before == 0) {
                        r = r.multiply(BigDecimal.valueOf(10));
                        r = r.setScale(2);
                        Log.d("CHANGED", "onTextChangedHERE: " + r);
                    } else {
                        r = r.divide(BigDecimal.valueOf(10), 2, BigDecimal.ROUND_UNNECESSARY);
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                Log.d("CHANGED", "afterTextChanged: " + s.toString());
                tipEditText.removeTextChangedListener(this);
                s.clear();
                s.append(r.toString());
                tipChangedText();

            }
        };
        tipEditText.addTextChangedListener(tipTextWatcher);
    }


    private void orderTotalChangedText() {
        if (orderTotalEditText.getText().toString().isEmpty() || orderTotalEditText.getText().toString().equals("0")){
            orderTotalEditText.setText("0.00");
        }
        orderTotalEditText.setSelection(orderTotalEditText.getText().toString().length());
        orderTotalTextWatcher = new TextWatcher() {
            BigDecimal r;
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                Log.d("CHANGED", "beforeTextChanged: " + s.toString());
                Log.d("CHANGED", "beforeTextChanged: " + start);
                Log.d("CHANGED", "beforeTextChanged: " + count);
                Log.d("CHANGED", "beforeTextChanged: " + after);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Log.d("CHANGED", "onTextChanged: " + s.toString());
                Log.d("CHANGED", "onTextChanged: " + start);
                Log.d("CHANGED", "onTextChanged: " + before);
                Log.d("CHANGED", "onTextChanged: " + count);
                Log.d("TESTS", s.toString());
                Pattern p = Pattern.compile("^\\.\\d*$");
                Matcher m = p.matcher(s.toString());
                boolean b = m.matches();
                if (b == true){
                    Log.d("MATCHED", "MATCHED: " + b);
                    s = "0" + s;
                    Log.d("MATCHED", "MATCHED: " + s);
                    r = new BigDecimal(s.toString());
                }
                else {
                    r = new BigDecimal(s.toString());
                    if (before == 0) {
                        r = r.multiply(BigDecimal.valueOf(10));
                        r = r.setScale(2);
                        Log.d("CHANGED", "onTextChangedHERE: " + r);
                    } else {
                        r = r.divide(BigDecimal.valueOf(10), 2, BigDecimal.ROUND_UNNECESSARY);
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                Log.d("CHANGED", "afterTextChanged: " + s.toString());
                orderTotalEditText.removeTextChangedListener(this);
                s.clear();
                s.append(r.toString());
                orderTotalChangedText();

            }
        };
        orderTotalEditText.addTextChangedListener(orderTotalTextWatcher);
    }

    private void cashReceivedChangedText() {
        if (cashReceivedEditText.getText().toString().isEmpty() || cashReceivedEditText.getText().toString().equals("0")){
            cashReceivedEditText.setText("0.00");
        }
        cashReceivedEditText.setSelection(cashReceivedEditText.getText().toString().length());
        cashReceivedTextWatcher = new TextWatcher() {
            BigDecimal r;
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                Log.d("CHANGED", "beforeTextChanged: " + s.toString());
                Log.d("CHANGED", "beforeTextChanged: " + start);
                Log.d("CHANGED", "beforeTextChanged: " + count);
                Log.d("CHANGED", "beforeTextChanged: " + after);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Log.d("CHANGED", "onTextChanged: " + s.toString());
                Log.d("CHANGED", "onTextChanged: " + start);
                Log.d("CHANGED", "onTextChanged: " + before);
                Log.d("CHANGED", "onTextChanged: " + count);
                Log.d("TESTS", s.toString());
                Pattern p = Pattern.compile("^\\.\\d*$");
                Matcher m = p.matcher(s.toString());
                boolean b = m.matches();
                if (b == true){
                    Log.d("MATCHED", "MATCHED: " + b);
                    s = "0" + s;
                    Log.d("MATCHED", "MATCHED: " + s);
                    r = new BigDecimal(s.toString());
                }
                else {
                    r = new BigDecimal(s.toString());
                    if (before == 0) {
                        r = r.multiply(BigDecimal.valueOf(10));
                        r = r.setScale(2);
                        Log.d("CHANGED", "onTextChangedHERE: " + r);
                    } else {
                        r = r.divide(BigDecimal.valueOf(10), 2, BigDecimal.ROUND_UNNECESSARY);
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                Log.d("CHANGED", "afterTextChanged: " + s.toString());
                cashReceivedEditText.removeTextChangedListener(this);
                s.clear();
                s.append(r.toString());
                tipEditText.removeTextChangedListener(tipTextWatcher);
                BigDecimal cashReceived;
                cashReceived = new BigDecimal(r.toString());
                BigDecimal orderTotal;
                orderTotal = new BigDecimal(orderTotalEditText.getText().toString());
                BigDecimal newTip;
                newTip = new BigDecimal(String.valueOf(cashReceived.subtract(orderTotal)));
                tipEditText.setText(newTip.toString());
                tipChangedText();
                cashReceivedChangedText();

            }
        };
        cashReceivedEditText.addTextChangedListener(cashReceivedTextWatcher);
    }

    public void tipEditTextOnClick (View v){
        Log.d("ONCLICK", "tipEditTextOnClick: " + "CLICKED");
        if (tipEditText.getText().toString().isEmpty() || tipEditText.getText().toString().equals("0")){
            tipEditText.setText("0.00");
        }
        tipEditText.setSelection(tipEditText.getText().toString().length());
    }

    private void showPopup(View view, int OrderNumber) {
        PopupMenu popup = new PopupMenu(view.getContext(), view);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.order_number_options, popup.getMenu());
        popup.show();

        MenuItem change = popup.getMenu().findItem(R.id.order_change_number);
        change.setOnMenuItemClickListener(v ->{
            final String[] m_Text = {""};
            AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
            builder.setTitle("New order number");
            final EditText input = new EditText(view.getContext());
            input.setInputType(InputType.TYPE_CLASS_NUMBER);
            builder.setView(input);
            builder.setPositiveButton("OK", (dialog, which) -> {
                m_Text[0] = input.getText().toString();
                int NewOrderNumber = Integer.parseInt(m_Text[0]);
                boolean changed = pizzaDriverDB.updateOrderNumber(OrderNumber, NewOrderNumber);
                if (changed){
                    Toast updateToast = Toast.makeText(view.getContext(), "Updated order number " + OrderNumber + " to " + NewOrderNumber, Toast.LENGTH_SHORT);
                    updateToast.show();
                    Intent addOrderIntent = new Intent(AddOrder.this, MainActivity.class);
                    startActivity(addOrderIntent);
                }
                else {
                    Toast updateToast = Toast.makeText(view.getContext(), "Unable to update order number " + OrderNumber + " to " + NewOrderNumber + " please check...", Toast.LENGTH_LONG);
                    updateToast.show();
                    dialog.cancel();
                }

            });
            builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());

            builder.show();

            // boolean changed = pizzaDriverDB.updateOrderNumber();
            return true;
        });
        MenuItem delete = popup.getMenu().findItem(R.id.order_delete);
        delete.setOnMenuItemClickListener(v ->{
            boolean deleted = pizzaDriverDB.deleteOrder(OrderNumber);
            if (deleted) {
                Toast deletedToast = Toast.makeText(view.getContext(), "Deleted Order Number " + OrderNumber, Toast.LENGTH_SHORT);
                deletedToast.show();
                Intent addOrderIntent = new Intent(AddOrder.this, MainActivity.class);
                startActivity(addOrderIntent);
            }
            else {
                Toast deletedToast = Toast.makeText(view.getContext(), "Unable to delete Order Number " + OrderNumber + " , something wrong", Toast.LENGTH_LONG);
                deletedToast.show();
            }
            return deleted;
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
