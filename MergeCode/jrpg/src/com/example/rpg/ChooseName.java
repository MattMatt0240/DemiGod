package com.example.rpg;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ChooseName extends Activity implements OnClickListener {

	static final String DBname = "Hero.db";
	static final String StatsTable = "Stats";
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
	static final String ColSpeed = "Speed";
	static final String ColAgility = "Agility";
	static final String ColDexterity = "Dexterity";
	String Hero_Name, Hero_Class, name;
	SharedPreferences prefs;
	SQLiteDatabase db;
	HeroDB helper;
	EditText NameBox;
	Button Confirm_Name;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.choose_name);

		helper = new HeroDB(this);
		db = helper.getWritableDatabase();

		NameBox = (EditText) findViewById(R.id.EDTXT_Name);

		Confirm_Name = (Button) findViewById(R.id.B_Confirm_Name);
		Confirm_Name.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {

		if (v.getId() == R.id.B_Confirm_Name) {

			name = NameBox.getText().toString();

			if (!(name.trim().length() == 0)) {

				Toast.makeText(getApplicationContext(),
						"So they call you " + name + " ? Alright then.",
						Toast.LENGTH_SHORT).show();

				ContentValues cv = new ContentValues();
				cv.put(ColID, 1);
				cv.put(ColClass, "");
				cv.put(ColName, name);
				cv.put(ColLevel, "");
				cv.put(ColExp, "");
				cv.put(ColMaxExp, "");
				cv.put(ColHealth, "");
				cv.put(ColMaxHealth, "");
				cv.put(ColEnergy, "");
				cv.put(ColMaxEnergy, "");
				cv.put(ColMana, "");
				cv.put(ColMaxMana, "");
				cv.put(ColAttack, "");
				cv.put(ColMagic, "");
				cv.put(ColPhDefense, "");
				cv.put(ColMgDefense, "");
				cv.put(ColSpeed, "");
				cv.put(ColAgility, "");
				cv.put(ColDexterity, "");

				db.insert(StatsTable, ColID, cv);
				db.close();

				prefs = getSharedPreferences("FirstRun", 0);

				Editor editor = prefs.edit();
				editor.putBoolean("NameChosen", true);
				editor.commit();

				Intent intent = new Intent("com.example.rpg.CHOOSE_CLASS");

				startActivity(intent);

				finish();
			} else {
				Toast.makeText(getBaseContext(), "Surely you have a name?",
						Toast.LENGTH_SHORT).show();
			}

		}
	}
}