package com.digitalruiz.pizzadriver;

import android.content.Intent;
import android.database.Cursor;
import android.icu.math.BigDecimal;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddOrderFragment#newInstance} factory method to
 * create an instance of this fragment.
 */

public class AddOrderFragment extends Fragment {
    public TextView TipText;
    public EditText tipEditText;
    public CheckBox cashCheckedBox;
    public TextView orderTotalText;
    public EditText orderTotalEditText;
    public TextView cashReceivedText;
    public EditText cashReceivedEditText;

    final String TAG = "TEST";
    String orderType;
    String OrderLocation;
    Integer orderNumber;
    BigDecimal Tip;
    Integer TipCashBool;
    BigDecimal OrderTotal;
    BigDecimal CashReceived;

    SQLiteDBHelper pizzaDriverDB;

    Button SaveButton;

    TextWatcher tipTextWatcher = null;
    TextWatcher orderTotalTextWatcher = null;
    TextWatcher cashReceivedTextWatcher = null;

    String workingDate;
    int OrderId;
    int TipId;
    int CashOrderId;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "SelectedDate";
    private static final String ARG_PARAM2 = "orderNumber";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private int mParam2;

    public AddOrderFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AddOrderFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AddOrderFragment newInstance(String param1, String param2) {
        AddOrderFragment fragment = new AddOrderFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getInt(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_order, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        workingDate = mParam1;
        orderNumber = mParam2;


        pizzaDriverDB = new SQLiteDBHelper(getContext());
        SaveButton = view.findViewById(R.id.saveButton);

        final Chip orderNumberChip = view.findViewById(R.id.orderNumberChip);

        final ChipGroup orderTypeChipGroup = view.findViewById(R.id.orderType);

        final Chip creditAutoChip = view.findViewById(R.id.creditAutoType);
        final Chip creditManualChip = view.findViewById(R.id.creditManualType);
        final Chip cashChip = view.findViewById(R.id.cashType);
        final Chip grubhubChip = view.findViewById(R.id.grubhubType);
        final Chip levelUpChip = view.findViewById(R.id.levelUpType);
        final Chip otherChip = view.findViewById(R.id.otherType);

        final ChipGroup orderLocationChipGroup = view.findViewById(R.id.OrderLocation);

        final Chip tracyChip = view.findViewById(R.id.tracyChip);
        final Chip mountainHouseChip = view.findViewById(R.id.mountainHouseChip);

        TipText = view.findViewById(R.id.tipStatic);
        orderTotalText = view.findViewById(R.id.orderTotalStatic);
        cashReceivedText = view.findViewById(R.id.cashReceivedStatic);

        tipEditText = view.findViewById(R.id.tip);
        orderTotalEditText = view.findViewById(R.id.orderTotal);
        cashReceivedEditText = view.findViewById(R.id.cashReceived);

        cashCheckedBox = view.findViewById(R.id.cashCheckedBox);

        orderNumberChip.setText(orderNumber.toString());

        Log.v("test", "bla" + "");

        if ((!tracyChip.isChecked()) && (!mountainHouseChip.isChecked())){
            tracyChip.setChecked(true);
        }
        setInvisible();

        Cursor order_result = pizzaDriverDB.getOrderData(workingDate, orderNumber);
        Log.d(TAG, "result: " + order_result.getCount());

        if (order_result.getCount() == 1) {
            order_result.moveToFirst();
            OrderId = order_result.getInt(order_result.getColumnIndex("OrderId"));
            int LocationId = order_result.getInt(order_result.getColumnIndex("LocationId"));
            Cursor location_result = pizzaDriverDB.getLocationData(LocationId);
            if (location_result.getCount() <= 1){
                location_result.moveToFirst();
                String Location = location_result.getString(location_result.getColumnIndex("Name"));

                switch (Location) {
                    case "Tracy":
                        tracyChip.setChecked(true);
                        break;
                    case "Mountain House":
                        mountainHouseChip.setChecked(true);
                        break;
                    default:
                        //Nothing
                        break;
                }
                orderNumberChip.setOnClickListener(v -> showPopup(v, orderNumber, OrderId, savedInstanceState));

            }
            Cursor tip_result = pizzaDriverDB.getTipData(OrderId);
            if (tip_result.getCount() >= 1){
                //TODO Right now only moving to first, will need to implement multiple tips per order.
                tip_result.moveToFirst();
                TipId = tip_result.getInt(tip_result.getColumnIndex("TipId"));
                String Amount = tip_result.getString(tip_result.getColumnIndex("Amount"));
                String Type = tip_result.getString(tip_result.getColumnIndex("Type"));
                int CashBool = tip_result.getInt(tip_result.getColumnIndex("Cash"));

                switch (Type) {
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

                Tip = new BigDecimal(Amount);
                Tip = Tip.divide(BigDecimal.valueOf(1),2, BigDecimal.ROUND_UNNECESSARY);
                tipEditText.setText(Tip.toString());
                tipEditText.setOnFocusChangeListener((v, hasFocus) -> {
                    Log.d("TEST", "onFocusChange: ");
                    tipEditText.setSelection(tipEditText.getText().toString().length());
                });

                TipCashBool = CashBool;
                cashCheckedBox.setChecked(TipCashBool == 1);

                if (Type.equals("Cash")){
                    Cursor cash_order_result = pizzaDriverDB.getCashOrderData(TipId);
                    cash_order_result.moveToFirst();
                    CashOrderId = cash_order_result.getInt(cash_order_result.getColumnIndex("CashOrderId"));
                    String Total = cash_order_result.getString(cash_order_result.getColumnIndex("Total"));
                    String Received = cash_order_result.getString(cash_order_result.getColumnIndex("Received"));

                    OrderTotal = new BigDecimal(Total);
                    OrderTotal = OrderTotal.divide(BigDecimal.valueOf(1),2, BigDecimal.ROUND_UNNECESSARY);
                    orderTotalEditText.setText(OrderTotal.toString());
                    orderTotalEditText.setOnFocusChangeListener((v, hasFocus) -> {
                        Log.d("TEST", "onFocusChange: ");
                        orderTotalEditText.setSelection(orderTotalEditText.getText().toString().length());
                    });

                    CashReceived = new BigDecimal(Received);
                    CashReceived = CashReceived.divide(BigDecimal.valueOf(1),2, BigDecimal.ROUND_UNNECESSARY);
                    cashReceivedEditText.setText(CashReceived.toString());
                    cashReceivedEditText.setOnFocusChangeListener((v, hasFocus) -> {
                        Log.d("TEST", "onFocusChange: ");
                        cashReceivedEditText.setSelection(cashReceivedEditText.getText().toString().length());
                    });
                }
            }


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

        SaveButton.setOnClickListener(v -> {
            Log.v("Test", "Selected " + orderTypeChipGroup.getCheckedChipId());
            int OrderTypeSelectedChipID = orderTypeChipGroup.getCheckedChipId();
            int LocationID = 0;
            // TODO SIMPLIFY THIS
            boolean error = false;

            if (OrderTypeSelectedChipID == -1) {
                error = true;
            }
            else {
                Chip OrderTypeSelectedChip = view.findViewById(OrderTypeSelectedChipID);
                orderType = OrderTypeSelectedChip.getText().toString();
            }

            int OrderLocationSelectedChipID = orderLocationChipGroup.getCheckedChipId();
            if (OrderLocationSelectedChipID == -1){
                error = true;
            }
            else {
                Chip OrderLocationSelectedChip = view.findViewById(OrderLocationSelectedChipID);
                OrderLocation = OrderLocationSelectedChip.getText().toString();
                //TODO set this programatically
                if (OrderLocation.equals("Tracy")){
                    LocationID = 1;
                }
                else if(OrderLocation.equals("Mountain House")){
                    LocationID = 2;
                }
            }
            if (error){
                Toast toast = Toast.makeText(getContext(), "Please select all options", Toast.LENGTH_LONG);
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


                if (OrderId >= 1) {
                    int OrderUpdateResult = pizzaDriverDB.updateOrder(OrderId, workingDate, orderNumber, LocationID);
                    if (OrderUpdateResult == 1){
                        if (TipId >=1) {
                            int TipUpdateResult = pizzaDriverDB.updateTip(TipId, Tip.toString(), orderType, TipCashBool, OrderId);
                            if (TipUpdateResult == 1){
                                if (orderType.equals("Cash")){
                                    if (CashOrderId >= 1){
                                        int CashOrderUpdateResult = pizzaDriverDB.updateCashOrder(CashOrderId, OrderTotal.toString(), CashReceived.toString(), TipId);
                                    }
                                    else {
                                        long insert_result_cash = pizzaDriverDB.insertCashOrder(OrderTotal.toString(), CashReceived.toString(), TipId);
                                    }

                                }
                                else {
                                    if (CashOrderId >=1 ){
                                        boolean deleted = pizzaDriverDB.deleteCashOrder(CashOrderId);
                                    }
                                }
                            }
                        }
                        Toast updateToast = Toast.makeText(getContext(), "Update Success", Toast.LENGTH_SHORT);
                        updateToast.show();
                        NavHostFragment.findNavController(AddOrderFragment.this)
                                .navigate(R.id.action_addOrderFragment_to_OrderListFragment, savedInstanceState);
                    }
                    else {
                        Toast updateToast = Toast.makeText(getContext(), "Unable to update data", Toast.LENGTH_LONG);
                        updateToast.show();
                    }

                }
                else {
                    long insert_result_order = pizzaDriverDB.insertOrder(workingDate, orderNumber, LocationID);
                    Log.d(TAG, "insert order: " + insert_result_order);
                    boolean data_inserted = true;
                    if (insert_result_order != -1) {
                        long insert_result_tip = pizzaDriverDB.insertTip(Tip.toString(), orderType, TipCashBool, insert_result_order);
                        Log.d(TAG, "insert tip: " + insert_result_tip);
                        if (insert_result_tip != -1){
                            if (orderType.equals("Cash")){
                                long insert_result_cash = pizzaDriverDB.insertCashOrder(OrderTotal.toString(), CashReceived.toString(), insert_result_tip);
                                Log.d(TAG, "insert cash: " + insert_result_cash);
                                if (insert_result_cash != -1){
                                    //nothing all good
                                }
                                else {
                                    data_inserted = false;
                                }
                            }
                            //nothing

                        }
                        else {
                            data_inserted = false;
                        }

                        NavHostFragment.findNavController(AddOrderFragment.this)
                                .navigate(R.id.action_addOrderFragment_to_OrderListFragment, savedInstanceState);

                    } else {
                        data_inserted = false;
                    }
                    if (data_inserted){
                        Toast toast = Toast.makeText(getContext(), "Success", Toast.LENGTH_SHORT);
                        toast.show();
                    }
                    else {
                        Toast toast = Toast.makeText(getContext(), "Unable to insert data", Toast.LENGTH_LONG);
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
                Log.d("CHANGED", "beforeTextChanged: " + s.toString().length());
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                int l = s.toString().length();
                int diff;
                diff = l - start;
                Log.d("CHANGED", "onTextChanged: " + s.toString());
                Log.d("CHANGED", "onTextChanged: " + start);
                Log.d("CHANGED", "onTextChanged: " + before);
                Log.d("CHANGED", "onTextChanged: " + count);
                Log.d("CHANGED", "onTextChanged: " + l);
                Log.d("CHANGED","OnTextChanged: " + diff);
                Pattern p = Pattern.compile("^\\.\\d*$");
                Matcher m = p.matcher(s.toString());
                boolean b = m.matches();
                if (b){
                    Log.d("MATCHED", "MATCHED: " + true);
                    s = "0" + s;
                    Log.d("MATCHED", "MATCHED: " + s);
                    r = new BigDecimal(s.toString());
                }
                else {
                    r = new BigDecimal(s.toString());
                    if (before == 0) {
                        if (diff < 4) {
                            r = r.multiply(BigDecimal.valueOf(10));
                        }
                        r = r.setScale(2);
                        Log.d("CHANGED", "onTextChangedHERE: " + r);
                    } else {
                        if (diff < 2) {
                            r = r.divide(BigDecimal.valueOf(10), 2, BigDecimal.ROUND_UNNECESSARY);
                        }
                        else if (diff == 2) {
                            StringBuilder sb = new StringBuilder(s);
                            sb.deleteCharAt(start - 1);
                            r = new BigDecimal(sb.toString());
                            r = r.divide(BigDecimal.valueOf(100), 2, BigDecimal.ROUND_UNNECESSARY);
                        }
                        else {
                            r = r.setScale(2);
                        }
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
                int l = s.toString().length();
                int diff;
                diff = l - start;
                Log.d("CHANGED", "onTextChanged: " + s.toString());
                Log.d("CHANGED", "onTextChanged: " + start);
                Log.d("CHANGED", "onTextChanged: " + before);
                Log.d("CHANGED", "onTextChanged: " + count);
                Log.d("CHANGED", "onTextChanged: " + l);
                Log.d("CHANGED","OnTextChanged: " + diff);
                Pattern p = Pattern.compile("^\\.\\d*$");
                Matcher m = p.matcher(s.toString());
                boolean b = m.matches();
                if (b){
                    Log.d("MATCHED", "MATCHED: " + true);
                    s = "0" + s;
                    Log.d("MATCHED", "MATCHED: " + s);
                    r = new BigDecimal(s.toString());
                }
                else {
                    r = new BigDecimal(s.toString());
                    if (before == 0) {
                        if (diff < 4) {
                            r = r.multiply(BigDecimal.valueOf(10));
                        }
                        r = r.setScale(2);
                        Log.d("CHANGED", "onTextChangedHERE: " + r);
                    } else {
                        if (diff < 2) {
                            r = r.divide(BigDecimal.valueOf(10), 2, BigDecimal.ROUND_UNNECESSARY);
                        }
                        else if (diff == 2) {
                            StringBuilder sb = new StringBuilder(s);
                            sb.deleteCharAt(start - 1);
                            r = new BigDecimal(sb.toString());
                            r = r.divide(BigDecimal.valueOf(100), 2, BigDecimal.ROUND_UNNECESSARY);
                        }
                        else {
                            r = r.setScale(2);
                        }
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
                int l = s.toString().length();
                int diff;
                diff = l - start;
                Log.d("CHANGED", "onTextChanged: " + s.toString());
                Log.d("CHANGED", "onTextChanged: " + start);
                Log.d("CHANGED", "onTextChanged: " + before);
                Log.d("CHANGED", "onTextChanged: " + count);
                Log.d("CHANGED", "onTextChanged: " + l);
                Log.d("CHANGED","OnTextChanged: " + diff);
                Pattern p = Pattern.compile("^\\.\\d*$");
                Matcher m = p.matcher(s.toString());
                boolean b = m.matches();
                if (b){
                    Log.d("MATCHED", "MATCHED: " + true);
                    s = "0" + s;
                    Log.d("MATCHED", "MATCHED: " + s);
                    r = new BigDecimal(s.toString());
                }
                else {
                    r = new BigDecimal(s.toString());
                    if (before == 0) {
                        if (diff < 4) {
                            r = r.multiply(BigDecimal.valueOf(10));
                        }
                        r = r.setScale(2);
                        Log.d("CHANGED", "onTextChangedHERE: " + r);
                    } else {
                        if (diff < 2) {
                            r = r.divide(BigDecimal.valueOf(10), 2, BigDecimal.ROUND_UNNECESSARY);
                        }
                        else if (diff == 2) {
                            StringBuilder sb = new StringBuilder(s);
                            sb.deleteCharAt(start - 1);
                            r = new BigDecimal(sb.toString());
                            r = r.divide(BigDecimal.valueOf(100), 2, BigDecimal.ROUND_UNNECESSARY);
                        }
                        else {
                            r = r.setScale(2);
                        }
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


    private void showPopup(View view, int OrderNumber, int OrderId, Bundle savedInstanceState) {
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
                //TODO
                Log.d(TAG, "showPopup: " + workingDate);
                boolean changed = pizzaDriverDB.updateOrderNumber(workingDate, NewOrderNumber, OrderId);

                if (changed){
                    Toast updateToast = Toast.makeText(view.getContext(), "Updated order number " + OrderNumber + " to " + NewOrderNumber, Toast.LENGTH_SHORT);
                    updateToast.show();
                    NavHostFragment.findNavController(AddOrderFragment.this)
                            .navigate(R.id.action_addOrderFragment_to_OrderListFragment, savedInstanceState);
                }
                else {
                    Toast updateToast = Toast.makeText(view.getContext(), "Unable to update order number " + OrderNumber + " to " + NewOrderNumber + " please check...", Toast.LENGTH_LONG);
                    updateToast.show();
                    dialog.cancel();
                }

            });
            builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());

            builder.show();

            return true;
        });
        MenuItem delete = popup.getMenu().findItem(R.id.order_delete);
        delete.setOnMenuItemClickListener(v ->{
            //TODO
            Log.d(TAG, "showPopup: deleted");
            ArrayList<Integer> tipsInOrder;
            tipsInOrder = pizzaDriverDB.getAllTipsPerOrderId(OrderId);
            ArrayList<Integer> cashOrders = new ArrayList<>();
            for (final Integer tipId: tipsInOrder ){
                ArrayList<Integer> ids =  pizzaDriverDB.getAllCashOrdersPerTipId(tipId);
                cashOrders.addAll(ids);
            }
            // Now Delete all information pertaining to the OrderNumber
            if (cashOrders.size() > 0){
                for (final Integer cashOrderId: cashOrders ){
                    boolean deleted = pizzaDriverDB.deleteCashOrder(cashOrderId);
                }
            }
            if (tipsInOrder.size() > 0){
                for (final Integer tipId: tipsInOrder ){
                    boolean deleted = pizzaDriverDB.deleteTip(tipId);
                }
            }
            boolean deleted = pizzaDriverDB.deleteOrder(OrderId);

            if (deleted) {
                Toast deletedToast = Toast.makeText(view.getContext(), "Deleted Order Number " + OrderNumber, Toast.LENGTH_SHORT);
                deletedToast.show();
                NavHostFragment.findNavController(AddOrderFragment.this)
                        .navigate(R.id.action_addOrderFragment_to_OrderListFragment, savedInstanceState);
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