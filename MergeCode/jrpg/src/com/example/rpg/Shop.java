package com.example.rpg;

import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

public class Shop extends Activity {

	ItemDB itemDbHelper;

	SQLiteDatabase dbItems;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.shop);

		itemDbHelper = new ItemDB(this);
		dbItems = itemDbHelper.getWritableDatabase();
	}
}
