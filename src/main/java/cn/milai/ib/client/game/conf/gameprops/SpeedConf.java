package cn.milai.ib.client.game.conf.gameprops;

import cn.milai.ib.client.game.conf.SystemConf;

public class SpeedConf {

	// 飞机
	public static final int PLAYER_SPEED_X = SystemConf.speedProrate(15);
	public static final int PLAYER_SPEED_Y = SystemConf.speedProrate(15);
	public static final int PLAYER_SPEED_X_LIMIT = SystemConf.speedProrate(30);
	public static final int PLAYER_SPEED_Y_LIMIT = SystemConf.speedProrate(30);
	public static final int ENEMY_SPEED_X = SystemConf.speedProrate(12);
	public static final int ENEMY_SPEED_Y = SystemConf.speedProrate(6);
	public static final int WELCOME_PLANE_SPEED_X = SystemConf.speedProrate(0);
	public static final int WELCOME_PLANE_SPEED_Y = SystemConf.speedProrate(5);
	public static final int ONE_LIFE_HELPER_SPEED_X = SystemConf.speedProrate(0);
	public static final int ONE_LIFE_HELPER_SPEED_Y = SystemConf.speedProrate(15);
	public static final int ACCELERATE_HELPER_SPEED_X = SystemConf.speedProrate(0);
	public static final int ACCELERATE_HELPER_SPEED_Y = SystemConf.speedProrate(15);
	public static final int MISSILE_BOSS_SPEED_X = SystemConf.speedProrate(10);
	public static final int MISSILE_BOSS_SPEED_Y = SystemConf.speedProrate(10);
	public static final int MISSILE_BOSS_ACC_SPEED_X = SystemConf.speedProrate(18);
	
	// 子弹
	public static final int NORMAL_BULLET_SPEED = SystemConf.speedProrate(25);
	public static final int ENEMEY_BULLET_SPEED = SystemConf.speedProrate(25);
	public static final int MISSILE_SPEED = SystemConf.speedProrate(50);
	
}
