package high.caliber.productions.demigod.settings;

import high.caliber.productions.demigod.R;

import java.io.IOException;
import java.io.InputStream;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.WindowManager;

public class DpadPosition extends Activity implements OnTouchListener {

	private Rect dPadContainer, bLeftRect, bRightRect, bUpRect, bDownRect;
	private Bitmap bLeft, bRight, bUp, bDown;
	Paint paint;

	SharedPreferences prefs;
	Editor editor;
	int containerX, containerY;

	int screenWidth, screenHeight;
	int buttonDimen;

	DpadSurface surface;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		Point size = new Point();
		WindowManager w;
		w = getWindowManager();
		w.getDefaultDisplay().getSize(size);

		screenWidth = size.x;
		screenHeight = size.y;

		getPosition();

		initBitmaps(buttonDimen);

		dPadContainer = new Rect(containerX, containerY, containerX
				+ (buttonDimen * 3), containerY + (buttonDimen * 3));

		paint = new Paint();

		surface = new DpadSurface(this);
		surface.setOnTouchListener(this);

		setContentView(surface);

	}

	@Override
	protected void onPause() {
		super.onPause();
		surface.pause();

		savePosition();
	}

	@Override
	protected void onResume() {
		super.onResume();
		surface.resume();

	}

	@Override
	protected void onDestroy() {
		super.onDestroy();

		savePosition();
	}

	/**
	 * Save D-Pad X and Y values to Shared Preferences
	 */
	public void savePosition() {

		prefs = getSharedPreferences(SettingsMain.SETTINGS_SHARED_PREFS,
				MODE_PRIVATE);
		editor = prefs.edit();
		editor.putInt(SettingsMain.KEY_DPAD_POS_X, (int) dPadContainer.left);
		editor.putInt(SettingsMain.KEY_DPAD_POS_Y, (int) dPadContainer.top);

		editor.commit();
	}

	/**
	 * Get D-Pad X and Y values from SharedPreferences
	 */
	private void getPosition() {

		prefs = getSharedPreferences(SettingsMain.SETTINGS_SHARED_PREFS,
				MODE_PRIVATE);
		editor = prefs.edit();

		int tempSize = (int) getResources().getDimension(R.dimen.button_size);

		buttonDimen = prefs.getInt(SettingsMain.KEY_DPAD_SIZE, tempSize);

		containerX = prefs.getInt(SettingsMain.KEY_DPAD_POS_X, 0);
		containerY = prefs.getInt(SettingsMain.KEY_DPAD_POS_Y, screenHeight
				- (buttonDimen * 3));
	}

	/**
	 * Gets bitmaps from assets and scales them
	 * 
	 * @param size
	 *            how big to scale buttons
	 */
	private void initBitmaps(int size) {

		BitmapFactory.Options opts = new BitmapFactory.Options();
		opts.inDither = true;
		opts.inPreferQualityOverSpeed = true;

		Bitmap bLeftTemp = getBitmapFromAssets("views/left_key.png", opts);
		bLeft = Bitmap.createScaledBitmap(bLeftTemp, buttonDimen, buttonDimen,
				true);
		bLeftTemp.recycle();
		bLeftTemp = null;
		bLeftRect = new Rect(0, screenHeight - (buttonDimen * 2), buttonDimen,
				screenHeight - buttonDimen);

		Bitmap bUpTemp = getBitmapFromAssets("views/up_key.png", opts);
		bUp = Bitmap
				.createScaledBitmap(bUpTemp, buttonDimen, buttonDimen, true);
		bUpTemp.recycle();
		bUpTemp = null;

		bUpRect = new Rect(buttonDimen, screenHeight - (buttonDimen * 3),
				buttonDimen * 2, screenHeight - (buttonDimen * 2));

		Bitmap bRightTemp = getBitmapFromAssets("views/right_key.png", opts);
		bRight = Bitmap.createScaledBitmap(bRightTemp, buttonDimen,
				buttonDimen, true);
		bRightTemp.recycle();
		bRightTemp = null;

		bRightRect = new Rect(buttonDimen * 2,
				screenHeight - (buttonDimen * 2), buttonDimen * 3, screenHeight
						- buttonDimen);

		Bitmap bDownTemp = getBitmapFromAssets("views/down_key.png", opts);
		bDown = Bitmap.createScaledBitmap(bDownTemp, buttonDimen, buttonDimen,
				true);
		bDownTemp.recycle();
		bDownTemp = null;

		bDownRect = new Rect(buttonDimen, screenHeight - buttonDimen,
				buttonDimen * 2, screenHeight);
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		int x = (int) event.getX();
		int y = (int) event.getY();

		switch (event.getAction()) {

		case MotionEvent.ACTION_DOWN:

			containerX = (int) (x - (buttonDimen * 1.5));
			containerY = (int) (y - (buttonDimen * 1.5));

			if (containerX < 0) {
				containerX = 0;
			}
			if (containerX + (buttonDimen * 3) > screenWidth) {
				containerX = screenWidth - (buttonDimen * 3);
			}
			if (containerY < 0) {
				containerY = 0;
			}
			if (containerY + (buttonDimen * 3) > screenHeight) {
				containerY = screenHeight - (buttonDimen * 3);
			}
			break;

		case MotionEvent.ACTION_MOVE:

			containerX = (int) (x - (buttonDimen * 1.5));
			containerY = (int) (y - (buttonDimen * 1.5));

			if (containerX < 0) {
				containerX = 0;
			}
			if (containerX + (buttonDimen * 3) > screenWidth) {
				containerX = screenWidth - (buttonDimen * 3);
			}
			if (containerY < 0) {
				containerY = 0;
			}
			if (containerY + (buttonDimen * 3) > screenHeight) {
				containerY = screenHeight - (buttonDimen * 3);
			}
			break;

		case MotionEvent.ACTION_UP:

			containerX = (int) (x - (buttonDimen * 1.5));
			containerY = (int) (y - (buttonDimen * 1.5));

			if (containerX < 0) {
				containerX = 0;
			}
			if (containerX + (buttonDimen * 3) > screenWidth) {
				containerX = screenWidth - (buttonDimen * 3);
			}
			if (containerY < 0) {
				containerY = 0;
			}
			if (containerY + (buttonDimen * 3) > screenHeight) {
				containerY = screenHeight - (buttonDimen * 3);
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

	public class DpadSurface extends SurfaceView implements
			SurfaceHolder.Callback, Runnable {

		volatile boolean running = false;
		private Thread t = null;
		private SurfaceHolder holder;
		private Paint paint;

		public DpadSurface(Context context) {
			super(context);
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

				c.drawColor(Color.BLACK);

				updateRects();
				c.drawRect(dPadContainer, paint);

				c.drawBitmap(bLeft, null, bLeftRect, null);
				c.drawBitmap(bUp, null, bUpRect, null);
				c.drawBitmap(bRight, null, bRightRect, null);
				c.drawBitmap(bDown, null, bDownRect, null);

				holder.unlockCanvasAndPost(c);

			}

		}

		private void updateRects() {
			dPadContainer.set(containerX, containerY, containerX
					+ (buttonDimen * 3), containerY + (buttonDimen * 3));

			bLeftRect.set(dPadContainer.left, dPadContainer.top + buttonDimen,
					dPadContainer.right - (buttonDimen * 2),
					dPadContainer.bottom - buttonDimen);

			bUpRect.set(dPadContainer.left + buttonDimen, dPadContainer.top,
					dPadContainer.right - buttonDimen, dPadContainer.bottom
							- (buttonDimen * 2));

			bRightRect.set(dPadContainer.left + (buttonDimen * 2),
					dPadContainer.top + buttonDimen, dPadContainer.right,
					dPadContainer.bottom - buttonDimen);

			bDownRect.set(dPadContainer.left + buttonDimen, dPadContainer.top
					+ (buttonDimen * 2), dPadContainer.right - buttonDimen,
					dPadContainer.bottom);
		}

		public void init() {

			holder = getHolder();
			holder.addCallback(this);

			paint = new Paint();
			paint.setDither(false);
			paint.setStrokeWidth(2);
			paint.setColor(Color.CYAN);

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
}