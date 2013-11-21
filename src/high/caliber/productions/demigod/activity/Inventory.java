package high.caliber.productions.demigod.activity;

import high.caliber.productions.demigod.R;
import high.caliber.productions.demigod.database.DbHero;

import java.util.ArrayList;

import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class Inventory extends Activity {

	SQLiteDatabase heroDb;

	DbHero heroDbHelper;

	static final String colItem = DbHero.COL_ITEM;
	static final String colQty = DbHero.COL_QTY;

	ListView listItems;

	public static final String items[] = { colQty };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.inventory);

		heroDbHelper = new DbHero(this);

		heroDb = heroDbHelper.getWritableDatabase();

		listItems = (ListView) findViewById(R.id.listViewInventory);

		ArrayList<String> data = heroDbHelper.getInventory();
		heroDbHelper.close();
		listItems.setAdapter(new ArrayAdapter<String>(this,
				R.layout.inventory_rows, data));

	}
}
