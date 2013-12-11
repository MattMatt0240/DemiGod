package high.caliber.productions.demigod;

import high.caliber.productions.demigod.utils.XmlMapAdapter;

import java.io.IOException;
import java.util.ArrayList;

import org.xmlpull.v1.XmlPullParserException;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class XmlActivityTest extends Activity {

	TestSurfaceView view;
	ArrayList<Tile> tiles;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		XmlMapAdapter adapter = new XmlMapAdapter(this);
		try {
			tiles = adapter.convertMapData(XmlMapAdapter.MAP_HOME);
		} catch (XmlPullParserException e) {
			e.printStackTrace();
			Log.d("test", "error");
		} catch (IOException e) {
			e.printStackTrace();
			Log.d("test", "error");
		}
		view = new TestSurfaceView(this);
		setContentView(view);
	}

	@Override
	protected void onPause() {
		super.onPause();
		view.pause();
	}

	@Override
	protected void onResume() {
		super.onResume();
		view.resume();
	}

	public class TestSurfaceView extends SurfaceView implements
			SurfaceHolder.Callback, Runnable {

		boolean running = false;
		private Thread t = null;
		private SurfaceHolder holder;
		private Context context;

		public TestSurfaceView(Context context) {
			super(context);
			this.context = context;
			init();
		}

		public TestSurfaceView(Context context, AttributeSet attrs) {
			super(context, attrs);
			this.context = context;
			init();
		}

		public TestSurfaceView(Context context, AttributeSet attrs, int defStyle) {
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

				holder.unlockCanvasAndPost(c);

			}

		}

		@Override
		protected void onDraw(Canvas c) {
			super.onDraw(c);

			for (int i = 0; i < tiles.size(); i++) {
				Tile tile = tiles.get(i);

				c.drawBitmap(tile.getBitmap(), tile.getX(), tile.getY(), null);
			}
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
}
