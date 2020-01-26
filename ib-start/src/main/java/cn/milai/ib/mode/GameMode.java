package cn.milai.ib.mode;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import cn.milai.ib.IBObject;
import cn.milai.ib.character.Explosion;
import cn.milai.ib.character.plane.PlayerPlane;
import cn.milai.ib.component.form.GameOverLabel;
import cn.milai.ib.component.form.RestartButton;
import cn.milai.ib.conf.AudioConf;
import cn.milai.ib.conf.SystemConf;
import cn.milai.ib.container.listener.GameEventListener;
import cn.milai.ib.ex.IBException;
import cn.milai.ib.form.BattleForm;
import cn.milai.ib.interaction.form.FormContainer;
import cn.milai.ib.interaction.form.listener.FormCloseListener;
import cn.milai.ib.interaction.form.listener.GameProcController;
import cn.milai.ib.interaction.form.listener.PlayerController;

/**
 * 游戏模式抽象基类
 * @author milai
 */
public abstract class GameMode extends Thread implements FormCloseListener, GameEventListener {

	protected FormContainer form;
	protected PlayerPlane player;
	private String formTitle;

	public static final int GAME_OVER_LABEL_POS_X = SystemConf.prorate(400);
	public static final int GAME_OVER_LABEL_POS_Y = SystemConf.prorate(380);
	public static final int RESTART_BUTTON_POS_X = SystemConf.prorate(390);
	public static final int RESTART_BUTTON_POS_Y = SystemConf.prorate(576);

	public GameMode() {
		form = new BattleForm();
		player = new PlayerPlane(form.getWidth() / 2, (int) (form.getHeight() * 0.93), form);
		formTitle = form.getTitle();
		form.addObject(player);
		form.addFormCloseListener(this);
		form.addGameEventListener(this);
		form.addKeyboardListener(new PlayerController(player));
		form.addKeyboardListener(new GameProcController(form));
		refreshFormTitle();
	}

	private void refreshFormTitle() {
		form.setTitle(formTitle + "         得分：" + player.getGameScore() + "      生命：" + player.getLife());
	}

	@Override
	public void onObjectAdded(IBObject obj) {
		if (obj instanceof Explosion) {
			form.playAudio(AudioConf.BOMB.newAudio());
		}
	}

	@Override
	public void onObjectRemoved(IBObject obj) {
		if (obj == player) {
			gameOver();
		} else {
			refreshFormTitle();
		}
	}

	protected void gameOver() {
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
				try {
					GameMode.this.getClass().newInstance().start();
				} catch (InstantiationException | IllegalAccessException e1) {
					throw new IBException("创建 GameMode 实例失败，gameModeClass = %s", GameMode.this.getClass().getName());
				}
			}
		});
		form.addObject(restart);
	}

	/**
	 * 开始容器并启动游戏主逻辑线程
	 */
	@Override
	public final synchronized void start() {
		form.start();
		super.start();
	}

}
