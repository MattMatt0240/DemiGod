package high.caliber.productions.demigod.utils;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import high.caliber.productions.demigod.database.*;

public class LevelUpWorker {

	public LevelUpWorker() {

	}

	static final String dbName = HeroDB.getDbName();
	static final String statsTable = HeroDB.getTableStats();
	static final String colID = HeroDB.COL_ID;
	static final String colClass = HeroDB.COL_CLASS;
	static final String colName = HeroDB.COL_NAME;
	static final String colLevel = HeroDB.COL_LVL;
	static final String colExp = HeroDB.COL_EXP;
	static final String colMaxExp = HeroDB.COL_MAX_EXP;
	static final String colHealth = HeroDB.COL_HEALTH;
	static final String colMaxHealth = HeroDB.COL_MAX_HEALTH;
	static final String colEnergy = HeroDB.COL_ENERGY;
	static final String colMaxEnergy = HeroDB.COL_MAX_ENERGY;
	static final String colMana = HeroDB.COL_MANA;
	static final String colMaxMana = HeroDB.COL_MAX_MANA;
	static final String colAttack = HeroDB.COL_ATTACK;
	static final String colMagic = HeroDB.COL_MAGIC;
	static final String colPhDefense = HeroDB.COL_PH_DEFENSE;
	static final String colMgDefense = HeroDB.COL_MG_DEFENSE;
	static final String colAgility = HeroDB.COL_AGILITY;
	static final String colDexterity = HeroDB.COL_DEXTERITY;

	String heroName, heroClass;

	SQLiteDatabase db;
	Cursor c;

	private int heroLevel;
	private int heroHealth;
	private int heroMaxHealth;
	private int heroExp;
	private int heroMaxExp;
	private int heroEnergy;
	private int heroMaxEnergy;
	private int heroMana;
	private int heroMaxMana;
	private int heroAttack;
	private int heroMagic;
	private int heroPhDefense;
	private int heroMgDefense;
	private int heroAgility;
	private int heroDexterity;

	// Warrior Level-Up Algorithm
	public void Warrior() {

		db = SQLiteDatabase.openDatabase(HeroDB.getPath(), null,
				SQLiteDatabase.OPEN_READWRITE);

		c = db.rawQuery(
				"SELECT Class, Name, Level, Exp, MaxExp, Health, MaxHealth, Energy, MaxEnergy, Mana, MaxMana, Attack, Magic, PhDefense, MgDefense, Agility, Dexterity FROM Stats",
				null);

		if (c != null) {

			// if 1st Row
			if (c.moveToFirst()) {
				do {
					// Retrieve values from columns
					heroClass = c.getString(c.getColumnIndex(colClass));
					heroName = c.getString(c.getColumnIndex(colName));
					heroLevel = c.getInt(c.getColumnIndex(colLevel));
					heroExp = c.getInt(c.getColumnIndex(colExp));
					heroMaxExp = c.getInt(c.getColumnIndex(colMaxExp));
					heroHealth = c.getInt(c.getColumnIndex(colHealth));
					heroMaxHealth = c.getInt(c.getColumnIndex(colMaxHealth));
					heroEnergy = c.getInt(c.getColumnIndex(colEnergy));
					heroMaxEnergy = c.getInt(c.getColumnIndex(colMaxEnergy));
					heroMana = c.getInt(c.getColumnIndex(colMana));
					heroMaxMana = c.getInt(c.getColumnIndex(colMaxMana));
					heroAttack = c.getInt(c.getColumnIndex(colAttack));
					heroMagic = c.getInt(c.getColumnIndex(colMagic));
					heroPhDefense = c.getInt(c.getColumnIndex(colPhDefense));
					heroMgDefense = c.getInt(c.getColumnIndex(colMgDefense));
					heroAgility = c.getInt(c.getColumnIndex(colAgility));
					heroDexterity = c.getInt(c.getColumnIndex(colDexterity));

				} while (c.moveToNext());

			}
		}
		c.close();

		Log.d("Level Up", "Warrior Level Up");

		ContentValues cv = new ContentValues();

		heroExp -= heroMaxExp;
		heroMaxExp += heroLevel * 10;
		heroMaxHealth += heroLevel * 4;
		heroHealth = heroMaxHealth;
		heroMaxEnergy += heroLevel * 2;
		heroEnergy = heroMaxEnergy;
		heroMaxMana += heroLevel;
		heroMana = heroMaxMana;
		heroAttack += heroLevel + 1;
		heroMagic += heroLevel;
		heroPhDefense += heroLevel + 2;
		heroMgDefense += heroLevel;
		heroAgility += heroLevel - 1;
		heroDexterity += heroLevel - 1;

		cv.put(colID, "1");
		cv.put(colLevel, heroLevel + 1);
		cv.put(colMaxExp, heroMaxExp);
		cv.put(colExp, heroExp);
		cv.put(colMaxHealth, heroMaxHealth);
		cv.put(colHealth, heroHealth);
		cv.put(colMaxEnergy, heroMaxEnergy);
		cv.put(colEnergy, heroEnergy);
		cv.put(colMaxMana, heroMaxMana);
		cv.put(colMana, heroMana);
		cv.put(colAttack, heroAttack);
		cv.put(colMagic, heroMagic);
		cv.put(colPhDefense, heroPhDefense);
		cv.put(colMgDefense, heroMgDefense);
		cv.put(colAgility, heroAgility);
		cv.put(colDexterity, heroDexterity);

		db.update(statsTable, cv, colID, null);
		db.close();

	}

	// Mage Level-Up Algorithm
	public void Mage() {

		db = SQLiteDatabase.openDatabase(HeroDB.getPath(), null,
				SQLiteDatabase.OPEN_READWRITE);

		c = db.rawQuery(
				"SELECT Class, Name, Level, Exp, MaxExp, Health, MaxHealth, Energy, MaxEnergy, Mana, MaxMana, Attack, Magic, PhDefense, MgDefense, Agility, Dexterity FROM Stats",
				null);

		if (c != null) {

			// if 1st Row
			if (c.moveToFirst()) {
				do {
					// Retrieve values from columns
					heroClass = c.getString(c.getColumnIndex(colClass));
					heroName = c.getString(c.getColumnIndex(colName));
					heroLevel = c.getInt(c.getColumnIndex(colLevel));
					heroExp = c.getInt(c.getColumnIndex(colExp));
					heroMaxExp = c.getInt(c.getColumnIndex(colMaxExp));
					heroHealth = c.getInt(c.getColumnIndex(colHealth));
					heroMaxHealth = c.getInt(c.getColumnIndex(colMaxHealth));
					heroEnergy = c.getInt(c.getColumnIndex(colEnergy));
					heroMaxEnergy = c.getInt(c.getColumnIndex(colMaxEnergy));
					heroMana = c.getInt(c.getColumnIndex(colMana));
					heroMaxMana = c.getInt(c.getColumnIndex(colMaxMana));
					heroAttack = c.getInt(c.getColumnIndex(colAttack));
					heroMagic = c.getInt(c.getColumnIndex(colMagic));
					heroPhDefense = c.getInt(c.getColumnIndex(colPhDefense));
					heroMgDefense = c.getInt(c.getColumnIndex(colMgDefense));
					heroAgility = c.getInt(c.getColumnIndex(colAgility));
					heroAgility = c.getInt(c.getColumnIndex(colDexterity));

				} while (c.moveToNext());

			}
		}
		c.close();

		Log.d("Level Up", "Mage Level Up");

		ContentValues cv = new ContentValues();

		heroExp -= heroMaxExp;
		heroMaxExp += heroLevel * 10;
		heroMaxHealth += heroLevel * 2;
		heroHealth = heroMaxHealth;
		heroMaxEnergy += heroLevel;
		heroEnergy = heroMaxEnergy;
		heroMaxMana += heroLevel * 2;
		heroMana = heroMaxMana;
		heroAttack += heroLevel - 1;
		heroMagic += heroLevel * 2;
		heroPhDefense += heroLevel - 1;
		heroMgDefense += heroLevel + 1;
		heroAgility += heroLevel;
		heroDexterity += heroLevel;

		cv.put(colID, "1");
		cv.put(colLevel, heroLevel + 1);
		cv.put(colMaxExp, heroMaxExp);
		cv.put(colExp, heroExp);
		cv.put(colMaxHealth, heroMaxHealth);
		cv.put(colHealth, heroHealth);
		cv.put(colMaxEnergy, heroMaxEnergy);
		cv.put(colEnergy, heroEnergy);
		cv.put(colMaxMana, heroMaxMana);
		cv.put(colMana, heroMana);
		cv.put(colAttack, heroAttack);
		cv.put(colMagic, heroMagic);
		cv.put(colPhDefense, heroPhDefense);
		cv.put(colMgDefense, heroMgDefense);
		cv.put(colAgility, heroAgility);
		cv.put(colDexterity, heroDexterity);

		db.update(statsTable, cv, colID, null);
		db.close();
	}

	// Mercenary Level-Up Algorithm
	public void Mercenary() {

		db = SQLiteDatabase.openDatabase(HeroDB.getPath(), null,
				SQLiteDatabase.OPEN_READWRITE);

		c = db.rawQuery(
				"SELECT Class, Name, Level, Exp, MaxExp, Health, MaxHealth, Energy, MaxEnergy, Mana, MaxMana, Attack, Magic, PhDefense, MgDefense, Agility, Dexterity FROM Stats",
				null);

		if (c != null) {

			// if 1st Row
			if (c.moveToFirst()) {
				do {
					// Retrieve values from columns
					heroClass = c.getString(c.getColumnIndex(colClass));
					heroName = c.getString(c.getColumnIndex(colName));
					heroLevel = c.getInt(c.getColumnIndex(colLevel));
					heroExp = c.getInt(c.getColumnIndex(colExp));
					heroMaxExp = c.getInt(c.getColumnIndex(colMaxExp));
					heroHealth = c.getInt(c.getColumnIndex(colHealth));
					heroMaxHealth = c.getInt(c.getColumnIndex(colMaxHealth));
					heroEnergy = c.getInt(c.getColumnIndex(colEnergy));
					heroMaxEnergy = c.getInt(c.getColumnIndex(colMaxEnergy));
					heroMana = c.getInt(c.getColumnIndex(colMana));
					heroMaxMana = c.getInt(c.getColumnIndex(colMaxMana));
					heroAttack = c.getInt(c.getColumnIndex(colAttack));
					heroMagic = c.getInt(c.getColumnIndex(colMagic));
					heroPhDefense = c.getInt(c.getColumnIndex(colPhDefense));
					heroMgDefense = c.getInt(c.getColumnIndex(colMgDefense));
					heroAgility = c.getInt(c.getColumnIndex(colAgility));
					heroDexterity = c.getInt(c.getColumnIndex(colDexterity));

				} while (c.moveToNext());

			}
		}
		c.close();

		Log.d("Level Up", "Merc Level Up");

		ContentValues cv = new ContentValues();

		heroExp -= heroMaxExp;
		heroMaxExp += heroLevel * 10;
		heroMaxHealth += heroLevel * 3;
		heroHealth = heroMaxHealth;
		heroMaxEnergy += heroLevel * 3;
		heroEnergy = heroMaxEnergy;
		heroMaxMana += heroLevel + 1;
		heroMana = heroMaxMana;
		heroAttack += heroLevel + 1;
		heroMagic += heroLevel + 1;
		heroPhDefense += heroLevel + 1;
		heroMgDefense += heroLevel + 1;
		heroAgility += heroLevel + 1;
		heroDexterity += heroLevel + 1;

		cv.put(colID, "1");
		cv.put(colLevel, heroLevel + 1);
		cv.put(colMaxExp, heroMaxExp);
		cv.put(colExp, heroExp);
		cv.put(colMaxHealth, heroMaxHealth);
		cv.put(colHealth, heroHealth);
		cv.put(colMaxEnergy, heroMaxEnergy);
		cv.put(colEnergy, heroEnergy);
		cv.put(colMaxMana, heroMaxMana);
		cv.put(colMana, heroMana);
		cv.put(colAttack, heroAttack);
		cv.put(colMagic, heroMagic);
		cv.put(colPhDefense, heroPhDefense);
		cv.put(colMgDefense, heroMgDefense);
		cv.put(colAgility, heroAgility);
		cv.put(colDexterity, heroDexterity);

		db.update(statsTable, cv, colID, null);
		db.close();

	}
}
