package high.caliber.productions.demigod.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class ItemDB extends SQLiteOpenHelper {

	private static final String DB_PATH = "data/data/high.caliber.productions.demigod/databases/Items.db";
	private static final String DB_NAME = "Items.db";

	private static final String TABLE_WEAPONS = "Weapons";
	private static final String TABLE_ARMOR = "Armor";
	private static final String TABLE_CONSUMABLES = "Consumables";
	private static final int DATABASE_VERSION = 1;

	public static final String COL_ID = "_id";
	public static final String COL_ITEM = "Item";
	public static final String COL_ITEM_VALUE = "ItemValue";
	public static final String COL_EFFECT = "Effect";
	public static final String COL_EFFECT_VALUE = "EffectValue";

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
			+ " TEXT, " + COL_EFFECT_VALUE + " TEXT)");

	private static final String CREATE_WEAPONS = ("CREATE TABLE "
			+ TABLE_WEAPONS + " (" + COL_ID
			+ "  INTEGER PRIMARY KEY AUTOINCREMENT, " + COL_ITEM + " TEXT, "
			+ COL_ITEM_VALUE + " TEXT, " + COL_EFFECT + " TEXT, "
			+ COL_EFFECT_VALUE + " TEXT)");

	private static final String CREATE_CONSUMABLES = ("CREATE TABLE "
			+ TABLE_CONSUMABLES + " (" + COL_ID
			+ "  INTEGER PRIMARY KEY AUTOINCREMENT, " + COL_ITEM + " TEXT, "
			+ COL_ITEM_VALUE + " TEXT, " + COL_EFFECT + " TEXT, "
			+ COL_EFFECT_VALUE + " TEXT)");

	private SQLiteDatabase db;

	public ItemDB(Context context) {
		super(context, DB_NAME, null, DATABASE_VERSION);
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

			db.insert(TABLE_WEAPONS, COL_ID, cv);

			cv.put(COL_ID, "2");
			cv.put(COL_ITEM, "Aluminum Sword");
			cv.put(COL_ITEM_VALUE, "50");
			cv.put(COL_EFFECT, EFFECT_ATTACK);
			cv.put(COL_EFFECT_VALUE, "3");

			db.insert(TABLE_WEAPONS, COL_ID, cv);

			cv.put(COL_ID, "3");
			cv.put(COL_ITEM, "Bronze Sword");
			cv.put(COL_ITEM_VALUE, "150");
			cv.put(COL_EFFECT, EFFECT_ATTACK);
			cv.put(COL_EFFECT_VALUE, "5");

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

			db.insert(TABLE_ARMOR, COL_ID, cv);

			cv.put(COL_ID, "2");
			cv.put(COL_ITEM, "Leather Armor");
			cv.put(COL_ITEM_VALUE, "50");
			cv.put(COL_EFFECT, EFFECT_PH_DEF);
			cv.put(COL_EFFECT_VALUE, "3");

			db.insert(TABLE_ARMOR, COL_ID, cv);

			cv.put(COL_ID, "3");
			cv.put(COL_ITEM, "Bronze Armor");
			cv.put(COL_ITEM_VALUE, "150");
			cv.put(COL_EFFECT, EFFECT_PH_DEF);
			cv.put(COL_EFFECT_VALUE, "5");

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
			cv.put(COL_ITEM, "Apple");
			cv.put(COL_ITEM_VALUE, "5");
			cv.put(COL_EFFECT, EFFECT_RESTORE_HEALTH);
			cv.put(COL_EFFECT_VALUE, "5");

			db.insert(TABLE_CONSUMABLES, COL_ID, cv);

			cv.put(COL_ID, "2");
			cv.put(COL_ITEM, "Chicken Thigh");
			cv.put(COL_ITEM_VALUE, "25");
			cv.put(COL_EFFECT, EFFECT_RESTORE_HEALTH);
			cv.put(COL_EFFECT_VALUE, "30");

			db.insert(TABLE_CONSUMABLES, COL_ID, cv);

			cv.put(COL_ID, "3");
			cv.put(COL_ITEM, "Steak");
			cv.put(COL_ITEM_VALUE, "40");
			cv.put(COL_EFFECT, EFFECT_RESTORE_HEALTH);
			cv.put(COL_EFFECT_VALUE, "50");

			db.insert(TABLE_CONSUMABLES, COL_ID, cv);
			db.close();
		} catch (SQLiteException e) {
			Log.d("Consumables Table", "Error Inserting Values");

		}

		db.close();

		Log.d("Consumables Table", "Values Successfully Inserted Into Table");

	}

}
