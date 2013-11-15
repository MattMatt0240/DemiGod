package com.example.rpg;

import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetManager;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.graphics.drawable.AnimationDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class SplashScreen extends Activity {

	SQLiteDatabase enemyDb, itemDb, heroDb;
	EnemyDB enemyDB;
	ItemDB itemDB;
	HeroDB heroDB;

	ImageView heroView;

	Bitmap heroSheet, hero1, hero2, hero3, hero4;

	TextView tvLoadDescription;

	AnimationDrawable anim;

	ProgressBar progBarLoad;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.splash);

		AssetManager manager = getAssets();
		Typeface font = Typeface.createFromAsset(manager, "fonts/infected.ttf");

		TextView tvTitle = (TextView) findViewById(R.id.tvSplashTitle);
		tvTitle.setTypeface(font);

		heroView = (ImageView) findViewById(R.id.ivSplashSprite);

		tvLoadDescription = (TextView) findViewById(R.id.tvSplashLoadDescription);

		heroView.setImageDrawable(getResources().getDrawable(
				R.drawable.shadow_knight_front1));

		anim = new AnimationDrawable();
		anim.addFrame(
				getResources().getDrawable(R.drawable.shadow_knight_front1),
				150);
		anim.addFrame(
				getResources().getDrawable(R.drawable.shadow_knight_front2),
				150);
		anim.addFrame(
				getResources().getDrawable(R.drawable.shadow_knight_front1),
				150);
		anim.addFrame(
				getResources().getDrawable(R.drawable.shadow_knight_front3),
				150);
		anim.setOneShot(false);

		heroView.setImageDrawable(anim);

		// Initialize a LoadViewTask object and call the execute() method
		new LoadViewTask().execute();

	}

	// To use the AsyncTask, it must be subclassed
	private class LoadViewTask extends AsyncTask<Void, Integer, Void> {
		// Before running code in separate thread
		@Override
		protected void onPreExecute() {
			anim.start();

			progBarLoad = (ProgressBar) findViewById(R.id.progBarSplashScreen);
			progBarLoad.setIndeterminate(false);
			progBarLoad.setProgress(0);
			progBarLoad.setMax(100);

		}

		// The code to be executed in a background thread.
		@Override
		protected Void doInBackground(Void... params) {
			// Get the current thread's token
			synchronized (this) {
				// Initialize an integer (that will act as a counter) to
				// zero
				int progressCounter = 0;

				try {
					while (progressCounter < 101) {

						heroDB = new HeroDB(getBaseContext());

						progressCounter = 5;
						publishProgress(progressCounter);
						wait(600);

						itemDB = new ItemDB(getBaseContext());

						progressCounter = 10;
						publishProgress(progressCounter);
						wait(600);

						enemyDB = new EnemyDB(getBaseContext());

						progressCounter = 15;
						publishProgress(progressCounter);
						wait(600);

						//
						// hero DB
						heroDb = heroDB.getWritableDatabase();

						heroDB.PopulateInventoryFields(heroDb);
						heroDb.close();

						progressCounter = 35;
						publishProgress(progressCounter);
						wait(300);

						//
						// enemy DB
						if (enemyDB.CheckDbExists() != true) {

							enemyDb = enemyDB.getWritableDatabase();

							try {

								enemyDB.onCreate(enemyDb);
								enemyDB.CreateCommonEnemyTable(enemyDb);

								Log.d("New Game Clicked", "Enemy Db Created");

							} catch (SQLiteException e) {
								e.printStackTrace();
							}
						} else {
							Log.d("Main",
									"Enemy DB Exists, Continuing to Choose Name");
						}

						progressCounter = 60;
						publishProgress(progressCounter);
						wait(300);

						//
						// Item DB
						itemDb = itemDB.getWritableDatabase();

						progressCounter = 65;
						publishProgress(progressCounter);
						wait(600);

						itemDB.PopulateWeaponsTable();

						progressCounter = 75;
						publishProgress(progressCounter);
						wait(600);

						itemDB.PopulateArmorTable();

						progressCounter = 85;
						publishProgress(progressCounter);
						wait(600);

						itemDB.PopulateConsumablesTable();

						progressCounter = 100;

						itemDb.close();

						progressCounter = 101;

					}
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

				// Set the current progress.
				// This value is going to be passed to the
				// onProgressUpdate() method.

				publishProgress(progressCounter);
			}

			return null;
		}

		// Update the progress
		@Override
		protected void onProgressUpdate(Integer... values) {
			// set the current progress of the progress dialog
			progBarLoad.setProgress(values[0]);

			switch (progBarLoad.getProgress()) {

			case 15:
				tvLoadDescription.setText("Skipping water across rocks...");
				break;

			case 35:
				tvLoadDescription.setText("Calling in sick...");
				break;

			case 60:
				tvLoadDescription.setText("Chasing Chickens...");
				break;

			case 65:
				tvLoadDescription.setText("Smelling the coffee...");
				break;

			case 75:
				tvLoadDescription.setText("Calling mom...");
				break;

			case 85:
				tvLoadDescription.setText("Walking on sunshine...");
				break;

			case 100:
				tvLoadDescription.setText("Finally...");
				break;

			}
		}

		// after executing the code in the thread
		@Override
		protected void onPostExecute(Void result) {
			// initialize the View
			startActivity(new Intent(SplashScreen.this, Main.class));

			finish();
		}
	}
}