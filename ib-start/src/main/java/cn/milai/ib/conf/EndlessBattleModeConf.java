package cn.milai.ib.conf;

import cn.milai.ib.conf.gameprops.SizeConf;

public final class EndlessBattleModeConf {

	private EndlessBattleModeConf() {
	}

	// Level Up
	public static final int INIT_LEVEL_UP_GAME_SCORE = 30;
	public static final int MAX_PLAYER_BULLET_NUM = 8;
	public static final int INIT_MAX_ENEMY_NUM = 5;
	public static final int LEVEL_UP_SCORE_INTERVAL = 10;
	
	// 位置
	public static final int INIT_PLAYER_POS_X = FormSizeConf.BATTLE_WIDTH / 2 + SizeConf.PLAYER_WIDTH / 2;
	public static final int INIT_PLAYER_POS_Y = FormSizeConf.BATTLE_HEIGHT - SizeConf.PLAYER_HEIGHT;

}
