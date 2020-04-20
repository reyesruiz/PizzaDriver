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


        LinearLayout WrapperLinerLayout = (LinearLayout)view.findViewById(R.id.wrapperLinerLayout);

        LinearLayout WrapperHorizontalLinerLayoutHeadLine = new LinearLayout(getContext());
        WrapperHorizontalLinerLayoutHeadLine.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));


        TextView OrderNumberStatic = new TextView(getContext());
        OrderNumberStatic.setText("Order Number");
        OrderNumberStatic.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));

        TextView OrderTypeStatic = new TextView(getContext());
        OrderTypeStatic.setText("Order Type");
        OrderTypeStatic.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));

        TextView TipTextStatic = new TextView(getContext());
        TipTextStatic.setText("Tip");
        TipTextStatic.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));

        WrapperHorizontalLinerLayoutHeadLine.addView(OrderNumberStatic);
        WrapperHorizontalLinerLayoutHeadLine.addView(OrderTypeStatic);
        WrapperHorizontalLinerLayoutHeadLine.addView(TipTextStatic);

        WrapperLinerLayout.addView(WrapperHorizontalLinerLayoutHeadLine);

        for (Integer orderNumber: orders ){
            Log.v("Test", "Order number is " + orderNumber);

            Cursor result = pizzaDriverDB.getData(orderNumber);
            result.moveToFirst();
            String OrderType = result.getString(result.getColumnIndex("OrderType"));
            String Tip = result.getString(result.getColumnIndex("Tip"));
            Log.v("Test", "cursor " + result);

            LinearLayout WrapperHorizontalLinerLayout = new LinearLayout(getContext());
            WrapperHorizontalLinerLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));

            Chip orderNumberChip = new Chip(getContext());
            orderNumberChip.setText(orderNumber.toString());
            orderNumberChip.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));


            TextView OrderTypeText = new TextView(getContext());
            OrderTypeText.setText(OrderType);
            OrderTypeText.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));

            TextView TipText = new TextView(getContext());
            TipText.setText(Tip);
            TipText.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));

            WrapperHorizontalLinerLayout.addView(orderNumberChip);
            WrapperHorizontalLinerLayout.addView(OrderTypeText);
            WrapperHorizontalLinerLayout.addView(TipText);

            WrapperLinerLayout.addView(WrapperHorizontalLinerLayout);
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
