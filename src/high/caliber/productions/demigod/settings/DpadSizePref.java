package high.caliber.productions.demigod.settings;

import high.caliber.productions.demigod.R;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.Preference;
import android.util.AttributeSet;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;

public class DpadSizePref extends Preference implements OnSeekBarChangeListener {

	Context context;
	int progress;

	SharedPreferences prefs;
	Editor editor;

	public DpadSizePref(Context context) {
		super(context);
		this.context = context;
		setLayoutResource(R.layout.dpad_resize);
	}

	public DpadSizePref(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
		setLayoutResource(R.layout.dpad_resize);
	}

	public DpadSizePref(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		this.context = context;
		setLayoutResource(R.layout.dpad_resize);
	}

	@Override
	public void onProgressChanged(SeekBar seekBar, int progress,
			boolean fromUser) {
		this.progress = progress;
	}

	@Override
	public void onStartTrackingTouch(SeekBar seekBar) {

	}

	@Override
	public void onStopTrackingTouch(SeekBar seekBar) {

	}

	public int getProgress() {
		return this.progress;
	}

	public void setProgress(int progress) {
		this.progress = progress;
	}

}
