package high.caliber.productions.demigod.utils;

import high.caliber.productions.demigod.Tile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;

public class JsonMapAdapter {

	public static final int TILE_GRASS1 = 1;
	public static final int TILE_GRASS2 = 2;
	public static final int TILE_GRASS3 = 3;
	public static final int TILE_WALL_WOOD_VERTICAL = 4;
	public static final int TILE_WALL_WOOD_HORIZONTAL = 5;
	public static final int TILE_FLOOR_WOOD_VERTICAL = 6;
	public static final int TILE_FLOOR_WOOD_HORIZONTAL = 7;
	public static final int TILE_DOOR_WOOD = 8;
	public static final int TILE_TABLE_WOOD = 9;
	public static final int TILE_BED_WOOD = 10;
	public static final int TILE_CHAIR_BLUE = 11;

	private Context context;
	private JSONObject jsonObj, map;
	private String jsonData;

	public JsonMapAdapter(Context context) {
		this.context = context;
	}

	public JSONObject loadJsonFromAssets(String path) {
		try {

			InputStream is = context.getAssets().open(path);
			int size = is.available();
			byte[] buffer = new byte[size];
			is.read(buffer);
			is.close();
			jsonData = new String(buffer, "UTF-8");
			jsonObj = new JSONObject(jsonData);
			map = jsonObj.getJSONObject("map");
			// Log.d("JSON Text", jsonData);

		} catch (IOException e) {
			e.printStackTrace();
			return null;
		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		}
		return map;

	}

	public String jsonText(JSONObject object) {
		return object.toString();
	}

	public JSONArray getTileArray(JSONObject obj) {
		try {
			String tiles = obj.getString("tile_data");
			JSONArray tileArrayData = obj.getJSONArray(tiles);
			return tileArrayData;
		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		}
	}

	public int getMapWidth(JSONObject obj) {
		try {
			return obj.getInt("map_width");
		} catch (JSONException e) {
			e.printStackTrace();
			return 0;
		}
	}

	public int getMapHeight(JSONObject obj) {
		try {
			return obj.getInt("map_height");
		} catch (JSONException e) {
			e.printStackTrace();
			return 0;
		}
	}

	public Bitmap getBitmapFromAssets(String path) {
		AssetManager manager = context.getAssets();

		PixelUnitConverter pxConverter = new PixelUnitConverter(context);
		int tileDimen = pxConverter.dpToPx(32);
		BitmapFactory.Options opts = new BitmapFactory.Options();
		opts.inDither = true;
		opts.inPreferQualityOverSpeed = true;
		try {
			Bitmap tempBitmap = BitmapFactory.decodeStream(manager.open(path),
					null, opts);
			Bitmap bitmap = Bitmap.createScaledBitmap(tempBitmap, tileDimen,
					tileDimen, true);
			return bitmap;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	public ArrayList<Tile> convertMapData(JSONArray jsonTileArray)
			throws JSONException {
		ArrayList<Tile> tiles = new ArrayList<Tile>();
		Bitmap bitmap;
		Tile tile;

		for (int i = 0; i < jsonTileArray.length(); i++) {
			int tileID = jsonTileArray.getInt(i);

			switch (tileID) {
			case 0:
				bitmap = Bitmap.createBitmap(1, 1, Config.ALPHA_8);
				tile = new Tile(context, bitmap, 0, 0, bitmap.getWidth(),
						bitmap.getHeight(), false);
				tiles.add(i, tile);
				break;

			case 1:
				bitmap = getBitmapFromAssets("drawables/x32/tiles/grass1.png");
				tile = new Tile(context, bitmap, 0, 0, bitmap.getWidth(),
						bitmap.getHeight(), false);
				tiles.add(i, tile);
				break;

			case 2:
				bitmap = getBitmapFromAssets("drawables/x32/tiles/grass2.png");
				tile = new Tile(context, bitmap, 0, 0, bitmap.getWidth(),
						bitmap.getHeight(), false);
				tiles.add(i, tile);
				break;

			case 3:
				bitmap = getBitmapFromAssets("drawables/x32/tiles/grass3.png");
				tile = new Tile(context, bitmap, 0, 0, bitmap.getWidth(),
						bitmap.getHeight(), false);
				tiles.add(i, tile);
				break;

			case 4:
				bitmap = getBitmapFromAssets("drawables/x32/tiles/wall_wood_top_vertical.png");
				tile = new Tile(context, bitmap, 0, 0, bitmap.getWidth(),
						bitmap.getHeight(), true);
				tiles.add(i, tile);
				break;

			case 5:
				bitmap = getBitmapFromAssets("drawables/x32/tiles/wall_wood_top_horizontal.png");
				tile = new Tile(context, bitmap, 0, 0, bitmap.getWidth(),
						bitmap.getHeight(), true);
				tiles.add(i, tile);
				break;

			case 6:
				bitmap = getBitmapFromAssets("drawables/x32/tiles/floor_wood_vertical.png");
				tile = new Tile(context, bitmap, 0, 0, bitmap.getWidth(),
						bitmap.getHeight(), false);
				tiles.add(i, tile);
				break;

			case 7:
				bitmap = getBitmapFromAssets("drawables/x32/tiles/floor_wood_horizontal.png");
				tile = new Tile(context, bitmap, 0, 0, bitmap.getWidth(),
						bitmap.getHeight(), false);
				tiles.add(i, tile);
				break;

			case 8:
				bitmap = getBitmapFromAssets("drawables/x32/objects/door_wood.png");
				tile = new Tile(context, bitmap, 0, 0, bitmap.getWidth(),
						bitmap.getHeight(), true);
				tiles.add(i, tile);
				break;

			case 9:
				bitmap = getBitmapFromAssets("drawables/x32/objects/table_wood.png");
				tile = new Tile(context, bitmap, 0, 0, bitmap.getWidth(),
						bitmap.getHeight(), true);
				tiles.add(i, tile);
				break;

			case 10:
				bitmap = getBitmapFromAssets("drawables/x32/objects/bed_wood.png");
				tile = new Tile(context, bitmap, 0, 0, bitmap.getWidth(),
						bitmap.getHeight(), true);
				tiles.add(i, tile);
				break;

			case 11:
				bitmap = getBitmapFromAssets("drawables/x32/objects/chair_blue_front.png");
				tile = new Tile(context, bitmap, 0, 0, bitmap.getWidth(),
						bitmap.getHeight(), false);
				tiles.add(i, tile);
				break;
			}
		}
		return tiles;

	}
}
