package cn.milai.ib.obj.plane;

import java.awt.Image;
import java.lang.reflect.InvocationTargetException;
import java.util.Stack;

import cn.milai.ib.AudioConf;
import cn.milai.ib.ImageLoader;
import cn.milai.ib.conf.SystemConf;
import cn.milai.ib.constant.Camp;
import cn.milai.ib.container.Container;
import cn.milai.ib.obj.Bomb;
import cn.milai.ib.obj.bullet.shooter.BulletShooter;
import cn.milai.ib.obj.bullet.shooter.UpBulletShooter;
import cn.milai.ib.property.Alive;
import cn.milai.ib.property.HasDamage;

public class PlayerPlane extends Plane {

	private static final int WIDTH = SystemConf.prorate(36);
	private static final int HEIGHT = SystemConf.prorate(54);

	private static final int INIT_LIFE = 3;
	private static final int INIT_MAX_BULLET = 5;

	private final String TITLE_PREFIX;

	/**
	 * X 方向初始额定速度
	 */
	private static final int DEF_SPEED_X = SystemConf.prorate(15);

	/**
	 * Y 方向初始额定速度
	 */
	private static final int DEF_SPEED_Y = SystemConf.prorate(15);

	/**
	 * X 方向允许的最大额定速度
	 */
	private static final int MAX_SPEED_X = SystemConf.prorate(30);

	/**
	 * Y 方向允许的最大额定速度
	 */
	private static final int MAX_SPEED_Y = SystemConf.prorate(30);

	private boolean up = false;
	private boolean down = false;
	private boolean left = false;
	private boolean right = false;
	private boolean isShooting = false;

	// 额定速度
	private int ratedSpeedX;
	private int ratedSpeedY;
	private long shootInterval = 3;

	private int gameScore = 0;
	private long lastShootFrame = -shootInterval;

	private static final PropsStatus INIT_STATUS = new PropsStatus(WIDTH, HEIGHT, DEF_SPEED_X, DEF_SPEED_Y,
			INIT_MAX_BULLET, UpBulletShooter.class, ImageLoader.getContextImageLoader().loadImage(PlayerPlane.class));

	private Stack<PropsStatus> statusStack = new Stack<>();

	public PlayerPlane(int x, int y, Container container) {
		super(x, y, WIDTH, HEIGHT, 0, 0, INIT_MAX_BULLET, INIT_LIFE, Camp.PLAYER, container);
		setBullet(new UpBulletShooter(this));
		rollBackStatus();
		this.TITLE_PREFIX = getContainer().getTitle();
	}

	private void refreshContainerTitle() {
		getContainer().setTitle(TITLE_PREFIX + "         得分：" + gameScore + "      生命：" + getLife());
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
		if (isShooting) {
			if (getContainer().currentFrame() >= lastShootFrame + shootInterval) {
				lastShootFrame = getContainer().currentFrame();
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
		if (ratedSpeedX > MAX_SPEED_X) {
			ratedSpeedX = MAX_SPEED_X;
		}
		this.ratedSpeedX = ratedSpeedX;
	}

	public int getRatedSpeedY() {
		return ratedSpeedY;
	}

	public void setRatedSpeedY(int ratedSpeedY) {
		if (ratedSpeedY > MAX_SPEED_Y) {
			ratedSpeedY = MAX_SPEED_Y;
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
