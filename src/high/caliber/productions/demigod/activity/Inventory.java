package high.caliber.productions.demigod.activity;

import high.caliber.productions.demigod.R;
import high.caliber.productions.demigod.database.HeroDb;
import high.caliber.productions.demigod.utils.InventoryAdapter;
import high.caliber.productions.demigod.utils.InventoryData;

import java.util.ArrayList;

import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ListView;

public class Inventory extends Activity {

	SQLiteDatabase heroDb;

	HeroDb heroDbHelper;

	static final String colItem = HeroDb.COL_ITEM;
	static final String colQty = HeroDb.COL_QTY;

	private ArrayList<InventoryData> listData;
	private InventoryAdapter inventoryAdapter;
	private ListView listItems;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.inventory);

		heroDbHelper = new HeroDb(this);
		heroDb = heroDbHelper.getWritableDatabase();

		listData = heroDbHelper.getInventory();
		listItems = (ListView) findViewById(R.id.listViewInventory);
		inventoryAdapter = new InventoryAdapter(this, R.layout.inventory_rows,
				listData);

		listItems.setAdapter(inventoryAdapter);

		heroDbHelper.close();
		heroDb.close();

	}
}
