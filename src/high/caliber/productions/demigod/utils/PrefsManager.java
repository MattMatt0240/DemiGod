package high.caliber.productions.demigod.utils;

import android.content.*;

public class PrefsManager {

	Context context;

	public PrefsManager(Context context) {
		this.context = context;
	}

	/**
	 * Class that handles data in BattleLog SharedPreferences
	 */
	public class BattleLogPrefs {

		private SharedPreferences prefs;
		private SharedPreferences.Editor editor;

		// BattleLog SharedPreference Constants
		public static final String BATTLE_LOG = "Battle Log";
		public static final String DAMAGE_DEALT = "Damage Dealt";
		public static final String LIFETIME_DAMAGE_DEALT = "Lifetime Damage Dealt";
		public static final String DAMAGE_RECIEVED = "Damage Recieved";
		public static final String LIFETIME_DAMAGE_RECIEVED = "Lifetime Damage Recieved";

		/**
		 * Adds damageDealt to BattleLog SharedPreferences
		 * 
		 * @param damageDealt
		 *            Desired value to add to damageDealt
		 */
		public void addDamageDealt(int damageDealt) {

			prefs = context.getSharedPreferences(BATTLE_LOG, 0);
			editor = prefs.edit();

			editor.putInt(DAMAGE_DEALT, damageDealt);

			editor.commit();

		}

		/**
		 * Retrieves damageDealt value from BattleLog SharedPreferences
		 * 
		 * @return damageDealt value
		 */
		public int getDamageDealt() {
			prefs = context.getSharedPreferences(BATTLE_LOG, 0);

			return prefs.getInt(DAMAGE_DEALT, 0);

		}

		public void addLifetimeDamageDealt(int LifetimeDamageDealt) {
			prefs = context.getSharedPreferences(BATTLE_LOG, 0);
			editor = prefs.edit();

			editor.putInt(LIFETIME_DAMAGE_DEALT, LifetimeDamageDealt);
			editor.commit();
		}

		public int getLifetimeDamageDealt() {
			prefs = context.getSharedPreferences(BATTLE_LOG, 0);

			return prefs.getInt(LIFETIME_DAMAGE_DEALT, 0);
		}

		public void addDamageRecieved(int damageRecieved) {
			prefs = context.getSharedPreferences(BATTLE_LOG, 0);
			editor = prefs.edit();

			editor.putInt(DAMAGE_RECIEVED, damageRecieved);
			editor.commit();
		}

		public int getDamageRecieved() {
			prefs = context.getSharedPreferences(BATTLE_LOG, 0);

			return prefs.getInt(DAMAGE_RECIEVED, 0);
		}

		public void addLifetimeDamageRecieved(int lifetimeDamageRecieved) {
			prefs = context.getSharedPreferences(BATTLE_LOG, 0);
			editor = prefs.edit();

			editor.putInt(LIFETIME_DAMAGE_RECIEVED, lifetimeDamageRecieved);
			editor.commit();
		}

		public int getLifetimeDamageRecieved() {
			prefs = context.getSharedPreferences(BATTLE_LOG, 0);

			return prefs.getInt(LIFETIME_DAMAGE_RECIEVED, 0);
		}
	}

}