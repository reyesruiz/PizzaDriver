package com.digitalruiz.pizzadriver;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.google.android.libraries.places.api.model.AddressComponents;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class SQLiteDBHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 4;
    public static final String DATABASE_NAME = "pizza_driver";

    //OrdersTable
    public static final String ORDERS_TABLE = "Orders";
    public static final String ORDER_ID = "OrderId";
    public static final String DATE = "Date";
    public static final String ORDER_NUMBER = "OrderNumber";
    public static final String TIP_ID = "TipId";
    public static final String LOCATION_ID = "LocationId";

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

    //Location Addresses
    public static final String LOCATION_ADDRESSES_TABLE = "LocationAddresses";
    public static final String ADDRESS_ID = "AddressID";
    public static final String GOOGLE_LOCATION_ID = "GoogleLocationId";
    public static final String ADDRESS_NAME = "AddressName";
    public static final String ADDRESS = "Address";
    public static final String ADDRESS_COMPONENTS = "AddressComponents";

    //LocationAddressSubDivision
    public static final String LOCATION_ADDRESS_SUBDIVISION_TABLE = "LocationAddressSubDivision";
    public static final String SUBDIVISION_ID = "SubdivisionID";
    public static final String SUBDIVISION = "SUBDIVISION";

    //Notes
    public static final String LOCATION_ADDRESS_NOTES_TABLE = "LocationAddressNotes";
    public static final String NOTE_ID = "NoteId";
    public static final String NOTE = "Note";
    public static final String DATE_ADDED = "DateAdded";

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

        pizza_driver_db.execSQL("CREATE TABLE " + LOCATION_ADDRESSES_TABLE + " (" +
                ADDRESS_ID + " INTEGER NOT NULL UNIQUE PRIMARY KEY AUTOINCREMENT, " +
                GOOGLE_LOCATION_ID + " TEXT NOT NULL UNIQUE, " +
                ADDRESS_NAME + " TEXT NOT NULL, " +
                ADDRESS + " TEXT NOT NULL, " +
                ADDRESS_COMPONENTS + " TEXT NOT NULL " +
                ")"
        );

        pizza_driver_db.execSQL("CREATE TABLE " + LOCATION_ADDRESS_SUBDIVISION_TABLE + " (" +
                SUBDIVISION_ID + " INTEGER NOT NULL UNIQUE PRIMARY KEY AUTOINCREMENT, " +
                ADDRESS_ID + " INTEGER NOT NULL, " +
                SUBDIVISION + " TEXT NOT NULL, " +
                "UNIQUE (" + ADDRESS_ID + "," + SUBDIVISION + ")" +
                ")"
        );

        pizza_driver_db.execSQL("CREATE TABLE " + LOCATION_ADDRESS_NOTES_TABLE + " (" +
                NOTE_ID + " INTEGER NOT NULL UNIQUE PRIMARY KEY AUTOINCREMENT, " +
                ADDRESS_ID + " INTEGER NOT NULL, " +
                SUBDIVISION_ID + " INTEGER, " +
                NOTE + " TEXT NOT NULL, " +
                DATE_ADDED + " TEXT NOT NULL" +
                ")"
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
        if (oldVersion < 2) {
            pizza_driver_db.execSQL("DROP TABLE IF EXISTS " + ORDERS_TABLE);
            pizza_driver_db.execSQL("DROP TABLE IF EXISTS " + TIPS_TABLE);
            pizza_driver_db.execSQL("DROP TABLE IF EXISTS " + CASH_ORDERS_TABLE);
            pizza_driver_db.execSQL("DROP TABLE IF EXISTS " + LOCATIONS_TABLE);
        }
        if (newVersion == 3) {
            pizza_driver_db.execSQL("CREATE TABLE " + LOCATION_ADDRESSES_TABLE + " (" +
                    ADDRESS_ID + " INTEGER NOT NULL UNIQUE PRIMARY KEY AUTOINCREMENT, " +
                    GOOGLE_LOCATION_ID + " TEXT NOT NULL UNIQUE, " +
                    ADDRESS_NAME + " TEXT NOT NULL, " +
                    ADDRESS + " TEXT NOT NULL, " +
                    ADDRESS_COMPONENTS + " TEXT NOT NULL " +
                    ")"
            );
        }
        if (newVersion == 4) {
            pizza_driver_db.execSQL("CREATE TABLE " + LOCATION_ADDRESS_SUBDIVISION_TABLE + " (" +
                    SUBDIVISION_ID + " INTEGER NOT NULL UNIQUE PRIMARY KEY AUTOINCREMENT, " +
                    ADDRESS_ID + " INTEGER NOT NULL, " +
                    SUBDIVISION + " TEXT NOT NULL, " +
                    "UNIQUE (" + ADDRESS_ID + "," + SUBDIVISION + ")" +
                    ")"
            );

            pizza_driver_db.execSQL("CREATE TABLE " + LOCATION_ADDRESS_NOTES_TABLE + " (" +
                    NOTE_ID + " INTEGER NOT NULL UNIQUE PRIMARY KEY AUTOINCREMENT, " +
                    ADDRESS_ID + " INTEGER NOT NULL, " +
                    SUBDIVISION_ID + " INTEGER, " +
                    NOTE + " TEXT NOT NULL, " +
                    DATE_ADDED + " TEXT NOT NULL" +
                    ")"
            );
        }
    }

    public void deleteData(){
        SQLiteDatabase pizza_driver_db = this.getWritableDatabase();
        pizza_driver_db.execSQL("DELETE FROM " + ORDERS_TABLE);
        pizza_driver_db.execSQL("DELETE FROM " + TIPS_TABLE);
        pizza_driver_db.execSQL("DELETE FROM " + CASH_ORDERS_TABLE);
        pizza_driver_db.execSQL("DELETE FROM " + LOCATIONS_TABLE);
        pizza_driver_db.execSQL("DELETE FROM " + LOCATION_ADDRESSES_TABLE);
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

    public long insertOrder (String WorkingDate, Integer OrderNumber, Integer LocationID){
        SQLiteDatabase pizza_driver_db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DATE, WorkingDate);
        contentValues.put(ORDER_NUMBER, OrderNumber);
        contentValues.put(LOCATION_ID, LocationID);
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
        Log.d("TEST", "insertTip: " + rowInserted);
        return rowInserted;
    }

    public long insertCashOrder (String Total,  String Received, long TipId){
        Log.d("TEST", "insertCashOrder: " + Total + " " + Received);
        SQLiteDatabase pizza_driver_db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(TOTAL, Total);
        contentValues.put(RECEIVED, Received);
        contentValues.put(TIP_ID, TipId);
        long rowInserted  = pizza_driver_db.insert(CASH_ORDERS_TABLE, null, contentValues);
        Log.d("TEST", "insertCash: " + rowInserted);
        return rowInserted;
    }

    public long insertLocationAddress (String GoogleLocationId, String AddressName, String Address, String
        AddressComponents){
        SQLiteDatabase pizza_driver_db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(GOOGLE_LOCATION_ID, GoogleLocationId);
        contentValues.put(ADDRESS_NAME, AddressName);
        contentValues.put(ADDRESS, Address);
        contentValues.put(ADDRESS_COMPONENTS, AddressComponents);
        long rowInserted  = pizza_driver_db.insert(LOCATION_ADDRESSES_TABLE, null, contentValues);
        Log.d("TEST", "insertLocationAddress: " + rowInserted);
        return rowInserted;
    }

    public long insertSubDivisionAddress(int AddressId, String Subdivision ) {
        SQLiteDatabase pizza_driver_db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(ADDRESS_ID, AddressId);
        contentValues.put(SUBDIVISION, Subdivision);
        long rowInserted = pizza_driver_db.insert(LOCATION_ADDRESS_SUBDIVISION_TABLE, null, contentValues);
        return rowInserted;
    }


    public Cursor getOrderData(String WorkingDate, int orderNumber) {
        SQLiteDatabase pizza_driver_db = this.getReadableDatabase();
        String sql = "SELECT * FROM " + ORDERS_TABLE + " WHERE " + ORDER_NUMBER + " = " + orderNumber + " AND " + DATE + " = " + "\""+WorkingDate+"\"";
        return pizza_driver_db.rawQuery(sql, null);
    }

    public Cursor getOrderDataByOrderId(int orderId) {
        SQLiteDatabase pizza_driver_db = this.getReadableDatabase();
        String sql = "SELECT * FROM " + ORDERS_TABLE + " WHERE " + ORDER_ID + " = " + orderId;
        return pizza_driver_db.rawQuery(sql, null);
    }

    public Cursor getTipData(int orderId) {
        SQLiteDatabase pizza_driver_db = this.getReadableDatabase();
        String sql = "SELECT * FROM " + TIPS_TABLE + " WHERE " + ORDER_ID + " = " + orderId;
        return pizza_driver_db.rawQuery(sql, null);
    }

    public Cursor getTipDataByTipId(int tipId) {
        SQLiteDatabase pizza_driver_db = this.getReadableDatabase();
        String sql = "SELECT * FROM " + TIPS_TABLE + " WHERE " + TIP_ID + " = " + tipId;
        return pizza_driver_db.rawQuery(sql, null);
    }

    public Cursor getLocationData(int locationId) {
        SQLiteDatabase pizza_driver_db = this.getReadableDatabase();
        String sql = "SELECT * FROM " + LOCATIONS_TABLE + " WHERE " + LOCATION_ID + " = " + locationId;
        return pizza_driver_db.rawQuery(sql, null);
    }

    public Cursor getCashOrderData(int tipId) {
        SQLiteDatabase pizza_driver_db = this.getReadableDatabase();
        String sql = "SELECT * FROM " + CASH_ORDERS_TABLE + " WHERE " + TIP_ID + " = " + tipId;
        return pizza_driver_db.rawQuery(sql, null);
    }

    public Cursor getCashOrderDataByCashOrderId(int orderId) {
        SQLiteDatabase pizza_driver_db = this.getReadableDatabase();
        String sql = "SELECT * FROM " + CASH_ORDERS_TABLE + " WHERE " + CASH_ORDER_ID + " = " + orderId;
        return pizza_driver_db.rawQuery(sql, null);
    }

    public Cursor getLocationAddressDataByAddressId(int addressId) {
        SQLiteDatabase pizza_driver_db = this.getReadableDatabase();
        String sql = "SELECT * FROM " + LOCATION_ADDRESSES_TABLE + " WHERE " + ADDRESS_ID + " = " + addressId;
        return pizza_driver_db.rawQuery(sql, null);
    }

    public int getAddressIdByLocationId(String googleLocationId) {
        SQLiteDatabase pizza_driver_db = this.getReadableDatabase();
        String sql = "SELECT " + ADDRESS_ID + " FROM " + LOCATION_ADDRESSES_TABLE + " WHERE " + GOOGLE_LOCATION_ID + " = " + "'" + googleLocationId + "'";
        Cursor cursor = pizza_driver_db.rawQuery(sql, null);
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            int AddressID = cursor.getInt(cursor.getColumnIndex(ADDRESS_ID));
            Log.d("TAG", "getAddressIdByLocationId: " + AddressID);
            return AddressID;
        }
        else {
            return 0;
        }
    }

    public ArrayList<Integer> getSubDivisionsByAddressID(int AddressID){
        ArrayList<Integer> array_list = new ArrayList<>();
        SQLiteDatabase pizza_driver_db = this.getReadableDatabase();
        String sql = "SELECT " + SUBDIVISION_ID + " FROM " + LOCATION_ADDRESS_SUBDIVISION_TABLE + " WHERE " + ADDRESS_ID + " = " + "'" + AddressID + "'";
        Cursor res = pizza_driver_db.rawQuery(sql, null);
        res.moveToFirst();
        while(!res.isAfterLast()){
            array_list.add(Integer.parseInt(res.getString(res.getColumnIndex(SUBDIVISION_ID))));
            res.moveToNext();
        }
        res.close();
        return array_list;

    }

    public String getSubDivisionBySubId(int SubdivisionID){
        SQLiteDatabase pizza_driver_db = this.getReadableDatabase();
        String sql = "SELECT " + SUBDIVISION + " FROM " + LOCATION_ADDRESS_SUBDIVISION_TABLE + " WHERE " + SUBDIVISION_ID + " = " + "'" + SubdivisionID + "'";
        Cursor res = pizza_driver_db.rawQuery(sql, null);
        res.moveToFirst();
        String SubNum = res.getString(res.getColumnIndex(SUBDIVISION));
        return SubNum;
    }


    public int updateOrder (long OrderId, String WorkingDate, Integer OrderNumber, Integer LocationID){
        SQLiteDatabase pizza_driver_db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DATE, WorkingDate);
        contentValues.put(ORDER_NUMBER, OrderNumber);
        contentValues.put(LOCATION_ID, LocationID);
        int rowUpdated  = pizza_driver_db.update(ORDERS_TABLE, contentValues, ORDER_ID + " = ? ", new String[]{String.valueOf(OrderId)});
        Log.d("TEST", "rowUpdated: " + rowUpdated);
        return rowUpdated;
    }

    public int updateTip (long TipId, String Amount,  String Type, Integer TipCashBool, long OrderId){
        SQLiteDatabase pizza_driver_db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(AMOUNT, Amount);
        contentValues.put(TYPE, Type);
        contentValues.put(CASH, TipCashBool);
        contentValues.put(ORDER_ID, OrderId);
        int rowUpdated  = pizza_driver_db.update(TIPS_TABLE, contentValues, TIP_ID + " = ? ", new String[]{String.valueOf(TipId)});
        Log.d("TEST", "rowUpdated: " + rowUpdated);
        return rowUpdated;
    }

    public int updateCashOrder (long CashOrderId, String Total,  String Received, long TipId){
        SQLiteDatabase pizza_driver_db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(TOTAL, Total);
        contentValues.put(RECEIVED, Received);
        contentValues.put(TIP_ID, TipId);
        int rowUpdated  = pizza_driver_db.update(CASH_ORDERS_TABLE, contentValues, CASH_ORDER_ID + " = ? ", new String[]{String.valueOf(CashOrderId)});
        Log.d("TEST", "rowUpdated: " + rowUpdated);
        return rowUpdated;
    }



    public boolean updateOrderNumber (String WorkingDate, Integer OrderNumber, Integer OrderId){
        SQLiteDatabase pizza_driver_db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(ORDER_NUMBER, OrderNumber);
        boolean exists = checkAlreadyExist(WorkingDate, OrderNumber);
        if (exists){
            return false;
        }
        else {
            int rowUpdated = pizza_driver_db.update(ORDERS_TABLE, contentValues, ORDER_ID + " = ? ", new String[]{(String.valueOf(OrderId))});
            Log.v("Test", "row updated " + rowUpdated);
            return rowUpdated != -1;
        }
    }

    public ArrayList<Integer> getAllTipsPerOrderId(Integer orderId) {
        ArrayList<Integer> array_list = new ArrayList<>();
        //hp = new HashMap();
        SQLiteDatabase pizza_driver_db = this.getReadableDatabase();
        Cursor res =  pizza_driver_db.rawQuery( "select * from " + TIPS_TABLE + " where " + ORDER_ID + " = " + orderId, null );

        res.moveToFirst();

        while(!res.isAfterLast()){
            array_list.add(Integer.parseInt(res.getString(res.getColumnIndex(TIP_ID))));
            res.moveToNext();
        }
        res.close();
        return array_list;
    }

    public ArrayList<Integer> getAllCashOrdersPerTipId(Integer tipId) {
        ArrayList<Integer> array_list = new ArrayList<>();
        //hp = new HashMap();
        SQLiteDatabase pizza_driver_db = this.getReadableDatabase();
        Cursor res =  pizza_driver_db.rawQuery( "select * from " + CASH_ORDERS_TABLE + " where " + TIP_ID + " = " + tipId, null );
        res.moveToFirst();

        while(!res.isAfterLast()){
            array_list.add(Integer.parseInt(res.getString(res.getColumnIndex(CASH_ORDER_ID))));
            res.moveToNext();
        }
        res.close();
        return array_list;
    }

    public boolean deleteCashOrder (Integer cashOrderId) {
        SQLiteDatabase pizza_driver_db = this.getWritableDatabase();
        int row_deleted = pizza_driver_db.delete(CASH_ORDERS_TABLE,
                CASH_ORDER_ID + " = ? ",
                new String[] { Integer.toString(cashOrderId) });
        Log.v("Test", "row deleted " + row_deleted);
        return row_deleted == 1;
    }

    public boolean deleteTip (Integer tipId) {
        SQLiteDatabase pizza_driver_db = this.getWritableDatabase();
        int row_deleted = pizza_driver_db.delete(TIPS_TABLE,
                TIP_ID + " = ? ",
                new String[] { Integer.toString(tipId) });
        Log.v("Test", "row deleted " + row_deleted);
        return row_deleted == 1;
    }

    public boolean deleteOrder (Integer orderId) {
        SQLiteDatabase pizza_driver_db = this.getWritableDatabase();
        int row_deleted = pizza_driver_db.delete(ORDERS_TABLE,
                ORDER_ID + " = ? ",
                new String[] { Integer.toString(orderId) });
        Log.v("Test", "row deleted " + row_deleted);
        return row_deleted == 1;
    }

    public boolean deleteLocationId (Integer AddressId) {
        SQLiteDatabase pizza_driver_db = this.getWritableDatabase();
        int row_deleted = pizza_driver_db.delete(LOCATION_ADDRESSES_TABLE,
                ADDRESS_ID + " = ? ",
                new String[] { Integer.toString(AddressId) });
        Log.v("Test", "row deleted " + row_deleted);
        return row_deleted == 1;
    }



    public ArrayList<Integer> getAllOrders(String workingDate) {
        ArrayList<Integer> array_list = new ArrayList<>();

        //hp = new HashMap();
        SQLiteDatabase pizza_driver_db = this.getReadableDatabase();
        Cursor res =  pizza_driver_db.rawQuery( "select * from " + ORDERS_TABLE + " where " + DATE + " = " + "\"" + workingDate + "\"", null );
        res.moveToFirst();

        while(!res.isAfterLast()){
            array_list.add(Integer.parseInt(res.getString(res.getColumnIndex(ORDER_ID))));
            res.moveToNext();
        }
        res.close();
        return array_list;
    }

    public ArrayList<Integer> getAllTips(ArrayList<Integer> ordersIds) {
        ArrayList<Integer> array_list = new ArrayList<>();
        Log.d("TEST", "getAllCredit: " + ordersIds);
        String ordersIdsString = ordersIds.stream().map(Object::toString).collect(Collectors.joining(", "));

        Log.d("TEST", "getAllCredit: " + ordersIdsString);
        //hp = new HashMap();
        SQLiteDatabase pizza_driver_db = this.getReadableDatabase();
        Cursor res =  pizza_driver_db.rawQuery( "SELECT * FROM " + TIPS_TABLE + " WHERE " + ORDER_ID + " IN (" + ordersIdsString + ")", null);
        res.moveToFirst();
        while(!res.isAfterLast()){
            array_list.add(Integer.parseInt(res.getString(res.getColumnIndex(TIP_ID))));
            res.moveToNext();
        }
        res.close();
        return array_list;
    }

    public ArrayList<Integer> getAllCredit(ArrayList<Integer> tipsIds) {
        ArrayList<Integer> array_list = new ArrayList<>();
        Log.d("TEST", "getAllCredit: " + tipsIds);
        String tipsIdsString = tipsIds.stream().map(Object::toString).collect(Collectors.joining(", "));

        Log.d("TEST", "getAllCredit: " + tipsIdsString);
        //hp = new HashMap();
        SQLiteDatabase pizza_driver_db = this.getReadableDatabase();
        Cursor res =  pizza_driver_db.rawQuery( "SELECT * FROM " + TIPS_TABLE + " WHERE " + TIP_ID + " IN (" + tipsIdsString + ") AND " + CASH + " = " + 0, null);
        res.moveToFirst();
        while(!res.isAfterLast()){
            array_list.add(Integer.parseInt(res.getString(res.getColumnIndex(TIP_ID))));
            res.moveToNext();
        }
        res.close();
        return array_list;
    }

    public ArrayList<Integer> getAllCash(ArrayList<Integer> tipsIds) {
        ArrayList<Integer> array_list = new ArrayList<>();
        Log.d("TEST", "getAllCash: " + tipsIds);
        String tipsIdsString = tipsIds.stream().map(Object::toString).collect(Collectors.joining(", "));

        Log.d("TEST", "getAllCash: " + tipsIdsString);
        //hp = new HashMap();
        SQLiteDatabase pizza_driver_db = this.getReadableDatabase();
        Cursor res =  pizza_driver_db.rawQuery( "SELECT * FROM " + TIPS_TABLE + " WHERE " + TIP_ID + " IN (" + tipsIdsString + ") AND " + CASH + " = " + 1, null);
        res.moveToFirst();
        while(!res.isAfterLast()){
            array_list.add(Integer.parseInt(res.getString(res.getColumnIndex(TIP_ID))));
            res.moveToNext();
        }
        res.close();
        return array_list;
    }

    public ArrayList<Integer> getAllOrdersPerLocationId(ArrayList<Integer> ordersIds, String locationId) {
        ArrayList<Integer> array_list = new ArrayList<>();
        String ordersIdsString = ordersIds.stream().map(Object::toString).collect(Collectors.joining(", "));

        //hp = new HashMap();
        SQLiteDatabase pizza_driver_db = this.getReadableDatabase();
        Cursor res =  pizza_driver_db.rawQuery( "SELECT * FROM " + ORDERS_TABLE + " WHERE " + ORDER_ID + " IN (" + ordersIdsString + ") AND " + LOCATION_ID + " = " + locationId, null);
        res.moveToFirst();
        while(!res.isAfterLast()){
            array_list.add(Integer.parseInt(res.getString(res.getColumnIndex(ORDER_ID))));
            res.moveToNext();
        }
        res.close();
        return array_list;
    }

    public ArrayList<Integer> getAllCashOrders(ArrayList<Integer> tipsCashIds) {
        ArrayList<Integer> array_list = new ArrayList<>();

        String tipsIdsString = tipsCashIds.stream().map(Object::toString).collect(Collectors.joining(", "));

        SQLiteDatabase pizza_driver_db = this.getReadableDatabase();
        Cursor res =  pizza_driver_db.rawQuery( "SELECT * FROM " + CASH_ORDERS_TABLE + " WHERE " + TIP_ID + " IN (" + tipsIdsString + ")", null);
        res.moveToFirst();
        while(!res.isAfterLast()){
            array_list.add(Integer.parseInt(res.getString(res.getColumnIndex(CASH_ORDER_ID))));
            res.moveToNext();
        }
        res.close();
        return array_list;
    }

    public Cursor getTipDataPerTypeAndCashBool(ArrayList<Integer> tipsIds, String Type, String TipCashBool){
        String tipsIdsString = tipsIds.stream().map(Object::toString).collect(Collectors.joining(", "));
        SQLiteDatabase pizza_driver_db = this.getReadableDatabase();
        Cursor res;
        if (TipCashBool.equals("*")) {
            res = pizza_driver_db.rawQuery("SELECT * FROM " + TIPS_TABLE + " WHERE " + TIP_ID + " IN (" + tipsIdsString + ")" + " AND " + TYPE + " = '" + Type + "'", null);
        }
        else {
            res = pizza_driver_db.rawQuery("SELECT * FROM " + TIPS_TABLE + " WHERE " + TIP_ID + " IN (" + tipsIdsString + ")" + " AND " + TYPE + " = '" + Type + "'" + " AND " + CASH + " = " + TipCashBool, null);
        }
        return res;
    }


    public boolean checkAlreadyExist(String workingDate, int orderNumber){
        Cursor cursor = getOrderData(workingDate, orderNumber);
        int count = cursor.getCount();
        Log.d("TEST", "checkAlreadyExist: " + count);
        return count > 0;

    }

    public ArrayList<Integer> getAllLocationAddressIds() {
        ArrayList<Integer> array_list = new ArrayList<>();

        //hp = new HashMap();
        SQLiteDatabase pizza_driver_db = this.getReadableDatabase();
        Cursor res =  pizza_driver_db.rawQuery( "select * from " + LOCATION_ADDRESSES_TABLE, null );
        res.moveToFirst();

        while(!res.isAfterLast()){
            array_list.add(Integer.parseInt(res.getString(res.getColumnIndex(ADDRESS_ID))));
            res.moveToNext();
        }
        res.close();
        return array_list;
    }



}
