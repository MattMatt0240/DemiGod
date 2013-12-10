package high.caliber.productions.demigod.activity;

import high.caliber.productions.demigod.Item;
import high.caliber.productions.demigod.R;
import high.caliber.productions.demigod.database.HeroDB;
import high.caliber.productions.demigod.utils.InventoryAdapter;

import java.util.ArrayList;

import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.ViewFlipper;

public class Inventory extends Activity {

	SQLiteDatabase heroDb;

	HeroDB heroDbHelper;

	private ArrayList<Item> items;
	private InventoryAdapter inventoryAdapter;
	private ListView listItems;

	ViewFlipper vf;

	LinearLayout.LayoutParams paramsFill;
	boolean isViewing = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.inventory);

		vf = (ViewFlipper) findViewById(R.id.inventoryFlipper);

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
			public void onItemClick(AdapterView<?> arg0, View view,
					int position, long arg3) {

				vf.showNext();

				isViewing = true;

				TextView tvItemName = (TextView) findViewById(R.id.tvInventoryViewItemName);
				tvItemName.setText(inventoryAdapter.getItem(position).item);

				ImageView ivIcon = (ImageView) findViewById(R.id.ivInventoryViewIcon);
				Bitmap icon = inventoryAdapter.getItem(position).icon;
				ivIcon.setImageBitmap(icon);

				TextView tvDescription = (TextView) findViewById(R.id.tvInventoryViewDescription);
				tvDescription.setText(inventoryAdapter.getItem(position).description);

				TextView tvEffect = (TextView) findViewById(R.id.tvInventoryViewEffect);
				tvEffect.setText(inventoryAdapter.getItem(position).effect
						+ " : "
						+ inventoryAdapter.getItem(position).effectValue);

				TextView tvValue = (TextView) findViewById(R.id.tvInventoryViewValue);
				tvValue.setText("Value = "
						+ inventoryAdapter.getItem(position).itemValue);

			}
		});

	}

	@Override
	public void onBackPressed() {

		if (isViewing) {
			vf.showPrevious();
			isViewing = false;
		} else {
			finish();
		}
	}
}