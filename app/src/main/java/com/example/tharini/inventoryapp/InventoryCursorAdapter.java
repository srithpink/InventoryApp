package com.example.tharini.inventoryapp;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.example.tharini.inventoryapp.data.InventoryContract.InventoryEntry;

/**
 * Created by Tharini on 05-03-2018.
 */

public class InventoryCursorAdapter extends CursorAdapter{
    public InventoryCursorAdapter(Context context, Cursor c) {
        super(context, c, 0 /* flags */);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.list_item, parent, false);

    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView pNameTextView = (TextView) view.findViewById(R.id.name);
        TextView pQuantityTextView = (TextView) view.findViewById(R.id.quantityL);

        int pNameColumnIndex =cursor.getColumnIndex(InventoryEntry.COLUMN_PRODUCT_NAME);
        int pQuantityColumnIndex = cursor.getColumnIndex(InventoryEntry.COLUMN_QUANTITY);

        String pName =cursor.getString(pNameColumnIndex);
        String pQuantity = cursor.getString(pQuantityColumnIndex);

        pNameTextView.setText(pName);
        pQuantityTextView.setText(pQuantity);

    }
}
