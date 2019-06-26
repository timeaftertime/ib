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

	// 最大子弹数
	public static final int PLAYER_INIT_MAX_BULLENT = 5;
	public static final int ENEMY_MAX_BULLET = 3;
	public static final int WELCOME_PLANE_MAX_BULLET = 0;

	public static final int ENEMY_TYPE = 2;

	// 概率
	public static final int MAX_ENEMEY_SHOOT_CHANCE = 100;
	public static final int ENEMY_SHOOT_CHANCE = 20;
	public static final int MAX_ADD_ENEMY_CHANCE = 100;
	public static final int ADD_ENEMY_CHANCE = 20;
	public static final int MAX_ENEMY_FOLLOW_CHANCE = 100;
	public static final int ENEMY_FOLLOW_CHANCE = 10;

	// 子弹力量
	public static final int NORMAL_BULLET_POWER = 1;
	public static final int ENEMY_BULLET_POWER = 1;
	public static final int MISSILE_POWER = 3;

	public static final long BOMB_LAST_MILLISEC = 500;

	// 位置
	public static final int GAME_OVER_LABEL_POS_X = SystemConf.sizeProrate(40);
	public static final int GAME_OVER_LABEL_POS_Y = SystemConf.sizeProrate(300);
	public static final int RESTART_BUTTON_POS_X = SystemConf.sizeProrate(300);
	public static final int RESTART_BUTTON_POS_Y = SystemConf.sizeProrate(480);

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
