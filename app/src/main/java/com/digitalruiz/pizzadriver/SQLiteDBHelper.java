package com.digitalruiz.pizzadriver;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;

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
        super((Context) context, DATABASE_NAME, null, DATABASE_VERSION);
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

    public boolean insertOrder (Integer OrderNumber,  String OrderType, double Tip, Integer TipCashBool, double OrderTotal, double CashReceived, String Location){
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
        if(rowInserted != -1) {
            return true;
        }
        else {
            return false;
        }
    }

    public Cursor getData(int orderNumber) {
        SQLiteDatabase pizza_driver_db = this.getReadableDatabase();
        Cursor res =  pizza_driver_db.rawQuery( "select * from " + TABLE + " where " + ORDER_NUMBER + "="+orderNumber+"", null );
        return res;
    }


    public int numberOfRows(){
        SQLiteDatabase pizza_driver_db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(pizza_driver_db, TABLE);
        return numRows;
    }

    public boolean updateOrder (Integer OrderNumber,  String OrderType, double Tip, Integer TipCashBool, double OrderTotal, double CashReceived, String Location) {
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
        if(rowUpdated != -1) {
            return true;
        }
        else {
            return false;
        }
    }

    public boolean updateOrderNumber (Integer OldOrderNumber, Integer OrderNumber){
        SQLiteDatabase pizza_driver_db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(ORDER_NUMBER, OrderNumber);
        pizza_driver_db.update(TABLE, contentValues, ORDER_NUMBER + " = ? ", new String[] { Integer.toString(OldOrderNumber) } );
        long rowUpdated = pizza_driver_db.update(TABLE, contentValues, ORDER_NUMBER + " = ? ", new String[] { Integer.toString(OrderNumber) } );
        Log.v("Test", "row updated " + rowUpdated);
        if(rowUpdated != -1) {
            return true;
        }
        else {
            return false;
        }
    }

    public Integer deleteOrder (Integer OrderNumber) {
        SQLiteDatabase pizza_driver_db = this.getWritableDatabase();
        return pizza_driver_db.delete(TABLE,
                ORDER_NUMBER + " = ? ",
                new String[] { Integer.toString(OrderNumber) });
    }

    public ArrayList<Integer> getAllOrders() {
        ArrayList<Integer> array_list = new ArrayList<Integer>();

        //hp = new HashMap();
        SQLiteDatabase pizza_driver_db = this.getReadableDatabase();
        Cursor res =  pizza_driver_db.rawQuery( "select * from " + TABLE, null );
        res.moveToFirst();

        while(res.isAfterLast() == false){
            array_list.add(Integer.parseInt(res.getString(res.getColumnIndex(ORDER_NUMBER))));
            res.moveToNext();
        }
        return array_list;
    }

}
