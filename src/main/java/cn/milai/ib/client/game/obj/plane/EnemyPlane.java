package cn.milai.ib.client.game.obj.plane;

import java.awt.Image;

import cn.milai.ib.client.game.form.GameForm;
import cn.milai.ib.client.game.obj.property.Alive;
import cn.milai.ib.client.game.obj.property.CanCrash;

public abstract class EnemyPlane extends Plane implements CanCrash {

	private Plane attackTarget;

	public EnemyPlane(int x, int y, int width, int height, int speedX, int speedY, int maxBulletNum, int life,
			Plane attackTarget, Image image, GameForm container) {
		super(x, y, width, height, speedX, speedY, maxBulletNum, life, Camp.ENEMY, image, container);
		this.attackTarget = attackTarget;
	}

	public void setAttackTarget(Plane target) {
		this.attackTarget = target;
	}

	public Plane getAttackTarget() {
		return attackTarget;
	}

	@Override
	public void onKill(Alive alive) {
		// 这个方法一般用于玩家飞机得分，这里不做反应，因为敌机不需要得分
	}

	@Override
	public int getDamage() {
		return 1;
	}

}
