package cn.milai.ib.obj.plane;

import cn.milai.ib.conf.SystemConf;
import cn.milai.ib.container.Container;
import cn.milai.ib.obj.bullet.shooter.DownBulletShooter;
import cn.milai.ib.util.RandomUtil;

/**
 * 随机跟随攻击目标的敌机
 *
 * @author milai
 */
public class FollowPlane extends EnemyPlane {

	private static final int WIDTH = SystemConf.prorate(48);
	private static final int HEIGHT = SystemConf.prorate(48);
	private static final int SPEED_X = SystemConf.prorate(12);
	private static final int SPEED_Y = SystemConf.prorate(6);

	private static final int MAX_BULLET = 3;
	private static final int INIT_LIFE = 1;
	private static final int SCORE = 1;

	private static final String[] STATUS = { "red", "blue" };

	/******************
	 * FOLLOW_CHANCE / MAX_FOLLOW_CHANCE 即每一帧为跟随攻击目标而转变方向的概率
	 ******************/
	private static final int FOLLOW_CHANCE = 10;
	private static final int MAX_FOLLOW_CHANCE = 100;
	/******************
	 * SHOOT_CHANCE / MAX_SHOOT_CHANCE 即每一帧发起射击的概率
	 ******************/
	private static final int SHOOT_CHANCE = 20;
	private static final int MAX_SHOOT_CHANCE = 100;
	/**
	 * 发射一次子弹的最小帧数间隔
	 */
	private static final int SHOOT_INTERVAL = 10;

	private long lastShootFrame;

	public FollowPlane(int x, int y, Plane attackTarget, Container container) {
		super(x, y, WIDTH, HEIGHT, SPEED_X, SPEED_Y, MAX_BULLET, INIT_LIFE, attackTarget, container);
		setBullet(new DownBulletShooter(this));
	}

	@Override
	protected String getStatus() {
		return STATUS[RandomUtil.nextInt(STATUS.length)];
	}

	@Override
	protected void beforeMove() {

	}

	@Override
	protected void afterMove() {
		redirectIfNeed();
		removeIfOutOfOwner();
		if (getAttackTarget() == null || !getAttackTarget().isAlive()) {
			return;
		}
		randomRedirect();
		if (nearTarget()) {
			randomShoot();
		}
	}

	private void randomRedirect() {
		if (getX() < getAttackTarget().getX() && getSpeedX() < 0) {
			if (RandomUtil.goalAtPossible(FOLLOW_CHANCE, MAX_FOLLOW_CHANCE)) {
				setSpeedX(-getSpeedX());
			}
		} else if (getX() > getAttackTarget().getX() && getSpeedX() > 0) {
			if (RandomUtil.goalAtPossible(FOLLOW_CHANCE, MAX_FOLLOW_CHANCE)) {
				setSpeedX(-getSpeedX());
			}
		}
	}

	private void redirectIfNeed() {
		if (getX() <= 0) {
			setSpeedX(Math.abs(getSpeedX()));
		} else if (getX() + getWidth() > getContainer().getWidth()) {
			setSpeedX(-Math.abs(getSpeedX()));
		}
	}

	private void removeIfOutOfOwner() {
		if (getY() > getContainer().getHeight()) {
			getContainer().removeGameObject(this);
		}
	}

	private boolean nearTarget() {
		int targetX = getAttackTarget().getCenterX();
		int targetWidth = getAttackTarget().getWidth();
		return getCenterX() > targetX - targetWidth && getCenterX() < targetX + targetWidth;
	}

	private void randomShoot() {
		if (RandomUtil.goalAtPossible(SHOOT_CHANCE, MAX_SHOOT_CHANCE)) {
			if (getContainer().currentFrame() >= lastShootFrame + SHOOT_INTERVAL) {
				lastShootFrame = getContainer().currentFrame();
				shoot();
			}
		}
	}

	@Override
	public int getScore() {
		return SCORE;
	}

}
