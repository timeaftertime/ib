package cn.milai.ib.obj.character.plane;

import cn.milai.ib.container.Container;
import cn.milai.ib.obj.character.bullet.shooter.DownBulletShooter;
import cn.milai.ib.util.RandomUtil;

/**
 * 随机跟随攻击目标的敌机
 *
 * @author milai
 */
public class FollowPlane extends EnemyPlane {

	private static final String[] STATUS = { "red", "blue" };

	private static final String FOLLOW_CHANCE = "followChance";
	private static final String SHOOT_CHANCE = "shootChance";

	private double followChance;
	private double shootChance;

	public FollowPlane(int x, int y, Plane attackTarget, Container container) {
		super(x, y, attackTarget, container);
		setBulletShooter(new DownBulletShooter(this));
		followChance = doubleProp(FOLLOW_CHANCE);
		shootChance = doubleProp(SHOOT_CHANCE);
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
			if (RandomUtil.nextLess(followChance)) {
				setSpeedX(-getSpeedX());
			}
		} else if (getX() > getAttackTarget().getX() && getSpeedX() > 0) {
			if (RandomUtil.nextLess(followChance)) {
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
			getContainer().removeObject(this);
		}
	}

	private boolean nearTarget() {
		int targetX = getAttackTarget().getCenterX();
		int targetWidth = getAttackTarget().getWidth();
		return getCenterX() > targetX - targetWidth && getCenterX() < targetX + targetWidth;
	}

	private void randomShoot() {
		if (RandomUtil.nextLess(shootChance)) {
			shoot();
		}
	}

}
