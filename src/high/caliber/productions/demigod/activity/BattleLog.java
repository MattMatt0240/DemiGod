package high.caliber.productions.demigod.activity;

import high.caliber.productions.demigod.R;
import high.caliber.productions.demigod.utils.PrefsManager;
import high.caliber.productions.demigod.utils.PrefsManager.BattleLogPrefs;
import android.app.Activity;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class BattleLog extends Activity implements OnClickListener {

	PrefsManager prefManager;
	BattleLogPrefs battlePrefs;

	TextView tvDamageDealt, tvLifeTimeDamageDealt, tvDamageRecieved,
			tvLifeTimeDamageRecieved;

	Button bContinue;

	int damageDealt, lifeTimeDamageDealt, damageRecieved,
			lifeTimeDamageRecieved;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.battle_log);

		prefManager = new PrefsManager(this);
		battlePrefs = prefManager.new BattleLogPrefs();

		damageDealt = battlePrefs.getDamageDealt();
		lifeTimeDamageDealt = battlePrefs.getLifetimeDamageDealt();
		damageRecieved = battlePrefs.getDamageRecieved();
		lifeTimeDamageRecieved = battlePrefs.getLifetimeDamageRecieved();

		tvDamageDealt = (TextView) findViewById(R.id.tvBattleLog_DamageDealt);
		tvDamageDealt.setText(String.valueOf(damageDealt));

		tvLifeTimeDamageDealt = (TextView) findViewById(R.id.tvBattleLog_LifeTimeDamageDealt);
		tvLifeTimeDamageDealt.setText(String.valueOf(lifeTimeDamageDealt));

		tvDamageRecieved = (TextView) findViewById(R.id.tvBattleLog_DamageRecieved);
		tvDamageRecieved.setText(String.valueOf(damageRecieved));

		tvLifeTimeDamageRecieved = (TextView) findViewById(R.id.tvBattleLog_LifeTimeDamageRecieved);
		tvLifeTimeDamageRecieved
				.setText(String.valueOf(lifeTimeDamageRecieved));

		bContinue = (Button) findViewById(R.id.bBattleLog_Continue);
		bContinue.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {

		if (v.getId() == R.id.bBattleLog_Continue) {

			finish();

		}

	}

	@Override
	protected void onDestroy() {
		super.onDestroy();

		battlePrefs.addDamageDealt(0);
		battlePrefs.addDamageRecieved(0);
	}

}
