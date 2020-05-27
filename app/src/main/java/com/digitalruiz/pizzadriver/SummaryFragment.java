package com.digitalruiz.pizzadriver;

import android.database.Cursor;
import android.icu.math.BigDecimal;
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

        BigDecimal CreditTotal = new BigDecimal("0.00");
        BigDecimal CashTotal = new BigDecimal("0.00");
        BigDecimal TipsTotal = new BigDecimal("0.00");
        BigDecimal ReinbursmentTotal = new BigDecimal("0.00");
        BigDecimal TracyTotal = new BigDecimal("0.00");
        BigDecimal MountainHouseTotal = new BigDecimal("0.00");
        BigDecimal CompensationTotal = new BigDecimal("0.00");
        BigDecimal CashOrdersTotal = new BigDecimal("0.00");
        BigDecimal NetCash = new BigDecimal("0.00");
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
            BigDecimal TipCredit = new BigDecimal(result.getString(result.getColumnIndex("Tip")));
            CreditTotal = CreditTotal.add(TipCredit);
        }
        TipsCreditText.setText(CreditTotal.toString());

        ArrayList<Integer> ordersCash = pizzaDriverDB.getAllCash();
        for (final Integer orderNumber: ordersCash ){
            Cursor result = pizzaDriverDB.getData(orderNumber);
            result.moveToFirst();
            BigDecimal TipCash = new BigDecimal(result.getString(result.getColumnIndex("Tip")));
            CashTotal = CashTotal.add(TipCash);
        }
        TipsCashText.setText(CashTotal.toString());

        TipsTotal = CreditTotal.add(CashTotal);

        TipsTotalText.setText(TipsTotal.toString());


        ArrayList<Integer> ordersTracy = pizzaDriverDB.getAllLocation("Tracy");
        for (final Integer orderNumber: ordersTracy ){
            Cursor result = pizzaDriverDB.getData(orderNumber);
            result.moveToFirst();
            BigDecimal Rate = new BigDecimal("1.50");
            TracyTotal =  TracyTotal.add(Rate);
        }
        TracyTotalText.setText(TracyTotal.toString());

        ArrayList<Integer> ordersMountainHouse = pizzaDriverDB.getAllLocation("Mountain House");
        for (final Integer orderNumber: ordersMountainHouse ){
            Cursor result = pizzaDriverDB.getData(orderNumber);
            result.moveToFirst();
            BigDecimal Rate = new BigDecimal("2.50");
            MountainHouseTotal = MountainHouseTotal.add(Rate);
        }
        MountainHouseText.setText(MountainHouseTotal.toString());

        ReinbursmentTotal = TracyTotal.add(MountainHouseTotal);
        ReimbursementTotalText.setText(ReinbursmentTotal.toString());

        CompensationTotal = TipsTotal.add(ReinbursmentTotal);
        CompensationTotalText.setText(CompensationTotal.toString());

        ArrayList<Integer> cashOrders = pizzaDriverDB.getAllCashOrders();
        for (final Integer orderNumber: cashOrders ){
            Cursor result = pizzaDriverDB.getData(orderNumber);
            result.moveToFirst();
            BigDecimal OrderTotal = new BigDecimal(result.getString(result.getColumnIndex("OrderTotal")));
            CashOrdersTotal = CashOrdersTotal.add(OrderTotal);
        }

        CashOrdersTotalText.setText(CashOrdersTotal.toString());

        NetCash = (CreditTotal.add(ReinbursmentTotal)).subtract(CashOrdersTotal);
        netCashText.setText(NetCash.toString());

        OrdersCreditAuto = pizzaDriverDB.numberOfRowsPerType("Credit Auto", "0");
        OrdersCreditManual = pizzaDriverDB.numberOfRowsPerType("Credit Manual", "0");
        OrdersCreditManualCash = pizzaDriverDB.numberOfRowsPerType("Credit Manual", "1");
        OrdersCash = pizzaDriverDB.numberOfRowsPerType("Cash", "1");
        OrdersGrubhub = pizzaDriverDB.numberOfRowsPerType("Grubhub", "*");
        OrdersOther = pizzaDriverDB.numberOfRowsPerType("Other", "*");
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
