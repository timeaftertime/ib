package cn.milai.ib.client.game.mode;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Random;

import cn.milai.ib.client.game.AudioPlayer;
import cn.milai.ib.client.game.AudioPlayer.AudioController;
import cn.milai.ib.client.game.conf.AudioConf;
import cn.milai.ib.client.game.conf.BattleConf;
import cn.milai.ib.client.game.conf.EndlessBattleModeConf;
import cn.milai.ib.client.game.conf.ImageConf;
import cn.milai.ib.client.game.form.BattleForm;
import cn.milai.ib.client.game.form.GameEventListener;
import cn.milai.ib.client.game.form.StartForm;
import cn.milai.ib.client.game.obj.GameEntity;
import cn.milai.ib.client.game.obj.ImageButton;
import cn.milai.ib.client.game.obj.plane.NormalEnemyPlayer;
import cn.milai.ib.client.game.obj.plane.PlayerPlane;
import cn.milai.ib.client.game.obj.plane.WelcomePlane;

public class EndlessBattleMode extends GameMode implements GameEventListener {

	private BattleForm form;
	private PlayerPlane player;
	private int maxEnemyNum = EndlessBattleModeConf.INIT_MAX_ENEMY_NUM;
	private int preGameScore = EndlessBattleModeConf.INIT_LEVEL_UP_GAME_SCORE;
	private long lastLevelUpTime;
	private static Random rand = new Random();
	private AudioPlayer audioPlayer;
	private AudioController audioController;
	private long addNormalEnemyInterval = EndlessBattleModeConf.INIT_ADD_NORMAL_ENEMEY_WAIT_MILLISEC;
	private boolean closed = false;

	public EndlessBattleMode() {
		form = new BattleForm();
		player = new PlayerPlane(EndlessBattleModeConf.INIT_PLAYER_POS_X, EndlessBattleModeConf.INIT_PLAYER_POS_Y,
				form);
		form.addGameObject(player);
		form.addKeyboardListener(player);
		form.addGameEventListener(this);
		audioPlayer = AudioConf.ENDLESS_BG;
	}

	public void run() {
		audioController = audioPlayer.play();
		form.start();
		try {
			addWelComePlayer();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		new Thread(new GameControl()).start();
	}

	private void addWelComePlayer() throws InterruptedException {
		addVerticalWelcomePlayer(5, 100);
		addVerticalWelcomePlayer(5, 200);
		addLadderWelcomePlayer(5, 35);
		Thread.sleep(2000);
	}

	private void addVerticalWelcomePlayer(int row, int disFromCenter) throws InterruptedException {
		if (row < 1)
			throw new IllegalArgumentException("行数必须大于等于 1 ：" + row);
		for (int i = 0; i < row; i++) {
			form.addGameObject(new WelcomePlane(form.getWidth() / 2 - disFromCenter, 0, form));
			form.addGameObject(new WelcomePlane(form.getWidth() / 2 + disFromCenter, 0, form));
			Thread.sleep(EndlessBattleModeConf.ADD_VERTICAL_WELCOME_PLANE_INTERVAL);
		}
	}

	private void addLadderWelcomePlayer(int row, int disOfX) throws InterruptedException {
		if (row < 1)
			throw new IllegalArgumentException("行数必须大于等于 1 ：" + row);
		form.addGameObject(new WelcomePlane(form.getWidth() / 2, 0, form));
		Thread.sleep(EndlessBattleModeConf.ADD_LADDER_WELCOME_PLANE_INTERVAL);
		for (int i = 2; i <= row; i++) {
			form.addGameObject(new WelcomePlane(form.getWidth() / 2 - i * disOfX, 0, form));
			form.addGameObject(new WelcomePlane(form.getWidth() / 2 + i * disOfX, 0, form));
			Thread.sleep(EndlessBattleModeConf.ADD_LADDER_WELCOME_PLANE_INTERVAL);
		}
	}

	private class GameControl implements Runnable {

		@Override
		public void run() {
			// 消灭所有欢迎机则奖励分数
			if (player.getGameScore() >= 29) {
				player.gainScore(30);
			}
			while (!closed) {
				if (form.getGameObjectCnt(NormalEnemyPlayer.class) < maxEnemyNum) {
					randomAddEnemy();
					checkLevelUp();
				}
			}
		}

		private void checkLevelUp() {
			if (player.getGameScore() >= preGameScore + EndlessBattleModeConf.LEVEL_UP_SCORE_INTERVAL)
				levelUp();
			if (System.currentTimeMillis() - lastLevelUpTime > EndlessBattleModeConf.LEVEL_UP_MILLISEC)
				levelUp();
		}

		private void levelUp() {
			maxEnemyNum++;
			preGameScore += EndlessBattleModeConf.LEVEL_UP_SCORE_INTERVAL;

			int playerBulletNum = player.getMaxBulletNum() + 1;
			if (playerBulletNum > EndlessBattleModeConf.MAX_PLAYER_BULLET_NUM)
				playerBulletNum = EndlessBattleModeConf.MAX_PLAYER_BULLET_NUM;
			player.setMaxBulletNum(playerBulletNum);

			addNormalEnemyInterval = addNormalEnemyInterval * 2 / 3;
			if (addNormalEnemyInterval < EndlessBattleModeConf.MIN_ADD_ENEMEY_WAIT_MILLISEC)
				addNormalEnemyInterval = EndlessBattleModeConf.MIN_ADD_ENEMEY_WAIT_MILLISEC;

			lastLevelUpTime = System.currentTimeMillis();
		}

		private void randomAddEnemy() {
			if (rand.nextInt(BattleConf.MAX_ADD_ENEMY_CHANCE) > BattleConf.ADD_ENEMY_CHANCE) {
				form.addGameObject(new NormalEnemyPlayer(rand.nextInt(form.getWidth()), 0, form, player));
				try {
					Thread.sleep(addNormalEnemyInterval);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}

	@Override
	public void onGameObjectDead(GameEntity obj) {
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
		showBackButton();
	}

	private void showGameOverLabel() {
		ImageButton gameOverLabel = new ImageButton(BattleConf.GAME_OVER_LABEL_POS_X, BattleConf.GAME_OVER_LABEL_POS_Y,
				BattleConf.GAME_OVER_LABEL_WIDTH, BattleConf.GAME_OVER_LABEL_HEIGHT, ImageConf.GAME_OVER, form);
		form.addGameObject(gameOverLabel);
	}

	private void showRestartButton() {
		ImageButton restart = new ImageButton(BattleConf.RESTART_BUTTON_POS_X, BattleConf.RESTART_BUTTON_POS_Y,
				BattleConf.RESTART_BUTTON_WIDTH, BattleConf.RESTART_BUTTON_HEIGHT, ImageConf.RESTART_BUTTON, form);
		restart.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				form.dispose();
				new EndlessBattleMode().start();
			}
		});
		form.addGameObject(restart);
	}

	private void showBackButton() {
		ImageButton back = new ImageButton(BattleConf.BACK_BUTTON_POS_X, BattleConf.BACK_BUTTON_POS_Y,
				BattleConf.BACK_BUTTON_WIDTH, BattleConf.BACK_BUTTON_HEIGHT, ImageConf.BACK_BUTTON, form);
		back.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				form.dispose();
				new StartForm();
			}
		});
		form.addGameObject(back);
	}
}
