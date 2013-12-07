package high.caliber.productions.demigod;

import high.caliber.productions.demigod.utils.JsonMapAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

public class JsonActivityTest extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		JsonMapAdapter adapter = new JsonMapAdapter(this);
		TextView tv = new TextView(this);
		tv.setLayoutParams(new LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT,
				LinearLayout.LayoutParams.MATCH_PARENT));
		JSONObject map = adapter.loadJsonFromAssets("mapping/hero_home.json");
		JSONArray array;
		try {
			array = map.getJSONArray("tile_data");
			String values = array.toString();
			StringBuilder builder = new StringBuilder(values.length());

			for (int i = 0; i < values.length(); i++) {
				builder.append(values.charAt(i));
				tv.setText(builder.toString());
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}

		setContentView(tv);
	}
}
