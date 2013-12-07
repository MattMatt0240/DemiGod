package high.caliber.productions.demigod.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class EnemyDB extends SQLiteOpenHelper {

	private static final String DB_PATH = "data/data/high.caliber.productions.demigod/databases/Enemy.db";
	private static final String DB_NAME = "Enemy.db";
	private static final String TABLE_COMMON = "Common";
	private static final String TABLE_RARE = "Rare";
	private static final int DB_VERSION = 1;

	public static final String COL_ID = "_id";
	public static final String COL_CLASS = "Class";
	public static final String COL_NAME = "Name";
	public static final String COL_LVL = "Level";
	public static final String COL_EXP_VALUE = "ExpValue";
	public static final String COL_HEALTH = "Health";
	public static final String COL_ENERGY = "Energy";
	public static final String COL_MANA = "Mana";
	public static final String COL_ATTACK = "Attack";
	public static final String COL_MAGIC = "Magic";
	public static final String COL_PH_DEFENSE = "PhDefense";
	public static final String COL_MG_DEFENSE = "MgDefense";
	public static final String COL_AGILITY = "Agility";
	public static final String COL_DEXTERITY = "Dexterity";

	private final String CREATE_COMMON = ("CREATE TABLE " + TABLE_COMMON + " ("
			+ COL_ID + "  INTEGER PRIMARY KEY AUTOINCREMENT, " + COL_CLASS
			+ " TEXT, " + COL_NAME + " TEXT, " + COL_LVL + " TEXT, "
			+ COL_EXP_VALUE + " TEXT, " + COL_HEALTH + " TEXT, " + COL_ENERGY
			+ " TEXT, " + COL_MANA + " TEXT, " + COL_ATTACK + " TEXT, "
			+ COL_MAGIC + " TEXT, " + COL_PH_DEFENSE + " TEXT, "
			+ COL_MG_DEFENSE + " TEXT, " + COL_AGILITY + " TEXT, "
			+ COL_DEXTERITY + " TEXT)");

	private final String CREATE_RARE = ("CREATE TABLE " + TABLE_RARE + " ("
			+ COL_ID + "  INTEGER PRIMARY KEY AUTOINCREMENT, " + COL_CLASS
			+ " TEXT, " + COL_NAME + " TEXT, " + COL_LVL + " TEXT, "
			+ COL_EXP_VALUE + " TEXT, " + COL_HEALTH + " TEXT, " + COL_ENERGY
			+ " TEXT, " + COL_MANA + " TEXT, " + COL_ATTACK + " TEXT, "
			+ COL_MAGIC + " TEXT, " + COL_PH_DEFENSE + " TEXT, "
			+ COL_MG_DEFENSE + " TEXT, " + COL_AGILITY + " TEXT, "
			+ COL_DEXTERITY + " TEXT)");

	private static SQLiteDatabase db;
	Context context;

	public EnemyDB(Context context) {
		super(context, DB_NAME, null, DB_VERSION);
		this.context = context;
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(CREATE_COMMON);
		db.execSQL(CREATE_RARE);
	}

	/**
	 * Populates Common Enemy Table with enemies
	 */
	public void PopulateCommonTable() {

		db = SQLiteDatabase.openDatabase(DB_PATH, null,
				SQLiteDatabase.OPEN_READWRITE);
		try {

			Log.d("Common Enemy Table", "Inserting Values for Common Enemies");

			ContentValues cv = new ContentValues();

			cv.put(COL_ID, "1");
			cv.put(COL_CLASS, "Warrior");
			cv.put(COL_NAME, "Filthy Barbarian");
			cv.put(COL_LVL, "1");
			cv.put(COL_EXP_VALUE, "15");
			cv.put(COL_HEALTH, "10");
			cv.put(COL_ENERGY, "6");
			cv.put(COL_MANA, "1");
			cv.put(COL_ATTACK, "3");
			cv.put(COL_MAGIC, "1");
			cv.put(COL_PH_DEFENSE, "4");
			cv.put(COL_MG_DEFENSE, "2");
			cv.put(COL_AGILITY, "4");
			cv.put(COL_DEXTERITY, "2");

			db.insert(TABLE_COMMON, COL_ID, cv);

			cv.put(COL_ID, "2");
			cv.put(COL_CLASS, "Mage");
			cv.put(COL_NAME, "Delirious Peasant");
			cv.put(COL_LVL, "1");
			cv.put(COL_EXP_VALUE, "10");
			cv.put(COL_HEALTH, "8");
			cv.put(COL_ENERGY, "2");
			cv.put(COL_MANA, "5");
			cv.put(COL_ATTACK, "1");
			cv.put(COL_MAGIC, "4");
			cv.put(COL_PH_DEFENSE, "1");
			cv.put(COL_MG_DEFENSE, "5");
			cv.put(COL_AGILITY, "3");
			cv.put(COL_DEXTERITY, "2");

			db.insert(TABLE_COMMON, COL_ID, cv);

			cv.put(COL_ID, "3");
			cv.put(COL_CLASS, "Mercenary");
			cv.put(COL_NAME, "Outcast Wanderer");
			cv.put(COL_LVL, "1");
			cv.put(COL_EXP_VALUE, "20");
			cv.put(COL_HEALTH, "10");
			cv.put(COL_ENERGY, "5");
			cv.put(COL_MANA, "3");
			cv.put(COL_ATTACK, "2");
			cv.put(COL_MAGIC, "2");
			cv.put(COL_PH_DEFENSE, "3");
			cv.put(COL_MG_DEFENSE, "2");
			cv.put(COL_AGILITY, "5");
			cv.put(COL_DEXTERITY, "4");

			db.insert(TABLE_COMMON, COL_ID, cv);
			db.close();
		} catch (SQLiteException e) {
			Log.d("Common Table", "Error Inserting Values");

		}

		Log.d("Common Enemy Table", "Values Successfully Inserted Into Table");

	}

	/**
	 * Populates Rare Enemy Table with enemies
	 */
	public void PopulateRareTable() {

		db = SQLiteDatabase.openDatabase(DB_PATH, null,
				SQLiteDatabase.OPEN_READWRITE);

		try {

			Log.d("Rare Enemy Table", "Inserting Values for Rare Enemies");

			ContentValues cv = new ContentValues();

			cv.put(COL_ID, "1");
			cv.put(COL_CLASS, "Warrior");
			cv.put(COL_NAME, "Blood Dragon");
			cv.put(COL_LVL, "10");
			cv.put(COL_EXP_VALUE, "250");
			cv.put(COL_HEALTH, "100");
			cv.put(COL_ENERGY, "30");
			cv.put(COL_MANA, "10");
			cv.put(COL_ATTACK, "10");
			cv.put(COL_MAGIC, "5");
			cv.put(COL_PH_DEFENSE, "9");
			cv.put(COL_MG_DEFENSE, "4");
			cv.put(COL_AGILITY, "6");
			cv.put(COL_DEXTERITY, "5");

			db.insert(TABLE_RARE, COL_ID, cv);
			db.close();
		} catch (SQLiteException e) {
			Log.d("Rare Table", "Error Inserting Values");

		}

		Log.d("Rare Enemy Table", "Values Successfully Inserted Into Table");

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.w(HeroDB.class.getName(), "Upgrading database from version "
				+ oldVersion + " to " + newVersion
				+ ", which will destroy all old data");
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_COMMON);

		this.onCreate(db);
	}

	@Override
	public synchronized void close() {

		if (db != null)
			db.close();

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
	 * Retrieves enemy entry from Common Table
	 * 
	 * @param rowId
	 *            Row to query against
	 * @return Cursor
	 * @throws SQLException
	 */
	public Cursor getCommonEnemy(int rowId) throws SQLException {

		db = getWritableDatabase();
		Cursor cursor = db.query(TABLE_COMMON, null, COL_ID + "=?",
				new String[] { String.valueOf(rowId) }, null, null, null, null);
		if (cursor != null) {
			cursor.moveToFirst();

		}
		return cursor;
	}

	public static String getPath() {
		return DB_PATH;
	}

	public static String getDbName() {
		return DB_NAME;
	}

	public static String getTableStats() {
		return TABLE_COMMON;
	}

	public static String getTableInventory() {
		return TABLE_COMMON;
	}

}
