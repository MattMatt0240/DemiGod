package high.caliber.productions.demigod.activity;

import high.caliber.productions.demigod.R;
import high.caliber.productions.demigod.database.DbHero;
import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.TextView;

public class Status extends Activity {

	static final String DBname = "Stats.db";
	static final String Table = "Stats";
	static final String ColID = "_id";
	static final String ColClass = "Class";
	static final String ColName = "Name";
	static final String ColLevel = "Level";
	static final String ColExp = "Exp";
	static final String ColMaxExp = "MaxExp";
	static final String ColHealth = "Health";
	static final String ColMaxHealth = "MaxHealth";
	static final String ColEnergy = "Energy";
	static final String ColMaxEnergy = "MaxEnergy";
	static final String ColMana = "Mana";
	static final String ColMaxMana = "MaxMana";
	static final String ColAttack = "Attack";
	static final String ColMagic = "Magic";
	static final String ColPhDefense = "PhDefense";
	static final String ColMgDefense = "MgDefense";
	static final String ColAgility = "Agility";
	static final String ColDexterity = "Dexterity";

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
	int Hero_Speed;
	int Hero_Agility;
	int Hero_Dexterity;

	TextView Class, Name, Level, Health, Energy, Exp, Mana, Attack, Magic,
			PhDef, MgDef, Speed, Agility, Dexterity;

	ProgressBar healthbar, energybar, manabar, expbar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.status_screen);

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
					Hero_Class = c.getString(c.getColumnIndex(ColClass));
					Hero_Name = c.getString(c.getColumnIndex(ColName));
					Hero_Level = c.getInt(c.getColumnIndex(ColLevel));
					Hero_Exp = c.getInt(c.getColumnIndex(ColExp));
					Hero_MaxExp = c.getInt(c.getColumnIndex(ColMaxExp));
					Hero_Health = c.getInt(c.getColumnIndex(ColHealth));
					Hero_MaxHealth = c.getInt(c.getColumnIndex(ColMaxHealth));
					Hero_Energy = c.getInt(c.getColumnIndex(ColEnergy));
					Hero_MaxEnergy = c.getInt(c.getColumnIndex(ColMaxEnergy));
					Hero_Mana = c.getInt(c.getColumnIndex(ColMana));
					Hero_MaxMana = c.getInt(c.getColumnIndex(ColMaxMana));
					Hero_Attack = c.getInt(c.getColumnIndex(ColAttack));
					Hero_Magic = c.getInt(c.getColumnIndex(ColMagic));
					Hero_PhDefense = c.getInt(c.getColumnIndex(ColPhDefense));
					Hero_MgDefense = c.getInt(c.getColumnIndex(ColMgDefense));
					Hero_Agility = c.getInt(c.getColumnIndex(ColAgility));
					Hero_Dexterity = c.getInt(c.getColumnIndex(ColDexterity));

				} while (c.moveToNext());

			}
		}
		c.close();

		Class = (TextView) findViewById(R.id.tvStatus_Class);
		Class.setText(Hero_Class);

		Name = (TextView) findViewById(R.id.tvStatus_Name);
		Name.setText(Hero_Name);

		Level = (TextView) findViewById(R.id.tvStatus_Level);
		Level.setText(String.valueOf(Hero_Level));

		Health = (TextView) findViewById(R.id.tvStatus_Health);
		Health.setText(Hero_Health + " / " + String.valueOf(Hero_MaxHealth));

		healthbar = (ProgressBar) findViewById(R.id.progBar_Status_Health);
		healthbar.setProgress(Hero_Health);
		healthbar.setMax(Hero_MaxHealth);

		Energy = (TextView) findViewById(R.id.tvStatus_Energy);
		Energy.setText(Hero_Energy + " /" + String.valueOf(Hero_MaxEnergy));

		energybar = (ProgressBar) findViewById(R.id.progBar_Status_Energy);
		energybar.setProgress(Hero_Energy);
		energybar.setMax(Hero_MaxEnergy);

		Exp = (TextView) findViewById(R.id.tvStatus_Exp);
		Exp.setText(Hero_Exp + " / " + String.valueOf(Hero_MaxExp));

		expbar = (ProgressBar) findViewById(R.id.progBar_Status_Exp);
		expbar.setProgress(Hero_Exp);
		expbar.setMax(Hero_MaxExp);

		Mana = (TextView) findViewById(R.id.tvStatus_Mana);
		Mana.setText(Hero_Mana + " / " + String.valueOf(Hero_MaxMana));

		manabar = (ProgressBar) findViewById(R.id.progBar_Status_Mana);
		manabar.setProgress(Hero_Mana);
		manabar.setMax(Hero_MaxMana);

		Attack = (TextView) findViewById(R.id.tvStatus_Attack);
		Attack.setText(String.valueOf(Hero_Attack));

		Magic = (TextView) findViewById(R.id.tvStatus_Magic);
		Magic.setText(String.valueOf(Hero_Magic));

		PhDef = (TextView) findViewById(R.id.tvStatus_PhDefense);
		PhDef.setText(String.valueOf(Hero_PhDefense));

		MgDef = (TextView) findViewById(R.id.tvStatus_MgDefense);
		MgDef.setText(String.valueOf(Hero_MgDefense));

		Agility = (TextView) findViewById(R.id.tvStatus_Agility);
		Agility.setText(String.valueOf(Hero_Agility));

		Dexterity = (TextView) findViewById(R.id.tvStatus_Dexterity);
		Dexterity.setText(String.valueOf(Hero_Dexterity));

	}

	@Override
	protected void onResume() {
		super.onResume();

		healthbar.setProgress(0);
		healthbar.setProgress(Hero_Health);

		energybar.setProgress(0);
		energybar.setProgress(Hero_Energy);

		manabar.setProgress(0);
		manabar.setProgress(Hero_Mana);

		expbar.setProgress(0);
		expbar.setProgress(Hero_Exp);

	}
}
