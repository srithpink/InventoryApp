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
    private InventoryDbHelper mDbHelper;
    EditText mPnameEditText;
    EditText mPquantityEditText;
    EditText mPprice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        mDbHelper = new InventoryDbHelper(this);
        setContentView(R.layout.activity_insert);

        Intent intent = getIntent();
        Uri currentStockUri =intent.getData();

        if(currentStockUri == null)
        {
            setTitle("Add a product");
        }
        else
        {
            setTitle("Edit a product");
        }
        mPnameEditText = (EditText)findViewById(R.id.nameEdit);
        mPquantityEditText = (EditText)findViewById(R.id.quantEdit);
        mPprice = (EditText)findViewById(R.id.priceEdit);

//       savePet();
        Button inButton = (Button)findViewById(R.id.inButton);
        inButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                savePet();
                finish();
                //displayDatabaseInfo();
            }
        });



    }


    private void savePet()
    {
        String pNameString = mPnameEditText.getText().toString().trim();
        String pQuantitySring = mPquantityEditText.getText().toString().trim();
        String pPriceString = mPprice.getText().toString().trim();
        int quantity = Integer.parseInt(pQuantitySring);
        int price = Integer.parseInt(pPriceString);
        InventoryDbHelper mDbHelper = new InventoryDbHelper(this);

        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(InventoryEntry.COLUMN_PRODUCT_NAME, pNameString);
        values.put(InventoryEntry.COLUMN_QUANTITY, quantity);
        values.put(InventoryEntry.COLUMN_PRICE, price);
        // Insert a new pet into the provider, returning the content URI for the new pet.
        Uri newUri = getContentResolver().insert(InventoryEntry.CONTENT_URI, values);

        // Show a toast message depending on whether or not the insertion was successful
        if (newUri == null) {
            // If the new content URI is null, then there was an error with insertion.
            Toast.makeText(this, "inserting failed",
                    Toast.LENGTH_SHORT).show();
        } else {
            // Otherwise, the insertion was successful and we can display a toast.
            Toast.makeText(this, "inserting successful",
                    Toast.LENGTH_SHORT).show();
        }
        }




    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
       return null;
    }


    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}
