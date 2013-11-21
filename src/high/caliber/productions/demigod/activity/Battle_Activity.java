package high.caliber.productions.demigod.activity;

import high.caliber.productions.demigod.R;
import high.caliber.productions.demigod.database.DbHero;
import high.caliber.productions.demigod.utils.LevelUpWorker;
import high.caliber.productions.demigod.utils.PrefsManager;
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

	static final String dbPath = DbHero.getPath();
	static final String dbName = DbHero.getDbName();
	static final String statsTable = DbHero.getTableStats();
	static final String inventoryTable = DbHero.getTableInventory();

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

	DbHero heroDbHelper;

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
	int enemyExpValue = 15;
	int enemyMaxHealth = 10;
	int enemyMaxEnergy = 4;

	int damageDealt, lifeTimeDamageDealt, damageRecieved,
			lifeTimeDamageRecieved;

	boolean PlayerTurn;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.battle);

		heroDbHelper = new DbHero(this);
		db = heroDbHelper.getWritableDatabase();

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
					heroEnergy = c.getInt(c.getColumnIndex(colMaxEnergy));
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

		lvlUp = new LevelUpWorker();

		// Create BattleLog pref files if not exist
		battleLogPrefs = getSharedPreferences(PrefsManager.getBattleLogPrefs(),
				0);
		damageDealt = battleLogPrefs.getInt(PrefsManager.getDamageDealt(), 0);
		lifeTimeDamageDealt = battleLogPrefs.getInt(
				PrefsManager.getLifeTimeDamageDealt(), 0);
		damageRecieved = battleLogPrefs.getInt(
				PrefsManager.getDamageRecieved(), 0);
		lifeTimeDamageRecieved = battleLogPrefs.getInt(
				PrefsManager.getLifetimeDamageRecieved(), 0);

		// Initialize variables (TextViews, Buttons, & ProgressBars)
		hero_name = (TextView) findViewById(R.id.tvBattle_Hero_Name);
		hero_name.setText(heroName);
		hero_name.bringToFront();

		hero_level = (TextView) findViewById(R.id.tvBattle_Hero_Level);
		hero_level.setText("LVL: " + heroLevel);
		hero_name.bringToFront();

		hero_health = (TextView) findViewById(R.id.tvBattle_Hero_Health);
		hero_health.setText("Health: " + heroHealth + " / " + heroMaxHealth);

		progBarHealth = (ProgressBar) findViewById(R.id.progBar_HeroHealth);
		progBarHealth.setProgress(heroHealth);
		progBarHealth.setMax(heroMaxHealth);

		hero_energy = (TextView) findViewById(R.id.tvBattle_Hero_Energy);
		hero_energy.setText("Energy: " + heroEnergy + " / " + heroEnergy);

		progBarEnergy = (ProgressBar) findViewById(R.id.progBar_HeroEnergy);
		progBarEnergy.setProgress(heroEnergy);
		progBarEnergy.setMax(heroEnergy);

		hero_exp = (TextView) findViewById(R.id.tvBattle_Hero_EXP);
		hero_exp.setText("EXP: " + heroExp + " / " + heroMaxExp);

		progBarEXP = (ProgressBar) findViewById(R.id.progBar_HeroExp);
		progBarEXP.setProgress(heroExp);
		progBarEXP.setMax(heroMaxExp);

		enemy_name = (TextView) findViewById(R.id.tvBattle_Enemy_Name);
		enemy_name.setText("Punk");

		enemy_level = (TextView) findViewById(R.id.tvBattle_Enemy_Level);
		enemy_level.setText(String.valueOf("LVL: " + enemyLevel));

		enemy_health = (TextView) findViewById(R.id.tvBattle_Enemy_Health);
		enemy_health.setText("Health: " + String.valueOf(enemyHealth) + " / "
				+ enemyMaxHealth);

		progBarEnemyHealth = (ProgressBar) findViewById(R.id.progBar_EnemyHealth);
		progBarEnemyHealth.setProgress(enemyHealth);
		progBarEnemyHealth.setMax(enemyMaxHealth);

		enemy_energy = (TextView) findViewById(R.id.tvBattle_Enemy_Energy);
		enemy_energy.setText("Energy: " + String.valueOf(enemyEnergy) + " / "
				+ enemyMaxEnergy);

		progBarEnemyEnergy = (ProgressBar) findViewById(R.id.progBar_EnemyEnergy);
		progBarEnemyEnergy.setProgress(enemyEnergy);
		progBarEnemyEnergy.setMax(enemyMaxEnergy);

		bAttack = (Button) findViewById(R.id.bBattle_Attack);
		bAttack.setOnClickListener(this);

		bDefend = (Button) findViewById(R.id.bBattle_Defend);
		bDefend.setOnClickListener(this);

		if (heroAgility >= enemyAgility) {
			PlayerTurn = true;
		} else {
			PlayerTurn = false;
			bAttack.setText("Enemy's Turn");
		}

		if (PlayerTurn == true) {
			Toast.makeText(getApplicationContext(), "Your turn",
					Toast.LENGTH_SHORT).show();
		} else {
			Toast.makeText(getApplicationContext(), "Enemy's Turn",
					Toast.LENGTH_SHORT).show();
		}
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

			battleLogPrefs = getSharedPreferences(
					PrefsManager.getBattleLogPrefs(), 0);
			damageDealt = battleLogPrefs.getInt(PrefsManager.getDamageDealt(),
					0);
			lifeTimeDamageDealt = battleLogPrefs.getInt(
					PrefsManager.getLifeTimeDamageDealt(), 0);

			Editor editor = battleLogPrefs.edit();
			editor.putInt(PrefsManager.getDamageDealt(), damageDealt + Damage);
			editor.putInt(PrefsManager.getLifeTimeDamageDealt(),
					lifeTimeDamageDealt + Damage);
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

			battleLogPrefs = getSharedPreferences(
					PrefsManager.getBattleLogPrefs(), 0);
			damageRecieved = battleLogPrefs.getInt(
					PrefsManager.getDamageRecieved(), 0);
			lifeTimeDamageRecieved = battleLogPrefs.getInt(
					PrefsManager.getLifetimeDamageRecieved(), 0);

			Editor editor = battleLogPrefs.edit();
			editor.putInt(PrefsManager.getDamageRecieved(), damageRecieved
					+ Damage);
			editor.putInt(PrefsManager.getLifetimeDamageRecieved(),
					lifeTimeDamageRecieved + Damage);
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
		if (v.getId() == R.id.bBattle_Attack) {

			if (PlayerTurn == true) {
				Attack();

			} else {
				EnemyAttack();
			}

		}

		// DEFEND BUTTON
		if (v.getId() == R.id.bBattle_Defend) {

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

			startActivity(new Intent(Battle_Activity.this, BattleLog.class));

			finish();
		}

		// BATTLE DEFEAT
		if (heroHealth <= 0) {
			heroHealth = 0;

			hero_health
					.setText("Health: " + heroHealth + " / " + heroMaxHealth);

			Defeat();

			startActivity(new Intent(Battle_Activity.this, BattleLog.class));

			finish();

		}

	}

	public void Victory() {

		db = heroDbHelper.getWritableDatabase();

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
