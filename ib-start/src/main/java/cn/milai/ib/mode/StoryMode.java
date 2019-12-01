package cn.milai.ib.mode;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import cn.milai.ib.AudioConf;
import cn.milai.ib.AudioPlayer;
import cn.milai.ib.AudioPlayer.AudioController;
import cn.milai.ib.conf.SystemConf;
import cn.milai.ib.container.listener.GameEventListener;
import cn.milai.ib.form.BattleForm;
import cn.milai.ib.form.listener.PlayerController;
import cn.milai.ib.obj.IBObject;
import cn.milai.ib.obj.character.helper.AccelerateHelper;
import cn.milai.ib.obj.character.helper.OneLifeHelper;
import cn.milai.ib.obj.character.plane.FollowPlane;
import cn.milai.ib.obj.character.plane.MissileBoss;
import cn.milai.ib.obj.character.plane.PlayerPlane;
import cn.milai.ib.obj.character.plane.WelcomePlane;
import cn.milai.ib.obj.component.GameOverLabel;
import cn.milai.ib.obj.component.RestartButton;
import cn.milai.ib.util.RandomUtil;
import cn.milai.ib.util.TimeUtil;

public class StoryMode extends GameMode implements GameEventListener {

	private BattleForm form;
	private PlayerPlane player;
	private AudioPlayer audioPlayer;
	private AudioController audioController;
	private Thread processThread;

	public static final int GAME_OVER_LABEL_POS_X = SystemConf.prorate(48);
	public static final int GAME_OVER_LABEL_POS_Y = SystemConf.prorate(360);
	public static final int RESTART_BUTTON_POS_X = SystemConf.prorate(360);
	public static final int RESTART_BUTTON_POS_Y = SystemConf.prorate(576);

	private static final long ADD_LADDER_WELCOME_PLANE_FRAMES = 24;

	public StoryMode() {
		form = new BattleForm();
		player = new PlayerPlane(form.getWidth() / 2, form.getHeight() / 3 * 2, form);
		form.addObject(player);
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
		form.addObject(new WelcomePlane(form.getWidth() / 2, 0, form));
		TimeUtil.wait(form, ADD_LADDER_WELCOME_PLANE_FRAMES);
		for (int i = 2; i <= row; i++) {
			form.addObject(new WelcomePlane(form.getWidth() / 2 - i * disOfX, 0, form));
			form.addObject(new WelcomePlane(form.getWidth() / 2 + i * disOfX, 0, form));
			TimeUtil.wait(form, ADD_LADDER_WELCOME_PLANE_FRAMES);
		}
	}

	private class GameControl implements Runnable {

		@Override
		public void run() {
			try {
				addLadderWelcomePlayer(5, SystemConf.prorate(35));

				// Stage 1
				TimeUtil.wait(form, 60);
				form.addObject(new AccelerateHelper(form.getWidth() / 2, 0, form));
				TimeUtil.wait(form, 8);
				form.addObject(new OneLifeHelper(form.getWidth() / 6, 0, form));
				form.addObject(new OneLifeHelper(form.getWidth() / 6 * 5, 0, form));
				TimeUtil.wait(form, 116);
				MissileBoss boss1 = new MissileBoss(SystemConf.prorate(300), SystemConf.prorate(-90), player, form);
				form.addObject(boss1);
				int preSocre = 20;
				while (boss1.isAlive()) {
					TimeUtil.wait(form, 66);
					if (player.getGameScore() > preSocre) {
						preSocre += 30;
						form.addObject(new AccelerateHelper(RandomUtil.nextInt(form.getWidth() / 6 * 5), 0, form));
					}
					for (int i = 0; i < 10; i++) {
						form.addObject(new WelcomePlane(form.getWidth() / 10 * i, 0, form));
						form.addObject(new WelcomePlane(form.getWidth() / 10 * i, -60, form));
					}
					while (form.countOf(FollowPlane.class) < 2) {
						form.addObject(new FollowPlane(RandomUtil.nextInt(form.getWidth()), 0, player, form));
					}
					TimeUtil.wait(form, 66);
					form.addObject(new OneLifeHelper(RandomUtil.nextInt(form.getWidth() / 6 * 5), 0, form));
				}

			} catch (InterruptedException e) {
				return;
			}
		}
	}

	@Override
	public void onGameObjectDead(IBObject obj) {
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
		GameOverLabel gameOverLabel = new GameOverLabel(GAME_OVER_LABEL_POS_X, GAME_OVER_LABEL_POS_Y, form);
		form.addObject(gameOverLabel);
	}

	private void showRestartButton() {
		RestartButton restart = new RestartButton(RESTART_BUTTON_POS_X, RESTART_BUTTON_POS_Y, form);
		restart.addOnceMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				form.dispose();
				new StoryMode().start();
			}
		});
		form.addObject(restart);
	}

}
