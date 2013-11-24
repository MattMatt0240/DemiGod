package high.caliber.productions.demigod.utils;

import android.content.Context;
import android.util.DisplayMetrics;
import android.util.TypedValue;

public class PixelUnitConverter {

	Context context;
	DisplayMetrics displayMetrics;

	public PixelUnitConverter(Context context) {
		this.context = context;
		this.displayMetrics = context.getResources().getDisplayMetrics();
	}

	public int dpToPx(int dp) {
		int valueInDp = (int) TypedValue.applyDimension(
				TypedValue.COMPLEX_UNIT_DIP, dp, context.getResources()
						.getDisplayMetrics());
		return valueInDp;
	}

	public int pxToDp(int px) {
		int valueInDp = (int) TypedValue.applyDimension(
				TypedValue.COMPLEX_UNIT_PX, px, context.getResources()
						.getDisplayMetrics());
		return valueInDp;
	}

}
