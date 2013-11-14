/*
 Copyright (C) 2013  

 @author High Caliber Productions

 This program is free software: you can redistribute it and/or modify
 it under the terms of the GNU General Public License as published by
 the Free Software Foundation, either version 3 of the License, or
 (at your option) any later version.

 This program is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 GNU General Public License for more details.

 You should have received a copy of the GNU General Public License
 along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.example.demigod.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DbHero extends SQLiteOpenHelper {

	private static final String DB_PATH = "data/data/com.example.demigod/databases/Hero.db";
	private static final String DB_NAME = "Hero.db";
	private static final String TABLE_STATS = "Stats";
	private static final String TABLE_INVENTORY = "Inventory";
	private static final int DB_VERSION = 1;

	private static final String COL_ID = "_id";
	private static final String COL_CLASS = "Class"; // The hero's class
														// (warrior, mage, or
														// mercenary)
	private static final String COL_NAME = "Name"; // User's chosen name for
													// hero
	private static final String COL_LVL = "Level"; // Hero's experience level
	private static final String COL_EXP = "Exp"; // Hero's current experience
													// points
	private static final String COL_MAX_EXP = "MaxExp"; // Experience needed for
														// the Hero to level-up
	private static final String COL_HEALTH = "Health"; // Hero's current Health
														// (reaches 0 = dead)
	private static final String COL_MAX_HEALTH = "MaxHealth"; // Hero's max
																// health
	private static final String COL_ENERGY = "Energy"; // Hero's energy, used
														// for performing moves
														// in battle
	private static final String COL_MAX_ENERGY = "MaxEnergy";
	private static final String COL_MANA = "Mana"; // Hero's magic pool, used
													// for performing magic
													// abilities in battle
	private static final String COL_MAX_MANA = "MaxMana";
	private static final String COL_ATTACK = "Attack"; // Dictating factor in
														// melee attacks damage
														// output
	private static final String COL_MAGIC = "Magic"; // Dictating factor in
														// Magical attacks
														// damage output
	private static final String COL_PH_DEFENSE = "PhDefense"; // Resistance to
																// physical
																// damage
	private static final String COL_MG_DEFENSE = "MgDefense"; // Resistance to
																// Magical
																// damage
	private static final String COL_AGILITY = "Agility"; // Dictating factor in
															// turn order (who
															// goes 1st)
	private static final String COL_DEXTERITY = "Dexterity"; // Dictating factor
																// in critical
																// hits, and
																// evading/dodging
																// attacks

	private static final String STATS_CREATE = ("CREATE TABLE " + TABLE_STATS
			+ " (" + COL_ID + "  INTEGER PRIMARY KEY AUTOINCREMENT, "
			+ COL_CLASS + " TEXT, " + COL_NAME + " TEXT, " + COL_LVL
			+ " TEXT, " + COL_EXP + " TEXT, " + COL_MAX_EXP + " TEXT, "
			+ COL_HEALTH + " TEXT, " + COL_MAX_HEALTH + " TEXT, " + COL_ENERGY
			+ " TEXT, " + COL_MAX_ENERGY + " TEXT, " + COL_MANA + " TEXT, "
			+ COL_MAX_MANA + " TEXT, " + COL_ATTACK + " TEXT, " + COL_MAGIC
			+ " TEXT, " + COL_PH_DEFENSE + " TEXT, " + COL_MG_DEFENSE
			+ " TEXT," + COL_AGILITY + " TEXT, " + COL_DEXTERITY + " TEXT)");

	private static final String COL_ITEM = "Item";
	private static final String COL_QTY = "Quantity";

	private static final String INVENTORY_CREATE = ("CREATE TABLE "
			+ TABLE_INVENTORY + " (" + COL_ID
			+ "  INTEGER PRIMARY KEY AUTOINCREMENT, " + COL_ITEM + " TEXT,"
			+ COL_QTY + " TEXT)");

	private static SQLiteDatabase db;
	Context context;

	public DbHero(Context context) {
		super(context, DB_NAME, null, DB_VERSION);
		this.context = context;
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(STATS_CREATE);
		db.execSQL(INVENTORY_CREATE);

	}

	/**
	 * Inserts hero's initial inventory
	 */
	public void PopulateInventoryFields() {

		try {

			db = SQLiteDatabase.openDatabase(DB_PATH, null,
					SQLiteDatabase.OPEN_READWRITE);

			Log.d("Inventory Table",
					"Inserting Initial Inventory Items into Table");

			ContentValues cv = new ContentValues();

			cv.put(COL_ID, "1");
			cv.put(COL_ITEM, "Gold");
			cv.put(COL_QTY, "150");

			db.insert(TABLE_INVENTORY, COL_ID, cv);

			cv.put(COL_ID, "2");
			cv.put(COL_ITEM, "Bread");
			cv.put(COL_QTY, "3");

			db.insert(TABLE_INVENTORY, COL_ID, cv);

			cv.put(COL_ID, "3");
			cv.put(COL_ITEM, "Bronze Sword");
			cv.put(COL_QTY, "1");

			db.insert(TABLE_INVENTORY, COL_ID, cv);
			db.close();
		} catch (SQLiteException e) {
			Log.d("Inventory Table", "Error Inserting Values");

		}

		db.close();
		Log.d("Inventory Table", "Values Successfully Inserted Into Table");

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.w(DbHero.class.getName(), "Upgrading database from version "
				+ oldVersion + " to " + newVersion
				+ ", which will destroy all old data");
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_STATS);

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
}
