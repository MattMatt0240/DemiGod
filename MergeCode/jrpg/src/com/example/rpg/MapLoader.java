package com.example.rpg;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class MapLoader extends SurfaceView implements SurfaceHolder.Callback,
		Runnable {

	SurfaceHolder holder;
	Thread thread;

	Bitmap grass = BitmapFactory.decodeResource(getResources(),
			R.drawable.grass);
	boolean running = false;

	int[][] grassCoords = new int[][] { { 0, 0 }, { 16, 16 }, { 32, 32 } };

	public MapLoader(Context context) {
		super(context);

		holder = getHolder();
		holder.addCallback(this);
	}

	public MapLoader(Context context, AttributeSet attrs) {
		super(context, attrs);

		holder = getHolder();
		holder.addCallback(this);
	}

	public MapLoader(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);

		holder = getHolder();
		holder.addCallback(this);
	}

	public void pause() {
		running = false;

		while (running) {
			try {
				thread.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			break;
		}
		thread = null;
	}

	public void resume() {
		running = true;
		thread = new Thread(this);
		thread.start();

	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {

		running = true;
		thread = new Thread(this);
		thread.start();

	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		Canvas c = holder.lockCanvas();
		draw(c);
		holder.unlockCanvasAndPost(c);

	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {

	}

	@Override
	public void run() {

		while (running == true) {

			// performs drawing to the canvas
			if (!holder.getSurface().isValid()) {

				continue;
			}

			Canvas c = holder.lockCanvas();

			int x = 0;
			for (x = 0; x < grassCoords.length; x++) {
				c.drawBitmap(grass, grassCoords[x][0], grassCoords[x][1], null);
			}

			holder.unlockCanvasAndPost(c);

		}

	}
}