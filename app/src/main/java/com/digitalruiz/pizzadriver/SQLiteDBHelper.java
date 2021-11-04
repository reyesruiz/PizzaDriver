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
    private static final int DATABASE_VERSION = 2;
    public static final String DATABASE_NAME = "pizza_driver";

    //OrdersTable
    public static final String ORDERS_TABLE = "Orders";
    public static final String ORDER_ID = "OrderId";
    public static final String DATE = "Date";
    public static final String ORDER_NUMBER = "OrderNumber";
    public static final String TIP_ID = "TipId";
    public static final String LOCATION_ID = "LocationId";
    public static final String ARCHIVED = "Archived";

    //Locations Table
    public static final String LOCATIONS_TABLE = "Locations";
    public static final String NAME = "Name";
    public static final String RATE = "Rate";

    //Tips Table
    public static final String TIPS_TABLE = "Tips";
    public static final String AMOUNT = "Amount";
    public static final String TYPE = "Type";
    public static final String CASH = "Cash";
    public static final String CASH_ORDER_ID = "CashOrderId";

    //CashOrders
    public static final String CASH_ORDERS_TABLE = "CashOrders";
    public static final String TOTAL = "Total";
    public static final String RECEIVED = "Received";

    public SQLiteDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase pizza_driver_db) {
        pizza_driver_db.execSQL("CREATE TABLE " + ORDERS_TABLE + " (" +
                ORDER_ID + " INTEGER NOT NULL UNIQUE PRIMARY KEY AUTOINCREMENT, " +
                DATE + " TEXT NOT NULL, " +
                ORDER_NUMBER + " INTEGER NOT NULL, " +
                LOCATION_ID + " INTEGER NOT NULL, " +
                ARCHIVED + " INTEGER NOT NULL, " +
                "UNIQUE (" + DATE + "," + ORDER_NUMBER + ")" +
                ")"
        );

        pizza_driver_db.execSQL("CREATE TABLE " + TIPS_TABLE + " (" +
                TIP_ID + " INTEGER NOT NULL UNIQUE PRIMARY KEY AUTOINCREMENT, " +
                ORDER_ID + " INTEGER NOT NULL, " +
                AMOUNT + " REAL NOT NULL, " +
                TYPE + " TEXT NOT NULL, " +
                CASH + " INTEGER NOT NULL " +
                ")"
        );

        pizza_driver_db.execSQL("CREATE TABLE " + CASH_ORDERS_TABLE + " (" +
                CASH_ORDER_ID + " INTEGER NOT NULL UNIQUE PRIMARY KEY AUTOINCREMENT, " +
                TOTAL + " REAL, " +
                RECEIVED + " REAL, " +
                TIP_ID + " INTEGER NOT NULL " +
                ")"
        );

        pizza_driver_db.execSQL("CREATE TABLE " + LOCATIONS_TABLE + " (" +
                LOCATION_ID + " INTEGER NOT NULL UNIQUE PRIMARY KEY AUTOINCREMENT, " +
                NAME + " TEXT NOT NULL, " +
                RATE + " REAL NOT NULL " + ")"
        );

        ContentValues contentValues = new ContentValues();
        contentValues.put(LOCATION_ID, 1);
        contentValues.put(NAME, "Tracy");
        contentValues.put(RATE, "1.75");
        pizza_driver_db.insert(LOCATIONS_TABLE, null, contentValues);

        contentValues.clear();
        contentValues.put(LOCATION_ID, 2);
        contentValues.put(NAME, "Mountain House");
        contentValues.put(RATE, "2.50");
        pizza_driver_db.insert(LOCATIONS_TABLE, null, contentValues);


    }

    @Override
    public void onUpgrade(SQLiteDatabase pizza_driver_db, int oldVersion, int newVersion) {
        pizza_driver_db.execSQL("DROP TABLE IF EXISTS " +  ORDERS_TABLE);
        pizza_driver_db.execSQL("DROP TABLE IF EXISTS " +  TIPS_TABLE);
        pizza_driver_db.execSQL("DROP TABLE IF EXISTS " +  CASH_ORDERS_TABLE);
        pizza_driver_db.execSQL("DROP TABLE IF EXISTS " +  LOCATIONS_TABLE);
    }

    public void deleteData(){
        SQLiteDatabase pizza_driver_db = this.getWritableDatabase();
        pizza_driver_db.execSQL("DELETE FROM " +  ORDERS_TABLE);
        pizza_driver_db.execSQL("DELETE FROM " +  TIPS_TABLE);
        pizza_driver_db.execSQL("DELETE FROM " +  CASH_ORDERS_TABLE);
        pizza_driver_db.execSQL("DELETE FROM " +  LOCATIONS_TABLE);
        Log.d("TEST", "deleteData: ");
        ContentValues contentValues = new ContentValues();
        contentValues.put(LOCATION_ID, 1);
        contentValues.put(NAME, "Tracy");
        contentValues.put(RATE, "1.75");
        pizza_driver_db.insert(LOCATIONS_TABLE, null, contentValues);

        contentValues.clear();
        contentValues.put(LOCATION_ID, 2);
        contentValues.put(NAME, "Mountain House");
        contentValues.put(RATE, "2.50");
        pizza_driver_db.insert(LOCATIONS_TABLE, null, contentValues);
    }

    public long insertOrder (String WorkingDate, Integer OrderNumber, Integer LocationID, Integer Archived){
        SQLiteDatabase pizza_driver_db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DATE, WorkingDate);
        contentValues.put(ORDER_NUMBER, OrderNumber);
        contentValues.put(LOCATION_ID, LocationID);
        contentValues.put(ARCHIVED, Archived);
        long rowInserted  = pizza_driver_db.insert(ORDERS_TABLE, null, contentValues);
        Log.d("TEST", "insertOrder: " + rowInserted);
        return rowInserted;
    }

    public long insertTip (String Amount,  String Type, Integer TipCashBool, long OrderId){
        SQLiteDatabase pizza_driver_db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(AMOUNT, Amount);
        contentValues.put(TYPE, Type);
        contentValues.put(CASH, TipCashBool);
        contentValues.put(ORDER_ID, OrderId);
        long rowInserted  = pizza_driver_db.insert(TIPS_TABLE, null, contentValues);
        Log.d("TEST", "insertOrder: " + rowInserted);
        return rowInserted;
    }

    public long insertCashOrder (String Total,  String Received, long TipId){
        SQLiteDatabase pizza_driver_db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(TOTAL, Total);
        contentValues.put(RECEIVED, Received);
        contentValues.put(TIP_ID, TipId);
        long rowInserted  = pizza_driver_db.insert(CASH_ORDERS_TABLE, null, contentValues);
        Log.d("TEST", "insertOrder: " + rowInserted);
        return rowInserted;
    }


    public Cursor getOrderData(String WorkingDate, int orderNumber) {
        SQLiteDatabase pizza_driver_db = this.getReadableDatabase();
        String sql = "SELECT * FROM " + ORDERS_TABLE + " WHERE " + ORDER_NUMBER + " = " + orderNumber + " AND " + DATE + " = " + "\""+WorkingDate+"\"";
        Cursor cursor = pizza_driver_db.rawQuery(sql, null);
        return cursor;
    }

    public Cursor getTipData(int orderId) {
        SQLiteDatabase pizza_driver_db = this.getReadableDatabase();
        String sql = "SELECT * FROM " + TIPS_TABLE + " WHERE " + ORDER_ID + " = " + orderId;
        Cursor cursor = pizza_driver_db.rawQuery(sql, null);
        return cursor;
    }

    public Cursor getLocationData(int locationId) {
        SQLiteDatabase pizza_driver_db = this.getReadableDatabase();
        String sql = "SELECT * FROM " + LOCATIONS_TABLE + " WHERE " + LOCATION_ID + " = " + locationId;
        Cursor cursor = pizza_driver_db.rawQuery(sql, null);
        return cursor;
    }


    /*

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
        boolean exists = checkAlreadyExist(OrderNumber);
        if (exists){
            return false;
        }
        else {
            int rowUpdated = pizza_driver_db.update(TABLE, contentValues, ORDER_NUMBER + " = ? ", new String[]{Integer.toString(OldOrderNumber)});
            Log.v("Test", "row updated " + rowUpdated);
            return rowUpdated != -1;
        }
    }


    public boolean deleteOrder (Integer OrderNumber) {
        SQLiteDatabase pizza_driver_db = this.getWritableDatabase();
        int row_deleted = pizza_driver_db.delete(TABLE,
                ORDER_NUMBER + " = ? ",
                new String[] { Integer.toString(OrderNumber) });
        Log.v("Test", "row deleted " + row_deleted);
        return row_deleted == 1;

    }


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
        Cursor res =  pizza_driver_db.rawQuery( "SELECT * FROM " + TABLE + " WHERE (" + ORDER_TYPE + " = \"Credit Manual\" OR " + ORDER_TYPE + " = \"Credit Auto\" OR " + ORDER_TYPE + " = \"Grubhub\" OR " + ORDER_TYPE + " = \"LevelUp\" OR " + ORDER_TYPE + " = \"Other\") AND " + TIP_CASH_BOOL + " = 0", null);
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

    public boolean checkAlreadyExist(int orderNumber){
        SQLiteDatabase pizza_driver_db = this.getReadableDatabase();
        Cursor result = pizza_driver_db.rawQuery("select * from " + TABLE + " where " + ORDER_NUMBER + "=" + orderNumber + "", null);
        int count = result.getCount();
        result.close();
        return count > 0;
    }
    */


}
