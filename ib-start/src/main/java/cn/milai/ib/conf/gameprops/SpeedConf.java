package cn.milai.ib.conf.gameprops;

import cn.milai.ib.conf.SystemConf;

public class SpeedConf {

	// 飞机
	public static final int PLAYER_SPEED_X = SystemConf.frameProrate(15);
	public static final int PLAYER_SPEED_Y = SystemConf.frameProrate(15);
	public static final int PLAYER_SPEED_X_LIMIT = SystemConf.frameProrate(30);
	public static final int PLAYER_SPEED_Y_LIMIT = SystemConf.frameProrate(30);
	public static final int WELCOME_PLANE_SPEED_X = SystemConf.frameProrate(0);
	public static final int WELCOME_PLANE_SPEED_Y = SystemConf.frameProrate(5);
	public static final int ONE_LIFE_HELPER_SPEED_X = SystemConf.frameProrate(0);
	public static final int ONE_LIFE_HELPER_SPEED_Y = SystemConf.frameProrate(15);
	public static final int ACCELERATE_HELPER_SPEED_X = SystemConf.frameProrate(0);
	public static final int ACCELERATE_HELPER_SPEED_Y = SystemConf.frameProrate(15);
	public static final int MISSILE_BOSS_ACC_SPEED_X = SystemConf.frameProrate(18);
	
	// 子弹
	public static final int NORMAL_BULLET_SPEED = SystemConf.frameProrate(25);
	public static final int ENEMEY_BULLET_SPEED = SystemConf.frameProrate(25);
	public static final int MISSILE_BOSS_SPEED_X = SystemConf.frameProrate(10);
	public static final int MISSILE_BOSS_SPEED_Y = SystemConf.frameProrate(10);
	public static final int MISSILE_SPEED = SystemConf.frameProrate(50);
	public static final int ENEMY_SPEED_X = SystemConf.frameProrate(12);
	public static final int ENEMY_SPEED_Y = SystemConf.frameProrate(6);
	
}
