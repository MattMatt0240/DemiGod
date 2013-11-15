package com.example.rpg;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class ChooseClass extends Activity implements OnClickListener {

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
	SQLiteDatabase db;
	HeroDB helper;
	SharedPreferences prefs;
	TextView tvChoose, tvHeader, tvDescription;
	Button bWarrior, bMage, bMercenary, bConfirm, bCancel;
	Dialog dialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.choose_class);

		helper = new HeroDB(this);
		db = helper.getWritableDatabase();

		tvChoose = (TextView) findViewById(R.id.TV_Choose_Class);

		bWarrior = (Button) findViewById(R.id.B_Warrior);
		bWarrior.setOnClickListener(this);

		bMage = (Button) findViewById(R.id.B_Mage);
		bMage.setOnClickListener(this);

		bMercenary = (Button) findViewById(R.id.B_Mercenary);
		bMercenary.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {

		// Warrior
		if (v.getId() == R.id.B_Warrior) {

			AlertDialog.Builder helpBuilder = new AlertDialog.Builder(this);
			helpBuilder.setTitle(null);
			helpBuilder.setMessage("Are you sure?");
			helpBuilder.setPositiveButton("Confirm",
					new DialogInterface.OnClickListener() {

						public void onClick(DialogInterface dialog, int which) {
							try {

								ContentValues cv = new ContentValues();

								cv.put(colID, "1");
								cv.put(colClass, "Warrior");
								cv.put(colLevel, "1");
								cv.put(colExp, "0");
								cv.put(colMaxExp, "100");
								cv.put(colHealth, "20");
								cv.put(colMaxHealth, "20");
								cv.put(colEnergy, "8");
								cv.put(colMaxEnergy, "8");
								cv.put(colMana, "1");
								cv.put(colMaxMana, "1");
								cv.put(colAttack, "6");
								cv.put(colMagic, "3");
								cv.put(colPhDefense, "10");
								cv.put(colMgDefense, "7");
								cv.put(colSpeed, "5");
								cv.put(colAgility, "4");
								cv.put(colDexterity, "4");

								db.update(statsTable, cv, colID, null);

								prefs = getSharedPreferences("FirstRun", 0);

								Editor editor = prefs.edit();
								editor.putBoolean("ClassChosen", true);
								editor.commit();

								Log.d("Choose Character",
										"Warrior Character Created Successfully");
							} catch (SQLiteException e) {

								Log.d("Choose Character",
										"Error on Create Warrior Character");
							}

							helper.close();
							db.close();

							Intent intent = new Intent("com.example.rpg.BATTLE");
							startActivity(intent);

							Toast.makeText(getApplicationContext(),
									"Warrior Class Selected", Toast.LENGTH_LONG)
									.show();

							finish();

						}
					});
			helpBuilder.setNegativeButton("Cancel",
					new DialogInterface.OnClickListener() {

						public void onClick(DialogInterface dialog, int which) {
							// Do nothing but close the dialog
						}
					});

			// Remember, create doesn't show the dialog
			dialog = helpBuilder.create();
			dialog.show();

		}

		// Mage
		if (v.getId() == R.id.B_Mage)

		{

			AlertDialog.Builder helpBuilder = new AlertDialog.Builder(this);
			helpBuilder.setTitle(null);
			helpBuilder.setMessage("Are you sure?");
			helpBuilder.setPositiveButton("Confirm",
					new DialogInterface.OnClickListener() {

						public void onClick(DialogInterface dialog, int which) {
							try {

								ContentValues cv = new ContentValues();

								cv.put(colID, "1");
								cv.put(colClass, "Mage");
								cv.put(colLevel, "1");
								cv.put(colExp, "0");
								cv.put(colMaxExp, "100");
								cv.put(colHealth, "10");
								cv.put(colMaxHealth, "10");
								cv.put(colEnergy, "1");
								cv.put(colMaxEnergy, "1");
								cv.put(colMana, "15");
								cv.put(colMaxMana, "15");
								cv.put(colAttack, "3");
								cv.put(colMagic, "10");
								cv.put(colPhDefense, "3");
								cv.put(colMgDefense, "9");
								cv.put(colSpeed, "7");
								cv.put(colAgility, "3");
								cv.put(colDexterity, "5");

								db.update(statsTable, cv, colID, null);

								prefs = getSharedPreferences("FirstRun", 0);

								Editor editor = prefs.edit();
								editor.putBoolean("ClassChosen", true);
								editor.commit();

								Log.d("Choose Character",
										"Mage Character Created Successfully");

							} catch (SQLiteException e) {
								Log.d("Choose Character",
										"Error on Create Mage Character");
							}

							helper.close();
							db.close();

							Intent intent = new Intent("com.example.rpg.BATTLE");
							startActivity(intent);

							Toast.makeText(getApplicationContext(),
									"Mage Class Selected", Toast.LENGTH_LONG)
									.show();

							finish();

						}
					});
			helpBuilder.setNegativeButton("Cancel",
					new DialogInterface.OnClickListener() {

						public void onClick(DialogInterface dialog, int which) {
							// Do nothing but close the dialog
						}
					});

			// Remember, create doesn't show the dialog
			dialog = helpBuilder.create();
			dialog.show();

		}

		// Mercenary
		if (v.getId() == R.id.B_Mercenary)

		{

			AlertDialog.Builder helpBuilder = new AlertDialog.Builder(this);
			helpBuilder.setTitle(null);
			helpBuilder.setMessage("Are you sure?");
			helpBuilder.setPositiveButton("Confirm",
					new DialogInterface.OnClickListener() {

						public void onClick(DialogInterface dialog, int which) {

							try {

								ContentValues cv = new ContentValues();

								cv.put(colID, "1");
								cv.put(colClass, "Mercenary");
								cv.put(colLevel, "1");
								cv.put(colExp, "0");
								cv.put(colMaxExp, "100");
								cv.put(colHealth, "15");
								cv.put(colMaxHealth, "15");
								cv.put(colEnergy, "15");
								cv.put(colMaxEnergy, "15");
								cv.put(colMana, "1");
								cv.put(colMaxMana, "1");
								cv.put(colAttack, "8");
								cv.put(colMagic, "3");
								cv.put(colPhDefense, "6");
								cv.put(colMgDefense, "4");
								cv.put(colSpeed, "9");
								cv.put(colAgility, "6");
								cv.put(colDexterity, "10");

								db.update(statsTable, cv, colID, null);

								prefs = getSharedPreferences("FirstRun", 0);

								Editor editor = prefs.edit();
								editor.putBoolean("ClassChosen", true);
								editor.commit();

								Log.d("Choose Character",
										"Mercenary Character Created Successfully");

							} catch (SQLiteException e) {
								Log.d("Choose Character",
										"Error on Create Mercenary Character");
							}

							helper.close();
							db.close();

							Intent intent = new Intent("com.example.rpg.BATTLE");
							startActivity(intent);

							Toast.makeText(getApplicationContext(),
									"Mercenary Class Selected",
									Toast.LENGTH_LONG).show();

							finish();

						}
					});
			helpBuilder.setNegativeButton("Cancel",
					new DialogInterface.OnClickListener() {

						public void onClick(DialogInterface dialog, int which) {
							// Do nothing but close the dialog
						}
					});

			// Remember, create doesn't show the dialog
			dialog = helpBuilder.create();
			dialog.show();

		}

	}
}