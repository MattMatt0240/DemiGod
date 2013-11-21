package high.caliber.productions.demigod.utils;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import high.caliber.productions.demigod.database.*;

public class LevelUpWorker {

	public LevelUpWorker() {

	}

	static final String dbName = DbHero.getDbName();
	static final String statsTable = DbHero.getTableStats();
	static final String colID = DbHero.COL_ID;
	static final String colClass = DbHero.COL_CLASS;
	static final String colName = DbHero.COL_NAME;
	static final String colLevel = DbHero.COL_LVL;
	static final String colExp = DbHero.COL_EXP;
	static final String colMaxExp = DbHero.COL_MAX_EXP;
	static final String colHealth = DbHero.COL_HEALTH;
	static final String colMaxHealth = DbHero.COL_MAX_HEALTH;
	static final String colEnergy = DbHero.COL_ENERGY;
	static final String colMaxEnergy = DbHero.COL_MAX_ENERGY;
	static final String colMana = DbHero.COL_MANA;
	static final String colMaxMana = DbHero.COL_MAX_MANA;
	static final String colAttack = DbHero.COL_ATTACK;
	static final String colMagic = DbHero.COL_MAGIC;
	static final String colPhDefense = DbHero.COL_PH_DEFENSE;
	static final String colMgDefense = DbHero.COL_MG_DEFENSE;
	static final String colAgility = DbHero.COL_AGILITY;
	static final String colDexterity = DbHero.COL_DEXTERITY;

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

		db = SQLiteDatabase.openDatabase(DbHero.getPath(), null,
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

		heroExp = (heroExp - heroMaxExp);
		heroMaxExp = (heroMaxExp + (heroLevel * 10));
		heroMaxHealth = (heroMaxHealth + (heroLevel * 4));
		heroHealth = heroMaxHealth;
		heroMaxEnergy = (heroMaxEnergy + (heroLevel * 2));
		heroEnergy = heroMaxEnergy;
		heroMaxMana = (heroMaxMana + heroLevel);
		heroMana = heroMaxMana;
		heroAttack = (heroAttack + (heroLevel + 1));
		heroMagic = (heroMagic + heroLevel);
		heroPhDefense = (heroPhDefense + (heroLevel + 2));
		heroMgDefense = (heroMgDefense + heroLevel);
		heroAgility = (heroAgility + (heroLevel - 1));
		heroAgility = (heroDexterity + (heroLevel - 1));

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

		db = SQLiteDatabase.openDatabase(DbHero.getPath(), null,
				SQLiteDatabase.OPEN_READWRITE);

		c = db.rawQuery(
				"SELECT Class, Name, Level, Exp, MaxExp, Health, MaxHealth, Energy, MaxEnergy, Mana, MaxMana, Attack, Magic, PhDefense, MgDefense, Speed, Agility, Dexterity FROM Stats",
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

		heroExp = (heroExp - heroMaxExp);
		heroMaxExp = (heroMaxExp + (heroLevel * 10));
		heroMaxHealth = (heroHealth + (heroLevel * 2));
		heroHealth = heroMaxHealth;
		heroMaxEnergy = (heroEnergy + heroLevel);
		heroEnergy = heroMaxEnergy;
		heroMaxMana = (heroMana + (heroLevel * 2));
		heroMana = heroMaxMana;
		heroAttack = (heroAttack + (heroLevel - 1));
		heroMagic = (heroMagic + (heroLevel * 2));
		heroPhDefense = (heroPhDefense + (heroLevel - 1));
		heroMgDefense = (heroMgDefense + (heroLevel + 1));
		heroAgility = (heroAgility + heroLevel);
		heroDexterity = (heroDexterity + heroLevel);

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

		db = SQLiteDatabase.openDatabase(DbHero.getPath(), null,
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

		heroExp = (heroExp - heroMaxExp);
		heroMaxExp = (heroMaxExp + (heroLevel * 10));
		heroMaxHealth = (heroMaxHealth + (heroLevel * 3));
		heroHealth = heroMaxHealth;
		heroMaxEnergy = (heroMaxEnergy + (heroLevel * 3));
		heroEnergy = heroMaxEnergy;
		heroMaxMana = (heroMaxMana + (heroLevel + 1));
		heroMana = heroMaxMana;
		heroAttack = (heroAttack + (heroLevel + 1));
		heroMagic = (heroMagic + (heroLevel + 1));
		heroPhDefense = (heroPhDefense + (heroLevel + 1));
		heroMgDefense = (heroMgDefense + (heroLevel + 1));
		heroAgility = (heroAgility + (heroLevel + 1));
		heroDexterity = (heroDexterity + (heroLevel + 1));

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
