package high.caliber.productions.demigod.utils;

import high.caliber.productions.demigod.Tile;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.util.ArrayList;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.util.Log;

public class XmlMapAdapter {

	public static final String MAP_HOME = "home";
	public static final String MAP_HOME_TOWN = "home_town";

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
	int x = 0;
	int y = 0;

	private ArrayList<Tile> tiles;
	private ArrayList<Tile> objects;

	public XmlMapAdapter(Context context) {
		this.context = context;
	}

	public Bitmap getBitmapFromAssets(String path, int bitmapWidth,
			int bitmapHeight) {
		AssetManager manager = context.getAssets();
		BitmapFactory.Options opts = new BitmapFactory.Options();
		opts.inDither = true;
		opts.inPreferQualityOverSpeed = true;
		try {
			Bitmap tempBitmap = BitmapFactory.decodeStream(manager.open(path),
					null, opts);
			Bitmap bitmap = Bitmap.createScaledBitmap(tempBitmap, bitmapWidth,
					bitmapHeight, true);
			return bitmap;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	public ArrayList<Tile> convertMapData(String mapName)
			throws XmlPullParserException, IOException {

		PixelUnitConverter converter = new PixelUnitConverter(context);
		int tileDimen = converter.dpToPx(32);

		AssetManager manager = context.getAssets();

		InputStream is = null;
		tiles = new ArrayList<Tile>();
		objects = new ArrayList<Tile>();
		try {
			is = manager.open("mapping/maps.xml");
			XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
			factory.setNamespaceAware(true);
			XmlPullParser xpp = factory.newPullParser();
			xpp.setInput(is, null);
			int eventType = xpp.getEventType();
			String content;

			Tile tile = null;
			Tile object = null;
			int tileId = 0;

			boolean requestedMap = false;

			while (eventType != XmlPullParser.END_DOCUMENT) {
				if (eventType == XmlPullParser.START_DOCUMENT) {

				} else if (eventType == XmlPullParser.START_TAG) {
					if (xpp.getName().equalsIgnoreCase("index")) {
					} else if (xpp.getName().equalsIgnoreCase("map")) {

					} else if (xpp.getName().equalsIgnoreCase("name")) {

						if (xpp.nextText().equals(mapName)) {
							requestedMap = true;
						} else {
							requestedMap = false;
						}

					} else if (xpp.getName().equalsIgnoreCase("row")) {

						if (requestedMap) {
							x = 0;
						}

					} else if (xpp.getName().equalsIgnoreCase("tile")) {

						if (requestedMap) {
							int attrCount = xpp.getAttributeCount();
							if (attrCount != -1) {

								for (int x = 0; x < attrCount; x++) {

									if (xpp.getAttributeName(x)
											.equals("object")) {

										object = getTile(Integer.valueOf(xpp
												.getAttributeValue(x)));
										objects.add(object);
									}
								}

								tileId = Integer.valueOf(xpp.nextText());
								tile = getTile(tileId);
								tiles.add(tile);
							}
							x += tileDimen;
						}
					}
				} else if (eventType == XmlPullParser.END_TAG) {
					if (xpp.getName().equalsIgnoreCase("row")) {

						if (requestedMap) {
							y += tileDimen;
						}
					}

				} else if (eventType == XmlPullParser.TEXT) {
					content = xpp.getText().trim();
				}
				eventType = xpp.next();

			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (XmlPullParserException e) {
			e.printStackTrace();
		}
		Log.d("tile array size", String.valueOf((tiles.size())));
		Log.d("object array size", String.valueOf((objects.size())));
		return tiles;
	}

	public Tile getTile(int tileID) {
		Tile tile = null;
		Bitmap bitmap = null;
		PixelUnitConverter converter = new PixelUnitConverter(context);
		int tileDimen = converter.dpToPx(32);

		switch (tileID) {

		case 0:
			bitmap = Bitmap.createBitmap(1, 1, Config.ALPHA_8);
			tile = new Tile(context, bitmap, x, y, x + bitmap.getWidth(), y
					+ bitmap.getHeight(), false);
			break;

		case 1:
			bitmap = getBitmapFromAssets("drawables/x32/tiles/grass1.png",
					tileDimen, tileDimen);
			tile = new Tile(context, bitmap, x, y, x + bitmap.getWidth(), y
					+ bitmap.getHeight(), false);
			break;

		case 2:
			bitmap = getBitmapFromAssets("drawables/x32/tiles/grass2.png",
					tileDimen, tileDimen);
			tile = new Tile(context, bitmap, x, y, x + bitmap.getWidth(), y
					+ bitmap.getHeight(), false);
			break;

		case 3:
			bitmap = getBitmapFromAssets("drawables/x32/tiles/grass3.png",
					tileDimen, tileDimen);
			tile = new Tile(context, bitmap, x, y, x + bitmap.getWidth(), y
					+ bitmap.getHeight(), false);
			break;

		case 4:
			bitmap = getBitmapFromAssets(
					"drawables/x32/tiles/wall_wood_top_vertical.png",
					tileDimen, tileDimen);
			tile = new Tile(context, bitmap, x, y, x + bitmap.getWidth(), y
					+ bitmap.getHeight(), true);
			break;

		case 5:
			bitmap = getBitmapFromAssets(
					"drawables/x32/tiles/wall_wood_top_horizontal.png",
					tileDimen, tileDimen);
			tile = new Tile(context, bitmap, x, y, x + bitmap.getWidth(), y
					+ bitmap.getHeight(), true);
			break;

		case 6:
			bitmap = getBitmapFromAssets(
					"drawables/x32/tiles/floor_wood_vertical.png", tileDimen,
					tileDimen);
			tile = new Tile(context, bitmap, x, y, x + bitmap.getWidth(), y
					+ bitmap.getHeight(), false);
			break;

		case 7:
			bitmap = getBitmapFromAssets(
					"drawables/x32/tiles/floor_wood_horizontal.png", tileDimen,
					tileDimen);
			tile = new Tile(context, bitmap, x, y, x + bitmap.getWidth(), y
					+ bitmap.getHeight(), false);
			break;

		case 8:
			bitmap = getBitmapFromAssets("drawables/x32/objects/door_wood.png",
					tileDimen, tileDimen);
			tile = new Tile(context, bitmap, x, y, x + bitmap.getWidth(), y
					+ bitmap.getHeight(), true);
			break;

		case 9:
			bitmap = getBitmapFromAssets(
					"drawables/x32/objects/table_wood.png", tileDimen,
					tileDimen);
			tile = new Tile(context, bitmap, x, y, x + bitmap.getWidth(), y
					+ bitmap.getHeight(), true);
			break;

		case 10:
			bitmap = getBitmapFromAssets("drawables/x32/objects/bed_wood.png",
					tileDimen, tileDimen * 2);
			tile = new Tile(context, bitmap, x, y, x + bitmap.getWidth(), y
					+ bitmap.getHeight(), true);
			break;

		case 11:
			bitmap = getBitmapFromAssets(
					"drawables/x32/objects/chair_blue_front.png", tileDimen,
					tileDimen);
			tile = new Tile(context, bitmap, x, y, x + bitmap.getWidth(), y
					+ bitmap.getHeight(), false);
			break;

		}
		return tile;
	}

	public ArrayList<Tile> getTiles() {
		return tiles;
	}

	public void setTiles(ArrayList<Tile> tiles) {
		this.tiles = tiles;
	}

	public ArrayList<Tile> getObjects() {
		return objects;
	}

	public void setObjects(ArrayList<Tile> objects) {
		this.objects = objects;
	}
}
