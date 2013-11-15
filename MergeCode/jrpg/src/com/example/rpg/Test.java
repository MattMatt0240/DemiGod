package com.example.rpg;

import android.app.Activity;
import android.os.Bundle;

public class Test extends Activity {

	MapLoader mapLoader;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		mapLoader = new MapLoader(this);
		setContentView(mapLoader);

	}

	@Override
	protected void onResume() {
		super.onResume();
		mapLoader.resume();
	}

	@Override
	protected void onPause() {
		super.onPause();
		mapLoader.pause();
	}
}
