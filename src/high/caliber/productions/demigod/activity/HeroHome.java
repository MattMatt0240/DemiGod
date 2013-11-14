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

import java.io.IOException;
import java.io.InputStream;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.AnimationDrawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.Display;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import high.caliber.productions.demigod.R;
import high.caliber.productions.demigod.drawing.Sprite;
import high.caliber.productions.demigod.drawing.Tile;
 
public class HeroHome extends Activity implements View.OnTouchListener {

	private static int tileDimen;
	private WorldCanvas worldCanvas;
	private Sprite hero;
	private Bitmap spriteHeroFront, spriteHeroRight, wallHoriz, wallVert,
			floorHoriz, doorWood, chairBlue, tableWood, bedWood, shield;
	private Bitmap bLeft, bUp, bRight, bDown, aButton, bButton;
	private int[][] coordsWallHoriz, coordsWallVert, coordsFloorHoriz,
			coordsFloorVert;
	private Rect bLeftRect, bUpRect, bRightRect, bDownRect, aButtonRect,
			bButtonRect; // Buttons
	private Rect heroRect, perimeterRect, wallRect, tableRect, bedRect; // Collision
																		// Rects
	private int screenWidth, screenHeight;
	private Tile bedTile;
	private int heroDirection;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

		worldCanvas = new WorldCanvas(this);
		worldCanvas.setOnTouchListener(this);

		new BitmapLoader().execute();

	}

	@Override
	protected void onPause() {
		super.onPause();
		worldCanvas.pause();
	}

	@Override
	protected void onResume() {
		super.onResume();
		worldCanvas.resume();
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {

		int x = (int) event.getX();
		int y = (int) event.getY();

		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:

			// Left Key Touched
			if (x <= bLeftRect.right && x >= bLeftRect.left
					&& y >= bLeftRect.top && y <= bLeftRect.bottom) {

				hero.setX(hero.getX() - tileDimen);
				heroRect.set(hero.getX(), hero.getY(), hero.getX() + tileDimen,
						hero.getY() + tileDimen);

				if (!(perimeterRect.contains(heroRect))) {
					hero.setX(hero.getX() + tileDimen);
					heroRect.set(hero.getX(), hero.getY(), hero.getX()
							+ tileDimen, hero.getY() + tileDimen);
				} else if (heroRect.intersect(wallRect)
						|| heroRect.intersect(tableRect)
						|| heroRect.intersect(bedRect)) {
					hero.setX(hero.getX() + tileDimen);
					heroRect.set(hero.getX(), hero.getY(), hero.getX()
							+ tileDimen, hero.getY() + tileDimen);
				}

				// Up Key Touched
			} else if (x <= bUpRect.right && x >= bUpRect.left
					&& y >= bUpRect.top && y <= bUpRect.bottom) {

				hero.setY(hero.getY() - tileDimen);
				heroRect.set(hero.getX(), hero.getY(), hero.getX() + tileDimen,
						hero.getY() + tileDimen);

				if (!(perimeterRect.contains(heroRect))) {
					hero.setY(hero.getY() + tileDimen);
					heroRect.set(hero.getX(), hero.getY(), hero.getX()
							+ tileDimen, hero.getY() + tileDimen);

				} else if (heroRect.intersect(wallRect)
						|| heroRect.intersect(tableRect)
						|| heroRect.intersect(bedRect)) {
					hero.setY(hero.getY() + tileDimen);
					heroRect.set(hero.getX(), hero.getY(), hero.getX()
							+ tileDimen, hero.getY() + tileDimen);
				}

				// Right Key Touched
			} else if (x <= bRightRect.right && x >= bRightRect.left
					&& y >= bRightRect.top && y <= bRightRect.bottom) {

				hero = new Sprite(getBaseContext(), spriteHeroRight,
						hero.getX(), hero.getY(), 1, 1);

				hero.setX(hero.getX() + tileDimen);
				heroRect.set(hero.getX(), hero.getY(), hero.getX() + tileDimen,
						hero.getY() + tileDimen);

				if (!(perimeterRect.contains(heroRect))) {
					hero.setX(hero.getX() - tileDimen);
					heroRect.set(hero.getX(), hero.getY(), hero.getX()
							+ tileDimen, hero.getY() + tileDimen);
				} else if (heroRect.intersect(wallRect)
						|| heroRect.intersect(tableRect)
						|| heroRect.intersect(bedRect)) {
					hero.setX(hero.getX() - tileDimen);
					heroRect.set(hero.getX(), hero.getY(), hero.getX()
							+ tileDimen, hero.getY() + tileDimen);
				}

				// Down Key Touched
			} else if (x <= bDownRect.right && x >= bDownRect.left
					&& y >= bDownRect.top && y <= bDownRect.bottom) {

				hero = new Sprite(getBaseContext(), spriteHeroFront,
						hero.getX(), hero.getY(), 4, 4);

				hero.setY(hero.getY() + tileDimen);
				heroRect.set(hero.getX(), hero.getY(), hero.getX() + tileDimen,
						hero.getY() + tileDimen);

				if (!(perimeterRect.contains(heroRect))) {
					if (hero.getX() == tileDimen * 4
							&& hero.getY() == tileDimen * 7) {
						startActivity(new Intent(HeroHome.this, HomeTown.class));
						finish();
					} else {
						hero.setY(hero.getY() - tileDimen);
						heroRect.set(hero.getX(), hero.getY(), hero.getX()
								+ tileDimen, hero.getY() + tileDimen);
					}
				} else if (heroRect.intersect(wallRect)
						|| heroRect.intersect(tableRect)
						|| heroRect.intersect(bedRect)) {
					hero.setY(hero.getY() - tileDimen);
					heroRect.set(hero.getX(), hero.getY(), hero.getX()
							+ tileDimen, hero.getY() + tileDimen);
				}

				// A Button touched
			} else if (x <= aButtonRect.right && x >= aButtonRect.left
					&& y >= aButtonRect.top && y <= aButtonRect.bottom) {
				Toast.makeText(this, "A Button", Toast.LENGTH_SHORT).show();

				// B Button touched
			} else if (x <= bButtonRect.right && x >= bButtonRect.left
					&& y >= bButtonRect.top && y <= bButtonRect.bottom) {
				Toast.makeText(this, "B Button", Toast.LENGTH_SHORT).show();
			}

			break;
		}
		return true;
	}

	public Bitmap getBitmapFromAssets(String filePath,
			BitmapFactory.Options opts) {
		AssetManager manager = getAssets();
		InputStream inStream = null;
		try {
			inStream = manager.open(filePath);
		} catch (IOException e) {
			e.printStackTrace();
		}
		Bitmap bitmap = BitmapFactory.decodeStream(inStream, null, opts);
		return bitmap;
	}

	public class WorldCanvas extends SurfaceView implements
			SurfaceHolder.Callback, Runnable {

		volatile boolean running = false;
		private Thread t = null;
		private SurfaceHolder holder;
		private Context context;
		private Paint paint;

		public WorldCanvas(Context context) {
			super(context);
			this.context = context;
			init();
		}

		public WorldCanvas(Context context, AttributeSet attrs) {
			super(context, attrs);
			this.context = context;
			init();
		}

		public WorldCanvas(Context context, AttributeSet attrs, int defStyle) {
			super(context, attrs, defStyle);
			this.context = context;
			init();
		}

		@Override
		public void surfaceCreated(SurfaceHolder holder) {
			Canvas c = holder.lockCanvas();
			draw(c);
			holder.unlockCanvasAndPost(c);
		}

		@Override
		public void surfaceChanged(SurfaceHolder holder, int format, int width,
				int height) {
			running = true;
			t = new Thread(this);
			t.start();
		}

		@Override
		public void surfaceDestroyed(SurfaceHolder holder) {

		}

		@Override
		public void run() {

			while (running) {

				if (!holder.getSurface().isValid()) {
					continue;
				}

				Canvas c = holder.lockCanvas();

				c.drawColor(getResources().getColor(R.color.dark_grey));

				int x = 0;

				for (x = tileDimen; x < tileDimen * 14; x += tileDimen) {
					for (int y = tileDimen; y < tileDimen * 7; y += tileDimen) {
						c.drawBitmap(floorHoriz, x, y, null);
					}
				}
				for (x = 0; x < coordsWallHoriz.length; x++) {
					c.drawBitmap(wallHoriz, coordsWallHoriz[x][0],
							coordsWallHoriz[x][1], null);
				}
				for (x = 0; x < coordsWallVert.length; x++) {
					c.drawBitmap(wallVert, coordsWallVert[x][0],
							coordsWallVert[x][1], null);
				}

				// Objects
				c.drawBitmap(doorWood, tileDimen * 4, tileDimen * 7, null);
				c.drawBitmap(chairBlue, tileDimen * 8, tileDimen, null);
				c.drawBitmap(tableWood, tileDimen * 8, tileDimen * 2, null);
				c.drawBitmap(bedWood, tileDimen * 13, tileDimen, null);
				c.drawBitmap(shield, tileDimen * 3, 0, null);

				hero.draw(c);

				updateHero();

				// Buttons
				c.drawBitmap(bLeft, null, bLeftRect, null);
				c.drawBitmap(bUp, null, bUpRect, null);
				c.drawBitmap(bRight, null, bRightRect, null);
				c.drawBitmap(bDown, null, bDownRect, null);
				c.drawBitmap(aButton, null, aButtonRect, null);
				c.drawBitmap(bButton, null, bButtonRect, null);

				holder.unlockCanvasAndPost(c);

			}

		}

		public void init() {

			holder = getHolder();
			holder.addCallback(this);

			paint = new Paint();
			paint.setDither(false);
			paint.setColor(getResources().getColor(R.color.dark_grey));

			setWillNotDraw(false);
		}

		private void updateHero() {
			hero.update(System.currentTimeMillis());
		}

		public void pause() {
			running = false;

			while (running) {
				try {
					t.join();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				break;
			}
			t = null;
		}

		public void resume() {
			running = true;
			t = new Thread(this);
			t.start();

		}

	}

	private class BitmapLoader extends AsyncTask<Void, Integer, Void> {

		private TextView tvLoadDescription;
		private ProgressBar progBarLoad;

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

			anim.setOneShot(false);
			anim.start();

			ImageView ivLoadingSprite = (ImageView) findViewById(R.id.ivLoadingSprite);
			ivLoadingSprite.setImageDrawable(anim);

			tvLoadDescription = (TextView) findViewById(R.id.tvLoadingDescription);

			progBarLoad = (ProgressBar) findViewById(R.id.progBarLoadingScreen);
			progBarLoad.setIndeterminate(false);
			progBarLoad.setProgress(0);
			progBarLoad.setMax(100);

		}

		// The code to be executed in a background thread.
		@Override
		protected Void doInBackground(Void... params) {
			synchronized (this) {
				// Initialize an integer (that will act as a counter) to
				// zero
				int progressCounter = 0;

				while (progressCounter < 100) {

					tileDimen = (int) getResources().getDimension(
							R.dimen.tile_dimen);

					int buttonDimen = (int) getResources().getDimension(
							R.dimen.button_size);

					Point size = new Point();
					WindowManager w;
					w = getWindowManager();

					if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
						w.getDefaultDisplay().getSize(size);

						screenWidth = size.x;
						screenHeight = size.y;
					} else {
						Display d = w.getDefaultDisplay();
						screenWidth = d.getWidth();
						screenHeight = d.getHeight();
					}

					BitmapFactory.Options opts = new BitmapFactory.Options();
					opts.inDither = true;
					opts.inPreferQualityOverSpeed = true;

					
					//buttons
					Bitmap bLeftTemp = getBitmapFromAssets(
							"views/left_key.png", opts);
					bLeft = Bitmap.createScaledBitmap(bLeftTemp, buttonDimen,
							buttonDimen, true);
					bLeftTemp.recycle();
					bLeftTemp = null;

					bLeftRect = new Rect(0, screenHeight - (buttonDimen * 2),
							buttonDimen, screenHeight - buttonDimen);

					Bitmap bUpTemp = getBitmapFromAssets(
							"views/up_key.png", opts);
					bUp = Bitmap.createScaledBitmap(bUpTemp, buttonDimen,
							buttonDimen, true);
					bUpTemp.recycle();
					bUpTemp = null;

					bUpRect = new Rect(buttonDimen, screenHeight
							- (buttonDimen * 3), buttonDimen * 2, screenHeight
							- (buttonDimen * 2));

					Bitmap bRightTemp = getBitmapFromAssets(
							"views/right_key.png", opts);
					bRight = Bitmap.createScaledBitmap(bRightTemp, buttonDimen,
							buttonDimen, true);
					bRightTemp.recycle();
					bRightTemp = null;

					bRightRect = new Rect(buttonDimen * 2, screenHeight
							- (buttonDimen * 2), buttonDimen * 3, screenHeight
							- buttonDimen);

					Bitmap bDownTemp = getBitmapFromAssets(
							"views/down_key.png", opts);
					bDown = Bitmap.createScaledBitmap(bDownTemp, buttonDimen,
							buttonDimen, true);
					bDownTemp.recycle();
					bDownTemp = null;

					bDownRect = new Rect(buttonDimen, screenHeight
							- buttonDimen, buttonDimen * 2, screenHeight);

					Bitmap aButtonTemp = getBitmapFromAssets(
							"views/a_button.png", opts);
					aButton = Bitmap.createScaledBitmap(aButtonTemp,
							buttonDimen, buttonDimen, true);
					aButtonTemp.recycle();
					aButtonTemp = null;

					aButtonRect = new Rect(screenWidth - (buttonDimen * 2),
							screenHeight - (buttonDimen * 2), screenWidth
									- buttonDimen, screenHeight - buttonDimen);

					Bitmap bButtonTemp = getBitmapFromAssets(
							"views/b_button.png", opts);
					bButton = Bitmap.createScaledBitmap(bButtonTemp,
							buttonDimen, buttonDimen, true);
					bButtonTemp.recycle();
					bButtonTemp = null;

					bButtonRect = new Rect(screenWidth - (buttonDimen * 4),
							screenHeight - (buttonDimen * 2), screenWidth
									- (buttonDimen * 3), screenHeight
									- (buttonDimen));

									
					// objects
					Bitmap tempWallHoriz = getBitmapFromAssets(
							"drawables/x32/objects/wall_wood_top_horizontal.png",
							opts);
					wallHoriz = Bitmap.createScaledBitmap(tempWallHoriz,
							tileDimen, tileDimen, true);
					tempWallHoriz.recycle();
					tempWallHoriz = null;
					progressCounter = 10;
					publishProgress(progressCounter);

					Bitmap tempWallVert = getBitmapFromAssets(
							"drawables/x32/objects/wall_wood_top_vertical.png",
							opts);
					wallVert = Bitmap.createScaledBitmap(tempWallVert,
							tileDimen, tileDimen, true);
					tempWallVert.recycle();
					tempWallVert = null;
					progressCounter = 15;
					publishProgress(progressCounter);

					Bitmap tempFloorHoriz = getBitmapFromAssets(
							"drawables/x32/objects/floor_wood_horizontal.png", opts);
					floorHoriz = Bitmap.createScaledBitmap(tempFloorHoriz,
							tileDimen, tileDimen, true);
					tempFloorHoriz.recycle();
					tempFloorHoriz = null;
					progressCounter = 25;
					publishProgress(progressCounter);

					Bitmap tempDoor = getBitmapFromAssets(
							"drawables/x32/objects/door_wood.png", opts);
					doorWood = Bitmap.createScaledBitmap(tempDoor, tileDimen,
							tileDimen, true);
					tempDoor.recycle();
					tempDoor = null;

					Bitmap tempChair = getBitmapFromAssets(
							"drawables/x32/objects/chair_blue_front.png", opts);
					chairBlue = Bitmap.createScaledBitmap(tempChair, tileDimen,
							tileDimen, true);
					tempChair.recycle();
					tempChair = null;
					progressCounter = 35;
					publishProgress(progressCounter);

					Bitmap tempTable = getBitmapFromAssets(
							"drawables/x32/objects/table_wood.png", opts);
					tableWood = Bitmap.createScaledBitmap(tempTable, tileDimen,
							tileDimen, true);
					tempTable.recycle();
					tempTable = null;

					Bitmap tempHeroFront = getBitmapFromAssets(
							"drawables/x32/characters/knight/knight_male_front_spritesheet.png",
							opts);
					spriteHeroFront = Bitmap.createScaledBitmap(tempHeroFront,
							tileDimen * 4, tileDimen, true);
					tempHeroFront.recycle();
					tempHeroFront = null;

					Bitmap tempHeroRight = getBitmapFromAssets(
							"drawables/x32/characters/knight/knight_male_right1.png",
							opts);
					spriteHeroRight = Bitmap.createScaledBitmap(tempHeroRight,
							tileDimen, tileDimen, true);
					tempHeroRight.recycle();
					tempHeroRight = null;

					hero = new Sprite(getBaseContext(), spriteHeroFront,
							tileDimen * 5, tileDimen * 5, 4, 4);
					heroRect = hero.getCollisionRect();

					progressCounter = 45;
					publishProgress(progressCounter);

					Bitmap bedWoodTemp = getBitmapFromAssets(
							"drawables/x32/objects/bed_wood.png", opts);
					bedWood = Bitmap.createScaledBitmap(bedWoodTemp, tileDimen,
							tileDimen * 2, true);
					bedWoodTemp.recycle();
					bedWoodTemp = null;

					Bitmap shieldTemp = getBitmapFromAssets(
							"drawables/x32/objects/shield.png", opts);
					shield = Bitmap.createScaledBitmap(shieldTemp, tileDimen,
							tileDimen, true);
					shieldTemp.recycle();
					shieldTemp = null;

					bedTile = new Tile(getBaseContext(), bedWood,
							tileDimen * 13, tileDimen, tileDimen, tileDimen * 2);
					bedRect = bedTile.getCollisionRect();

					coordsWallHoriz = new int[][] { { tileDimen, 0 },
							{ tileDimen * 2, 0 }, { tileDimen * 3, 0 },
							{ tileDimen * 4, 0 }, { tileDimen * 5, 0 },
							{ tileDimen * 6, 0 }, { tileDimen * 7, 0 },
							{ tileDimen * 8, 0 }, { 0, tileDimen * 7 },
							{ tileDimen, tileDimen * 7 },
							{ tileDimen * 2, tileDimen * 7 },
							{ tileDimen * 3, tileDimen * 7 },
							{ tileDimen * 5, tileDimen * 7 },
							{ tileDimen * 6, tileDimen * 7 },
							{ tileDimen * 7, tileDimen * 7 },
							{ tileDimen * 8, tileDimen * 7 },
							{ tileDimen * 9, tileDimen * 7 },
							{ tileDimen * 10, tileDimen * 7 },
							{ tileDimen * 11, tileDimen * 7 },
							{ tileDimen * 12, tileDimen * 7 },
							{ tileDimen * 13, tileDimen * 7 },
							{ tileDimen * 14, tileDimen * 7 },
							{ tileDimen * 9, tileDimen * 3 },
							{ tileDimen * 9, 0 }, { tileDimen * 10, 0 },
							{ tileDimen * 11, 0 }, { tileDimen * 12, 0 },
							{ tileDimen * 13, 0 } };
					coordsWallVert = new int[][] { { 0, 0 }, { 0, tileDimen },
							{ 0, tileDimen * 2 }, { 0, tileDimen * 3 },
							{ 0, tileDimen * 4 }, { 0, tileDimen * 5 },
							{ 0, tileDimen * 6 }, { tileDimen * 9, 0 },
							{ tileDimen * 9, tileDimen },
							{ tileDimen * 9, tileDimen * 2 },
							{ tileDimen * 14, 0 },
							{ tileDimen * 14, tileDimen },
							{ tileDimen * 14, tileDimen * 2 },
							{ tileDimen * 14, tileDimen * 3 },
							{ tileDimen * 14, tileDimen * 4 },
							{ tileDimen * 14, tileDimen * 5 },
							{ tileDimen * 14, tileDimen * 6 } };

					perimeterRect = new Rect(tileDimen, tileDimen,
							tileDimen * 14, tileDimen * 7);
					wallRect = new Rect(tileDimen * 9, tileDimen,
							tileDimen * 10, tileDimen * 4);
					tableRect = new Rect(tileDimen * 8, tileDimen * 2,
							tileDimen * 9, tileDimen * 3);

					progressCounter = 100;
					publishProgress(progressCounter);

				}
			}

			return null;
		}

		// Update the progress
		@Override
		protected void onProgressUpdate(Integer... values) {
			// set the current progress of the progress dialog
			progBarLoad.setProgress(values[0]);

			switch (progBarLoad.getProgress()) {

			case 5:
				tvLoadDescription.setText("Skipping water across rocks...");
				break;

			case 10:
				tvLoadDescription.setText("Calling in sick...");
				break;

			case 15:
				tvLoadDescription.setText("Chasing Chickens...");
				break;

			case 20:
				tvLoadDescription.setText("Smelling the coffee...");
				break;

			case 25:
				tvLoadDescription.setText("Calling mom...");
				break;

			case 30:
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
			setContentView(worldCanvas);
		}
	}

}
