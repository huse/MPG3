package com.kpr.hus.mpg4;

/**
 * Created by f1 on 1/1/2016.
 */
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.LinkedList;
import java.util.List;

public class MySQLiteHelper extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 1;
    // Database Name
    private static final String DATABASE_NAME = "MpgDB";

    public MySQLiteHelper(Context context, String dbName) {
        super(context, dbName, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // SQL statement to create book table
        String CREATE_BOOK_TABLE = "CREATE TABLE books ( " +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "mpg TEXT, "+
                "fuel TEXT, "+
                "date TEXT, " +
                "distance TEXT, " +
                "price TEXT )";


        // create books table
        db.execSQL(CREATE_BOOK_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older books table if existed
        db.execSQL("DROP TABLE IF EXISTS books");

        // create fresh books table
        this.onCreate(db);
    }
    //---------------------------------------------------------------------

    /**
     * CRUD operations (create "add", read "get", update, delete) book + get all books + delete all books
     */

    // Books table name
    private static final String TABLE_BOOKS = "books";

    // Books Table Columns names
   // private static final String KEY_TITLE = "title";
    private static final String KEY_ID = "id";
    private static final String KEY_MPG = "mpg";
    private static final String KEY_FUEL = "fuel";
    private static final String KEY_DATE = "date";
    private static final String KEY_DISTANCE = "distance";
    private static final String KEY_PRICE = "price";

    private static final String[] COLUMNS = {KEY_ID,KEY_MPG,KEY_FUEL,KEY_DATE,KEY_DISTANCE,KEY_PRICE};

    public void addBook(Data data){
        Log.d("addBook", data.toString());
        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        // 2. create ContentValues to add key "column"/value
        ContentValues values = new ContentValues();
       // values.put(KEY_TITLE,"date");
        values.put(KEY_MPG, data.getMpg()); // get mpg
        values.put(KEY_FUEL, data.getFuel()); // get fuel
        values.put(KEY_DATE, data.getDate()); // get date
        values.put(KEY_DISTANCE, data.getDistance()); // get distance
        values.put(KEY_PRICE, data.getPrice()); // get price
        // 3. insert
        db.insert(TABLE_BOOKS, // table
                null, //nullColumnHack
                values); // key/value -> keys = column names/ values = column values

        // 4. close
        db.close();
    }

    public Data getData(int id){

        // 1. get reference to readable DB
        SQLiteDatabase db = this.getReadableDatabase();

        // 2. build query
        Cursor cursor =
                db.query(TABLE_BOOKS, // a. table
                        COLUMNS, // b. column names
                        " id = ?", // c. selections
                        new String[] { String.valueOf(id) }, // d. selections args
                        null, // e. group by
                        null, // f. having
                        null, // g. order by
                        null); // h. limit

        // 3. if we got results get the first one
        if (cursor != null)
            cursor.moveToFirst();

        // 4. build data object
        Data data = new Data();
        if(cursor != null && cursor.moveToFirst()) {
            data.setId(Integer.parseInt(cursor.getString(0)));

            data.setMpg(cursor.getString(1));
            data.setFuel(cursor.getString(2));
            data.setDate(cursor.getString(3));
            data.setDistance(cursor.getString(4));
            data.setPrice(cursor.getString(5));
        }
        Log.d("getData(" + id + ")", data.toString());

        // 5. return data
        return data;
    }

    // Get All Books
    public List<Data> getAllBooks() {
        List<Data> datas = new LinkedList<Data>();

        // 1. build the query
        String query = "SELECT  * FROM " + TABLE_BOOKS;

        // 2. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();
        //Cursor cursor = db.rawQuery(query, null);
        Cursor cursor = db.query(TABLE_BOOKS, null, null, null, null, null, KEY_ID + " DESC", null);
        // 3. go over each row, build data and add it to list
        Data data = null;
        if (cursor.moveToFirst()) {
            do {
                data = new Data();
                data.setId(Integer.parseInt(cursor.getString(0)));
                data.setMpg(cursor.getString(1));
                data.setFuel(cursor.getString(2));
               // Log.d("HHHHHHHHHHHHHHHHHHHHHH", cursor.getString(2).toString());
                data.setDate(cursor.getString(3));
                data.setDistance(cursor.getString(4));
                data.setPrice(cursor.getString(5));
                // Add data to datas
                datas.add(data);
            } while (cursor.moveToNext());
        }

        Log.d("getAllBooks()", datas.toString());

        // return datas
        return datas;
    }

    // Updating single data
    public int updateBook(Data data) {

        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        // 2. create ContentValues to add key "column"/value
        ContentValues values = new ContentValues();
        values.put("mpg", data.getMpg()); // get mpg
        values.put("fuel", data.getFuel()); // get fuel
        values.put("date", data.getDate());  // get date
        values.put("distance", data.getDistance());  // get distance
        values.put("price", data.getPrice());  // get price
        // 3. updating row
        int i = db.update(TABLE_BOOKS, //table
                values, // column/value
                KEY_ID+" = ?", // selections
                new String[] { String.valueOf(data.getId()) }); //selection args

        // 4. close
        db.close();

        return i;

    }

    // Deleting single data
    public void deleteBook(Data data) {

        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        // 2. delete
        db.delete(TABLE_BOOKS,
                KEY_ID+" = ?",
                new String[] { String.valueOf(data.getId()) });

        // 3. close
        db.close();

        Log.d("deleteBook", data.toString());

    }
}