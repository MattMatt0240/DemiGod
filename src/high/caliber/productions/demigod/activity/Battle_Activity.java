package high.caliber.productions.demigod.activity;

import high.caliber.productions.demigod.Enemy;
import high.caliber.productions.demigod.R;
import high.caliber.productions.demigod.database.EnemyDB;
import high.caliber.productions.demigod.database.HeroDB;
import high.caliber.productions.demigod.utils.AnimationUtils;
import high.caliber.productions.demigod.utils.AnimationUtils.Fireball;
import high.caliber.productions.demigod.utils.LevelUpWorker;
import high.caliber.productions.demigod.utils.PixelUnitConverter;
import high.caliber.productions.demigod.utils.SharedPrefsManager;
import high.caliber.productions.demigod.utils.SharedPrefsManager.BattleLogPrefs;

import java.io.IOException;
import java.util.Random;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class Battle_Activity extends Activity implements OnClickListener {

	static final String dbPath = HeroDB.getPath();
	static final String dbName = HeroDB.getDbName();
	static final String statsTable = HeroDB.getTableStats();
	static final String inventoryTable = HeroDB.getTableInventory();

	static final String colID = HeroDB.COL_ID;
	static final String colClass = HeroDB.COL_CLASS;
	static final String colName = HeroDB.COL_NAME;
	static final String colLevel = HeroDB.COL_LVL;
	static final String colExp = HeroDB.COL_EXP;
	static final String colMaxExp = HeroDB.COL_MAX_EXP;
	static final String colHealth = HeroDB.COL_HEALTH;
	static final String colMaxHealth = HeroDB.COL_MAX_HEALTH;
	static final String colEnergy = HeroDB.COL_ENERGY;
	static final String colMaxEnergy = HeroDB.COL_MAX_ENERGY;
	static final String colMana = HeroDB.COL_MANA;
	static final String colMaxMana = HeroDB.COL_MAX_MANA;
	static final String colAttack = HeroDB.COL_ATTACK;
	static final String colMagic = HeroDB.COL_MAGIC;
	static final String colPhDefense = HeroDB.COL_PH_DEFENSE;
	static final String colMgDefense = HeroDB.COL_MG_DEFENSE;
	static final String colAgility = HeroDB.COL_AGILITY;
	static final String colDexterity = HeroDB.COL_DEXTERITY;

	String heroName, heroClass;

	HeroDB heroDbHelper;

	SQLiteDatabase heroDb, enemyDb;
	Cursor c;

	private int heroLevel;
	private int heroHealth;
	private int heroMaxHealth;
	private int heroExp;
	private int heroMaxExp;
	private int heroEnergy;
	private int heroMaxEnergy;
	private int heroMana;
	private int heroMaxMana;
	private int heroAttack;
	private int heroMagic;
	private int heroPhDefense;
	private int heroMgDefense;
	private int heroAgility;
	private int heroDexterity;

	LevelUpWorker lvlUp;

	TextView tvHeroName, tvHeroLevel, tvHeroHealth, tvHeroEnergy, tvHeroExp,
			tvEnemyName, tvEnemyLevel, tvEnemyHealth, tvEnemyEnergy;

	Button bAttack, bDefend;

	ProgressBar progBarHealth, progBarEnergy, progBarEXP, progBarEnemyHealth,
			progBarEnemyEnergy;

	Enemy enemy;
	int enemyMaxHealth = 10;
	int enemyMaxEnergy = 4;

	int damageDealt, lifeTimeDamageDealt, damageRecieved,
			lifeTimeDamageRecieved;

	boolean playerTurn;

	SharedPrefsManager prefManager;
	BattleLogPrefs battlePrefs;

	ImageView ivHero, ivEnemy;

	PixelUnitConverter converter;

	AnimationUtils animUtils;

	RelativeLayout containerLayout = null;

	Fireball fireball;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.battle);

		containerLayout = (RelativeLayout) findViewById(R.id.battle_rl_container);

		converter = new PixelUnitConverter(this);

		animUtils = new AnimationUtils(this);

		ivHero = (ImageView) findViewById(R.id.ivHero);
		ivEnemy = (ImageView) findViewById(R.id.ivEnemy);

		AssetManager manager = getAssets();
		Bitmap bitmap1 = null, bitmap2 = null;
		try {
			bitmap1 = BitmapFactory
					.decodeStream(manager
							.open("drawables/x32/characters/knight/knight_male_right1.png"));
			bitmap2 = BitmapFactory.decodeStream(manager
					.open("drawables/x100/dragon_blood.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		ivHero.setImageBitmap(bitmap1);
		ivEnemy.setImageBitmap(bitmap2);

		EnemyDB enemyDbHelper = new EnemyDB(this);
		enemyDb = enemyDbHelper.getWritableDatabase();
		Random rand = new Random();

		// MUST BE NO MORE THAN AMOUNT OF ENEMIES IN DATABASE
		int enemyIndex = rand.nextInt(3) + 1;

		Cursor c = enemyDbHelper.getCommonEnemy(enemyIndex);

		enemy = new Enemy();

		enemy._class = c.getString(Enemy.CLASS_INDEX);
		enemy.name = c.getString(Enemy.NAME_INDEX);
		enemy.level = c.getInt(Enemy.LEVEL_INDEX);
		enemy.expValue = c.getInt(Enemy.EXP_VALUE_INDEX);
		enemy.health = c.getInt(Enemy.HEALTH_INDEX);
		enemy.energy = c.getInt(Enemy.ENERGY_INDEX);
		enemy.mana = c.getInt(Enemy.MANA_INDEX);
		enemy.attack = c.getInt(Enemy.ATTACK_INDEX);
		enemy.magic = c.getInt(Enemy.MAGIC_INDEX);
		enemy.phDefense = c.getInt(Enemy.PH_DEF_INDEX);
		enemy.mgDefense = c.getInt(Enemy.MG_DEF_INDEX);
		enemy.agility = c.getInt(Enemy.AGILITY_INDEX);
		enemy.dexterity = c.getInt(Enemy.DEXTERITY_INDEX);

		enemyMaxHealth = enemy.health;
		enemyMaxEnergy = enemy.energy;

		heroDbHelper = new HeroDB(this);
		heroDb = heroDbHelper.getWritableDatabase();

		c = heroDb
				.rawQuery(
						"SELECT Class, Name, Level, Exp, MaxExp, Health, MaxHealth, Energy, MaxEnergy, Mana, MaxMana, Attack, Magic, PhDefense, MgDefense, Agility, Dexterity FROM Stats",
						null);

		if (c != null) {

			// if 1st Row
			if (c.moveToFirst()) {
				do {
					// Retrieve values from columns
					heroClass = c.getString(c.getColumnIndex(colClass));
					heroName = c.getString(c.getColumnIndex(colName));
					heroLevel = c.getInt(c.getColumnIndex(colLevel));
					heroExp = c.getInt(c.getColumnIndex(colExp));
					heroMaxExp = c.getInt(c.getColumnIndex(colMaxExp));
					heroHealth = c.getInt(c.getColumnIndex(colHealth));
					heroMaxHealth = c.getInt(c.getColumnIndex(colMaxHealth));
					heroEnergy = c.getInt(c.getColumnIndex(colEnergy));
					heroMaxEnergy = c.getInt(c.getColumnIndex(colMaxEnergy));
					heroMana = c.getInt(c.getColumnIndex(colMana));
					heroMaxMana = c.getInt(c.getColumnIndex(colMaxMana));
					heroAttack = c.getInt(c.getColumnIndex(colAttack));
					heroMagic = c.getInt(c.getColumnIndex(colMagic));
					heroPhDefense = c.getInt(c.getColumnIndex(colPhDefense));
					heroMgDefense = c.getInt(c.getColumnIndex(colMgDefense));
					heroAgility = c.getInt(c.getColumnIndex(colAgility));
					heroDexterity = c.getInt(c.getColumnIndex(colDexterity));

				} while (c.moveToNext());

			}
		}
		c.close();

		lvlUp = new LevelUpWorker();

		prefManager = new SharedPrefsManager(this);
		battlePrefs = prefManager.new BattleLogPrefs();

		damageDealt = battlePrefs.getDamageDealt();
		lifeTimeDamageDealt = battlePrefs.getLifetimeDamageDealt();
		damageRecieved = battlePrefs.getDamageRecieved();
		lifeTimeDamageRecieved = battlePrefs.getLifetimeDamageRecieved();

		// Initialize variables (TextViews, Buttons, & ProgressBars)
		tvHeroName = (TextView) findViewById(R.id.tvBattle_Hero_Name);
		tvHeroName.setText(heroName);
		tvHeroName.bringToFront();

		tvHeroLevel = (TextView) findViewById(R.id.tvBattle_Hero_Level);
		tvHeroLevel.setText("LVL: " + heroLevel);
		tvHeroName.bringToFront();

		tvHeroHealth = (TextView) findViewById(R.id.tvBattle_Hero_Health);
		tvHeroHealth.setText("Health: " + heroHealth + " / " + heroMaxHealth);

		progBarHealth = (ProgressBar) findViewById(R.id.progBar_HeroHealth);
		progBarHealth.setProgress(heroHealth);
		progBarHealth.setMax(heroMaxHealth);

		tvHeroEnergy = (TextView) findViewById(R.id.tvBattle_Hero_Energy);
		tvHeroEnergy.setText("Energy: " + heroEnergy + " / " + heroMaxEnergy);

		progBarEnergy = (ProgressBar) findViewById(R.id.progBar_HeroEnergy);
		progBarEnergy.setProgress(heroEnergy);
		progBarEnergy.setMax(heroMaxEnergy);

		tvHeroExp = (TextView) findViewById(R.id.tvBattle_Hero_EXP);
		tvHeroExp.setText("EXP: " + heroExp + " / " + heroMaxExp);

		progBarEXP = (ProgressBar) findViewById(R.id.progBar_HeroExp);
		progBarEXP.setProgress(heroExp);
		progBarEXP.setMax(heroMaxExp);

		tvEnemyName = (TextView) findViewById(R.id.tvBattle_Enemy_Name);
		tvEnemyName.setText(enemy.name);

		tvEnemyLevel = (TextView) findViewById(R.id.tvBattle_Enemy_Level);
		tvEnemyLevel.setText(String.valueOf("LVL: " + enemy.level));

		tvEnemyHealth = (TextView) findViewById(R.id.tvBattle_Enemy_Health);
		tvEnemyHealth.setText("Health: " + String.valueOf(enemy.health) + " / "
				+ enemyMaxHealth);

		progBarEnemyHealth = (ProgressBar) findViewById(R.id.progBar_EnemyHealth);
		progBarEnemyHealth.setProgress(enemy.health);
		progBarEnemyHealth.setMax(enemyMaxHealth);

		tvEnemyEnergy = (TextView) findViewById(R.id.tvBattle_Enemy_Energy);
		tvEnemyEnergy.setText("Energy: " + String.valueOf(enemy.energy) + " / "
				+ enemyMaxEnergy);

		progBarEnemyEnergy = (ProgressBar) findViewById(R.id.progBar_EnemyEnergy);
		progBarEnemyEnergy.setProgress(enemy.energy);
		progBarEnemyEnergy.setMax(enemyMaxEnergy);

		bAttack = (Button) findViewById(R.id.bBattle_Attack);
		bAttack.setOnClickListener(this);

		bDefend = (Button) findViewById(R.id.bBattle_Defend);
		bDefend.setOnClickListener(this);

		if (heroAgility >= enemy.agility) {
			playerTurn = true;
			enemy.isEnemyTurn = false;
		} else {
			playerTurn = false;
			bAttack.setText("Enemy's Turn");
			enemy.isEnemyTurn = true;
			// enemy.chooseAction();
		}

		if (playerTurn == true) {
			Toast.makeText(getApplicationContext(), "Your turn",
					Toast.LENGTH_SHORT).show();
		} else {
			Toast.makeText(getApplicationContext(), "Enemy's Turn",
					Toast.LENGTH_SHORT).show();
		}

	}

	protected void onResume() {
		super.onResume();

		progBarHealth.setProgress(0);
		progBarHealth.setProgress(heroHealth);

		progBarEnergy.setProgress(0);
		progBarEnergy.setProgress(heroEnergy);

		progBarEXP.setProgress(0);
		progBarEXP.setProgress(heroExp);

		progBarEnemyHealth.setProgress(0);
		progBarEnemyHealth.setProgress(enemy.health);

		progBarEnemyEnergy.setProgress(0);
		progBarEnemyEnergy.setProgress(enemy.energy);

	}

	// User Attack Formula
	public void Attack() {

		Animation animationForward = new TranslateAnimation(0,
				converter.dpToPx(50), 0, 0);
		animationForward.setDuration(200);
		animationForward.setAnimationListener(new AnimationListener() {

			@Override
			public void onAnimationStart(Animation animation) {

			}

			@Override
			public void onAnimationRepeat(Animation animation) {

			}

			@Override
			public void onAnimationEnd(Animation animation) {
				animation = new TranslateAnimation(0.0f, 0.0f, 0.0f, 0.0f);
				animation.setDuration(1);
				ivHero.startAnimation(animation);
			}
		});

		ivHero.startAnimation(animationForward);

		int damage = ((heroAttack / enemy.phDefense) + 1);

		if (heroEnergy <= 0) {
			damage = 0;

			heroEnergy += 2;

		} else {

			enemy.health -= damage;

			heroEnergy -= 1;

			damageDealt = battlePrefs.getDamageDealt();
			lifeTimeDamageDealt = battlePrefs.getLifetimeDamageDealt();

			battlePrefs.addDamageDealt(damageDealt + damage);
			battlePrefs.addLifetimeDamageDealt(lifeTimeDamageDealt + damage);
		}

		progBarHealth.setProgress(0);
		progBarHealth.setProgress(heroHealth);

		progBarEnergy.setProgress(0);
		progBarEnergy.setProgress(heroEnergy);

		progBarEXP.setProgress(0);
		progBarEXP.setProgress(heroExp);

		progBarEnemyHealth.setProgress(0);
		progBarEnemyHealth.setProgress(enemy.health);

		progBarEnemyEnergy.setProgress(0);
		progBarEnemyEnergy.setProgress(enemy.energy);

		tvEnemyHealth.setText("Health: " + enemy.health + " / "
				+ enemyMaxHealth);

		tvHeroEnergy.setText("Energy: " + heroEnergy + " / " + heroMaxEnergy);

		tvEnemyName.setBackgroundColor(getApplicationContext().getResources()
				.getColor(R.color.dark_grey));

		tvHeroName.setBackgroundColor(getApplicationContext().getResources()
				.getColor(R.color.black));

		bAttack.setText("Enemy's Turn");

		playerTurn = false;
	}

	// AI Attack Formula
	public void EnemyAttack() {

		Animation animationForward = new TranslateAnimation(0,
				-converter.dpToPx(50), 0, 0);
		animationForward.setDuration(200);
		animationForward.setAnimationListener(new AnimationListener() {

			@Override
			public void onAnimationStart(Animation animation) {
			}

			@Override
			public void onAnimationRepeat(Animation animation) {

			}

			@Override
			public void onAnimationEnd(Animation animation) {
				animation = new TranslateAnimation(0.0f, 0.0f, 0.0f, 0.0f);
				animation.setDuration(1);
				ivEnemy.startAnimation(animation);
			}
		});

		ivEnemy.startAnimation(animationForward);
		//
		// final Animation fireballAnim = new TranslateAnimation(0,
		// ivHero.getRight() - ivEnemy.getLeft(), 0, 0);
		// fireballAnim.setDuration(1100);
		// fireballAnim.setAnimationListener(new AnimationListener() {
		//
		// @Override
		// public void onAnimationStart(Animation animation) {
		//
		// }
		//
		// @Override
		// public void onAnimationRepeat(Animation animation) {
		// }
		//
		// @Override
		// public void onAnimationEnd(Animation animation) {
		// fireball.setVisibility(View.INVISIBLE);
		// fireball = null;
		// }
		// });

		Handler handler = new Handler();
		handler.post(new Runnable() {
			@Override
			public void run() {

				fireball = animUtils.new Fireball(Battle_Activity.this);

				RelativeLayout.LayoutParams fireParams = new RelativeLayout.LayoutParams(
						new LayoutParams(converter.dpToPx(32), converter
								.dpToPx(32)));
				fireParams.addRule(RelativeLayout.LEFT_OF, ivEnemy.getId());
				fireParams.addRule(RelativeLayout.ALIGN_TOP, ivEnemy.getId());

				containerLayout.addView(fireball, fireParams);
				containerLayout.invalidate();

				fireball.start();

				fireball.animateMovement(ivHero.getRight());
				// fireball.startAnimation(fireballAnim);

			}
		});

		int damage = (enemy.attack / heroPhDefense);

		if (enemy.energy <= 0) {
			damage = 0;
			enemy.energy += 2;
		} else {

			heroHealth -= damage;

			damageRecieved = battlePrefs.getDamageRecieved();
			lifeTimeDamageRecieved = battlePrefs.getLifetimeDamageRecieved();

			battlePrefs.addDamageRecieved(damageRecieved + damage);
			battlePrefs.addLifetimeDamageRecieved(lifeTimeDamageRecieved
					+ damage);
		}

		progBarHealth.setProgress(0);
		progBarHealth.setProgress(heroHealth);

		progBarEnergy.setProgress(0);
		progBarEnergy.setProgress(heroEnergy);

		progBarEXP.setProgress(0);
		progBarEXP.setProgress(heroExp);

		progBarEnemyHealth.setProgress(0);
		progBarEnemyHealth.setProgress(enemy.health);

		progBarEnemyEnergy.setProgress(0);
		progBarEnemyEnergy.setProgress(enemy.energy);

		tvHeroHealth.setText("Health: " + heroHealth + " / " + heroMaxHealth);

		enemy.energy -= 1;
		tvEnemyEnergy.setText("Energy: " + enemy.energy + " / "
				+ enemyMaxEnergy);

		tvHeroName.setBackgroundColor(getApplicationContext().getResources()
				.getColor(R.color.dark_grey));

		tvEnemyName.setBackgroundColor(getApplicationContext().getResources()
				.getColor(R.color.black));

		bAttack.setText("Attack");

		playerTurn = true;
	}

	public void Defend() {

		if (heroEnergy + 2 > heroMaxEnergy) {
			heroEnergy = heroMaxEnergy;

		} else {
			heroEnergy += 2;
		}

		tvHeroEnergy.setText("Energy: " + heroEnergy + " / " + heroMaxEnergy);
		heroPhDefense = heroPhDefense + 2;

		playerTurn = false;

		EnemyAttack();
	}

	@Override
	public void onClick(View v) {

		// ATTACK BUTTON
		if (v.getId() == R.id.bBattle_Attack) {

			if (playerTurn == true) {
				Attack();

			} else {
				EnemyAttack();
			}

		}

		// DEFEND BUTTON
		if (v.getId() == R.id.bBattle_Defend) {

			tvHeroHealth.setText("Health: " + heroHealth + " / "
					+ heroMaxHealth);
			tvHeroEnergy.setText("Energy: " + heroEnergy + " / "
					+ heroMaxEnergy);

			if (playerTurn == true) {
				Defend();
				heroPhDefense += 2;
			}
			if (playerTurn == false) {
				EnemyAttack();
			}
		}

		// BATTLE VICTORY
		if (enemy.health <= 0) {

			enemy.health = 0;

			tvEnemyHealth.setText("Health: " + enemy.health + " / "
					+ enemyMaxHealth);

			Victory();

			startActivity(new Intent(Battle_Activity.this, BattleLog.class));

			finish();
		}

		// BATTLE DEFEAT
		if (heroHealth <= 0) {
			heroHealth = 0;

			tvHeroHealth.setText("Health: " + heroHealth + " / "
					+ heroMaxHealth);

			Defeat();

			startActivity(new Intent(Battle_Activity.this, BattleLog.class));

			finish();

		}

	}

	public void Victory() {

		heroDb = heroDbHelper.getWritableDatabase();

		ContentValues cv = new ContentValues();

		heroExp += enemy.expValue;

		cv.put(colExp, heroExp);
		cv.put(colHealth, heroHealth);
		cv.put(colEnergy, heroEnergy);
		cv.put(colMana, heroMana);
		heroDb.update(statsTable, cv, "1", null);

		if (heroExp >= heroMaxExp) {
			heroDb.close();

			Toast.makeText(getApplicationContext(), "Level Up!",
					Toast.LENGTH_SHORT).show();

			if (heroClass.equals("Warrior")) {
				lvlUp.Warrior();
			}
			if (heroClass.equals("Mage")) {
				lvlUp.Mage();
			}
			if (heroClass.equals("Mercenary")) {
				lvlUp.Mercenary();
			}

		} else {

			heroDb.close();
		}

	}

	private void Defeat() {
		Toast.makeText(getApplicationContext(),
				"Come back when you get stronger", Toast.LENGTH_LONG).show();

		heroDb.close();

	}
}
