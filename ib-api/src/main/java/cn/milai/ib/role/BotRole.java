package cn.milai.ib.role;

/**
 * 程序角色
 * @author milai
 * @date 2020.04.04
 */
public interface BotRole extends Bot, Role {

	@Override
	default int getCamp() {
		return Camp.ENEMY;
	}

	/**
	 * 设置攻击目标
	 * @param target
	 */
	void setAttackTarget(PlayerRole target);

	/**
	 * 获取当前的攻击目标，若尚未设置，将返回 null
	 * @return
	 */
	PlayerRole getAttackTarget();

	/**
	 * 重新自动选择一个攻击目标
	 */
	void selectAttackTarget();
}
