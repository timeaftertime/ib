package cn.milai.ib.client.game.obj.plane;

import java.util.Random;

import cn.milai.ib.client.game.conf.BattleConf;
import cn.milai.ib.client.game.conf.ImageConf;
import cn.milai.ib.client.game.form.GameForm;
import cn.milai.ib.client.game.obj.bullet.factory.EnemyBulletFactory;

public class NormalEnemyPlayer extends EnemyPlane {
	
	private Plane attackTarget;
	
	private static Random rand = new Random();
	
	public NormalEnemyPlayer(int x, int y, GameForm container, Plane attackTarget) {
		super(x, y,
				BattleConf.ENEMY_WIDTH, BattleConf.ENEMY_HEIGHT,
				BattleConf.ENEMY_SPEED_X, BattleConf.ENEMY_SPEED_Y,
				BattleConf.ENEMY_MAX_BULLET,
				BattleConf.ENEMY_LIFE,
				ImageConf.ENEMY[rand.nextInt(BattleConf.ENEMY_TYPE)],
				container
				);
		setBullet(new EnemyBulletFactory(this));
		this.attackTarget = attackTarget;
	}

	public void setAttackTarget(Plane target) {
		this.attackTarget = target;
	}

	@Override
	protected void beforeMove() {
		
	}
	
	@Override
	protected void afterMove() {
		redirectIfNeed();
		removeIfOutOfOwner();
		if(attackTarget  == null || !attackTarget.isAlive())
			return;
		randomRedirect();
		if(nearTarget()) {
			randomShoot();
		}
	}
	
	private void randomRedirect() {
		if(getX() < attackTarget.getX() && getSpeedX() < 0) {
			if(rand.nextInt(BattleConf.MAX_ENEMY_FOLLOW_CHANCE) < BattleConf.ENEMY_FOLLOW_CHANCE)
				setSpeedX(-getSpeedX());
		}
		else if(getX() > attackTarget.getX() && getSpeedX() > 0){
			if(rand.nextInt(BattleConf.MAX_ENEMY_FOLLOW_CHANCE) < BattleConf.ENEMY_FOLLOW_CHANCE)
				setSpeedX(-getSpeedX());
		}
	}
	
	private void redirectIfNeed() {
		if(getX() <= 0)
			setSpeedX(Math.abs(getSpeedX()));
		else if(getX() + getWidth() > getContainer().getWidth())
			setSpeedX(-Math.abs(getSpeedX()));
	}

	private void removeIfOutOfOwner() {
		if(getY() > getContainer().getHeight())
			getContainer().removeGameObject(this);
	}
	
	private boolean nearTarget() {
		int targetX = attackTarget.getCenterX();
		int targetWidth = attackTarget.getWidth();
		return getCenterX() > targetX - targetWidth && getCenterX() < targetX + targetWidth;
	}

	private void randomShoot() {
		if(rand.nextInt(BattleConf.MAX_ENEMEY_SHOOT_CHANCE) < BattleConf.ENEMY_SHOOT_CHANCE)
			shoot();
	}

	@Override
	public int getScore() {
		return BattleConf.ENEMY_SCORE;
	}

}
