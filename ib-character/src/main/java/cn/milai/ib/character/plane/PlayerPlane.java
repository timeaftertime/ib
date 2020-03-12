package cn.milai.ib.character.plane;

import java.awt.Image;
import java.util.Stack;

import cn.milai.ib.character.bullet.shooter.BulletShooter;
import cn.milai.ib.character.bullet.shooter.UpBulletShooter;
import cn.milai.ib.character.explosion.Explosion;
import cn.milai.ib.container.Container;
import cn.milai.ib.obj.Camp;
import cn.milai.ib.obj.IBCharacter;
import cn.milai.ib.obj.Player;

/**
 * 玩家飞机
 * @author milai
 */
public class PlayerPlane extends AbstractPlane implements Player {

	private boolean up = false;
	private boolean down = false;
	private boolean left = false;
	private boolean right = false;
	private boolean shooting = false;

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
		// 在构造方法最后以确保所有变量已经初始化
		INIT_STATUS = new Status(getWidth(), getHeight(), getRatedSpeedX(), getRatedSpeedY(), getMaxBulletNum(),
			getBulletShooter(), getImage());
	}

	private void initProps() {
		ratedSpeedX = proratedIntProp(P_RATED_SPEED_X);
		ratedSpeedY = proratedIntProp(P_RATED_SPEED_Y);
		maxRatedSpeedX = proratedIntProp(P_MAX_RATED_SPEED_X);
		maxRatedSpeedY = proratedIntProp(P_MAX_RATED_SPEED_Y);
		setBulletShooter(new UpBulletShooter());
	}

	@Override
	public void beforeMove() {
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
			}
		}
	}

	@Override
	protected void afterMove() {
		ensureInContainer();
	}

	@Override
	public void setUp() {
		up = true;
	}

	@Override
	public void clearUp() {
		up = false;
	}

	@Override
	public void setDown() {
		down = true;
	}

	@Override
	public void clearDown() {
		down = false;
	}

	@Override
	public void setLeft() {
		left = true;
	}

	@Override
	public void clearLeft() {
		left = false;
	}

	@Override
	public void setRight() {
		right = true;
	}

	@Override
	public void clearRight() {
		right = false;
	}

	@Override
	public void setShooting() {
		shooting = true;
	}

	@Override
	public void clearShooting() {
		shooting = false;
	}

	@Override
	public int getDamage() {
		return 1;
	}

	@Override
	public synchronized void loseLife(IBCharacter character, int life) throws IllegalArgumentException {
		super.loseLife(character, life);
		rollBackStatus();
		// 如果没有死亡，显示受伤效果
		if (isAlive()) {
			for (Explosion explosion : getExplosionCreator().createExplosions(this)) {
				getContainer().addObject(explosion);
			}
		}
	}

	/**
	 * 如果状态栈位空，保存当前状态到状态栈中，否则根据 {@code mustCreateNew} 决定是否复制当前状态并压入栈
	 * @param mustCreateNew
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
		setBulletShooter(status.shooter);
		setImage(status.img);
	}

	@Override
	public int getRatedSpeedX() {
		return ratedSpeedX;
	}

	public void setRatedSpeedX(int ratedSpeedX) {
		if (ratedSpeedX > maxRatedSpeedX) {
			ratedSpeedX = maxRatedSpeedX;
		}
		this.ratedSpeedX = ratedSpeedX;
	}

	@Override
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
	 * @author milai
	 */
	private static class Status implements Cloneable {
		private int width;
		private int height;
		private int ratedSpeedX;
		private int ratedSpeedY;
		private int maxBulletNum;
		private BulletShooter shooter;
		private Image img;

		Status(int width, int height, int ratedSpeedX, int ratedSpeedY, int maxBulletNum, BulletShooter shooter,
			Image img) {
			this.width = width;
			this.height = height;
			this.ratedSpeedX = ratedSpeedX;
			this.ratedSpeedY = ratedSpeedY;
			this.maxBulletNum = maxBulletNum;
			this.shooter = shooter;
			this.img = img;
		}

		@Override
		public Object clone() throws CloneNotSupportedException {
			// 必须重载该方法使得访问权限变为 public
			return super.clone();
		}
	}

}
