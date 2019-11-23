package cn.milai.ib.obj.plane;

import java.awt.Image;

import cn.milai.ib.conf.SystemConf;
import cn.milai.ib.constant.BulletType;
import cn.milai.ib.container.Container;
import cn.milai.ib.obj.Bomb;
import cn.milai.ib.obj.bullet.shooter.DoubleDownBulletShooter;
import cn.milai.ib.obj.bullet.shooter.MissileShooter;
import cn.milai.ib.property.HasDamage;
import cn.milai.ib.property.HasLocation;
import cn.milai.ib.util.RandomUtil;

/**
 * 导弹 BOSS
 *
 * @author milai
 */
public class MissileBoss extends EnemyPlane {

	private static final int WIDTH = SystemConf.prorate(120);
	private static final int HEIGHT = SystemConf.prorate(120);
	private static final int SPEED_X = SystemConf.prorate(10);
	private static final int SPEED_Y = SystemConf.prorate(10);

	private static final int INIT_LIFE = 120;
	private static final int SCORE = 100;

	private static final int INIT_SHOOT_INTERVAL = 20;
	private static final int MIN_SHOOT_INTERVAL = 8;

	/**
	 * 进入“危险”状态的最大生命值
	 */
	private static final int DANGER_LIFE = INIT_LIFE / 3;

	/**
	 * 从 Prepareing 转换为 Pursuing 状态的间隔帧数
	 */
	private static final long PAREPAE_INTERVAL = 100;

	/******************
	 * TURN_Y_CHANCE / MAX_TURN_Y_CHANCE 即改变 y 轴速度方向的概率
	 ******************/
	private static final int TURN_Y_CHANCE = 5;
	private static final int MAX_TURN_Y_CHANCE = 100;

	private long lastShootFrame;
	private int shootInterval = INIT_SHOOT_INTERVAL;
	private Status status;

	private final Image DANGER_IMG = getImageLoader().loadImage(MissileBoss.class, "danger");

	public MissileBoss(int x, int y, Plane attackTarget, Container container) {
		super(x, y, WIDTH, HEIGHT, 0, 0, Integer.MAX_VALUE, INIT_LIFE, attackTarget, container);
		setBullet(new DoubleDownBulletShooter(this));
		setBullet(new MissileShooter(this), BulletType.SIDE);
		status = new Comming();
	}

	@Override
	public int getScore() {
		return SCORE;
	}

	@Override
	protected void beforeMove() {
		status.beforeMove();
		updateShootInterval();
		if (getContainer().currentFrame() >= lastShootFrame + shootInterval) {
			lastShootFrame = getContainer().currentFrame();
			shoot();
		}
	}

	@Override
	protected void afterMove() {
		status.afterMove();
	}

	private void updateShootInterval() {
		shootInterval = (int) Math.max(MIN_SHOOT_INTERVAL, 1.0 * getLife() / INIT_LIFE);
	}

	@Override
	public void damagedBy(HasDamage attacker) {
		super.damagedBy(attacker);
		if (isAlive() && (attacker instanceof HasLocation)) {
			HasLocation location = (HasLocation) attacker;
			getContainer().addGameObject(new Bomb(location.getX(), location.getY(), getContainer()));
		}
		if (getImage() != DANGER_IMG && getLife() <= DANGER_LIFE) {
			setImage(DANGER_IMG);
		}
	}

	private interface Status {
		void beforeMove();

		void afterMove();
	}

	private class Comming implements Status {

		private final int LIMIT_Y = SystemConf.prorate(480);

		public Comming() {
			setSpeedY(SPEED_Y);
		}

		@Override
		public void beforeMove() {
			if (getY() + getHeight() >= LIMIT_Y) {
				status = new Pareparing();
				return;
			}
		}

		@Override
		public void afterMove() {
		}

	}

	private class Pareparing implements Status {

		private final int MIN_Y = SystemConf.prorate(12);
		private final int MAX_Y = SystemConf.prorate(480);

		private long createFrame;

		public Pareparing() {
			setSpeedX((RandomUtil.goalAtPossible(1, 2) ? 1 : (-1)) * SPEED_X);
			setSpeedY(-SPEED_Y);
			createFrame = getContainer().currentFrame();
		}

		@Override
		public void beforeMove() {
			if (getX() + getWidth() >= getContainer().getWidth()) {
				setSpeedX(-Math.abs(getSpeedX()));
			} else if (getX() <= 0) {
				setSpeedX(Math.abs(getSpeedX()));
			}
			if (RandomUtil.goalAtPossible(TURN_Y_CHANCE, MAX_TURN_Y_CHANCE)) {
				setSpeedY(getSpeedY() * -1);
			}
		}

		@Override
		public void afterMove() {
			ensureIn(0, getContainer().getWidth(), MIN_Y, MAX_Y);
			if (getContainer().currentFrame() >= createFrame + PAREPAE_INTERVAL) {
				status = new Pursuing();
			}
		}

	}

	private class Pursuing implements Status {

		private final int PURSUING_SPEED_X = SystemConf.frameProrate(18);

		public Pursuing() {
			setSpeedX(PURSUING_SPEED_X);
			setSpeedY(0);
		}

		@Override
		public void beforeMove() {
			if (getCenterX() > getAttackTarget().getCenterX()) {
				setSpeedX(-Math.abs(getSpeedX()));
			}
			if (getCenterX() < getAttackTarget().getCenterX()) {
				setSpeedX(Math.abs(getSpeedX()));
			}
		}

		@Override
		public void afterMove() {
			Plane target = getAttackTarget();
			if (getCenterX() > target.getX() && getCenterX() < target.getX() + target.getWidth()) {
				shoot(BulletType.SIDE);
				status = new Pareparing();
			}
		}

	}
}
