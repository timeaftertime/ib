package cn.milai.ib.mode;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import cn.milai.ib.AudioConf;
import cn.milai.ib.AudioPlayer;
import cn.milai.ib.AudioPlayer.AudioController;
import cn.milai.ib.GameObject;
import cn.milai.ib.conf.ImageConf;
import cn.milai.ib.conf.SystemConf;
import cn.milai.ib.container.listener.GameEventListener;
import cn.milai.ib.form.BattleForm;
import cn.milai.ib.form.listener.PlayerController;
import cn.milai.ib.obj.ImageButton;
import cn.milai.ib.obj.plane.FollowPlane;
import cn.milai.ib.obj.plane.PlayerPlane;
import cn.milai.ib.obj.plane.WelcomePlane;
import cn.milai.ib.util.RandomUtil;
import cn.milai.ib.util.TimeUtil;

public class EndlessBattleMode extends GameMode implements GameEventListener {

	private BattleForm form;
	private PlayerPlane player;
	private int maxEnemyNum = INIT_MAX_ENEMY_NUM;
	private int preGameScore = INIT_LEVEL_UP_GAME_SCORE;
	private long lastLevelUpTime;
	private AudioPlayer audioPlayer;
	private AudioController audioController;
	private long addNormalEnemyInterval = INIT_ADD_NORMAL_ENEMEY_FRAMES;
	private boolean closed = false;

	public static final int GAME_OVER_LABEL_POS_X = SystemConf.prorate(48);
	public static final int GAME_OVER_LABEL_POS_Y = SystemConf.prorate(360);
	public static final int RESTART_BUTTON_POS_X = SystemConf.prorate(360);
	public static final int RESTART_BUTTON_POS_Y = SystemConf.prorate(576);
	public static final int GAME_OVER_LABEL_WIDTH = SystemConf.prorate(672);
	public static final int GAME_OVER_LABEL_HEIGHT = SystemConf.prorate(120);
	public static final int RESTART_BUTTON_WIDTH = SystemConf.prorate(144);
	public static final int RESTART_BUTTON_HEIGHT = SystemConf.prorate(36);

	private static final int INIT_LEVEL_UP_GAME_SCORE = 30;
	private static final int MAX_PLAYER_BULLET_NUM = 8;
	private static final int INIT_MAX_ENEMY_NUM = 5;
	private static final int LEVEL_UP_SCORE_INTERVAL = 10;

	/**
	 * 最短添加新敌机的时间间隔
	 */
	private static final long MIN_ADD_ENEMEY_FRAMES = 1;

	/**
	 * 初始添加新敌机的时间间隔
	 */
	private static final int INIT_ADD_NORMAL_ENEMEY_FRAMES = 20;

	private static final long LEVEL_UP_FRAMES = 200;

	private static final long ADD_LADDER_WELCOME_PLANE_FRAMES = 24;

	private static final long ADD_VERTICAL_WELCOME_PLANE_FRAMES = 18;

	/******************
	 * ADD_ENEMY_CHANCE / MAX_ADD_ENEMY_CHANCE 即新增敌机的概率
	 ******************/
	private static final int ADD_ENEMY_CHANCE = 20;
	private static final int MAX_ADD_ENEMY_CHANCE = 100;

	public EndlessBattleMode() {
		form = new BattleForm();
		player = new PlayerPlane(form.getWidth() / 2, form.getHeight() / 3 * 2, form);
		form.addGameObject(player);
		form.addGameEventListener(this);
		form.addKeyboardListener(new PlayerController(player));
		audioPlayer = AudioConf.ENDLESS_BG;
	}

	public void run() {
		audioController = audioPlayer.play();
		form.start();
		Thread gameController = new Thread(new GameControl(), "EndlessBattleModeGameControl");
		gameController.setDaemon(true);
		gameController.start();
	}

	private class GameControl implements Runnable {

		@Override
		public void run() {
			addWelComePlayer();
			// 消灭所有欢迎机则奖励分数
			if (player.getGameScore() >= 29) {
				player.gainScore(30);
			}
			while (!closed) {
				if (form.countOf(FollowPlane.class) < maxEnemyNum) {
					randomAddEnemy();
					checkLevelUp();
				}
			}
		}

		private void addWelComePlayer() {
			addVerticalWelcomePlayer(5, SystemConf.prorate(100));
			addVerticalWelcomePlayer(5, SystemConf.prorate(200));
			addLadderWelcomePlayer(5, SystemConf.prorate(35));
			TimeUtil.wait(form, 42);
		}

		private void addVerticalWelcomePlayer(int row, int disFromCenter) {
			if (row < 1)
				throw new IllegalArgumentException("行数必须大于等于 1 ：" + row);
			for (int i = 0; i < row; i++) {
				form.addGameObject(new WelcomePlane(form.getWidth() / 2 - disFromCenter, 0, form));
				form.addGameObject(new WelcomePlane(form.getWidth() / 2 + disFromCenter, 0, form));
				TimeUtil.wait(form, ADD_VERTICAL_WELCOME_PLANE_FRAMES);
			}
		}

		private void addLadderWelcomePlayer(int row, int disOfX) {
			if (row < 1)
				throw new IllegalArgumentException("行数必须大于等于 1 ：" + row);
			form.addGameObject(new WelcomePlane(form.getWidth() / 2, 0, form));
			TimeUtil.wait(form, ADD_LADDER_WELCOME_PLANE_FRAMES);
			for (int i = 2; i <= row; i++) {
				form.addGameObject(new WelcomePlane(form.getWidth() / 2 - i * disOfX, 0, form));
				form.addGameObject(new WelcomePlane(form.getWidth() / 2 + i * disOfX, 0, form));
				TimeUtil.wait(form, ADD_LADDER_WELCOME_PLANE_FRAMES);
			}
		}

		private void checkLevelUp() {
			if (player.getGameScore() >= preGameScore + LEVEL_UP_SCORE_INTERVAL)
				levelUp();
			if (form.currentFrame() - lastLevelUpTime > LEVEL_UP_FRAMES)
				levelUp();
		}

		private void levelUp() {
			maxEnemyNum++;
			preGameScore += LEVEL_UP_SCORE_INTERVAL;

			int playerBulletNum = player.getMaxBulletNum() + 1;
			if (playerBulletNum > MAX_PLAYER_BULLET_NUM)
				playerBulletNum = MAX_PLAYER_BULLET_NUM;
			player.setMaxBulletNum(playerBulletNum);

			addNormalEnemyInterval = addNormalEnemyInterval * 2 / 3;
			if (addNormalEnemyInterval < MIN_ADD_ENEMEY_FRAMES)
				addNormalEnemyInterval = MIN_ADD_ENEMEY_FRAMES;

			lastLevelUpTime = form.currentFrame();
		}

		private void randomAddEnemy() {
			if (RandomUtil.goalAtPossible(ADD_ENEMY_CHANCE, MAX_ADD_ENEMY_CHANCE)) {
				form.addGameObject(new FollowPlane(RandomUtil.nextInt(form.getWidth()), 0, player, form));
				TimeUtil.wait(form, addNormalEnemyInterval);
			}
		}
	}

	@Override
	public void onGameObjectDead(GameObject obj) {
		if (obj == player) {
			gameOver();
		}
	}

	@Override
	public void onFormClosed() {
		audioController.close();
		closed = true;
	}

	private void gameOver() {
		form.setGameOver();
		audioController.close();
		showGameOverLabel();
		showRestartButton();
	}

	private void showGameOverLabel() {
		ImageButton gameOverLabel = new ImageButton(GAME_OVER_LABEL_POS_X, GAME_OVER_LABEL_POS_Y, GAME_OVER_LABEL_WIDTH,
				GAME_OVER_LABEL_HEIGHT, ImageConf.GAME_OVER, form);
		form.addGameObject(gameOverLabel);
	}

	private void showRestartButton() {
		ImageButton restart = new ImageButton(RESTART_BUTTON_POS_X, RESTART_BUTTON_POS_Y, RESTART_BUTTON_WIDTH,
				RESTART_BUTTON_HEIGHT, ImageConf.RESTART, form);
		restart.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				form.setVisible(false);
				form.dispose();
				new EndlessBattleMode().start();
			}
		});
		form.addGameObject(restart);
	}
}
