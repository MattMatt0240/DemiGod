package com.example.rpg.tools;

import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.widget.TextView;

public class TypeWriter extends TextView {

	private CharSequence mText;
	private int mIndex;
	private long mDelay = 75;
	volatile boolean isTyping = false;

	public TypeWriter(Context context) {
		super(context);
	}

	public TypeWriter(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	private Handler mHandler = new Handler();
	private Runnable characterAdder = new Runnable() {
		@Override
		public void run() {

			setText(mText.subSequence(0, mIndex++));
			if (mIndex <= mText.length()) {

				isTyping = true;
				mHandler.postDelayed(characterAdder, mDelay);
			}

		}

	};

	public void animateText(CharSequence text) {
		mText = text;
		mIndex = 0;

		setText("");
		mHandler.removeCallbacks(characterAdder);
		mHandler.postDelayed(characterAdder, mDelay);

	}

	public void setCharacterDelay(long millis) {
		mDelay = millis;
	}

	public boolean isTyping() {

		return this.isTyping;

	}
}