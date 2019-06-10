package cn.milai.ib.client.game.mode;

import java.util.Random;

import cn.milai.ib.client.game.AudioPlayer;
import cn.milai.ib.client.game.GameObject;
import cn.milai.ib.client.game.conf.AudioConf;
import cn.milai.ib.client.game.conf.StoryModeConf;
import cn.milai.ib.client.game.form.BattleForm;
import cn.milai.ib.client.game.form.GameEventListener;
import cn.milai.ib.client.game.plane.NormalEnemyPlayer;
import cn.milai.ib.client.game.plane.PlayerPlane;
import cn.milai.ib.client.game.plane.WelcomePlane;

public class StoryMode extends GameMode implements GameEventListener {

	private BattleForm form;
	private PlayerPlane player;
	private static Random rand = new Random();
	private AudioPlayer audioPlayer;
	private Thread processThread;
	private long maxEnemyNum = StoryModeConf.MAX_ENEMY_NUM_STAGE_1_LEVEL_1;
	
	public StoryMode() {
		form = new BattleForm();
		player = new PlayerPlane(320, 650, form);
		form.addGameObject(player);
		form.addKeyboardListener(player);
		form.addGameEventListener(this);
		audioPlayer = AudioConf.ENDLESS_BG;
	}
	
	public void run() {
		audioPlayer.play();
		form.start();
		processThread = new Thread(new GameControl());
		processThread.start();
	}
	
	private void addWelComePlayer() throws InterruptedException {
		addVerticalWelcomePlayer(5, 100);
		addLadderWelcomePlayer(5, 35);
	}

	private void addVerticalWelcomePlayer(int row, int disFromCenter) throws InterruptedException {
		if(row < 1)
			throw new IllegalArgumentException("行数必须大于等于 1 ：" + row);
		for(int i=0; i<row; i++) {
			form.addGameObject(new WelcomePlane(form.getWidth() / 2 - disFromCenter, 0, form));
			form.addGameObject(new WelcomePlane(form.getWidth() / 2 + disFromCenter, 0, form));
			Thread.sleep(StoryModeConf.ADD_VERTICAL_WELCOME_PLANE_INTERVAL);
		}
	}
	
	private void addLadderWelcomePlayer(int row, int disOfX) throws InterruptedException {
		if(row < 1)
			throw new IllegalArgumentException("行数必须大于等于 1 ：" + row);
		form.addGameObject(new WelcomePlane(form.getWidth() / 2, 0, form));
		Thread.sleep(StoryModeConf.ADD_LADDER_WELCOME_PLANE_INTERVAL);
		for(int i=2; i<=row; i++) {
			form.addGameObject(new WelcomePlane(form.getWidth() / 2 - i * disOfX, 0, form));
			form.addGameObject(new WelcomePlane(form.getWidth() / 2 + i * disOfX, 0, form));
			Thread.sleep(StoryModeConf.ADD_LADDER_WELCOME_PLANE_INTERVAL);
		}
	}

	private class GameControl implements Runnable {
		
		@Override
		public void run() {
			try {
				addWelComePlayer();
				while(player.getGameScore() < StoryModeConf.STAGE_1_LEVEL_1_SCORE) {
					if(form.getGameObjectCnt(NormalEnemyPlayer.class) < maxEnemyNum)
						addEnemy();
					Thread.sleep(StoryModeConf.ADD_ENEMY_MILLISEC);
				}
				
			} catch (InterruptedException e) {
				return;
			}
		}

		private void addEnemy() {
				form.addGameObject(new NormalEnemyPlayer(rand.nextInt(form.getWidth()), 0, form, player));
		}
	}

	@Override
	public void onGameObjectDead(GameObject obj) {
		if(obj == player) {
			form.setGameOver();
			audioPlayer.close();
			return;
		}
	}

	@Override
	public void onFormClosed() {
		audioPlayer.close();
		processThread.interrupt();
	}
	
}
