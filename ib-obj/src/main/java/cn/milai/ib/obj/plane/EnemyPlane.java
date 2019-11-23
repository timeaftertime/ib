package cn.milai.ib.obj.plane;

import cn.milai.ib.constant.Camp;
import cn.milai.ib.container.Container;
import cn.milai.ib.property.Alive;
import cn.milai.ib.property.CanCrash;

public abstract class EnemyPlane extends Plane implements CanCrash {

	private Plane attackTarget;

	public EnemyPlane(int x, int y, int width, int height, int speedX, int speedY, int maxBulletNum, int life,
			Plane attackTarget, Container container) {
		super(x, y, width, height, speedX, speedY, maxBulletNum, life, Camp.ENEMY, container);
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
		// 这个方法一般用于玩家飞机得分，敌机中不做反应，因为敌机不需要得分
	}

	@Override
	public int getDamage() {
		return 1;
	}

}
