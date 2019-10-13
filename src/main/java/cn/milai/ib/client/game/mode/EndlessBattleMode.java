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
import cn.milai.ib.client.game.conf.IntervalConf;
import cn.milai.ib.client.game.conf.gameprops.SizeConf;
import cn.milai.ib.client.game.form.BattleForm;
import cn.milai.ib.client.game.form.listener.GameEventListener;
import cn.milai.ib.client.game.form.listener.PlayerController;
import cn.milai.ib.client.game.obj.GameEntity;
import cn.milai.ib.client.game.obj.ImageButton;
import cn.milai.ib.client.game.obj.plane.NormalEnemyPlayer;
import cn.milai.ib.client.game.obj.plane.PlayerPlane;
import cn.milai.ib.client.game.obj.plane.WelcomePlane;
import cn.milai.ib.client.util.RandomUtil;
import cn.milai.ib.client.util.TimeUtil;

public class EndlessBattleMode extends GameMode implements GameEventListener {

	private BattleForm form;
	private PlayerPlane player;
	private int maxEnemyNum = EndlessBattleModeConf.INIT_MAX_ENEMY_NUM;
	private int preGameScore = EndlessBattleModeConf.INIT_LEVEL_UP_GAME_SCORE;
	private long lastLevelUpTime;
	private static Random rand = new Random();
	private AudioPlayer audioPlayer;
	private AudioController audioController;
	private long addNormalEnemyInterval = IntervalConf.INIT_ADD_NORMAL_ENEMEY_FRAMES;
	private boolean closed = false;

	public EndlessBattleMode() {
		form = new BattleForm();
		player = new PlayerPlane(EndlessBattleModeConf.INIT_PLAYER_POS_X, EndlessBattleModeConf.INIT_PLAYER_POS_Y,
				form);
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
				if (form.getGameObjectCnt(NormalEnemyPlayer.class) < maxEnemyNum) {
					randomAddEnemy();
					checkLevelUp();
				}
			}
		}

		private void addWelComePlayer() {
			addVerticalWelcomePlayer(5, 100);
			addVerticalWelcomePlayer(5, 200);
			addLadderWelcomePlayer(5, 35);
			TimeUtil.wait(form, 42);
		}

		private void addVerticalWelcomePlayer(int row, int disFromCenter) {
			if (row < 1)
				throw new IllegalArgumentException("行数必须大于等于 1 ：" + row);
			for (int i = 0; i < row; i++) {
				form.addGameObject(new WelcomePlane(form.getWidth() / 2 - disFromCenter, 0, form));
				form.addGameObject(new WelcomePlane(form.getWidth() / 2 + disFromCenter, 0, form));
				TimeUtil.wait(form, IntervalConf.ADD_VERTICAL_WELCOME_PLANE_FRAMES);
			}
		}

		private void addLadderWelcomePlayer(int row, int disOfX) {
			if (row < 1)
				throw new IllegalArgumentException("行数必须大于等于 1 ：" + row);
			form.addGameObject(new WelcomePlane(form.getWidth() / 2, 0, form));
			TimeUtil.wait(form, IntervalConf.ADD_LADDER_WELCOME_PLANE_FRAMES);
			for (int i = 2; i <= row; i++) {
				form.addGameObject(new WelcomePlane(form.getWidth() / 2 - i * disOfX, 0, form));
				form.addGameObject(new WelcomePlane(form.getWidth() / 2 + i * disOfX, 0, form));
				TimeUtil.wait(form, IntervalConf.ADD_LADDER_WELCOME_PLANE_FRAMES);
			}
		}

		private void checkLevelUp() {
			if (player.getGameScore() >= preGameScore + EndlessBattleModeConf.LEVEL_UP_SCORE_INTERVAL)
				levelUp();
			if (form.getCurrentFrameCnt() - lastLevelUpTime > IntervalConf.LEVEL_UP_FRAMES)
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
			if (addNormalEnemyInterval < IntervalConf.MIN_ADD_ENEMEY_FRAMES)
				addNormalEnemyInterval = IntervalConf.MIN_ADD_ENEMEY_FRAMES;

			lastLevelUpTime = form.getCurrentFrameCnt();
		}

		private void randomAddEnemy() {
			if (RandomUtil.goalAtPossible(BattleConf.ADD_ENEMY_CHANCE, BattleConf.MAX_ADD_ENEMY_CHANCE)) {
				form.addGameObject(new NormalEnemyPlayer(rand.nextInt(form.getWidth()), 0, player, form));
				TimeUtil.wait(form, addNormalEnemyInterval);
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
	}

	private void showGameOverLabel() {
		ImageButton gameOverLabel = new ImageButton(BattleConf.GAME_OVER_LABEL_POS_X, BattleConf.GAME_OVER_LABEL_POS_Y,
				SizeConf.GAME_OVER_LABEL_WIDTH, SizeConf.GAME_OVER_LABEL_HEIGHT, ImageConf.GAME_OVER, form);
		form.addGameObject(gameOverLabel);
	}

	private void showRestartButton() {
		ImageButton restart = new ImageButton(BattleConf.RESTART_BUTTON_POS_X, BattleConf.RESTART_BUTTON_POS_Y,
				SizeConf.RESTART_BUTTON_WIDTH, SizeConf.RESTART_BUTTON_HEIGHT, ImageConf.RESTART_BUTTON, form);
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
