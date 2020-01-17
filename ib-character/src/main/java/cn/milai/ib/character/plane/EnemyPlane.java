package cn.milai.ib.character.plane;

import java.util.List;

import cn.milai.ib.character.Player;
import cn.milai.ib.constant.Camp;
import cn.milai.ib.container.Container;
import cn.milai.ib.property.Alive;
import cn.milai.ib.property.CanCrash;
import cn.milai.ib.util.RandomUtil;

public abstract class EnemyPlane extends Plane implements CanCrash {

	private static final String P_SCORE = "score";

	private int score;
	private Player attackTarget;

	public EnemyPlane(int x, int y, Container container) {
		super(x, y, Camp.ENEMY, container);
		score = intProp(P_SCORE);
		selectAttackTarget();
	}

	private void selectAttackTarget() {
		List<Player> targets = getContainer().getAll(Player.class);
		if (targets.size() <= 0) {
			return;
		}
		setAttackTarget(targets.get(RandomUtil.nextInt(targets.size())));
	}

	public void setAttackTarget(Player target) {
		this.attackTarget = target;
	}

	public Player getAttackTarget() {
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
