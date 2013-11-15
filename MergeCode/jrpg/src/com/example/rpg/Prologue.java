package com.example.rpg;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ScrollView;

import com.example.rpg.tools.TypeWriter;

/**
 * Created by Matt Matt on 5/22/13.
 */
public class Prologue extends Activity implements View.OnClickListener {

	TypeWriter tvPrologue1, tvPrologue2, tvPrologue3;
	Button bPrologue;
	ScrollView sv;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.prologue);

		bPrologue = (Button) findViewById(R.id.bPrologueContinue);
		bPrologue.setOnClickListener(this);

		tvPrologue1 = (TypeWriter) findViewById(R.id.TV_prologue1);

		tvPrologue2 = (TypeWriter) findViewById(R.id.TV_prologue2);

		tvPrologue3 = (TypeWriter) findViewById(R.id.TV_prologue3);

		sv = (ScrollView) findViewById(R.id.svPrologue);
	}

	@Override
	public void onClick(View v) {

		if (v.getId() == R.id.bPrologueContinue) {

			tvPrologue1.setText(getString(R.string.Prologue4));
			tvPrologue2.setText(getString(R.string.Prologue5));
			tvPrologue3.setText(getString(R.string.Prologue6));

			bPrologue.setId(1);

			if (hasWindowFocus()) {
				sv.scrollTo(0, 0);
			}

		} else if (v.getId() == 1) {

			Intent intent = new Intent("com.example.rpg.CHOOSE_NAME");
			startActivity(intent);

			finish();
		}

	}
}
