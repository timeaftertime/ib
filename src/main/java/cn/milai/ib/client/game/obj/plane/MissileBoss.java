package cn.milai.ib.client.game.obj.plane;

import cn.milai.ib.client.game.conf.ImageConf;
import cn.milai.ib.client.game.conf.StoryModeConf;
import cn.milai.ib.client.game.conf.gameprops.LifeConf;
import cn.milai.ib.client.game.conf.gameprops.ScoreConf;
import cn.milai.ib.client.game.conf.gameprops.SizeConf;
import cn.milai.ib.client.game.conf.gameprops.SpeedConf;
import cn.milai.ib.client.game.form.GameForm;
import cn.milai.ib.client.game.obj.Bomb;
import cn.milai.ib.client.game.obj.bullet.BulletType;
import cn.milai.ib.client.game.obj.bullet.shooter.DoubleEnemyBulletShooter;
import cn.milai.ib.client.game.obj.bullet.shooter.MissileShooter;
import cn.milai.ib.client.game.obj.property.HasDamage;
import cn.milai.ib.client.game.obj.property.HasLocation;
import cn.milai.ib.client.util.RandomUtil;

public class MissileBoss extends EnemyPlane {

	private static final long PAREPAE_INTERVAL_MILLISECOND = 5000;
	private long preShootTime;

	private Status status;

	public MissileBoss(int x, int y, Plane attackTarget, GameForm container) {
		super(x, y, SizeConf.MISSILE_BOSS_WIDTH, SizeConf.MISSILE_BOSS_HEIGHT, 0, 0, Integer.MAX_VALUE,
				LifeConf.MISSILE_BOSS_LIFE, attackTarget, ImageConf.MISSILE_BOSS, container);
		setBullet(new DoubleEnemyBulletShooter(this));
		setBullet(new MissileShooter(this), BulletType.SIDE);
		status = new Comming();
	}

	@Override
	public int getScore() {
		return ScoreConf.MISSLE_BOSS_SCORE;
	}

	@Override
	protected void beforeMove() {
		status.beforeMove();
		if (System.currentTimeMillis() - Math.max(
				StoryModeConf.MISSILE_BOSS_SHOOT_INTERVAL_MILLISEC * (1.0 * getLife() / LifeConf.MISSILE_BOSS_LIFE),
				StoryModeConf.MISSILE_BOSS_SHOOT_INTERVAL_MILLISEC_LIMIT) >= preShootTime) {
			preShootTime = System.currentTimeMillis();
			shoot();
		}
	}

	@Override
	protected void afterMove() {
		status.afterMove();
	}

	@Override
	public void damagedBy(HasDamage attacker) {
		super.damagedBy(attacker);
		if (isAlive() && (attacker instanceof HasLocation)) {
			HasLocation location = (HasLocation) attacker;
			getContainer().addGameObject(new Bomb(location.getX(), location.getY(), getContainer()));
		}
		if (getImage() != ImageConf.MISSILE_BOSS_DANGER && getLife() <= LifeConf.MISSILE_BOSS_LIFE / 3) {
			setImage(ImageConf.MISSILE_BOSS_DANGER);
		}
	}

	private interface Status {
		void beforeMove();

		void afterMove();
	}

	private class Comming implements Status {

		public Comming() {
			setSpeedY(SpeedConf.MISSILE_BOSS_SPEED_Y);
		}

		@Override
		public void beforeMove() {
			if (getY() >= StoryModeConf.MIN_MISSILE_BOSS_Y) {
				status = new Pareparing();
				return;
			}
		}

		@Override
		public void afterMove() {
		}

	}

	private class Pareparing implements Status {

		private long createTime;

		public Pareparing() {
			setSpeedX((RandomUtil.goalAtPossible(1, 2) ? 1 : (-1)) * SpeedConf.MISSILE_BOSS_SPEED_X);
			setSpeedY(SpeedConf.MISSILE_BOSS_SPEED_Y);
			createTime = System.currentTimeMillis();
		}

		@Override
		public void beforeMove() {
			if (getX() + getWidth() >= getContainer().getWidth()) {
				setSpeedX(-Math.abs(getSpeedX()));
			} else if (getX() <= 0) {
				setSpeedX(Math.abs(getSpeedX()));
			}
			if (RandomUtil.goalAtPossible(StoryModeConf.MISSILE_BOSS_TURN_Y_CHANCE,
					StoryModeConf.MISSILE_BOSS_MAX_TURN_Y_CHANCE)) {
				setSpeedY(getSpeedY() * -1);
			}
		}

		@Override
		public void afterMove() {
			ensureIn(0, getContainer().getWidth(), StoryModeConf.MIN_MISSILE_BOSS_Y, StoryModeConf.MAX_MISSILE_BOSS_Y);
			if (System.currentTimeMillis() - PAREPAE_INTERVAL_MILLISECOND >= createTime) {
				status = new Pursuing();
			}
		}

	}

	private class Pursuing implements Status {

		public Pursuing() {
			setSpeedX(SpeedConf.MISSILE_BOSS_ACC_SPEED_X);
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
