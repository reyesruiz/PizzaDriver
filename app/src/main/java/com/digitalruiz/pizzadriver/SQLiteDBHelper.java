package com.digitalruiz.pizzadriver;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import java.util.stream.Collectors;

public class SQLiteDBHelper extends SQLiteOpenHelper {
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
    public static final String BACKUP_LOCATIONS_TABLE = "BackupLocations";
    //Tips Table
    public static final String TIPS_TABLE = "Tips";
    //public static final String RATE = "Rate";
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
    //Date
    public static final String BUSINESS_DAY_TABLE = "BusinessDay";
    public static final String BUSINESS_DAY_ID = "BusinessDayId";
    public static final String BUSINESS_DAY_DATE = "Date";
    public static final String BACKUP_BUSINESS_DAY_TABLE = "BackupBusinessDay";

    //Rate
    public static final String RATE_TABLE = "Rate";
    public static final String RATE_ID = "RateId";
    public static final String RATE = "Rate";
    public static final String BACKUP_RATE_TABLE = "BackupRate";

    //Active Business Day Table
    public static final String ACTIVE_BUSINESS_DAY_TABLE = "ActiveBusinessDay";
    public static final String ACTIVE_BUSINESS_DAY_ID = "ActiveBusinessDayId";
    private static final int DATABASE_VERSION = 7;
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
                NAME + " TEXT NOT NULL " +
                ")"
        );

        ContentValues contentValues = new ContentValues();
        contentValues.put(NAME, "Tracy");
        pizza_driver_db.insert(LOCATIONS_TABLE, null, contentValues);

        contentValues.clear();
        contentValues.put(NAME, "Mountain House");
        pizza_driver_db.insert(LOCATIONS_TABLE, null, contentValues);

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

        pizza_driver_db.execSQL("CREATE TABLE " + BUSINESS_DAY_TABLE + " (" +
                BUSINESS_DAY_ID + " INTEGER NOT NULL UNIQUE PRIMARY KEY AUTOINCREMENT, " +
                BUSINESS_DAY_DATE + " TEXT NOT NULL UNIQUE " +
                ")"
        );

        pizza_driver_db.execSQL("CREATE TABLE " + RATE_TABLE + " (" +
                RATE_ID + " INTEGER NOT NULL UNIQUE PRIMARY KEY AUTOINCREMENT, " +
                BUSINESS_DAY_ID + " INTEGER NOT NULL, " +
                LOCATION_ID + " INTEGER NOT NULL, " +
                RATE + " REAL NOT NULL, " +
                "UNIQUE (" + RATE_ID + "," + BUSINESS_DAY_ID + "," + LOCATION_ID + ")" +
                ")"
        );

        pizza_driver_db.execSQL("CREATE TABLE " + ACTIVE_BUSINESS_DAY_TABLE + " (" +
                ACTIVE_BUSINESS_DAY_ID + " INTEGER NOT NULL UNIQUE PRIMARY KEY AUTOINCREMENT, " +
                BUSINESS_DAY_ID + " INTEGER " +
                ")"
        );


    }

    @Override
    public void onUpgrade(SQLiteDatabase pizza_driver_db, int oldVersion, int newVersion) {

        //App support only from DB version 7 and up.
        if (newVersion == 7) {
            pizza_driver_db.execSQL("CREATE TABLE " + BACKUP_BUSINESS_DAY_TABLE + " (" +
                    BUSINESS_DAY_ID + " INTEGER NOT NULL UNIQUE PRIMARY KEY AUTOINCREMENT, " +
                    BUSINESS_DAY_DATE + " TEXT NOT NULL UNIQUE " +
                    ")"
            );
            pizza_driver_db.execSQL("CREATE TABLE " + BACKUP_RATE_TABLE + " (" +
                    RATE_ID + " INTEGER NOT NULL UNIQUE PRIMARY KEY AUTOINCREMENT, " +
                    BUSINESS_DAY_ID + " INTEGER NOT NULL, " +
                    LOCATION_ID + " INTEGER NOT NULL, " +
                    RATE + " REAL NOT NULL, " +
                    "UNIQUE (" + RATE_ID + "," + BUSINESS_DAY_ID + "," + LOCATION_ID + ")" +
                    ")"
            );

            pizza_driver_db.execSQL("INSERT INTO " + BACKUP_BUSINESS_DAY_TABLE + " SELECT " + BUSINESS_DAY_ID + "," + DATE + " FROM " + BUSINESS_DAY_TABLE);
            pizza_driver_db.execSQL("INSERT INTO " + BACKUP_RATE_TABLE + " SELECT " + RATE_ID + "," + BUSINESS_DAY_ID + "," + LOCATION_ID + "," + RATE + " FROM " + RATE_TABLE);

            pizza_driver_db.execSQL("DROP TABLE " + BUSINESS_DAY_TABLE);
            pizza_driver_db.execSQL("DROP TABLE " + RATE_TABLE);

            pizza_driver_db.execSQL("CREATE TABLE " + BUSINESS_DAY_TABLE + " (" +
                    BUSINESS_DAY_ID + " INTEGER NOT NULL UNIQUE PRIMARY KEY AUTOINCREMENT, " +
                    BUSINESS_DAY_DATE + " TEXT NOT NULL UNIQUE " +
                    ")"
            );
            pizza_driver_db.execSQL("CREATE TABLE " + RATE_TABLE + " (" +
                    RATE_ID + " INTEGER NOT NULL UNIQUE PRIMARY KEY AUTOINCREMENT, " +
                    BUSINESS_DAY_ID + " INTEGER NOT NULL, " +
                    LOCATION_ID + " INTEGER NOT NULL, " +
                    RATE + " REAL NOT NULL, " +
                    "UNIQUE (" + RATE_ID + "," + BUSINESS_DAY_ID + "," + LOCATION_ID + ")" +
                    ")"
            );

            pizza_driver_db.execSQL("INSERT INTO " + BUSINESS_DAY_TABLE + " SELECT " + BUSINESS_DAY_ID + "," + DATE + " FROM " + BACKUP_BUSINESS_DAY_TABLE);
            pizza_driver_db.execSQL("INSERT INTO " + RATE_TABLE + " SELECT " + RATE_ID + "," + BUSINESS_DAY_ID + "," + LOCATION_ID + "," + RATE + " FROM " + BACKUP_RATE_TABLE);

            pizza_driver_db.execSQL("DROP TABLE " + BACKUP_BUSINESS_DAY_TABLE);
            pizza_driver_db.execSQL("DROP TABLE " + BACKUP_RATE_TABLE);
        }
    }

    public void deleteData() {
        SQLiteDatabase pizza_driver_db = this.getWritableDatabase();
        pizza_driver_db.execSQL("DELETE FROM " + ORDERS_TABLE);
        pizza_driver_db.execSQL("DELETE FROM " + TIPS_TABLE);
        pizza_driver_db.execSQL("DELETE FROM " + CASH_ORDERS_TABLE);
        pizza_driver_db.execSQL("DELETE FROM " + LOCATIONS_TABLE);
        pizza_driver_db.execSQL("DELETE FROM " + LOCATION_ADDRESSES_TABLE);
        pizza_driver_db.execSQL("DELETE FROM " + BUSINESS_DAY_TABLE);
        pizza_driver_db.execSQL("DELETE FROM " + RATE_TABLE);
        Log.d("TEST", "deleteData: ");
    }

    public long insertOrder(String WorkingDate, Integer OrderNumber, Integer LocationID) {
        SQLiteDatabase pizza_driver_db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DATE, WorkingDate);
        contentValues.put(ORDER_NUMBER, OrderNumber);
        contentValues.put(LOCATION_ID, LocationID);
        long rowInserted = pizza_driver_db.insert(ORDERS_TABLE, null, contentValues);
        Log.d("TEST", "insertOrder: " + rowInserted);
        return rowInserted;
    }

    public long insertTip(String Amount, String Type, Integer TipCashBool, long OrderId) {
        SQLiteDatabase pizza_driver_db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(AMOUNT, Amount);
        contentValues.put(TYPE, Type);
        contentValues.put(CASH, TipCashBool);
        contentValues.put(ORDER_ID, OrderId);
        long rowInserted = pizza_driver_db.insert(TIPS_TABLE, null, contentValues);
        Log.d("TEST", "insertTip: " + rowInserted);
        return rowInserted;
    }

    public long insertCashOrder(String Total, String Received, long TipId) {
        Log.d("TEST", "insertCashOrder: " + Total + " " + Received);
        SQLiteDatabase pizza_driver_db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(TOTAL, Total);
        contentValues.put(RECEIVED, Received);
        contentValues.put(TIP_ID, TipId);
        long rowInserted = pizza_driver_db.insert(CASH_ORDERS_TABLE, null, contentValues);
        Log.d("TEST", "insertCash: " + rowInserted);
        return rowInserted;
    }


    public long insertLocationAddress(String GoogleLocationId, String AddressName, String Address, String
            AddressComponents) {
        SQLiteDatabase pizza_driver_db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(GOOGLE_LOCATION_ID, GoogleLocationId);
        contentValues.put(ADDRESS_NAME, AddressName);
        contentValues.put(ADDRESS, Address);
        contentValues.put(ADDRESS_COMPONENTS, AddressComponents);
        long rowInserted = pizza_driver_db.insert(LOCATION_ADDRESSES_TABLE, null, contentValues);
        Log.d("TEST", "insertLocationAddress: " + rowInserted);
        return rowInserted;
    }

    public long insertLocationNote(int AddressId, int SubdivisionId, String Note, String DateAdded) {
        SQLiteDatabase pizza_driver_db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(ADDRESS_ID, AddressId);
        if (SubdivisionId > 0) {
            contentValues.put(SUBDIVISION_ID, SubdivisionId);
        }
        contentValues.put(NOTE, Note);
        contentValues.put(DATE_ADDED, DateAdded);
        return pizza_driver_db.insert(LOCATION_ADDRESS_NOTES_TABLE, null, contentValues);
    }

    public long insertSubDivisionAddress(int AddressId, String Subdivision) {
        SQLiteDatabase pizza_driver_db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(ADDRESS_ID, AddressId);
        contentValues.put(SUBDIVISION, Subdivision);
        return pizza_driver_db.insert(LOCATION_ADDRESS_SUBDIVISION_TABLE, null, contentValues);
    }

    public long insertDate(String BusinessDayDate) {
        SQLiteDatabase pizza_driver_db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(BUSINESS_DAY_DATE, BusinessDayDate);
        return pizza_driver_db.insert(BUSINESS_DAY_TABLE, null, contentValues);
    }

    public void insertRate(long BusinessDayId, Integer LocationId, String Rate) {
        SQLiteDatabase pizza_driver_db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(BUSINESS_DAY_ID, BusinessDayId);
        contentValues.put(LOCATION_ID, LocationId);
        contentValues.put(RATE, Rate);
        pizza_driver_db.insert(RATE_TABLE, null, contentValues);
    }

    public void insertActiveBusinessDay(Long BusinessDayId) {
        SQLiteDatabase pizza_driver_db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(BUSINESS_DAY_ID, BusinessDayId);
        pizza_driver_db.insert(ACTIVE_BUSINESS_DAY_TABLE, null, contentValues);
    }

    public Cursor getOrderData(String WorkingDate, int orderNumber) {
        SQLiteDatabase pizza_driver_db = this.getReadableDatabase();
        String sql = "SELECT * FROM " + ORDERS_TABLE + " WHERE " + ORDER_NUMBER + " = " + orderNumber + " AND " + DATE + " = " + "\"" + WorkingDate + "\"";
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

    public Cursor getNoteData(int noteId) {
        SQLiteDatabase pizza_driver_db = this.getReadableDatabase();
        String sql = "SELECT * FROM " + LOCATION_ADDRESS_NOTES_TABLE + " WHERE " + NOTE_ID + " = " + noteId;
        return pizza_driver_db.rawQuery(sql, null, null);
    }

    public int getAddressIdByLocationId(String googleLocationId) {
        SQLiteDatabase pizza_driver_db = this.getReadableDatabase();
        String sql = "SELECT " + ADDRESS_ID + " FROM " + LOCATION_ADDRESSES_TABLE + " WHERE " + GOOGLE_LOCATION_ID + " = " + "'" + googleLocationId + "'";
        Cursor cursor = pizza_driver_db.rawQuery(sql, null);
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            int AddressID = cursor.getInt(cursor.getColumnIndex(ADDRESS_ID));
            Log.d("TAG", "getAddressIdByLocationId: " + AddressID);
            cursor.close();
            return AddressID;
        } else {
            cursor.close();
            return 0;
        }
    }

    public String getPlaceIdByAddressId(int AddressId) {
        SQLiteDatabase pizza_driver_db = this.getReadableDatabase();
        String sql = "SELECT " + GOOGLE_LOCATION_ID + " FROM " + LOCATION_ADDRESSES_TABLE + " WHERE " + ADDRESS_ID + " = " + "'" + AddressId + "'";
        Cursor cursor = pizza_driver_db.rawQuery(sql, null);
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            String PlaceId = cursor.getString(cursor.getColumnIndex(GOOGLE_LOCATION_ID));
            cursor.close();
            return PlaceId;
        } else {
            cursor.close();
            return "0";
        }
    }

    public ArrayList<Integer> getSubDivisionsByAddressID(int AddressID) {
        ArrayList<Integer> array_list = new ArrayList<>();
        SQLiteDatabase pizza_driver_db = this.getReadableDatabase();
        String sql = "SELECT " + SUBDIVISION_ID + " FROM " + LOCATION_ADDRESS_SUBDIVISION_TABLE + " WHERE " + ADDRESS_ID + " = " + "'" + AddressID + "'";
        Cursor res = pizza_driver_db.rawQuery(sql, null);
        res.moveToFirst();
        while (!res.isAfterLast()) {
            array_list.add(Integer.parseInt(res.getString(res.getColumnIndex(SUBDIVISION_ID))));
            res.moveToNext();
        }
        res.close();
        return array_list;

    }

    public String getSubDivisionBySubId(int SubdivisionID) {
        SQLiteDatabase pizza_driver_db = this.getReadableDatabase();
        String sql = "SELECT " + SUBDIVISION + " FROM " + LOCATION_ADDRESS_SUBDIVISION_TABLE + " WHERE " + SUBDIVISION_ID + " = " + "'" + SubdivisionID + "'";
        Cursor res = pizza_driver_db.rawQuery(sql, null);
        res.moveToFirst();
        String subdivision = res.getString(res.getColumnIndex(SUBDIVISION));
        res.close();
        return subdivision;
    }

    public String getRate(long BusinessDayId, long LocationId) {
        SQLiteDatabase pizza_driver_db = this.getReadableDatabase();
        String sql = "SELECT " + RATE + " FROM " + RATE_TABLE + " WHERE " + BUSINESS_DAY_ID + " = " + "'" + BusinessDayId + "'" + " AND " + LOCATION_ID + " = " + "'" + LocationId + "'";
        Cursor res = pizza_driver_db.rawQuery(sql, null);
        res.moveToFirst();
        String rate = res.getString(res.getColumnIndex(RATE));
        res.close();
        return rate;
    }


    public int updateOrder(long OrderId, String WorkingDate, Integer OrderNumber, Integer LocationID) {
        SQLiteDatabase pizza_driver_db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DATE, WorkingDate);
        contentValues.put(ORDER_NUMBER, OrderNumber);
        contentValues.put(LOCATION_ID, LocationID);
        int rowUpdated = pizza_driver_db.update(ORDERS_TABLE, contentValues, ORDER_ID + " = ? ", new String[]{String.valueOf(OrderId)});
        Log.d("TEST", "rowUpdated: " + rowUpdated);
        return rowUpdated;
    }

    public int updateTip(long TipId, String Amount, String Type, Integer TipCashBool, long OrderId) {
        SQLiteDatabase pizza_driver_db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(AMOUNT, Amount);
        contentValues.put(TYPE, Type);
        contentValues.put(CASH, TipCashBool);
        contentValues.put(ORDER_ID, OrderId);
        int rowUpdated = pizza_driver_db.update(TIPS_TABLE, contentValues, TIP_ID + " = ? ", new String[]{String.valueOf(TipId)});
        Log.d("TEST", "rowUpdated: " + rowUpdated);
        return rowUpdated;
    }

    public void updateCashOrder(long CashOrderId, String Total, String Received, long TipId) {
        SQLiteDatabase pizza_driver_db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(TOTAL, Total);
        contentValues.put(RECEIVED, Received);
        contentValues.put(TIP_ID, TipId);
        int rowUpdated = pizza_driver_db.update(CASH_ORDERS_TABLE, contentValues, CASH_ORDER_ID + " = ? ", new String[]{String.valueOf(CashOrderId)});
        Log.d("TEST", "rowUpdated: " + rowUpdated);
    }


    public boolean updateOrderNumber(String WorkingDate, Integer OrderNumber, Integer OrderId) {
        SQLiteDatabase pizza_driver_db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(ORDER_NUMBER, OrderNumber);
        boolean exists = checkAlreadyExist(WorkingDate, OrderNumber);
        if (exists) {
            return false;
        } else {
            int rowUpdated = pizza_driver_db.update(ORDERS_TABLE, contentValues, ORDER_ID + " = ? ", new String[]{(String.valueOf(OrderId))});
            Log.v("Test", "row updated " + rowUpdated);
            return rowUpdated != -1;
        }
    }

    public ArrayList<Integer> getAllTipsPerOrderId(Integer orderId) {
        ArrayList<Integer> array_list = new ArrayList<>();
        //hp = new HashMap();
        SQLiteDatabase pizza_driver_db = this.getReadableDatabase();
        Cursor res = pizza_driver_db.rawQuery("select * from " + TIPS_TABLE + " where " + ORDER_ID + " = " + orderId, null);

        res.moveToFirst();

        while (!res.isAfterLast()) {
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
        Cursor res = pizza_driver_db.rawQuery("select * from " + CASH_ORDERS_TABLE + " where " + TIP_ID + " = " + tipId, null);
        res.moveToFirst();

        while (!res.isAfterLast()) {
            array_list.add(Integer.parseInt(res.getString(res.getColumnIndex(CASH_ORDER_ID))));
            res.moveToNext();
        }
        res.close();
        return array_list;
    }

    public ArrayList<Integer> getNoteIds(int AddressId, int SubId) {
        ArrayList<Integer> array_list = new ArrayList<>();
        //hp = new HashMap();
        SQLiteDatabase pizza_driver_db = this.getReadableDatabase();
        String sql;
        if (SubId == 0) {
            sql = "SELECT " + NOTE_ID + " FROM " + LOCATION_ADDRESS_NOTES_TABLE + " WHERE " + ADDRESS_ID + " = " + "'" + AddressId + "'" + " AND " + SUBDIVISION_ID + " is null";
        } else {
            sql = "SELECT " + NOTE_ID + " FROM " + LOCATION_ADDRESS_NOTES_TABLE + " WHERE " + ADDRESS_ID + " = " + "'" + AddressId + "'" + " AND " + SUBDIVISION_ID + " = " + "'" + SubId + "'";
        }

        Cursor res = pizza_driver_db.rawQuery(sql, null);

        res.moveToFirst();

        while (!res.isAfterLast()) {
            array_list.add(Integer.parseInt(res.getString(res.getColumnIndex(NOTE_ID))));
            res.moveToNext();
        }
        res.close();
        return array_list;
    }

    public void deleteCashOrder(Integer cashOrderId) {
        SQLiteDatabase pizza_driver_db = this.getWritableDatabase();
        int row_deleted = pizza_driver_db.delete(CASH_ORDERS_TABLE,
                CASH_ORDER_ID + " = ? ",
                new String[]{Integer.toString(cashOrderId)});
        Log.v("Test", "row deleted " + row_deleted);
    }

    public void deleteTip(Integer tipId) {
        SQLiteDatabase pizza_driver_db = this.getWritableDatabase();
        int row_deleted = pizza_driver_db.delete(TIPS_TABLE,
                TIP_ID + " = ? ",
                new String[]{Integer.toString(tipId)});
        Log.v("Test", "row deleted " + row_deleted);
    }

    public boolean deleteOrder(Integer orderId) {
        SQLiteDatabase pizza_driver_db = this.getWritableDatabase();
        int row_deleted = pizza_driver_db.delete(ORDERS_TABLE,
                ORDER_ID + " = ? ",
                new String[]{Integer.toString(orderId)});
        Log.v("Test", "row deleted " + row_deleted);
        return row_deleted == 1;
    }

    public boolean deleteLocationId(Integer AddressId) {
        SQLiteDatabase pizza_driver_db = this.getWritableDatabase();
        int row_deleted = pizza_driver_db.delete(LOCATION_ADDRESSES_TABLE,
                ADDRESS_ID + " = ? ",
                new String[]{Integer.toString(AddressId)});
        Log.v("Test", "row deleted " + row_deleted);
        return row_deleted == 1;
    }

    public boolean deleteNote(Integer NoteId) {
        SQLiteDatabase pizza_driver_db = this.getWritableDatabase();
        int row_deleted = pizza_driver_db.delete(LOCATION_ADDRESS_NOTES_TABLE,
                NOTE_ID + " = ? ",
                new String[]{Integer.toString(NoteId)});
        Log.v("Test", "row deleted " + row_deleted);
        return row_deleted == 1;
    }


    public ArrayList<Integer> getAllOrders(String workingDate) {
        ArrayList<Integer> array_list = new ArrayList<>();

        //hp = new HashMap();
        SQLiteDatabase pizza_driver_db = this.getReadableDatabase();
        Cursor res = pizza_driver_db.rawQuery("select * from " + ORDERS_TABLE + " where " + DATE + " = " + "\"" + workingDate + "\"", null);
        res.moveToFirst();

        while (!res.isAfterLast()) {
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
        Cursor res = pizza_driver_db.rawQuery("SELECT * FROM " + TIPS_TABLE + " WHERE " + ORDER_ID + " IN (" + ordersIdsString + ")", null);
        res.moveToFirst();
        while (!res.isAfterLast()) {
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
        Cursor res = pizza_driver_db.rawQuery("SELECT * FROM " + TIPS_TABLE + " WHERE " + TIP_ID + " IN (" + tipsIdsString + ") AND " + CASH + " = " + 0, null);
        res.moveToFirst();
        while (!res.isAfterLast()) {
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
        Cursor res = pizza_driver_db.rawQuery("SELECT * FROM " + TIPS_TABLE + " WHERE " + TIP_ID + " IN (" + tipsIdsString + ") AND " + CASH + " = " + 1, null);
        res.moveToFirst();
        while (!res.isAfterLast()) {
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
        Cursor res = pizza_driver_db.rawQuery("SELECT * FROM " + ORDERS_TABLE + " WHERE " + ORDER_ID + " IN (" + ordersIdsString + ") AND " + LOCATION_ID + " = " + locationId, null);
        res.moveToFirst();
        while (!res.isAfterLast()) {
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
        Cursor res = pizza_driver_db.rawQuery("SELECT * FROM " + CASH_ORDERS_TABLE + " WHERE " + TIP_ID + " IN (" + tipsIdsString + ")", null);
        res.moveToFirst();
        while (!res.isAfterLast()) {
            array_list.add(Integer.parseInt(res.getString(res.getColumnIndex(CASH_ORDER_ID))));
            res.moveToNext();
        }
        res.close();
        return array_list;
    }

    public Cursor getTipDataPerTypeAndCashBool(ArrayList<Integer> tipsIds, String Type, String TipCashBool) {
        String tipsIdsString = tipsIds.stream().map(Object::toString).collect(Collectors.joining(", "));
        SQLiteDatabase pizza_driver_db = this.getReadableDatabase();
        Cursor res;
        if (TipCashBool.equals("*")) {
            res = pizza_driver_db.rawQuery("SELECT * FROM " + TIPS_TABLE + " WHERE " + TIP_ID + " IN (" + tipsIdsString + ")" + " AND " + TYPE + " = '" + Type + "'", null);
        } else {
            res = pizza_driver_db.rawQuery("SELECT * FROM " + TIPS_TABLE + " WHERE " + TIP_ID + " IN (" + tipsIdsString + ")" + " AND " + TYPE + " = '" + Type + "'" + " AND " + CASH + " = " + TipCashBool, null);
        }
        return res;
    }


    public boolean checkAlreadyExist(String workingDate, int orderNumber) {
        Cursor cursor = getOrderData(workingDate, orderNumber);
        int count = cursor.getCount();
        Log.d("TEST", "checkAlreadyExist: " + count);
        return count > 0;
    }

    public long getBusinessDay(String BusinessDayDate) {
        SQLiteDatabase pizza_driver_db = this.getReadableDatabase();
        Cursor res = pizza_driver_db.rawQuery("SELECT " + BUSINESS_DAY_ID + " FROM " + BUSINESS_DAY_TABLE + " WHERE " + BUSINESS_DAY_DATE + " = '" + BusinessDayDate + "'", null);
        if (res.getCount() > 0) {
            res.moveToFirst();
            int businessDayId = res.getInt(res.getColumnIndex(BUSINESS_DAY_ID));
            res.close();
            return businessDayId;
        } else {
            res.close();
            return 0;
        }
    }

    public String getBusinessDayById(long BusinessDayId) {
        SQLiteDatabase pizza_driver_db = this.getReadableDatabase();
        Cursor res = pizza_driver_db.rawQuery("SELECT " + BUSINESS_DAY_DATE + " FROM " + BUSINESS_DAY_TABLE + " WHERE " + BUSINESS_DAY_ID + " = " + BusinessDayId, null);
        res.moveToFirst();
        if (res.getCount() > 0) {
            res.moveToFirst();
            String businessDay = res.getString(res.getColumnIndex(BUSINESS_DAY_DATE));
            res.close();
            return businessDay;
        } else {
            res.close();
            return "0";
        }
    }

    public long getActiveBusinessDay() {
        SQLiteDatabase pizza_driver_db = this.getReadableDatabase();
        Cursor res = pizza_driver_db.rawQuery("SELECT " + BUSINESS_DAY_ID + " FROM " + ACTIVE_BUSINESS_DAY_TABLE, null);
        if (res.getCount() > 0) {
            res.moveToLast();
            int businessDayId = res.getInt(res.getColumnIndex(BUSINESS_DAY_ID));
            res.close();
            return businessDayId;
        } else {
            res.close();
            return 0;
        }
    }

    public ArrayList<Integer> getAllLocationAddressIds() {
        ArrayList<Integer> array_list = new ArrayList<>();

        //hp = new HashMap();
        SQLiteDatabase pizza_driver_db = this.getReadableDatabase();
        Cursor res = pizza_driver_db.rawQuery("select * from " + LOCATION_ADDRESSES_TABLE, null);
        res.moveToFirst();

        while (!res.isAfterLast()) {
            array_list.add(Integer.parseInt(res.getString(res.getColumnIndex(ADDRESS_ID))));
            res.moveToNext();
        }
        res.close();
        return array_list;
    }


}
