package cn.milai.ib.client.game.conf;

import cn.milai.ib.client.game.conf.gameprops.SizeConf;

public final class StoryModeConf {

	private StoryModeConf() {} 
	
	public static final long ADD_ENEMY_MILLISEC = 200;
	
	// WelcomePlane
	public static final long ADD_VERTICAL_WELCOME_PLANE_INTERVAL =  900;
	public static final long ADD_LADDER_WELCOME_PLANE_INTERVAL =  1200;
	
	// 位置
	public static final int INIT_PLAYER_POS_X = FormSizeConf.BATTLE_WIDTH / 2 + SizeConf.PLAYER_WIDTH / 2;
	public static final int INIT_PLAYER_POS_Y = FormSizeConf.BATTLE_HEIGHT - SizeConf.PLAYER_HEIGHT;
	public static final int MIN_MISSILE_BOSS_Y = SystemConf.sizeProrate(10);
	public static final int MAX_MISSILE_BOSS_Y = SystemConf.sizeProrate(500);
	
	// 概率
	public static final int MISSILE_BOSS_TURN_Y_CHANCE = 5;
	public static final int MISSILE_BOSS_MAX_TURN_Y_CHANCE = 100;
	
	// 时间间隔
	public static final int MISSILE_BOSS_SHOOT_INTERVAL_MILLISEC = 1000;
	public static final int MISSILE_BOSS_SHOOT_INTERVAL_MILLISEC_LIMIT = 400;
	
}
