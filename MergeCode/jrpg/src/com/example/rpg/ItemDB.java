package com.example.rpg;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class ItemDB extends SQLiteOpenHelper {

	static final String dbPath = "data/data/com.example.rpg/databases/Items.db";
	static final String dbName = "Items.db";
	static final String tableWeapons = "Weapons";
	static final String tableArmor = "Armor";
	static final String tableConsumables = "Consumables";
	public static final int DATABASE_VERSION = 1;

	static final String colID = "_id";
	static final String colItem = "Item";
	static final String colValue = "Value";

	private final String DATABASE_CREATE = ("CREATE TABLE " + tableWeapons
			+ " (" + colID + "  INTEGER PRIMARY KEY AUTOINCREMENT, " + colItem
			+ " TEXT, " + colValue + " TEXT)");

	private final String CreateArmorTable = ("CREATE TABLE " + tableArmor
			+ " (" + colID + "  INTEGER PRIMARY KEY AUTOINCREMENT, " + colItem
			+ " TEXT, " + colValue + " TEXT)");

	private final String CreateConsumableTable = ("CREATE TABLE "
			+ tableConsumables + " (" + colID
			+ "  INTEGER PRIMARY KEY AUTOINCREMENT, " + colItem + " TEXT, "
			+ colValue + " TEXT)");

	private static SQLiteDatabase db;
	public final Context context;

	public ItemDB(Context context) {
		super(context, dbName, null, DATABASE_VERSION);
		this.context = context;
	}

	@Override
	public void onCreate(SQLiteDatabase db) {

		try {
			db = SQLiteDatabase.openDatabase(dbPath, null,
					SQLiteDatabase.OPEN_READWRITE);
			Log.d("Creating ItemDb", "Exists, Closing Now");
			db.close();
		} catch (SQLiteException e) {
			Log.d("Creating ItemDb", "Does Not Exist, Creating Now");
			db.execSQL(DATABASE_CREATE);
			Log.d("ItemDb Created",
					"Created with Weapons Table, Attempting to create Armor Table Now");

			db.execSQL(CreateArmorTable);
			db.execSQL(CreateConsumableTable);

		}
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.w(ItemDB.class.getName(), "Upgrading database from version "
				+ oldVersion + " to " + newVersion
				+ ", which will destroy all old data");
		db.execSQL("DROP TABLE IF EXISTS " + tableWeapons);
		db.execSQL("DROP TABLE IF EXISTS " + tableArmor);
		db.execSQL("DROP TABLE IF EXISTS " + tableConsumables);
		this.onCreate(db);
	}

	@Override
	public synchronized void close() {

		if (db != null)
			db.close();

		super.close();

	}

	/**
	 * Inserts all Weapon items and values into the Items Database
	 */
	public void PopulateWeaponsTable() {

		try {

			db = SQLiteDatabase.openDatabase(dbPath, null,
					SQLiteDatabase.OPEN_READWRITE);

			Log.d("Weapons Table", "Inserting Values for Armor");

			ContentValues cv = new ContentValues();

			cv.put(colID, "1");
			cv.put(colItem, "Wooden Sword");
			cv.put(colValue, "50");

			db.insert(tableWeapons, colID, cv);

			cv.put(colID, "2");
			cv.put(colItem, "Aluminum Sword");
			cv.put(colValue, "100");

			db.insert(tableWeapons, colID, cv);

			cv.put(colID, "3");
			cv.put(colItem, "Bronze Sword");
			cv.put(colValue, "150");

			db.insert(tableWeapons, colID, cv);
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

			db = SQLiteDatabase.openDatabase(dbPath, null,
					SQLiteDatabase.OPEN_READWRITE);

			Log.d("Armor Table", "Inserting Values for Armor");

			ContentValues cv = new ContentValues();

			cv.put(colID, "1");
			cv.put(colItem, "Cloth Tunic");
			cv.put(colValue, "50");

			db.insert(tableArmor, colID, cv);

			cv.put(colID, "2");
			cv.put(colItem, "Leather Armor");
			cv.put(colValue, "100");

			db.insert(tableArmor, colID, cv);

			cv.put(colID, "3");
			cv.put(colItem, "Bronze Armor");
			cv.put(colValue, "150");

			db.insert(tableArmor, colID, cv);
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

			db = SQLiteDatabase.openDatabase(dbPath, null,
					SQLiteDatabase.OPEN_READWRITE);

			Log.d("Consumables Table", "Inserting Values for Consumables");

			ContentValues cv = new ContentValues();

			cv.put(colID, "1");
			cv.put(colItem, "Apple");
			cv.put(colValue, "50");

			db.insert(tableConsumables, colID, cv);

			cv.put(colID, "2");
			cv.put(colItem, "Chicken Thigh");
			cv.put(colValue, "100");

			db.insert(tableConsumables, colID, cv);

			cv.put(colID, "3");
			cv.put(colItem, "Steak");
			cv.put(colValue, "150");

			db.insert(tableConsumables, colID, cv);
			db.close();
		} catch (SQLiteException e) {
			Log.d("Consumables Table", "Error Inserting Values");

		}

		db.close();

		Log.d("Consumables Table", "Values Successfully Inserted Into Table");

	}

	/**
	 * Checks the existence of a database by attempting to open the database's
	 * path
	 * 
	 * 
	 * @return true, false, or null
	 */
	public boolean CheckDbExists() {

		SQLiteDatabase db = null;

		try {
			Log.d("Check Exist ItemDB", "Checking");

			db = SQLiteDatabase.openDatabase(dbPath, null,
					SQLiteDatabase.OPEN_READWRITE);

			Log.d("Check Exist ItemDB", "Exists");
			db.close();
		} catch (SQLiteException e) {
			Log.d("Check Exist ItemDB", "ItemDB Does Not Exist");

		}

		return db != null ? true : false;

	}
}
