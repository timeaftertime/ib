package cn.milai.ib.character;

/**
 * 程序角色
 * @author milai
 * @date 2020.04.04
 */
public interface BotCharacter extends Bot, IBCharacter {

	@Override
	default int getCamp() {
		return Camp.ENEMY;
	}

	/**
	 * 设置攻击目标
	 * @param target
	 */
	void setAttackTarget(PlayerCharacter target);

	/**
	 * 获取当前的攻击目标，若尚未设置，将返回 null
	 * @return
	 */
	PlayerCharacter getAttackTarget();

	/**
	 * 重新自动选择一个攻击目标
	 */
	void selectAttackTarget();
}
