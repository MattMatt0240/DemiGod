package high.caliber.productions.demigod.database;

import high.caliber.productions.demigod.utils.Item;

import java.io.IOException;
import java.io.InputStream;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

public class ItemDB extends SQLiteOpenHelper {

	private static final String DB_PATH = "data/data/high.caliber.productions.demigod/databases/Items.db";
	private static final String DB_NAME = "Items.db";

	private static final String TABLE_WEAPONS = "Weapons";
	private static final String TABLE_ARMOR = "Armor";
	private static final String TABLE_CONSUMABLES = "Consumables";
	private static final int DATABASE_VERSION = 1;

	// Table IDs
	public static int TABLE_WEAPONS_ID = 1;
	public static int TABLE_ARMOR_ID = 2;
	public static int TABLE_CONSUMABLES_ID = 3;

	// Table Columns
	public static final String COL_ID = "_id";
	public static final String COL_ITEM = "Item";
	public static final String COL_ITEM_VALUE = "ItemValue";
	public static final String COL_EFFECT = "Effect";
	public static final String COL_EFFECT_VALUE = "EffectValue";
	public static final String COL_ICON_PATH = "IconPath";

	// Effect Constants
	public static final String EFFECT_ATTACK = "Attack";
	public static final String EFFECT_PH_DEF = "PhDef";
	public static final String EFFECT_MG_DEF = "MgDef";
	public static final String EFFECT_RESTORE_HEALTH = "Heal";
	public static final String EFFECT_RESTORE_STAMINA = "Rest";
	public static final String EFFECT_RESTORE_ALL = "Rejuvinate";

	private static final String CREATE_ARMOR = ("CREATE TABLE " + TABLE_ARMOR
			+ " (" + COL_ID + "  INTEGER PRIMARY KEY AUTOINCREMENT, "
			+ COL_ITEM + " TEXT, " + COL_ITEM_VALUE + " TEXT, " + COL_EFFECT
			+ " TEXT, " + COL_EFFECT_VALUE + " TEXT,  " + COL_ICON_PATH + " TEXT)");

	private static final String CREATE_WEAPONS = ("CREATE TABLE "
			+ TABLE_WEAPONS + " (" + COL_ID
			+ "  INTEGER PRIMARY KEY AUTOINCREMENT, " + COL_ITEM + " TEXT, "
			+ COL_ITEM_VALUE + " TEXT, " + COL_EFFECT + " TEXT, "
			+ COL_EFFECT_VALUE + " TEXT,  " + COL_ICON_PATH + " TEXT)");

	private static final String CREATE_CONSUMABLES = ("CREATE TABLE "
			+ TABLE_CONSUMABLES + " (" + COL_ID
			+ "  INTEGER PRIMARY KEY AUTOINCREMENT, " + COL_ITEM + " TEXT, "
			+ COL_ITEM_VALUE + " TEXT, " + COL_EFFECT + " TEXT, "
			+ COL_EFFECT_VALUE + " TEXT,  " + COL_ICON_PATH + " TEXT)");

	private SQLiteDatabase db;
	private Context context;

	public ItemDB(Context context) {
		super(context, DB_NAME, null, DATABASE_VERSION);
		this.context = context;
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(CREATE_ARMOR);
		db.execSQL(CREATE_WEAPONS);
		db.execSQL(CREATE_CONSUMABLES);

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

	}

	@Override
	public synchronized void close() {

		if (this.db != null)
			this.db.close();

		super.close();

	}

	public boolean isCreated() {

		SQLiteDatabase db = null;
		boolean isCreated = false;

		try {
			db = SQLiteDatabase.openDatabase(DB_PATH, null,
					SQLiteDatabase.OPEN_READONLY);
		} catch (SQLiteException e) {
			e.printStackTrace();
		}

		if (db != null) {
			isCreated = true;
		} else {
			isCreated = false;
		}

		Log.d("DB created?", String.valueOf(isCreated));
		return isCreated;

	}

	/**
	 * Returns an Item object by ID and Table ID
	 * 
	 * @param itemId
	 *            Id of the desired item in the database
	 * @param tableId
	 *            Id associated with the Table to query against
	 * @return
	 */
	public Item getItem(int itemId, int tableId) {

		db = SQLiteDatabase.openDatabase(DB_PATH, null,
				SQLiteDatabase.OPEN_READWRITE);

		String table = null;

		if (tableId == TABLE_WEAPONS_ID) {
			table = TABLE_WEAPONS;
		}
		if (tableId == TABLE_ARMOR_ID) {
			table = TABLE_ARMOR;
		}
		if (tableId == TABLE_CONSUMABLES_ID) {
			table = TABLE_CONSUMABLES;
		}

		Cursor c = db.query(table, null, COL_ID + " = ? ",
				new String[] { String.valueOf(itemId) }, null, null, null);

		int rowItemId = c.getColumnIndex(COL_ID);
		int rowItem = c.getColumnIndex(COL_ITEM);
		int rowItemValue = c.getColumnIndex(COL_ITEM_VALUE);
		int rowEffect = c.getColumnIndex(COL_EFFECT);
		int rowEffectValue = c.getColumnIndex(COL_EFFECT_VALUE);
		int rowIconPath = c.getColumnIndex(COL_ICON_PATH);

		Item item = new Item();

		if (c != null) {

			if (c.moveToFirst()) {

				item.id = c.getInt(rowItemId);
				item.item = c.getString(rowItem);
				item.itemValue = c.getInt(rowItemValue);
				item.effect = c.getString(rowEffect);
				item.effectValue = c.getInt(rowEffectValue);

				item.icon = getItemIconImage(c.getString(rowIconPath), null);
			}
		}
		db.close();

		return item;
	}

	public Bitmap getItemIconImage(String imagePath, BitmapFactory.Options opts) {
		AssetManager manager = context.getAssets();
		InputStream inStream = null;
		try {
			inStream = manager.open(imagePath);
		} catch (IOException e) {
			e.printStackTrace();
		}
		Bitmap bitmap = BitmapFactory.decodeStream(inStream, null, opts);
		return bitmap;
	}

	/**
	 * Inserts all Weapon items and values into the Items Database
	 */
	public void PopulateWeaponsTable() {

		try {

			db = SQLiteDatabase.openDatabase(DB_PATH, null,
					SQLiteDatabase.OPEN_READWRITE);

			Log.d("Weapons Table", "Inserting Values for Armor");

			ContentValues cv = new ContentValues();

			cv.put(COL_ID, "1");
			cv.put(COL_ITEM, "Wooden Sword");
			cv.put(COL_ITEM_VALUE, "5");
			cv.put(COL_EFFECT, EFFECT_ATTACK);
			cv.put(COL_EFFECT_VALUE, "2");
			cv.put(COL_ICON_PATH, "icons/weapons/sword_wood.png");

			db.insert(TABLE_WEAPONS, COL_ID, cv);

			cv.put(COL_ID, "2");
			cv.put(COL_ITEM, "Bronze Sword");
			cv.put(COL_ITEM_VALUE, "50");
			cv.put(COL_EFFECT, EFFECT_ATTACK);
			cv.put(COL_EFFECT_VALUE, "3");
			cv.put(COL_ICON_PATH, "icons/weapons/sword_bronze.png");

			db.insert(TABLE_WEAPONS, COL_ID, cv);

			cv.put(COL_ID, "3");
			cv.put(COL_ITEM, "Silver Sword");
			cv.put(COL_ITEM_VALUE, "150");
			cv.put(COL_EFFECT, EFFECT_ATTACK);
			cv.put(COL_EFFECT_VALUE, "5");
			cv.put(COL_ICON_PATH, "icons/weapons/sword_silver.png");

			db.insert(TABLE_WEAPONS, COL_ID, cv);
			db.close();
		} catch (SQLiteException e) {
			Log.d("Weapons Table", "Error Inserting Values");

		}

		db.close();

		Log.d("Weapons Table", "Values Successfully Inserted Into Table");

	}

	/**
	 * Inserts all Armor items and values into the Items Database
	 */
	public void PopulateArmorTable() {

		try {

			db = SQLiteDatabase.openDatabase(DB_PATH, null,
					SQLiteDatabase.OPEN_READWRITE);

			Log.d("Armor Table", "Inserting Values for Armor");

			ContentValues cv = new ContentValues();

			cv.put(COL_ID, "1");
			cv.put(COL_ITEM, "Cloth Tunic");
			cv.put(COL_ITEM_VALUE, "5");
			cv.put(COL_EFFECT, EFFECT_PH_DEF);
			cv.put(COL_EFFECT_VALUE, "2");
			cv.put(COL_ICON_PATH, "");

			// needs icon
			// cv.put(COL_ICON_PATH, "icons/armor/.png");

			db.insert(TABLE_ARMOR, COL_ID, cv);

			cv.put(COL_ID, "2");
			cv.put(COL_ITEM, "Leather Armor");
			cv.put(COL_ITEM_VALUE, "50");
			cv.put(COL_EFFECT, EFFECT_PH_DEF);
			cv.put(COL_EFFECT_VALUE, "3");
			cv.put(COL_ICON_PATH, "icons/armor/armor_leather.png");

			db.insert(TABLE_ARMOR, COL_ID, cv);

			cv.put(COL_ID, "3");
			cv.put(COL_ITEM, "Silver Armor");
			cv.put(COL_ITEM_VALUE, "150");
			cv.put(COL_EFFECT, EFFECT_PH_DEF);
			cv.put(COL_EFFECT_VALUE, "5");
			cv.put(COL_ICON_PATH, "icons/armor/armor_silver.png");

			db.insert(TABLE_ARMOR, COL_ID, cv);
			db.close();
		} catch (SQLiteException e) {
			Log.d("Armor Table", "Error Inserting Values");

		}

		db.close();

		Log.d("Armor Table", "Values Successfully Inserted Into Table");

	}

	/**
	 * Inserts all Consumable items and values into the Items Database
	 */
	public void PopulateConsumablesTable() {

		try {

			db = SQLiteDatabase.openDatabase(DB_PATH, null,
					SQLiteDatabase.OPEN_READWRITE);

			Log.d("Consumables Table", "Inserting Values for Consumables");

			ContentValues cv = new ContentValues();

			cv.put(COL_ID, "1");
			cv.put(COL_ITEM, "Gold");
			cv.put(COL_ITEM_VALUE, "1");
			cv.put(COL_EFFECT, "Currency");
			cv.put(COL_EFFECT_VALUE, "1");
			cv.put(COL_ICON_PATH, "icons/gold.png");

			db.insert(TABLE_CONSUMABLES, COL_ID, cv);

			cv.put(COL_ID, "2");
			cv.put(COL_ITEM, "Apple");
			cv.put(COL_ITEM_VALUE, "5");
			cv.put(COL_EFFECT, EFFECT_RESTORE_HEALTH);
			cv.put(COL_EFFECT_VALUE, "5");
			cv.put(COL_ICON_PATH, "");

			db.insert(TABLE_CONSUMABLES, COL_ID, cv);

			cv.put(COL_ID, "3");
			cv.put(COL_ITEM, "Chicken Leg");
			cv.put(COL_ITEM_VALUE, "25");
			cv.put(COL_EFFECT, EFFECT_RESTORE_HEALTH);
			cv.put(COL_EFFECT_VALUE, "30");
			cv.put(COL_ICON_PATH, "icons/consumables/chicken_leg.png");

			db.insert(TABLE_CONSUMABLES, COL_ID, cv);

			cv.put(COL_ID, "4");
			cv.put(COL_ITEM, "Steak");
			cv.put(COL_ITEM_VALUE, "40");
			cv.put(COL_EFFECT, EFFECT_RESTORE_HEALTH);
			cv.put(COL_EFFECT_VALUE, "50");
			cv.put(COL_ICON_PATH, "icons/consumables/steak.png");

			db.insert(TABLE_CONSUMABLES, COL_ID, cv);
			db.close();
		} catch (SQLiteException e) {
			Log.d("Consumables Table", "Error Inserting Values");

		}

		db.close();

		Log.d("Consumables Table", "Values Successfully Inserted Into Table");

	}

}
