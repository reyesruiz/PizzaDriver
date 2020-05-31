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

        TextView TipsCreditText = view.findViewById(R.id.tipsCredit);
        TextView TipsCashText = view.findViewById(R.id.tipsCash);
        TextView TipsTotalText = view.findViewById(R.id.tipsTotal);
        TextView TracyTotalText = view.findViewById(R.id.tracyTotal);
        TextView MountainHouseText = view.findViewById(R.id.mountainHouseTotal);
        TextView ReimbursementTotalText = view.findViewById(R.id.reimbursementTotal);
        TextView CompensationTotalText = view.findViewById(R.id.compensationTotal);
        TextView CashOrdersTotalText = view.findViewById(R.id.cashOrdersTotal);
        TextView netCashText = view.findViewById(R.id.netCash);
        TextView ordersCreditAutoText = view.findViewById(R.id.CreditAuto);
        TextView ordersCreditManualText = view.findViewById(R.id.creditManual);
        TextView ordersCreditManualCashText = view.findViewById(R.id.creditManualCash);
        TextView ordersCashText = view.findViewById(R.id.Cash);
        TextView ordersGrubhubText = view.findViewById(R.id.grubhub);
        TextView ordersOtherText = view.findViewById(R.id.other);
        TextView ordersTotalText = view.findViewById(R.id.Total);

        TextView creditAutoStatic = view.findViewById(R.id.creditAutoStatic);
        TextView creditManualStatic = view.findViewById(R.id.creditManualStatic);
        TextView creditManualCashStatic = view.findViewById(R.id.creditManualCashStatic);
        TextView cashStatic = view.findViewById(R.id.cashStatic);
        TextView grubhubStatic = view.findViewById(R.id.grubhubStatic);
        TextView otherStatic = view.findViewById(R.id.otherStatic);

        TextView tracy = view.findViewById(R.id.tracy);
        TextView mountainHouse = view.findViewById(R.id.mountainHouse);


        pizzaDriverDB = new SQLiteDBHelper(getContext());

        BigDecimal CreditTotal;
        BigDecimal CashTotal = new BigDecimal("0.00");
        BigDecimal TipsTotal;
        BigDecimal ReimbursementTotal;
        BigDecimal TracyTotal = new BigDecimal("0.00");
        BigDecimal MountainHouseTotal = new BigDecimal("0.00");
        BigDecimal CompensationTotal;
        BigDecimal CashOrdersTotal = new BigDecimal("0.00");
        BigDecimal NetCash;
        int OrdersCreditAuto;
        int OrdersCreditManual;
        int OrdersCreditManualCash;
        int OrdersCash;
        int OrdersGrubhub;
        int OrdersOther;
        int OrdersTotal;
        int OrdersTotalFromDB;

        ArrayList<Integer> ordersCredit = pizzaDriverDB.getAllCredit();
        CreditTotal = new BigDecimal("0.00");
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

        ReimbursementTotal = TracyTotal.add(MountainHouseTotal);
        ReimbursementTotalText.setText(ReimbursementTotal.toString());

        CompensationTotal = TipsTotal.add(ReimbursementTotal);
        CompensationTotalText.setText(CompensationTotal.toString());

        ArrayList<Integer> cashOrders = pizzaDriverDB.getAllCashOrders();
        for (final Integer orderNumber: cashOrders ){
            Cursor result = pizzaDriverDB.getData(orderNumber);
            result.moveToFirst();
            BigDecimal OrderTotal = new BigDecimal(result.getString(result.getColumnIndex("OrderTotal")));
            CashOrdersTotal = CashOrdersTotal.add(OrderTotal);
        }

        CashOrdersTotalText.setText(CashOrdersTotal.toString());

        NetCash = (CreditTotal.add(ReimbursementTotal)).subtract(CashOrdersTotal);
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

        final Bundle bundle = new Bundle();
        view.findViewById(R.id.buttonBackToOrders).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bundle.putString("OrderType", "*");
                bundle.putString("CashBool", "*");
                bundle.putString("Location", "*");
                NavHostFragment.findNavController(SummaryFragment.this)
                        .navigate(R.id.action_SummaryFragment_to_OrderListFragment, bundle);
            }
        });

        creditAutoStatic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bundle.putString("OrderType", "Credit Auto");
                bundle.putString("CashBool", "0");
                bundle.putString("Location", "*");
                NavHostFragment.findNavController(SummaryFragment.this)
                        .navigate(R.id.action_SummaryFragment_to_OrderListFragment, bundle);
            }
        });

        creditManualStatic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bundle.putString("OrderType", "Credit Manual");
                bundle.putString("CashBool", "0");
                bundle.putString("Location", "*");
                NavHostFragment.findNavController(SummaryFragment.this)
                        .navigate(R.id.action_SummaryFragment_to_OrderListFragment, bundle);
            }
        });

        creditManualCashStatic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bundle.putString("OrderType", "Credit Manual");
                bundle.putString("CashBool", "1");
                bundle.putString("Location", "*");
                NavHostFragment.findNavController(SummaryFragment.this)
                        .navigate(R.id.action_SummaryFragment_to_OrderListFragment, bundle);
            }
        });

        cashStatic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bundle.putString("OrderType", "Cash");
                bundle.putString("CashBool", "1");
                bundle.putString("Location", "*");
                NavHostFragment.findNavController(SummaryFragment.this)
                        .navigate(R.id.action_SummaryFragment_to_OrderListFragment, bundle);
            }
        });

        grubhubStatic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bundle.putString("OrderType", "Grubhub");
                bundle.putString("CashBool", "*");
                bundle.putString("Location", "*");
                NavHostFragment.findNavController(SummaryFragment.this)
                        .navigate(R.id.action_SummaryFragment_to_OrderListFragment, bundle);
            }
        });

        otherStatic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bundle.putString("OrderType", "Other");
                bundle.putString("CashBool", "*");
                bundle.putString("Location", "*");
                NavHostFragment.findNavController(SummaryFragment.this)
                        .navigate(R.id.action_SummaryFragment_to_OrderListFragment, bundle);
            }
        });

        tracy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bundle.putString("OrderType", "*");
                bundle.putString("CashBool", "*");
                bundle.putString("Location", "Tracy");
                NavHostFragment.findNavController(SummaryFragment.this)
                        .navigate(R.id.action_SummaryFragment_to_OrderListFragment, bundle);
            }
        });

        mountainHouse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bundle.putString("OrderType", "*");
                bundle.putString("CashBool", "*");
                bundle.putString("Location", "Mountain House");
                NavHostFragment.findNavController(SummaryFragment.this)
                        .navigate(R.id.action_SummaryFragment_to_OrderListFragment, bundle);
            }
        });

    }
}
