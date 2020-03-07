package cn.milai.ib.character.plane;

import java.awt.Image;

import cn.milai.ib.character.WeaponType;
import cn.milai.ib.character.bullet.shooter.DoubleDownBulletShooter;
import cn.milai.ib.character.bullet.shooter.MissileShooter;
import cn.milai.ib.character.explosion.DefaultExplosion;
import cn.milai.ib.container.Container;
import cn.milai.ib.loader.ImageLoader;
import cn.milai.ib.obj.IBCharacter;
import cn.milai.ib.obj.Player;
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

	private final Image DANGER_IMG = ImageLoader.load(MissileBoss.class, "danger");

	public MissileBoss(int x, int y, Container container) {
		super(x, y, container);
		setBulletShooter(new DoubleDownBulletShooter(), WeaponType.MAIN);
		setBulletShooter(new MissileShooter(), WeaponType.SIDE);
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
	public synchronized void loseLife(IBCharacter character, int life) throws IllegalArgumentException {
		super.loseLife(character, life);
		if (isAlive()) {
			getContainer().addObject(
				new DefaultExplosion((int) character.getCenterX(), (int) character.getCenterY(), getContainer()));
		}
		if (getImage() != DANGER_IMG && getLife() <= DANGER_LIFE) {
			setImage(DANGER_IMG);
		}
	}

	@Override
	protected boolean canShoot(WeaponType type) {
		if (type == WeaponType.MAIN) {
			return super.canShoot(type);
		}
		return getBulletShooter(type) != null;
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
			Player target = getAttackTarget();
			if (getCenterX() > target.getX() && getCenterX() < target.getX() + target.getWidth()) {
				shoot(WeaponType.SIDE);
				status = new Pareparing();
			}
		}

	}
}
