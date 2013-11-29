package high.caliber.productions.demigod.activity;

import high.caliber.productions.demigod.R;
import high.caliber.productions.demigod.database.HeroDB;
import high.caliber.productions.demigod.utils.InventoryAdapter;
import high.caliber.productions.demigod.utils.Item;

import java.util.ArrayList;

import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ListView;

public class Inventory extends Activity {

	SQLiteDatabase heroDb;

	HeroDB heroDbHelper;

	private ArrayList<Item> items;
	private InventoryAdapter inventoryAdapter;
	private ListView listItems;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.inventory);

		heroDbHelper = new HeroDB(this);
		heroDb = heroDbHelper.getWritableDatabase();

		items = heroDbHelper.getInventory();
		listItems = (ListView) findViewById(R.id.listViewInventory);
		inventoryAdapter = new InventoryAdapter(this, R.layout.inventory_rows,
				items);

		listItems.setAdapter(inventoryAdapter);

		heroDbHelper.close();
		heroDb.close();

	}
}
