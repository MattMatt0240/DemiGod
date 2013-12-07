package high.caliber.productions.demigod.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.graphics.Bitmap;

public class JsonMapAdapter {

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
			String tag = obj.getString("tile_data");
			JSONArray arrayData = obj.getJSONArray(tag);
			return arrayData;
		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		}
	}

	public ArrayList<Bitmap> mapData(JSONArray jsonTileArray) {
		ArrayList<Bitmap> tileData = new ArrayList<Bitmap>();
		return null;

	}
}
