package com.example.tharini.inventoryapp;

import android.app.AlertDialog;
import android.app.LoaderManager;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.tharini.inventoryapp.data.InventoryContract.InventoryEntry;
import com.example.tharini.inventoryapp.data.InventoryDbHelper;

/**
 * Created by Tharini on 25-02-2018.
 */

public class InsertActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final int EXISTING_INVENTORY_LOADER = 1;

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
            ImageButton button = (ImageButton) findViewById(R.id.deleteData);
            button.setVisibility(View.GONE);

        } else {
            setTitle("Edit a product");
            ImageButton button = (ImageButton) findViewById(R.id.deleteData);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showDeleteConfirmationDialog();
                }
            });

            getLoaderManager().initLoader(EXISTING_INVENTORY_LOADER, null, this);

        }
        mPnameEditText = (EditText) findViewById(R.id.nameEdit);
        mPquantityEditText = (EditText) findViewById(R.id.quantEdit);
        mPprice = (EditText) findViewById(R.id.priceEdit);

//       saveStock();
        ImageButton inButton = (ImageButton) findViewById(R.id.inButton);
        inButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveStock();
                finish();
                //displayDatabaseInfo();
            }
        });




    }


    private void saveStock() {
        String pNameString = mPnameEditText.getText().toString().trim();
        String pQuantitySring = mPquantityEditText.getText().toString().trim();
        String pPriceString = mPprice.getText().toString().trim();

        int price = Integer.parseInt(pPriceString);


        int quantity = 0;
        if (!TextUtils.isEmpty(pQuantitySring)) {
            quantity = Integer.parseInt(pQuantitySring);
        }
        ContentValues values = new ContentValues();

        values.put(InventoryEntry.COLUMN_PRODUCT_NAME, pNameString);
        values.put(InventoryEntry.COLUMN_QUANTITY, quantity);
        values.put(InventoryEntry.COLUMN_PRICE, price);

        if (mCurrentStockUri == null) {
            // This is a NEW pet, so insert a new pet into the provider,
            // returning the content URI for the new pet.
            Uri newUri = getContentResolver().insert(InventoryEntry.CONTENT_URI, values);

            // Show a toast message depending on whether or not the insertion was successful.
            if (newUri == null) {
                // If the new content URI is null, then there was an error with insertion.
                Toast.makeText(this, "insertion failed",
                        Toast.LENGTH_SHORT).show();
            } else {
                // Otherwise, the insertion was successful and we can display a toast.
                Toast.makeText(this, "insertion successful",
                        Toast.LENGTH_SHORT).show();
            }
        } else {
            int rowsAffected = getContentResolver().update(mCurrentStockUri, values, null, null);

            if (rowsAffected == 0) {
                Toast.makeText(this, "update failed",
                        Toast.LENGTH_SHORT).show();
            } else {
                // Otherwise, the update was successful and we can display a toast.
                Toast.makeText(this, "update successful",
                        Toast.LENGTH_SHORT).show();

            }

        }

    }


    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        String[] projection = {
                InventoryEntry._ID,
                InventoryEntry.COLUMN_PRODUCT_NAME,
                InventoryEntry.COLUMN_QUANTITY,
                InventoryEntry.COLUMN_PRICE};
        Log.v("Editor Activity", "Loader Cursor");
        return new CursorLoader(this,
                mCurrentStockUri,
                projection,
                null,
                null,
                null);

    }


    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {

        if (cursor == null || cursor.getCount() < 1) {
            return;
        }

        if (cursor.moveToFirst()) {
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

    private void showDeleteConfirmationDialog() {
        // Create an AlertDialog.Builder and set the message, and click listeners
        // for the postivie and negative buttons on the dialog.
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.delete_dialog_msg);
        builder.setPositiveButton(R.string.delete, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User clicked the "Delete" button, so delete the pet.
                deleteStock();
            }
        });
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User clicked the "Cancel" button, so dismiss the dialog
                // and continue editing the pet.
                if (dialog != null) {
                    dialog.dismiss();
                }
            }
        });

        // Create and show the AlertDialog
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    /**
     * Perform the deletion of the pet in the database.
     */
    private void deleteStock() {
        if (mCurrentStockUri != null) {
            // Call the ContentResolver to delete the pet at the given content URI.
            // Pass in null for the selection and selection args because the mCurrentPetUri
            // content URI already identifies the pet that we want.
            int rowsDeleted = getContentResolver().delete(mCurrentStockUri, null, null);

            // Show a toast message depending on whether or not the delete was successful.
            if (rowsDeleted == 0) {
                // If no rows were deleted, then there was an error with the delete.
                Toast.makeText(this, getString(R.string.editor_delete_pet_failed),
                        Toast.LENGTH_SHORT).show();
            } else {
                // Otherwise, the delete was successful and we can display a toast.
                Toast.makeText(this, getString(R.string.editor_delete_pet_successful),
                        Toast.LENGTH_SHORT).show();
            }
            finish();
        }
    }

}
