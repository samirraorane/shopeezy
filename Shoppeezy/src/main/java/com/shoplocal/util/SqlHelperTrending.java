package com.shoplocal.util;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SqlHelperTrending extends SQLiteOpenHelper {

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "trendingdb";

    // Contacts table name
    private static final String TABLE_LISTINGS = "trendinglistings";

    // Contacts Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_STOREID = "storeid";
    private static final String KEY_TITLE = "title";
    private static final String KEY_PRICE = "price";
    private static final String KEY_DESC = "description";
    private static final String KEY_IMG = "image";
    private static final String KEY_VOTES = "votes";

    public SqlHelperTrending(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_POCKET_TABLE = "CREATE TABLE " + TABLE_LISTINGS + "("
                + KEY_ID + " INTEGER PRIMARY KEY,"
                + KEY_STOREID + " TEXT,"
                + KEY_TITLE + " TEXT,"
                + KEY_PRICE + " TEXT,"
                + KEY_DESC + " TEXT,"
                + KEY_IMG + " TEXT,"
                + KEY_VOTES + " TEXT" + ")";
        db.execSQL(CREATE_POCKET_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LISTINGS);
        // Create tables again
        onCreate(db);
    }

    public void addTrendEntry(TrendingEntry trend) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_ID, trend.getId()); // Contact Name
        values.put(KEY_STOREID, trend.getStoreId());
        values.put(KEY_TITLE, trend.getTitle());
        values.put(KEY_PRICE, trend.getPrice());
        values.put(KEY_DESC, trend.getDescription());
        values.put(KEY_IMG, trend.getImage());
        values.put(KEY_VOTES, trend.getTrendVote());


        // Inserting Row
        db.insert(TABLE_LISTINGS, null, values);
        db.close(); // Closing database connection
    }

    // Getting single contact
    public int getTrendListing(String id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_LISTINGS, new String[] { KEY_ID,
                KEY_STOREID,KEY_TITLE,KEY_PRICE,KEY_DESC, KEY_IMG, KEY_VOTES }, KEY_ID + "=?",
                new String[] { id }, null, null, null, null);

        TrendingEntry trend = null;
        try{
            if (cursor != null){
                cursor.moveToFirst();

                return cursor.getInt(6);
            }

        }catch(Exception e){
        }
        return -1;
    }

    // Updating single contact
    public void updateTrending(TrendingEntry trend) {
        SQLiteDatabase db = this.getWritableDatabase();

         if(this.getTrendListing(trend.getId()) != -1){
            ContentValues values = new ContentValues();
            values.put(KEY_ID, trend.getId()); // Contact Name
            values.put(KEY_STOREID, trend.getStoreId());
            values.put(KEY_TITLE, trend.getTitle());
            values.put(KEY_PRICE, trend.getPrice());
            values.put(KEY_DESC, trend.getDescription());
            values.put(KEY_IMG, trend.getImage());
            values.put(KEY_VOTES, trend.getTrendVote());


            // updating row
            db.update(TABLE_LISTINGS, values, KEY_ID + " = ?",
                    new String[] { String.valueOf(trend.getId()) });
        }else{
             this.addTrendEntry(trend);
        }
    }

    // Getting All Contacts
    public List<TrendingEntry> getAllTrendingEntries() {
        List<TrendingEntry> trendList = new ArrayList<TrendingEntry>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_LISTINGS + " ORDER BY " + KEY_VOTES + " DESC";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                TrendingEntry trend = new TrendingEntry(cursor.getString(0),
                        cursor.getString(1), cursor.getString(2), cursor.getString(3),
                        cursor.getString(4), cursor.getString(5), cursor.getInt(6));
                // Adding contact to list
                trendList.add(trend);
            } while (cursor.moveToNext());
        }

        // return contact list
        return trendList;
    }

}