package high.caliber.productions.demigod.utils;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import high.caliber.productions.demigod.database.*;

public class LevelUpWorker {

	public LevelUpWorker() {

	}

	static final String dbName = "Hero.db";
	static final String statsTable = "Stats";
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

	String Hero_Name, Hero_Class;
	
	DbHero heroDbHelper;

	SQLiteDatabase db;
	Cursor c;

	int Hero_Level;
	int Hero_Health;
	int Hero_MaxHealth;
	int Hero_Exp;
	int Hero_MaxExp;
	int Hero_Energy;
	int Hero_MaxEnergy;
	int Hero_Mana;
	int Hero_MaxMana;
	int Hero_Attack;
	int Hero_Magic;
	int Hero_PhDefense;
	int Hero_MgDefense;
	int Hero_Agility;
	int Hero_Dexterity;

	// Warrior Level-Up Algorithm
	public void Warrior() {

		db = SQLiteDatabase.openDatabase(heroDbHelper.getPath(), null,
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
					Hero_Speed = c.getInt(c.getColumnIndex(colSpeed));
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
		Hero_Speed = (Hero_Speed + (Hero_Level - 1));
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
		cv.put(colSpeed, Hero_Speed);
		cv.put(colAgility, Hero_Agility);
		cv.put(colDexterity, Hero_Dexterity);

		db.update(statsTable, cv, colID, null);
		db.close();

	}

	// Mage Level-Up Algorithm
	public void Mage() {

		db = SQLiteDatabase.openDatabase(heroDbHelper.getPath(), null,
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
					Hero_Speed = c.getInt(c.getColumnIndex(colSpeed));
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
		Hero_Speed = (Hero_Speed + Hero_Level);
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
		cv.put(colSpeed, Hero_Speed);
		cv.put(colAgility, Hero_Agility);
		cv.put(colDexterity, Hero_Dexterity);

		db.update(statsTable, cv, colID, null);
		db.close();
	}

	// Mercenary Level-Up Algorithm
	public void Mercenary() {

		db = SQLiteDatabase.openDatabase(heroDbHelper.getPath(), null,
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
					Hero_Speed = c.getInt(c.getColumnIndex(colSpeed));
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
		Hero_Speed = (Hero_Speed + (Hero_Level + 1));
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
		cv.put(colSpeed, Hero_Speed);
		cv.put(colAgility, Hero_Agility);
		cv.put(colDexterity, Hero_Dexterity);

		db.update(statsTable, cv, colID, null);
		db.close();

	}
}
