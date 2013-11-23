package high.caliber.productions.demigod.settings;

import high.caliber.productions.demigod.R;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.util.Log;
import android.widget.SeekBar;

public class SettingsMain extends PreferenceActivity {

	// Settings Constants
	public static final String SETTINGS_SHARED_PREFS = "Settings";
	public static final String KEY_DPAD_SIZE = "D-Pad Size";
	public static final String KEY_DPAD_POS_X = "D-Pad X Position";
	public static final String KEY_DPAD_POS_Y = "D-Pad Y Position";

	Preference dPadPosPref, dPadSizePref;

	SharedPreferences prefs;
	Editor editor;

	int dPadSize;

	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.settings_preference);

		// prefs = getSharedPreferences(SettingsMain.SETTINGS_SHARED_PREFS,
		// MODE_PRIVATE);
		// editor = prefs.edit();
		//
		// dPadSize = prefs.getInt(KEY_DPAD_SIZE, 35);

		Log.d("D-Pad resize", String.valueOf(dPadSize));

		dPadPosPref = findPreference("dPadPosition");
		dPadPosPref
				.setIntent(new Intent(SettingsMain.this, DpadPosition.class));

	}

	@Override
	protected void onDestroy() {
		super.onDestroy();

		// editor.putInt(KEY_DPAD_SIZE, dPadSize);
		// editor.commit();
	}
}
