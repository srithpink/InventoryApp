package com.example.tharini.inventoryapp;

import android.app.LoaderManager;
import android.content.ContentUris;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.tharini.inventoryapp.data.InventoryContract;

/**
 * Created by Tharini on 05-03-2018.
 */

public class DisplayActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {


    EditText mPnameEditText,mPquantityEditText,mPprice;
    private  static final int INVENTORY_LOADER = 0;
    InventoryCursorAdapter mCursorAdapter;
    private Uri mCurrentStockUri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);

        mPnameEditText = (EditText)findViewById(R.id.nameEdit);
        mPquantityEditText = (EditText)findViewById(R.id.quantEdit);
        mPprice = (EditText)findViewById(R.id.priceEdit);

//        Intent intent = getIntent();
//        mCurrentStockUri =intent.getData();
//
//        if(mCurrentStockUri == null)
//        {
//            setTitle("Add a product");
//        }
//        else
//        {
//            setTitle("Edit a product");
//        }
        ListView inventoryListView = (ListView) findViewById(R.id.list);

        View emptyView = findViewById(R.id.empty_view);
        inventoryListView.setEmptyView(emptyView);
        mCursorAdapter = new InventoryCursorAdapter(this,null);
        inventoryListView.setAdapter(mCursorAdapter);

        inventoryListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {


                Intent intent = new Intent(DisplayActivity.this, InsertActivity.class);

                Uri currentStockUri = ContentUris.withAppendedId(InventoryContract.InventoryEntry.CONTENT_URI, id);
                intent.setData(currentStockUri);
                startActivity(intent);
            }
        });
        getLoaderManager().initLoader(INVENTORY_LOADER,null,this);
    }


    @Override
    public Loader onCreateLoader(int i, Bundle bundle) {

        String[] projection = {
                InventoryContract.InventoryEntry._ID,
                InventoryContract.InventoryEntry.COLUMN_PRODUCT_NAME,
                InventoryContract.InventoryEntry.COLUMN_QUANTITY,
                InventoryContract.InventoryEntry.COLUMN_PRICE
        };

        return new CursorLoader(this,
                InventoryContract.InventoryEntry.CONTENT_URI,
                projection,
                null,
                null,
                null);
    }

    @Override
    public void onLoadFinished(Loader <Cursor> loader, Cursor data) {
       mCursorAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader <Cursor> loader) {

         mCursorAdapter.swapCursor(null);
    }
}
