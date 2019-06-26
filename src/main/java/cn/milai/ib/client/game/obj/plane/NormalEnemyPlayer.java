package cn.milai.ib.client.game.obj.plane;

import java.util.Random;

import cn.milai.ib.client.game.conf.BattleConf;
import cn.milai.ib.client.game.conf.ImageConf;
import cn.milai.ib.client.game.conf.gameprops.LifeConf;
import cn.milai.ib.client.game.conf.gameprops.SizeConf;
import cn.milai.ib.client.game.conf.gameprops.ScoreConf;
import cn.milai.ib.client.game.conf.gameprops.SpeedConf;
import cn.milai.ib.client.game.form.GameForm;
import cn.milai.ib.client.game.obj.bullet.shooter.EnemyBulletShooter;
import cn.milai.ib.client.util.RandomUtil;

public class NormalEnemyPlayer extends EnemyPlane {

	private static Random rand = new Random();

	public NormalEnemyPlayer(int x, int y, Plane attackTarget, GameForm container) {
		super(x, y, SizeConf.ENEMY_WIDTH, SizeConf.ENEMY_HEIGHT, SpeedConf.ENEMY_SPEED_X, SpeedConf.ENEMY_SPEED_Y,
				BattleConf.ENEMY_MAX_BULLET, LifeConf.ENEMY_LIFE, attackTarget,
				ImageConf.ENEMY[rand.nextInt(BattleConf.ENEMY_TYPE)], container);
		setBullet(new EnemyBulletShooter(this));
	}

	@Override
	protected void beforeMove() {

	}

	@Override
	protected void afterMove() {
		redirectIfNeed();
		removeIfOutOfOwner();
		if (getAttackTarget() == null || !getAttackTarget().isAlive())
			return;
		randomRedirect();
		if (nearTarget()) {
			randomShoot();
		}
	}

	private void randomRedirect() {
		if (getX() < getAttackTarget().getX() && getSpeedX() < 0) {
			if (RandomUtil.goalAtPossible(BattleConf.ENEMY_FOLLOW_CHANCE, BattleConf.MAX_ENEMY_FOLLOW_CHANCE))
				setSpeedX(-getSpeedX());
		} else if (getX() > getAttackTarget().getX() && getSpeedX() > 0) {
			if (RandomUtil.goalAtPossible(BattleConf.ENEMY_FOLLOW_CHANCE, BattleConf.MAX_ENEMY_FOLLOW_CHANCE))
				setSpeedX(-getSpeedX());
		}
	}

	private void redirectIfNeed() {
		if (getX() <= 0)
			setSpeedX(Math.abs(getSpeedX()));
		else if (getX() + getWidth() > getContainer().getWidth())
			setSpeedX(-Math.abs(getSpeedX()));
	}

	private void removeIfOutOfOwner() {
		if (getY() > getContainer().getHeight())
			getContainer().removeGameObject(this);
	}

	private boolean nearTarget() {
		int targetX = getAttackTarget().getCenterX();
		int targetWidth = getAttackTarget().getWidth();
		return getCenterX() > targetX - targetWidth && getCenterX() < targetX + targetWidth;
	}

	private void randomShoot() {
		if (RandomUtil.goalAtPossible(BattleConf.ENEMY_SHOOT_CHANCE, BattleConf.MAX_ENEMEY_SHOOT_CHANCE))
			shoot();
	}

	@Override
	public int getScore() {
		return ScoreConf.ENEMY_PLANE_SCORE;
	}

}
