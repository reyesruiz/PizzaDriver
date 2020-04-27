package com.digitalruiz.pizzadriver;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.google.android.material.chip.Chip;

import java.util.ArrayList;

public class OrderListFragment extends Fragment {

    SQLiteDBHelper pizzaDriverDB;


    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState

    )
    {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_order_list, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        pizzaDriverDB = new SQLiteDBHelper(getContext());
        Button button_first = (Button) view.findViewById(R.id.buttonSumary);


        ArrayList<Integer> orders = pizzaDriverDB.getAllOrders();
        Log.v("Test", "Array is " + orders);

        LinearLayout wrapperLayout = (LinearLayout) view.findViewById(R.id.wrapperLayout);
        TableLayout WrapperTable = (TableLayout) view.findViewById(R.id.wrapperTableLayout);

        TableRow HeadLine = new TableRow(getContext());
        HeadLine.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT));
        HeadLine.setBackgroundColor(Color.parseColor("#0079d6"));
        HeadLine.setPadding(5, 5, 5, 5);

        TextView OrderNumberStatic = new TextView(getContext());
        OrderNumberStatic.setText("Number");
        OrderNumberStatic.setTypeface(OrderNumberStatic.getTypeface(), Typeface.BOLD);
        OrderNumberStatic.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));

        TextView OrderTypeStatic = new TextView(getContext());
        OrderTypeStatic.setText("Type");
        OrderTypeStatic.setTypeface(OrderNumberStatic.getTypeface(), Typeface.BOLD);
        OrderTypeStatic.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));

        TextView CashStatic = new TextView(getContext());
        CashStatic.setText("Cash?");
        CashStatic.setTypeface(CashStatic.getTypeface(), Typeface.BOLD);
        CashStatic.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));

        TextView TipTextStatic = new TextView(getContext());
        TipTextStatic.setText("Tip");
        TipTextStatic.setTypeface(TipTextStatic.getTypeface(), Typeface.BOLD);
        TipTextStatic.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));

        TextView LocationStatic = new TextView(getContext());
        LocationStatic.setText("Location");
        LocationStatic.setTypeface(LocationStatic.getTypeface(), Typeface.BOLD);
        LocationStatic.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));

        HeadLine.addView(OrderNumberStatic);
        HeadLine.addView(OrderTypeStatic);
        HeadLine.addView(CashStatic);
        HeadLine.addView(TipTextStatic);
        HeadLine.addView(LocationStatic);

        WrapperTable.addView(HeadLine);
        int counter = 0;
        for (final Integer orderNumber: orders ){
            counter = counter + 1;
            Log.v("Test", "Order number is " + orderNumber);

            Cursor result = pizzaDriverDB.getData(orderNumber);
            result.moveToFirst();
            String OrderType = result.getString(result.getColumnIndex("OrderType"));
            String Tip = result.getString(result.getColumnIndex("Tip"));
            int Cash = Integer.parseInt(result.getString(result.getColumnIndex("TipCashBool")));
            String OrderLocation = result.getString(result.getColumnIndex("Location"));

            Log.v("Test", "cursor " + result);
            result.close();
            Log.v("Test", "Cash " + Cash);
            TableRow Row = new TableRow(getContext());
            Row.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT));
            Row.setPadding(5, 5, 5, 5);
            if (counter % 2 == 0){
                Row.setBackgroundColor(Color.parseColor("#0079d6"));
            }
            else {
                Row.setBackgroundColor(Color.parseColor("#00cccc"));
            }
            Chip orderNumberChip = new Chip(getContext());
            orderNumberChip.setText(orderNumber.toString());
            orderNumberChip.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
            orderNumberChip.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent addOrderIntent = new Intent(getActivity(), AddOrder.class);
                    addOrderIntent.putExtra("orderNumber", orderNumber);
                    startActivity(addOrderIntent);
                }
            });

            TextView OrderTypeText = new TextView(getContext());
            OrderTypeText.setText(OrderType);
            OrderTypeText.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));

            TextView CashText = new TextView((getContext()));
            if (Cash == 1){
                CashText.setText("Cash");
            }
            else {

            }
            CashText.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));

            TextView TipText = new TextView(getContext());
            TipText.setText(Tip);
            TipText.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));

            TextView LocationText = new TextView(getContext());
            LocationText.setText(OrderLocation);
            LocationText.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));


            Row.addView(orderNumberChip);
            Row.addView(OrderTypeText);
            Row.addView(CashText);
            Row.addView(TipText);
            Row.addView(LocationText);

            WrapperTable.addView(Row);
        }


        button_first.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(OrderListFragment.this)
                        .navigate(R.id.action_OrderListFragment_to_SummaryFragment);
            }
        });
    }
}
