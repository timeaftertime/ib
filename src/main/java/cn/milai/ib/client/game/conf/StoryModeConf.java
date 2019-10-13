package cn.milai.ib.client.game.conf;

import cn.milai.ib.client.game.conf.gameprops.SizeConf;

public final class StoryModeConf {

	private StoryModeConf() {} 
	
	// 位置
	public static final int INIT_PLAYER_POS_X = FormSizeConf.BATTLE_WIDTH / 2 + SizeConf.PLAYER_WIDTH / 2;
	public static final int INIT_PLAYER_POS_Y = FormSizeConf.BATTLE_HEIGHT - SizeConf.PLAYER_HEIGHT;
	public static final int MIN_MISSILE_BOSS_Y = SystemConf.sizeProrate(12);
	public static final int MAX_MISSILE_BOSS_Y = SystemConf.sizeProrate(480);
	
	// 概率
	public static final int MISSILE_BOSS_TURN_Y_CHANCE = 5;
	public static final int MISSILE_BOSS_MAX_TURN_Y_CHANCE = 100;
	
}
