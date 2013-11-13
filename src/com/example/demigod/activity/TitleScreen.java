package com.example.demigod.activity;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.AnimationDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.demigod.R;
import com.example.demigod.database.DbHero;

public class TitleScreen extends Activity implements View.OnClickListener {

	ProgressBar progbar;
	ImageView loadingSprite;
	TextView tvMainTitle;
	Button bPlay;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		new DatabaseCreator().execute();
	}

	@Override
	public void onClick(View v) {

		if (v.getId() == R.id.bMainPlay) {
			startActivity(new Intent(TitleScreen.this, HeroHome.class));
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

					DbHero heroDbHelper = new DbHero(getApplicationContext());

					if (heroDbHelper.isCreated() == false) {
						SQLiteDatabase db = heroDbHelper.getWritableDatabase();
						heroDbHelper.PopulateInventoryFields();
						heroDbHelper.close();
					}

					progressCounter = 50;

					publishProgress(progressCounter);

					try {
						wait(1000);
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
		}
	}

}
