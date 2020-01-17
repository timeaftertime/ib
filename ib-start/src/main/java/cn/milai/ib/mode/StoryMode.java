package cn.milai.ib.mode;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import cn.milai.ib.AudioConf;
import cn.milai.ib.AudioPlayer;
import cn.milai.ib.AudioPlayer.AudioController;
import cn.milai.ib.IBObject;
import cn.milai.ib.character.plane.PlayerPlane;
import cn.milai.ib.component.form.GameOverLabel;
import cn.milai.ib.component.form.RestartButton;
import cn.milai.ib.conf.SystemConf;
import cn.milai.ib.container.listener.GameEventListener;
import cn.milai.ib.drama.DramaStarter;
import cn.milai.ib.form.BattleForm;
import cn.milai.ib.interaction.form.FormContainer;
import cn.milai.ib.interaction.form.listener.FormCloseListener;
import cn.milai.ib.interaction.form.listener.GameProcController;
import cn.milai.ib.interaction.form.listener.PlayerController;

public class StoryMode extends GameMode implements FormCloseListener, GameEventListener {

	private FormContainer form;
	private PlayerPlane player;
	private AudioPlayer audioPlayer;
	private AudioController audioController;
	private DramaStarter drama;
	private String formTitle;

	public static final int GAME_OVER_LABEL_POS_X = SystemConf.prorate(400);
	public static final int GAME_OVER_LABEL_POS_Y = SystemConf.prorate(380);
	public static final int RESTART_BUTTON_POS_X = SystemConf.prorate(390);
	public static final int RESTART_BUTTON_POS_Y = SystemConf.prorate(576);

	public StoryMode() {
		form = new BattleForm();
		formTitle = form.getTitle();
		drama = new DramaStarter("cn.milai.ib.Welcome", form);
		player = new PlayerPlane(form.getWidth() / 2, (int) (form.getHeight() * 0.93), form);
		form.addObject(player);
		form.addFormCloseListener(this);
		form.addGameEventListener(this);
		form.addKeyboardListener(new PlayerController(player));
		form.addKeyboardListener(new GameProcController(form));
		audioPlayer = AudioConf.ENDLESS_BG;
		refreshFormTitle();
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
		} else {
			refreshFormTitle();
		}
	}

	@Override
	public void onFormClosed() {
		audioController.close();
		drama.interrupt();
	}

	private void gameOver() {
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
		form.notifyOnce(restart, new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				form.close();
				new StoryMode().start();
			}
		});
		form.addObject(restart);
	}

	private void refreshFormTitle() {
		form.setTitle(formTitle + "         得分：" + player.getGameScore() + "      生命：" + player.getLife());
	}

}
