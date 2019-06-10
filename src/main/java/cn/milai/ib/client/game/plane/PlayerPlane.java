package cn.milai.ib.client.game.plane;

import cn.milai.ib.client.game.bullet.factory.NormalBulletFactory;
import cn.milai.ib.client.game.conf.AudioConf;
import cn.milai.ib.client.game.conf.BattleConf;
import cn.milai.ib.client.game.conf.ImageConf;
import cn.milai.ib.client.game.form.Command;
import cn.milai.ib.client.game.form.GameForm;
import cn.milai.ib.client.game.form.KeyboardListener;
import cn.milai.ib.client.game.property.Alive;

public class PlayerPlane extends Plane implements KeyboardListener {
	
	private String containerTitle;
	
	private boolean up = false;
	private boolean down = false;
	private boolean left = false;
	private boolean right = false;
	
	private int gameScore = 0;
	
	public PlayerPlane(int x, int y, GameForm container) {
		super(x, y,
				BattleConf.PLAYER_WIDTH, BattleConf.PLAYER_HEIGHT,
				0, 0,
				BattleConf.PLAYER_INIT_MAX_BULLENT,
				BattleConf.PLAYER_INIT_LIFE,
				Camp.PLAYER,
				ImageConf.PLAYER,
				container
				);
		setBullet(new NormalBulletFactory(this));
		this.containerTitle = getContainer().getTitle();
		refreshContainerTitle();
	}
	
	private void refreshContainerTitle() {
		getContainer().setTitle(containerTitle + "         得分：" + gameScore);
	}

	@Override
	public void beforeMove() {
		setSpeedX(0);
		setSpeedY(0);
		if(up)
			setSpeedY(getSpeedY() - BattleConf.PLAYER_SPEED_Y);
		if(down)
			setSpeedY(getSpeedY() + BattleConf.PLAYER_SPEED_Y);
		if(left)
			setSpeedX(getSpeedX() - BattleConf.PLAYER_SPEED_X);
		if(right)
			setSpeedX(getSpeedX() + BattleConf.PLAYER_SPEED_X);
	}
	
	@Override
	protected void afterMove() {
		ensureInOwner();
	}
	
	public void ensureInOwner() {
		if(getX() < 0)
			setX(0);
		if(getY() < getContainer().getHeight() - getContainer().getContentPane().getHeight())
			setY(getContainer().getHeight() - getContainer().getContentPane().getHeight());
		if(getX() + getWidth() > getContainer().getWidth())
			setX(getContainer().getWidth() - getWidth());
		if(getY() + getHeight() > getContainer().getHeight())
			setY(getContainer().getHeight() - getHeight());
	}
	
	@Override
	public void keyDown(Command e) {
		switch (e) {
		case UP:
			up = true;
			break;
		case DOWN:
			down = true;
			break;
		case LEFT:
			left = true;
			break;
		case RIGHT:
			right = true;
			break;
		case SHOOT:
			AudioConf.SHOOT.play();;
			super.shoot();
		default:
			break;
		}
	}
	
	@Override
	public void keyUp(Command e) {
		switch (e) {
		case UP:
			up = false;
			break;
		case DOWN:
			down = false;
			break;
		case LEFT:
			left = false;
			break;
		case RIGHT:
			right = false;
			break;
		default:
			break;
		}
	}

	@Override
	public synchronized void onKill(Alive alive) {
		this.gameScore += alive.getScore();
		refreshContainerTitle();
	}

	@Override
	public int getDamage() {
		return 1;
	}

	@Override
	public int getScore() {
		return 0;
	}
	
	public int getGameScore() {
		return this.gameScore;
	}

}
