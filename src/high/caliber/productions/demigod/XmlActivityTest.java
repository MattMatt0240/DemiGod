package high.caliber.productions.demigod;

import high.caliber.productions.demigod.activity.Home;
import high.caliber.productions.demigod.activity.HomeTown;
import high.caliber.productions.demigod.settings.SettingsMain;
import high.caliber.productions.demigod.utils.PixelUnitConverter;
import high.caliber.productions.demigod.utils.XmlMapAdapter;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import org.xmlpull.v1.XmlPullParserException;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.AnimationDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class XmlActivityTest extends Activity implements OnTouchListener {

	TestSurfaceView worldCanvas;
	ArrayList<Tile> tiles;
	ArrayList<Tile> objects;

	int anchorX = 0;
	int anchorY = 0;

	int screenWidth, screenHeight;

	int buttonDimen;
	Bitmap bLeft, bUp, bRight, bDown, aButton, bButton;
	Rect bLeftRect, bUpRect, bRightRect, bDownRect, aButtonRect, bButtonRect,
			dPadRect;

	int tileDimen;

	SurfaceHolder holder;
	Canvas c;

	Bitmap bitHeroFront;
	Hero hero;
	Rect heroRect;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

		worldCanvas = new TestSurfaceView(this);
		worldCanvas.setOnTouchListener(this);

		new WorldLoader().execute();
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

		int action = event.getAction();
		int x = (int) event.getX();
		int y = (int) event.getY();

		switch (action) {
		case MotionEvent.ACTION_DOWN:

			// Left Key Touched
			if (x <= bLeftRect.right && x >= bLeftRect.left
					&& y >= bLeftRect.top && y <= bLeftRect.bottom) {

				anchorX += tileDimen;

				// Up Key Touched
			} else if (x <= bUpRect.right && x >= bUpRect.left
					&& y >= bUpRect.top && y <= bUpRect.bottom) {
				anchorY += tileDimen;

				// Right Key Touched
			} else if (x <= bRightRect.right && x >= bRightRect.left
					&& y >= bRightRect.top && y <= bRightRect.bottom) {

				anchorX -= tileDimen;

				// Down Key Touched
			} else if (x <= bDownRect.right && x >= bDownRect.left
					&& y >= bDownRect.top && y <= bDownRect.bottom) {

				anchorY -= tileDimen;

				// A Button touched
			} else if (x <= aButtonRect.right && x >= aButtonRect.left
					&& y >= aButtonRect.top && y <= aButtonRect.bottom) {
				Toast.makeText(this, "A Button", Toast.LENGTH_SHORT).show();

				// B Button touched
			} else if (x <= bButtonRect.right && x >= bButtonRect.left
					&& y >= bButtonRect.top && y <= bButtonRect.bottom) {
				Toast.makeText(this, "B Button", Toast.LENGTH_SHORT).show();
			}
			v.invalidate();

			break;

		default:
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

	public class TestSurfaceView extends SurfaceView implements
			SurfaceHolder.Callback, Runnable {

		boolean running = false;
		private Thread t = null;
		private SurfaceHolder holder;

		public TestSurfaceView(Context context) {
			super(context);
			init();
		}

		public TestSurfaceView(Context context, AttributeSet attrs) {
			super(context, attrs);
			init();
		}

		public TestSurfaceView(Context context, AttributeSet attrs, int defStyle) {
			super(context, attrs, defStyle);
			init();
		}

		@Override
		public void surfaceCreated(SurfaceHolder holder) {
			c = holder.lockCanvas();
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

				c = holder.lockCanvas();

				draw(c);

				holder.unlockCanvasAndPost(c);
				postInvalidate();

			}

		}

		@Override
		protected void onDraw(Canvas c) {
			super.onDraw(c);

			for (int i = 0; i < tiles.size(); i++) {
				Tile tile = tiles.get(i);
				c.drawBitmap(tile.getBitmap(), anchorX + tile.getX(), anchorY
						+ tile.getY(), null);
			}

			for (int j = 0; j < objects.size(); j++) {
				Tile object = objects.get(j);
				c.drawBitmap(object.getBitmap(), anchorX + object.getX(),
						anchorY + object.getY(), null);
			}
			hero.update(System.currentTimeMillis());
			hero.draw(c);

			c.drawBitmap(bLeft, null, bLeftRect, null);
			c.drawBitmap(bUp, null, bUpRect, null);
			c.drawBitmap(bRight, null, bRightRect, null);
			c.drawBitmap(bDown, null, bDownRect, null);
			c.drawBitmap(aButton, null, aButtonRect, null);
			c.drawBitmap(bButton, null, bButtonRect, null);

			c.translate(anchorX, anchorY);
		}

		public void init() {
			holder = getHolder();
			holder.addCallback(this);
			setWillNotDraw(false);
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

	private class WorldLoader extends AsyncTask<Void, Integer, Void> {

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

					SharedPreferences prefs = getSharedPreferences(
							SettingsMain.SETTINGS_SHARED_PREFS, MODE_PRIVATE);

					PixelUnitConverter converter = new PixelUnitConverter(
							XmlActivityTest.this);
					int defaultSize = prefs.getInt(SettingsMain.KEY_DPAD_SIZE,
							35);

					buttonDimen = converter.dpToPx(defaultSize);

					tileDimen = converter.dpToPx(32);

					Point size = new Point();
					WindowManager w;
					w = getWindowManager();

					w.getDefaultDisplay().getSize(size);

					screenWidth = size.x;
					screenHeight = size.y;

					XmlMapAdapter adapter = new XmlMapAdapter(
							XmlActivityTest.this);
					try {
						tiles = adapter
								.convertMapData(XmlMapAdapter.MAP_HOME_TOWN);
						objects = adapter.getObjects();
					} catch (XmlPullParserException e) {
						e.printStackTrace();
						Log.d(XmlActivityTest.this.getPackageName().getClass()
								.getSimpleName(), "error parsing XML");
					} catch (IOException e) {
						e.printStackTrace();
						Log.d(XmlActivityTest.this.getPackageName().getClass()
								.getSimpleName(), "error loading asset");
					}

					BitmapFactory.Options opts = new BitmapFactory.Options();
					opts.inDither = true;
					opts.inPreferQualityOverSpeed = true;

					// buttons
					Bitmap bLeftTemp = getBitmapFromAssets(
							"views/left_key.png", opts);
					bLeft = Bitmap.createScaledBitmap(bLeftTemp, buttonDimen,
							buttonDimen, true);
					bLeftTemp.recycle();
					bLeftTemp = null;

					Bitmap bUpTemp = getBitmapFromAssets("views/up_key.png",
							opts);
					bUp = Bitmap.createScaledBitmap(bUpTemp, buttonDimen,
							buttonDimen, true);
					bUpTemp.recycle();
					bUpTemp = null;

					Bitmap bRightTemp = getBitmapFromAssets(
							"views/right_key.png", opts);
					bRight = Bitmap.createScaledBitmap(bRightTemp, buttonDimen,
							buttonDimen, true);
					bRightTemp.recycle();
					bRightTemp = null;

					Bitmap bDownTemp = getBitmapFromAssets(
							"views/down_key.png", opts);
					bDown = Bitmap.createScaledBitmap(bDownTemp, buttonDimen,
							buttonDimen, true);
					bDownTemp.recycle();
					bDownTemp = null;

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

					Bitmap tempHeroFront = getBitmapFromAssets(
							"drawables/x32/characters/knight/knight_male_front_spritesheet.png",
							opts);
					bitHeroFront = Bitmap.createScaledBitmap(tempHeroFront,
							tileDimen * 4, tileDimen, true);
					tempHeroFront.recycle();
					tempHeroFront = null;

					// NEED TO FIND A WAY TO ALIGN TILES TO HERO
					hero = new Hero(XmlActivityTest.this, bitHeroFront,
							screenWidth / 2, screenHeight / 2, 4, 4);

					int dPadX = prefs.getInt(SettingsMain.KEY_DPAD_POS_X, 0);
					int dPadY = prefs.getInt(
							SettingsMain.KEY_DPAD_POS_Y,
							screenHeight
									- (prefs.getInt(SettingsMain.KEY_DPAD_SIZE,
											buttonDimen)) * 3);

					dPadRect = new Rect(dPadX, dPadY,
							dPadX + (buttonDimen * 3), dPadY
									+ (buttonDimen * 3));

					bLeftRect = new Rect(dPadRect.left, dPadRect.top
							+ (buttonDimen),
							dPadRect.right - (buttonDimen * 2), dPadRect.bottom
									- buttonDimen);

					bUpRect = new Rect(dPadRect.left + buttonDimen,
							dPadRect.top, dPadRect.right - buttonDimen,
							dPadRect.bottom - (buttonDimen * 2));

					bRightRect = new Rect(dPadRect.left + (buttonDimen * 2),
							dPadRect.top + buttonDimen, dPadRect.right,
							dPadRect.bottom - buttonDimen);

					bDownRect = new Rect(dPadRect.left + buttonDimen,
							dPadRect.top + (buttonDimen * 2), dPadRect.right
									- buttonDimen, dPadRect.bottom);

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
