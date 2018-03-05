package com.example.tharini.inventoryapp;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.example.tharini.inventoryapp.DisplayActivity;
import com.example.tharini.inventoryapp.data.InventoryDbHelper;
import com.example.tharini.inventoryapp.data.InventoryContract.InventoryEntry;
import com.example.tharini.inventoryapp.data.InventoryDbHelper;

/**
 * Created by Tharini on 25-02-2018.
 */

public class HomeActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Button goToInsertActivity = (Button)findViewById(R.id.insertItems);
        Button goToDisplayActivity = (Button) findViewById(R.id.viewItems);
        goToInsertActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this,InsertActivity.class);
                startActivity(intent);

            }
        });
        goToDisplayActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this,DisplayActivity.class);
                startActivity(intent);

            }
        });

         InventoryDbHelper mDbHelper = new InventoryDbHelper(this);
    }

//    @Override
//    protected void onStart()
//    {
//        super.onStart();
//        displayDatabaseInfo();
//    }

}