package com.digitalruiz.pizzadriver;

import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

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
        TextView ReimbursementTotalText = (TextView) view.findViewById(R.id.reinbursmentTotal);
        TextView CompensationTotalText = (TextView) view.findViewById(R.id.compensationTotal);
        TextView CashOrdersTotalText = (TextView) view.findViewById(R.id.cashOrdersTotal);
        TextView netCashText = (TextView) view.findViewById(R.id.netCash);
        TextView ordersCreditAutoText = (TextView) view.findViewById(R.id.CreditAuto);
        TextView ordersCreditManualText = (TextView) view.findViewById(R.id.creditManual);
        TextView ordersCreditManualCashText = (TextView) view.findViewById(R.id.creditManualCash);
        TextView ordersCashText = (TextView) view.findViewById(R.id.Cash);
        TextView ordersGrubhubText = (TextView) view.findViewById(R.id.grubhub);
        TextView ordersOtherText = (TextView) view.findViewById(R.id.other);
        TextView ordersTotalText = (TextView) view.findViewById(R.id.Total);

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
        int OrdersCreditAuto = 0;
        int OrdersCreditManual = 0;
        int OrdersCreditManualCash = 0;
        int OrdersCash = 0;
        int OrdersGrubhub = 0;
        int OrdersOther = 0;
        int OrdersTotal = 0;
        int OrdersTotalFromDB = 0;

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
        ReimbursementTotalText.setText(Double.toString(ReinbursmentTotal));

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

        OrdersCreditAuto = pizzaDriverDB.numberOfRowsPerType("Credit Auto", 0);
        OrdersCreditManual = pizzaDriverDB.numberOfRowsPerType("Credit Manual", 0);
        OrdersCreditManualCash = pizzaDriverDB.numberOfRowsPerType("Credit Manual", 1);
        OrdersCash = pizzaDriverDB.numberOfRowsPerType("Cash", 1);
        OrdersGrubhub = pizzaDriverDB.numberOfRowsPerType("Grubhub", 0);
        OrdersOther = pizzaDriverDB.numberOfRowsPerType("Other", 0);
        OrdersTotal = OrdersCreditAuto + OrdersCreditManual + OrdersCreditManualCash + OrdersCash + OrdersGrubhub + OrdersOther;
        OrdersTotalFromDB = pizzaDriverDB.numberOfRows();

        if (OrdersTotal != OrdersTotalFromDB){
            Toast toast = Toast.makeText(getContext(), "Number of Orders does not match the number of Records in DB", Toast.LENGTH_LONG);
            toast.show();
        }


        ordersCreditAutoText.setText(Integer.toString(OrdersCreditAuto));
        ordersCreditManualText.setText(Integer.toString(OrdersCreditManual));
        ordersCreditManualCashText.setText(Integer.toString(OrdersCreditManualCash));
        ordersCashText.setText(Integer.toString(OrdersCash));
        ordersGrubhubText.setText((Integer.toString(OrdersGrubhub)));
        ordersOtherText.setText(Integer.toString(OrdersOther));
        ordersTotalText.setText(Integer.toString(OrdersTotal));


        view.findViewById(R.id.buttonBackToOrders).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(SummaryFragment.this)
                        .navigate(R.id.action_SummaryFragment_to_OrderListFragment);
            }
        });
    }
}
