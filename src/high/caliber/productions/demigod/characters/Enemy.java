package high.caliber.productions.demigod.characters;

public class Enemy {

	public static final int ID_INDEX = 0;
	public static final int CLASS_INDEX = 1;
	public static final int NAME_INDEX = 2;
	public static final int LEVEL_INDEX = 3;
	public static final int EXP_VALUE_INDEX = 4;
	public static final int HEALTH_INDEX = 5;
	public static final int ENERGY_INDEX = 6;
	public static final int MANA_INDEX = 7;
	public static final int ATTACK_INDEX = 8;
	public static final int MAGIC_INDEX = 9;
	public static final int PH_DEF_INDEX = 10;
	public static final int MG_DEF_INDEX = 11;
	public static final int AGILITY_INDEX = 12;
	public static final int DEXTERITY_INDEX = 13;

	public int id;
	public String _class;
	public String name;
	public int level;
	public int expValue;
	public int health;
	public int energy;
	public int mana;
	public int attack;
	public int magic;
	public int phDefense;
	public int mgDefense;
	public int agility;
	public int dexterity;

	public boolean isEnemyTurn = false;

	/**
	 * Simple melee attack
	 * 
	 * @return Enemy's Attack value
	 */
	public int Attack() {
		return this.attack;
	}

	/**
	 * Simple defend option that boosts enemy's physical defense +2 for a turn
	 */
	public void Defend() {

		if (this.isEnemyTurn) {
			this.phDefense += 2;
		}

	}

	/**
	 * Simple AI mechanics, needs much more attention on return values/detailed
	 * "thought" for AI
	 */
	public void chooseAction() {
		if (this.health > 5) {
			Attack();

		} else {
			Defend();
		}

	}

}
