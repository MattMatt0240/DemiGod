/*
 Copyright (C) 2013  

 @author High Caliber Productions

 This program is free software: you can redistribute it and/or modify
 it under the terms of the GNU General Public License as published by
 the Free Software Foundation, either version 3 of the License, or
 (at your option) any later version.

 This program is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 GNU General Public License for more details.

 You should have received a copy of the GNU General Public License
 along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package high.caliber.productions.demigod.activity;

import high.caliber.productions.demigod.R;
import high.caliber.productions.demigod.database.EnemyDB;
import high.caliber.productions.demigod.database.HeroDB;
import high.caliber.productions.demigod.database.ItemDB;
import high.caliber.productions.demigod.settings.SettingsMain;
import high.caliber.productions.demigod.utils.PixelUnitConverter;
import high.caliber.productions.demigod.utils.SharedPrefsManager.BattleLogPrefs;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.AnimationDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class TitleScreen extends Activity implements View.OnClickListener {

	ProgressBar progbar;
	ImageView loadingSprite;
	TextView tvMainTitle;
	Button bPlay, bStatus, bBattleLog, bInventory, bBattle, bCreateHero,
			bDeleteGame;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		new DatabaseCreator().execute();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.titlescreen_menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle item selection
		switch (item.getItemId()) {
		case R.id.menu_title_settings:
			startActivity(new Intent(TitleScreen.this, SettingsMain.class));
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	@Override
	public void onClick(View v) {

		if (v.getId() == R.id.bMainPlay) {
			startActivity(new Intent(TitleScreen.this, Home.class));
		}

		if (v.getId() == R.id.bStatus) {
			startActivity(new Intent(TitleScreen.this, Status.class));
		}

		if (v.getId() == R.id.bBattleLog) {
			startActivity(new Intent(TitleScreen.this, BattleLog.class));
		}

		if (v.getId() == R.id.bInventory) {
			startActivity(new Intent(TitleScreen.this, Inventory.class));
		}

		if (v.getId() == R.id.bBattle) {
			startActivity(new Intent(TitleScreen.this, Battle_Activity.class));
		}

		if (v.getId() == R.id.bCreateHero) {
			startActivity(new Intent(TitleScreen.this, CreateHero.class));
		}

		if (v.getId() == R.id.bDeleteGame) {
			deleteDatabase(HeroDB.getDbName());

			Toast.makeText(this, "Succesfully deleted game data",
					Toast.LENGTH_SHORT).show();
		}

	}

	private class DatabaseCreator extends AsyncTask<Void, Integer, Void> {
		// Before running code in separate thread
		@Override
		protected void onPreExecute() {

			setContentView(R.layout.loading_screen);

			AnimationDrawable anim = new AnimationDrawable();
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

			progbar = (ProgressBar) findViewById(R.id.progBarLoadingScreen);
			progbar.setIndeterminate(false);
			progbar.setProgress(0);
			progbar.setMax(100);

			loadingSprite = (ImageView) findViewById(R.id.ivLoadingSprite);
			loadingSprite.setImageDrawable(anim);

			anim.setOneShot(false);
			anim.start();

		}

		@Override
		protected Void doInBackground(Void... params) {
			// Get the current thread's token
			synchronized (this) {

				int progressCounter = 0;

				while (progressCounter < 100) {

					EnemyDB enemyDbHelper = new EnemyDB(TitleScreen.this);

					if (!(enemyDbHelper.isCreated())) {
						SQLiteDatabase enemyDb = enemyDbHelper
								.getWritableDatabase();

						progressCounter = 15;
						publishProgress(progressCounter);

						enemyDbHelper.PopulateCommonTable(enemyDb);

						enemyDbHelper.close();
						enemyDb.close();
					}
					progressCounter = 15;
					publishProgress(progressCounter);

					ItemDB itemDbHelper = new ItemDB(TitleScreen.this);
					if (!(itemDbHelper.isCreated())) {
						SQLiteDatabase itemDb = itemDbHelper
								.getWritableDatabase();

						itemDbHelper.PopulateArmorTable();
						progressCounter = 25;
						publishProgress(progressCounter);

						itemDbHelper.PopulateWeaponsTable();
						progressCounter = 35;
						publishProgress(progressCounter);

						itemDbHelper.PopulateConsumablesTable();
						progressCounter = 45;
						publishProgress(progressCounter);

						itemDbHelper.close();

						// create initial values for D-Pad Size
						SharedPreferences prefs = getSharedPreferences(
								SettingsMain.SETTINGS_SHARED_PREFS,
								MODE_PRIVATE);

						PixelUnitConverter converter = new PixelUnitConverter(
								TitleScreen.this);

						int defaultValue = prefs.getInt(
								SettingsMain.KEY_DPAD_SIZE,
								converter.dpToPx(35));
						Editor editor = prefs.edit();
						editor.putInt(SettingsMain.KEY_DPAD_SIZE, defaultValue);
						editor.apply();
					}

					try {
						wait(800);
						progressCounter = 100;
						publishProgress(progressCounter);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}

				}
			}

			return null;
		}

		// Update the progress
		@Override
		protected void onProgressUpdate(Integer... values) {

			progbar.setProgress(values[0]);

		}

		@Override
		protected void onPostExecute(Void aVoid) {
			super.onPostExecute(aVoid);

			setContentView(R.layout.title_screen);

			tvMainTitle = (TextView) findViewById(R.id.tvMainHeader);

			bPlay = (Button) findViewById(R.id.bMainPlay);
			bPlay.setOnClickListener(TitleScreen.this);

			bStatus = (Button) findViewById(R.id.bStatus);
			bStatus.setOnClickListener(TitleScreen.this);

			bBattleLog = (Button) findViewById(R.id.bBattleLog);
			bBattleLog.setOnClickListener(TitleScreen.this);

			bInventory = (Button) findViewById(R.id.bInventory);
			bInventory.setOnClickListener(TitleScreen.this);

			bBattle = (Button) findViewById(R.id.bBattle);
			bBattle.setOnClickListener(TitleScreen.this);

			bCreateHero = (Button) findViewById(R.id.bCreateHero);
			bCreateHero.setOnClickListener(TitleScreen.this);

			bDeleteGame = (Button) findViewById(R.id.bDeleteGame);
			bDeleteGame.setOnClickListener(TitleScreen.this);

		}
	}

}
