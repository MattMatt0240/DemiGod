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
	static final String colExp = DbHero.COL_LVL;
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

	String Hero_Name, Hero_Class;

	SQLiteDatabase db;
	Cursor c;

	private int Hero_Level;
	private int Hero_Health;
	private int Hero_MaxHealth;
	private int Hero_Exp;
	private int Hero_MaxExp;
	private int Hero_Energy;
	private int Hero_MaxEnergy;
	private int Hero_Mana;
	private int Hero_MaxMana;
	private int Hero_Attack;
	private int Hero_Magic;
	private int Hero_PhDefense;
	private int Hero_MgDefense;
	private int Hero_Agility;
	private int Hero_Dexterity;

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
					Hero_Class = c.getString(c.getColumnIndex(colClass));
					Hero_Name = c.getString(c.getColumnIndex(colName));
					Hero_Level = c.getInt(c.getColumnIndex(colLevel));
					Hero_Exp = c.getInt(c.getColumnIndex(colExp));
					Hero_MaxExp = c.getInt(c.getColumnIndex(colMaxExp));
					Hero_Health = c.getInt(c.getColumnIndex(colHealth));
					Hero_MaxHealth = c.getInt(c.getColumnIndex(colMaxHealth));
					Hero_Energy = c.getInt(c.getColumnIndex(colEnergy));
					Hero_MaxEnergy = c.getInt(c.getColumnIndex(colMaxEnergy));
					Hero_Mana = c.getInt(c.getColumnIndex(colMana));
					Hero_MaxMana = c.getInt(c.getColumnIndex(colMaxMana));
					Hero_Attack = c.getInt(c.getColumnIndex(colAttack));
					Hero_Magic = c.getInt(c.getColumnIndex(colMagic));
					Hero_PhDefense = c.getInt(c.getColumnIndex(colPhDefense));
					Hero_MgDefense = c.getInt(c.getColumnIndex(colMgDefense));
					Hero_Agility = c.getInt(c.getColumnIndex(colAgility));
					Hero_Dexterity = c.getInt(c.getColumnIndex(colDexterity));

				} while (c.moveToNext());

			}
		}
		c.close();

		Log.d("Level Up", "Warrior Level Up");

		ContentValues cv = new ContentValues();

		Hero_Exp = (Hero_Exp - Hero_MaxExp);
		Hero_MaxExp = (Hero_MaxExp + (Hero_Level * 10));
		Hero_MaxHealth = (Hero_MaxHealth + (Hero_Level * 4));
		Hero_Health = Hero_MaxHealth;
		Hero_MaxEnergy = (Hero_MaxEnergy + (Hero_Level * 2));
		Hero_Energy = Hero_MaxEnergy;
		Hero_MaxMana = (Hero_MaxMana + Hero_Level);
		Hero_Mana = Hero_MaxMana;
		Hero_Attack = (Hero_Attack + (Hero_Level + 1));
		Hero_Magic = (Hero_Magic + Hero_Level);
		Hero_PhDefense = (Hero_PhDefense + (Hero_Level + 2));
		Hero_MgDefense = (Hero_MgDefense + Hero_Level);
		Hero_Agility = (Hero_Agility + (Hero_Level - 1));
		Hero_Dexterity = (Hero_Dexterity + (Hero_Level - 1));

		cv.put(colID, "1");
		cv.put(colLevel, Hero_Level + 1);
		cv.put(colMaxExp, Hero_MaxExp);
		cv.put(colExp, Hero_Exp);
		cv.put(colMaxHealth, Hero_MaxHealth);
		cv.put(colHealth, Hero_Health);
		cv.put(colMaxEnergy, Hero_MaxEnergy);
		cv.put(colEnergy, Hero_Energy);
		cv.put(colMaxMana, Hero_MaxMana);
		cv.put(colMana, Hero_Mana);
		cv.put(colAttack, Hero_Attack);
		cv.put(colMagic, Hero_Magic);
		cv.put(colPhDefense, Hero_PhDefense);
		cv.put(colMgDefense, Hero_MgDefense);
		cv.put(colAgility, Hero_Agility);
		cv.put(colDexterity, Hero_Dexterity);

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
					Hero_Class = c.getString(c.getColumnIndex(colClass));
					Hero_Name = c.getString(c.getColumnIndex(colName));
					Hero_Level = c.getInt(c.getColumnIndex(colLevel));
					Hero_Exp = c.getInt(c.getColumnIndex(colExp));
					Hero_MaxExp = c.getInt(c.getColumnIndex(colMaxExp));
					Hero_Health = c.getInt(c.getColumnIndex(colHealth));
					Hero_MaxHealth = c.getInt(c.getColumnIndex(colMaxHealth));
					Hero_Energy = c.getInt(c.getColumnIndex(colEnergy));
					Hero_MaxEnergy = c.getInt(c.getColumnIndex(colMaxEnergy));
					Hero_Mana = c.getInt(c.getColumnIndex(colMana));
					Hero_MaxMana = c.getInt(c.getColumnIndex(colMaxMana));
					Hero_Attack = c.getInt(c.getColumnIndex(colAttack));
					Hero_Magic = c.getInt(c.getColumnIndex(colMagic));
					Hero_PhDefense = c.getInt(c.getColumnIndex(colPhDefense));
					Hero_MgDefense = c.getInt(c.getColumnIndex(colMgDefense));
					Hero_Agility = c.getInt(c.getColumnIndex(colAgility));
					Hero_Dexterity = c.getInt(c.getColumnIndex(colDexterity));

				} while (c.moveToNext());

			}
		}
		c.close();

		Log.d("Level Up", "Mage Level Up");

		ContentValues cv = new ContentValues();

		Hero_Exp = (Hero_Exp - Hero_MaxExp);
		Hero_MaxExp = (Hero_MaxExp + (Hero_Level * 10));
		Hero_MaxHealth = (Hero_Health + (Hero_Level * 2));
		Hero_Health = Hero_MaxHealth;
		Hero_MaxEnergy = (Hero_Energy + Hero_Level);
		Hero_Energy = Hero_MaxEnergy;
		Hero_MaxMana = (Hero_Mana + (Hero_Level * 2));
		Hero_Mana = Hero_MaxMana;
		Hero_Attack = (Hero_Attack + (Hero_Level - 1));
		Hero_Magic = (Hero_Magic + (Hero_Level * 2));
		Hero_PhDefense = (Hero_PhDefense + (Hero_Level - 1));
		Hero_MgDefense = (Hero_MgDefense + (Hero_Level + 1));
		Hero_Agility = (Hero_Agility + Hero_Level);
		Hero_Dexterity = (Hero_Dexterity + Hero_Level);

		cv.put(colID, "1");
		cv.put(colLevel, Hero_Level + 1);
		cv.put(colMaxExp, Hero_MaxExp);
		cv.put(colExp, Hero_Exp);
		cv.put(colMaxHealth, Hero_MaxHealth);
		cv.put(colHealth, Hero_Health);
		cv.put(colMaxEnergy, Hero_MaxEnergy);
		cv.put(colEnergy, Hero_Energy);
		cv.put(colMaxMana, Hero_MaxMana);
		cv.put(colMana, Hero_Mana);
		cv.put(colAttack, Hero_Attack);
		cv.put(colMagic, Hero_Magic);
		cv.put(colPhDefense, Hero_PhDefense);
		cv.put(colMgDefense, Hero_MgDefense);
		cv.put(colAgility, Hero_Agility);
		cv.put(colDexterity, Hero_Dexterity);

		db.update(statsTable, cv, colID, null);
		db.close();
	}

	// Mercenary Level-Up Algorithm
	public void Mercenary() {

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
					Hero_Class = c.getString(c.getColumnIndex(colClass));
					Hero_Name = c.getString(c.getColumnIndex(colName));
					Hero_Level = c.getInt(c.getColumnIndex(colLevel));
					Hero_Exp = c.getInt(c.getColumnIndex(colExp));
					Hero_MaxExp = c.getInt(c.getColumnIndex(colMaxExp));
					Hero_Health = c.getInt(c.getColumnIndex(colHealth));
					Hero_MaxHealth = c.getInt(c.getColumnIndex(colMaxHealth));
					Hero_Energy = c.getInt(c.getColumnIndex(colEnergy));
					Hero_MaxEnergy = c.getInt(c.getColumnIndex(colMaxEnergy));
					Hero_Mana = c.getInt(c.getColumnIndex(colMana));
					Hero_MaxMana = c.getInt(c.getColumnIndex(colMaxMana));
					Hero_Attack = c.getInt(c.getColumnIndex(colAttack));
					Hero_Magic = c.getInt(c.getColumnIndex(colMagic));
					Hero_PhDefense = c.getInt(c.getColumnIndex(colPhDefense));
					Hero_MgDefense = c.getInt(c.getColumnIndex(colMgDefense));
					Hero_Agility = c.getInt(c.getColumnIndex(colAgility));
					Hero_Dexterity = c.getInt(c.getColumnIndex(colDexterity));

				} while (c.moveToNext());

			}
		}
		c.close();

		Log.d("Level Up", "Merc Level Up");

		ContentValues cv = new ContentValues();

		Hero_Exp = (Hero_Exp - Hero_MaxExp);
		Hero_MaxExp = (Hero_MaxExp + (Hero_Level * 10));
		Hero_MaxHealth = (Hero_MaxHealth + (Hero_Level * 3));
		Hero_Health = Hero_MaxHealth;
		Hero_MaxEnergy = (Hero_MaxEnergy + (Hero_Level * 3));
		Hero_Energy = Hero_MaxEnergy;
		Hero_MaxMana = (Hero_MaxMana + (Hero_Level + 1));
		Hero_Mana = Hero_MaxMana;
		Hero_Attack = (Hero_Attack + (Hero_Level + 1));
		Hero_Magic = (Hero_Magic + (Hero_Level + 1));
		Hero_PhDefense = (Hero_PhDefense + (Hero_Level + 1));
		Hero_MgDefense = (Hero_MgDefense + (Hero_Level + 1));
		Hero_Agility = (Hero_Agility + (Hero_Level + 1));
		Hero_Dexterity = (Hero_Dexterity + (Hero_Level + 1));

		cv.put(colID, "1");
		cv.put(colLevel, Hero_Level + 1);
		cv.put(colMaxExp, Hero_MaxExp);
		cv.put(colExp, Hero_Exp);
		cv.put(colMaxHealth, Hero_MaxHealth);
		cv.put(colHealth, Hero_Health);
		cv.put(colMaxEnergy, Hero_MaxEnergy);
		cv.put(colEnergy, Hero_Energy);
		cv.put(colMaxMana, Hero_MaxMana);
		cv.put(colMana, Hero_Mana);
		cv.put(colAttack, Hero_Attack);
		cv.put(colMagic, Hero_Magic);
		cv.put(colPhDefense, Hero_PhDefense);
		cv.put(colMgDefense, Hero_MgDefense);
		cv.put(colAgility, Hero_Agility);
		cv.put(colDexterity, Hero_Dexterity);

		db.update(statsTable, cv, colID, null);
		db.close();

	}
}
