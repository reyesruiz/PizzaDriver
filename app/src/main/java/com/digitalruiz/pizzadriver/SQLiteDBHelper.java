package com.digitalruiz.pizzadriver;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import java.util.ArrayList;

public class SQLiteDBHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "pizza_driver";
    public static final String TABLE = "Orders";
    public static final String ORDER_NUMBER = "OrderNumber";
    public static final String ORDER_TYPE = "OrderType";
    public static final String TIP = "Tip";
    public static final String TIP_CASH_BOOL = "TipCashBool";
    public static final String ORDER_TOTAL = "OrderTotal";
    public static final String CASH_RECEIVED = "CashReceived";
    public static final String LOCATION = "Location";


    public SQLiteDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase pizza_driver_db) {
        pizza_driver_db.execSQL("CREATE TABLE " + TABLE + " (" +
                ORDER_NUMBER + " INTEGER PRIMARY KEY UNIQUE, " +
                ORDER_TYPE + " TEXT, " +
                TIP + " REAL, " +
                TIP_CASH_BOOL + " INTEGER, " +
                ORDER_TOTAL + " REAL, " +
                CASH_RECEIVED + " REAL, " +
                LOCATION + " TEXT" + ")");

    }

    @Override
    public void onUpgrade(SQLiteDatabase pizza_driver_db, int oldVersion, int newVersion) {
        pizza_driver_db.execSQL("DROP TABLE IF EXISTS " +  TABLE);
    }

    public void deleteData(){
        SQLiteDatabase pizza_driver_db = this.getWritableDatabase();
        pizza_driver_db.execSQL("DELETE FROM " +  TABLE);
    }

    public boolean insertOrder (Integer OrderNumber,  String OrderType, String Tip, Integer TipCashBool, String OrderTotal, String CashReceived, String Location){
        SQLiteDatabase pizza_driver_db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(ORDER_NUMBER, OrderNumber);
        contentValues.put(ORDER_TYPE, OrderType);
        contentValues.put(TIP, Tip);
        contentValues.put(TIP_CASH_BOOL, TipCashBool);
        contentValues.put(ORDER_TOTAL, OrderTotal);
        contentValues.put(CASH_RECEIVED, CashReceived);
        contentValues.put(LOCATION, Location);
        long rowInserted  = pizza_driver_db.insert(TABLE, null, contentValues);
        return rowInserted != -1;
    }

    public Cursor getData(int orderNumber) {
        SQLiteDatabase pizza_driver_db = this.getReadableDatabase();
        return pizza_driver_db.rawQuery( "select * from " + TABLE + " where " + ORDER_NUMBER + "="+orderNumber+"", null );
    }


    public int numberOfRows(){
        SQLiteDatabase pizza_driver_db = this.getReadableDatabase();
        return (int) DatabaseUtils.queryNumEntries(pizza_driver_db, TABLE);
    }

    public boolean updateOrder (Integer OrderNumber,  String OrderType, String Tip, Integer TipCashBool, String OrderTotal, String CashReceived, String Location) {
        SQLiteDatabase pizza_driver_db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(ORDER_NUMBER, OrderNumber);
        contentValues.put(ORDER_TYPE, OrderType);
        contentValues.put(TIP, Tip);
        contentValues.put(TIP_CASH_BOOL, TipCashBool);
        contentValues.put(ORDER_TOTAL, OrderTotal);
        contentValues.put(CASH_RECEIVED, CashReceived);
        contentValues.put(LOCATION, Location);
        pizza_driver_db.update(TABLE, contentValues, ORDER_NUMBER + " = ? ", new String[] { Integer.toString(OrderNumber) } );
        long rowUpdated = pizza_driver_db.update(TABLE, contentValues, ORDER_NUMBER + " = ? ", new String[] { Integer.toString(OrderNumber) } );
        Log.v("Test", "row updated " + rowUpdated);
        return rowUpdated != -1;
    }

    public boolean updateOrderNumber (Integer OldOrderNumber, Integer OrderNumber){
        SQLiteDatabase pizza_driver_db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(ORDER_NUMBER, OrderNumber);
        pizza_driver_db.update(TABLE, contentValues, ORDER_NUMBER + " = ? ", new String[] { Integer.toString(OldOrderNumber) } );
        long rowUpdated = pizza_driver_db.update(TABLE, contentValues, ORDER_NUMBER + " = ? ", new String[] { Integer.toString(OrderNumber) } );
        Log.v("Test", "row updated " + rowUpdated);
        return rowUpdated != -1;
    }

// --Commented out by Inspection START (5/30/2020 1:26 AM):
//    public Integer deleteOrder (Integer OrderNumber) {
//        SQLiteDatabase pizza_driver_db = this.getWritableDatabase();
//        return pizza_driver_db.delete(TABLE,
//                ORDER_NUMBER + " = ? ",
//                new String[] { Integer.toString(OrderNumber) });
//    }
// --Commented out by Inspection STOP (5/30/2020 1:26 AM)

    public ArrayList<Integer> getAllOrders() {
        ArrayList<Integer> array_list = new ArrayList<>();

        //hp = new HashMap();
        SQLiteDatabase pizza_driver_db = this.getReadableDatabase();
        Cursor res =  pizza_driver_db.rawQuery( "select * from " + TABLE, null );
        res.moveToFirst();

        while(!res.isAfterLast()){
            array_list.add(Integer.parseInt(res.getString(res.getColumnIndex(ORDER_NUMBER))));
            res.moveToNext();
        }
        res.close();
        return array_list;
    }

    public ArrayList<Integer> getAllCredit() {
        ArrayList<Integer> array_list = new ArrayList<>();

        //hp = new HashMap();
        SQLiteDatabase pizza_driver_db = this.getReadableDatabase();
        Cursor res =  pizza_driver_db.rawQuery( "SELECT * FROM " + TABLE + " WHERE (" + ORDER_TYPE + " = \"Credit Manual\" OR " + ORDER_TYPE + " = \"Credit Auto\" OR " + ORDER_TYPE + " = \"Grubhub\" OR " + ORDER_TYPE + " = \"Other\") AND " + TIP_CASH_BOOL + " = 0", null);
        res.moveToFirst();
        while(!res.isAfterLast()){
            array_list.add(Integer.parseInt(res.getString(res.getColumnIndex(ORDER_NUMBER))));
            res.moveToNext();
        }
        res.close();
        return array_list;
    }

    public ArrayList<Integer> getAllCash() {
        ArrayList<Integer> array_list = new ArrayList<>();

        //hp = new HashMap();
        SQLiteDatabase pizza_driver_db = this.getReadableDatabase();
        Cursor res =  pizza_driver_db.rawQuery( "SELECT * FROM " + TABLE + " WHERE " + ORDER_TYPE + " = \"Cash\" OR " + TIP_CASH_BOOL + " = 1", null);
        res.moveToFirst();
        while(!res.isAfterLast()){
            array_list.add(Integer.parseInt(res.getString(res.getColumnIndex(ORDER_NUMBER))));
            res.moveToNext();
        }
        res.close();
        return array_list;
    }

    public ArrayList<Integer> getAllLocation(String location) {
        ArrayList<Integer> array_list = new ArrayList<>();

        //hp = new HashMap();
        SQLiteDatabase pizza_driver_db = this.getReadableDatabase();
        Cursor res =  pizza_driver_db.rawQuery( "SELECT * FROM " + TABLE + " WHERE " + LOCATION + " = \"" + location + "\"", null);
        res.moveToFirst();
        while(!res.isAfterLast()){
            array_list.add(Integer.parseInt(res.getString(res.getColumnIndex(ORDER_NUMBER))));
            res.moveToNext();
        }
        res.close();
        return array_list;
    }

    public ArrayList<Integer> getAllCashOrders() {
        ArrayList<Integer> array_list = new ArrayList<>();

        //hp = new HashMap();
        SQLiteDatabase pizza_driver_db = this.getReadableDatabase();
        Cursor res =  pizza_driver_db.rawQuery( "SELECT * FROM " + TABLE + " WHERE " + ORDER_TYPE + " = \"Cash\"", null);
        res.moveToFirst();
        while(!res.isAfterLast()){
            array_list.add(Integer.parseInt(res.getString(res.getColumnIndex(ORDER_NUMBER))));
            res.moveToNext();
        }
        res.close();
        return array_list;
    }

    public int numberOfRowsPerType(String Type, String TipCashBool){
        SQLiteDatabase pizza_driver_db = this.getReadableDatabase();
        Cursor res;
        if (TipCashBool.equals("*")){
            res = pizza_driver_db.rawQuery("SELECT * FROM " + TABLE + " WHERE " + ORDER_TYPE + " = \"" + Type + "\"", null);
        }
        else {
            res = pizza_driver_db.rawQuery("SELECT * FROM " + TABLE + " WHERE " + ORDER_TYPE + " = \"" + Type + "\"" + " AND " + TIP_CASH_BOOL + " = " + TipCashBool, null);
        }
        res.moveToFirst();
        int counter = 0;
        while(!res.isAfterLast()){
            counter = counter + 1;
            res.moveToNext();
        }
        res.close();
        return counter;
    }


    public ArrayList<Integer> getAllOrdersPerType(String Type, String TipCashBool) {
        ArrayList<Integer> array_list = new ArrayList<>();

        SQLiteDatabase pizza_driver_db = this.getReadableDatabase();
        Cursor res;
        if (TipCashBool.equals("*")){
            res = pizza_driver_db.rawQuery("SELECT * FROM " + TABLE + " WHERE " + ORDER_TYPE + " = \"" + Type + "\"", null);
        }
        else {
            res = pizza_driver_db.rawQuery("SELECT * FROM " + TABLE + " WHERE " + ORDER_TYPE + " = \"" + Type + "\"" + " AND " + TIP_CASH_BOOL + " = " + TipCashBool, null);
            Log.v("TEST", res + "");
        }
        res.moveToFirst();

        while(!res.isAfterLast()){
            array_list.add(Integer.parseInt(res.getString(res.getColumnIndex(ORDER_NUMBER))));
            res.moveToNext();
        }
        res.close();
        return array_list;
    }

    public ArrayList<Integer> getAllOrdersPerLocation(String Location) {
        ArrayList<Integer> array_list = new ArrayList<>();

        SQLiteDatabase pizza_driver_db = this.getReadableDatabase();
        Cursor res;
        res = pizza_driver_db.rawQuery("SELECT * FROM " + TABLE + " WHERE " + LOCATION + " = \"" + Location + "\"", null);

        res.moveToFirst();

        while(!res.isAfterLast()){
            array_list.add(Integer.parseInt(res.getString(res.getColumnIndex(ORDER_NUMBER))));
            res.moveToNext();
        }
        res.close();
        return array_list;
    }


}
