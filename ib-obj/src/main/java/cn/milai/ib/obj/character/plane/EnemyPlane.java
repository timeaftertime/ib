package cn.milai.ib.obj.character.plane;

import cn.milai.ib.constant.Camp;
import cn.milai.ib.container.Container;
import cn.milai.ib.property.Alive;
import cn.milai.ib.property.CanCrash;

public abstract class EnemyPlane extends Plane implements CanCrash {

	private static final String P_SCORE = "score";

	private int score;
	private Plane attackTarget;

	public EnemyPlane(int x, int y, Plane attackTarget, Container container) {
		super(x, y, Camp.ENEMY, container);
		this.attackTarget = attackTarget;
		score = intProp(P_SCORE);
	}

	public void setAttackTarget(Plane target) {
		this.attackTarget = target;
	}

	public Plane getAttackTarget() {
		return attackTarget;
	}

	@Override
	public void onKill(Alive alive) {
		// HasDamage 的这个方法一般用于玩家飞机得分，敌机中不做反应，因为敌机不需要得分
	}

	@Override
	public int getDamage() {
		return 1;
	}

	@Override
	public final int getScore() {
		return score;
	}

}
