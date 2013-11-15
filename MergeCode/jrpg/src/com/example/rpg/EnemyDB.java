package com.example.rpg;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class EnemyDB extends SQLiteOpenHelper {

	static final String DB_Path = "data/data/com.example.rpg/databases/Enemy.db";
	static final String dbName = "Enemy.db";
	static final String Table = "Common";
	public static final int DATABASE_VERSION = 1;

	private static final String TAG = EnemyDB.class.getSimpleName();

	static final String ColID = "_id";
	static final String ColClass = "Class";
	static final String ColName = "Name";
	static final String ColLevel = "Level";
	static final String ColExpValue = "Exp_Value";
	static final String ColHealth = "Health";
	static final String ColEnergy = "Energy";
	static final String ColMana = "Mana";
	static final String ColAttack = "Attack";
	static final String ColMagic = "Magic";
	static final String ColPhDefense = "PhDefense";
	static final String ColMgDefense = "MgDefense";
	static final String ColSpeed = "Speed";
	static final String ColAgility = "Agility";
	static final String ColDexterity = "Dexterity";

	private final String DATABASE_CREATE = ("CREATE TABLE " + Table + " ("
			+ ColID + "  INTEGER PRIMARY KEY AUTOINCREMENT, " + ColClass
			+ " TEXT, " + ColName + " TEXT, " + ColLevel + " TEXT, "
			+ ColExpValue + " TEXT, " + ColHealth + " TEXT, " + ColEnergy
			+ " TEXT, " + ColMana + " TEXT, " + ColAttack + " TEXT, "
			+ ColMagic + " TEXT, " + ColPhDefense + " TEXT, " + ColMgDefense
			+ " TEXT, " + ColSpeed + " TEXT, " + ColAgility + " TEXT, "
			+ ColDexterity + " TEXT)");

	public static final boolean Debug = true;

	private static SQLiteDatabase db;
	public final Context context;

	public EnemyDB(Context context) {
		super(context, dbName, null, DATABASE_VERSION);
		this.context = context;
	}

	@Override
	public void onCreate(SQLiteDatabase db) {

		try {
			db = SQLiteDatabase.openDatabase(DB_Path, null,
					SQLiteDatabase.OPEN_READWRITE);
			Log.d("Creating EnemyDb", "Exists, Closing Now");
			db.close();
		} catch (SQLiteException e) {
			Log.d("Creating EnemyDb", "Does Not Exist, Creating Now");
			db.execSQL(DATABASE_CREATE);

		}
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.w(EnemyDB.class.getName(), "Upgrading database from version "
				+ oldVersion + " to " + newVersion
				+ ", which will destroy all old data");
		db.execSQL("DROP TABLE IF EXISTS " + Table);

		if (Debug) {
			Log.d(TAG, "Upgrade: Dropping Table and Calling onCreate");
		}
		this.onCreate(db);
	}

	@Override
	public synchronized void close() {

		if (db != null)
			db.close();

		super.close();

	}

	public void CreateCommonEnemyTable(SQLiteDatabase db) {

		String Table = "Common";
		String ColID = "_id";
		String ColClass = "Class";
		String ColName = "Name";
		String ColLevel = "Level";
		String ColExpValue = "Exp_Value";
		String ColHealth = "Health";
		String ColEnergy = "Energy";
		String ColMana = "Mana";
		String ColAttack = "Attack";
		String ColMagic = "Magic";
		String ColPhDefense = "PhDefense";
		String ColMgDefense = "MgDefense";
		String ColSpeed = "Speed";
		String ColAgility = "Agility";
		String ColDexterity = "Dexterity";

		try {

			db = SQLiteDatabase.openDatabase(DB_Path, null,
					SQLiteDatabase.OPEN_READWRITE);

			Log.d("Common Table", "Inserting Values for Common Enemies");

			ContentValues cv = new ContentValues();

			cv.put(ColID, "1");
			cv.put(ColClass, "Warrior");
			cv.put(ColName, "Stick Man");
			cv.put(ColLevel, "1");
			cv.put(ColExpValue, "15");
			cv.put(ColHealth, "10");
			cv.put(ColEnergy, "6");
			cv.put(ColMana, "1");
			cv.put(ColAttack, "3");
			cv.put(ColMagic, "1");
			cv.put(ColPhDefense, "4");
			cv.put(ColMgDefense, "2");
			cv.put(ColSpeed, "5");
			cv.put(ColAgility, "6");
			cv.put(ColDexterity, "4");

			db.insert(Table, ColID, cv);

			cv.put(ColID, "2");
			cv.put(ColClass, "Mage");
			cv.put(ColName, "Spoon Bender");
			cv.put(ColLevel, "1");
			cv.put(ColExpValue, "10");
			cv.put(ColHealth, "8");
			cv.put(ColEnergy, "2");
			cv.put(ColMana, "5");
			cv.put(ColAttack, "1");
			cv.put(ColMagic, "4");
			cv.put(ColPhDefense, "1");
			cv.put(ColMgDefense, "5");
			cv.put(ColSpeed, "4");
			cv.put(ColAgility, "3");
			cv.put(ColDexterity, "2");

			db.insert(Table, ColID, cv);

			cv.put(ColID, "3");
			cv.put(ColClass, "Mercenary");
			cv.put(ColName, "Beggar");
			cv.put(ColLevel, "1");
			cv.put(ColExpValue, "20");
			cv.put(ColHealth, "10");
			cv.put(ColEnergy, "5");
			cv.put(ColMana, "3");
			cv.put(ColAttack, "2");
			cv.put(ColMagic, "2");
			cv.put(ColPhDefense, "3");
			cv.put(ColMgDefense, "2");
			cv.put(ColSpeed, "6");
			cv.put(ColAgility, "3");
			cv.put(ColDexterity, "4");

			db.insert(Table, ColID, cv);
			db.close();
		} catch (SQLiteException e) {
			Log.d("Common Table", "Error Inserting Values");

		}

		db.close();

		Log.d("Common Table", "Values Successfully Inserted Into Table");

	}

	public boolean CheckDbExists() {

		SQLiteDatabase db = null;

		try {
			Log.d("Check Exist EnemyDb", "Checking");

			db = SQLiteDatabase.openDatabase(DB_Path, null,
					SQLiteDatabase.OPEN_READWRITE);

			Log.d("Check Exist EnemyDb", "Exists");
			db.close();
		} catch (SQLiteException e) {
			Log.d("Check Exist EnemyDb", "Enemy DB Does Not Exist");

		}

		return db != null ? true : false;

	}
}
