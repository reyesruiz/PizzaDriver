package com.digitalruiz.pizzadriver;

import android.app.ActionBar;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.google.android.material.chip.Chip;

import java.util.ArrayList;

public class FirstFragment extends Fragment {

    SQLiteDBHelper pizzaDriverDB;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState

    )
    {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_first, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        pizzaDriverDB = new SQLiteDBHelper(getContext());


        ArrayList<Integer> orders = pizzaDriverDB.getAllOrders();
        Log.v("Test", "Array is " + orders);


        TableLayout WrapperTable = (TableLayout) view.findViewById(R.id.wrapperTableLayout);

        TableRow HeadLine = new TableRow(getContext());
        HeadLine.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT));

        TextView OrderNumberStatic = new TextView(getContext());
        OrderNumberStatic.setText("Order Number");
        OrderNumberStatic.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));

        TextView OrderTypeStatic = new TextView(getContext());
        OrderTypeStatic.setText("Order Type");
        OrderTypeStatic.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));

        TextView TipTextStatic = new TextView(getContext());
        TipTextStatic.setText("Tip");
        TipTextStatic.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));

        HeadLine.addView(OrderNumberStatic);
        HeadLine.addView(OrderTypeStatic);
        HeadLine.addView(TipTextStatic);

        WrapperTable.addView(HeadLine);

        for (Integer orderNumber: orders ){
            Log.v("Test", "Order number is " + orderNumber);

            Cursor result = pizzaDriverDB.getData(orderNumber);
            result.moveToFirst();
            String OrderType = result.getString(result.getColumnIndex("OrderType"));
            String Tip = result.getString(result.getColumnIndex("Tip"));
            Log.v("Test", "cursor " + result);

            TableRow Row = new TableRow(getContext());
            Row.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT));

            Chip orderNumberChip = new Chip(getContext());
            orderNumberChip.setText(orderNumber.toString());
            orderNumberChip.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));


            TextView OrderTypeText = new TextView(getContext());
            OrderTypeText.setText(OrderType);
            OrderTypeText.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));

            TextView TipText = new TextView(getContext());
            TipText.setText(Tip);
            TipText.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));

            Row.addView(orderNumberChip);
            Row.addView(OrderTypeText);
            Row.addView(TipText);

            WrapperTable.addView(Row);
        }


     //   view.findViewById(R.id.button_first).setOnClickListener(new View.OnClickListener() {
       //     @Override
        //    public void onClick(View view) {
         //       NavHostFragment.findNavController(FirstFragment.this)
          //              .navigate(R.id.action_FirstFragment_to_SecondFragment);
           // }
        //});
    }
}
