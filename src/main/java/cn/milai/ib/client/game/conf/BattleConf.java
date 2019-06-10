package cn.milai.ib.client.game.conf;

import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.Map;

import cn.milai.ib.client.game.form.Command;

public final class BattleConf {

	private BattleConf() {
	}
	
	// 窗体设置
	public static final String FORM_TITLE = "敌星弹雨";

	// 游戏对象大小
	public static final int PLAYER_WIDTH =SystemConf.prorate(30);
	public static final int PLAYER_HEIGHT = SystemConf.prorate(45);
	public static final int NORMAL_BULLET_WIDTH = SystemConf.prorate(8);
	public static final int NORMAL_BULLET_HEIGHT = SystemConf.prorate(25);
	public static final int ENEMY_BULLET_WIDTH = SystemConf.prorate(8);
	public static final int ENEMY_BULLET_HEIGHT = SystemConf.prorate(25);
	public static final int ENEMY_WIDTH = SystemConf.prorate(40);
	public static final int ENEMY_HEIGHT = SystemConf.prorate(40);
	public static final int BOMB_WIDTH = SystemConf.prorate(40);
	public static final int BOMB_HEIGHT = SystemConf.prorate(40);
	public static final int WELCOME_PLAYER_WIDTH = SystemConf.prorate(60);
	public static final int WELCOME_PLAYER_HEIGHT = SystemConf.prorate(40);

	// 最大子弹数
	public static final int PLAYER_INIT_MAX_BULLENT = 3;
	public static final int ENEMY_MAX_BULLET = 3;
	public static final int WELCOME_PLANE_MAX_BULLET = 0;

	// 速度
	public static final int PLAYER_SPEED_X = 15;
	public static final int PLAYER_SPEED_Y = 15;
	public static final int NORMAL_BULLET_SPEED = 25;
	public static final int ENEMEY_BULLET_SPEED = 25;
	public static final int ENEMY_SPEED_X = 12;
	public static final int ENEMY_SPEED_Y = 6;
	public static final int WELCOME_PLANE_SPEED_X = 0;
	public static final int WELCOME_PLANE_SPEED_Y = 5;

	// 初始生命值
	public static final int PLAYER_INIT_LIFE = 1;
	public static final int ENEMY_LIFE = 1;
	public static final int WELCOME_PLANE_LIFE = 1;

	public static final int ENEMY_TYPE = 2;

	// 概率
	public static final int MAX_ENEMEY_SHOOT_CHANCE = 100;
	public static final int ENEMY_SHOOT_CHANCE = 20;
	public static final int MAX_ADD_ENEMY_CHANCE = 100;
	public static final int ADD_ENEMY_CHANCE = 60;
	public static final int MAX_ENEMY_FOLLOW_CHANCE = 100;
	public static final int ENEMY_FOLLOW_CHANCE = 10;

	// 子弹力量
	public static final int NORMAL_BULLET_POWER = 1;
	public static final int ENEMY_BULLET_POWER = 1;

	// 分数
	public static final int ENEMY_SCORE = 1;
	public static final int WELCOME_PLANE_SCORE = 1;

	public static final long BOMB_LAST_MILLISEC = 500;

	public static final Map<Integer, Command> keySettings = new HashMap<>();

	static {
		initKeySetting();
	}

	private static void initKeySetting() {
		keySettings.put(KeyEvent.VK_W, Command.UP);
		keySettings.put(KeyEvent.VK_S, Command.DOWN);
		keySettings.put(KeyEvent.VK_A, Command.LEFT);
		keySettings.put(KeyEvent.VK_D, Command.RIGHT);
		keySettings.put(KeyEvent.VK_J, Command.SHOOT);
	}
	
}
