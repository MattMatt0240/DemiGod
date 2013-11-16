package high.caliber.productions.demigod.utils;
import android.content.*;

public abstract class PrefsManager
{
	private SharedPreferences prefs;

//SharedPreference Constants
	private static final String BATTLE_LOG = "Battle Log";
	private static final String DAMAGE_DEALT = "Damage Dealt";
	private static final String LIFETIME_DAMAGE_DEALT="Lifetime Damage Dealt";
	private static final String DAMAGE_RECIEVED="Damage Recieved";
	private static final String LIFETIME_DAMAGE_RECIEVED ="Lifetime Damage Recieved";

	public static String getBattleLogPrefs ()
	{
		return BATTLE_LOG;
	}
	
	public static String getDamageDealt(){
		return DAMAGE_DEALT;
	}
	
	public static String getLifeTimeDamageDealt(){
		return LIFETIME_DAMAGE_DEALT;
	}
	
	public static String getDamageRecieved() {
		return DAMAGE_RECIEVED;
	}
	
	public static String getLifetimeDamageRecieved() {
		return LIFETIME_DAMAGE_RECIEVED;
	}
}
