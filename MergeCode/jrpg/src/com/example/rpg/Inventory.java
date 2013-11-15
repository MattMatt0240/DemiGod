package com.example.rpg;

import java.util.ArrayList;

import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class Inventory extends Activity {

	SQLiteDatabase heroDB;

	HeroDB heroDBhelper;

	static final String ColItem = "Item";
	static final String ColQty = "Quantity";

	ListView listItems;

	public static final String items[] = { HeroDB.ColItem };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.inventory);

		heroDBhelper = new HeroDB(this);

		heroDB = heroDBhelper.getWritableDatabase();

		listItems = (ListView) findViewById(R.id.listViewInventory);

		ArrayList<String> data = heroDBhelper.geData();
		heroDBhelper.close();
		listItems.setAdapter(new ArrayAdapter<String>(this,
				R.layout.inventory_rows, data));

	}
}
