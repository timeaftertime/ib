package cn.milai.ib.character.plane;

import java.util.List;

import cn.milai.ib.character.property.HasScore;
import cn.milai.ib.container.Container;
import cn.milai.ib.obj.Camp;
import cn.milai.ib.obj.Player;
import cn.milai.ib.util.RandomUtil;

public abstract class EnemyPlane extends AbstractPlane implements HasScore {

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
	public int getDamage() {
		return 1;
	}

	@Override
	public final int getScore() {
		return score;
	}

}
