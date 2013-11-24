package high.caliber.productions.demigod.settings;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.preference.Preference;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

public class DpadSizePref extends Preference implements OnSeekBarChangeListener {

	public static int MAXIMUM = 80;
	public static int INTERVAL = 1;

	private int oldValue;
	private TextView monitorBox;

	public DpadSizePref(Context context) {
		super(context);

		SharedPreferences prefs = getContext().getSharedPreferences(
				SettingsMain.SETTINGS_SHARED_PREFS, 0);

		this.oldValue = prefs.getInt(SettingsMain.KEY_DPAD_SIZE, 35);
	}

	public DpadSizePref(Context context, AttributeSet attrs) {
		super(context, attrs);

		SharedPreferences prefs = getContext().getSharedPreferences(
				SettingsMain.SETTINGS_SHARED_PREFS, 0);

		this.oldValue = prefs.getInt(SettingsMain.KEY_DPAD_SIZE, 35);
	}

	public DpadSizePref(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);

		SharedPreferences prefs = getContext().getSharedPreferences(
				SettingsMain.SETTINGS_SHARED_PREFS, 0);

		this.oldValue = prefs.getInt(SettingsMain.KEY_DPAD_SIZE, 35);
	}

	@Override
	protected View onCreateView(ViewGroup parent) {

		LinearLayout layout = new LinearLayout(getContext());

		LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.WRAP_CONTENT,
				LinearLayout.LayoutParams.WRAP_CONTENT);
		params1.gravity = Gravity.LEFT;
		params1.weight = 1.0f;

		LinearLayout.LayoutParams params2 = new LinearLayout.LayoutParams(80,
				LinearLayout.LayoutParams.WRAP_CONTENT);
		params2.gravity = Gravity.RIGHT;
		params2.weight = 2.0f;

		LinearLayout.LayoutParams params3 = new LinearLayout.LayoutParams(30,
				LinearLayout.LayoutParams.WRAP_CONTENT);
		params3.gravity = Gravity.CENTER;
		params3.weight = 0.5f;

		layout.setOrientation(LinearLayout.HORIZONTAL);

		TextView view = new TextView(getContext());
		view.setText(getTitle());
		view.setTextSize(20);
		view.setTypeface(Typeface.SANS_SERIF, Typeface.BOLD);
		view.setGravity(Gravity.LEFT);
		view.setLayoutParams(params1);

		SeekBar bar = new SeekBar(getContext());
		bar.setMax(MAXIMUM);
		bar.setProgress((int) this.oldValue);
		bar.setLayoutParams(params2);
		bar.setOnSeekBarChangeListener(this);

		this.monitorBox = new TextView(getContext());
		this.monitorBox.setTextSize(12);
		this.monitorBox.setTypeface(Typeface.MONOSPACE, Typeface.ITALIC);
		this.monitorBox.setLayoutParams(params3);
		this.monitorBox.setPadding(2, 5, 0, 0);
		this.monitorBox.setText(bar.getProgress() + "");

		layout.addView(view);
		layout.addView(bar);
		layout.addView(this.monitorBox);
		layout.setId(android.R.id.widget_frame);

		return layout;
	}

	@Override
	public void onProgressChanged(SeekBar seekBar, int progress,
			boolean fromUser) {

		progress = Math.round(((float) progress) / INTERVAL) * INTERVAL;

		if (!callChangeListener(progress)) {
			seekBar.setProgress((int) this.oldValue);
			return;
		}

		seekBar.setProgress(progress);
		this.oldValue = progress;
		this.monitorBox.setText(progress + "");
		updatePreference(progress);

		notifyChanged();
	}

	@Override
	public void onStartTrackingTouch(SeekBar seekBar) {
	}

	@Override
	public void onStopTrackingTouch(SeekBar seekBar) {
	}

	@Override
	protected Object onGetDefaultValue(TypedArray ta, int index) {

		SharedPreferences prefs = getContext().getSharedPreferences(
				SettingsMain.SETTINGS_SHARED_PREFS, 0);

		int defaultValue = prefs.getInt(SettingsMain.KEY_DPAD_SIZE, 35);

		return validateValue(defaultValue);
	}

	private int validateValue(int value) {

		if (value > MAXIMUM)
			value = MAXIMUM;
		else if (value < 5)
			value = 5;
		else if (value % INTERVAL != 0)
			value = Math.round(((float) value) / INTERVAL) * INTERVAL;

		return value;
	}

	private void updatePreference(int newValue) {

		SharedPreferences prefs = getContext().getSharedPreferences(
				SettingsMain.SETTINGS_SHARED_PREFS, 0);

		SharedPreferences.Editor editor = prefs.edit();
		editor.putInt(SettingsMain.KEY_DPAD_SIZE, newValue);
		editor.commit();
	}

}
