package cn.milai.ib.client.game.obj.plane;

import java.awt.Image;
import java.lang.reflect.InvocationTargetException;
import java.util.Stack;

import cn.milai.ib.client.game.conf.AudioConf;
import cn.milai.ib.client.game.conf.BattleConf;
import cn.milai.ib.client.game.conf.ImageConf;
import cn.milai.ib.client.game.conf.IntervalConf;
import cn.milai.ib.client.game.conf.gameprops.LifeConf;
import cn.milai.ib.client.game.conf.gameprops.SizeConf;
import cn.milai.ib.client.game.conf.gameprops.SpeedConf;
import cn.milai.ib.client.game.form.BattleForm;
import cn.milai.ib.client.game.obj.Bomb;
import cn.milai.ib.client.game.obj.bullet.shooter.BulletShooter;
import cn.milai.ib.client.game.obj.bullet.shooter.NormalBulletShooter;
import cn.milai.ib.client.game.obj.property.Alive;
import cn.milai.ib.client.game.obj.property.HasDamage;

public class PlayerPlane extends Plane {

	private String containerTitle;

	private boolean up = false;
	private boolean down = false;
	private boolean left = false;
	private boolean right = false;
	private boolean isShooting = false;

	// 额定速度
	private int ratedSpeedX;
	private int ratedSpeedY;
	private long shootInterval = IntervalConf.NORMAL_BULLET_FRAMES;

	private int gameScore = 0;
	private long lastShootFrame = -shootInterval;

	private static final PropsStatus INIT_STATUS = new PropsStatus(SizeConf.PLAYER_WIDTH, SizeConf.PLAYER_HEIGHT,
			SpeedConf.PLAYER_SPEED_X, SpeedConf.PLAYER_SPEED_Y, BattleConf.PLAYER_INIT_MAX_BULLENT,
			NormalBulletShooter.class, ImageConf.PLAYER);

	private Stack<PropsStatus> statusStack = new Stack<>();

	public PlayerPlane(int x, int y, BattleForm container) {
		super(x, y, SizeConf.PLAYER_WIDTH, SizeConf.PLAYER_HEIGHT, 0, 0, BattleConf.PLAYER_INIT_MAX_BULLENT,
				LifeConf.PLAYER_INIT_LIFE, Camp.PLAYER, ImageConf.PLAYER, container);
		setBullet(new NormalBulletShooter(this));
		rollBackStatus();
		this.containerTitle = getContainer().getTitle();
	}

	private void refreshContainerTitle() {
		getContainer().setTitle(containerTitle + "         得分：" + gameScore + "      生命：" + getLife());
	}

	@Override
	public void beforeMove() {
		refreshContainerTitle();
		setSpeedX(0);
		setSpeedY(0);
		if (up) {
			setSpeedY(getSpeedY() - ratedSpeedY);
		}
		if (down) {
			setSpeedY(getSpeedY() + ratedSpeedY);
		}
		if (left) {
			setSpeedX(getSpeedX() - ratedSpeedX);
		}
		if (right) {
			setSpeedX(getSpeedX() + ratedSpeedX);
		}
		if(isShooting) {
			BattleForm container = (BattleForm) getContainer();
			if(container.getCurrentFrameCnt() >= lastShootFrame + shootInterval) {
				lastShootFrame = container.getCurrentFrameCnt();
				AudioConf.SHOOT.play();
				shoot();
			}
		}
	}

	@Override
	protected void afterMove() {
		ensureInContainer();
	}
	
	public void setUp() {
		up = true;
	}
	
	public void clearUp() {
		up = false;
	}
	
	public void setDown() {
		down = true;
	}
	
	public void clearDown() {
		down = false;
	}
	
	public void setLeft() {
		left = true;
	}
	
	public void clearLeft() {
		left = false;
	}
	
	public void setRight() {
		right = true;
	}
	
	public void clearRight() {
		right = false;
	}
	
	public void setShooting() {
		isShooting = true;
	}
	
	public void clearShooting() {
		isShooting = false;
	}

	@Override
	public synchronized void onKill(Alive alive) {
		gainScore(alive.getScore());
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

	public void gainScore(int score) {
		gameScore += score;
	}

	@Override
	public void damagedBy(HasDamage attacker) {
		super.damagedBy(attacker);
		rollBackStatus();
		// 如果没有死亡，显示受伤效果
		if (isAlive()) {
			getContainer().addGameObject(new Bomb(getX(), getY(), getContainer()));
		}
		refreshContainerTitle();
	}

	/**
	 * 如果状态栈位空，保存当前状态到状态栈中，否则根据 {@code mustCreateNew} 决定是否复制当前状态并压入栈
	 * 
	 * @param mustCreateNew 栈不为空时是否需要复制当前状态并压入栈
	 */
	public synchronized void pushStatus(boolean mustCreateNew) {
		if (mustCreateNew || statusStack.isEmpty()) {
			try {
				statusStack.push((PropsStatus) currentPropStatus().clone());
			} catch (CloneNotSupportedException e) {
				throw new RuntimeException("复制状态帧失败", e);
			}
		}
	}

	private PropsStatus currentPropStatus() {
		return statusStack.isEmpty() ? INIT_STATUS : statusStack.peek();
	}

	public synchronized void rollBackStatus() {
		if (statusStack.isEmpty()) {
			resetStatus(INIT_STATUS);
			return;
		}
		resetStatus(statusStack.pop());
	}

	private void resetStatus(PropsStatus status) {
		setWidth(status.width);
		setHeight(status.height);
		setRatedSpeedX(status.ratedSpeedX);
		setRatedSpeedY(status.ratedSpeedY);
		setMaxBulletNum(status.maxBulletNum);
		try {
			setBullet(status.shooterClass.getConstructor(Plane.class).newInstance(this));
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException e) {
			throw new RuntimeException("重置 [" + status.shooterClass.getName() + "] BulletFactory 失败", e);
		}
		setImage(status.img);
	}

	public int getRatedSpeedX() {
		return ratedSpeedX;
	}

	public void setRatedSpeedX(int ratedSpeedX) {
		if (ratedSpeedX > SpeedConf.PLAYER_SPEED_X_LIMIT) {
			ratedSpeedX = SpeedConf.PLAYER_SPEED_X_LIMIT;
		}
		this.ratedSpeedX = ratedSpeedX;
	}

	public int getRatedSpeedY() {
		return ratedSpeedY;
	}

	public void setRatedSpeedY(int ratedSpeedY) {
		if (ratedSpeedY > SpeedConf.PLAYER_SPEED_Y_LIMIT) {
			ratedSpeedY = SpeedConf.PLAYER_SPEED_Y_LIMIT;
		}
		this.ratedSpeedY = ratedSpeedY;
	}

	private static class PropsStatus implements Cloneable {
		private int width;
		private int height;
		private int ratedSpeedX;
		private int ratedSpeedY;
		private int maxBulletNum;
		private Class<? extends BulletShooter> shooterClass;
		private Image img;

		public PropsStatus(int width, int height, int ratedSpeedX, int ratedSpeedY, int maxBulletNum,
				Class<? extends BulletShooter> shooterClass, Image img) {
			this.width = width;
			this.height = height;
			this.ratedSpeedX = ratedSpeedX;
			this.ratedSpeedY = ratedSpeedY;
			this.maxBulletNum = maxBulletNum;
			this.shooterClass = shooterClass;
			this.img = img;
		}

		@Override
		public Object clone() throws CloneNotSupportedException {
			return super.clone();
		}

	}
}
