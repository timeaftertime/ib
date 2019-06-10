package cn.milai.ib.client.game.mode;

import java.util.Random;

import cn.milai.ib.client.game.AudioPlayer;
import cn.milai.ib.client.game.GameObject;
import cn.milai.ib.client.game.conf.AudioConf;
import cn.milai.ib.client.game.conf.BattleConf;
import cn.milai.ib.client.game.conf.EndlessBattleModeConf;
import cn.milai.ib.client.game.form.BattleForm;
import cn.milai.ib.client.game.form.GameEventListener;
import cn.milai.ib.client.game.plane.NormalEnemyPlayer;
import cn.milai.ib.client.game.plane.PlayerPlane;
import cn.milai.ib.client.game.plane.WelcomePlane;

public class EndlessBattleMode  extends GameMode implements GameEventListener {

	public static void main(String[] args) {
		new EndlessBattleMode().start();
	}
	private BattleForm form;
	private PlayerPlane player;
	private int maxEnemyNum = EndlessBattleModeConf.INIT_MAX_ENEMY_NUM;
	private int preGameScore = EndlessBattleModeConf.INIT_LEVEL_UP_GAME_SCORE;
	private long lastLevelUpTime;
	private static Random rand = new Random();
	private AudioPlayer audioPlayer;
	private long addNormalEnemyInterval = EndlessBattleModeConf.INIT_ADD_NORMAL_ENEMEY_WAIT_MILLISEC;
	private boolean closed = false;
	
	public EndlessBattleMode() {
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
		if(row < 1)
			throw new IllegalArgumentException("行数必须大于等于 1 ：" + row);
		for(int i=0; i<row; i++) {
			form.addGameObject(new WelcomePlane(form.getWidth() / 2 - disFromCenter, 0, form));
			form.addGameObject(new WelcomePlane(form.getWidth() / 2 + disFromCenter, 0, form));
			Thread.sleep(EndlessBattleModeConf.ADD_VERTICAL_WELCOME_PLANE_INTERVAL);
		}
	}
	
	private void addLadderWelcomePlayer(int row, int disOfX) throws InterruptedException {
		if(row < 1)
			throw new IllegalArgumentException("行数必须大于等于 1 ：" + row);
		form.addGameObject(new WelcomePlane(form.getWidth() / 2, 0, form));
		Thread.sleep(EndlessBattleModeConf.ADD_LADDER_WELCOME_PLANE_INTERVAL);
		for(int i=2; i<=row; i++) {
			form.addGameObject(new WelcomePlane(form.getWidth() / 2 - i * disOfX, 0, form));
			form.addGameObject(new WelcomePlane(form.getWidth() / 2 + i * disOfX, 0, form));
			Thread.sleep(EndlessBattleModeConf.ADD_LADDER_WELCOME_PLANE_INTERVAL);
		}
	}

	private class GameControl implements Runnable {
		
		@Override
		public void run() {
			while(!closed) {
				if(form.getGameObjectCnt(NormalEnemyPlayer.class) < maxEnemyNum) {
					randomAddEnemy();
					checkLevelUp();
				}
			}
		}

		private void checkLevelUp() {
			if(player.getGameScore() >= preGameScore + EndlessBattleModeConf.LEVEL_UP_SCORE_INTERVAL)
				levelUp();
			if(System.currentTimeMillis() - lastLevelUpTime > EndlessBattleModeConf.LEVEL_UP_MILLISEC)
				levelUp();
		}

		private void levelUp() {
			maxEnemyNum++;
			preGameScore += EndlessBattleModeConf.LEVEL_UP_SCORE_INTERVAL;
			
			int playerBulletNum = player.getMaxBulletNum() + 1;
			if(playerBulletNum > EndlessBattleModeConf.MAX_PLAYER_BULLET_NUM)
				playerBulletNum = EndlessBattleModeConf.MAX_PLAYER_BULLET_NUM;
			player.setMaxBulletNum(playerBulletNum);
			
			addNormalEnemyInterval = addNormalEnemyInterval * 2 / 3;
			if(addNormalEnemyInterval < EndlessBattleModeConf.MIN_ADD_ENEMEY_WAIT_MILLISEC)
				addNormalEnemyInterval = EndlessBattleModeConf.MIN_ADD_ENEMEY_WAIT_MILLISEC;
			
			lastLevelUpTime = System.currentTimeMillis();
		}

		private void randomAddEnemy() {
			if(rand.nextInt(BattleConf.MAX_ADD_ENEMY_CHANCE) > BattleConf.ADD_ENEMY_CHANCE) {
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
	public void onGameObjectDead(GameObject obj) {
		if(obj == player) {
			form.setGameOver();
			audioPlayer.close();
		}
	}

	@Override
	public void onFormClosed() {
		audioPlayer.close();
		closed = true;
	}
}
