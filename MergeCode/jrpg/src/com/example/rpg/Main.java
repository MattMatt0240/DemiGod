package com.example.rpg;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class Main extends Activity implements OnClickListener {

	String firstRun = "FirstRun";
	Button newGame, delete, quit, battle, world, status, test;

	SharedPreferences prefs;

	boolean nameChosen, classChosen;

	Thread loadDbs;

	Context context;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		context = getBaseContext();

		setContentView(R.layout.main);

		prefs = getSharedPreferences(firstRun, 0);
		nameChosen = prefs.getBoolean("NameChosen", false);
		classChosen = prefs.getBoolean("ClassChosen", false);

		newGame = (Button) findViewById(R.id.B_NewGame);
		newGame.setOnClickListener(this);

		delete = (Button) findViewById(R.id.B_Delete);
		delete.setOnClickListener(this);

		quit = (Button) findViewById(R.id.B_Quit);
		quit.setOnClickListener(this);

		battle = (Button) findViewById(R.id.B_Battle);
		battle.setOnClickListener(this);

		world = (Button) findViewById(R.id.B_World_Map);
		world.setOnClickListener(this);

		status = (Button) findViewById(R.id.B_Status);
		status.setOnClickListener(this);

		test = (Button) findViewById(R.id.B_Test);
		test.setOnClickListener(this);

		if (nameChosen == true && classChosen == true) {
			newGame.setText("Continue");
		}

	}

	@Override
	public void onClick(View v) {

		nameChosen = prefs.getBoolean("NameChosen", false);
		classChosen = prefs.getBoolean("ClassChosen", false);

		switch (v.getId()) {
		case R.id.B_NewGame:

			if (nameChosen == true) {
				if (classChosen == true) {
					newGame.setText("Continue");

					Intent intent = new Intent("com.example.rpg.BATTLE");
					startActivity(intent);

				} else {
					Intent intent = new Intent("com.example.rpg.CHOOSE_CLASS");
					startActivity(intent);
				}
			} else {

				Intent intent = new Intent("com.example.rpg.PROLOGUE");
				startActivity(intent);

			}

			break;

		case R.id.B_Delete: {

			Toast.makeText(getApplicationContext(), "Deleting game and stats",
					Toast.LENGTH_SHORT).show();

			try {

				deleteDatabase(HeroDB.dbName);
				deleteDatabase(EnemyDB.dbName);
				deleteDatabase(ItemDB.dbName);

				Editor editor = prefs.edit();
				editor.clear();
				editor.commit();

				SharedPreferences prefs2 = getSharedPreferences("BattleLog", 0);
				Editor editor2 = prefs2.edit();
				editor2.clear();
				editor2.commit();

				Log.d("Delete Data", "All Data Deleted Successfully");

				newGame.setText("New Game");

			} catch (SQLiteException e) {
				Log.d("Delete Data", "Error on Deleting Saved Data");

			}
		}
			break;

		case R.id.B_Quit: {
			finish();

		}
			break;

		case R.id.B_Battle: {

			if (nameChosen == false && classChosen == false) {
				Toast.makeText(getApplicationContext(),
						"Create a Character First", Toast.LENGTH_SHORT).show();

			} else {
				Intent intent = new Intent("com.example.rpg.BATTLE");
				startActivity(intent);

			}
		}
			break;

		case R.id.B_World_Map: {

			if (nameChosen == false && classChosen == false) {
				Toast.makeText(getApplicationContext(),
						"Create a Character First", Toast.LENGTH_SHORT).show();

			} else {
				Intent intent = new Intent("com.example.rpg.WORLD");
				startActivity(intent);
			}
		}
			break;

		case R.id.B_Status: {

			if (nameChosen == false && classChosen == false) {
				Toast.makeText(getApplicationContext(),
						"Create a Character First", Toast.LENGTH_SHORT).show();

			} else {
				Intent intent = new Intent("com.example.rpg.STATUS");
				startActivity(intent);
			}
		}
			break;

		case R.id.B_Test: {

			Intent intent = new Intent("com.example.rpg.TEST");
			startActivity(intent);

		}
			break;
		}
	}

	@Override
	protected void onResume() {
		super.onResume();

		nameChosen = prefs.getBoolean("NameChosen", false);
		classChosen = prefs.getBoolean("ClassChosen", false);

		if (nameChosen == true && classChosen == true) {
			newGame.setText("Continue");
		}
	}
}