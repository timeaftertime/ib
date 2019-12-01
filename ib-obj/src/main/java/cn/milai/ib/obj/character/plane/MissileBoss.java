package cn.milai.ib.obj.character.plane;

import java.awt.Image;

import cn.milai.ib.constant.BulletType;
import cn.milai.ib.container.Container;
import cn.milai.ib.obj.character.bullet.shooter.DoubleDownBulletShooter;
import cn.milai.ib.obj.character.bullet.shooter.MissileShooter;
import cn.milai.ib.obj.character.explosion.DefaultExplosion;
import cn.milai.ib.property.HasDamage;
import cn.milai.ib.property.Locable;
import cn.milai.ib.util.RandomUtil;

/**
 * 导弹 BOSS
 *
 * @author milai
 */
public class MissileBoss extends EnemyPlane {

	private static final String P_MIN_SHOOT_INTERVAL = "minShootInterval";
	private static final String P_COMMING_MAX_Y = "commingMaxY";
	private static final String P_PURSUING_SPEED_X = "pursuingSpeedX";

	/**
	 * 进入“危险”状态的最大生命值
	 */
	private final int DANGER_LIFE = getLife() / 3;

	/**
	 * 初始生命值
	 */
	private final int INIT_LIFE = getLife();

	private final long MIN_SHOOT_INTERVAL = longProp(P_MIN_SHOOT_INTERVAL);

	private Status status;

	private final Image DANGER_IMG = getImageLoader().loadImage(MissileBoss.class, "danger");

	public MissileBoss(int x, int y, Plane attackTarget, Container container) {
		super(x, y, attackTarget, container);
		setBulletShooter(new DoubleDownBulletShooter(this), BulletType.MAIN);
		setBulletShooter(new MissileShooter(this), BulletType.SIDE);
		status = new Comming();
	}

	@Override
	protected void beforeMove() {
		status.beforeMove();
		updateShootInterval();
		shoot();
	}

	@Override
	protected void afterMove() {
		status.afterMove();
	}

	private void updateShootInterval() {
		setShootInterval((long) Math.max(MIN_SHOOT_INTERVAL, 1.0 * getLife() / INIT_LIFE * getInitShootInterval()));
	}

	@Override
	public void damagedBy(HasDamage attacker) {
		super.damagedBy(attacker);
		if (isAlive() && (attacker instanceof Locable)) {
			Locable location = (Locable) attacker;
			getContainer().addObject(new DefaultExplosion(location.getX(), location.getY(), getContainer()));
		}
		if (getImage() != DANGER_IMG && getLife() <= DANGER_LIFE) {
			setImage(DANGER_IMG);
		}
	}

	@Override
	protected boolean canShoot(BulletType type) {
		if (type == BulletType.MAIN) {
			return super.canShoot(type);
		}
		return getBulletShooterClass(type) != null;
	}

	private interface Status {
		void beforeMove();

		void afterMove();
	}

	private class Comming implements Status {

		private final int COMMING_MAX_Y = proratedIntProp(P_COMMING_MAX_Y);

		public Comming() {
			setSpeedX(0);
		}

		@Override
		public void beforeMove() {
			if (getY() + getHeight() >= COMMING_MAX_Y) {
				status = new Pareparing();
				return;
			}
		}

		@Override
		public void afterMove() {
		}

	}

	private class Pareparing implements Status {

		private final int PREPARE_MIN_Y = proratedIntProp("prepareMinY");
		private final int PREPARE_MAX_Y = proratedIntProp("prepareMaxY");

		private final double TURN_Y_CHANCE = doubleProp("turnYChance");

		/**
		 * 从 Prepareing 转换为 Pursuing 状态的间隔帧数
		 */
		private final long PREPARE_INTERVAL = longProp("prepareInterval");

		private final long CREATE_FRAME = getContainer().currentFrame();

		public Pareparing() {
			setSpeedX((RandomUtil.nextLess(0.5) ? 1 : (-1)) * getInitSpeedX());
			setSpeedY(-getInitSpeedY());
		}

		@Override
		public void beforeMove() {
			if (getX() + getWidth() >= getContainer().getWidth()) {
				setSpeedX(-Math.abs(getSpeedX()));
			} else if (getX() <= 0) {
				setSpeedX(Math.abs(getSpeedX()));
			}
			if (RandomUtil.nextLess(TURN_Y_CHANCE)) {
				setSpeedY(getSpeedY() * -1);
			}
		}

		@Override
		public void afterMove() {
			ensureIn(0, getContainer().getWidth(), PREPARE_MIN_Y, PREPARE_MAX_Y);
			if (getContainer().currentFrame() >= CREATE_FRAME + PREPARE_INTERVAL) {
				status = new Pursuing();
			}
		}

	}

	private class Pursuing implements Status {

		private final int PURSUING_SPEED_X = proratedIntProp(P_PURSUING_SPEED_X);

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
