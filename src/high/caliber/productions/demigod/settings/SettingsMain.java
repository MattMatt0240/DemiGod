package high.caliber.productions.demigod.settings;

import high.caliber.productions.demigod.R;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;

public class SettingsMain extends PreferenceActivity {

	// Settings Constants
	public static final String SETTINGS_SHARED_PREFS = "Settings";
	public static final String KEY_DPAD_SIZE = "D-Pad Size";
	public static final String KEY_DPAD_POS_X = "D-Pad X Position";
	public static final String KEY_DPAD_POS_Y = "D-Pad Y Position";

	Preference dPadPosPref;
	DpadSizePref dPadSizePref;

	SharedPreferences prefs;
	Editor editor;

	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.settings_preference);

		prefs = getSharedPreferences(SETTINGS_SHARED_PREFS, MODE_PRIVATE);
		editor = prefs.edit();

		dPadPosPref = findPreference("dPadPosition");
		dPadPosPref
				.setIntent(new Intent(SettingsMain.this, DpadPosition.class));
	}
}