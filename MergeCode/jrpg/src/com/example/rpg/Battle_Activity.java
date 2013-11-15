package com.example.rpg;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class Battle_Activity extends Activity implements OnClickListener {

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

	String heroName, heroClass;

	HeroDB helper;

	SQLiteDatabase db;
	Cursor c;

	int heroLevel;
	int heroHealth;
	int heroMaxHealth;
	int heroExp;
	int heroMaxExp;
	int heroEnergy;
	int heroMaxEnergy;
	int heroMana;
	int heroMaxMana;
	int heroAttack;
	int heroMagic;
	int heroPhDefense;
	int heroMgDefense;
	int heroSpeed;
	int heroAgility;
	int heroDexterity;

	SharedPreferences battleLogPrefs;

	LevelUpWorker lvlUp;

	TextView hero_name, hero_level, hero_health, hero_energy, hero_exp,
			enemy_name, enemy_level, enemy_health, enemy_energy;

	Button bAttack, bDefend;

	ProgressBar progBarHealth, progBarEnergy, progBarEXP, progBarEnemyHealth,
			progBarEnemyEnergy;

	int enemyLevel = 1;
	int enemyHealth = 10;
	int enemyEnergy = 4;
	int enemyMana = 0;
	int enemyAttack = 10;
	int enemyMagic = 1;
	int enemyPhDefense = 4;
	int enemyMgDefense = 2;
	int enemySpeed = 10;
	int enemyAgility = 6;
	int enemyDexterity = 3;
	int enemyExpValue = 101;

	int enemyMaxHealth = 10;
	int enemyMaxEnergy = 4;

	int damageDealt, lifeTimeDamageDealt, damageRecieved,
			lifeTimeDamageRecieved;

	boolean PlayerTurn;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.battle);

		helper = new HeroDB(this);
		db = helper.getWritableDatabase();

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
					heroEnergy = c.getInt(c.getColumnIndex(colMaxEnergy));
					heroMana = c.getInt(c.getColumnIndex(colMana));
					heroMaxMana = c.getInt(c.getColumnIndex(colMaxMana));
					heroAttack = c.getInt(c.getColumnIndex(colAttack));
					heroMagic = c.getInt(c.getColumnIndex(colMagic));
					heroPhDefense = c.getInt(c.getColumnIndex(colPhDefense));
					heroMgDefense = c.getInt(c.getColumnIndex(colMgDefense));
					heroSpeed = c.getInt(c.getColumnIndex(colSpeed));
					heroAgility = c.getInt(c.getColumnIndex(colAgility));
					heroDexterity = c.getInt(c.getColumnIndex(colDexterity));

				} while (c.moveToNext());

			}
		}
		c.close();

		lvlUp = new LevelUpWorker();

		// Create BattleLog pref files if not exist
		battleLogPrefs = getSharedPreferences("BattleLog", 0);
		damageDealt = battleLogPrefs.getInt("DamageDealt", 0);
		lifeTimeDamageDealt = battleLogPrefs.getInt("LifeTimeDamageDealt", 0);
		damageRecieved = battleLogPrefs.getInt("DamageReceived", 0);
		lifeTimeDamageRecieved = battleLogPrefs.getInt(
				"LifeTimeDamageRecieved", 0);

		// Initialize variables (TextViews, Buttons, & ProgressBars)
		hero_name = (TextView) findViewById(R.id.TV_Battle_Hero_Name);
		hero_name.setText(heroName);
		hero_name.bringToFront();

		hero_level = (TextView) findViewById(R.id.TV_Battle_Hero_Level);
		hero_level.setText("LVL: " + heroLevel);
		hero_name.bringToFront();

		hero_health = (TextView) findViewById(R.id.TV_Battle_Hero_Health);
		hero_health.setText("Health: " + heroHealth + " / " + heroMaxHealth);

		progBarHealth = (ProgressBar) findViewById(R.id.ProgBar_HeroHealth);
		progBarHealth.setProgress(heroHealth);
		progBarHealth.setMax(heroMaxHealth);

		hero_energy = (TextView) findViewById(R.id.TV_Battle_Hero_Energy);
		hero_energy.setText("Energy: " + heroEnergy + " / " + heroEnergy);

		progBarEnergy = (ProgressBar) findViewById(R.id.ProgBar_HeroEnergy);
		progBarEnergy.setProgress(heroEnergy);
		progBarEnergy.setMax(heroEnergy);

		hero_exp = (TextView) findViewById(R.id.TV_Battle_Hero_EXP);
		hero_exp.setText("EXP: " + heroExp + " / " + heroMaxExp);

		progBarEXP = (ProgressBar) findViewById(R.id.ProgBar_HeroExp);
		progBarEXP.setProgress(heroExp);
		progBarEXP.setMax(heroMaxExp);

		enemy_name = (TextView) findViewById(R.id.TV_Battle_Enemy_Name);
		enemy_name.setText("Punk");

		enemy_level = (TextView) findViewById(R.id.TV_Battle_Enemy_Level);
		enemy_level.setText(String.valueOf("LVL: " + enemyLevel));

		enemy_health = (TextView) findViewById(R.id.TV_Battle_Enemy_Health);
		enemy_health.setText("Health: " + String.valueOf(enemyHealth) + " / "
				+ enemyMaxHealth);

		progBarEnemyHealth = (ProgressBar) findViewById(R.id.ProgBar_EnemyHealth);
		progBarEnemyHealth.setProgress(enemyHealth);
		progBarEnemyHealth.setMax(enemyMaxHealth);

		enemy_energy = (TextView) findViewById(R.id.TV_Battle_Enemy_Energy);
		enemy_energy.setText("Energy: " + String.valueOf(enemyEnergy) + " / "
				+ enemyMaxEnergy);

		progBarEnemyEnergy = (ProgressBar) findViewById(R.id.ProgBar_EnemyEnergy);
		progBarEnemyEnergy.setProgress(enemyEnergy);
		progBarEnemyEnergy.setMax(enemyMaxEnergy);

		bAttack = (Button) findViewById(R.id.B_Battle_Attack);
		bAttack.setOnClickListener(this);

		bDefend = (Button) findViewById(R.id.B_Battle_Defend);
		bDefend.setOnClickListener(this);

		if (heroSpeed >= enemySpeed) {
			PlayerTurn = true;
		}
		if (enemySpeed > heroSpeed) {
			PlayerTurn = false;
			bAttack.setText("Enemy's Turn");
		}

		if (PlayerTurn == true) {
			Toast.makeText(getApplicationContext(), "Your turn",
					Toast.LENGTH_SHORT).show();
		} else
			Toast.makeText(getApplicationContext(), "Enemy's Turn",
					Toast.LENGTH_SHORT).show();
	}

	protected void onResume() {
		super.onResume();

		progBarHealth.setProgress(0);
		progBarHealth.setProgress(heroHealth);

		progBarEnergy.setProgress(0);
		progBarEnergy.setProgress(heroEnergy);

		progBarEXP.setProgress(0);
		progBarEXP.setProgress(heroExp);

		progBarEnemyHealth.setProgress(0);
		progBarEnemyHealth.setProgress(enemyHealth);

		progBarEnemyEnergy.setProgress(0);
		progBarEnemyEnergy.setProgress(enemyEnergy);

	}

	// User Attack Formula
	public void Attack() {

		int Damage = ((heroAttack / enemyPhDefense) + 1);

		if (heroEnergy <= 0) {
			Damage = 0;

			heroEnergy = heroEnergy + 2;

		} else {

			enemyHealth = enemyHealth - Damage;

			heroEnergy = heroEnergy - 1;

			battleLogPrefs = getSharedPreferences("BattleLog", 0);
			damageDealt = battleLogPrefs.getInt("DamageDealt", 0);
			lifeTimeDamageDealt = battleLogPrefs.getInt("LifeTimeDamageDealt",
					0);

			Editor editor = battleLogPrefs.edit();
			editor.putInt("DamageDealt", damageDealt + Damage);
			editor.putInt("LifeTimeDamageDealt", lifeTimeDamageDealt + Damage);
			editor.commit();
		}

		progBarHealth.setProgress(0);
		progBarHealth.setProgress(heroHealth);

		progBarEnergy.setProgress(0);
		progBarEnergy.setProgress(heroEnergy);

		progBarEXP.setProgress(0);
		progBarEXP.setProgress(heroExp);

		progBarEnemyHealth.setProgress(0);
		progBarEnemyHealth.setProgress(enemyHealth);

		progBarEnemyEnergy.setProgress(0);
		progBarEnemyEnergy.setProgress(enemyEnergy);

		enemy_health.setText("Health: " + enemyHealth + " / " + enemyMaxHealth);

		hero_energy.setText("Energy: " + heroEnergy + " / " + heroEnergy);

		enemy_name.setBackgroundColor(getApplicationContext().getResources()
				.getColor(R.color.dark_grey));

		hero_name.setBackgroundColor(getApplicationContext().getResources()
				.getColor(R.color.black));

		bAttack.setText("Enemy's Turn");

		PlayerTurn = false;
	}

	// AI Attack Formula
	public void EnemyAttack() {

		int Damage = ((enemyAttack / heroPhDefense) + 1);

		if (enemyEnergy <= 0) {
			Damage = 0;
			enemyEnergy = enemyEnergy + 2;
		} else {

			heroHealth = heroHealth - Damage;

			battleLogPrefs = getSharedPreferences("BattleLog", 0);
			damageRecieved = battleLogPrefs.getInt("DamageRecieved", 0);
			lifeTimeDamageRecieved = battleLogPrefs.getInt(
					"LifeTimeDamageRecieved", 0);

			Editor editor = battleLogPrefs.edit();
			editor.putInt("DamageRecieved", damageRecieved + Damage);
			editor.putInt("LifeTimeDamageRecieved", lifeTimeDamageRecieved
					+ Damage);
			editor.commit();
		}

		progBarHealth.setProgress(0);
		progBarHealth.setProgress(heroHealth);

		progBarEnergy.setProgress(0);
		progBarEnergy.setProgress(heroEnergy);

		progBarEXP.setProgress(0);
		progBarEXP.setProgress(heroExp);

		progBarEnemyHealth.setProgress(0);
		progBarEnemyHealth.setProgress(enemyHealth);

		progBarEnemyEnergy.setProgress(0);
		progBarEnemyEnergy.setProgress(enemyEnergy);

		hero_health.setText("Health: " + heroHealth + " / " + heroMaxHealth);

		enemyEnergy = enemyEnergy - 1;
		enemy_energy.setText("Energy: " + enemyEnergy + " / " + enemyMaxEnergy);

		hero_name.setBackgroundColor(getApplicationContext().getResources()
				.getColor(R.color.dark_grey));

		enemy_name.setBackgroundColor(getApplicationContext().getResources()
				.getColor(R.color.black));

		bAttack.setText("Attack");

		PlayerTurn = true;
	}

	public void Defend() {

		if (heroEnergy + 2 > heroMaxEnergy) {
			heroEnergy = heroMaxEnergy;

		} else {
			heroEnergy = heroEnergy + 2;
		}

		hero_energy.setText("Energy: " + heroEnergy + " / " + heroEnergy);
		heroPhDefense = heroPhDefense + 2;

		PlayerTurn = false;

		EnemyAttack();
	}

	@Override
	public void onClick(View v) {

		// ATTACK BUTTON
		if (v.getId() == R.id.B_Battle_Attack) {

			if (PlayerTurn == true) {
				Attack();

			} else {
				EnemyAttack();
			}

		}

		// DEFEND BUTTON
		if (v.getId() == R.id.B_Battle_Defend) {

			hero_health
					.setText("Health: " + heroHealth + " / " + heroMaxHealth);
			hero_energy.setText("Energy: " + heroEnergy + " / " + heroEnergy);

			if (PlayerTurn == true) {
				Defend();
				heroPhDefense = heroPhDefense - 2;
			}
			if (PlayerTurn == false) {
				EnemyAttack();
			}
		}

		// BATTLE VICTORY
		if (enemyHealth <= 0) {

			enemyEnergy = 0;

			enemy_health.setText("Health: " + enemyHealth + " / "
					+ enemyMaxHealth);

			Victory();

			Intent intent = new Intent("com.example.rpg.BATTLELOG");
			startActivity(intent);

			finish();
		}

		// BATTLE DEFEAT
		if (heroHealth <= 0) {
			heroHealth = 0;

			hero_health
					.setText("Health: " + heroHealth + " / " + heroMaxHealth);

			Defeat();

			Intent intent = new Intent("com.example.rpg.BATTLELOG");
			startActivity(intent);

			finish();

		}

	}

	public void Victory() {

		db = helper.getWritableDatabase();

		ContentValues cv = new ContentValues();

		heroExp = heroExp + enemyExpValue;

		cv.put(colExp, heroExp);
		cv.put(colHealth, heroHealth);
		cv.put(colEnergy, heroEnergy);
		cv.put(colMana, heroMana);
		db.update(statsTable, cv, "1", null);

		if (heroExp >= heroMaxExp) {
			db.close();

			Toast.makeText(getApplicationContext(), "Level Up!",
					Toast.LENGTH_SHORT).show();

			if (heroClass.equals("Warrior")) {
				lvlUp.Warrior();
			}
			if (heroClass.equals("Mage")) {
				lvlUp.Mage();
			}
			if (heroClass.equals("Mercenary")) {
				lvlUp.Mercenary();
			}

		} else {

			db.close();
		}

	}

	private void Defeat() {
		Toast.makeText(getApplicationContext(),
				"Come back when you get stronger", Toast.LENGTH_LONG).show();

		db.close();

	}
}