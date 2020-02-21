package cn.milai.ib.mode;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import cn.milai.ib.character.bullet.Bullet;
import cn.milai.ib.character.explosion.Explosion;
import cn.milai.ib.character.plane.PlayerPlane;
import cn.milai.ib.character.property.HasScore;
import cn.milai.ib.component.form.FormContainer;
import cn.milai.ib.component.form.GameOverLabel;
import cn.milai.ib.component.form.RestartButton;
import cn.milai.ib.component.form.listener.FormCloseListener;
import cn.milai.ib.component.form.listener.GameProcController;
import cn.milai.ib.component.form.listener.PlayerController;
import cn.milai.ib.conf.AudioConf;
import cn.milai.ib.conf.SystemConf;
import cn.milai.ib.container.listener.GameEventListener;
import cn.milai.ib.ex.IBException;
import cn.milai.ib.form.BattleForm;
import cn.milai.ib.obj.IBCharacter;
import cn.milai.ib.obj.IBObject;

/**
 * 游戏模式抽象基类
 * @author milai
 */
public abstract class GameMode extends Thread implements FormCloseListener, GameEventListener {

	protected FormContainer form;
	protected PlayerPlane player;
	private String formTitle;

	private int playerScore;

	public static final int GAME_OVER_LABEL_POS_X = SystemConf.prorate(400);
	public static final int GAME_OVER_LABEL_POS_Y = SystemConf.prorate(380);
	public static final int RESTART_BUTTON_POS_X = SystemConf.prorate(390);
	public static final int RESTART_BUTTON_POS_Y = SystemConf.prorate(576);

	public GameMode() {
		form = new BattleForm();
		player = new PlayerPlane(form.getWidth() / 2, (int) (form.getHeight() * 0.93), form);
	}

	protected void addPlayer() {
		formTitle = form.getTitle();
		form.addObject(player);
		form.addFormCloseListener(this);
		form.addGameEventListener(this);
		form.addKeyboardListener(new PlayerController(player));
		form.addKeyboardListener(new GameProcController(form));
		playerScore = 0;
		refreshFormTitle();
	}

	/**
	 * 线程安全地添加玩家分数
	 * @param score
	 */
	protected synchronized void addPlayerScore(int score) {
		playerScore += score;
	}

	/**
	 * 获取当前玩家分数
	 * @return
	 */
	protected int getPlayerSocre() {
		return playerScore;
	}

	private void refreshFormTitle() {
		form.setTitle(formTitle + "         得分：" + getPlayerSocre() + "      生命：" + player.getLife());
	}

	@Override
	public void onObjectAdded(IBObject obj) {
		if (obj instanceof Explosion) {
			form.playAudio(AudioConf.newBombAudio());
		}
	}

	@Override
	public void onObjectRemoved(IBObject obj) {
		if (obj == player) {
			gameOver();
		} else {
			if (obj instanceof HasScore) {
				HasScore hasScore = ((HasScore) obj);
				IBCharacter lastAttacker = hasScore.getLastAttacker();
				if (lastAttacker instanceof Bullet) {
					if (((Bullet) lastAttacker).getOwner() == player) {
						addPlayerScore(hasScore.getScore());
					}
				}
			}
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
