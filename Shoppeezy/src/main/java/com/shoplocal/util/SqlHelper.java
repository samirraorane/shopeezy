package com.shoplocal.util;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SqlHelper extends SQLiteOpenHelper {

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "pocketdb";

    // Contacts table name
    private static final String TABLE_LISTINGS = "listings";

    // Contacts Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_STOREID = "storeid";
    private static final String KEY_TITLE = "title";
    private static final String KEY_PRICE = "price";
    private static final String KEY_DESC = "description";

    public SqlHelper(Context context) {
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
                + KEY_DESC + " TEXT" + ")";
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

    public void addPocketEntry(PocketEntry pocket) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_ID, pocket.getId()); // Contact Name
        values.put(KEY_STOREID, pocket.getStoreId());
        values.put(KEY_TITLE, pocket.getTitle());
        values.put(KEY_PRICE, pocket.getPrice());
        values.put(KEY_DESC, pocket.getDescription());

        // Inserting Row
        db.insert(TABLE_LISTINGS, null, values);
        db.close(); // Closing database connection
    }

    // Getting single contact
    PocketEntry getPocketListing(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_LISTINGS, new String[] { KEY_ID,
                KEY_ID, KEY_STOREID,KEY_TITLE,KEY_PRICE,KEY_DESC }, KEY_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        PocketEntry pocket = new PocketEntry(cursor.getString(0),
                cursor.getString(1), cursor.getString(2), cursor.getString(3),
                cursor.getString(4));

        return pocket;
    }

}