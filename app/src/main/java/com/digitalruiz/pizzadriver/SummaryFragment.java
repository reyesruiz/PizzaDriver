package com.digitalruiz.pizzadriver;

import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import java.util.ArrayList;

public class SummaryFragment extends Fragment {

    SQLiteDBHelper pizzaDriverDB;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_summary, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextView TipsCreditText = (TextView) view.findViewById(R.id.tipsCredit);
        TextView TipsCashText = (TextView) view.findViewById(R.id.tipsCash);
        TextView TipsTotalText = (TextView) view.findViewById(R.id.tipsTotal);
        TextView TracyTotalText = (TextView) view.findViewById(R.id.tracyTotal);
        TextView MountainHouseText = (TextView) view.findViewById(R.id.mountainHouseTotal);
        TextView ReinbursmentTotalText = (TextView) view.findViewById(R.id.reinbursmentTotal);
        TextView CompensationTotalText = (TextView) view.findViewById(R.id.compensationTotal);
        TextView CashOrdersTotalText = (TextView) view.findViewById(R.id.cashOrdersTotal);
        TextView netCashText = (TextView) view.findViewById(R.id.netCash);

        pizzaDriverDB = new SQLiteDBHelper(getContext());

        double CreditTotal = 0;
        double CashTotal = 0;
        double TipsTotal = 0;
        double ReinbursmentTotal = 0;
        double TracyTotal = 0;
        double MountainHouseTotal = 0;
        double CompensationTotal = 0;
        double CashOrdersTotal = 0;
        double NetCash = 0;

        ArrayList<Integer> ordersCredit = pizzaDriverDB.getAllCredit();
        for (final Integer orderNumber: ordersCredit ){
            Cursor result = pizzaDriverDB.getData(orderNumber);
            result.moveToFirst();
            double TipCredit = Double.parseDouble(result.getString(result.getColumnIndex("Tip")));
            CreditTotal = CreditTotal + TipCredit;
        }
        TipsCreditText.setText(Double.toString(CreditTotal));

        ArrayList<Integer> ordersCash = pizzaDriverDB.getAllCash();
        for (final Integer orderNumber: ordersCash ){
            Cursor result = pizzaDriverDB.getData(orderNumber);
            result.moveToFirst();
            double TipCash = Double.parseDouble(result.getString(result.getColumnIndex("Tip")));
            CashTotal = CashTotal + TipCash;
        }
        TipsCashText.setText(Double.toString(CashTotal));

        TipsTotal = CreditTotal + CashTotal;

        TipsTotalText.setText(Double.toString(TipsTotal));


        ArrayList<Integer> ordersTracy = pizzaDriverDB.getAllLocation("Tracy");
        for (final Integer orderNumber: ordersTracy ){
            Cursor result = pizzaDriverDB.getData(orderNumber);
            result.moveToFirst();
            TracyTotal = TracyTotal + 1.50;
        }
        TracyTotalText.setText(Double.toString(TracyTotal));

        ArrayList<Integer> ordersMountainHouse = pizzaDriverDB.getAllLocation("Mountain House");
        for (final Integer orderNumber: ordersMountainHouse ){
            Cursor result = pizzaDriverDB.getData(orderNumber);
            result.moveToFirst();
            MountainHouseTotal = MountainHouseTotal + 2.50;
        }
        MountainHouseText.setText(Double.toString(MountainHouseTotal));

        ReinbursmentTotal = TracyTotal + MountainHouseTotal;
        ReinbursmentTotalText.setText(Double.toString(ReinbursmentTotal));

        CompensationTotal = TipsTotal + ReinbursmentTotal;
        CompensationTotalText.setText(Double.toString(CompensationTotal));

        ArrayList<Integer> cashOrders = pizzaDriverDB.getAllCashOrders();
        for (final Integer orderNumber: cashOrders ){
            Cursor result = pizzaDriverDB.getData(orderNumber);
            result.moveToFirst();
            double OrderTotal = Double.parseDouble(result.getString(result.getColumnIndex("OrderTotal")));
            CashOrdersTotal = CashOrdersTotal + OrderTotal;
        }

        CashOrdersTotalText.setText(Double.toString(CashOrdersTotal));

        NetCash = (CreditTotal + ReinbursmentTotal) - CashOrdersTotal;
        netCashText.setText(Double.toString(NetCash));




        view.findViewById(R.id.buttonBackToOrders).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(SummaryFragment.this)
                        .navigate(R.id.action_SummaryFragment_to_OrderListFragment);
            }
        });
    }
}
