package com.digitalruiz.pizzadriver;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.google.android.material.chip.Chip;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

public class OrderListFragment extends Fragment {

    SQLiteDBHelper pizzaDriverDB;
    String workingDate;


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

        Intent intent = getActivity().getIntent();
        if(intent != null){
            workingDate = intent.getStringExtra("SelectedDate");
            if (workingDate == null){
                Date date = Calendar.getInstance().getTime();
                DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                workingDate = formatter.format(date);
            }
        }
        else {
            Date date = Calendar.getInstance().getTime();
            DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            workingDate = formatter.format(date);
        }

        pizzaDriverDB = new SQLiteDBHelper(getContext());
        Button button_first = view.findViewById(R.id.buttonSummary);
        ArrayList<Integer> all_orders_ids;
        ArrayList<Integer> orders_ids;
        orders_ids = new ArrayList<>();

        all_orders_ids = pizzaDriverDB.getAllOrders(workingDate);
        ArrayList<Integer> allTips = pizzaDriverDB.getAllTips(all_orders_ids);

        if (getArguments() == null){
            //In case no arguments get passed
            orders_ids = all_orders_ids;
        }
        else {
            //Need to rework this

            if ((Objects.equals(requireArguments().getString("Type"), "*")) && (Objects.equals(requireArguments().getString("CashBool"), "*")) && (Objects.equals(requireArguments().getString("LocationId"), "*"))) {
                orders_ids = all_orders_ids;
            }
            else if (Objects.equals(requireArguments().getString("LocationId"), "*")){
                Log.d("TEST", "TYPE: " + getArguments().getString("Type"));
                Cursor tips_result = pizzaDriverDB.getTipDataPerTypeAndCashBool(allTips, getArguments().getString("Type"), getArguments().getString("CashBool"));
                while (tips_result.moveToNext()){
                    int order_id = tips_result.getInt(tips_result.getColumnIndex("OrderId"));
                    if (orders_ids.contains(order_id)){
                        //nothing
                    }
                    else{
                        orders_ids.add(order_id);
                    }
                }

            }
            else {
                orders_ids = pizzaDriverDB.getAllOrdersPerLocationId(all_orders_ids, getArguments().getString("LocationId"));
            }

        }

        Log.v("Test", "Array is " + orders_ids);


        TableLayout WrapperTable = view.findViewById(R.id.wrapperTableLayout);


        TableRow HeadLine = new TableRow(getContext());
        HeadLine.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.MATCH_PARENT));
        HeadLine.setBackgroundColor(ResourcesCompat.getColor(getResources(), R.color.mm_pine_green_shade_1, requireContext().getTheme()));
        HeadLine.setPadding(10, 10, 0, 10);
        HeadLine.setDividerPadding(10);

        TextView OrderNumberStatic = new TextView(getContext());
        OrderNumberStatic.setText(getString(R.string.number));
        OrderNumberStatic.setTypeface(OrderNumberStatic.getTypeface(), Typeface.BOLD);
        OrderNumberStatic.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
        OrderNumberStatic.setTextColor(ResourcesCompat.getColor(getResources(), R.color.white_50, requireContext().getTheme()));

        TextView OrderTypeStatic = new TextView(getContext());
        OrderTypeStatic.setText(R.string.type);
        OrderTypeStatic.setTypeface(OrderNumberStatic.getTypeface(), Typeface.BOLD);
        OrderTypeStatic.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
        OrderTypeStatic.setTextColor(ResourcesCompat.getColor(getResources(), R.color.white_50, getContext().getTheme()));

        TextView CashStatic = new TextView(getContext());
        CashStatic.setText(R.string.cash_boolean);
        CashStatic.setTypeface(CashStatic.getTypeface(), Typeface.BOLD);
        CashStatic.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
        CashStatic.setTextColor(ResourcesCompat.getColor(getResources(), R.color.white_50, getContext().getTheme()));

        TextView TipTextStatic = new TextView(getContext());
        TipTextStatic.setText(R.string.tip_text);
        TipTextStatic.setTypeface(TipTextStatic.getTypeface(), Typeface.BOLD);
        TipTextStatic.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
        TipTextStatic.setTextColor(ResourcesCompat.getColor(getResources(), R.color.white_50, getContext().getTheme()));

        TextView LocationStatic = new TextView(getContext());
        LocationStatic.setText(R.string.location);
        LocationStatic.setTypeface(LocationStatic.getTypeface(), Typeface.BOLD);
        LocationStatic.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
        LocationStatic.setTextColor(ResourcesCompat.getColor(getResources(), R.color.white_50, getContext().getTheme()));

        HeadLine.addView(OrderNumberStatic);
        HeadLine.addView(OrderTypeStatic);
        HeadLine.addView(CashStatic);
        HeadLine.addView(TipTextStatic);
        HeadLine.addView(LocationStatic);

        WrapperTable.addView(HeadLine);
        int counter = 0;
        for (final Integer orderId: orders_ids ){
            counter = counter + 1;
            Log.v("Test", "Order id is " + orderId);

            Cursor order_result = pizzaDriverDB.getOrderDataByOrderId(orderId);
            order_result.moveToFirst();
            Cursor tip_result = pizzaDriverDB.getTipData(orderId);
            tip_result.moveToFirst();
            int tipId = Integer.parseInt(tip_result.getString(tip_result.getColumnIndex("TipId")));
            Cursor cash_order_result = pizzaDriverDB.getCashOrderData(tipId);
            if (cash_order_result.getCount() > 0) {
                cash_order_result.moveToFirst();

            }
            Integer orderNumber = Integer.parseInt(order_result.getString(order_result.getColumnIndex("OrderNumber")));
            String OrderType = tip_result.getString(tip_result.getColumnIndex("Type"));
            String Tip = tip_result.getString(tip_result.getColumnIndex("Amount"));
            int Cash = Integer.parseInt(tip_result.getString(tip_result.getColumnIndex("Cash")));
            int LocationId = Integer.parseInt(order_result.getString(order_result.getColumnIndex("LocationId")));
            Cursor locations_result = pizzaDriverDB.getLocationData(LocationId);
            locations_result.moveToFirst();
            String OrderLocation = locations_result.getString(locations_result.getColumnIndex("Name"));

            TableRow Row = new TableRow(getContext());
            Row.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
            Row.setPadding(0, 0, 0, 0);
            if (counter % 2 == 0){
                Row.setBackgroundColor(ResourcesCompat.getColor(getResources(), R.color.mm_pine_green_shade_2, getContext().getTheme()));
            }
            else {
                Row.setBackgroundColor(ResourcesCompat.getColor(getResources(), R.color.mm_wild_yellow_shade_2, getContext().getTheme()));

            }
            Chip orderNumberChip = new Chip(requireContext());
            orderNumberChip.setEnsureMinTouchTargetSize(false);
            orderNumberChip.setText(orderNumber.toString());
            orderNumberChip.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
            orderNumberChip.setOnClickListener(v -> {
                Intent addOrderIntent = new Intent(getActivity(), AddOrder.class);
                addOrderIntent.putExtra("orderNumber", orderNumber);
                addOrderIntent.putExtra("SelectedDate", workingDate);
                startActivity(addOrderIntent);
            });
            orderNumberChip.setOnLongClickListener(v -> {
                showPopup(v, orderNumber, orderId);
                return true;
            });




            TextView OrderTypeText = new TextView(getContext());
            OrderTypeText.setText(OrderType);
            OrderTypeText.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));


            TextView CashText = new TextView((getContext()));
            if (Cash == 1){
                CashText.setText(R.string.cash);
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
            Row.setOnLongClickListener(v -> {
                Log.v("TEST", "Long Click");
                return false;
            });

            WrapperTable.addView(Row);
        }




        button_first.setOnClickListener(v -> NavHostFragment.findNavController(OrderListFragment.this)
                .navigate(R.id.action_OrderListFragment_to_SummaryFragment));




    }

    private void showPopup(View view, int OrderNumber, int OrderId) {
        PopupMenu popup = new PopupMenu(getContext(), view);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.order_number_options, popup.getMenu());
        popup.show();

        MenuItem change = popup.getMenu().findItem(R.id.order_change_number);
        change.setOnMenuItemClickListener(v ->{
            final String[] m_Text = {""};
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setTitle("New order number");
            final EditText input = new EditText(getContext());
            input.setInputType(InputType.TYPE_CLASS_NUMBER);
            builder.setView(input);
            builder.setPositiveButton("OK", (dialog, which) -> {
                m_Text[0] = input.getText().toString();
                int NewOrderNumber = Integer.parseInt(m_Text[0]);
                boolean changed = pizzaDriverDB.updateOrderNumber(workingDate, NewOrderNumber, OrderId);
                if (changed){
                    Toast updateToast = Toast.makeText(getContext(), "Updated order number " + OrderNumber + " to " + NewOrderNumber, Toast.LENGTH_SHORT);
                    updateToast.show();
                    getActivity().finish();
                    startActivity(getActivity().getIntent());
                }
                else {
                    Toast updateToast = Toast.makeText(getContext(), "Unable to update order number " + OrderNumber + " to " + NewOrderNumber + " please check...", Toast.LENGTH_LONG);
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
            Log.d("TEST", "showPopup: deleted");
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
            }
            else {
                Toast deletedToast = Toast.makeText(view.getContext(), "Unable to delete Order Number " + OrderNumber + " , something wrong", Toast.LENGTH_LONG);
                deletedToast.show();
            }

            getActivity().finish();
            startActivity(getActivity().getIntent());
            return deleted;
        });
    }
}
