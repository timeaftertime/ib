package cn.milai.ib.mode;

import cn.milai.ib.character.plane.FollowPlane;
import cn.milai.ib.character.plane.WelcomePlane;
import cn.milai.ib.conf.AudioConf;
import cn.milai.ib.conf.SystemConf;
import cn.milai.ib.util.RandomUtil;
import cn.milai.ib.util.TimeUtil;

/**
 * 无尽模式
 * @author milai
 */
public class EndlessBattleMode extends GameMode {

	private int maxEnemyNum = INIT_MAX_ENEMY_NUM;
	private int preGameScore = INIT_LEVEL_UP_GAME_SCORE;
	private long lastLevelUpTime;
	private long addNormalEnemyInterval = INIT_ADD_NORMAL_ENEMEY_FRAMES;
	private boolean closed = false;

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

	private static final long ADD_VERTICAL_WELCOME_PLANE_FRAMES = 16;

	private static final double ADD_ENEMY_CHANCE = 0.2;

	@Override
	public void run() {
		Thread gameController = new Thread(new GameControl(), "EndlessBattleModeGameControl");
		gameController.setDaemon(true);
		gameController.start();
	}

	private class GameControl implements Runnable {

		@Override
		public void run() {
			form.playAudio(AudioConf.BGM.newAudio());
			addWelComePlayer();
			// 消灭所有欢迎机则奖励分数
			if (player.getGameScore() >= 29) {
				player.gainScore(30);
			}
			while (!closed) {
				if (form.getAll(FollowPlane.class).size() < maxEnemyNum) {
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
				form.addObject(new WelcomePlane(form.getWidth() / 2 - disFromCenter, 0, form));
				form.addObject(new WelcomePlane(form.getWidth() / 2 + disFromCenter, 0, form));
				TimeUtil.wait(form, ADD_VERTICAL_WELCOME_PLANE_FRAMES);
			}
		}

		private void addLadderWelcomePlayer(int row, int disOfX) {
			if (row < 1)
				throw new IllegalArgumentException("行数必须大于等于 1 ：" + row);
			form.addObject(new WelcomePlane(form.getWidth() / 2, 0, form));
			TimeUtil.wait(form, ADD_LADDER_WELCOME_PLANE_FRAMES);
			for (int i = 2; i <= row; i++) {
				form.addObject(new WelcomePlane(form.getWidth() / 2 - i * disOfX, 0, form));
				form.addObject(new WelcomePlane(form.getWidth() / 2 + i * disOfX, 0, form));
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
			if (RandomUtil.nextLess(ADD_ENEMY_CHANCE)) {
				form.addObject(new FollowPlane(RandomUtil.nextInt(form.getWidth()), 0, form));
				TimeUtil.wait(form, addNormalEnemyInterval);
			}
		}
	}

	@Override
	protected void gameOver() {
		super.gameOver();
		form.stopAudio(AudioConf.BGM_CODE);
	}

	@Override
	public void onFormClosed() {
		closed = true;
	}

}
