package cn.milai.ib.client.game.conf;

/**
 * 时间间隔相关默认配置
 * 
 * @author milai
 *
 */
public class IntervalConf {

	/*************************
	 * 子弹发射间隔
	 ************************/
	public static final long NORMAL_BULLET_INTERVAL = SystemConf.speedProrate(3);
	public static final long ENEMY_BULLET_INTERVAL = SystemConf.speedProrate(10);
	public static final int INIT_MISSILE_BOSS_SHOOT_FRAMES = SystemConf.speedProrate(20);
	public static final int MIN_MISSILE_BOSS_SHOOT_FRAMES = SystemConf.speedProrate(8);
	
	/*************************
	 * 无尽模式
	 ************************/
	public static final long MIN_ADD_ENEMEY_WAIT_FRAMES = SystemConf.speedProrate(1);
	public static final int INIT_ADD_NORMAL_ENEMEY_WAIT_FRAMES = SystemConf.speedProrate(20);
	public static final long LEVEL_UP_FRAMES = SystemConf.speedProrate(200);
	public static final long ADD_LADDER_WELCOME_PLANE_FRAMES = SystemConf.speedProrate(24);
	public static final long ADD_VERTICAL_WELCOME_PLANE_FRAMES = SystemConf.speedProrate(18);
	
}
