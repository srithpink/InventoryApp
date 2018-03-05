package com.example.tharini.inventoryapp;

import android.app.LoaderManager;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.tharini.inventoryapp.data.InventoryContract.InventoryEntry;
import com.example.tharini.inventoryapp.data.InventoryDbHelper;

/**
 * Created by Tharini on 25-02-2018.
 */

public class InsertActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final int EXISTING_INVENTORY_LOADER = 0;

    EditText mPnameEditText;
    EditText mPquantityEditText;
    EditText mPprice;
    private Uri mCurrentStockUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert);

        Intent intent = getIntent();
        mCurrentStockUri = intent.getData();

        if (mCurrentStockUri == null) {
            setTitle("Add a product");
        } else {
            setTitle("Edit a product");
            getLoaderManager().initLoader(EXISTING_INVENTORY_LOADER, null, this);

        }
        mPnameEditText = (EditText) findViewById(R.id.nameEdit);
        mPquantityEditText = (EditText) findViewById(R.id.quantEdit);
        mPprice = (EditText) findViewById(R.id.priceEdit);

//       insertStock();
        Button inButton = (Button) findViewById(R.id.inButton);
        inButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insertStock();
                finish();
                //displayDatabaseInfo();
            }
        });



    }


    private void insertStock() {
        String pNameString = mPnameEditText.getText().toString().trim();
        String pQuantitySring = mPquantityEditText.getText().toString().trim();
        String pPriceString = mPprice.getText().toString().trim();
        int quantity = Integer.parseInt(pQuantitySring);
        int price = Integer.parseInt(pPriceString);
        InventoryDbHelper mDbHelper = new InventoryDbHelper(this);


        ContentValues values = new ContentValues();

        values.put(InventoryEntry.COLUMN_PRODUCT_NAME, pNameString);
        values.put(InventoryEntry.COLUMN_QUANTITY, quantity);
        values.put(InventoryEntry.COLUMN_PRICE, price);
        Uri newUri = getContentResolver().insert(InventoryEntry.CONTENT_URI, values);
        if (newUri == null) {
            Toast.makeText(this, "insertion failed",
                    Toast.LENGTH_SHORT).show();
        } else

        {
            // Otherwise, the insertion was successful and we can display a toast.
            Toast.makeText(this, "insertion successful",
                    Toast.LENGTH_SHORT).show();
        }
    }




    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
       String[] projection = {
               InventoryEntry._ID,
               InventoryEntry.COLUMN_PRODUCT_NAME,
               InventoryEntry.COLUMN_QUANTITY,
               InventoryEntry.COLUMN_PRICE };
       return new CursorLoader(this,
               mCurrentStockUri,
               projection,
               null,
               null,
               null);
       }



    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {

        if(cursor == null || cursor.getCount() < 1)
        {
            return;
        }

        if (cursor.moveToFirst())
        {
            int nameColumnIndex = cursor.getColumnIndex(InventoryEntry.COLUMN_PRODUCT_NAME);
            int quantityColumnIndex = cursor.getColumnIndex(InventoryEntry.COLUMN_QUANTITY);
            int priceColumnIndex = cursor.getColumnIndex(InventoryEntry.COLUMN_PRICE);


            String name = cursor.getString(nameColumnIndex);
            int quantity = cursor.getInt(quantityColumnIndex);
            int price = cursor.getInt(priceColumnIndex);


            mPnameEditText.setText(name);
            mPquantityEditText.setText(Integer.toString(quantity));
            mPprice.setText(Integer.toString(price));
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

        mPnameEditText.setText("");
        mPquantityEditText.setText("");
        mPprice.setText("");
    }
}
