package cn.milai.ib.client.game.mode;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import cn.milai.ib.client.game.AudioPlayer;
import cn.milai.ib.client.game.AudioPlayer.AudioController;
import cn.milai.ib.client.game.conf.AudioConf;
import cn.milai.ib.client.game.conf.BattleConf;
import cn.milai.ib.client.game.conf.ImageConf;
import cn.milai.ib.client.game.conf.IntervalConf;
import cn.milai.ib.client.game.conf.StoryModeConf;
import cn.milai.ib.client.game.conf.gameprops.SizeConf;
import cn.milai.ib.client.game.form.BattleForm;
import cn.milai.ib.client.game.form.listener.GameEventListener;
import cn.milai.ib.client.game.form.listener.PlayerController;
import cn.milai.ib.client.game.obj.GameEntity;
import cn.milai.ib.client.game.obj.ImageButton;
import cn.milai.ib.client.game.obj.helper.AccelerateHelper;
import cn.milai.ib.client.game.obj.helper.OneLifeHelper;
import cn.milai.ib.client.game.obj.plane.MissileBoss;
import cn.milai.ib.client.game.obj.plane.NormalEnemyPlayer;
import cn.milai.ib.client.game.obj.plane.PlayerPlane;
import cn.milai.ib.client.game.obj.plane.WelcomePlane;
import cn.milai.ib.client.util.RandomUtil;
import cn.milai.ib.client.util.TimeUtil;

public class StoryMode extends GameMode implements GameEventListener {

	private BattleForm form;
	private PlayerPlane player;
	private AudioPlayer audioPlayer;
	private AudioController audioController;
	private Thread processThread;

	public StoryMode() {
		form = new BattleForm();
		player = new PlayerPlane(StoryModeConf.INIT_PLAYER_POS_X, StoryModeConf.INIT_PLAYER_POS_Y, form);
		form.addGameObject(player);
		form.addGameEventListener(this);
		form.addKeyboardListener(new PlayerController(player));
		audioPlayer = AudioConf.ENDLESS_BG;
	}

	public void run() {
		audioController = audioPlayer.play();
		form.start();
		processThread = new Thread(new GameControl());
		processThread.setDaemon(true);
		processThread.start();
	}

	private void addLadderWelcomePlayer(int row, int disOfX) throws InterruptedException {
		if (row < 1) {
			throw new IllegalArgumentException("行数必须大于等于 1 ：" + row);
		}
		form.addGameObject(new WelcomePlane(form.getWidth() / 2, 0, form));
		TimeUtil.wait(form, IntervalConf.ADD_LADDER_WELCOME_PLANE_FRAMES);
		for (int i = 2; i <= row; i++) {
			form.addGameObject(new WelcomePlane(form.getWidth() / 2 - i * disOfX, 0, form));
			form.addGameObject(new WelcomePlane(form.getWidth() / 2 + i * disOfX, 0, form));
			TimeUtil.wait(form, IntervalConf.ADD_LADDER_WELCOME_PLANE_FRAMES);
		}
	}

	private class GameControl implements Runnable {

		@Override
		public void run() {
			try {
				addLadderWelcomePlayer(5, 35);

				// Stage 1
				TimeUtil.wait(form, 60);
				form.addGameObject(new AccelerateHelper(form.getWidth() / 2, 0, form));
				TimeUtil.wait(form, 8);
				form.addGameObject(new OneLifeHelper(form.getWidth() / 6, 0, form));
				form.addGameObject(new OneLifeHelper(form.getWidth() / 6 * 5, 0, form));
				TimeUtil.wait(form, 116);
				MissileBoss boss1 = new MissileBoss(300, -90, player, form);
				form.addGameObject(boss1);
				int preSocre = 20;
				while (boss1.isAlive()) {
					TimeUtil.wait(form, 66);
					if (player.getGameScore() > preSocre) {
						preSocre += 30;
						form.addGameObject(new AccelerateHelper(
								RandomUtil.nextInt(form.getWidth() - SizeConf.ACCELERATE_HELPER_WIDTH), 0, form));
					}
					for (int i = 0; i < 10; i++) {
						form.addGameObject(new WelcomePlane(form.getWidth() / 10 * i, 0, form));
						form.addGameObject(new WelcomePlane(form.getWidth() / 10 * i, -60, form));
					}
					while (form.getGameObjectCnt(NormalEnemyPlayer.class) < 2) {
						form.addGameObject(new NormalEnemyPlayer(RandomUtil.nextInt(form.getWidth()), 0, player, form));
					}
					TimeUtil.wait(form, 66);
					form.addGameObject(new OneLifeHelper(
							RandomUtil.nextInt(form.getWidth() - SizeConf.ONE_LIFE_HELPER_WIDTH), 0, form));
				}

			} catch (InterruptedException e) {
				return;
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
		processThread.interrupt();
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
				form.dispose();
				new StoryMode().start();
			}
		});
		form.addGameObject(restart);
	}

}
