package high.caliber.productions.demigod.activity;

import high.caliber.productions.demigod.R;
import high.caliber.productions.demigod.database.HeroDB;
import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.TextView;

public class Status extends Activity {

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

	HeroDB heroDbHelper;
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

	TextView Class, Name, Level, Health, Energy, Exp, Mana, Attack, Magic,
			PhDef, MgDef, Speed, Agility, Dexterity;

	ProgressBar healthbar, energybar, manabar, expbar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.status_screen);

		heroDbHelper = new HeroDB(this);
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

		Class = (TextView) findViewById(R.id.tvStatus_Class);
		Class.setText(heroClass);

		Name = (TextView) findViewById(R.id.tvStatus_Name);
		Name.setText(heroName);

		Level = (TextView) findViewById(R.id.tvStatus_Level);
		Level.setText(String.valueOf(heroLevel));

		Health = (TextView) findViewById(R.id.tvStatus_Health);
		Health.setText(heroHealth + " / " + String.valueOf(heroMaxHealth));

		healthbar = (ProgressBar) findViewById(R.id.progBar_Status_Health);
		healthbar.setProgress(heroHealth);
		healthbar.setMax(heroMaxHealth);

		Energy = (TextView) findViewById(R.id.tvStatus_Energy);
		Energy.setText(heroEnergy + " /" + String.valueOf(heroMaxEnergy));

		energybar = (ProgressBar) findViewById(R.id.progBar_Status_Energy);
		energybar.setProgress(heroEnergy);
		energybar.setMax(heroMaxEnergy);

		Exp = (TextView) findViewById(R.id.tvStatus_Exp);
		Exp.setText(heroExp + " / " + String.valueOf(heroMaxExp));

		expbar = (ProgressBar) findViewById(R.id.progBar_Status_Exp);
		expbar.setProgress(heroExp);
		expbar.setMax(heroMaxExp);

		Mana = (TextView) findViewById(R.id.tvStatus_Mana);
		Mana.setText(heroMana + " / " + String.valueOf(heroMaxMana));

		manabar = (ProgressBar) findViewById(R.id.progBar_Status_Mana);
		manabar.setProgress(heroMana);
		manabar.setMax(heroMaxMana);

		Attack = (TextView) findViewById(R.id.tvStatus_Attack);
		Attack.setText(String.valueOf(heroAttack));

		Magic = (TextView) findViewById(R.id.tvStatus_Magic);
		Magic.setText(String.valueOf(heroMagic));

		PhDef = (TextView) findViewById(R.id.tvStatus_PhDefense);
		PhDef.setText(String.valueOf(heroPhDefense));

		MgDef = (TextView) findViewById(R.id.tvStatus_MgDefense);
		MgDef.setText(String.valueOf(heroMgDefense));

		Agility = (TextView) findViewById(R.id.tvStatus_Agility);
		Agility.setText(String.valueOf(heroAgility));

		Dexterity = (TextView) findViewById(R.id.tvStatus_Dexterity);
		Dexterity.setText(String.valueOf(heroDexterity));

	}

	@Override
	protected void onResume() {
		super.onResume();

		healthbar.setProgress(0);
		healthbar.setProgress(heroHealth);

		energybar.setProgress(0);
		energybar.setProgress(heroEnergy);

		manabar.setProgress(0);
		manabar.setProgress(heroMana);

		expbar.setProgress(0);
		expbar.setProgress(heroExp);

	}
}
