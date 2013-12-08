package high.caliber.productions.demigod;

import high.caliber.productions.demigod.utils.JsonMapAdapter;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class JsonActivityTest extends Activity {

	TestSurfaceView view;
	JSONArray array;
	ArrayList<Tile> tileBitmaps;
	int tileDimen, mapWidth, mapHeight;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		tileDimen = (int) getResources().getDimension(R.dimen.tile_dimen);

		JsonMapAdapter adapter = new JsonMapAdapter(this);
		JSONObject map = adapter.loadJsonFromAssets("mapping/hero_home.json");
		mapWidth = adapter.getMapWidth(map);
		mapHeight = adapter.getMapHeight(map);
		try {
			array = map.getJSONArray("tile_data");
			tileBitmaps = adapter.convertMapData(array);
			Log.d("array conversion", "success");
		} catch (JSONException e) {
			e.printStackTrace();
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

			int x = 0;
			int y = 0;

			for (int i = 0; i < tileBitmaps.size(); i++) {
				Bitmap bitmap = tileBitmaps.get(i).getBitmap();

				c.drawBitmap(bitmap, x, y, null);
				x += tileDimen;

				if (x > (tileDimen * mapWidth) - tileDimen) {
					y += tileDimen;
					x = 0;
				}
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
