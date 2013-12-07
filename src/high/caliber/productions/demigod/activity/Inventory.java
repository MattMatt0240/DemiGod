package high.caliber.productions.demigod.activity;

import high.caliber.productions.demigod.Item;
import high.caliber.productions.demigod.R;
import high.caliber.productions.demigod.database.HeroDB;
import high.caliber.productions.demigod.utils.InventoryAdapter;

import java.util.ArrayList;

import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

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

		listItems.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				Toast.makeText(Inventory.this, "Position = " + position,
						Toast.LENGTH_SHORT).show();

			}
		});

	}
}
