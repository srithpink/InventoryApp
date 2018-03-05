package com.example.tharini.inventoryapp.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import com.example.tharini.inventoryapp.data.InventoryContract.InventoryEntry;

/**
 * Created by Tharini on 25-02-2018.
 */

public class InventoryDbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "inventory.db";

    private static final int DATABASE_VERSION = 1;


    public InventoryDbHelper(Context context)
    {
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
       String SQL__CREATE_STOCK_TABLE = "CREATE TABLE " + InventoryEntry.TABLE_NAME + "("
               + InventoryEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
               + InventoryEntry.COLUMN_PRODUCT_NAME + " TEXT, "
               + InventoryEntry.COLUMN_QUANTITY + " INTEGER NOT NULL, "
               + InventoryEntry.COLUMN_PRICE + " INTERGER NOT NULL DEFAULT 0);";
       db.execSQL(SQL__CREATE_STOCK_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
