package cn.milai.ib.obj.character.plane;

import java.awt.Image;
import java.lang.reflect.InvocationTargetException;
import java.util.Stack;

import cn.milai.ib.AudioConf;
import cn.milai.ib.ImageLoader;
import cn.milai.ib.constant.Camp;
import cn.milai.ib.container.Container;
import cn.milai.ib.obj.character.bullet.shooter.BulletShooter;
import cn.milai.ib.obj.character.bullet.shooter.UpBulletShooter;
import cn.milai.ib.obj.character.explosion.DefaultExplosion;
import cn.milai.ib.property.Alive;
import cn.milai.ib.property.HasDamage;

/**
 * 玩家飞机
 *
 * @author milai
 */
public class PlayerPlane extends Plane {

	private int gameScore = 0;

	/******************
	 * 配置 key
	 ******************/
	private static final String P_RATED_SPEED_X = "ratedSpeedX";
	private static final String P_RATED_SPEED_Y = "ratedSpeedY";
	private static final String P_MAX_RATED_SPEED_X = "maxRatedSpeedX";
	private static final String P_MAX_RATED_SPEED_Y = "maxRatedSpeedY";

	private boolean up = false;
	private boolean down = false;
	private boolean left = false;
	private boolean right = false;
	private boolean shooting = false;

	private final String TITLE_PREFIX;

	// 额定速度
	private int ratedSpeedX;
	private int ratedSpeedY;

	// 最大额定速度
	private int maxRatedSpeedX;
	private int maxRatedSpeedY;

	private final Status INIT_STATUS;
	private Stack<Status> statusStack = new Stack<>();

	public PlayerPlane(int x, int y, Container container) {
		super(x, y, Camp.PLAYER, container);
		initProps();
		this.TITLE_PREFIX = getContainer().getTitle();
		// 在构造方法最后以确保所有变量已经初始化
		INIT_STATUS = new Status(getWidth(), getHeight(), getRatedSpeedX(), getRatedSpeedY(), getMaxBulletNum(),
				getBulletShooterClass(), ImageLoader.getContextImageLoader().loadImage(PlayerPlane.class));
	}

	private void initProps() {
		ratedSpeedX = proratedIntProp(P_RATED_SPEED_X);
		ratedSpeedY = proratedIntProp(P_RATED_SPEED_Y);
		maxRatedSpeedX = proratedIntProp(P_MAX_RATED_SPEED_X);
		maxRatedSpeedY = proratedIntProp(P_MAX_RATED_SPEED_Y);
		setBulletShooter(new UpBulletShooter(this));
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
			setSpeedY(-ratedSpeedY);
		}
		if (down) {
			setSpeedY(ratedSpeedY);
		}
		if (left) {
			setSpeedX(-ratedSpeedX);
		}
		if (right) {
			setSpeedX(ratedSpeedX);
		}
		if (shooting) {
			if (shoot()) {
				AudioConf.SHOOT.play();
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
		shooting = true;
	}

	public void clearShooting() {
		shooting = false;
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
		throw new UnsupportedOperationException("不允许获取 PlayerPlane 的分数");
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
			getContainer().addObject(new DefaultExplosion(getX(), getY(), getContainer()));
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
				statusStack.push((Status) currentStatus().clone());
			} catch (CloneNotSupportedException e) {
				throw new RuntimeException("复制状态帧失败", e);
			}
		}
	}

	private Status currentStatus() {
		return statusStack.isEmpty() ? INIT_STATUS : statusStack.peek();
	}

	public synchronized void rollBackStatus() {
		if (statusStack.isEmpty()) {
			resetStatus(INIT_STATUS);
			return;
		}
		resetStatus(statusStack.pop());
	}

	private void resetStatus(Status status) {
		setWidth(status.width);
		setHeight(status.height);
		setRatedSpeedX(status.ratedSpeedX);
		setRatedSpeedY(status.ratedSpeedY);
		setMaxBulletNum(status.maxBulletNum);
		try {
			setBulletShooter(status.shooterClass.getConstructor(Plane.class).newInstance(this));
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
		if (ratedSpeedX > maxRatedSpeedX) {
			ratedSpeedX = maxRatedSpeedX;
		}
		this.ratedSpeedX = ratedSpeedX;
	}

	public int getRatedSpeedY() {
		return ratedSpeedY;
	}

	public void setRatedSpeedY(int ratedSpeedY) {
		if (ratedSpeedY > maxRatedSpeedY) {
			ratedSpeedY = maxRatedSpeedY;
		}
		this.ratedSpeedY = ratedSpeedY;
	}

	/**
	 * 玩家状态，用于协助道具效果与复原
	 *
	 * @author milai
	 */
	private static class Status implements Cloneable {
		private int width;
		private int height;
		private int ratedSpeedX;
		private int ratedSpeedY;
		private int maxBulletNum;
		private Class<? extends BulletShooter> shooterClass;
		private Image img;

		public Status(int width, int height, int ratedSpeedX, int ratedSpeedY, int maxBulletNum,
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
			// 必须重载该方法使得访问权限变为 public
			return super.clone();
		}

	}
}
