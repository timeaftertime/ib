package cn.milai.ib.mode;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import cn.milai.ib.AudioConf;
import cn.milai.ib.AudioPlayer;
import cn.milai.ib.AudioPlayer.AudioController;
import cn.milai.ib.conf.SystemConf;
import cn.milai.ib.container.listener.GameEventListener;
import cn.milai.ib.drama.DramaStarter;
import cn.milai.ib.form.BattleForm;
import cn.milai.ib.form.FormContainer;
import cn.milai.ib.form.listener.PlayerController;
import cn.milai.ib.obj.IBObject;
import cn.milai.ib.obj.character.plane.PlayerPlane;
import cn.milai.ib.obj.component.GameOverLabel;
import cn.milai.ib.obj.component.RestartButton;

public class StoryMode extends GameMode implements GameEventListener {

	private FormContainer form;
	private PlayerPlane player;
	private AudioPlayer audioPlayer;
	private AudioController audioController;
	private DramaStarter drama;

	public static final int GAME_OVER_LABEL_POS_X = SystemConf.prorate(48);
	public static final int GAME_OVER_LABEL_POS_Y = SystemConf.prorate(360);
	public static final int RESTART_BUTTON_POS_X = SystemConf.prorate(360);
	public static final int RESTART_BUTTON_POS_Y = SystemConf.prorate(576);

	public StoryMode() {
		form = new BattleForm();
		drama = new DramaStarter("cn.milai.ib.welcome", form);
		player = new PlayerPlane(form.getWidth() / 2, form.getHeight() / 3 * 2, form);
		form.addObject(player);
		form.addGameEventListener(this);
		form.addKeyboardListener(new PlayerController(player));
		audioPlayer = AudioConf.ENDLESS_BG;
	}

	public void run() {
		audioController = audioPlayer.play();
		form.start();
		drama.start();
	}

	@Override
	public void onObjectRemoved(IBObject obj) {
		if (obj == player) {
			gameOver();
		}
	}

	@Override
	public void onFormClosed() {
		audioController.close();
		drama.interrupt();
	}

	private void gameOver() {
		//		form.setGameOver();
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
				form.close();
				new StoryMode().start();
			}
		});
		form.addObject(restart);
	}

}
