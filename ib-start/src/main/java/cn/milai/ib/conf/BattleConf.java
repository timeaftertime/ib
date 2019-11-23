package cn.milai.ib.conf;

public final class BattleConf {

	private BattleConf() {
	}

	// 最大子弹数
	public static final int PLAYER_INIT_MAX_BULLENT = 5;
	public static final int WELCOME_PLANE_MAX_BULLET = 0;

	// 概率
	public static final int MAX_ENEMEY_SHOOT_CHANCE = 100;
	public static final int ENEMY_SHOOT_CHANCE = 20;
	public static final int MAX_ADD_ENEMY_CHANCE = 100;
	public static final int ADD_ENEMY_CHANCE = 20;

	// 子弹力量
	public static final int NORMAL_BULLET_POWER = 1;

	public static final long BOMB_LAST_MILLISEC = 500;

	// 位置
	public static final int GAME_OVER_LABEL_POS_X = SystemConf.prorate(48);
	public static final int GAME_OVER_LABEL_POS_Y = SystemConf.prorate(360);
	public static final int RESTART_BUTTON_POS_X = SystemConf.prorate(360);
	public static final int RESTART_BUTTON_POS_Y = SystemConf.prorate(576);

}
