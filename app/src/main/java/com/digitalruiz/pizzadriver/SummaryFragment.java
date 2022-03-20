package com.digitalruiz.pizzadriver;

import android.content.Intent;
import android.database.Cursor;
import android.icu.math.BigDecimal;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

public class SummaryFragment extends Fragment {

    SQLiteDBHelper pizzaDriverDB;
    String workingDate;

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

        long BusinessDayId = pizzaDriverDB.getActiveBusinessDay();
        if (BusinessDayId > 0){
            workingDate = pizzaDriverDB.getBusinessDayById(BusinessDayId);
        }
        else {
            Date date = Calendar.getInstance().getTime();
            DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            workingDate = formatter.format(date);
            BusinessDayId = pizzaDriverDB.getBusinessDay(workingDate);
            if (BusinessDayId > 0){

            }
            else {
                BusinessDayId = pizzaDriverDB.insertDate(workingDate);
                pizzaDriverDB.insertActiveBusinessDay(BusinessDayId);
            }
        }

        TextView TipsCreditText = view.findViewById(R.id.tipsCredit);
        TextView TipsCashText = view.findViewById(R.id.tipsCash);
        TextView TipsTotalText = view.findViewById(R.id.tipsTotal);
        TextView TracyTotalText = view.findViewById(R.id.tracyTotal);
        TextView MountainHouseText = view.findViewById(R.id.mountainHouseTotal);
        TextView ReimbursementTotalText = view.findViewById(R.id.reimbursementTotal);
        TextView CompensationTotalText = view.findViewById(R.id.compensationTotal);
        TextView CashOrdersTotalText = view.findViewById(R.id.cashOrdersTotal);
        TextView netCashText = view.findViewById(R.id.netCash);
        TextView ordersCreditAutoText = view.findViewById(R.id.creditAuto);
        TextView ordersCreditManualText = view.findViewById(R.id.creditManual);
        TextView ordersCreditManualCashText = view.findViewById(R.id.creditManualCash);
        TextView ordersCashText = view.findViewById(R.id.cash);
        TextView ordersGrubhubText = view.findViewById(R.id.grubhub);
        TextView ordersLevelUpText = view.findViewById(R.id.levelup);
        TextView ordersOtherText = view.findViewById(R.id.other);
        TextView ordersTotalText = view.findViewById(R.id.total);

        TextView creditAutoStatic = view.findViewById(R.id.creditAutoStatic);
        TextView creditManualStatic = view.findViewById(R.id.creditManualStatic);
        TextView creditManualCashStatic = view.findViewById(R.id.creditManualCashStatic);
        TextView cashStatic = view.findViewById(R.id.cashStatic);
        TextView grubhubStatic = view.findViewById(R.id.grubhubStatic);
        TextView levelUpStatic = view.findViewById(R.id.levelUpStatic);
        TextView otherStatic = view.findViewById(R.id.otherStatic);

        TextView tracy = view.findViewById(R.id.tracy);
        TextView mountainHouse = view.findViewById(R.id.mountainHouse);


        pizzaDriverDB = new SQLiteDBHelper(getContext());

        BigDecimal CreditTotal;
        BigDecimal CashTotal = new BigDecimal("0.00");
        BigDecimal TipsTotal;
        BigDecimal ReimbursementTotal;
        BigDecimal TracyTotal;
        BigDecimal MountainHouseTotal;
        BigDecimal CompensationTotal;
        BigDecimal CashOrdersTotal = new BigDecimal("0.00");
        BigDecimal NetCash;
        int OrdersCreditAuto;
        int OrdersCreditManual;
        int OrdersCreditManualCash;
        int OrdersCash;
        int OrdersGrubhub;
        int OrdersLevelUp;
        int OrdersOther;
        int OrdersTotal;
        ArrayList<Integer> orders_ids;
        orders_ids = pizzaDriverDB.getAllOrders(workingDate);
        ArrayList<Integer> allTips = pizzaDriverDB.getAllTips(orders_ids);

        ArrayList<Integer> tipsCredit = pizzaDriverDB.getAllCredit(allTips);
        Log.d("TEST", "onViewCreated: " + tipsCredit);
        CreditTotal = new BigDecimal("0.00");
        for (final int tipId: tipsCredit ){
            Log.d("TEST", "onViewCreated: " + tipId);
            Cursor tip_result = pizzaDriverDB.getTipDataByTipId(tipId);
            Log.d("TEST", "onViewCreated: " + tip_result.getCount());
            tip_result.moveToFirst();
            BigDecimal TipCredit = new BigDecimal(tip_result.getString(tip_result.getColumnIndex("Amount")));
            CreditTotal = CreditTotal.add(TipCredit);
        }
        TipsCreditText.setText(CreditTotal.toString());


        ArrayList<Integer> tipsCash = pizzaDriverDB.getAllCash(allTips);
        Log.d("TEST", "onViewCreated: " + tipsCash);
        for (final int tipId: tipsCash ){
            Cursor result = pizzaDriverDB.getTipDataByTipId(tipId);
            result.moveToFirst();
            BigDecimal TipCash = new BigDecimal(result.getString(result.getColumnIndex("Amount")));
            CashTotal = CashTotal.add(TipCash);
        }
        TipsCashText.setText(CashTotal.toString());

        TipsTotal = CreditTotal.add(CashTotal);

        TipsTotalText.setText(TipsTotal.toString());

        //Tracy
        ArrayList<Integer> ordersTracy = pizzaDriverDB.getAllOrdersPerLocationId(orders_ids, "1");
        Cursor tracy_location_result = pizzaDriverDB.getLocationData(1);
        tracy_location_result.moveToFirst();
        String rateString = tracy_location_result.getString(tracy_location_result.getColumnIndex("Rate"));
        BigDecimal Rate = new BigDecimal(rateString);
        BigDecimal tracyCount = new BigDecimal(ordersTracy.size());
        Log.d("TEST", "Count: " + tracyCount);
        TracyTotal = tracyCount.multiply(Rate);
        TracyTotalText.setText(TracyTotal.toString());

        //Mountain House
        ArrayList<Integer> ordersMH = pizzaDriverDB.getAllOrdersPerLocationId(orders_ids, "2");
        Cursor mh_location_result = pizzaDriverDB.getLocationData(2);
        mh_location_result.moveToFirst();
        rateString = mh_location_result.getString(mh_location_result.getColumnIndex("Rate"));
        Rate = new BigDecimal(rateString);
        BigDecimal mhCount = new BigDecimal(ordersMH.size());
        MountainHouseTotal = mhCount.multiply(Rate);
        MountainHouseText.setText(MountainHouseTotal.toString());

        ReimbursementTotal = TracyTotal.add(MountainHouseTotal);
        ReimbursementTotalText.setText(ReimbursementTotal.toString());

        CompensationTotal = TipsTotal.add(ReimbursementTotal);
        CompensationTotalText.setText(CompensationTotal.toString());

        ArrayList<Integer> cashOrdersIds = pizzaDriverDB.getAllCashOrders(tipsCash);
        for (final int cashOrderId: cashOrdersIds){
            Log.d("TEST", "cashOrderId: " + cashOrderId);
            Cursor cash_order_result = pizzaDriverDB.getCashOrderDataByCashOrderId(cashOrderId);
            cash_order_result.moveToFirst();
            BigDecimal OrderTotal = new BigDecimal(cash_order_result.getString(cash_order_result.getColumnIndex("Total")));
            CashOrdersTotal = CashOrdersTotal.add(OrderTotal);
        }

        CashOrdersTotalText.setText(CashOrdersTotal.toString());

        NetCash = (CreditTotal.add(ReimbursementTotal)).subtract(CashOrdersTotal);
        netCashText.setText(NetCash.toString());

        Cursor creditAuto = pizzaDriverDB.getTipDataPerTypeAndCashBool(allTips,"Credit Auto", "0");
        OrdersCreditAuto = creditAuto.getCount();
        Cursor creditManual = pizzaDriverDB.getTipDataPerTypeAndCashBool(allTips, "Credit Manual", "0");
        OrdersCreditManual = creditManual.getCount();
        Cursor creditManualCash = pizzaDriverDB.getTipDataPerTypeAndCashBool(allTips, "Credit Manual", "1");
        OrdersCreditManualCash = creditManualCash.getCount();
        Cursor cash = pizzaDriverDB.getTipDataPerTypeAndCashBool(allTips, "Cash", "1");
        OrdersCash = cash.getCount();
        Cursor grubHub = pizzaDriverDB.getTipDataPerTypeAndCashBool(allTips, "Grubhub", "*");
        OrdersGrubhub = grubHub.getCount();
        Cursor levelUp = pizzaDriverDB.getTipDataPerTypeAndCashBool(allTips, "LevelUp", "*");
        OrdersLevelUp = levelUp.getCount();
        Cursor other = pizzaDriverDB.getTipDataPerTypeAndCashBool(allTips, "Grubhub", "*");
        OrdersOther = other.getCount();
        OrdersTotal = allTips.size();




        ordersCreditAutoText.setText(Integer.toString(OrdersCreditAuto));
        ordersCreditManualText.setText(Integer.toString(OrdersCreditManual));
        ordersCreditManualCashText.setText(Integer.toString(OrdersCreditManualCash));
        ordersCashText.setText(Integer.toString(OrdersCash));
        ordersGrubhubText.setText((Integer.toString(OrdersGrubhub)));
        ordersLevelUpText.setText(Integer.toString(OrdersLevelUp));
        ordersOtherText.setText(Integer.toString(OrdersOther));
        ordersTotalText.setText(Integer.toString(OrdersTotal));

        final Bundle bundle = new Bundle();
        view.findViewById(R.id.buttonBackToOrders).setOnClickListener(view1 -> {
            bundle.putString("Type", "*");
            bundle.putString("CashBool", "*");
            bundle.putString("LocationId", "*");
            NavHostFragment.findNavController(SummaryFragment.this)
                    .navigate(R.id.action_SummaryFragment_to_OrderListFragment, bundle);
        });

        creditAutoStatic.setOnClickListener(v -> {
            bundle.putString("Type", "Credit Auto");
            bundle.putString("CashBool", "0");
            bundle.putString("LocationId", "*");
            NavHostFragment.findNavController(SummaryFragment.this)
                    .navigate(R.id.action_SummaryFragment_to_OrderListFragment, bundle);
        });

        creditManualStatic.setOnClickListener(v -> {
            bundle.putString("Type", "Credit Manual");
            bundle.putString("CashBool", "0");
            bundle.putString("LocationId", "*");
            NavHostFragment.findNavController(SummaryFragment.this)
                    .navigate(R.id.action_SummaryFragment_to_OrderListFragment, bundle);
        });

        creditManualCashStatic.setOnClickListener(v -> {
            bundle.putString("Type", "Credit Manual");
            bundle.putString("CashBool", "1");
            bundle.putString("LocationId", "*");
            NavHostFragment.findNavController(SummaryFragment.this)
                    .navigate(R.id.action_SummaryFragment_to_OrderListFragment, bundle);
        });

        cashStatic.setOnClickListener(v -> {
            bundle.putString("Type", "Cash");
            bundle.putString("CashBool", "1");
            bundle.putString("LocationId", "*");
            NavHostFragment.findNavController(SummaryFragment.this)
                    .navigate(R.id.action_SummaryFragment_to_OrderListFragment, bundle);
        });

        grubhubStatic.setOnClickListener(v -> {
            bundle.putString("Type", "Grubhub");
            bundle.putString("CashBool", "*");
            bundle.putString("LocationId", "*");
            NavHostFragment.findNavController(SummaryFragment.this)
                    .navigate(R.id.action_SummaryFragment_to_OrderListFragment, bundle);
        });

        levelUpStatic.setOnClickListener(v -> {
            bundle.putString("Type", "LevelUp");
            bundle.putString("CashBool", "*");
            bundle.putString("LocationId", "*");
            NavHostFragment.findNavController(SummaryFragment.this)
                    .navigate(R.id.action_SummaryFragment_to_OrderListFragment, bundle);
        });

        otherStatic.setOnClickListener(v -> {
            bundle.putString("Type", "Other");
            bundle.putString("CashBool", "*");
            bundle.putString("LocationId", "*");
            NavHostFragment.findNavController(SummaryFragment.this)
                    .navigate(R.id.action_SummaryFragment_to_OrderListFragment, bundle);
        });

        tracy.setOnClickListener(v -> {
            bundle.putString("Type", "*");
            bundle.putString("CashBool", "*");
            bundle.putString("LocationId", "1");
            NavHostFragment.findNavController(SummaryFragment.this)
                    .navigate(R.id.action_SummaryFragment_to_OrderListFragment, bundle);
        });

        mountainHouse.setOnClickListener(v -> {
            bundle.putString("Type", "*");
            bundle.putString("CashBool", "*");
            bundle.putString("LocationId", "2");
            NavHostFragment.findNavController(SummaryFragment.this)
                    .navigate(R.id.action_SummaryFragment_to_OrderListFragment, bundle);
        });

    }
}
