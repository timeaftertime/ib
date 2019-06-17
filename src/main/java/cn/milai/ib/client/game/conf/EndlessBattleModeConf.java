package cn.milai.ib.client.game.conf;

public final class EndlessBattleModeConf {

	private EndlessBattleModeConf() {
	}

	// Level Up
	public static final int INIT_LEVEL_UP_GAME_SCORE = 30;
	public static final int LEVEL_UP_SCORE_INTERVAL = 10;
	public static final long LEVEL_UP_MILLISEC = 10000;
	public static final int MAX_PLAYER_BULLET_NUM = 8;
	public static final int INIT_MAX_ENEMY_NUM = 5;
	public static final int INIT_ADD_NORMAL_ENEMEY_WAIT_MILLISEC = 1000;
	public static final long MIN_ADD_ENEMEY_WAIT_MILLISEC = 50;

	// WelcomePlane
	public static final long ADD_VERTICAL_WELCOME_PLANE_INTERVAL = 900;
	public static final long ADD_LADDER_WELCOME_PLANE_INTERVAL = 1200;

	// 位置
	public static final int INIT_PLAYER_POS_X = FormSizeConf.BATTLE_WIDTH / 2 + BattleConf.PLAYER_WIDTH / 2;
	public static final int INIT_PLAYER_POS_Y = FormSizeConf.BATTLE_HEIGHT - BattleConf.PLAYER_HEIGHT;

}
