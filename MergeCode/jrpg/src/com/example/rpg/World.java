package com.example.rpg;

import java.io.IOException;
import java.io.InputStream;

import android.annotation.SuppressLint;
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
import android.graphics.RectF;
import android.os.Build;
import android.os.Bundle;
import android.view.Display;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.WindowManager;

public class World extends Activity implements OnTouchListener {

	Bitmap map, sprite, knight, shop, shopSign, bLeft, bUp, bRight, bDown,
			fence_wood_horiz;

	BitmapFactory.Options opts;

	float spriteX, spriteY;

	ExploreView ev;

	Rect heroBox, enemyBox, shopBox, shopSignBox, fenceRect;
	Rect heroStatus, inventory;
	Rect screenRect, mapRect;
	Rect bLeftRect, bUpRect, bRightRect, bDownRect;
	RectF mapClip;

	int mapX, mapY, screenWidth, screenHeight, shopX, shopY, shopSignX,
			shopSignY, bLeftX, bLeftY, fenceX, fenceY;

	String statusBox = "Status";

	int buttonDimen, shopDimen;

	WindowManager w;

	float mapWidth, mapHeight, spriteWidth, spriteHeight;

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		Point size = new Point();
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

		mapWidth = getResources().getDimension(R.dimen.map_width);
		mapHeight = getResources().getDimension(R.dimen.map_height);

		spriteWidth = getResources().getDimension(R.dimen.hero_width);
		spriteHeight = getResources().getDimension(R.dimen.hero_height);

		shopDimen = (int) getResources().getDimension(R.dimen.shop_size);

		buttonDimen = (int) getResources().getDimension(R.dimen.button_size);

		screenRect = new Rect(0, 0, screenWidth, screenHeight);

		map = Bitmap.createScaledBitmap(
				getBitmapFromAssets("drawables/world_map.png"), (int) mapWidth,
				(int) mapHeight, true);

		mapRect = new Rect(0, 0, (int) mapWidth, (int) mapHeight);

		sprite = Bitmap.createScaledBitmap(
				getBitmapFromAssets("drawables/shadow_knight_front1.png"),
				(int) spriteWidth, (int) spriteHeight, true);

		shop = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(
				getResources(), R.drawable.building_shop1), shopDimen,
				shopDimen, true);

		shopSign = BitmapFactory.decodeResource(getResources(),
				R.drawable.sign_shop);

		fence_wood_horiz = BitmapFactory.decodeResource(getResources(),
				R.drawable.fence_wooden_horiz);

		bLeft = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(
				getResources(), R.drawable.left_key), buttonDimen, buttonDimen,
				true);

		bLeftRect = new Rect(0, screenHeight - (buttonDimen * 2), buttonDimen,
				screenHeight - buttonDimen);

		bUp = Bitmap
				.createScaledBitmap(BitmapFactory.decodeResource(
						getResources(), R.drawable.up_key), buttonDimen,
						buttonDimen, true);

		bUpRect = new Rect(buttonDimen, screenHeight - (buttonDimen * 3),
				buttonDimen * 2, screenHeight - (buttonDimen * 2));

		bRight = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(
				getResources(), R.drawable.right_key), buttonDimen,
				buttonDimen, true);

		bRightRect = new Rect(buttonDimen * 2,
				screenHeight - (buttonDimen * 2), buttonDimen * 3, screenHeight
						- buttonDimen);

		bDown = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(
				getResources(), R.drawable.down_key), buttonDimen, buttonDimen,
				true);

		bDownRect = new Rect(buttonDimen, screenHeight - buttonDimen,
				buttonDimen * 2, screenHeight);

		spriteX = sprite.getWidth() * 3;
		spriteY = sprite.getHeight() * 7;
		heroBox = new Rect((int) spriteX, (int) spriteY, (int) spriteX
				+ sprite.getWidth(), (int) spriteY + sprite.getHeight());

		enemyBox = new Rect(384, 64, 480, 128);

		shopX = (int) spriteWidth * 4;
		shopY = (int) spriteHeight * 3;
		shopBox = new Rect(shopX, shopY, shopX + shop.getWidth(), shopY
				+ (shop.getHeight() - 15));

		shopSignX = shopX + shop.getWidth();
		shopSignY = shopY + (shop.getHeight() - shopSign.getHeight());
		shopSignBox = new Rect(shopSignX, shopSignY,
				(shopSignX + shopSign.getWidth()),
				(shopSignY + shopSign.getHeight()));

		fenceX = 0;
		fenceY = 0;
		fenceRect = new Rect(fenceX, fenceY, fence_wood_horiz.getWidth(),
				fence_wood_horiz.getHeight());

		heroStatus = new Rect(screenWidth - 70, screenHeight - 50,
				screenWidth - 5, screenHeight - 5);

		inventory = new Rect((heroStatus.left - 85), heroStatus.top,
				(heroStatus.left - 20), heroStatus.bottom);

		mapX = 0;
		mapY = 0;

		ev = new ExploreView(this);
		ev.setOnTouchListener(this);

		setContentView(ev);

	}

	public Bitmap getBitmapFromAssets(String fileName) {
		AssetManager assetManager = getAssets();

		InputStream istr = null;
		try {
			istr = assetManager.open(fileName);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Bitmap bitmap = BitmapFactory.decodeStream(istr);

		return bitmap;
	}

	@Override
	protected void onPause() {
		super.onPause();
		ev.pause();
	}

	@Override
	protected void onResume() {
		super.onResume();
		ev.resume();
	}

	// seperate class (thread) to handle all of the graphics instead of activity
	// thread above
	public class ExploreView extends SurfaceView implements Runnable,
			SurfaceHolder.Callback {

		Thread t = null;
		SurfaceHolder holder;
		volatile boolean running = false;

		public ExploreView(Context context) {
			super(context);
			holder = getHolder();
			holder.addCallback(this);

		}

		@Override
		public void run() {

			while (running) {

				// performs drawing to the canvas
				if (!holder.getSurface().isValid()) {

					continue;
				}

				Canvas c = holder.lockCanvas();

				Paint paint = new Paint();

				c.drawBitmap(map, mapX, mapY, null);

				c.drawBitmap(shop, shopX, shopY, null);

				c.drawBitmap(shopSign, shopSignX, shopSignY, null);

				c.drawBitmap(fence_wood_horiz,
						fenceX + fence_wood_horiz.getWidth(), fenceY, null);
				c.drawBitmap(fence_wood_horiz,
						(fenceX + (fence_wood_horiz.getWidth() * 2)), fenceY,
						null);
				c.drawBitmap(fence_wood_horiz,
						(fenceX + (fence_wood_horiz.getWidth() * 3)), fenceY,
						null);
				c.drawBitmap(fence_wood_horiz,
						(fenceX + (fence_wood_horiz.getWidth() * 4)), fenceY,
						null);
				c.drawBitmap(fence_wood_horiz,
						(fenceX + (fence_wood_horiz.getWidth() * 5)), fenceY,
						null);

				paint.setColor(getContext().getResources().getColor(
						R.color.midnight_blue));
				c.drawRect(enemyBox, paint);

				paint.setColor(getContext().getResources().getColor(
						R.color.baby_blue));
				c.drawRect(heroStatus, paint);
				c.drawRect(inventory, paint);

				paint.setColor(getContext().getResources().getColor(
						R.color.black));
				c.drawText(statusBox, heroStatus.left + 5,
						heroStatus.bottom - 20, paint);
				c.drawText("Inventory", inventory.left + 5,
						inventory.bottom - 20, paint);

				c.drawBitmap(sprite, spriteX, spriteY, null);

				c.drawBitmap(bLeft, null, bLeftRect, null);

				c.drawBitmap(bUp, null, bUpRect, null);

				c.drawBitmap(bRight, null, bRightRect, null);

				c.drawBitmap(bDown, null, bDownRect, null);

				heroBox.set((int) spriteX, (int) spriteY, (int) spriteX
						+ sprite.getWidth(), (int) spriteY + sprite.getHeight());

				holder.unlockCanvasAndPost(c);

				if (heroBox.intersect(enemyBox)) {
					Intent intent = new Intent("com.example.rpg.BATTLE");
					startActivity(intent);
					finish();
				}

				else if (heroBox.intersect(shopBox)) {

					Intent intent = new Intent("com.example.rpg.SHOP");
					startActivity(intent);

					spriteX = shopBox.left + spriteWidth;
					spriteY = shopBox.bottom + spriteHeight;
				}

			}

		}

		public void pause() {
			running = false;

			boolean retry = true;
			running = false;
			while (retry) {
				try {
					t.join();
					retry = false;
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

		public void resume() {
			running = true;
			t = new Thread(this);
			t.start();

		}

		@Override
		public void surfaceChanged(SurfaceHolder holder, int format, int width,
				int height) {
			running = true;
			t = new Thread(this);
			t.start();

		}

		@Override
		public void surfaceCreated(SurfaceHolder holder) {
			Canvas c = getHolder().lockCanvas();
			draw(c);
			getHolder().unlockCanvasAndPost(c);

		}

		@Override
		public void surfaceDestroyed(SurfaceHolder holder) {
			// TODO Auto-generated method stub

		}
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {

		int x = (int) event.getX();
		int y = (int) event.getY();

		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:

			if (x >= heroStatus.left && y >= heroStatus.top) {
				Intent intent = new Intent("com.example.rpg.STATUS");
				startActivity(intent);
			}

			else if (x >= inventory.left && x <= inventory.right
					&& y >= inventory.top) {
				Intent intent = new Intent("com.example.rpg.INVENTORY");
				startActivity(intent);

			}

			// Left Key Touched
			else if (x <= bLeftRect.right && x >= bLeftRect.left
					&& y >= bLeftRect.top && y <= bLeftRect.bottom) {

				// if hero goes past left of screen
				if (spriteX - sprite.getWidth() <= 0) {
					spriteX = 0;
				}

				// map pan right
				else if (spriteX - (sprite.getWidth() * 4) <= 0 && mapX != 0) {

					panRight();
				}

				else {
					spriteX = spriteX - sprite.getWidth();
				}
				// Up Key Touched
			} else if (x <= bUpRect.right && x >= bUpRect.left
					&& y >= bUpRect.top && y <= bUpRect.bottom) {

				// if hero goes past top of screen
				if (spriteY - sprite.getHeight() <= 0) {
					spriteY = 0;
				}

				// map pan down
				else if (spriteY - (sprite.getHeight() * 4) <= 0 && mapY != 0) {

					panDown();
				}

				else {
					spriteY = spriteY - sprite.getHeight();
				}
				// Right Key Touched
			} else if (x <= bRightRect.right && x >= bRightRect.left
					&& y >= bRightRect.top && y <= bRightRect.bottom) {

				// if hero goes past right of screen
				if (spriteX + sprite.getWidth() >= screenWidth) {
					spriteX = screenWidth - sprite.getWidth();
				}
				// map pan left
				else if (spriteX + (sprite.getWidth() * 4) >= screenWidth
						&& mapX + mapRect.right >= screenWidth) {

					panLeft();
				}

				else {
					spriteX = spriteX + sprite.getWidth();
				}
				// Down Key Touched
			} else if (x <= bDownRect.right && x >= bDownRect.left
					&& y >= bDownRect.top && y <= bDownRect.bottom) {

				// if hero goes past bottom of screen
				if (spriteY + sprite.getHeight() >= screenHeight) {
					spriteY = screenHeight - sprite.getHeight() * 2;
				}

				// map pan up
				if (spriteY + (sprite.getHeight() * 4) >= screenHeight
						&& mapY + mapRect.bottom >= screenWidth) {

					panUp();
				}

				else {
					spriteY = spriteY + sprite.getHeight();
				}

			}

			break;
		}
		return true;
	}

	private void panRight() {
		mapX = mapX + sprite.getWidth();

		shopX = shopX + sprite.getWidth();

		shopBox.set(shopBox.left + sprite.getWidth(), shopBox.top,
				shopBox.right + sprite.getWidth(), shopBox.bottom);

		enemyBox.set(enemyBox.left + sprite.getWidth(), enemyBox.top,
				enemyBox.right + sprite.getWidth(), enemyBox.bottom);

		shopSignX = shopSignX + sprite.getWidth();

		fenceX = fenceX + sprite.getWidth();
	}

	private void panDown() {
		mapY = mapY + sprite.getHeight();

		shopY = shopY + sprite.getHeight();

		shopBox.set(shopBox.left, shopBox.top + sprite.getHeight(),
				shopBox.right, shopBox.bottom + sprite.getHeight());

		enemyBox.set(enemyBox.left, enemyBox.top + sprite.getHeight(),
				enemyBox.right, enemyBox.bottom + sprite.getHeight());

		shopSignY = shopSignY + sprite.getHeight();

		fenceY = fenceY + sprite.getHeight();
	}

	private void panLeft() {
		mapX = mapX - sprite.getWidth();

		shopX = shopX - sprite.getWidth();

		shopBox.set(shopBox.left - sprite.getWidth(), shopBox.top,
				shopBox.right - sprite.getWidth(), shopBox.bottom);

		enemyBox.set(enemyBox.left - sprite.getWidth(), enemyBox.top,
				enemyBox.right - sprite.getWidth(), enemyBox.bottom);

		shopSignX = shopSignX - sprite.getWidth();

		fenceX = fenceX - sprite.getWidth();
	}

	private void panUp() {
		mapY = mapY - sprite.getHeight();

		shopY = shopY - sprite.getHeight();

		shopBox.set(shopBox.left, shopBox.top - sprite.getHeight(),
				shopBox.right, shopBox.bottom - sprite.getHeight());

		enemyBox.set(enemyBox.left, enemyBox.top - sprite.getHeight(),
				enemyBox.right, enemyBox.bottom - sprite.getHeight());

		shopSignY = shopSignY - sprite.getHeight();

		fenceY = fenceY - sprite.getHeight();
	}
}