package com.example.rpg;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class HeroDB extends SQLiteOpenHelper {

	static final String dbPath = "data/data/com.example.rpg/databases/Hero.db";
	static final String dbName = "Hero.db";
	static final String statsTable = "Stats";
	static final String inventoryTable = "Inventory";
	public static final int DATABASE_VERSION = 1;

	static final String colID = "_id";
	static final String colClass = "Class";
	static final String colName = "Name";
	static final String colLevel = "Level";
	static final String colExp = "Exp";
	static final String colMaxExp = "MaxExp";
	static final String colHealth = "Health";
	static final String colMaxHealth = "MaxHealth";
	static final String colEnergy = "Energy";
	static final String colMaxEnergy = "MaxEnergy";
	static final String colMana = "Mana";
	static final String colMaxMana = "MaxMana";
	static final String colAttack = "Attack";
	static final String colMagic = "Magic";
	static final String colPhDefense = "PhDefense";
	static final String colMgDefense = "MgDefense";
	static final String colSpeed = "Speed";
	static final String colAgility = "Agility";
	static final String colDexterity = "Dexterity";

	private final String DATABASE_CREATE = ("CREATE TABLE " + statsTable + " ("
			+ colID + "  INTEGER PRIMARY KEY AUTOINCREMENT, " + colClass
			+ " TEXT, " + colName + " TEXT, " + colLevel + " TEXT, " + colExp
			+ " TEXT, " + colMaxExp + " TEXT, " + colHealth + " TEXT, "
			+ colMaxHealth + " TEXT, " + colEnergy + " TEXT, " + colMaxEnergy
			+ " TEXT, " + colMana + " TEXT, " + colMaxMana + " TEXT, "
			+ colAttack + " TEXT, " + colMagic + " TEXT, " + colPhDefense
			+ " TEXT, " + colMgDefense + " TEXT, " + colSpeed + " TEXT, "
			+ colAgility + " TEXT, " + colDexterity + " TEXT)");

	static final String ColItem = "Item";
	static final String ColQty = "Quantity";

	private final String Inventory_Create = ("CREATE TABLE " + inventoryTable
			+ " (" + colID + "  INTEGER PRIMARY KEY AUTOINCREMENT, " + ColItem
			+ " TEXT," + ColQty + " TEXT)");

	private static SQLiteDatabase db;
	public final Context context;

	public HeroDB(Context context) {
		super(context, dbName, null, DATABASE_VERSION);
		this.context = context;
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(DATABASE_CREATE);
		db.execSQL(Inventory_Create);

	}

	/**
	 * Inserts hero's initial inventory
	 */
	public void PopulateInventoryFields(SQLiteDatabase db) {

		try {

			db = SQLiteDatabase.openDatabase(dbPath, null,
					SQLiteDatabase.OPEN_READWRITE);

			Log.d("Inventory Table",
					"Inserting Initial Inventory Items into Table");

			ContentValues cv = new ContentValues();

			cv.put(colID, "1");
			cv.put(ColItem, "Gold");
			cv.put(ColQty, "150");

			db.insert(inventoryTable, colID, cv);

			cv.put(colID, "2");
			cv.put(ColItem, "Bread");
			cv.put(ColQty, "3");

			db.insert(inventoryTable, colID, cv);

			cv.put(colID, "3");
			cv.put(ColItem, "Bronze Sword");
			cv.put(ColQty, "1");

			db.insert(inventoryTable, colID, cv);
			db.close();
		} catch (SQLiteException e) {
			Log.d("Inventory Table", "Error Inserting Values");

		}

		db.close();

		Log.d("Inventory Table", "Values Successfully Inserted Into Table");

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.w(HeroDB.class.getName(), "Upgrading database from version "
				+ oldVersion + " to " + newVersion
				+ ", which will destroy all old data");
		db.execSQL("DROP TABLE IF EXISTS " + statsTable);

		this.onCreate(db);
	}

	@Override
	public synchronized void close() {

		if (db != null)
			db.close();

		super.close();

	}

	public Cursor query(SQLiteDatabase db, String query) {
		Cursor cursor = db
				.rawQuery(
						"SELECT name FROM sqlite_master WHERE type='Table' AND name='Hero.db'",
						null);
		return cursor;
	}

	public ArrayList<String> geData() {

		db = SQLiteDatabase.openDatabase(dbPath, null,
				SQLiteDatabase.OPEN_READWRITE);

		Cursor c = db.query(inventoryTable, null, null, null, null, null, null);
		ArrayList<String> item = new ArrayList<String>();
		int rowItem = c.getColumnIndex(ColItem);
		int rowQty = c.getColumnIndex(ColQty);

		item.add("Item" + "           " + "Value");
		item.trimToSize();

		for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
			item.add(c.getString(rowItem) + " " + c.getString(rowQty));
		}
		return item;
	}

}
